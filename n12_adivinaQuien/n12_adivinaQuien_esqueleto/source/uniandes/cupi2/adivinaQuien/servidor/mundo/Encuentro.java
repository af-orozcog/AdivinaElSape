package uniandes.cupi2.adivinaQuien.servidor.mundo;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

import uniandes.cupi2.adivinaQuien.cliente.*;
import uniandes.cupi2.adivinaQuien.cliente.mundo.AdivinaQuienClienteException;
import uniandes.cupi2.adivinaQuien.servidor.mundo.RegistroJugador;



public class Encuentro extends Thread
{
	//---------------------------------
	// CONSTANTES
	//---------------------------------
	
    /**
     * Ruta con el archivo de la informaci�n de las personas.
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
     * Constante que representa el separador de los par�metros.
     */
    public static final String SEPARADOR_PARAMETROS = ":::";
    
    
    
    /**
     * nombres de los jugadores
     */
    public static final String nombreJugadores[] = {"Alejandro","Anita","Pedro","Eric","Carlos","Samuel","Juan","Maria"
    		,"Felipe","Ricardo","Susana","Max","Alfredo","Roberto","Francisco","Clara"
    		,"Pablo","Fernando","Fernando","David","Bernardo","Jorge","Tom","Guillermo"
    		,"Adriana"};
    
    
    /**
     * nombres de los archivos de imagen de los jugadores
     */
    public static final String imagenesJugadores[] = {"alejandro.png","anita.png","pedro.png","eric.png","carlos.png","samuel.png","juan.png","maria.png"
    		,"felipe.png","ricardo.png","susana.png","max.png","alfredo.png","roberto.png","francisco.png","clara.png"
    		,"pablo.png","fernando.png","fernando.png","david.png","bernardo.png","jorge.png","tom.png","guillermo.png"
    		,"adriana.png"};
    
    
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * El canal usado para comunicarse con el jugador 1
     */
    private Socket socketJugador1;

    /**
     * El flujo de escritura conectado con el jugador 1
     */
    private PrintWriter out1;

    /**
     * El flujo de lectura conectado con el jugador 1
     */
    private BufferedReader in1;

    /**
     * El canal usado para comunicarse con el jugador 2
     */
    private Socket socketJugador2;

    /**
     * El flujo de escritura conectado con el jugador 2
     */
    private PrintWriter out2;

    /**
     * El flujo de lectura conectado con el jugador 2
     */
    private BufferedReader in2;

    /**
     * El objeto con la informaci�n sobre el jugador 1: visibilidad protegida, para facilitar las pruebas
     */
    protected JugadorRemoto jugador1;

    /**
     * El objeto con la informaci�n sobre el jugador 2: visibilidad protegida, para facilitar las pruebas
     */
    protected JugadorRemoto jugador2;

    /**
     * Indica que el encuentro debe terminar
     */
    private boolean finJuego;

    /**
     * Es el administrador que permite registrar el resultado del encuentro sobre la base de datos y consultar la informaci�n de los jugadores
     */
    private AdministradorResultados adminResultados;
    
    /**
     *  Es el administrador que permite registrar y validar la informacion de los usuarios
     */
    private AdministradorUsuarios adminUsuarios;
    
    private int Jugador;
    
    
    //----------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------
    
    /**
     * Establece la comunicacion entre los dos jugadores
     * @param canal1 El socket para conectarse con el jugador 1
     * @param canal2 El socket para conectarse con el jugador 2
     * @param pAdminRes Es el objeto que maneja la base de datos resultados
     * @param pAdminUsers Es el objeto que maneja la base de datos usuarios
     * @throws IOException Se lanza si hay problemas estableciendo la comunicacion
     */
    public Encuentro( Socket canal1, Socket canal2, AdministradorResultados pAdminRes, AdministradorUsuarios pAdminUsers) throws IOException
    {
    	adminResultados = pAdminRes;
    	adminUsuarios = pAdminUsers;
    	
    	out1 = new PrintWriter( canal1.getOutputStream( ), true );
    	in1 = new BufferedReader ( new InputStreamReader( canal1.getInputStream( ) ) );
    	socketJugador1 = canal1;
    	
    	out2 = new PrintWriter( canal2.getOutputStream(),true );
    	in2 = new BufferedReader( new InputStreamReader( canal2.getInputStream( ) ) );
    	socketJugador2 = canal2;
    	
    	finJuego = false;
    }
    
