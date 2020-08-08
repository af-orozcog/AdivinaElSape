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
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.adivinaQuien.servidor.mundo.AdivinaQuien;
import uniandes.cupi2.adivinaQuien.servidor.mundo.AdivinaQuienServidorException;

/**
 * Ventana principal del servidor de adivina quién.
 */
public class InterfazServidor extends JFrame
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del servidor.
     */
    private AdivinaQuien servidorAdivinaQuien;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel donde se muestran los registros de los jugadores.
     */
    private PanelJugadores panelJugadores;

    /**
     * Panel donde se muestran las partidas actuales.
     */
    private PanelPartidas panelPartidas;

    /**
     * Panel con las extensiones.
     */
    private PanelExtension panelExtension;

    /**
     * Panel con el encabezado.
     */
    private PanelImagen panelImagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la ventana principal de la aplicación.
     * @param pServidorAdivinaQuien Servidor del juego. pServidorAdivinaQuien != null.
     */
    public InterfazServidor(AdivinaQuien pServidorAdivinaQuien  )
    {
        servidorAdivinaQuien = pServidorAdivinaQuien;
        setLayout( new BorderLayout( ) );
        setSize( 580, 530 );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setTitle( "Servidor adivina quién" );
        setLocationRelativeTo( null );

        JPanel panelAux = new JPanel( );
        panelAux.setLayout( new GridLayout( 2, 1 ) );

        panelPartidas = new PanelPartidas( this );
        panelAux.add( panelPartidas );

        panelJugadores = new PanelJugadores( this );
        panelAux.add( panelJugadores );

        panelExtension = new PanelExtension( this );

        panelImagen = new PanelImagen( );

        add( panelImagen, BorderLayout.NORTH);
        add( panelExtension, BorderLayout.SOUTH );
        add( panelAux, BorderLayout.CENTER);
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Actualiza la lista de batallas mostrada en el panelBatallas.
     */
    public void actualizarBatallas( )
    {
        Collection encuentros = servidorAdivinaQuien.darListaEncuentros( );
        panelPartidas.actualizarEncuentros( encuentros );
    }

    /**
     * Actualiza la lista de jugadores mostrada en el panelJugadores.
     */
    public void actualizarJugadores( )
    {
        try
        {
            Collection jugadores = servidorAdivinaQuien.consultarRegistrosJugadores( );
            panelJugadores.actualizarJugadores( jugadores );
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
            JOptionPane.showMessageDialog( this, "Hubo un error consultando la lista de jugadores:\n" + e.getMessage( ) + ".", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Cierra la ventana y la aplicación.
     */
    public void dispose( )
    {
        super.dispose( );
        try
        {
            servidorAdivinaQuien.darAdministradorResultados( ).desconectarBD( );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
        System.exit( 0 );
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1.
     */
    public void reqFuncOpcion1( )
    {
        String resultado = servidorAdivinaQuien.metodo1( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Método para la extensión 2.
     */
    public void reqFuncOpcion2( )
    {
        String resultado = servidorAdivinaQuien.metodo2( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
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

            String archivoPropiedades = "./data/servidor.properties";
            AdivinaQuien servidorAdivinaQuien= new AdivinaQuien( archivoPropiedades );
            
            InterfazServidor interfaz = new InterfazServidor( servidorAdivinaQuien );
            interfaz.setVisible( true );
            
            servidorAdivinaQuien.recibirConexiones( );

        }
        catch( Exception pEvento )
        {
            JOptionPane.showMessageDialog( null, pEvento.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

}