/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_adivinaQuien
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.adivinaQuien.cliente.mundo;

import uniandes.cupi2.adivinaQuien.cliente.interfaz.InterfazAdivinaQuien;

/**
 * Esta clase implementa lo que se debe hacer en un hilo de ejecución cuando se quiere conectar al cliente con el servidor.
 */
public class ThreadRegistrar extends Thread
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Referencia a adivinando quién.
     */
    private AdivinaQuien adivinaQuien;

    /**
     * Ventana principal de la aplicación
     */
    private InterfazAdivinaQuien principal;

    /**
     * Nombre que utilizará el jugador.
     */
    private String nombre;

    /**
     * Login que utilizará el jugador.
     */
    private String login;

    /**
     * Dirección para localizar al servidor.
     */
    private String servidor;

    /**
     * Puerto a través del cual se realizará la conexión con el servidor.
     */
    private int puerto;

    /**
     * Contraseña del jugador.
     */
    private String contrasenia;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pAdivinandoQuien Cliente del juego. pAdivinandoQuien != null.
     * @param pPrincipal Interfaz principal de la aplicación. pPrincipal != null.
     * @param pDireccionServidor Dirección para localizar al servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto a través del cual se realizará la conexión con el servidor. pPuertoServidor != null.
     * @param pLogin Login que utilizará el jugador. pLogin != null && pLogin != "".
     * @param pNombreJugador Nombre que utilizará el jugador. pNombreJugador != null && pNombreJugador != "".
     * @param pContrasenia Contraseña que utilizará el jugador. pContrasenia != null && pContrasenia != "".
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
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que realiza la conexión con el servidor.<br>
     * 
     */
    public void run( )
    {
        try
        {
            // registra el usuario y recibe la respuesta del servidor
            adivinaQuien.registrar( servidor, puerto, login, nombre, contrasenia );
            principal.inicializarTablero( );

            // recibe la información del rival del servidor y los actualiza en el mundo del cliente
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
