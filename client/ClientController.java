package ClientControllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import package.ClientModels;

/**
 * When run, connects the client to the server, and allows the client
 * to send and recieve lists of items, suppliers, and orders.
 * Sent lists can be created from text files.
 *
 * @author Jake Liu
 * @version 1.0
 * @since March 29, 2019
 */
public class ClientController implements SCCommuncationConstants
{
	/**
	 * Client socket object.
	 */
	private Socket clientSocket;
	
	/**
	 * Object Input stream from the socket.
	 */
	private ObjectInputStream inputReader;
	
	/**
	 * Object Output stream to the socket.
	 */
	private ObjectOutputStream outputWriter;
	
	/**
	 * File reading object to enable reading text files.
	 */
	private FileReader fileInput;
	
	/**
	 * List of items held by the store.
	 */
	private ArrayList<ItemModel> ItemInfoList;
	
	/**
	 * List of suppliers held by the store.
	 */
	private ArrayList<SupplierModel> supplierInfoList;
	
	/**
	 * List of orders held by the store.
	 */
	private ArrayList<OrderModel> orderInfoList;
	
	/**
	 * Verifies if the client-server connection can still run
	 * close all streams and terminate.
	 */
	 /*
	public void connection()
	{
		try
		{		
			while(true)
			{
				//Get a message from the server
				String serverMessage = (String)inputReader.readObject();
				
				//Session is over, so stop communicating with the server
				if(serverMessage.equals("Client Quit"))
					break;
				
				//Act on the server's message.
				interpretServerMessage(serverMessage);
			}	
		}
		catch(IOException ioErr)
		{
			System.out.println(ioErr.getMessage());
		}
		
		//close client processes before terminating.
		try
		{
			inputReader.close();
			outputWriter.close();
			cSocket.close();
		}
		catch(IOException shutDownErr)
		{
			System.out.println("Error when closing: " + shutDownErr.getMessage());
		}
	}
*/
	/**
	 * Verifies if the client-server connection can still run.
	 * If it cannot, then the client will close all streams and terminate.
	 *
	 * @return true if the connection is still valid. If false, then a new client
	 * 				will need to be created.
	 */
	/*
	public boolean checkConnection()
	{
		try
		{		
			while(true)
			{
				
				
				//Get a message from the server
				String serverMessage = (String)inputReader.readObject();
				
				//Session is over, so stop communicating with the server
				if(serverMessage.equals("Client Quit"))
					break;
				
				//Act on the server's message.
				interpretServerMessage(serverMessage);
			}	
		}
		catch(IOException ioErr)
		{
			System.out.println(ioErr.getMessage());
		}
		
		try
		{
			inputReader.close();
			outputWriter.close();
			cSocket.close();
		}
		catch(IOException shutDownErr)
		{
			System.out.println("Error when closing: " + shutDownErr.getMessage());
		}
		return true;
	}
	*/
	
	/**
	 * Informs the server that the client is done communicating with the server.
	 * closes all streams and sockets.
	 */
	public void terminate()
	{
		outputWriter.writeObject(scQuit);
		try
		{
			inputReader.close();
			outputWriter.close();
			cSocket.close();
		}
		catch(IOException shutDownErr)
		{
			System.out.println("Error when closing: " + shutDownErr.getMessage());
		}
	}
	
