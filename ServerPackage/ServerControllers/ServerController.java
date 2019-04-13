package ServerPackage.ServerControllers;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
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
        	InetAddress yay = InetAddress.getByName("10.13.146.120");
            socket = new ServerSocket(8428, 10, yay );
            pool = Executors.newFixedThreadPool(10);
        }
		catch (IOException e) 
		{
            System.err.println("Could not start the server");
            System.err.println(e.getMessage());
        }
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
        	System.out.println("Server is running.");
            while (true) 
			{
				//Accept a connection to a client.
				clientSocket = socket.accept();
				System.out.println("Connection occurred");
				ShopController shop = new ShopController(clientSocket);
				
				pool.execute(shop);
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
        myServer.runServer();
    }
}
