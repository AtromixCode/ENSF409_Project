package Models;

import java.io.Serializable;

/**
 * OrderLine class.
 * Contains information about an order.
 * 
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 1, 2019
 */
public class OrderLineModel implements Serializable, Cloneable
{
	/**
	 * serialVersionUID, for serializing and deserialization
	 */
	static final long serialVersionUID = 62L;

	/**
	 * The information of the order.
	 */
	private String orderLine;
	
	/**
	 * The date of the order.
	 */
	String dateString;
	
	/**
	 * The id of the order.
	 */
	private int orderID;
	
	/**
	 * Cloneable OrderLineModel clone function
	 */
	public Object clone() throws CloneNotSupportedException
	{
		OrderLineModel temp = (OrderLineModel)super.clone();
		return temp;
	}
}