	/**
	 * Asks the server for the items held in the database.
	 * throws null if there is an error in the server.
	 *
	 * @return An ArrayList of the items.
	 */
	public ArrayList<ItemModel> retrieveItemListFromServer() 
	{
		outputWriter.writeObject(scQuery + scItem);
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ArrayList<ItemModel>)inputReader.readObject();
	}
	
	/**
	 * Asks the server for the suppliers held in the database.
	 * throws null if there is an error in the server.
	 *
	 * @return An ArrayList of the suppliers.
	 */
	public ArrayList<SupplierModel> retrieveSupplierListFromServer() 
	{
		outputWriter.writeObject(scQuery + scSupplier);
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ArrayList<SupplierModel>)inputReader.readObject();
	}
	
	/**
	 * Asks the server for the suppliers held in the database.
	 * throws null if there is an error in the server.
	 *
	 * @return An ArrayList of the suppliers.
	 */
	public ArrayList<SupplierModel> retrieveSupplierListFromServer() 
	{
		outputWriter.writeObject(scQuery + scSupplier);
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ArrayList<SupplierModel>)inputReader.readObject();
	}
	
	/**
	 * Asks the server for the orders held in the database.
	 * throws null if there is an error in the server.
	 *
	 * @return An ArrayList of the orders.
	 */
	public ArrayList<OrderModel> retrieveSupplierListFromServer() 
	{
		outputWriter.writeObject(scQuery + scSupplier);
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ArrayList<OrderModel>)inputReader.readObject();
	}
	
	/**
	 * Sends an Array List of Items to the server to update the 
	 * server items in the database.
	 * 
	 * @param itemList An ArrayList of ItemModel to update the database.
	 */
	public void sendItemListToServer(ArrayList<ItemModel> itemList)
	{
		outputWriter.writeObject(scData + scItem);
		
		outputWriter.writeObject(itemList);
		
		outputWriter.flush();
	}
	
	
	/**
	 * Sends an Array List of Suppliers to the server to update the 
	 * server Suppliers in the database.
	 * 
	 * @param supplierList An ArrayList of SupplierModel to update the database.
	 */
	public void sendSupplierListToServer(ArrayList<SupplierModel> supplierList)
	{
		outputWriter.writeObject(scData + scSupplier);
		
		outputWriter.writeObject(supplierList);
		
		outputWriter.flush();
	}
	
	/**
	 * Sends an Array List of Orders to the server to update the 
	 * server Orders in the database.
	 * 
	 * @param orderList An ArrayList of OrderModel to update the database.
	 */
	public void sendOrderListToServer(ArrayList<OrderModel> orderList)
	{
		outputWriter.writeObject(scData + scOrder);
		
		outputWriter.writeObject(orderList);
		
		outputWriter.flush();

	}
	
	/**
	 * Searches for an item in the server.
	 * @param itemId The ID of the item being searched for.
	 * @return The found item or null.
	 */
	public ItemModel sendItemSearch(int itemId)
	{
		outputWriter.writeObject(scSearch + scItem + scInt);
		
		outputWriter.writeObject(new Integer(itemId));
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ItemModel)inputReader.readObject();
	}
	
	
	/**
	 * Searches for an item in the server.
	 * @param itemName The name of the item being searched for.
	 * @return The found item or null.
	 */
	public ItemModel sendItemSearch(String itemName)
	{
		outputWriter.writeObject(scSearch + scItem + scString);
		
		outputWriter.writeObject(itemName);
		
		outputWriter.flush();
		
		String serverResponse = (String)inputReader.readObject();
		
		if(serverResponse.contains(scError))
			return null;
		
		return (ItemModel)inputReader.readObject();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Constructor.
	 * Creates the client's socket and the object I/O streams for that socket, 
	 * using the socket's I/O handles.
	 *
	 * @param serverName The server host name.
	 * @param portNum The port number.
	 */
	public ClientController(String serverName, int portNum)
	{
		try
		{
			clientSocket = new Socket(serverName, portNum);
			inputReader = new ObjectInputStream(clientSocket.getInputStream());
			outputWriter = new ObjectOutputStream(cSocket.getOutputStream());
			outputWriter.flush();
		}
		catch(IOException ioErr)
		{
			System.out.println(ioErr.getMessage());
		}
	}
	
	/**
	 * Main method for the client.
	 * Creates and runs a client, which tries to connect to a game server
	 * on port 8428.
	 *
	 * @param args Command line arguments when starting the program.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		ClientController client = new ClientController("localhost", 8428);
		client.communicate();
	}
}