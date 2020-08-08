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
package uniandes.cupi2.adivinaQuien.cliente.mundo;

/**
 * Persona del tablero de juego. <br>
 * TODO inv
 */
public class Persona
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Nombre de la persona.
     */
    private String nombre;

    /**
     * Ruta de la imagen de la persona.
     */
    private String rutaImagen;

    /**
     * Indica si la persona ha sido descartada por el jugador o no.
     */
    private boolean descartada;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea una nueva persona con el nombre y la ruta de imagen dados por parámetro.<br>
     * <b> post: </b> Descartada fue inicializado en false.
     * @param pNombre Nombre de la persona. pNombre != null && pNombre != "".
     * @param pRutaImagen Ruta de la imagen de la persona. pRutaImagen != null && pRutaImagen != "".
     */
    public Persona( String pNombre, String pRutaImagen )
    {
        nombre = pNombre;
        rutaImagen = pRutaImagen;
        descartada = false;
    }

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Retorna el nombre de la persona.
     * @return Nombre de la persona.
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Retorna la ruta de la imagen de la persona.
     * @return Ruta de la imagen de la persona.
     */
    public String darRutaImagen( )
    {
        return rutaImagen;
    }

    /**
     * Retorna si la persona ha sido descartada.
     * @return True si ha sido descartada, false de lo contrario.
     */
    public boolean estaDescartada( )
    {
        return descartada;
    }

    /**
     * Cambia el estado de descartada con el valor dado por parámetro.<br>
     * <b> post: </b> El estado de descartada cambió al valor dado por parámetro.
     * @param pDescartada Nuevo estado de descartada de la persona.
     */
    public void cambiarDescartada( boolean pDescartada )
    {
        descartada = pDescartada;
    }

    /**
     * Retorna el String que representa la información del personaje.
     * @return Nombre del personaje.
     */
    public String toString( )
    {
        return nombre;
    }
}
