package Models;

import java.io.Serializable;

public class OrderLineModel implements Serializable, Cloneable
{
	static final long serialVersionUID = 63L;	
	
    private ItemModel item;
    private String orderLine;

    public OrderLineModel(ItemModel i)
    {
        item = i;
        orderLine = "\r\n"+i.orderInfoItem()+ "\r\n" + i.orderInfoSupplier();
    }
    public String toString()
    {
        return orderLine;
    }
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderLineModel temp = (OrderLineModel)super.clone();
		if(temp.item != null) 
			temp.item = (ItemModel)temp.item; 
		
		return temp;
	}
}
