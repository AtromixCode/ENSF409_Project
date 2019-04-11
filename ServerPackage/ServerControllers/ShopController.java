package ServerPackage.ServerControllers;

import Models.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The shop controller class that handles requests for information
 * from the server controller. Handles logic used in all shop
 * features.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
 */
public class ShopController implements Runnable, SCCommunicationConstants {

	/**
	 * The list of orders corresponding to the shop.
	 */
	private ArrayList<OrderLineModel> orders;

	/**
	 * The order used to generate more orders.
	 */
	private OrderModel order;

	/**
	 * The socket used for communications with the client.
	 */
	private Socket clientSocket;

	/**
	 * The list of suppliers in the shop.
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
	 * The inventory of the shop.
	 */
	private InventoryController inv;

	/**
	 * The data controller of the shop.
	 */
	private DataBaseController data;

	/**
	 * Constructs a store and does the necessary calling to get up to date
	 * information from the database.
	 * @param sc the accepted socket from the server used to communicate with the client.
	 */
	public ShopController(Socket sc)
	{
		clientSocket = sc;
		data = new DataBaseController();
		inv = new InventoryController(data);
		suppliers = data.supplierListFromDatabase();
		inv.updateItemsSuppliers(suppliers);
		orders = data.orderLineListFromDataBase();
		order = new OrderModel();
		order.setOrderLines(orders);
		checkQuantities();

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
	 * Read opening messages from the client and then respond appropriately.
	 * @return False if the user quits.
	 */
	private boolean readClientMessage()
	{
		try
		{
			String clientOpeningMessage = (String)inputReader.readObject();

			if(clientOpeningMessage.contains(scQuit)) {
				System.out.println("Client Disconnected");
				return false;
			}

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

	/**
	 * Make the shop do something based on the given opcode.
	 * opcodes are translated from keywords in SCCommunicationConstants as follows:
	 * Hundreds digit: 1 = sending data from the database to the client.
	 * 				   2 = updating the database based on data from the client.
	 * 				   3 = search the database for an object based on a given criteria and send it to the client.
	 * 				   4 = remove an object from the database.
	 * 				   5 = prompt the server to analyze and modify the data in the database
	 *
	 * Tens digit:	   0 = data received from or sent to the client will be in ArrayList form.
	 * 				   1 = data received from or sent to the client will be in int form.
	 * 				   2 = data received from or sent to the client will be in String form.
	 * 				   3 = data received from or sent to the client will be in user-defined object form.
	 *
	 * Ones digit:	   1 = data received from or sent to the client will be Items.
	 *  			   2 = data received from or sent to the client will be Suppliers.
	 *  			   3 = data received from or sent to the client will be Orders or order lines.
	 *
	 * @param opcode The opcode of the action to perform.
	 */
	private void actOnOpCode(int opcode)
	{
		switch(opcode)
		{
			case(101):	sendItemListToClient();			break;	//return the item list 
			case(102):	sendSupplierListToClient();		break;	//return the supplier list 
			case(103):	sendOrderListToClient();		break;	//return the order list 

			case(201):	updateItemListFromClient();		break;	//read an item list
			case(202):	updateSupplierListFromClient();	break;	//read an supplier list
			case(203):	updateOrderListFromClient();	break;	//read an order list
			case(211):	updateOrderListFromClientOrder();	break;	// read an item and quantity to order and order it.
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
	private void sendItemListToClient()
	{
		try
		{
			outputWriter.writeObject(scOkay);
			inv.updateItemList(data.itemListFromDataBase());
			inv.updateItemsSuppliers(suppliers);
			outputWriter.writeObject(cloneArrayList(inv.getItems()));

			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr.getMessage());
			printErrorToClient();
		}
	}

	/**
	 * Sends the list of suppliers to the client.
	 * If the list fails to write, instead sends an error message.
	 * This should result in an error on the client side.
	 */
	private void sendSupplierListToClient()
	{
		try
		{
			outputWriter.writeObject(scOkay);
			suppliers = data.supplierListFromDatabase();
			outputWriter.writeObject(cloneArrayList(suppliers));

			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr.getMessage());
			printErrorToClient();
		}
	}

	/**
	 * Sends the list of orders to the client.
	 * If the list fails to write, instead sends an error message.
	 * This should result in an error on the client side.
	 */
	private void sendOrderListToClient()
	{
		try
		{
			outputWriter.writeObject(scOkay);
			orders = data.orderLineListFromDataBase();
			outputWriter.writeObject(cloneArrayList(orders));

			outputWriter.flush();
		}
		catch(IOException writeErr)
		{
			System.err.println(writeErr.getMessage());
			printErrorToClient();
		}
	}


	/**
	 * Receives a list of ItemModel from the client.
	 * Updates the item list across the server and the database.
	 */
	private void updateItemListFromClient()
	{
		ArrayList<ItemModel> tempList;
		try
		{
			tempList = (ArrayList<ItemModel>)inputReader.readObject();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr.getMessage());
			return;
		}

		inv.updateItemList(tempList);
		inv.updateItemsSuppliers(suppliers);
		data.updateItemList(tempList);
		if(!suppliers.isEmpty()){
			checkQuantities();
		}

	}

	/**
	 * Receives a list of SupplierModel from the client.
	 * Updates the supplier list across the server and the database.
	 */
	private void updateSupplierListFromClient()
	{
		ArrayList<SupplierModel> tempList;
		try
		{
			tempList = (ArrayList<SupplierModel>)inputReader.readObject();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr.getMessage());
			return;
		}
		suppliers = tempList;
		data.updateSupplierList(tempList);
		inv.updateItemsSuppliers(tempList);
		if(!inv.items.isEmpty()) {
			checkQuantities();
		}
	}


	/**
	 * Receives a list of OrderModel from the client.
	 * Updates the supplier list across the server and the database.
	 */
	private void updateOrderListFromClient()
	{
		ArrayList<OrderLineModel> tempList;
		try
		{
			tempList = (ArrayList<OrderLineModel>)inputReader.readObject();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr.getMessage());
			return;
		}

		orders = tempList;
		data.updateOrderList(tempList);


	}

