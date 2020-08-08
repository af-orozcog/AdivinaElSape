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
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.adivinaQuien.cliente.mundo.Persona;
import uniandes.cupi2.adivinaQuien.cliente.mundo.RegistroJugador;

/**
 * Panel con la información de un jugador.
 */
public class PanelJugador extends JPanel
{
    private final static String RUTA = "./data/imagenes/";

    /**
     * Etiqueta con el nombre del jugador.
     */
    private JLabel lblNombre;

    /**
     * Etiqueta con la cantidad de victorias del jugador.
     */
    private JLabel lblVictorias;

    /**
     * Etiqueta con la cantidad de derrotas del jugador.
     */
    private JLabel lblDerrotas;

    /**
     * Etiqueta con la imagen del personaje.
     */
    private JLabel lblPersonaje;

    /**
     * Constructor del panel.<br>
     * <b>post:</b> Se inicializaron todos los componentes del panel.<br>
     * Se inicializó el tipo del panel.
     * @param pTipo Tipo del panel. pTipo pertenece {"Jugador", "Oponente"}.
     */
    public PanelJugador( String pTipo )
    {
        setBorder( new TitledBorder( "Información " + pTipo ) );
        setLayout( new BorderLayout( ) );

        lblPersonaje = new JLabel( );
        lblPersonaje.setIcon( new ImageIcon( RUTA + "incognito.png" ) );
        lblPersonaje.setHorizontalAlignment( JLabel.CENTER );
        add( lblPersonaje, BorderLayout.CENTER );

        JPanel panelAux = new JPanel( );
        panelAux.setOpaque( true );
        panelAux.setBackground( null );
        panelAux.setLayout( new GridLayout( 3, 1, 5, 5 ) );
        add( panelAux, BorderLayout.NORTH );

        lblNombre = new JLabel( "Nombre: " );

        panelAux.add( lblNombre );

        lblVictorias = new JLabel( "Victorias: " );
        panelAux.add( lblVictorias );

        lblDerrotas = new JLabel( "Derrotas:" );
        panelAux.add( lblDerrotas );
    }

    /**
     * Actualiza el panel con la información del jugador dado.
     * @param pJugador Registro del jugador a mostrar. pJugador != null.
     */
    public void actualizar( RegistroJugador pJugador )
    {
        lblNombre.setText( "Nombre: " + pJugador.darLogin( ) );
        lblVictorias.setText( "Victorias: " + pJugador.darPartidasGanadas( ) );
        lblDerrotas.setText( "Derrotas: " + pJugador.darPartidasPerdidas( ) );
    }

    /**
     * Actualiza la imagen del jugador.
     * @param pPersona Personaje a mostrar. pPersona!= null.
     */
    public void actualizarImagen( Persona pPersona )
    {
        lblPersonaje.setIcon( new ImageIcon( RUTA + pPersona.darRutaImagen( ) ) );
    }

    /**
     * Actualiza el panel indicando si tiene el turno.<br>
     * <b>post: </b> Si el jugador tiene el turno, el fondo cambió amarillo.<br>
     * Si el jugador no tiene el turno, el fondo no tiene color.
     * @param tieneTurno True si tiene el turno, false de lo contrario.
     */
    public void actualizarTurno( boolean tieneTurno )
    {
        if( tieneTurno )
        {
            setBackground( Color.yellow );
            lblNombre.setBackground( Color.yellow );
        }
        else
        {
            setBackground( null );
        }
    }

}
