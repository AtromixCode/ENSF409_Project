package client.ClientModels;

import java.io.Serializable;

public class OrderLineModel implements Serializable, Cloneable
{
	static final long serialVersionUID = 62L;

	private String orderLine;
	String dateString;
	private int orderID;
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderLineModel temp = (OrderLineModel)super.clone();
		return temp;
	}
}
