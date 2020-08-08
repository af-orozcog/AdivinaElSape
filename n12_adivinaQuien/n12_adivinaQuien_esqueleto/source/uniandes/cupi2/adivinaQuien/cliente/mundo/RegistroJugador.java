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


/**
 * Clase que tiene la informaci�n del n�mero de partidas ganadas y perdidas de un jugador.<br>
 * <b>inv:</b><br>
 * login != null && login != ""<br>
 * partidasGanadas >= 0<br>
 * partidasPerdidas >= 0<br>
 */
public class RegistroJugador
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Login del jugador.
     */
    private String login;

    /**
     * N�mero de partidas ganadas.
     */
    private int partidasGanadas;

    /**
     * N�mero de partidas perdidas.
     */
    private int partidasPerdidas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un nuevo registro.
     * @param pLogin Login del jugador. pLogin != null && pLogin != "".
     * @param pPartidasGanadas Cantidad de partidas ganadas. pPartidasGanadas >= 0.
     * @param pPartidasPerdidas Cantidad de partidas perdidas. pPartidasPerdidas >= 0.
     */
    public RegistroJugador( String pLogin, int pPartidasGanadas, int pPartidasPerdidas )
    {
        login = pLogin;
        partidasGanadas = pPartidasGanadas;
        partidasPerdidas = pPartidasPerdidas;
        verificarInvariante( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    
    /**
     * Retorna el login del jugador.
     * @return Login del jugador.
     */
    public String darLogin( )
    {
        return login;
    }

    /**
     * Retorna el n�mero de partidas ganadas por el jugador.
     * @return Cantidad de partidas ganadas.
     */
    public int darPartidasGanadas( )
    {
        return partidasGanadas;
    }

    /**
     * Retorna el n�mero de partidas perdidas por el jugador.
     * @return Cantidad de partidas perdidas.
     */
    public int darPartidasPerdidas( )
    {
        return partidasPerdidas;
    }
    
    
    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase<br>
     * <b>inv:</b><br>
     * login != null && login != ""<br>
     * partidasGanadas >= 0<br>
     * partidasPerdidas >= 0<br>
     */
    private void verificarInvariante( )
    {
        assert ( login != null && !login.equals( "" ) ) : "El alias no puede ser null.";
        assert ( partidasGanadas >= 0 ) : "El n�mero de partidas ganadas debe ser mayor o igual a 0.";
        assert ( partidasPerdidas >= 0 ) : "El n�mero de partidas perdidas debe ser mayor o igual a 0.";
    }
}