    //-----------------------------------------------------
    // METODOS
    //------------------------------------------------------
    
    /**
     * Retorna el socket del jugador 1
     * @return
     */
    public Socket darSocketJugador1()
    {
    	return socketJugador1;
    }
    
    /**
     * Retorna el socket del jugador 2
     * @return
     */
    public Socket darSocketJugador2()
    {
    	return socketJugador2;
    }
    
    /**
     * Retorna el administrador de la base de datos resultados
     * @return
     */
    public AdministradorResultados darAdministradorResultados()
    {
    	return adminResultados;
    }
    
    /**
     * Retorna el administrador de la base de datos usuarios
     * @return
     */
    public AdministradorUsuarios darAdministradorUsuarios()
    {
    	return adminUsuarios;
    }
    
    /**
     * Registra al usuario en la base de datos
     * @param info
     * @return
     * @throws AdivinaQuienServidorException
     */
    private RegistroJugador registrarJugador( String info) throws AdivinaQuienServidorException
    {
    	if (info.startsWith(REGISTRO))
    	{
    		String login = info.split(SEPARADOR_COMANDO)[1].split(SEPARADOR_PARAMETROS)[0];
    		String nombre = info.split(SEPARADOR_COMANDO)[1].split(SEPARADOR_PARAMETROS)[1];
    		String contrasenia = info.split(SEPARADOR_COMANDO)[1].split(SEPARADOR_PARAMETROS)[2];
    		try
    		{
    			RegistroJugador reg1 = adminUsuarios.registrarJugador(login,nombre,contrasenia);
    			return reg1;
    		}
    		catch (SQLException e)
    		{
    			e.printStackTrace();
    			throw new AdivinaQuienServidorException("Hubo un problema registrando la informacion del jugador " + e.getMessage() );
    		}
    	}
    	else
    		throw new AdivinaQuienServidorException( " El mensaje no tiene el formato esperado");
    }
    
    /**
     * Inicia la sesion del jugador
     * @param info
     * @return
     * @throws AdivinaQuienServidorException
     */
    private RegistroJugador iniciarSesion( String info) throws AdivinaQuienServidorException
    {
    	if (info.startsWith(LOGIN) )
    	{
    		String login = info.split(SEPARADOR_COMANDO)[1].split(SEPARADOR_PARAMETROS)[0];
    		String contrasenia = info.split(SEPARADOR_COMANDO)[1].split(SEPARADOR_PARAMETROS)[1];
    		try
    		{
    			RegistroJugador reg1 = adminUsuarios.consultarRegistroJugador(login, contrasenia);
    			return reg1;
    		}
    		catch (SQLException e)
    		{
    			e.printStackTrace();
    			throw new AdivinaQuienServidorException(" Hubo un problema loggeando al jugador" +e.getMessage() );
    		}
    	}
    	else
    		throw new AdivinaQuienServidorException( "El mensaje no tiene el formato esperado" );
    }
    
    
    /**
     * Inicia el encuentro y realiza todas las acciones necesarias mientras que este dure.<br>
     * El ciclo de vida de un encuentro tiene tres partes:<br>
     * 1. Iniciar el encuentro <br>
     * 2. Realizar el encuentro (permitir la comunicaci�n entre los clientes)<br>
     * 3. Terminar el encuentro y enviar la informaci�n sobre el ganador
     */
    public void run( )
    {
        try
        {
            iniciarEncuentro( );

            // Iniciar el juego

            while( !finJuego )
            {
       
                procesarJugada( Jugador );

                if( finJuego )
                {
                    terminarEncuentro( );
                
                }
                else
                {
                    Jugador = ( Jugador == 1 ) ? 2 : 1;
                }
            }
        }
        catch( Exception e )
        {
            finJuego = true;

            try
            {
                in1.close( );
                out1.close( );
                socketJugador1.close( );
            }
            catch( IOException e2 )
            {
                e2.printStackTrace( );
            }

            try
            {
                in2.close( );
                out2.close( );
                socketJugador2.close( );
            }
            catch( IOException e2 )
            {
                e2.printStackTrace( );
            }
        }
    }
    
    
    /**
     * Metodo que envia informacion de registro exitoso por parte de un usuario
     * @param out canal de comunicacion del jugador
     * @param reg informacion del jugador
     */
    private void enviarInformacionRegistro( PrintWriter out, RegistroJugador reg )
    {
    	String cadena = REGISTRO_OK + SEPARADOR_COMANDO + reg.darLoginJugador() + SEPARADOR_PARAMETROS + reg.darEncuentrosGanados( ) + SEPARADOR_PARAMETROS + reg.darEncuentrosPerdidos( );
        out.println( cadena );
    }
	
