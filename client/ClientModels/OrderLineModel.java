package client.ClientModels;

import java.io.Serializable;

public class OrderLineModel implements Serializable, Cloneable
{
	static final long serialVersionUID = 63L;		
	
	private ItemModel item;
	private String orderLine;
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderLineModel temp = (OrderLineModel)super.clone();
		if(temp.item != null) 
			temp.item = (ItemModel)temp.item; 
		
		return temp;
	}
}
