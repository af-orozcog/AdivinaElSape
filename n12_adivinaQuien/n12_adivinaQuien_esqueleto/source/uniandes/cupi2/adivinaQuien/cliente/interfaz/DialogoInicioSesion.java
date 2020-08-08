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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Dialogo para el registro o inicio de sesión.
 */
public class DialogoInicioSesion extends JDialog implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para indicar que se desea registrar un nuevo jugador.
     */
    public final static String REGISTRO = "Registrar";

    /**
     * Constante para indicar que se desea iniciar sesión.
     */
    public final static String INICIO_SESION = "Iniciar sesión";

    /**
     * Constante para iniciar el inicio de sesión.
     */
    public final static String ACEPTAR = "Aceptar";

    /**
     * Constante para cancelar el inicio de sesión.
     */
    public final static String CANCELAR = "Cancelar";
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación.
     */
    private InterfazAdivinaQuien principal;

    /**
     * Tipo del diálogo.
     */
    private String tipo;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Campo de texto para la dirección ip del servidor.
     */
    private JTextField txtDireccionServidor;

    /**
     * Campo de texto para el puerto del servidor.
     */
    private JTextField txtPuertoServidor;

    /**
     * Campo de texto para el login del jugador.
     */
    private JTextField txtLogin;

    /**
     * Campo de texto para el nombre del jugador.
     */
    private JTextField txtNombreJugador;

    /**
     * Campo de texto para la contraseña del jugador.
     */
    private JPasswordField txtContrasenia;

    /**
     * Botón para el inicio de sesión.
     */
    private JButton btnAceptar;

    /**
     * Botón para cancelar el inicio de sesión.
     */
    private JButton btnCancelar;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Construye el diálogo con todos sus componentes.
     * @param pPrincipal Ventana principal de la aplicación. pPrincipal != null
     * @param pTipo Tipo del diálogo a crear. pTipo pertenece{REGISTRO, INICIO_SESION}
     */
    public DialogoInicioSesion( InterfazAdivinaQuien pPrincipal, String pTipo )
    {
        principal = pPrincipal;
        tipo = pTipo;

        setTitle( pTipo );
        setSize( 500, tipo.equals( REGISTRO ) ? 310 : 270 );
        setLayout( new BorderLayout( ) );
        setLocationRelativeTo( principal );

        JPanel panelInformacion = new JPanel( );
        panelInformacion.setLayout( new BorderLayout( ) );
        add( panelInformacion );

        JPanel panelServidor = new JPanel( );
        panelServidor.setPreferredSize( new Dimension( 0, 100 ) );
        panelInformacion.add( panelServidor, BorderLayout.NORTH );
        panelServidor.setBorder( new TitledBorder( "Datos servidor" ) );
        panelServidor.setLayout( new GridLayout( 2, 2, 5, 5 ) );

        JLabel lblDireccion = new JLabel( "Dirección ip:" );
        panelServidor.add( lblDireccion );

        txtDireccionServidor = new JTextField( "Localhost" );
        panelServidor.add( txtDireccionServidor );

        JLabel lblPuerto = new JLabel( "Puerto:" );
        panelServidor.add( lblPuerto );

        txtPuertoServidor = new JTextField( "9999" );
        panelServidor.add( txtPuertoServidor );

        JPanel panelDatosJugador = new JPanel( );
        panelDatosJugador.setBorder( new TitledBorder( "Datos jugador" ) );
        panelDatosJugador.setLayout( new GridLayout( tipo.equals( REGISTRO ) ? 3 : 2, 2, 5, 5 ) );
        panelInformacion.add( panelDatosJugador, BorderLayout.CENTER );

        JLabel lblLogin = new JLabel( "Login:" );
        panelDatosJugador.add( lblLogin );

        txtLogin = new JTextField( );
        panelDatosJugador.add( txtLogin );

        if( tipo.equals( REGISTRO ) )
        {
            JLabel lblNombre = new JLabel( "Nombre:" );
            panelDatosJugador.add( lblNombre );

            txtNombreJugador = new JTextField( );
            panelDatosJugador.add( txtNombreJugador );
        }

        JLabel lblContrasenia = new JLabel( "Contraseña" );
        panelDatosJugador.add( lblContrasenia );

        txtContrasenia = new JPasswordField( );
        panelDatosJugador.add( txtContrasenia );

        JPanel panelBotones = new JPanel( );
        panelBotones.setLayout( new GridLayout( 1, 2, 10, 10 ) );
        panelBotones.setBorder( new EmptyBorder( 3, 55, 3, 55 ) );
        panelBotones.setPreferredSize( new Dimension( 0, 40 ) );
        add( panelBotones, BorderLayout.SOUTH );

        btnAceptar = new JButton( ACEPTAR );
        btnAceptar.setActionCommand( ACEPTAR );
        btnAceptar.addActionListener( this );
        panelBotones.add( btnAceptar );

        btnCancelar = new JButton( CANCELAR );
        btnCancelar.setActionCommand( CANCELAR );
        btnCancelar.addActionListener( this );
        panelBotones.add( btnCancelar );

        JLabel lblImagen = new JLabel( );
        lblImagen.setIcon( new ImageIcon( "./data/imagenes/imagen_dialogo.png" ) );
        add( lblImagen, BorderLayout.WEST );
    }
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Acción que generó el evento. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String comando = pEvento.getActionCommand( );
        if( comando == ACEPTAR )
        {

            String direccion = txtDireccionServidor.getText( );
            String strPuerto = txtPuertoServidor.getText( );
            String login = txtLogin.getText( );
            String contrasenia = txtContrasenia.getText( );
            try
            {
                int puerto = Integer.parseInt( strPuerto );
                if( direccion.isEmpty( ) || strPuerto.isEmpty( ) || login.isEmpty( ) || contrasenia.isEmpty( ) )
                {
                    JOptionPane.showMessageDialog( this, "Debe ingresar todos los valores.", tipo, JOptionPane.WARNING_MESSAGE );
                }
                else
                {
                    if( tipo.equals( REGISTRO ) )
                    {
                        String nombre = txtNombreJugador.getText( );
                        if( nombre.isEmpty( ) )
                        {
                            JOptionPane.showMessageDialog( this, "Debe ingresar todos los valores.", tipo, JOptionPane.WARNING_MESSAGE );
                        }
                        else
                        {
                            principal.registrar( direccion, puerto, login, nombre, contrasenia );
                            dispose( );
                        }
                    }
                    else if( tipo.equals( INICIO_SESION ) )
                    {

                        principal.iniciarSesion( direccion, puerto, login, contrasenia );
                        dispose( );
                    }
                }
            }
            catch( NumberFormatException nfe )
            {
                JOptionPane.showMessageDialog( this, "El puerto debe  ser un valor numérico.", tipo, JOptionPane.ERROR_MESSAGE );
            }
        }
        else if( comando.equals( CANCELAR ) )
        {
            dispose( );
        }
    }

}
