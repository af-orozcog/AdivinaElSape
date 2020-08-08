package uniandes.cupi2.adivinaQuien.servidor.mundo;

import uniandes.cupi2.adivinaQuien.servidor.mundo.RegistroJugador;

public class JugadorRemoto {

	// -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Registro con la información de los partidos del jugador antes de iniciar el encuentro
     */
    private RegistroJugador registroJugador;
    
    /**
     * representa si el jugador ha ganado la partida actual.
     */
    private boolean victoria;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el objeto para mantener la información del jugador remoto. <br>
     * Al iniciar el encuentro, el puntaje es 0.
     * Al iniciar el encuentro, victoria es false.
     * @param registro El registro del jugador antes de empezar el encuentro actual - registro != null
     */
    public JugadorRemoto( RegistroJugador registro )
    {
        registroJugador = registro;
        victoria = false;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
   
    /**
     * cambia el valor actual del atributo victoria.
     */
    public void cambiarVictoria() {
    	victoria = !victoria;
    }
    
    /**
     * Retorna el registro del jugador
     * @return registroJugador
     */
    public RegistroJugador darRegistroJugador( )
    {
        return registroJugador;
    }
    
    /**
     * retorna el valor que se encuentre en el atributo victoria.
     * @return boolean
     */
    public boolean darVictoria() {
    	return victoria;
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase<br>
     * <b>inv</b><br>
     * registroJugador != null<br>
     */
    private void verificarInvariante( )
    {
        assert ( registroJugador != null ) : "El registros de un jugador no puede ser null";
    }
}
