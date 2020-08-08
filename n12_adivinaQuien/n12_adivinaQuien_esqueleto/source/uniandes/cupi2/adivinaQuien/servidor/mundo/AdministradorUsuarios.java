package uniandes.cupi2.adivinaQuien.servidor.mundo;

import java.io.*;
import java.sql.*;
import java.util.*;
import uniandes.cupi2.adivinaQuien.servidor.mundo.RegistroJugador;


public class AdministradorUsuarios 
{
	//------------------------------
	// ATRIBUTOS
	//------------------------------
	
	/**
	 * Conexion a la base de datos
	 */
	private Connection conexion;
	
	/**
	 * Conjunto de propiedades con la configuracion de la aplicacion
	 */
	private Properties config;
	
	//----------------------------
	// CONSTRUCTOR
	// ---------------------------------
	
	public AdministradorUsuarios ( Properties propiedades)
	{
		config = propiedades;
		
		File data = new File( config.getProperty("admin.db.path"));
		System.setProperty("derby.system.home", data.getAbsolutePath());
	}
	
	// ----------------------------------------
	// METODOS
	// ----------------------------------------
	
	/**
	 * Conecta el administrador a la base de datos
	 * @throws SQLException Se lanza cuando hay problemas con la operacion
	 * @throws Exception Se lanza cuando hay problemas con los controladores
	 */
	public void conectarABD( ) throws SQLException, Exception
	{
		String driver = config.getProperty( "admin.db.driver" );
		Class.forName( driver ).newInstance( );
		
		String url = config.getProperty( " admin.db.url" );
		conexion = DriverManager.getConnection(url);
	}
	
	/**
	 * Desconecta al administrador de la base de datos y la detiene
	 * @throws SQLException
	 */
	public void desconectarBD( ) throws SQLException
	{
		conexion.close();
		String down = config.getProperty( "admin.db.shutdown");
		try
		{
			DriverManager.getConnection( down );
		}
		catch ( SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea la tabla para guardar los usuarios. Si la tabla existe no hace nada
	 * @throws SQLException Se lanza si hay problemas creando la tabla
	 */
	public void inicializarTabla( ) throws SQLException
	{
		Statement s = conexion.createStatement();
		
		boolean crearTabla = false;
		try
		{
			s.executeQuery("SELECT * from usuarios WHERE 1=2");
		}
		catch (SQLException e)
		{
			crearTabla = true;
		}
		if ( crearTabla)
		{
			s.execute("CREATE TABLE usuarios (login varchar(32)), (nombre varchar(32)), contrasenia varchar(32)),PRIMARY KEY (login))");	
		}
		s.close();
	}
	
	
	
	/**
	 *  Metodo que se encarga de consultar la informacion de un jugador
	 *  Si no se encuentra el usuario se lanza una excepcion
	 *  Si se encuentra el usuario pero no coincide su contraseña se lanza excepcion
	 * @param pLogin
	 * @return
	 */
	public RegistroJugador consultarRegistroJugador ( String pLogin, String pContrasenia ) throws SQLException, AdivinaQuienServidorException
	{
		RegistroJugador registro = null;
		
		String sql = "SELECT nombre FROM usuarios WHERE login ='" + pLogin +"' AND contrasenia ='" + pContrasenia +"'";
		Statement st = conexion.createStatement( );
		ResultSet resultado = st.executeQuery( sql );
		
		// Si el resultado es correcto, existe con el login y contraseña pasada por parametro
		if ( resultado.next() )
		{
			String nombre = resultado.getString(1);
			sql = "SELECT cantidadGanadas, cantidadPerdidas FROM resultados WHERE login ='" + pLogin;
			st = conexion.createStatement( );
			resultado = st.executeQuery( sql );
			resultado = st.executeQuery( sql );
			int ganadosP = resultado.getInt(1);
			int perdidosP = resultado.getInt(2);
			registro = new RegistroJugador ( nombre,pLogin ,ganadosP, perdidosP);
			resultado.close();
		}
		// Si el resultado no existe
		else
		{
			// Si el login existe, envía contraseña incorrecta, de lo contrario no esta registrado
			sql = "SELECT nombre FROM usuarios WHERE login ='" + pLogin + "'";
			st = conexion.createStatement( );
			resultado = st.executeQuery( sql );
			if ( !resultado.next())
			{
				st.close();
				throw new AdivinaQuienServidorException(" El usuario no está registrado");
			}
			else {
				st.close();
				throw new AdivinaQuienServidorException("El login y/o contraseña son incorrectos");
			}
		}
		st.close();
		return registro;
	}
	
	/**
	 * Registra al usuario en la base de datos si no existe
	 * @param pLogin
	 * @param pNombre
	 * @param pContrasenia
	 * @return
	 * @throws SQLException
	 * @throws AdivinaQuienServidorException 
	 */
	public RegistroJugador registrarJugador ( String pLogin, String pNombre, String pContrasenia) throws SQLException, AdivinaQuienServidorException
	{
		RegistroJugador registro = null;
		try 
		{
			registro = consultarRegistroJugador(pLogin, pContrasenia);
		}
		catch (AdivinaQuienServidorException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (registro == null) {
				Statement st = conexion.createStatement( );
				String insert = "INSERT INTO resultados (login, ganados, perdidos) VALUES ('" + pLogin + "', 0,0)";
				st.execute( insert );
				insert = "INSERT INTO usuarios (login, nombre, contrasenia) VALUES ('" + pLogin + "','" + pNombre + "' , '" + pContrasenia + "')";
				registro = new RegistroJugador(pNombre,pLogin, 0, 0);
			}
		}
		return registro;
	}
}
