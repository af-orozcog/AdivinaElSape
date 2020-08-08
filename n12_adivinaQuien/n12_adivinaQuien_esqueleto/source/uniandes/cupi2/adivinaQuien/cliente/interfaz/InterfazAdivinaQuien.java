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

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadEsperarJugadaRival;
import uniandes.cupi2.adivinaQuien.cliente.mundo.AdivinaQuien;
import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadEnviarPregunta;
import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadEnviarResultado;
import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadIniciarSesion;
import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadIntentarAdivinar;
import uniandes.cupi2.adivinaQuien.cliente.mundo.ThreadRegistrar;

/**
 * Ventana principal de la interfaz
 */
public class InterfazAdivinaQuien extends JFrame
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
    /**
     * Clase principal del mundo.
     */
    private AdivinaQuien adivinaQuien;
    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Barra con el menú-
     */
    private BarraMenu barraMenu;

    /**
     * Panel con la imagen.
     */
    private PanelImagen panelImagen;

    /**
     * Panel con el tablero de juego.
     */
    private PanelTablero panelTablero;

    /**
     * Panel con la información y opciones de juego.
     */
    private PanelJuego panelJuego;

    /**
     * Panel con los mensajes del juego.
     */
    private PanelMensajes panelMensajes;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la ventana con todos sus componentes.<br>
     * <b>post:</b> Se inicializaron todos los componentes de la interfaz.
     */
    public InterfazAdivinaQuien( )
    {
        setTitle( "Adivina  quién" );
        setSize( new Dimension( 950, 750 ) );

        setLayout( new BorderLayout( ) );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( EXIT_ON_CLOSE );

        barraMenu = new BarraMenu( this );
        setJMenuBar( barraMenu );

        panelImagen = new PanelImagen( );
        add( panelImagen, BorderLayout.NORTH );

        JPanel panelAux = new JPanel( );
        panelAux.setLayout( new BorderLayout( ) );
        add( panelAux, BorderLayout.CENTER );

        panelTablero = new PanelTablero( this );
        panelAux.add( panelTablero, BorderLayout.CENTER );

        panelMensajes = new PanelMensajes( );
        panelAux.add( panelMensajes, BorderLayout.SOUTH );

        panelJuego = new PanelJuego( this );
        add( panelJuego, BorderLayout.EAST );
    }
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Muestra el diálogo para el inicio de sesión.
     * @param pTipo Tipo del diálogo a crear. pTipo pertenece {DialogoInicioSecion.REGISTRO, DialogoInicioSecion.InicioSesion}
     */
    public void mostrarDialogoInicioSesion( String pTipo )
    {
        DialogoInicioSesion dialogo = new DialogoInicioSesion( this, pTipo );
        dialogo.setVisible( true );
    }

    /**
     * Muestra un mensaje de error con la información dada.
     * @param pMensaje Mensaje de error a mostrar. pMensaje != null.
     * @param pTitulo Título del mensaje a mostrar. pTitulo != null.
     */
    public void mostrarMensajeError( String pMensaje, String pTitulo )
    {
        JOptionPane.showMessageDialog( this, pMensaje, pTitulo, JOptionPane.ERROR_MESSAGE );
    }

    /**
     * Muestra la respuesta de una pregunta.
     * @param pRespuesta Respuesta a mostrar. pRespuesta != null.
     * @param pTitulo Título del mensaje a mostrar.pTitulo != null.
     */
    public void mostrarMensaje( String pRespuesta, String pTitulo )
    {
        JOptionPane.showMessageDialog( this, pRespuesta, pTitulo, JOptionPane.INFORMATION_MESSAGE );
    }
    /**
     * Le avisa al usuario quién es el ganador del juego.
     * @param pGanador Nombre del ganador del juego.
     */
    public void mostrarGanador( String pGanador )
    {
        JOptionPane.showMessageDialog( this, "El ganador del encuentro fue " + pGanador + ".", "Fin del juego", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Registra al jugador con los datos dados.
     * @param pDireccionIp Dirección IP del servidor. pDireccionIp != null && pDireccionIp != "".
     * @param pPuerto Puerto del servidor.
     * @param pLogin Login del usuario a registrar. pLogin != null && pLogin != "".
     * @param pNombre Nombre del usuario a registrar. pNombre != null && pNombre != "".
     * @param pContrasenia Contraseña del usuario a registrar. pContrasenia != null && pContrasenia != "".
     */
    public void registrar( String pDireccionIp, int pPuerto, String pLogin, String pNombre, String pContrasenia )
    {
        try
        {
            adivinaQuien = new AdivinaQuien( );
            ThreadRegistrar thread = new ThreadRegistrar( adivinaQuien, this, pDireccionIp, pPuerto, pLogin, pNombre, pContrasenia );
            thread.start( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Registrar", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Inicia sesión del jugador dado por parámetro.
     * @param pDireccionIp Dirección IP del servidor. pDireccionIp != null && pDireccionIp != "".
     * @param pPuerto Puerto del servidor.
     * @param pLogin Login del usuario a iniciar sesión. pLogin != null && pLogin != "".
     * @param pContrasenia Contraseña del usuario a iniciar sesión. pContrasenia != null && pContrasenia != "".
     */
    public void iniciarSesion( String pDireccionIp, int pPuerto, String pLogin, String pContrasenia )
    {
        try
        {
            adivinaQuien = new AdivinaQuien( );
            ThreadIniciarSesion thread = new ThreadIniciarSesion( adivinaQuien, this, pDireccionIp, pPuerto, pLogin, pContrasenia );
            thread.start( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Registrar", JOptionPane.ERROR_MESSAGE );
        }
    }
    /**
     * Descarta el personaje que se encuentra en la fila y columna dadas.
     * @param pFila Fila del jugador a descartar. pFila está dentro del tablero.
     * @param pColumna Columna del jugador a descartar. pColumna está dentro del tablero.
     */
    public void descartarPersonaje( int pFila, int pColumna )
    {
        adivinaQuien.descartar( pFila, pColumna );
        actualizarTablero( );
    }

    /**
     * Inicializa el tablero.
     */
    public void inicializarTablero( )
    {
        panelTablero.inicializarTablero( adivinaQuien.darTablero( ) );
    }

    /**
     * Actualiza la interfaz con la información actual del juego.
     */
    public void actualizarTablero( )
    {
        panelTablero.actualizar( adivinaQuien.darTablero( ) );
    }

    /**
     * Actualiza la información de los jugadores.
     */
    public void actualizar( )
    {
        panelJuego.actualizar( adivinaQuien.darJugador( ), adivinaQuien.darOponente( ), adivinaQuien.darPersonAsignada( ) );
        boolean turnoActual = adivinaQuien.darEstadoJuego( ) == AdivinaQuien.TURNO_ACTUAL;
        if( turnoActual )
        {
            habilitarTablero( true );
        }
        panelJuego.cambiarTurno( adivinaQuien.darEstadoJuego( ) == AdivinaQuien.TURNO_ACTUAL );
    }

    /**
     * Habilita el envío de la jugada.<br>
     * <b>post:</b> El botón para el envío de la jugada fue habilitado.
     */
    public void habilitarEnvio( )
    {
        panelJuego.habilitarEnvio( );
    }

    /**
     * Deshabilita el inicio de sesión.<br>
     * <b>post:</b> Los items de inicio de sesión están deshabilitados.
     */
    public void deshabilitarInicioSesion( )
    {
        barraMenu.deshabilitarInicioSesion( );
    }

    /**
     * Habilita o desabilita el tablero y las opciones de juego.
     * @param pHabilitar True si se desea habilitar el tablero, false de lo contrario.
     */
    public void habilitarTablero( boolean pHabilitar )
    {
        panelTablero.habilitarBotones( pHabilitar );
        panelJuego.habilitarBotones( pHabilitar );
    }

    /**
     * Agrega una nueva pregunta al panel de mensajes.
     */
    public void agregarMensaje( )
    {
        panelMensajes.agregarMensajes( adivinaQuien.darListaPreguntas( ) );
    }

    /**
     * Muestra un diálogo para hacer una pregunta al adivinaQuien.<br>
     * <b>post: </<b> Inicia un thread de enviar pregunta.
     */
    public void preguntar( )
    {
        String respuesta = JOptionPane.showInputDialog( this, "Ingrese la pregunta que desea hacer.\n(Recuerde que la respuesta debe ser si o no)", "Pregunta", JOptionPane.QUESTION_MESSAGE );
        if( respuesta != null && !respuesta.isEmpty( ) )
        {
            Thread t = new ThreadEnviarPregunta( adivinaQuien, this, respuesta );
            t.start( );
        }
        panelJuego.deshabilitarPregunta( );
    }

    /**
     * Inicia el thread de enviar jugada.
     */
    public void enviarJugada( )
    {
        Thread t = new ThreadEnviarResultado( adivinaQuien, this );
        t.start( );
    }

    /**
     * Muestra el mensaje para que el usuario responda la pregunta.
     * @param pPregunta Pregunta que debe responder el usuario. pPregunta != null && pPregunta != "".
     * @return Respuesta del usuario a la pregunta.
     */
    public String responder( String pPregunta )
    {
        String respuesta;
        int resp = JOptionPane.showConfirmDialog( this, pPregunta, "Responder pregunta", JOptionPane.YES_NO_OPTION );
        if( resp == JOptionPane.YES_OPTION )
        {
            respuesta = "SI";
        }
        else
        {
            respuesta = "NO";
        }
        return respuesta;
    }

    /**
     * Inicia el thread para esperar una jugada.
     */
    public void esperarJugada( )
    {
        if( adivinaQuien.darEstadoJuego( ) == AdivinaQuien.TURNO_RIVAL )
        {
            habilitarTablero( false );
            Thread t = new ThreadEsperarJugadaRival( adivinaQuien, this );
            t.start( );

        }
    }

    /**
     * Permite al usuario adivinar el personaje del oponente.<br>
     * <b>post:</b> Inicia el thread de intentar adivinar.
     */
    public void adivinar( )
    {
        String[] lista = adivinaQuien.darListaPersonas( );
        JComboBox<String> cmbPersonas = new JComboBox<String>( lista );
        JOptionPane.showMessageDialog( null, cmbPersonas, "Seleccione el personaje", JOptionPane.QUESTION_MESSAGE );
        String seleccionado = ( String )cmbPersonas.getSelectedItem( );
        if( seleccionado != null && !seleccionado.isEmpty( ) )
        {
            Thread t = new ThreadIntentarAdivinar( adivinaQuien, this, seleccionado );
            t.start( );
        }
    }

    /**
     * Reinicia el tablero de juego.
     */
    public void reiniciar( )
    {
        adivinaQuien.reiniciarTablero( );
        actualizarTablero( );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz.
     * @param pArgs Parámetros de ejecución que no son usados. pArgs != null.
     */
    public static void main( String[] pArgs )
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
        InterfazAdivinaQuien interfaz = new InterfazAdivinaQuien( );
        interfaz.setVisible( true );
    }
}
