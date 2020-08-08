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
public class ThreadEnviarPregunta extends Thread
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
     * Pregunta del jugador.
     */
    private String pregunta;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para enviar la jugada.
     * @param pAdivinandoQuien Cliente del juego. pAdivinandoQuien != null.
     * @param pPrincipal Interfaz principal de la aplicación. pPrincipal != null.
     * @param pPregunta Pregunta del jugador.
     */
    public ThreadEnviarPregunta( AdivinaQuien pAdivinandoQuien, InterfazAdivinaQuien pPrincipal, String pPregunta )
    {
        adivinaQuien = pAdivinandoQuien;
        principal = pPrincipal;
        pregunta = pPregunta;

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
            String rspta = adivinaQuien.realizarJugada( pregunta );

            principal.agregarMensaje( );
            principal.mostrarMensaje( rspta, "Respuesta" );
            principal.habilitarEnvio( );

        }
        catch( AdivinaQuienClienteException e )
        {
            principal.mostrarMensajeError( e.getMessage( ), "Preguntar" );
        }
    }
}