    /**
     * Metodo que envia informacion de loggeada exitosa por parte de un usuario
     * @param out canal de comunicacion del jugador
     * @param reg informacion del jugador
     */
	private void enviarInformacionLogin( PrintWriter out, RegistroJugador reg )
    {
        String cadena = LOGIN_OK + SEPARADOR_COMANDO + reg.darLoginJugador() + SEPARADOR_PARAMETROS + reg.darEncuentrosGanados( ) + SEPARADOR_PARAMETROS + reg.darEncuentrosPerdidos( );
        out.println( cadena );
    }
	
	/**
	 * Metodo que envia la informacion del contrincante a uno de los jugadores
	 * @param out canal de comunicacion del jugador
     * @param reg informacion del jugador
	 */
	private void enviarInformacionOponente(PrintWriter out, RegistroJugador reg) {
		 String cadena = INFO_JUGADOR + SEPARADOR_COMANDO + reg.darLoginJugador() + SEPARADOR_PARAMETROS + reg.darEncuentrosGanados( ) + SEPARADOR_PARAMETROS + reg.darEncuentrosPerdidos( );
	     out.println( cadena );
	}
    
    
	/**
     * Metodo que notifica a uno de los jugadores de errores en el registro del mismo
     * @param out canal de comunicacion del jugador
     */
    private void enviarErrorRegistro( PrintWriter out)
    {
    	String cadena = ERROR + SEPARADOR_COMANDO + "MensajeError";
        out.println( cadena );
    }
	
    /**
     * Metodo que notifica a uno de los jugadores de errores en la logging del mismo
     * @param out canal de comunicacion del jugador
     */
	private void enviarErrorLogin( PrintWriter out)
    {
        String cadena = ERROR + SEPARADOR_COMANDO + "El usuario no esta registrado";
        out.println( cadena );
    }
	
	/**
	 * Envia la informarcion del jugador que comienza el juego
	 * @param out canal de comunicacion del jugador
	 * @param reg informacion del jugador que comienza el juego
	 */
	private void enviarInicioJuego( PrintWriter out, int turno, int persona) {
		String cadena = INICIO + SEPARADOR_COMANDO + turno + SEPARADOR_PARAMETROS + nombreJugadores[persona] + SEPARADOR_PARAMETROS + imagenesJugadores[persona];
        out.println( cadena );
	}
	
