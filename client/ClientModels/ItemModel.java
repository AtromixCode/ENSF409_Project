package client.ClientModels;

public class ItemModel {
	static final long serialVersionUID = 60L;
	private SupplierModel supplier;
	private int id;
	private String desc;
	private int quantity;
	private float price;
	private int supplierID;

	public ItemModel(int id, String name, int quantity, float price, int supplierID)
	{
		this.id = id;
		this.desc = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierID = supplierID;
	}

	@Override
	public String toString() {
		return "<html><pre> "+id+"\t\t"+desc+"\t\t"+quantity+"\t\t"+price+"\t\t"+supplierID+"</pre></html>";
	}

	public Object clone() throws CloneNotSupportedException
	{
		ItemModel temp = (ItemModel)super.clone();
		if(temp.supplier != null)
			temp.supplier = (SupplierModel)supplier.clone();
		return temp;
	}
}
