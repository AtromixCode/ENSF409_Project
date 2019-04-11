package client.ClientControllers;

import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.swing.*;

import Models.*;

/**
 * When run, connects the client to the server, and allows the client
 * to send and receive lists of items, suppliers, and orders.
 * Sent lists can be created from text files.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
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
	 * List of items held by the store.
	 */
	private ArrayList<ItemModel> itemList;

	/**
	 * List of suppliers held by the store.
	 */
	private ArrayList<SupplierModel> supplierList;

	/**
	 * The display model that is used to display the list of items.
	 */
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
		catch (Exception ex)
		{
			System.out.println("Cannot close connection because client never connected.");
		}
		finally {
			System.exit(0);
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
	 * Sends an item and a quantity to order to the server.
	 * The server will generate an order line to add to their order list.
	 * The server will not automatically send the updated line.
	 * If the item being ordered does not exist in the server's list, it will throw an error.
	 * If less than 1 item is being ordered, it will throw an error.
	 *
	 * @param itemToOrder The item being ordered.
	 * @param quantityToOrder The quantity of the item to be ordered.
	 * @throws IOException
	 */
	public void sendItemOrderUpdate(ItemModel itemToOrder, int quantityToOrder) throws IOException {

		if(quantityToOrder < 1)
			throw new IOException("Error: cannot order less than 1 item.");

		try {
			outputWriter.writeObject(scData + scItem + scInt);

			outputWriter.writeObject((ItemModel) itemToOrder.clone());
			outputWriter.writeObject(quantityToOrder);

			outputWriter.flush();
		} catch (IOException writeErr) {
			throw new IOException("Error: could not communicate to the server.");
		} catch (CloneNotSupportedException cnsErr) {
			throw new IOException("Error: item could not be cloned.");
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

	/**
	 * Sets the display model for items.
	 * @param s the display model used in the GUI.
	 */
	public void setItemDisplay(DefaultListModel<String> s){itemDisplay = s;}


	/**
	 * Gets all data from server and inputs into display models.
	 * @return true if communication with server was successful, false if otherwise.
	 */
	public boolean fetchAndDisplayItems(String search)
	{
		try {
			itemList = retrieveItemListFromServer();
			displayItems(search);
		}
		catch (Exception e)
		{
			System.out.println("Error retrieving from server!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean fetchSuppliers()
	{
		try {
			supplierList = retrieveSupplierListFromServer();
		}
		catch (Exception e)
		{
			System.out.println("Error retrieving from server!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Fills/overwrites a given list model with the strings of
	 * the item list.
	 */
	public void displayItems(String search)
	{
		itemDisplay.clear();
		if(search.equals("")) {

			for (ItemModel i : itemList) {
				itemDisplay.addElement(i.displayString());
			}
		}
		else
		{
			for (ItemModel i: itemList)
			{
				if (i.toString().toLowerCase().contains(search))
				{
					itemDisplay.addElement(i.displayString());
				}
			}
		}
	}

	/**
	 * Removes a certain quantity of the item, updating this on the server and client.
	 * @param id the id of the item to be sold.
	 * @param quantity the quantity to be sold of the item.
	 * @return the result of the item sale.
	 */
	public String buyItem(int id, int quantity)
	{
		for (ItemModel i: itemList)
		{
			if (i.getId()==id)
			{
				if (quantity>i.getQuantity())
				{
					return "Not enough quantity to sell that much.";
				}
				boolean check = false;
				for (SupplierModel s: supplierList)
				{
					if (s.getId()==i.getSupplierID())
					{
						check = true;
					}
				}
				if (!check)
				{
					return "No supplier found for that item!";
				}
				else
				{
					i.setQuantity(i.getQuantity()-quantity);
					try {
						sendItemUpdate(i);
						return "Successfully sold item!";
					}
					catch(Exception e)
					{
						return "Error communicating with server.";
					}

				}
			}
		}
		return "Item does not exist";
	}

	/**
	 * Constructor.
	 * Creates the client's socket and the object I/O streams for that socket,
	 * using the socket's I/O handles.
	 *
	 * @param serverName The server host name.
	 * @param portNum    The port number.
	 */
	public ClientController(String serverName, int portNum) {
		itemList = new ArrayList<ItemModel>();
		supplierList = new ArrayList<SupplierModel>();

		try {
			clientSocket = new Socket(serverName, portNum);
			outputWriter = new ObjectOutputStream(clientSocket.getOutputStream());
			outputWriter.flush();
			inputReader = new ObjectInputStream(clientSocket.getInputStream());
			itemList = retrieveItemListFromServer();
			supplierList = retrieveSupplierListFromServer();
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
		System.out.println("Testing ClientController concluded.");
	}
}
