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
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Panel donde se muestran los mensajes con las preguntas del juego.
 */
public class PanelMensajes extends JPanel
{
    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Área de texto donde se muestran los mensajes.
     */
    private JTextArea txtArea;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel de mensajes.
     */
    public PanelMensajes( )
    {
        setBorder( new TitledBorder( "Registro de mensajes" ) );
        setLayout( new BorderLayout( ) );

        JScrollPane scroll = new JScrollPane( );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

        txtArea = new JTextArea( 6, 10 );
        txtArea.setWrapStyleWord( true );
        txtArea.setLineWrap( true );
        txtArea.setEditable( false );

        scroll.setOpaque( false );
        scroll.getViewport( ).add( txtArea );
        add( scroll );

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Agrega todos los mensajes de la colección al campo de texto, uno por uno.
     * @param pMensajes Lista con los mensajes a mostrar.
     */
    public void agregarMensajes( Collection pMensajes )
    {
        Iterator iter = pMensajes.iterator( );
        while( iter.hasNext( ) )
        {
            String mensaje = ( String )iter.next( );
            txtArea.append( mensaje + "\n" );
            txtArea.setCaretPosition( txtArea.getText( ).length( ) );
        }
    }

    /**
     * Pinta el panel y sus componentes.<br>
     * @param pG Superficie del panel.
     */
    public void paintComponent( Graphics pG )
    {
        ImageIcon icon = new ImageIcon( "./data/imagenes/PraderaAbajo.JPG" );
        pG.drawImage( icon.getImage( ), 0, 0, null );
        setOpaque( false );
        super.paintComponent( pG );
    }
}
