/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_adivinaQuien
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.adivinaQuien.cliente.mundo;

import uniandes.cupi2.adivinaQuien.cliente.interfaz.InterfazAdivinaQuien;

/**
 * Esta clase implementa lo que se debe hacer en un hilo de ejecuci�n cuando se quiere conectar al cliente con el servidor.
 */
public class ThreadRegistrar extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia a adivinando qui�n.
     */
    private AdivinaQuien adivinaQuien;

    /**
     * Ventana principal de la aplicaci�n
     */
    private InterfazAdivinaQuien principal;

    /**
     * Nombre que utilizar� el jugador.
     */
    private String nombre;

    /**
     * Login que utilizar� el jugador.
     */
    private String login;

    /**
     * Direcci�n para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a trav�s del cual se realizar� la conexi�n con el servidor.
     */
    private int puerto;

    /**
     * Contrase�a del jugador.
     */
    private String contrasenia;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pAdivinandoQuien Cliente del juego. pAdivinandoQuien != null.
     * @param pPrincipal Interfaz principal de la aplicaci�n. pPrincipal != null.
     * @param pDireccionServidor Direcci�n para localizar al servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto a trav�s del cual se realizar� la conexi�n con el servidor. pPuertoServidor != null.
     * @param pLogin Login que utilizar� el jugador. pLogin != null && pLogin != "".
     * @param pNombreJugador Nombre que utilizar� el jugador. pNombreJugador != null && pNombreJugador != "".
     * @param pContrasenia Contrase�a que utilizar� el jugador. pContrasenia != null && pContrasenia != "".
     */
    public ThreadRegistrar( AdivinaQuien pAdivinandoQuien, InterfazAdivinaQuien pPrincipal, String pDireccionServidor, int pPuertoServidor, String pLogin, String pNombreJugador, String pContrasenia )
    {
        adivinaQuien = pAdivinandoQuien;
        principal = pPrincipal;
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        login = pLogin;
        nombre = pNombreJugador;
        contrasenia = pContrasenia;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que realiza la conexi�n con el servidor.<br>
     * 
     */
    public void run( )
    {
        try
        {
            // registra el usuario y recibe la respuesta del servidor
            adivinaQuien.registrar( servidor, puerto, login, nombre, contrasenia );
            principal.inicializarTablero( );

            // recibe la informaci�n del rival del servidor y los actualiza en el mundo del cliente
            adivinaQuien.actualizarRival( );

            // recibe la info de inicio de partida: turno asignado y persona asignada
            adivinaQuien.iniciarPartida( );

            principal.esperarJugada( );
            principal.actualizar( );
            principal.deshabilitarInicioSesion( );

        }
        catch( AdivinaQuienClienteException e )
        {
            principal.mostrarMensajeError( e.getMessage( ), "Registrar" );
        }

    }
}
