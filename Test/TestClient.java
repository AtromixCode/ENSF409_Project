package Test;

import java.io.*;
import java.net.Socket;

public class TestClient {
    /**
     * used to send data to the server
     */
    private ObjectOutputStream socketOut;

    /**
     * used to establish communication to the server
     */
    private Socket aSocket;


    /**
     * used to read in responses from the server
     */
    private ObjectOutputStream socketIn;

    /**
     * Constructs a client and connects it to the server with the given name and port
     *
     * @param serverName the name of the server to connect to
     * @param portNumber the port to connect over
     */
    public TestClient(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            socketIn = new ObjectOutputStream(aSocket.getOutputStream());
            socketOut = new ObjectOutputStream(aSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * starts the communication between a client and a server and keeps it until the client quits
     */
    public void communicate() {
        boolean running = true;
        while (running) {
            try {
                String Quit = "QUIT;";
                socketOut.writeObject(Quit);
                System.out.println("Goodbye");
                running = false;
            }catch (IOException e){
                System.err.println("Error Exiting");
            }
        }

        try {
            socketOut.close();
            socketIn.close();
        } catch (IOException e) {
            System.out.println("error trying to close");
            e.printStackTrace();
        }

    }

        /**
         * the main function used to start the connection between a new client and a server
         * @param args
         * @throws IOException
         */
        public static void main(String[] args) throws IOException {
            TestClient aClient = new TestClient("localhost", 8428);
            aClient.communicate();
        }
    }
