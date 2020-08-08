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
 * Esta clase implementa lo que se debe hacer en un hilo de ejecución cuando se quiere enviar una jugada.
 */
public class ThreadEnviarResultado extends Thread
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

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para enviar la jugada.
     * @param pAdivinandoQuien Cliente del juego. pAdivinandoQuien != null.
     * @param pPrincipal Interfaz principal de la aplicación. pPrincipal != null.
     */
    public ThreadEnviarResultado( AdivinaQuien pAdivinandoQuien, InterfazAdivinaQuien pPrincipal )
    {
        adivinaQuien = pAdivinandoQuien;
        principal = pPrincipal;

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecución del hilo que realiza el envío del mensaje y espera la respuesta. <br>
     * Cuando se tiene información sobre el resultado de la jugada entonces se actualiza la interfaz.<br>
     * Si el juego debe terminar entonces muestra quién fue el ganador, termina el encuentro y la conexión al servidor.
     */
    public void run( )
    {
        try
        {
            adivinaQuien.enviarResultado( );
            principal.habilitarTablero( false );
            principal.actualizar( );
            principal.esperarJugada( );
            //TODO Revisar si cambia protocolo
        }
        catch( AdivinaQuienClienteException e )
        {
            principal.mostrarMensajeError( e.getMessage( ), "Jugada" );
        }
    }
}
