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
package uniandes.cupi2.adivinaQuien.cliente.mundo;

/**
 * Clase que representa una excepci�n en el sistema.
 */
public class AdivinaQuienClienteException extends Exception
{
    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye una nueva excepci�n.
     * @param pMensaje Mensaje de la excepci�n. pMensaje != null && pMensaje != "".
     */
    public AdivinaQuienClienteException( String pMensaje )
    {
        super( pMensaje );
    }
}