	private void preguntaDeJugador(PrintWriter out, String pregunta) {
		
	}
	
	
    /**
     * m�todo que realiza las actividades necesarias para iniciar un encuentro. <br>
     * 1. lee el registro de cada jugador y inicializa a los jugadores.
     * 2. envia la informaci�n del oponente a cada jugador.
     * 3. envia las cartas correspondientes a cada jugador.
     * 4. anuncia a cada jugador quien va a tener el primer turno.
     * @throws IOException si se presentan problemas en la comunicaci�n se envia dicho error.
     * @throws ContinentalException se envia esta excepci�n si existen problemas de comunicaci�n con la base de datos.
     */
    protected void iniciarEncuentro( ) throws IOException, AdivinaQuienServidorException
    {
        // Leer la informaci�n sobre los jugadores
        String info1 = in1.readLine( );
        RegistroJugador reg1;
        if(info1.startsWith(REGISTRO)) { 
        	try {
        		reg1 = registrarJugador( info1 );
        		
        		//se envia la informacion de registro exitosa al jugador
        		enviarInformacionRegistro(out1, reg1);
        	
        	}catch(Exception e){
        		
        		//se envia la informacion de error de registro
        		enviarErrorRegistro(out1);
        		throw new AdivinaQuienServidorException("F");
        	}
        	
        }
        else {
        	try {
        		reg1 = iniciarSesion( info1 );
        		
        		//Se envia la informacion de logging exitosa
        		enviarInformacionLogin(out1, reg1);
        		
        	}catch(Exception e){
        		
        		//se envia la informacion de error de login
        		enviarErrorLogin(out1);
        		throw new AdivinaQuienServidorException("F");
        	}
        }
        jugador1 = new JugadorRemoto( reg1 );
        RegistroJugador reg2;
        String info2 = in2.readLine( );
        if(info2.startsWith(REGISTRO)) { 
        	try {
        		reg2 = registrarJugador( info2 );
        		
        		//se envia la informacion de registro exitosa al jugador
        		enviarInformacionRegistro(out2, reg2);
        	
        	}catch(Exception e){
        		
        		//se envia la informacion de error de registro
        		enviarErrorRegistro(out2);
        		throw new AdivinaQuienServidorException("F");
        	}
        	
        }
        else {
        	try {
        		reg2 = iniciarSesion( info2 );
        		
        		//Se envia la informacion de logging exitosa
        		enviarInformacionLogin(out2, reg2);
        		
        	}catch(Exception e){
        		
        		//se envia la informacion de error de login
        		enviarErrorLogin(out2);
        		throw new AdivinaQuienServidorException("F");
        	}
        }
        System.out.println("jugador 1 nombre es: " + reg1.darNombreJugador() + " jugador 2 nombre es: " + reg2.darNombreJugador());
        
        jugador2 = new JugadorRemoto( reg2 );
       
        // Enviar a cada jugador la informacion de su oponente
        enviarInformacionOponente(out1, reg2);
        enviarInformacionOponente(out2, reg1);
        
        //elige de manera aleatoria el primer jugador que va a comenzar
        int inicioTurno = (int) Math.floor(Math.random()*1+1);
        
        int imagen1 = (int) Math.floor(Math.random()*nombreJugadores.length);
        int imagen2 = (int) Math.floor(Math.random()*nombreJugadores.length);
        if(imagen2 == imagen1) {
        	++imagen2;
        	imagen2 %= nombreJugadores.length;
        }
        
        if(inicioTurno == 0) {
        	Jugador = 1;
        	enviarInicioJuego(out1, 1, imagen1);
        	enviarInicioJuego(out2, 2, imagen2);
        }
        else {
        	Jugador = 2;
        	enviarInicioJuego(out1, 2,imagen1);
        	enviarInicioJuego(out2, 1, imagen2);
        }  
    }
    
