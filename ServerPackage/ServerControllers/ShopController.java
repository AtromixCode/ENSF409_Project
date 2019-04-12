package ServerPackage.ServerControllers;

import Models.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The shop controller class that handles requests for information
 * from the server controller. Handles logic used in all shop
 * features.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
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
		updateOrderListFromClientPrompt();

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
	 * Read opening messages from the client, translate it into opcode,
	 * and then respond appropriately.
	 * @return False if the user quits.
	 */
	private boolean readClientOpeningMessage()
	{
		try {
			String clientOpeningMessage = (String)inputReader.readObject();
			if(clientOpeningMessage.contains(scQuit)) {
				System.out.println("Client Disconnected");
				return false;
			}
			Scanner openingMessageScanner = new Scanner(clientOpeningMessage).useDelimiter(";");
			int opcode = convertOpeningMessageToOpcode(openingMessageScanner);
			
			int extraArgInt = -1;
			String extraArgString = null;
			if(opcode % 10 == 1) extraArgInt = openingMessageScanner.nextInt();
			else if(opcode % 10 == 2) extraArgString = openingMessageScanner.next();

			openingMessageScanner.close();
			actOnOpCode(opcode, extraArgInt, extraArgString);
			return true;
		}
		catch(IOException readErr) {
			System.err.println("Error: Could not read.");
			return false;
		} 
		catch(ClassNotFoundException classErr) {
			System.err.println("Error: Could not read class.");
		}
		return true;
	}

	/**
	 * Converts the opening message into opcode.
	 * 
	 * @param openingMessageScanner The scanner scanning the opening message. Needs to be starting at the front. 
	 * @return the opcode representation of the opening message.
	 */
	public int convertOpeningMessageToOpcode(Scanner openingMessageScanner)
	{		
		int opcode = 0;
		opcode += convertOpeningMessageOperationToOpcode(openingMessageScanner.next() + ";");
		opcode += convertOpeningMessageArgDegreeToOpcode(openingMessageScanner.next() + ";");
		opcode += convertOpeningMessageArgTypeToOpcode(openingMessageScanner.next() + ";");
		opcode += convertOpeningMessageArgExtraToOpcode(openingMessageScanner.next() + ";");
		
		return opcode;
	}
	
	/**
	 * Converts the opening message Operation keyword into a number value.
	 * Operation keyword values are in the 1000s
	 * 
	 * @param operationKeyword The Operation keyword of the opening message.
	 * @return The opcode representation of the Operation keyword.
	 */
	private int convertOpeningMessageOperationToOpcode(String operationKeyword)
	{
		if(operationKeyword.equals(scSend))
			return 1000;
		else if(operationKeyword.equals(scUpdate))
			return 2000;
		else if(operationKeyword.equals(scCreate))
			return 3000;
		else if(operationKeyword.equals(scDelete))
			return 4000;
		else if(operationKeyword.equals(scVerify))
			return 5000;
		else
			return -10000;
	}
	
	/**
	 * Converts the opening message Argument Degree keyword into a number value.
	 * Argument Degree keyword values are in the 100s
	 * 
	 * @param argDegreeKeyword The Argument Degree keyword of the opening message.
	 * @return The opcode representation of the Argument Degree keyword.
	 */
	private int convertOpeningMessageArgDegreeToOpcode(String argDegreeKeyword)
	{
		if(argDegreeKeyword.equals(scSingle))
			return 100;
		else if(argDegreeKeyword.equals(scList))
			return 200;
		else
			return -10000;
	}
	
	/**
	 * Converts the opening message Argument Type keyword into a number value.
	 * Argument Type keyword values are in the 10s
	 * 
	 * @param argTypeKeyword The Argument Type keyword of the opening message.
	 * @return The opcode representation of the Argument Type keyword.
	 */
	private int convertOpeningMessageArgTypeToOpcode(String argTypeKeyword)
	{
		if(argTypeKeyword.equals(scItem))
			return 10;
		else if(argTypeKeyword.equals(scSupplier))
			return 20;
		else if(argTypeKeyword.equals(scOrder))
			return 30;
		else
			return -10000;
	}
	
	/**
	 * Converts the opening message Extra Argument keyword into a number value.
	 * Extra Argument keyword values are in the 1s
	 * 
	 * @param argExtraKeyword The Extra Argument keyword of the opening message.
	 * @return The opcode representation of the Extra Argument keyword.
	 */
	private int convertOpeningMessageArgExtraToOpcode(String argExtraKeyword)
	{
		if(argExtraKeyword.equals(scDefault))
			return 0;
		else if(argExtraKeyword.equals(scInt))
			return 1;
		else if(argExtraKeyword.equals(scString))
			return 2;
		else if(argExtraKeyword.equals(scPrompt))
			return 3;
		else
			return -10000;
	}
	
	/**
	 * Make the shop do something based on the given opcode.
	 * opcodes are translated from keywords in SCCommunicationConstants as follows:
	 * Operation keyword:	1 = Data is being sent to the client.
	 * Thousands digit:		2 = Data needs to be added or updated/replaced.
	 * 						3 = Data needs to be created.
	 * 						4 = Data needs to be deleted.
	 * 						5 = Data needs to be verified or searched for.
	 *  
	 * ArgDegree keyword:	1 = Mainly involves only a single object.
	 * Hundreds digit:		2 = Mainly involves a list of objects.
	 * 
	 * ArgType keyword:		1 = Concerns items.
	 * Tens digit:			2 = Concerns suppliers.
	 * 						3 = Concerns orders/order lines.
	 * 
	 * ArgExtra keyword:	0 = User-defined objects will be needed.
	 * Ones digit:			1 = An integer in the opening message will be needed.
	 *						2 = A String in the opening message will be needed.
	 *						3 = Nothing from the client will be needed to change data in the DB.
	 *
	 * @param opcode The opcode of the action to perform.
	 * @param extraParamInt Any extra integer parameter read from the client opening message.
	 * @param extraParamString Any extra String parameter read from the client opening message.
	 */
	private void actOnOpCode(int opcode, int extraParamInt, String extraParamString)
	{
		switch(opcode)
		{
			case(1210):	sendItemListToClient();			break;	//return the item list 
			case(1220):	sendSupplierListToClient();		break;	//return the supplier list 
			case(1230):	sendOrderListToClient();		break;	//return the order list 
			
			case(2110):	updateItemFromClient();			break;	//read an item to add/replace
			case(2210):	updateItemListFromClient();		break;	//read an item list
			case(2220):	updateSupplierListFromClient();	break;	//read an supplier list
			case(2230):	updateOrderListFromClient();	break;	//read an order list
			case(2233):	updateOrderListFromClientPrompt();		//check the quantites of the items and
						break;									//order some if necessary.
						
			case(3131): createOrderFromClientIntQuantity(extraParamInt);
						break;									//read an item and order extraParamInt of it.

			case(4110):	deleteItemFromClient();			break;	//delete an item
						
			case(5111):	searchItemFromClient(extraParamInt);			
						break;									//search for an item using an id
			case(5112):	searchItemFromClient(extraParamString);			
						break;									//search for an item using a String name

			default:	sendErrorToClient();			break;	//send an error to the client
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
			sendErrorToClient();
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
			sendErrorToClient();
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
			sendErrorToClient();
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
		updateOrderListFromClientPrompt();
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
		if(!suppliers.isEmpty())
			updateOrderListFromClientPrompt();
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
		if(!inv.items.isEmpty())
			updateOrderListFromClientPrompt();
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
	 * Checks the quantities of the items in the item lists.
	 * If necessary, create orders and update all server order lists
	 * and the database.
	 */
	private void updateOrderListFromClientPrompt()
	{
		if(inv.checkIfOrder()){
			inv.generateOrder(order);
			orders = order.getOrderLines();
		}
	}
	
	/**
	 * Receives an item from the client, and a quantity to order.
	 * Checks if the item exists in the server, and it it does,
	 * orders the item according to the desired amount.
	 * @param quantityToOrder The amount of the item to order. 
	 */
	private void createOrderFromClientIntQuantity(int quantityToOrder)
	{
		ItemModel itemToOrder;		
		try {
			itemToOrder = (ItemModel) inputReader.readObject();
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
	 * Searches for an item using an id, sending it to the client if it is found.
	 * If the item cannot be found, sends an error message to the client.
	 * If there is no search method for the given search parameter, do nothing.
	 *
	 * @param itemSearchId The item id to look for.
	 */
	private void searchItemFromClient(int itemSearchId)
	{
		try
		{
			ItemModel searchItem = null;
			
			searchItem = inv.findItem(itemSearchId);

			if(searchItem != null)
			{
				outputWriter.writeObject(scOkay);
				outputWriter.writeObject((ItemModel)searchItem.clone());
				outputWriter.flush();
			}
			else
				sendErrorToClient();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(CloneNotSupportedException cnsErr)
		{
			System.err.println(cnsErr.getMessage());
			return;
		}
	}
	
	/**
	 * Searches for an item using a name, sending it to the client if it is found.
	 * If the item cannot be found, sends an error message to the client.
	 * If there is no search method for the given search parameter, do nothing.
	 *
	 * @param itemSearchName The item name to look for.
	 */
	private void searchItemFromClient(String itemSearchName)
	{
		try
		{
			ItemModel searchItem = null;
			
			searchItem = inv.findItem(itemSearchName);

			if(searchItem != null)
			{
				outputWriter.writeObject(scOkay);
				outputWriter.writeObject((ItemModel)searchItem.clone());
				outputWriter.flush();
			}
			else
				sendErrorToClient();
		}
		catch(IOException readErr)
		{
			System.err.println(readErr.getMessage());
			return;
		}
		catch(CloneNotSupportedException cnsErr)
		{
			System.err.println(cnsErr.getMessage());
			return;
		}
	}
	
	/**
	 * Writes an error to the client.
	 */
	protected void sendErrorToClient()
	{
		try
		{
			outputWriter.writeObject(scError);
			outputWriter.flush();	
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
	protected <T> ArrayList<T> cloneArrayList(ArrayList<T> ogList)
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
		System.out.println("A shop is now running.");

		while(readClientOpeningMessage())
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
