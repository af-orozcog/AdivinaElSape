package uniandes.cupi2.adivinaQuien.servidor.mundo;

public class RegistroJugador {
	// -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * El nombre del jugador
     */
    private String nombre;
    
    /**
     * 
     */
    private String login;

    /**
     * El número de encuentros ganados hasta el momento
     */
    private int ganados;

    /**
     * El número de encuentros perdidos hasta el momento
     */
    private int perdidos;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un nuevo registro
     * @param nombreP El nombre del jugador - nombre != null
     * @param ganadosP El número de encuentros ganados - ganados >= 0
     * @param perdidosP El número de encuentros perdidos - perdidos >= 0
     */
    public RegistroJugador( String nombreP, String loginP, int ganadosP, int perdidosP )
    {
        nombre = nombreP;
        login = loginP;
        ganados = ganadosP;
        perdidos = perdidosP;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna el nombre del jugador
     * @return nombreJugador
     */
    public String darNombreJugador( )
    {
        return nombre;
    }

    /**
     * Retorna el login del jugador
     * @return el login del jugador
     */
    public String darLoginJugador() {
    	return login;
    }
    
    /**
     * Retorna el número de encuentros ganados por el jugador
     * @return encuentrosGanados
     */
    public int darEncuentrosGanados( )
    {
        return ganados;
    }

    /**
     * Retorna el número de encuentros perdidos por el jugador
     * @return encuentrosPerdidos
     */
    public int darEncuentrosPerdidos( )
    {
        return perdidos;
    }

    /**
     * Retorna una cadena con la información del registro
     * @return Retorna una cadena de la forma INFO: <nombre>: <ganados> victorias: <perdidos> derrotas 
     */
    public String toString( )
    {
        return  login + "- Ganadas: " + ganados + " - Perdidas: " + perdidos;
    }


    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase<br>
     * <b>inv:</b><br>
     * nombreJugador != null<br>
     * encuentrosGanados >= 0<br>
     * encuentrosPerdidos >= 0<br>
     */
    private void verificarInvariante( )
    {
        assert ( nombre != null ) : "El nombre no puede ser null";
        assert ( ganados >= 0 ) : "El número de encuentros ganados debe ser mayor o igual a 0";
        assert ( perdidos >= 0 ) : "El número de encuentros perdidos debe ser mayor o igual a 0";
    }
}
