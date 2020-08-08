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
package uniandes.cupi2.adivinaQuien.cliente.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Barra que contiene los men�s de la aplicaci�n
 */
public class BarraMenu extends JMenuBar implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
    /**
     * Constante para iniciar sesi�n.
     */
    private static final String INICIAR_SESION = "Iniciar sesi�n";

    /**
     * Constante para registrarse.
     */
    private static final String REGISTRAR = "Abrir";

    /**
     * Constante para salir del programa.
     */
    private static final String SALIR = "Salir";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es una referencia a la clase principal de la interfaz.
     */
    private InterfazAdivinaQuien principal;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * El men� Archivo.
     */
    private JMenu menuArchivo;

    /**
     * La opci�n IniciarSesi�n del men� Archivo.
     */
    private JMenuItem itemIniciarSesi�n;

    /**
     * La opci�n Registrar del men� Archivo.
     */
    private JMenuItem itemRegistrar;

    /**
     * La opci�n Salir del men� Archivo.
     */
    private JMenuItem itemSalir;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la barra de men�.
     * @param pPrincipal Es una referencia a la clase principal de la interfaz. pPrincipal != null
     */
    public BarraMenu( InterfazAdivinaQuien pPrincipal )
    {
        principal = pPrincipal;

        menuArchivo = new JMenu( "Opciones" );
        menuArchivo.setMnemonic( KeyEvent.VK_I );
        add( menuArchivo );

        itemRegistrar = new JMenuItem( "Registrar" );
        itemRegistrar.setActionCommand( REGISTRAR );
        itemRegistrar.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK ) );
        itemRegistrar.setMnemonic( KeyEvent.VK_R );
        itemRegistrar.addActionListener( this );
        menuArchivo.add( itemRegistrar );

        itemIniciarSesi�n = new JMenuItem( "IniciarSesion" );
        itemIniciarSesi�n.setActionCommand( INICIAR_SESION );
        itemIniciarSesi�n.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_L, ActionEvent.CTRL_MASK ) );
        itemIniciarSesi�n.setMnemonic( KeyEvent.VK_L );
        itemIniciarSesi�n.addActionListener( this );
        menuArchivo.add( itemIniciarSesi�n );

        menuArchivo.addSeparator( );

        itemSalir = new JMenuItem( "Salir" );
        itemSalir.setActionCommand( SALIR );
        itemSalir.addActionListener( this );
        menuArchivo.add( itemSalir );

    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Deshabilita los items para el inicio de sesi�n.
     */
    public void deshabilitarInicioSesion( )
    {
        itemIniciarSesi�n.setEnabled( false );
        itemRegistrar.setEnabled( false );
    }
    /**
     * Ejecuta la acci�n que corresponde a la opci�n del men� que fue seleccionada.
     * @param pEvento Es el evento de seleccionar una opci�n del men�.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );
        if( comando.equals( SALIR ) )
        {
            principal.dispose( );
        }
        else if( comando.equals( INICIAR_SESION ) )
        {
            principal.mostrarDialogoInicioSesion( DialogoInicioSesion.INICIO_SESION );
        }

        else if( comando.equals( REGISTRAR ) )
        {
            principal.mostrarDialogoInicioSesion( DialogoInicioSesion.REGISTRO );
        }
    }

}