	/**
	 * Receives an item from the client, and a quantity to order.
	 * Checks if the item exists in the server, and it it does,
	 * orders the item according to the desired amount.
	 */
	private void updateOrderListFromClientOrder()
	{
		ItemModel itemToOrder;
		int quantityToOrder;
		
		try {
			itemToOrder = (ItemModel) inputReader.readObject();
			quantityToOrder = (Integer)inputReader.readObject();
			inv.updateItemsSuppliers(suppliers);
            data.insertOrderline(order.createOrder(inv.findItem(itemToOrder.getId()), quantityToOrder));
			itemToOrder.setQuantity(itemToOrder.getQuantity() + quantityToOrder);
			inv.updateItem(inv.findItem(itemToOrder.getId()));
			data.addItem(itemToOrder);
		} catch (IOException readErr) {
			System.err.println(readErr.getMessage());
			return;
		} catch (ClassNotFoundException classErr) {
			System.err.println(classErr.getMessage());
			return;
		}
	}
	
	
	/**
	 * Receives an item from the client.
	 * Across the server and in the database, either updates the item in the
	 * item lists if it already exists, or adds a new item to those lists.
	 */
	private void updateItemFromClient() {
		ItemModel changeItem;

		try {
			changeItem = (ItemModel) inputReader.readObject();
		} catch (IOException readErr) {
			System.err.println(readErr.getMessage());
			return;
		} catch (ClassNotFoundException classErr) {
			System.err.println(classErr.getMessage());
			return;
		}
		inv.updateItem(changeItem);
		inv.updateItemsSuppliers(suppliers);
        checkQuantities();
	}

	/**
	 * Searches for an item, sending it to the client if it is found.
	 * If the item cannot be found, sends an error message to the client.
	 * If there is no search method for the given search parameter, do nothing.
	 *
	 * @param searchChar What parameter to use when searching for an item.
	 *					 'i' is for an int id, 's' is for an item name.
	 */
	private void searchItemFromClient(char searchChar)
	{

		try
		{
			ItemModel searchItem = null;

			if(searchChar == 'i')
			{
				int searchId = (Integer)inputReader.readObject();
				searchItem = inv.findItem(searchId);
			}
			else if(searchChar == 'c')
			{
				String searchName = (String)inputReader.readObject();
				searchItem = inv.findItem(searchName);
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
			System.err.println(readErr.getMessage());
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr.getMessage());
			return;
		}
		catch(CloneNotSupportedException cnsErr)
		{
			System.err.println(cnsErr.getMessage());
			return;
		}
	}

	/**
	 * Searches for a given item from the client.
	 * Then, attempts to delete the version of that item from the item lists
	 * of the server and the database.
	 */
	private void deleteItemFromClient()
	{
		try
		{
			ItemModel deleteItem = (ItemModel)inputReader.readObject();
			inv.removeItem(deleteItem);
			data.removeItem(deleteItem);

		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(ClassNotFoundException classErr)
		{
			System.err.println(classErr.getMessage());
			return;
		}
	}

	/**
	 * Checks the quantities of the items in the item lists.
	 * If necessary, create orders and update all server order lists
	 * and the database.
	 */
	private void checkQuantities()
	{
		if(inv.checkIfOrder()){
			inv.generateOrder(order);
			orders = order.getOrderLines();
		}
	}

	/**
	 * Indicate to the client that an error occurred when handling
	 * a client request.
	 */
	private void printErrorToClient()
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
	private <T> ArrayList<T> cloneArrayList(ArrayList<T> ogList)
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
