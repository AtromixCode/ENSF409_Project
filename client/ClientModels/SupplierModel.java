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

	public SupplierModel(int id, String cName, String address, String sContact)
	{
		this.id = id;
		this.companyName = cName;
		this.address = address;
		this.salesContact = sContact;
	}

	@Override
	public String toString(){
		return "<html><pre> "+id+"\t\t"+companyName+"\t\t"+address+"\t\t"+salesContact+"</pre></html>";
	}
}
