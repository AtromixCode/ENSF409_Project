package ServerPackage.ServerControllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class makes the connection between the back end found on the server package and to the client front
 * end found on the client package.
 *
 * Uses a thread pool of 10 worker threads, which allows up to 10 clients
 * to be using the server at any one time.
 * Each thread facilitates interaction between a client and the store.
 *
 * Up to 10 clients are allowed to connect to the server at a time.
 * Because Clients can connect and disconnect at will,
 * the server runs indefinitely until shut off with cntrl-c.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since March 29, 2019
 */
public class ServerController 
{
    /**
     * The socket of the server.
     */
    private ServerSocket socket;

    /**
     * The socket to accept new clients.
     */
    private Socket clientSocket;

    /**
     * The pool of threads where all the instances of the shop will run.
     */
    private ExecutorService pool;

	/**
	 * Server Controller constructor.
	 * Creates the server socket and the thread pool.
	 * Runs the server indefinitely.
	 */
    public ServerController(){
        try 
		{
            socket = new ServerSocket(8428, 10);
            pool = Executors.newFixedThreadPool(10);
        }
		catch (IOException e) 
		{
            System.err.println("Could not start the server");
            System.err.println(e.getMessage());
        }

        runServer();
    }

	/**
	 * Runs the server so that clients can connect and interact with the shop.
	 *
	 * Until someone forcibly terminates the server, like with a cntrl-c command,
	 * will continually accept connections to clients.
	 * I/O streams on the socket are opened to each client for communication.
	 * A thread pool of 10 worker threads works with clients. Clients terminating
	 * frees up worker threads to work with new clients.
	 */
    private void runServer () {
        try 
		{	
            while (true) 
			{
				//Accept a connection to a client.
				clientSocket = socket.accept();
				System.out.println("Connection occurred");
				ShopController shop = new ShopController(clientSocket);
				
				pool.execute(shop);
				/*
				//Accept a connection to an X player client
				xSocket = sSocket.accept();
				//Get I/O streams for the X player client socket
				inputReaderX = new BufferedReader(new InputStreamReader(xSocket.getInputStream()));
				outputWriterX = new PrintWriter(xSocket.getOutputStream(), true);
				
				//Inform the user that they have connected to the game and are waiting for the opponent.
				outputWriterX.println("~~~~~~~~~~~~~~~ WELCOME TO THE GAME OF TIC-TAC-TOE ~~~~~~~~~~~~~~~\n");
				outputWriterX.println("Connected to the server successfully."); 
				outputWriterX.println("Waiting for an opponent to connect...");

				//Accept a connection to an O player client
				oSocket = sSocket.accept();
				//Get I/O streams for the O player client socket
				inputReaderO = new BufferedReader(new InputStreamReader(oSocket.getInputStream()));
				outputWriterO = new PrintWriter(oSocket.getOutputStream(), true);
				outputWriterO.println("~~~~~~~~~~~~~~~ WELCOME TO THE GAME OF TIC-TAC-TOE ~~~~~~~~~~~~~~~\n");
				outputWriterO.println("Connected to the server successfully."); 
				outputWriterO.println("Waiting for the \'X\' player to choose their name...");
									  
				//Start a new Game using the client socket I/O streams
				Runnable ticTacToeGame = new Game(inputReaderX, outputWriterX,
												  inputReaderO, outputWriterO);
				
				System.out.println("Started a new game...");
				
				threadPool.execute(ticTacToeGame);
				*/
			}
		}
       //close all streams.
		catch(IOException ioErr)
		{
			System.out.println(ioErr.getMessage());
		}
		pool.shutdown();
    }

	/**
	 * The main method that initializes and starts the server.
	 * @param arg command line arguments (not used).
	 */
    public static void main (String arg []){
        ServerController myServer = new ServerController();
    }
}
