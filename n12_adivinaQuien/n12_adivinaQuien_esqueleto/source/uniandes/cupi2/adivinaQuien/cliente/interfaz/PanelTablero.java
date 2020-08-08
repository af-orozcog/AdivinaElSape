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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import uniandes.cupi2.adivinaQuien.cliente.mundo.Persona;

/**
 * Panel con el tablero de juego
 */
public class PanelTablero extends JPanel implements ActionListener
{

    /**
     * Ruta donde se encuentran todas las imágenes.
     */
    private final static String RUTA = "./data/imagenes/";
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación
     */
    private InterfazAdivinaQuien principal;

    /**
     * Cantidad de filas del tablero.
     */
    private int cantFilas;

    /**
     * Cantidad de columnas del tablero.
     */
    private int cantColumnas;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Botones del tablero de juego.
     */
    private JButton[][] btnTableroJuego;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Construye el panel.<br>
     * <b>post:</b> Inicializa el atributo principal.
     * @param pPrincipal Ventana principal de la aplicación. pPrincipal!= null.
     */
    public PanelTablero( InterfazAdivinaQuien pPrincipal )
    {
        principal = pPrincipal;

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Inicializa el tablero con la  información del  tablero recibido.
     * @param tablero Tablero a mostrar en el panel.
     */
    public void inicializarTablero( Persona[][] tablero )
    {
        cantFilas = tablero.length;
        cantColumnas = tablero[ 0 ].length;

        setLayout( new GridLayout( cantFilas, cantColumnas ) );
        btnTableroJuego = new JButton[cantFilas][cantColumnas];

        for( int i = 0; i < cantFilas; i++ )
        {
            for( int j = 0; j < cantColumnas; j++ )
            {
                btnTableroJuego[ i ][ j ] = new JButton( );
                btnTableroJuego[ i ][ j ].setBorder( BorderFactory.createLineBorder( Color.BLACK, 1 ) );
                if( i % 2 == j % 2 )
                {
                    btnTableroJuego[ i ][ j ].setBackground( new Color( 235, 122, 96 ) );
                }
                else
                {
                    btnTableroJuego[ i ][ j ].setBackground( new Color( 0, 157, 210 ) );
                }
                btnTableroJuego[ i ][ j ].setIcon( new ImageIcon( RUTA + tablero[ i ][ j ].darRutaImagen( ) ) );
                btnTableroJuego[ i ][ j ].setEnabled( false );
                btnTableroJuego[ i ][ j ].addActionListener( this );
                btnTableroJuego[ i ][ j ].setActionCommand( i + "," + j );
                add( btnTableroJuego[ i ][ j ] );
            }
        }
        validate( );
        repaint( );
    }
    /**
     * Actualiza el tablero con la información del tablero actual.
     * @param tablero Tablero de juego.
     */
    public void actualizar( Persona[][] tablero )
    {
        for( int i = 0; i < cantFilas; i++ )
        {
            for( int j = 0; j < cantColumnas; j++ )
            {
                Persona persona = tablero[ i ][ j ];
                if( persona.estaDescartada( ) )
                {
                    btnTableroJuego[ i ][ j ].setIcon( new ImageIcon( "./data/imagenes/descartada.png" ) );
                }
                else
                {
                    btnTableroJuego[ i ][ j ].setIcon( new ImageIcon( RUTA + tablero[ i ][ j ].darRutaImagen( ) ) );

                }
            }
        }
    }

    /**
     * Habilita o deshabilita los botones.
     * @param pHabilitar True si se desea habilitar, false de lo contrario.
     */
    public void habilitarBotones( boolean pHabilitar )
    {
        for( int i = 0; i < cantFilas; i++ )
        {
            for( int j = 0; j < cantColumnas; j++ )
            {
                btnTableroJuego[ i ][ j ].setEnabled( pHabilitar );
            }
        }
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );
        String coordenadas[] = comando.split( "," );
        try
        {
            int pFila = Integer.parseInt( coordenadas[ 0 ] );
            int pColumna = Integer.parseInt( coordenadas[ 1 ] );
            principal.descartarPersonaje( pFila, pColumna );
        }
        catch( Exception e )
        {
            // No hace nada
        }       
    }

}
