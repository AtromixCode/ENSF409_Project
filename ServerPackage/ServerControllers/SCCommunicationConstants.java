package ServerPackage.ServerControllers;

/**
 * Contains constant String variables for server-client 
 * message communication.
 * Messages take the format of: Opening ArgDegree ArgType ArgExtra
 * Messages are from the perspective of the server.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 1, 2019
 */
public interface SCCommunicationConstants 
{
	/**
	 * A string message containing this string indicates that an error has occurred
	 * when attempting to respond to a query by sending data.
	 */
	static final String scError = "ERROR;";
	
	/**
	 * String message Opening, that indicates that data will be sent over to the socket. 
	 */
	static final String scSend = "SEND;";
	
	/**
	 * String message Opening, that indicates that data in the reader will change, and new data may or may not be added.
	 * If necessary, data will overwrite data in the reader if data with the same identity attributes exists.
	 */
	static final String scUpdate = "UPDATE;";
	
	/**
	 * String message Opening, that indicates that data will be received from the socket.
	 * New data will be created in the reader based on the received data.
	 */
	static final String scCreate = "CREATE;";
	
	/**
	 * String message Opening, that indicates that data will be received from the socket.
	 * Data in the reader will be deleted based on the received data.
	 */
	static final String scDelete = "DELETE;";
	
	/**
	 * String message Opening, that indicates that data in the reader needs to be checked to
	 * verify if data in the sender matches the reader's data.
	 */
	static final String scVerify = "VERIFY;";
	
	
	
	/**
	 * String message ArgDegree indicates that a singular object will be needed to be operated on.
	 */
	static final String scSingle = "SINGLE;";
	
	/**
	 * String message ArgDegree indicates that a list of objects will be needed to be operated on.
	 */
	static final String scList = "LIST;";
	
	
	
	/**
	 * String message ArgType indicates that ItemModel objects will need to be operated on.
	 */
	static final String scItem = "ITEM;";
	
	/**
	 * String message ArgType indicates that SupplierModel objects will need to be operated on.
	 */
	static final String scSupplier = "SUPPLIER;";
	
	/**
	 * String message ArgType indicates that OrderLineModel objects will need to be operated on.
	 */
	static final String scOrder = "ORDER;";
	
	
	
	/**
	 * String message ArgExtra indicates that the only data items to be read from the socket
	 * are user-defined objects.
	 */
	static final String scDefault = "DEFAULT;";
	
	/**
	 * String message ArgExtra indicates that there is an int argument sent over in the opening message.
	 */
	static final String scInt = "INT;";
	
	/**
	 * String message ArgExtra indicates that there is a String argument sent over in the opening message.
	 */
	static final String scString = "STRING;";
	
	/**
	 * String message ArgExtra indicates that there are no data items or extra arguments to be read at all.
	 */
	static final String scPrompt = "PROMPT;";
	
	
	
	
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