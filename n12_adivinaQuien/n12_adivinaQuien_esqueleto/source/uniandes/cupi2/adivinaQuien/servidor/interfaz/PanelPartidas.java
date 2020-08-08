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

package uniandes.cupi2.adivinaQuien.servidor.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * Panel donde se muestran las partidas que hay actualmente en el servidor.
 */
public class PanelPartidas extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Representa el comando  Refrescar.
     */
    private static final String REFRESCAR = "Refrescar";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación del servidor.
     */
    private InterfazServidor principal;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Lista donde se muestran las partidas.
     */
    private JList listaBatallas;

    /**
     * Botón que se usa para refrescar la lista de partidas.
     */
    private JButton botonRefrescar;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel.
     * @param pPrincipal Ventana principal del servidor. pPrincipal != null.
     */
    public PanelPartidas( InterfazServidor pPrincipal )
    {
        principal = pPrincipal;
        setLayout( new BorderLayout( ) );
        setSize(new Dimension(500,200));
        
        JScrollPane scroll = new JScrollPane( );
        scroll.setPreferredSize(new Dimension(500, 150));
        listaBatallas = new JList( );
        scroll.getViewport( ).add( listaBatallas );
        add(scroll, BorderLayout.CENTER);  
        
        JPanel panelRefrescar= new JPanel(); 
        panelRefrescar.setLayout(new GridBagLayout());
        botonRefrescar = new JButton( "Refrescar" );
        botonRefrescar.addActionListener( this );
        botonRefrescar.setActionCommand( REFRESCAR );
                
        GridBagConstraints gbc= new GridBagConstraints(); 
        gbc.gridx=0;
        gbc.gridy=0;      
        gbc.fill= GridBagConstraints.BOTH;
        gbc.insets= new Insets(5,0,0,0);
        panelRefrescar.add( botonRefrescar, gbc );
        add(panelRefrescar, BorderLayout.SOUTH);
        setBorder( new TitledBorder( "Partidas" ) );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Actualiza la lista mostrada de partidas.
     * @param pPartidas Colección de las partidas que hay actualmente en curso. pPartidas != null.
     */
    public void actualizarEncuentros( Collection pPartidas )
    {
        listaBatallas.setListData( pPartidas.toArray( ) );
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );

        if( REFRESCAR.equals( comando ) )
        {
            principal.actualizarBatallas( );
        }
    }

}
