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
 * @since April 5, 2019
 */
public class OrderLineModel implements Serializable, Cloneable
{
	/**
	 * serialVersionUID, for serializing and deserialization
	 */
	static final long serialVersionUID = 63L;

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

	/**
	 * Gets the date string of the order line.
	 * @return a string containing the order date.
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * Gets the order information of the order line.
	 * @return a string containing the order line.
	 */
	public String getOrderLine() {
		return orderLine;
	}

	/**
	 * Gets the id of the order line.
	 * @return the id of the order line.
	 */
	public int getOrderID() {
		return orderID;
	}
}
