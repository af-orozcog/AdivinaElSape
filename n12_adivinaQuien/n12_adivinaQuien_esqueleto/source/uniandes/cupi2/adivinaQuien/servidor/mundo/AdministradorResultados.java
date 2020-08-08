package uniandes.cupi2.adivinaQuien.servidor.mundo;

import java.io.*;
import java.sql.*;
import java.util.*;

import uniandes.cupi2.adivinaQuien.servidor.mundo.RegistroJugador;

public class AdministradorResultados 
{

	//---------------------------------------
	// ATRIBUTOS
	// ---------------------------------------
	
	/**
	 * Conexion a la base de datos
	 */
	 private Connection conexion;
	 
	 /**
	  * Conjunto de propiedades con la configuracion de la aplicacion
	  */
	 private Properties config;
	 
	 //------------------------------------------
	 // CONSTRUCTOR
	 // ------------------------------------------
	 
	 /**
	  * Construye el administrador de resultados y lo deja lista para conectarse a la base de datos
	  * @param propiedades Las propiedades para la configuracion del administrador
	  */
	 public AdministradorResultados ( Properties propiedades)
	 {
		 config = propiedades;
		 
		 // Establecer la ruta de la ubicacion de la base de datos
		 // Derby usa la propiedad del sistema para ubicar los datos
		 File data = new File ( config.getProperty("admin.db.path") );
		 System.setProperty( "derby.system.home", data.getAbsolutePath() );
	 }
	 
	 // -------------------------------------------------
	 // METODOS
	 // --------------------------------------------------
	 
	 /**
	  * Conecta el administrador a la base de datos
	  * @throws SQLException Se lanza cuando hay problemas con la operacion 
	  * @throws Exception Se lanza cuando hay problemas con los controladoes
	  */
	 public void conectarABD() throws SQLException, Exception
	 {
		 String driver = config.getProperty( "admin.db.driver" );
		 Class.forName( driver ).newInstance();
		 
		 String url = config.getProperty( "admin.db.url");
		 conexion = DriverManager.getConnection(url);
	 }
	 
	 /**
	  * Desocnecta el administrador de la base de datos y la detiene
	  * @throws SQLException SQLException Se lanza cuando hay problemas con la operacion 
	  */
	 public void desconectarBD( ) throws SQLException
	 {
		 conexion.close( );
		 String down = config.getProperty( "admin.db.shutdown" );
		 try
		 {
			 DriverManager.getConnection(down);
		 }
		 catch ( SQLException e )
		 {
			 e.printStackTrace();
		 }
	 }
	 
	 /**
	  * Crea la tabla necesaria para guardar los resultados. Si la tabla existe no hace nada
	  * @throws SQLException Se lanza si hay problemas creando la tabla
	  */
	 public void inicializarTabla( ) throws SQLException
	 {
		 Statement s = conexion.createStatement();
		 
		 boolean crearTabla = false;
		 try
		 {
			 s.executeQuery( "SELECT * FROM resultados WHERE 1=2" );
		 }
		 catch (SQLException e) 
		 {
			// Se lanza excepcion si la tabla no existe
			 crearTabla = true;
		}
		 // Se crea una tabla vacia
		 if  ( crearTabla )
		 {
			 s.execute( "CREATE TABLE resultados (login varchar(32),  cantidadGanadas int, cantidadPerdidas int, PRIMARY KEY (login))");
		 }
		 
		 s.close();
	 }
	
	 
	 /**
	  * Registra una victoria para el jugador
	  * @param login
	  * @throws SQLException
	  */
	 public void registrarVictoria ( String login ) throws SQLException
	 {
		 String sql = "UPDATE resultados SET cantidadGanadas = cantidadGanadas+1 WHERE login ='" + login + "'";
		 
		 Statement st = conexion.createStatement();
		 st.executeUpdate( sql );
		 st.close();
	 }
	 
	 /**
	  * Registrs una derrota para el jugador
	  * @param login
	  * @throws SQLException
	  */
	 public void registrarDerrota ( String login) throws SQLException
	 {
		 String sql = "UPDATE resultados SET cantidadPerdidas = cantidadPerdidas+1 WHERE login ='" + login + "'";
		 
		 Statement st = conexion.createStatement();
		 st.executeUpdate( sql );
		 st.close();
	 }
}
