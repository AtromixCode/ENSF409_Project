package Models;

import java.io.Serializable;

public class ItemModel implements Serializable, Cloneable {

	static final long serialVersionUID = 1L;
	private SupplierModel supplier;
	private int id;
	private String desc;
	private int quantity;
	private float price;
	private int supplierID;

	public ItemModel(int id, String name, int quantity, float price, SupplierModel supplier)
	{
		this.id = id;
		this.desc = name;
		this.quantity = quantity;
		this.price = price;
		this.supplier = supplier;
		this.supplierID = supplier.getId();
	}

	public ItemModel(int id, String name, int quantity, float price, int supplierId)
	{
		this.id = id;
		this.desc = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierID = supplierId;
	}


	public String displayString() {
		if (desc.length()>7)
		{
			return "<html><pre> "+id+"\t\t"+desc+"\t"+quantity+"\t\t"+price+"\t\t"+supplierID+"</pre></html>";
		}
		return "<html><pre> "+id+"\t\t"+desc+"\t\t"+quantity+"\t\t"+price+"\t\t"+supplierID+"</pre></html>";
	}

	@Override
	public String toString() {
		return "ItemModel Name: " +desc+ ", Quantity: " +quantity+ ", Price " +price+ ", ItemModel ID: "+id+", SupplierModel ID: "+supplierID+ ".\n";
	}

	public Object clone() throws CloneNotSupportedException
	{
		ItemModel temp = (ItemModel)super.clone();
		if(temp.supplier != null)
			temp.supplier = (SupplierModel)supplier.clone();
		return temp;
	}
}
