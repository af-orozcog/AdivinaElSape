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
package uniandes.cupi2.adivinaQuien.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.adivinaQuien.cliente.mundo.Persona;
import uniandes.cupi2.adivinaQuien.cliente.mundo.RegistroJugador;

/**
 * Panel con la información y opciones de juego.
 */
public class PanelJuego extends JPanel implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Comando para hacer una pregunta.
     */
    public final static String PREGUNTAR = "Preguntar";

    /**
     * Comando para adivinar el personaje.
     */
    public final static String ADIVINAR = "Adivinar";

    /**
     * Comando para reiniciar el tablero.
     */
    public final static String REINICIAR = "Reiniciar";

    /**
     * Comando para enviar la jugada.
     */
    public final static String ENVIAR_JUGADA = "Enviar jugada";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación.
     */
    private InterfazAdivinaQuien principal;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------
    /**
     * Botón para hacer una pregunta.
     */
    private JButton btnPreguntar;

    /**
     * Botón para adivinar el personaje.
     */
    private JButton btnAdivinar;

    /**
     * Botón para reiniciar el juego.
     */
    private JButton btnReiniciar;

    /**
     * Botón para enviar una jugada.
     */
    private JButton btnEnviarJugada;

    /**
     * Panel con la información del jugador.
     */
    private PanelJugador panelJugador;

    /**
     * Panel con la información del oponente.
     */
    private PanelJugador panelOponente;

    /**
     * Constructor del panel. <b>post:</b> Inicializó todos los componentes del panel.<br>
     * Inicializó el atributo principal.
     * @param pPrincipal Ventana principal de la interfaz. pPrincipal != null.
     */
    public PanelJuego( InterfazAdivinaQuien pPrincipal )
    {
        principal = pPrincipal;
        setBorder( new TitledBorder( "Juego actual" ) );
        setPreferredSize( new Dimension( 200, 0 ) );
        setLayout( new BorderLayout( ) );

        JPanel panelJugadores = new JPanel( );
        panelJugadores.setLayout( new GridLayout( 2, 1 ) );
        add( panelJugadores, BorderLayout.CENTER );

        panelJugador = new PanelJugador( "jugador" );
        panelJugadores.add( panelJugador );

        panelOponente = new PanelJugador( "oponente" );
        panelJugadores.add( panelOponente );

        JPanel panelBotones = new JPanel( );
        panelBotones.setLayout( new GridLayout( 4, 1, 3, 3 ) );
        add( panelBotones, BorderLayout.SOUTH );

        btnPreguntar = new JButton( PREGUNTAR );
        btnPreguntar.addActionListener( this );
        btnPreguntar.setActionCommand( PREGUNTAR );
        btnPreguntar.setEnabled( false );
        panelBotones.add( btnPreguntar );

        btnAdivinar = new JButton( ADIVINAR );
        btnAdivinar.addActionListener( this );
        btnAdivinar.setActionCommand( ADIVINAR );
        btnAdivinar.setEnabled( false );
        panelBotones.add( btnAdivinar );

        btnEnviarJugada = new JButton( ENVIAR_JUGADA );
        btnEnviarJugada.addActionListener( this );
        btnEnviarJugada.setActionCommand( ENVIAR_JUGADA );
        btnEnviarJugada.setEnabled( false );
        panelBotones.add( btnEnviarJugada );

        btnReiniciar = new JButton( REINICIAR );
        btnReiniciar.addActionListener( this );
        btnReiniciar.setActionCommand( REINICIAR );
        btnEnviarJugada.setEnabled( false );
        panelBotones.add( btnReiniciar );
    }

    /**
     * Actualiza los páneles con la información del juego.
     * @param pJugador Información del jugador. pJugador!= null.
     * @param pOponente Información del oponente. pOponente != null.
     * @param pPersonajeJugador Personaje asignado al jugador. pPersonajeJugador != null.
     */
    public void actualizar( RegistroJugador pJugador, RegistroJugador pOponente, Persona pPersonajeJugador )
    {
        panelJugador.actualizar( pJugador );
        panelOponente.actualizar( pOponente );
        panelJugador.actualizarImagen( pPersonajeJugador );
    }

    /**
     * Habilita o deshabilita los botones de inicio de turno de un jugador.
     * @param pHabilitar True si se deben habilitar, false de lo contrario.
     */
    public void habilitarBotones( boolean pHabilitar )
    {
        btnPreguntar.setEnabled( pHabilitar );
        btnAdivinar.setEnabled( pHabilitar );
        btnReiniciar.setEnabled( pHabilitar );
        btnEnviarJugada.setEnabled( false );
    }

    /**
     * Deshabilita la opción para preguntar.<br>
     * <b>post:</b>El botón de pregunta fue deshabilitado .
     */
    public void deshabilitarPregunta( )
    {
        btnPreguntar.setEnabled( false );
        btnAdivinar.setEnabled( false );
    }

    /**
     * Habilita la opción para enviar jugada.<br>
     * <b>post:</b>El botón de envío fue habilitado .
     */
    public void habilitarEnvio( )
    {
        btnEnviarJugada.setEnabled( true );
    }

    /**
     * Cambia el turno actual.
     * @param turnoActual Indica si el jugador tiene el turno actual.
     */
    public void cambiarTurno( boolean turnoActual )
    {
        panelJugador.actualizarTurno( turnoActual );
        panelOponente.actualizarTurno( !turnoActual );
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );
        if( comando.equals( PREGUNTAR ) )
        {
            principal.preguntar( );
        }
        else if( comando.equals( ENVIAR_JUGADA ) )
        {
            principal.enviarJugada( );
        }
        else if( comando.equals( ADIVINAR ) )
        {
            principal.adivinar( );
        }
        else if( comando.equals( REINICIAR ) )
        {
            principal.reiniciar( );
        }

    }
}
