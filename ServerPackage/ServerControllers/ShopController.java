package ServerPackage.ServerControllers;

//import ServerPackage.ServerControllers.DataBaseController;
import Models.ItemModel;
import Models.OrderModel;
import Models.SupplierModel;
import ServerPackage.ServerControllers.InventoryController;
import ServerPackage.ServerControllers.SCCommunicationConstants;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ShopController implements Runnable, SCCommunicationConstants {
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
	 * Object Input stream from the socket.
	 */
	private ObjectInputStream inputReader;
	
	/**
	 * Object Output stream to the socket.
	 */
	private ObjectOutputStream outputWriter;

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
	 /*
    private BufferedReader socketIn;
*/
    /**
     * The output to the socket
     */
	 /*
    private PrintWriter socketOut;
*/

    /**
     * Constructs a store and does the necessary callings to get up to date
     * information from the database
     * @param sc the accepted socket from the server used to communicate with the client
     */
    public ShopController(Socket sc)
    {
        clientSocket = sc;
        //DataBaseController data = new DataBaseController();
		/**
		 * TODO: RE-ADD this when it works.
		 */
        try
        {	
			inputReader = new ObjectInputStream(clientSocket.getInputStream());
			outputWriter = new ObjectOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            System.err.println(e.getMessage());
            System.err.println("Error constructing the shop");
            System.err.println(e.getMessage());
        }
    }

    /**
     * outputs to the client the given string
     * @param s the string to output to the client
     */
	 /*
    private void outputToClient (String s){
        socketOut.println(s);
    }
	*/

	/**
	 * Read opening messages from the client and then respond appropriately.
	 * @return False if the user quits.
	 */
	private boolean readClientMessage()
	{
		try
		{
			String clientOpeningMessage = (String)inputReader.readObject();
		
			if(clientOpeningMessage.contains(scQuit))
				return false;
			
			int opcode = 0;
			
			//type of operation
			if(clientOpeningMessage.contains(scQuery))
				opcode += 100;
			else if(clientOpeningMessage.contains(scData))
				opcode += 200;
			else if(clientOpeningMessage.contains(scSearch))
				opcode += 300;
			else if(clientOpeningMessage.contains(scRemove))
				opcode += 400;
			else if(clientOpeningMessage.contains(scCheck))
				opcode += 500;
				
			//type of object involved
			if(clientOpeningMessage.contains(scItem))
				opcode += 1;
			else if(clientOpeningMessage.contains(scSupplier))
				opcode += 2;
			else if(clientOpeningMessage.contains(scOrder))
				opcode += 3;
			
			//type of identifier sent
			if(clientOpeningMessage.contains(scInt))
				opcode += 10;
			else if(clientOpeningMessage.contains(scString))
				opcode += 20;
			else if(clientOpeningMessage.contains(scObject))
				opcode += 30;
			
			actOnOpCode(opcode);
			
			/*
			if(clientOpeningMessage.contains(scQuery))
			{
				if(clientOpeningMessage.contains(scItem))
					//return the item list
				else if(clientOpeningMessage.contains(scSupplier))
					//return the supplier list.
				else if(clientOpeningMessage.contains(scOrder))
					//return the order list.
			}
			else if(clientOpeningMessage.contains(scData))
			{
				if(clientOpeningMessage.contains(scItem))
					//get an item list 
				else if(clientOpeningMessage.contains(scSupplier))
					//get a supplier list
				else if(clientOpeningMessage.contains(scOrder))
					//get an order list.
			}
			else if(clientOpeningMessage.contains(scSearch))
			{
				if(clientOpeningMessage.contains(scItem))
				{
					if(clientOpeningMessage.contains(scInt))
						//return the item
					else if(clientOpeningMessage.contains(scString))
						//return the item
				}
			}
			else
				outputWriter.writeObject(scErr);
			*/
			
			return true;

		}
		catch(IOException readErr)
		{
			System.err.println("Error: Could not read.");
			return false;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println("Error: Could not read class.");
		}
		
		return true;
	}

	public void actOnOpCode(int opcode)
	{
		switch(opcode)
		{
			case(101):	sendItemListToClient();			break;	//return the item list 
			case(102):	sendSupplierListToClient();		break;	//return the supplier list 
			case(103):	sendOrderListToClient();		break;	//return the order list 
			
			case(201):	updateItemListFromClient();		break;	//read an item list
			case(202):	updateSupplierListFromClient();	break;	//read an supplier list
			case(203):	updateOrderListFromClient();	break;	//read an order list
			case(231):	updateItemFromClient();			break;	//read an item to add/replace
			
			case(311):	searchItemFromClient('i');			break;	//search for an item using an id
			case(321):	searchItemFromClient('s');			break;	//search for an item using a String name

			case(401):	deleteItemFromClient();			break;	//delete an item
			
			case(501):	checkQuantities();				break;	//check the quantities of the items
			
			default:	printErrorToClient();	break;
		}
		
	}

	/**
	 * Sends the list of items in the inventory to the client.
	 * If the list fails to write, instead sends an error message.
	 * This should result in an error on the client side.
	 */
	public void sendItemListToClient()
	{
		System.out.println("Worked");
		try
		{	
			outputWriter.writeObject(scOkay);
		
			outputWriter.writeObject(cloneArrayList(inv.getItems()));
		
			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr);
			printErrorToClient();
		}
	}

	/**
	 * Sends the list of suppliers to the client.
	 * If the list fails to write, instead sends an error message.
	 * This should result in an error on the client side.
	 */
	public void sendSupplierListToClient()
	{
		System.out.println("Worked");
		try
		{	
			outputWriter.writeObject(scOkay);
		
			outputWriter.writeObject(cloneArrayList(suppliers));
		
			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr);
			printErrorToClient();
		}
	}

	/**
	 * Sends the list of orders to the client.
	 * If the list fails to write, instead sends an error message.
	 * This should result in an error on the client side.
	 */
	public void sendOrderListToClient()
	{
		try
		{	
			outputWriter.writeObject(scOkay);
		
			outputWriter.writeObject(cloneArrayList(orders));
		
			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr);
			printErrorToClient();
		}
	}
	
	
	/**
	 * Recieve a list of ItemModel from the client.
	 * updates the item list across the server and the database.
	 */
	public void updateItemListFromClient()
	{
		ArrayList<ItemModel> tempList;
		try
		{
			tempList = (ArrayList)inputReader.readObject();
			inv.setItems(tempList);
			for (ItemModel i: inv.getItems())
			{
				System.out.println(i.toString());
			}

		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
		
		//TODO
		//update all the lists of items to now be tempList
	}
	
	/**
	 * Recieve a list of SupplierModel from the client.
	 * updates the supplier list across the server and the database.
	 */
	public void updateSupplierListFromClient()
	{
		ArrayList<SupplierModel> tempList;
		try
		{
			tempList = (ArrayList<SupplierModel>)inputReader.readObject();
			suppliers = tempList;
			for (SupplierModel i: suppliers)
			{
				System.out.println(i.toString());
			}
		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
		//TODO
		//update all the lists of servers to now be tempList
	}
	
		
	/**
	 * Recieve a list of OrderModel from the client.
	 * updates the supplier list across the server and the database.
	 */
	public void updateOrderListFromClient()
	{
		ArrayList<OrderModel> tempList;
		try
		{
			tempList = (ArrayList<OrderModel>)inputReader.readObject();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
		
		//TODO
		//update all the lists of orders to now be tempList
	}
	
	/**
	 * Recieve an item from the client.
	 * Across the server and in the database, either updates the item in the
	 * item lists if it already exists, or adds a new item to those lists.
	 */
	public void updateItemFromClient()
	{
		ItemModel changeItem;
		
		try
		{
			changeItem = (ItemModel)inputReader.readObject();		
		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
		
		//TODO
		//For all item lists and the database, check if the list contains changeItem.
		//If so, overwrite/replace this item.
		//Otherwise, add changeItem to the lists and database. 
	}
	
	/**
	 * Searches for an item, sending it to the client if it is found.
	 * If the item cannot be found, sends an error message to the client.
	 * If there is no search method for the given search paramter, do nothing. 
	 *
	 * @param searchChar What parameter to use when searching for an item.
	 *					 'i' is for an int id, 's' is for an item name.
	 */
	public void searchItemFromClient(char searchChar)
	{
		
		try
		{
			ItemModel searchItem = null;
			
			if(searchChar == 'i')
			{
				int searchId = (Integer)inputReader.readObject();
				
				//TODO
				//search for an item using an int id. 
				//if the item cannot be found, then searchItem needs to be null.
				//otherwise, searchItem points to the appropriate item.
			}
			else if(searchChar == 'c')
			{
				String searchName = (String)inputReader.readObject();
				
				//TODO
				//search for an item using an int id. 
				//if the item cannot be found, then searchItem needs to be null.
				//otherwise, searchItem points to the appropriate item.
			}
			else
				return;
			
			if(searchItem != null)
			{
				outputWriter.writeObject(scOkay);
				outputWriter.writeObject((ItemModel)searchItem.clone());
				outputWriter.flush();
			}
			else
				printErrorToClient();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
		catch(CloneNotSupportedException cnsErr)
		{
			System.err.println(cnsErr);
			return;
		}
	}
	
	/**
	 * Searches for a given item from the client.
	 * Then, attempts to delete the version of that item from the item lists
	 * of the server and the database.
	 */
	public void deleteItemFromClient()
	{
		try
		{
			ItemModel deleteItem = (ItemModel)inputReader.readObject();
		
			//TODO
			//delete all instances of deleteItem from all lists and the database.
			//(if they have the same id and/or item name then delete it)
		}
		catch(IOException readErr)
		{
			System.err.println(readErr);
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr);
			return;
		}	
	}
	
	/**
	 * Checks the quantities of the items in the item lists.
	 * If necessary, create orders and update all server order lists
	 * and the database.
	 */
	public void checkQuantities()
	{
		//TODO
		//check item quantities and update orders as needed.
		//don't worry about the client copies of orders.
		//this is basically a one-way message
	}

	/**
	 * Indicate to the client that an error occurred when handling
	 * a client request.
	 */
	public void printErrorToClient()
	{
		try
		{
			outputWriter.writeObject(scError);
		}
		catch(IOException ioErr)
		{
			System.err.println(ioErr.getMessage());
		}
	}
	
	/**
	 * Generic method that returns a cloned version of a given ArrayList.
	 * Requires T to be cloneable.
	 * 
	 * @param ogList The original list to clone.
	 * @return a cloned list.
	 */
	public <T> ArrayList<T> cloneArrayList(ArrayList<T> ogList)
	{
		ArrayList<T> cloneList = new ArrayList<T>();
		for(T objInList : ogList)
			cloneList.add((T)objInList);
		
		return cloneList;
	}
	
	/**
	 * Runnable run method.
	 * Runs the server by waiting for client requests, then fulfilling those
	 * requests, before waiting again.
	 * When the client manually disconnects, closes all streams before this
	 * method terminates.
	 */
    @Override
    public void run ()
	{
        System.out.println("Doing shop things");
	
		while(readClientMessage())
		{
		}
		
		try
		{
			clientSocket.close();
			inputReader.close();
			outputWriter.close();
		}
		catch(IOException shutDownErr)
		{
			System.out.println("Could not close inputs and outputs");
			shutDownErr.printStackTrace();
		}
		
    }
}
