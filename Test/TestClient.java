package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    /**
     * used to send data to the server
     */
    private PrintWriter socketOut;

    /**
     * used to establish communication to the server
     */
    private Socket aSocket;

    /**
     * reads in the input from the user from the console
     */
    private BufferedReader stdIn;

    /**
     * used to read in responses from the server
     */
    private BufferedReader socketIn;

    /**
     * Constructs a client and connects it to the server with the given name and port
     *
     * @param serverName the name of the server to connect to
     * @param portNumber the port to connect over
     */
    public TestClient(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter((aSocket.getOutputStream()), true);
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
            System.out.println("Goodbye");
            running = false;
        }

        try {
            socketOut.close();
            socketIn.close();
            stdIn.close();
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
