package client.ClientModels;

import java.io.Serializable;

public class ItemModel implements Serializable, Cloneable
{	
	static final long serialVersionUID = 60L;
	
	private SupplierModel supplier;
	private int id;
	private String desc;
	private int quantity;
	private float price;
	private int supplierID;
	
	public Object clone() throws CloneNotSupportedException
	{
		ItemModel temp = (ItemModel)super.clone();
		if(temp.supplier != null) 
			temp.supplier = (SupplierModel)supplier.clone();
		return temp;
	}	
}
