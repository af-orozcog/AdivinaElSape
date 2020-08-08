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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Barra que contiene los menús de la aplicación
 */
public class BarraMenu extends JMenuBar implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
    /**
     * Constante para iniciar sesión.
     */
    private static final String INICIAR_SESION = "Iniciar sesión";

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
     * El menú Archivo.
     */
    private JMenu menuArchivo;

    /**
     * La opción IniciarSesión del menú Archivo.
     */
    private JMenuItem itemIniciarSesión;

    /**
     * La opción Registrar del menú Archivo.
     */
    private JMenuItem itemRegistrar;

    /**
     * La opción Salir del menú Archivo.
     */
    private JMenuItem itemSalir;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la barra de menú.
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

        itemIniciarSesión = new JMenuItem( "IniciarSesion" );
        itemIniciarSesión.setActionCommand( INICIAR_SESION );
        itemIniciarSesión.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_L, ActionEvent.CTRL_MASK ) );
        itemIniciarSesión.setMnemonic( KeyEvent.VK_L );
        itemIniciarSesión.addActionListener( this );
        menuArchivo.add( itemIniciarSesión );

        menuArchivo.addSeparator( );

        itemSalir = new JMenuItem( "Salir" );
        itemSalir.setActionCommand( SALIR );
        itemSalir.addActionListener( this );
        menuArchivo.add( itemSalir );

    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Deshabilita los items para el inicio de sesión.
     */
    public void deshabilitarInicioSesion( )
    {
        itemIniciarSesión.setEnabled( false );
        itemRegistrar.setEnabled( false );
    }
    /**
     * Ejecuta la acción que corresponde a la opción del menú que fue seleccionada.
     * @param pEvento Es el evento de seleccionar una opción del menú.
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
