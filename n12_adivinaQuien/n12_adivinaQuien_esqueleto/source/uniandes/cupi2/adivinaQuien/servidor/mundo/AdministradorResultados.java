package uniandes.cupi2.adivinaQuien.servidor.mundo;

import java.io.*;
import java.sql.*;
import java.util.*;

import uniandes.cupi2.adivinaQuien.cliente.mundo.RegistroJugador;

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
			 s.execute( "CREATE TABLE resultados (login varchar(32)),  cantidadGanadas int, cantidadPerdidas int, PRIMARY KEY (login))");
		 }
		 
		 s.close();
	 }
	 
	 /**
	  * Este método se encarga de consultar la información de un jugador (encuentros ganados y encuentros perdidos). <br>
	  * Si no se encuentra un registro del jugador en la base de datos, entonces se crea uno nuevo.
	  * @param pLogin
	  * @return
	  * @throws SQLException
	  */
	 public RegistroJugador consultarRegistroJugador ( String pLogin ) throws SQLException
	 {
		 RegistroJugador registro = null;
		 
		 String sql = "SELECT cantidadGanadas, cantidadPerdidas FROM resultados WHERE login ='" + pLogin + "'";
		 Statement st = conexion.createStatement( );
		 ResultSet resultado = st.executeQuery( sql );
		 
		 if ( resultado.next() )
		 {
			 int cantidadGanadas = resultado.getInt(1);
			 int cantidadPerdidas = resultado.getInt(2);
			 
			 registro = new RegistroJugador ( pLogin, cantidadGanadas, cantidadPerdidas );
			 
			 resultado.close( );
		 }
		 else
		 {
			 resultado.close();
			 
			 String insert = "INSERT into resultados (login, cantidadGanadas, cantidadPerdidas) VALUES ('" + pLogin + "',0,0)";
			 st.execute( insert );

			 registro = new RegistroJugador( pLogin,0,0);
		 }
		 st.close();
		 return registro;
	 }
	 
	 /**
	  * Este método se encarga de consultar la información de todos los jugadores (encuentros ganados y encuentros perdidos).
	  * @return Retorna una colección de los registros (RegistroJugador) de victorias y derrotas
	  * @throws SQLException Se lanza esta excepción si hay problemas en la comunicación con la base de datos
	  */
	 public Collection consultarRegistrosJugadores( ) throws SQLException
	 {
		 Collection registros = new LinkedList();
		 
		 String sql = " SELECT login, cantidadGanadas, cantidadPerdidas FROM resultados";
		 
		 Statement st = conexion.createStatement( );
		 ResultSet resultado = st.executeQuery( sql );
		 
		 while ( resultado.next() )
		 {
			 String login = resultado.getString( 1 );
			 int cantidadGanadas = resultado.getInt( 2 );
			 int cantidadPerdidas = resultado.getInt( 3 );
			 
			 RegistroJugador registro = new RegistroJugador ( login, cantidadGanadas, cantidadPerdidas );
			 registros.add( registro );
		 }
		 
		 resultado.close();
		 st.close();
		 
		 return registros;
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
		 String sql = "UPDATE resultados SET cantidadPerdidas = cantidadPerdidas+1 WHERE logih ='" + login + "'";
		 
		 Statement st = conexion.createStatement();
		 st.executeUpdate( sql );
		 st.close();
	 }
}
