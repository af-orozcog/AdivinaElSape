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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Clase que representa el juego de un jugador. <b>inv</b><br>
 * estadoJuego pertenece a {SIN_CONECTAR, TURNO_RIVAL, TURNO_ACTUAL}.<br>
 * estadoJuego = SIN_CONECTAR => juegoTerminado = true.<br>
 * estadoJuego != SIN_CONECTAR => canal != null.<br>
 * estadoJuego != SIN_CONECTAR => outWriter != null.<br>
 * estadoJuego != SIN_CONECTAR => inReader != null.<br>
 * estadoJuego != SIN_CONECTAR => servidor != null.<br>
 * estadoJuego != SIN_CONECTAR => mensajesSinLeer != null.<br>
 * estadoJuego != SIN_CONECTAR => puerto > 0.
 */
public class AdivinaQuien
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Ruta con el archivo de la información de las personas.
     */
    public static final String RUTA_PERSONAS = "./data/personas.txt";

    /**
     * Mensaje de registro de un jugador.
     */
    public static final String REGISTRO = "REGISTRO";

    /**
     * Mensaje de respuesta de registro de un jugador.
     */
    public static final String REGISTRO_OK = "REGISTRO_OK";

    /**
     * Mensaje de login de un jugador.
     */
    public static final String LOGIN = "LOGIN";

    /**
     * Mensaje de respuesta de login de un jugador.
     */
    public static final String LOGIN_OK = "LOGIN_OK";

    /**
     * Mensaje con el registro del jugador.
     */
    public static final String INFO_JUGADOR = "INFO";

    /**
     * Mensaje con la info de la persona y turno asignado.
     */
    public static final String INICIO = "INICIO";

    /**
     * Constante para indicar el estado sin conectar.
     */
    private static final int SIN_CONECTAR = 0;

    /**
     * Constante para indicar que el jugador se encuentra en el turno actual.
     */
    public static final int TURNO_ACTUAL = 1;

    /**
     * Constante para indicar que el jugador se encuentra esperando el turno del rival.
     */
    public static final int TURNO_RIVAL = 2;

    /**
     * Mensaje con una jugada.
     */
    public static final String JUGADA = "JUGADA";

    /**
     * Mensaje con la respuesta de una jugada.
     */
    public static final String RESPUESTA_JUGADA = "RESPUESTA_JUGADA";

    /**
     * Mensaje con intento de adivinar.
     */
    public static final String ADIVINAR = "ADIVINAR";

    /**
     * Mensaje con el resultado.
     */
    public static final String RESULTADO = "RESULTADO";

    /**
     * Mensaje de error.
     */
    public static final String ERROR = "ERROR";

    /**
     * Mensaje con fin del juego.
     */
    public static final String FIN = "FIN";

    /**
     * Mensaje que indica que es turno del jugador.
     */
    public static final String TURNO = "TURNO";

    /**
     * Constante que representa el separador de un comando.
     */
    public static final String SEPARADOR_COMANDO = ";;;";

    /**
     * Constante que representa el separador de los parámetros.
     */
    public static final String SEPARADOR_PARAMETROS = ":::";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Jugador de la batalla.
     */
    private RegistroJugador jugador;

    /**
     * Oponente del jugador en la batalla.
     */
    private RegistroJugador oponente;

    /**
     * Dirección de servidor al que se conectó.
     */
    private String servidor;

    /**
     * Puerto usado para conectarse.
     */
    private int puerto;

    /**
     * Canal usado para comunicarse con el servidor.
     */
    private Socket canal;

    /**
     * Flujo que envía los datos al servidor a través del socketServidor.
     */
    private PrintWriter outWriter;

    /**
     * Flujo de donde se leen los datos que llegan del servidor a través del socketServidor.
     */
    private BufferedReader inReader;

    /**
     * Matriz con las personas del tablero de juego.
     */
    private Persona[][] tablero;

    /**
     * Indica el estado actual del juego.
     */
    private int estadoJuego;

    /**
     * Persona asignada por el servidor.
     */
    private Persona personaAsignada;

    /**
     * Indica si el juego terminó o no.
     */
    private boolean juegoTerminado;

    /**
     * Lista con las preguntas
     */
    private ArrayList<String> listaPreguntas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un nuevo cliente.<br>
     * <b> post: </b> Se cargaron las persona de un archivo de texto. <br>
     * Se inicializó la lista de preguntas como una lista vacía.
     * @throws AdivinaQuienClienteException Si hay problemas cargando el archivo de personas.
     */
    public AdivinaQuien( ) throws AdivinaQuienClienteException
    {
        listaPreguntas = new ArrayList<String>( );
        cargarArchivo( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna el jugador de la batalla.
     * @return Jugador de la batalla.
     */
    public RegistroJugador darJugador( )
    {
        return jugador;
    }

    /**
     * Retorna el oponente.
     * @return Jugador oponente.
     */
    public RegistroJugador darOponente( )
    {
        return oponente;
    }

    /**
     * Retorna la persona asignada.
     * @return Persona asignada.
     */
    public Persona darPersonAsignada( )
    {
        return personaAsignada;
    }

    /**
     * Retorna el tablero del jugador.
     * @return Matriz de personas.
     */
    public Persona[][] darTablero( )
    {
        return tablero;
    }

    /**
     * Retorna el estado del juego.
     * @return Estado del juego.
     */
    public int darEstadoJuego( )
    {
        return estadoJuego;
    }

    /**
     * Retorna la lista de preguntas.
     * @return Lista de preguntas con su respectiva respuesta binaria.
     */
    public ArrayList<String> darListaPreguntas( )
    {
        return listaPreguntas;
    }

    /**
     * Retorna la lista de personajes del tablero.
     * @return Lista de personajes.
     */
    public String[] darListaPersonas( )
    {
        String[] lista = new String[tablero.length * tablero[ 0 ].length];
        int indice = 0;
        for( int i = 0; i < tablero.length; i++ )
        {
            for( int j = 0; j < tablero[ 0 ].length; j++ )
            {
                lista[ indice ] = tablero[ i ][ j ].darNombre( );
                indice++;
            }
        }
        return lista;
    }

    /**
     * Establece una conexión con el servidor del juego y envía los datos del jugador para poder empezar un juego.<br>
     * Este método termina cuando se consigue un oponente y se establece la conexión entre los dos jugadores.
     * @param pDireccionServidor Dirección usada para encontrar el servidor. pDireccionServidor != null && pDireccionServidor != "".
     * @param pPuertoServidor Puerto usado para realizar la conexión. pPuertoServidor > 0.
     * @param pLogin Login del jugador. pLogin != null && pLogin != "".
     * @param pContrasenia Contraseña del jugador. pContrasenia != null && pContrasenia != ""..
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public void iniciarSesion( String pDireccionServidor, int pPuertoServidor, String pLogin, String pContrasenia ) throws AdivinaQuienClienteException
    {
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;

        try
        {
            canal = new Socket( servidor, puerto );
            outWriter = new PrintWriter( canal.getOutputStream( ), true );
            inReader = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );

            String mensaje = LOGIN + SEPARADOR_COMANDO + pLogin + SEPARADOR_PARAMETROS + pContrasenia;
            outWriter.println( mensaje );
            String respuesta = inReader.readLine( );
            String[] partes = respuesta.split( SEPARADOR_COMANDO );
            if( partes[ 0 ].equals( ERROR ) )
            {
                throw new AdivinaQuienClienteException( partes[ 1 ] );
            }
            else if( partes[ 0 ].equals( LOGIN_OK ) )
            {
                String[] datosJugador = partes[ 1 ].split( SEPARADOR_PARAMETROS );
                jugador = new RegistroJugador( datosJugador[ 0 ], Integer.parseInt( datosJugador[ 1 ] ), Integer.parseInt( datosJugador[ 2 ] ) );

            }
        }
        catch( UnknownHostException e )
        {
            e.printStackTrace( );
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
        catch( IOException e )
        {
            e.printStackTrace( );
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
        verificarInvariante( );
    }

    /**
     * Establece una conexión con el servidor del juego y envía los datos del jugador para poder empezar un juego.<br>
     * Este método termina cuando se consigue un oponente y se establece la conexión entre los dos jugadores.
     * @param pNombre Nombre del jugador. pNombre != null.
     * @param pDireccionServidor Dirección usada para encontrar el servidor. pDireccionServidor != null.
     * @param pPuertoServidor Puerto usado para realizar la conexión. pPuertoServidor > 0.
     * @param pLogin Login del jugador. pLogin != null && pLogin != "".
     * @param pContrasenia Contraseña del jugador. pContrasenia != null && pContrasenia != "".
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public void registrar( String pDireccionServidor, int pPuertoServidor, String pLogin, String pNombre, String pContrasenia ) throws AdivinaQuienClienteException
    {
        servidor = pDireccionServidor;
        puerto = pPuertoServidor;
        try
        {
            canal = new Socket( servidor, puerto );
            outWriter = new PrintWriter( canal.getOutputStream( ), true );
            inReader = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );
            String mensaje = REGISTRO + SEPARADOR_COMANDO + pLogin + SEPARADOR_PARAMETROS + pNombre + SEPARADOR_PARAMETROS + pContrasenia;
            outWriter.println( mensaje );
            String respuesta = inReader.readLine( );
            
            
            String[] partes = respuesta.split( SEPARADOR_COMANDO );
            if( partes[ 0 ].equals( ERROR ) )
            {
                throw new AdivinaQuienClienteException( partes[ 1 ] );
            }
            else if( partes[ 0 ].equals( REGISTRO_OK ) )
            {
                String[] datosJugador = partes[ 1 ].split( SEPARADOR_PARAMETROS );
                jugador = new RegistroJugador( datosJugador[ 0 ], Integer.parseInt( datosJugador[ 1 ] ), Integer.parseInt( datosJugador[ 2 ] ) );

            }

        }
        catch( UnknownHostException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
        verificarInvariante( );
    }

    /**
     * Una vez que el usuario se ha registrado o iniciado sesión, lee la información que llega del servidor, con la infrmación del rival.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public void actualizarRival( ) throws AdivinaQuienClienteException
    {
        try
        {
            String linea = inReader.readLine( );
            System.out.println("quien es el re puto rival " + linea);
            String[] partes = linea.split( SEPARADOR_COMANDO );
            if( partes[ 0 ].equals( ERROR ) )
            {
                throw new AdivinaQuienClienteException( partes[ 1 ] );
            }
            else if( partes[ 0 ].equals( INFO_JUGADOR ) )
            {
                String[] datosJugador = partes[ 1 ].split( SEPARADOR_PARAMETROS );
                oponente = new RegistroJugador( datosJugador[ 0 ], Integer.parseInt( datosJugador[ 1 ] ), Integer.parseInt( datosJugador[ 2 ] ) );

            }
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
    }

    /**
     * Inicia la partida del jugador con la info que llega de la persona asignada y el turno.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public void iniciarPartida( ) throws AdivinaQuienClienteException
    {
        try
        {
            String linea = inReader.readLine( );
            System.out.println("quien es la re puta persona asignada " + linea);
            String[] partes = linea.split( SEPARADOR_COMANDO );
            if( partes[ 0 ].equals( ERROR ) )
            {
                throw new AdivinaQuienClienteException( partes[ 1 ] );
            }
            else if( partes[ 0 ].equals( INICIO ) )
            {
                String[] datosInicio = partes[ 1 ].split( SEPARADOR_PARAMETROS );
                int turno = Integer.parseInt( datosInicio[ 0 ] );
                String nombrePers = datosInicio[ 1 ];
                String rutaImgPersona = datosInicio[ 2 ];
                if( turno == 1 )
                {
                    estadoJuego = TURNO_ACTUAL;
                }
                else
                {
                    estadoJuego = TURNO_RIVAL;
                }
                personaAsignada = new Persona( nombrePers, rutaImgPersona );
            }
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
    }

    /**
     * Espera la jugada del rival.
     * @return Mensaje del servidor.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public String esperarJugada( ) throws AdivinaQuienClienteException
    {
        try
        {
            String mensajeServidor = inReader.readLine( );
           String partes[] = mensajeServidor.split( SEPARADOR_COMANDO );
            if( partes[ 0 ].equals( FIN ) )
            {
                juegoTerminado = true;

            }
            else if( mensajeServidor.startsWith( TURNO ) )
            {

                estadoJuego = TURNO_ACTUAL;
            }

            return mensajeServidor;
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }

    }

    /**
     * Realiza la jugada del jugador. <br>
     * 1. Envía la pregunta.<br>
     * 2. Agrega la pregunta a la lista.<br>
     * 3. Lee la respuesta.
     * @param pPregunta Pregunta realizada por el jugador.
     * @return Respuesta del rival.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public String realizarJugada( String pPregunta ) throws AdivinaQuienClienteException
    {
        String linea = JUGADA + SEPARADOR_COMANDO + pPregunta;
        outWriter.println( linea );
       try
        {
            String respuesta = inReader.readLine( );
            String[] partesRta = respuesta.split( SEPARADOR_COMANDO );
            if( partesRta[ 0 ].equals( RESPUESTA_JUGADA ) )
            {
                String nueva = "Preguntar: " + pPregunta + ": " + partesRta[ 1 ];
                listaPreguntas.add( nueva );
                return partesRta[ 1 ];

            }
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
        return null;
    }

    /**
     * Envia una respuesta al servidor.
     * @param pRespuesta Respuesta del servidor. SI ó NO.
     */
    public void enviarRespuesta( String pRespuesta )
    {
        String mensaje = RESPUESTA_JUGADA + SEPARADOR_COMANDO + pRespuesta;
        outWriter.println( mensaje );
    }

    /**
     * Envia un intento de adivinar con el nombre dado por parámetro.
     * @param pNombre Nombre de la persona para adivinar.
     * @return Retorna el mensaje del servidor.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public String intentarAdivinar( String pNombre ) throws AdivinaQuienClienteException
    {
        String mensaje = ADIVINAR + SEPARADOR_COMANDO + pNombre;
        outWriter.println( mensaje );
        try
        {
            String respuesta = inReader.readLine( );
            System.out.println( "inReader " + respuesta );
            String rspt = respuesta.split( SEPARADOR_COMANDO )[ 1 ];
            String nueva = "Adivinar: " + pNombre + ": " + rspt;
            listaPreguntas.add( nueva );
            estadoJuego = TURNO_RIVAL;
            return rspt;
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No fue posible establecer una conexión con el servidor. " + e.getMessage( ) );
        }
    }

    /**
     * Enviar el resultado al servidor de cuantas personas faltan para descartar.<br>
     * Si solo falta 1, envía un segundo mensaje intentando adivinar.
     * @throws AdivinaQuienClienteException Si hay problemas estableciendo la comunicación.
     */
    public void enviarResultado( ) throws AdivinaQuienClienteException
    {
        int contador = 0;
        String nombrePer = "";
        for( int i = 0; i < tablero.length; i++ )
        {
            for( int j = 0; j < tablero[ 0 ].length; j++ )
            {
                Persona actual = tablero[ i ][ j ];
                if( actual.estaDescartada( ) == false )
                {
                    nombrePer = actual.darNombre( );
                    contador++;
                }
            }
        }

        String mensaje = RESULTADO + SEPARADOR_COMANDO + contador;
        outWriter.println( mensaje );
       if( contador == 1 )
        {
            String mensaje2 = ADIVINAR + SEPARADOR_COMANDO + nombrePer;
            outWriter.println( mensaje2 );
        }

        estadoJuego = TURNO_RIVAL;
    }

    /**
     * Pone en falso el estado descartada de todas las personas del tablero.
     */
    public void reiniciarTablero( )
    {
        for( int i = 0; i < tablero.length; i++ )
        {
            for( int j = 0; j < tablero[ 0 ].length; j++ )
            {
                tablero[ i ][ j ].cambiarDescartada( false );
            }
        }
    }

    /**
     * Descarta la persona en las coordenadas dadas.
     * @param pFila Fila donde se encuentra la persona a descartar. pFila está dentro del tablero.
     * @param pColumna Columna donde se encuentra la persona a descargar. pColumna está dentro del tablero.
     */
    public void descartar( int pFila, int pColumna )
    {
        tablero[ pFila ][ pColumna ].cambiarDescartada( true );
    }

    /**
     * Carga el archivo con la información de las personas.<br>
     * <b>post: </b> Se inicializó la matriz de personas.
     * @throws AdivinaQuienClienteException Si hay problemas al cargar el archivo.
     */
    private void cargarArchivo( ) throws AdivinaQuienClienteException
    {
        try
        {
            File archivo = new File( RUTA_PERSONAS );
            BufferedReader lector = new BufferedReader( new FileReader( archivo ) );

            String linea = lector.readLine( );
            int filas = Integer.parseInt( linea );
            linea = lector.readLine( );
            int columnas = Integer.parseInt( linea );

            tablero = new Persona[filas][columnas];

            linea = lector.readLine( );
            while( linea != null )
            {

                String[] informacion = linea.split( ";" );
                agregarAleatoreamente( new Persona( informacion[ 0 ], informacion[ 1 ] ) );
                linea = lector.readLine( );

            }
            lector.close( );
        }
        catch( NumberFormatException nfe )
        {
            throw new AdivinaQuienClienteException( "No se pudo cargar el archivo con las personas. El archivo tiene el formato incorrecto." );
        }
        catch( IOException e )
        {
            throw new AdivinaQuienClienteException( "No se pudo cargar el archivo con las personas. No se encontró el archivo." );

        }
    }

    /**
     * Agrega los personajes aleatoreamente en el tablero.
     * @param pPersona Personaje a agregar. pPersona != null.
     */
    public void agregarAleatoreamente( Persona pPersona )
    {
        boolean asignado = false;
        while( !asignado )
        {
            int fila = ( int ) ( Math.random( ) * ( tablero.length - 0 ) );
            int columna = ( int ) ( Math.random( ) * ( tablero[ 0 ].length - 0 ) );
            if( tablero[ fila ][ columna ] == null )
            {
                tablero[ fila ][ columna ] = pPersona;
                asignado = true;
            }
        }
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase.<br>
     * <b>inv</b><br>
     * estadoJuego pertenece a {SIN_CONECTAR, TURNO_RIVAL, TURNO_ACTUAL}.<br>
     * estadoJuego = SIN_CONECTAR => juegoTerminado = true.<br>
     * estadoJuego != SIN_CONECTAR => canal != null.<br>
     * estadoJuego != SIN_CONECTAR => outWriter != null.<br>
     * estadoJuego != SIN_CONECTAR => inReader != null.<br>
     * estadoJuego != SIN_CONECTAR => servidor != null.<br>
     * estadoJuego != SIN_CONECTAR => mensajesSinLeer != null.<br>
     * estadoJuego != SIN_CONECTAR => puerto > 0.
     */
    private void verificarInvariante( )
    {
        assert ( estadoJuego == SIN_CONECTAR || estadoJuego == TURNO_ACTUAL || estadoJuego == TURNO_RIVAL ) : "El estado no es válido";
        if( estadoJuego == SIN_CONECTAR )
        {
            assert juegoTerminado : "Valor inválido de atributo juegoTerminado";
        }
        else
        {
            assert ( canal != null ) : "Si el estado no es SIN_CONECTAR, entonces  hay conexión";
            assert ( outWriter != null ) : "Si el estado no es SIN_CONECTAR, entonces  hay conexión";
            assert ( inReader != null ) : "Si el estado no es SIN_CONECTAR, entonces  hay conexión";
            assert ( servidor != null ) : "La dirección del servidor no puede ser null";
            assert ( jugador != null ) : "La dirección del servidor no puede ser null";
            assert ( oponente != null ) : "La dirección del servidor no puede ser null";
            assert ( puerto > 0 ) : "El puerto debe ser mayor a 0";
        }
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1.
     * @return Respuesta 1.
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Método para la extensión 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

}
