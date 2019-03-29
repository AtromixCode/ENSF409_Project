package ServerPackage.ServerControllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**This class makes the connection between the back end found on the server package and to the client front
 * end found on the client package.
 *
 */
public class ServerController {
    /**
     * the socket of the server
     */
    private ServerSocket socket;

    /**
     * the socket to accept new clients
     */
    private Socket clientSocket;

    /**
     * the pool of threads where all the instances of the shop will run
     */
    private ExecutorService pool;


    public ServerController(){
        try {
            socket = new ServerSocket(8428);
            pool = Executors.newCachedThreadPool();
        }catch (IOException e) {
            System.err.println("Could not start the server");
            System.err.println(e.getMessage());
        }

        runServer();
    }

    private void runServer () {
        try {
            while (true) {

            }
        }catch (){

        }
    }


    protected  static void main (String arg []){
        ServerController myServer = new ServerController();


    }

}
