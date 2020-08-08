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

import javax.swing.*;
import java.awt.*;

/**
 * Panel con la imagen encabezado.
 */
public class PanelImagen extends JPanel
{

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel y coloca la imagen del encabezado de la aplicación.
     */
    public PanelImagen( )
    {
        JLabel etiquetaImagen;
        setPreferredSize( new Dimension( 0, 120 ) );
        etiquetaImagen = new JLabel( );
        etiquetaImagen.setHorizontalAlignment( SwingConstants.CENTER );
        etiquetaImagen.setVerticalAlignment( SwingConstants.CENTER );
        etiquetaImagen.setIcon( new ImageIcon( "./data/imagenes/bannerCliente.png" ) );
        setLayout( new BorderLayout( ) );
        add( etiquetaImagen, BorderLayout.CENTER );

    }

}