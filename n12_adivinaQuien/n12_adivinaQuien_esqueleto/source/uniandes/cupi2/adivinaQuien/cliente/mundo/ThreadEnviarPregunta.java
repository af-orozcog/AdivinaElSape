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
 * Esta clase implementa lo que se debe hacer en un hilo de ejecuci�n cuando se quiere enviar una jugada.
 */
public class ThreadEnviarPregunta extends Thread
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
     * Pregunta del jugador.
     */
    private String pregunta;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para enviar la jugada.
     * @param pAdivinandoQuien Cliente del juego. pAdivinandoQuien != null.
     * @param pPrincipal Interfaz principal de la aplicaci�n. pPrincipal != null.
     * @param pPregunta Pregunta del jugador.
     */
    public ThreadEnviarPregunta( AdivinaQuien pAdivinandoQuien, InterfazAdivinaQuien pPrincipal, String pPregunta )
    {
        adivinaQuien = pAdivinandoQuien;
        principal = pPrincipal;
        pregunta = pPregunta;

    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicia la ejecuci�n del hilo que realiza el env�o del mensaje y espera la respuesta. <br>
     * Cuando se tiene informaci�n sobre el resultado de la jugada entonces se actualiza la interfaz.<br>
     * Si el juego debe terminar entonces muestra qui�n fue el ganador, termina el encuentro y la conexi�n al servidor.
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
