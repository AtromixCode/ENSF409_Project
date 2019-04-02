package ServerPackage.ServerModels;

import ServerPackage.ServerControllers.InventoryController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopController implements Runnable {
    /**
     * The list of orders corresponding to the shop
     */
    private ArrayList<OrderModel> orders;

    /**
     * the socket used for communications with the client
     */
    private Socket clientSocket;

    /**
     * The list of suppliers in the shop
     */
    private ArrayList<SupplierModel> suppliers;

    /**
     * The inventory of the shop
     */
    private InventoryController inv;

    /**
     *
     * TODO: ask Shamez wats dis boi do
     * The index of the order???
     */
    private int orderIndex;

    /**
     * The input from the socket
     */
    private BufferedReader socketIn;

    /**
     * The output to the socket
     */
    private PrintWriter socketOut;

    /**
     * Constructs a store and does the necessary callings to get up to date
     * information from the database
     * @param sc the accepted socket from the server used to communicate with the client
     */
    public ShopController(Socket sc)
    {
        clientSocket = sc;
        try
        {
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter((clientSocket.getOutputStream()),true);
        }catch (IOException e){
            outputToClient(e.getMessage());
            System.err.println("Error constructing the shop");
            System.err.println(e.getMessage());
        }
    }

    /**
     * outputs to the client the given string
     * @param s the string to output to the client
     */
    private void outputToClient (String s){
        socketOut.println(s);
    }

    @Override
    public void run (){
        System.out.println("Doing shop things");
    }
}
