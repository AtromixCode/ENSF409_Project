package ServerPackage.ServerControllers;

import ServerPackage.ServerModels.ShopController;

import java.io.IOException;
import java.net.ServerSocket;
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
    private ServerSocket serverSocket;

    /**
     * the pool of threads where all the instances of the shop will run
     */
    private ExecutorService pool;


    public ServerController(){
        try {
            serverSocket = new ServerSocket(8428);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server is running");
        }catch (IOException e) {
            System.err.println("Could not start the server, port already used in another server?");
            System.err.println(e.getMessage());
        }

        runServer();
    }

    private void runServer () {
        try {
            while (true) {
                ShopController shop = new ShopController(serverSocket.accept());
                pool.execute(shop);
            }
        }catch (IOException e){
            System.err.println("There was an error accepting a new client, finishing all connections" +
                    "with remaining clients");
            pool.shutdown();
            try {
                serverSocket.close();
            }catch (IOException ex){
                System.err.println("Error closing the socket, unauthorized stuff going on?");
                System.err.println(ex.getMessage());
            }
        }
    }


    public static void main (String []args){
        ServerController myServer = new ServerController();
    }

}