    /**
     * Realiza las actividades necesarias para terminar un encuentro: <br>
     * 1. Actualiza los registros de los jugadores en la base de datos <br>
     * 2. Env�a un mensaje a los jugadores advirtiendo sobre el fin del juego y el nombre del ganador <br>
     * 3. Cierra las conexiones a los jugadores
     * @throws ContinentalException se lanza dicha excepci�n cuando hay problemas para acceder a la base datos
     * @throws IOException se lanza dicha excepci�n cuando hay problemas en la comunicaci�n con los jugadores.
     */
    private void terminarEncuentro( ) throws AdivinaQuienServidorException, IOException
    {
        // Actualizar el registro de los jugadores
        RegistroJugador ganador = null;
        RegistroJugador perdedor = null;
        if( jugador1.darVictoria())
        {
            ganador = jugador1.darRegistroJugador( );
            perdedor = jugador2.darRegistroJugador( );
        }
        else
        {
            ganador = jugador2.darRegistroJugador( );
            perdedor = jugador1.darRegistroJugador( );
        }
        try
        {
            adminResultados.registrarVictoria( ganador.darNombreJugador( ) );
            adminResultados.registrarDerrota( perdedor.darNombreJugador( ) );
        }
        catch( SQLException e )
        {
            throw new AdivinaQuienServidorException( "Error actualizando la informaci�n en la base de datos: " + e.getMessage( ) );
        }
        // Enviar un mensaje indicando el fin del juego y el ganador
        //String cadenaGanador = GANADOR + ":" + ganador.darNombreJugador( );
        /*out1.println( cadenaGanador );
        out2.println( cadenaGanador );

        // Cerrar los canales de los jugadores
        in1.close( );
        out1.close( );
        out2.close( );
        in2.close( );
        socketJugador1.close( );
        socketJugador2.close( );
    	*/
    }
    
    
    /**
     * m�todo encargado de procesar las jugadas de los jugadores <br>
     * 1. manda la carta solicitada por el jugador.
     * 2. actualiza la baraja visible para ambos jugadores.
     * actualiza el atributo fin juego cuando se determine que el juego se tiene que acabar.
     * @param jugador identificaci�n de cual jugador esta jugando. 
     * @throws IOException se lanza cuando existen problemas para enviar y recibir mensajes.
     * @throws ContinentalException se lanza cuando hay inconsistencia en la informaci�n recibida por los jugadores.
     */
    private void procesarJugada( int jugador ) throws IOException, AdivinaQuienServidorException
    {
        PrintWriter jugador1Out = ( jugador == 1 ) ? out1 : out2;
        PrintWriter jugador2Out = ( jugador == 1 ) ? out2 : out1;

        BufferedReader jugador1In = ( jugador == 1 ) ? in1 : in2;
        BufferedReader jugador2In = ( jugador == 1 ) ? in2 : in1;

        // Leer la jugada del "atacante" que indica donde se va a hacer el ataque
        String lineaJugada = jugador1In.readLine( );
        
        
        if(lineaJugada != null)
        {
        	// Reenviar el "ataque" al jugador atacado
        	
        	if(lineaJugada.startsWith("")) {
        		if(lineaJugada.startsWith(BARAJA_INICIAL))
        			try {
        				enviarCartas(jugador1Out, null);
        			}catch(Exception e ) {
        				System.out.println(e.getMessage());
        			}
        		else
        			jugador1Out.println(cartaAbierta);
        			
        		// Leer la informaci�n sobre el resultado del ataque que env�a el jugador atacado
        		String lineaResultado = jugador1In.readLine( );
        		if( !lineaResultado.startsWith("CARTA"))
        			throw new ContinentalException( "Se esperaba el resultado de una JUGADA pero se recibi� " + lineaResultado );
        		cartaAbierta = lineaResultado;
        		jugador2Out.println(cartaAbierta);
        	}
        	// Rvisar el resultado para saber si el encuentro termina y actualizar los puntajes
        	else if( lineaJugada.startsWith( VICTORIA))
        	{
        		ArrayList<Carta> cartasJugador = new ArrayList<Carta>();
        		for(int i = 0; i < 7; i++) {
        			String valores[] = jugador1In.readLine().split(":");
        			if(!valores[0].equals("CARTA"))
        				throw new ContinentalException("se esperaba la lectura de una carta pero se recibio: " + valores[0]);
        			Carta meter = new Carta(valores[1], valores[2],valores[3]);
        			cartasJugador.add(meter);
        		}
        		organizar(cartasJugador);
        		boolean vic = determinarVictoria(cartasJugador);
        		String verdad = vic ? "OK": "FALSA";
        		JugadorRemoto actual = ( jugador == 1 ) ? jugador1 : jugador2;
        		if(vic) actual.cambiarVictoria();
        		jugador1Out.println(VICTORIA + ":" +verdad);
        		jugador2Out.println(VICTORIA + ":" +verdad);
        		finJuego = true;	
        	}
        }
        
    }
    
    
    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica el invariante de la clase
     * <b>inv:</b><br>
     * !finJuego => socketJugador1 != null <br>
     * !finJuego => out1 != null <br>
     * !finJuego => in1 != null <br>
     * !finJuego => socketJugador2 != null <br>
     * !finJuego => out2 != null <br>
     * !finJuego => in2 != null <br>
     * jugador1 != null <br>
     * jugador2 != null <br>
     */
    private void verificarInvariante( )
    {
        if( !finJuego )
        {
            assert socketJugador1 != null : "Canal inv�lido";
            assert out1 != null : "Flujo inv�lido";
            assert in1 != null : "Flujo inv�lido";
            assert socketJugador2 != null : "Canal inv�lido";
            assert out2 != null : "Flujo inv�lido";
            assert in2 != null : "Flujo inv�lido";
        }
        
        assert jugador1 != null : "Jugador nulo";
        assert jugador2 != null : "Jugador nulo";
    }
    
    
}
