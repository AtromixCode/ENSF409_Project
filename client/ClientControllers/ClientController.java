package client.ClientControllers;

import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.DefaultListModel;

import Models.*;
import server.Supplier;

/**
 * When run, connects the client to the server, and allows the client
 * to send and receive lists of items, suppliers, and orders.
 * Sent lists can be created from text files.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 4, 2019
 */
public class ClientController implements SCCommunicationConstants {
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
	private ArrayList<ItemModel> itemList;

	/**
	 * List of suppliers held by the store.
	 */
	private ArrayList<SupplierModel> supplierList;

	/**
	 * List of orders held by the store.
	 */
	private ArrayList<OrderLineModel> orderList;

	private DefaultListModel<String> supplierDisplay;

	private DefaultListModel<String> itemDisplay;

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
	public void terminate() {
		try {
			outputWriter.writeObject(scQuit);
			inputReader.close();
			outputWriter.close();
			clientSocket.close();
		} catch (IOException shutDownErr) {
			System.out.println("Error when closing: " + shutDownErr.getMessage());
		}
	}

	/**
	 * Asks the server for the items held in the database.
	 * throws an error if there is an error in the server interaction.
	 *
	 * @return An ArrayList of the items.
	 * @throws IOException
	 */
	public ArrayList<ItemModel> retrieveItemListFromServer() throws IOException {
		try {
			outputWriter.writeObject(scQuery + scItem);
			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not send opening message to the server.");
		}

		try {
			String serverResponse = (String) inputReader.readObject();

			if (serverResponse.contains(scError))
				throw new IOException("Error: server could not send the item list.");

			return (ArrayList<ItemModel>) inputReader.readObject();
		} catch (IOException readErr) {
			throw new IOException("Error: could not read the server's message.");
		} catch (ClassNotFoundException classErr) {
			throw new IOException("Error: could not properly read the object.");
		}
	}

	/**
	 * Asks the server for the suppliers held in the database.
	 * throws an error if there is an error in the server.
	 *
	 * @return An ArrayList of the suppliers.
	 * @throws IOException
	 */
	public ArrayList<SupplierModel> retrieveSupplierListFromServer() throws IOException {
		try {
			outputWriter.writeObject(scQuery + scSupplier);
			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not send opening message to the server.");
		}

		try {
			String serverResponse = (String) inputReader.readObject();

			if (serverResponse.contains(scError))
				return null;

			return (ArrayList<SupplierModel>) inputReader.readObject();
		} catch (IOException readErr) {
			throw new IOException("Error: could not read the server's message.");
		} catch (ClassNotFoundException classErr) {
			throw new IOException("Error: could not properly read the object.");
		}
	}

	/**
	 * Asks the server for the orders held in the database.
	 * throws an error if there is an error in the server.
	 *
	 * @return An ArrayList of the orders.
	 * @throws IOException
	 */
	public ArrayList<OrderLineModel> retrieveOrderListFromServer() throws IOException {
		try {
			outputWriter.writeObject(scQuery + scOrder);
			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not send opening message to the server.");
		}

		try {
			String serverResponse = (String) inputReader.readObject();

			if (serverResponse.contains(scError))
				return null;

			return (ArrayList<OrderLineModel>) inputReader.readObject();
		} catch (IOException readErr) {
			throw new IOException("Error: could not read the server's message.");
		} catch (ClassNotFoundException classErr) {
			throw new IOException("Error: could not properly read the object.");
		}
	}

	/**
	 * Sends an Array List of Items to the server to update the
	 * server items in the database.
	 *
	 * @param itemList An ArrayList of ItemModel to update the database.
	 * @throws IOException
	 */
	public void sendItemListToServer(ArrayList<ItemModel> itemList) throws IOException {
		try {
			outputWriter.writeObject(scData + scItem);

			outputWriter.writeObject(cloneArrayList(itemList));

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}
	}


	/**
	 * Sends an Array List of Suppliers to the server to update the
	 * server Suppliers in the database.
	 *
	 * @param supplierList An ArrayList of SupplierModel to update the database.
	 * @throws IOException
	 */
	public void sendSupplierListToServer(ArrayList<SupplierModel> supplierList) throws IOException {
		try {
			outputWriter.writeObject(scData + scSupplier);

			outputWriter.writeObject(cloneArrayList(supplierList));

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}
	}

