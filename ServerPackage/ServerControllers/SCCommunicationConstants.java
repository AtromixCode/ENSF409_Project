package ServerPackage.ServerControllers;

/**
 * Contains constant String variables for server-client 
 * message communication.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 1, 2019
 */
public interface SCCommunicationConstants 
{
	/**
	 * A string message containing this string is asking for objects.
	 * Needs an object type specifying string to also be in the string.
	 * The following object messages can be assumed to be of the object 
	 * type specified, or an error.
	 */
	static final String scQuery = "QUERY;";
	
	/**
	 * A string message containing this string is about to send objects, to update
	 * data. Needs an object type specifying string to also be in the string.
	 * The following object messages can be assumed to be of the object 
	 * type specified.
	 */
	static final String scData = "DATA;";
	
	/**
	 * A string message containing this string is asking for an object.
	 * Needs an object type specifying string to also be in the string.
	 * The following object message can be assumed to be of the object 
	 * type specified, or an error.
	 */
	static final String scSearch = "SEARCH;";
	
	/**
	 * A string message containing this string indicates that an error has occurred
	 * when attempting to respond to a query by sending data.
	 */
	static final String scError = "ERROR;";
	
	/**
	 * A string message containing this string indicates that ItemModel objects
	 * are needed to be sent or read.
	 */
	static final String scItem = "ITEM;";
	
	/**
	 * A string message containing this string indicates that SupplierModel objects
	 * are needed to be sent or read.
	 */
	static final String scSupplier = "SUPPLIER;";
	
	/**
	 * A string message containing this string indicates that OrderModel objects
	 * are needed to be sent or read.
	 */
	static final String scOrder = "ORDER;";
	
	/**
	 * A string message containing this string indicates that Integer objects 
	 * are needed to be sent or read.
	 */
	static final String scInt = "INT;";
	
	/**
	 * A string message containing this string indicates that String objects 
	 * are needed to be sent or read.
	 */
	static final String scString = "STRING;";
	
	/**
	 * A string message containing this string indicates that an object 
	 * will be needed to be sent or read.
	 */
	static final String scObject = "OBJECT;";
	
	/**
	 * A string message containing this string indicates that objects 
	 * are needed to be removed.
	 */
	static final String scRemove = "REMOVE;";
	
	/**
	 * A string message containing this string indicates that objects 
	 * are needed to be removed.
	 */
	static final String scCheck = "CHECK;";
	
	/**
	 * A string message containing this string indicates that a request
	 * should be able to be fulfilled.
	 */	
	static final String scOkay = "OKAY;";
	
	/**
	 * A string message containing this string indicates that the entity is
	 * quitting/terminating.
	 */
	static final String scQuit = "QUIT;";
	
}