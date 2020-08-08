package uniandes.cupi2.adivinaQuien.servidor.mundo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

public class AdivinaQuien {

	// -----------------------------------
	// ATRIBUTOS
	// -----------------------------------
	
	/**
	 * 
	 */
	private ServerSocket receptor;
	
	/**
	 * 
	 */
	private Properties config;
	
	/**
	 * 
	 */
	private AdministradorResultados adminResultados;
	
	/**
	 * 
	 */
	private AdministradorUsuarios adminUsuarios;
	
	/**
	 * 
	 */
	private Socket socketJugadorEnEspera;
	
	/**
	 * 
	 */
	protected Collection encuentros;
	
	//------------------------------------------------------
	// CONSTRUCTOR
	// ------------------------------------------------------
	public AdivinaQuien ( String archivo) throws SQLException, Exception
	{
		encuentros = new Vector<>();
		
		cargarConfiguracion( archivo );
		
		adminResultados = new AdministradorResultados ( config );
		adminResultados.conectarABD();
		adminResultados.inicializarTabla();
		
		adminUsuarios = new AdministradorUsuarios( config );
		adminUsuarios.conectarABD();
		adminUsuarios.inicializarTabla();
	}
	
	//-------------------------------------------------------
	// METODOS
	// ------------------------------------------------------
	
	/**
	 * 
	 * @param archivo
	 * @throws Exception
	 */
	public void cargarConfiguracion(String archivo) throws Exception
	{
		FileInputStream fis = new FileInputStream(archivo);
		config = new Properties();
		config.load(fis);
		fis.close();
	}
	
	/**
	 * 
	 * @return
	 */
	public AdministradorResultados darAdministradorResultados()
	{
		return adminResultados;
	}
	
	/**
	 * 
	 * @return
	 */
	public AdministradorUsuarios darAdministradorUsuarios()
	{
		return adminUsuarios;
	}
	
	/**
	 * Retorna la lista actualizada con los encuentros que estan tomando lugar y no hna acabado
	 * @return La lista con los encuentros que no han terminado
	 */
	public Collection darListaEncuentros ()
	{
		Collection listaActualizada = new Vector();
		
		/*// Se construye la nueva lista actualizada
		Iterator iter = encuentros.iterator();
		while (iter.hasNext())
		{
			Encuentro e = (Encuentro)iter.next();
			if (!e.encuentroTerminado() )
				listaActualizada.add(e);
		}
		 encuentros = listaActualizada;*/
		 return encuentros;
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection consultarRegistrosJugadores() throws SQLException
	{
		return adminResultados.consultarRegistrosJugadores();
	}
	
	/**
	 * Metodo que genera la nueva conexion con el cliente
	 */
	public void recibirConexiones()
	{
		String aux = config.getProperty("servidor.puerto");
		int puerto = Integer.parseInt(aux);
		try
		{
			receptor = new ServerSocket(puerto);
			while (true)
			{
				Socket socketNuevoCliente = receptor.accept();
				
				crearEncuentro(socketNuevoCliente);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				receptor.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	synchronized private void crearEncuentro ( Socket socketNuevoCliente) throws IOException
	{
		if (socketJugadorEnEspera == null)
		{
			// Si no hay jugador en espera se pone el cliente en espera
			socketJugadorEnEspera = socketNuevoCliente;
		}
		else
		{
			// Como hay jugador en espera crea un encuentro entre los dos jugadores
			Socket jug1 = socketJugadorEnEspera;
			Socket jug2 = socketNuevoCliente;
			
			socketJugadorEnEspera = null;
			try
			{
				 Encuentro nuevo = new Encuentro ( jug1, jug2, adminResultados, adminUsuarios);
				 iniciarEncuentro( nuevo );
			}
			catch ( IOException e)
			{
				try
				{
					jug1.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				try
				{
					jug2.close();
				}
				catch (IOException e2)
				{
					e2.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	protected void iniciarEncuentro( Encuentro nuevoEncuentro)
	{
		encuentros.add(nuevoEncuentro);
		nuevoEncuentro.start();
	}
	
	 /**
     * Método para la extensión 1
     * @return respuesta1
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Método para la extensión2
     * @return respuesta2
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }
}