	/**
	 * Sends an Array List of Orders to the server to update the
	 * server Orders in the database.
	 *
	 * @param orderList An ArrayList of OrderLineModel to update the database.
	 * @throws IOException
	 */
	public void sendOrderListToServer(ArrayList<OrderLineModel> orderList) throws IOException {
		try {
			outputWriter.writeObject(scData + scOrder);

			outputWriter.writeObject(cloneArrayList(orderList));

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}
	}

	/**
	 * Sends an item to the server to update. This is for new items and changed items,
	 * such as items that have had their quantity decreased.
	 *
	 * @param updateItem The item to update in the server.
	 * @throws IOException
	 */
	public void sendItemUpdate(ItemModel updateItem) throws IOException {
		try {
			outputWriter.writeObject(scData + scItem + scObject);

			outputWriter.writeObject((ItemModel) updateItem.clone());

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		} catch (CloneNotSupportedException cnsErr) {
			throw new IOException("Error: item could not be cloned.");
		}
	}

	/**
	 * Searches for an item in the server.
	 *
	 * @param itemId The ID of the item being searched for.
	 * @return The found item or null.
	 * @throws IOException
	 */
	public ItemModel sendItemSearch(int itemId) throws IOException {
		try {
			outputWriter.writeObject(scSearch + scItem + scInt);

			outputWriter.writeObject(itemId);

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}

		try {
			String serverResponse = (String) inputReader.readObject();

			if (serverResponse.contains(scError))
				return null;

			return (ItemModel) inputReader.readObject();
		} catch (IOException readErr) {
			throw new IOException("Error: could not read the server's message.");
		} catch (ClassNotFoundException classErr) {
			throw new IOException("Error: could not properly read the object.");
		}
	}


	/**
	 * Searches for an item in the server.
	 *
	 * @param itemName The name of the item being searched for.
	 * @return The found item or null.
	 * @throws IOException
	 */
	public ItemModel sendItemSearch(String itemName) throws IOException {
		try {
			outputWriter.writeObject(scSearch + scItem + scString);

			outputWriter.writeObject(itemName);

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}

		try {
			String serverResponse = (String) inputReader.readObject();

			if (serverResponse.contains(scError))
				return null;

			return (ItemModel) inputReader.readObject();
		} catch (IOException readErr) {
			throw new IOException("Error: could not read the server's message.");
		} catch (ClassNotFoundException classErr) {
			throw new IOException("Error: could not properly read the object.");
		}
	}

	/**
	 * Sends an item to the server to remove.
	 * Therefore, before removing an item in the client side, this method
	 * should be used to remove the item from the server properly.
	 *
	 * @param deleteItem The item to remove in the server.
	 * @throws IOException
	 */
	public void sendDeletedItemUpdate(ItemModel deleteItem) throws IOException {
		try {
			outputWriter.writeObject(scRemove + scItem);

			outputWriter.writeObject((ItemModel) deleteItem.clone());

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		} catch (CloneNotSupportedException cnsErr) {
			throw new IOException("Error: item could not be cloned.");
		}
	}

	/**
	 * Tells the server to check its quantities, in order to produce
	 * orders if necessary.
	 * Automatically returns the possibly updated order list.
	 *
	 * @return A list of orders after the quantity check.
	 * @throws IOException
	 */
	public ArrayList<OrderLineModel> sendQuantityCheck() throws IOException {
		try {
			outputWriter.writeObject(scCheck + scItem);
			outputWriter.flush();

			return retrieveOrderListFromServer();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		}
	}

	/**
	 * Generic method that returns a cloned version of a given ArrayList.
	 * Requires T to be cloneable.
	 *
	 * @param ogList The original list to clone.
	 * @return a cloned list.
	 */
	public <T> ArrayList<T> cloneArrayList(ArrayList<T> ogList) {
		ArrayList<T> cloneList = new ArrayList<T>();
		for (T objInList : ogList)
			cloneList.add((T) objInList);

		return cloneList;
	}

	public void setSupplierDisplay(DefaultListModel<String> s){supplierDisplay = s;}
	public void setItemDisplay(DefaultListModel<String> s){itemDisplay = s;}

	public String[] supplierIdsandNames()
	{
		ArrayList<String> list = new ArrayList<String>();
		for (SupplierModel s: supplierList)
		{
			list.add(s.idAndName());
		}
		return list.toArray(new String [0]);
	}

