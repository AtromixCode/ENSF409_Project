package client.ClientModels;

import java.io.Serializable;
import java.util.ArrayList;

public class SupplierModel implements Serializable, Cloneable
{	
	static final long serialVersionUID = 61L;
	
	private int id;
	private String companyName;
	private String address;
	private String salesContact;
	private ArrayList<ItemModel> items;
	
	public Object clone() throws CloneNotSupportedException
	{
		SupplierModel temp = (SupplierModel)super.clone();
		if(temp.items != null) 
		{
			temp.items = new ArrayList<ItemModel>();
			for(ItemModel itemInList : items)
				temp.items.add((ItemModel)itemInList.clone());
		}
		return temp;
	}
}