	/**
	 * Attempts to read a file with the given filename,converting it into
	 * a list of items, before sending the list to the server.
	 * If the file cannot be read, nothing else happens.
	 *
	 * @param fileName The name of the file to be read.
	 * @return A description of how the attempt went.
	 */
	public String readItems(String fileName)
	{
		if (fileInput.readItemFile(fileName, itemList))
		{
			displayItems();
			try {
				sendItemListToServer(itemList);
			}
			catch (Exception ex)
			{
				return "Error sending data to server.";
			}
			return "Successfully loaded items from file";
		}
		return "There was a problem reading the file, perhaps it doesn't exist.\n" +
				"Reminder: Don't forget to include the .txt";
	}

	/**
	 * Attempts to read a file with the given filename,converting it into
	 * a list of items, before sending the list to the server.
	 * If the file cannot be read, nothing else happens.
	 *
	 * @param fileName The name of the file to be read.
	 * @return A description of how the attempt went.
	 */
	public String readSuppliers(String fileName)
	{
		if (fileInput.readSupplierFile(fileName, supplierList))
		{
			displaySuppliers();
			try {
				sendSupplierListToServer(supplierList);
			}
			catch (Exception ex)
			{
				return "Error sending data to server.";
			}
			return "Successfully loaded suppliers from file";
		}
		return "There was a problem reading the file, perhaps it doesn't exist.\n" +
				"Reminder: Don't forget to include the .txt";
	}

	/**
	 * Fills/overwrites a given list model with the strings of
	 * the supplier list.
	 *
	 */
	protected void displaySuppliers()
	{
		supplierDisplay.clear();
		for(SupplierModel s: supplierList)
		{
			supplierDisplay.addElement(s.displayString());
		}
	}

	/**
	 * Fills/overwrites a given list model with the strings of
	 * the item list.
	 *
	 */
	protected void displayItems()
	{
		itemDisplay.clear();
		for(ItemModel i: itemList)
		{
			itemDisplay.addElement(i.displayString());
		}
	}

	public String addSupplier(int id, String name, String address, String contact)
	{
		for(SupplierModel s: supplierList)
		{
			if (s.getId()==id)
			{
				return "Supplier already exists!";
			}
		}
		SupplierModel s = new SupplierModel(id, name, address, contact);
		supplierList.add(s);
		displaySuppliers();
		try
		{
			sendSupplierListToServer(supplierList);
		}
		catch(Exception ex)
		{
			return "Error sending data to server";
		}

		return "Successfully added supplier.";
	}

	public String addItem(int id, String name, int quantity, float price, String supplierInfo)
	{
		ItemModel i;
		Scanner scan = new Scanner(supplierInfo);
		int supId = scan.nextInt();
		for(SupplierModel s: supplierList)
		{
			if (s.getId()==supId)
			{
				i = new ItemModel(id, name, quantity, price,s);
				itemList.add(i);
			}
		}
		displayItems();
		try
		{
			sendItemListToServer(itemList);
		}
		catch(Exception ex)
		{
			return "Error sending data to server";
		}

		return "Successfully added item.";
	}

	//public void orderItem(){}

	/**
	 * Constructor.
	 * Creates the client's socket and the object I/O streams for that socket,
	 * using the socket's I/O handles.
	 *
	 * @param serverName The server host name.
	 * @param portNum    The port number.
	 */
	public ClientController(String serverName, int portNum) {
		fileInput = new FileReader();
		itemList = new ArrayList<ItemModel>();
		supplierList = new ArrayList<SupplierModel>();

		try {
			clientSocket = new Socket(serverName, portNum);
			outputWriter = new ObjectOutputStream(clientSocket.getOutputStream());
			outputWriter.flush();
			inputReader = new ObjectInputStream(clientSocket.getInputStream());
			outputWriter.flush();
		} catch (IOException ioErr) {
			System.out.println(ioErr.getMessage());
		}
	}

	/**
	 * Main method for the client, used for testing.
	 * Creates and runs a client, which tries to connect to a game server
	 * on port 8428.
	 *
	 * @param args Command line arguments when starting the program.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ClientController client = new ClientController("localhost", 8428);
		System.out.println("Testing ClienController concluded.");
	}
}
