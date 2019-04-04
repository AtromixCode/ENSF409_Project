package Models;

import java.io.Serializable;

public class ItemModel implements Serializable, Cloneable{

	static final long serialVersionUID = 1L;

	private SupplierModel supplier;
    private int id;
    private String desc;
    private int quantity;
    private float price;
    private int supplierID;

    public ItemModel(int id, String desc, int quantity, float price, int supplierID)
    {
        this.id = id;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
    }

    public void setSupplier(SupplierModel s)
    {
        supplier=s;
    }

    @Override
    public String toString()
    {
        return "In shop. ItemModel Name: " +desc+ ", Quantity: " +quantity+ ", Price " +price+ ", ItemModel ID: "+id+", SupplierModel ID: "+supplierID+ ".\n";
    }

    public String orderInfoItem()
    {
        return "ItemModel description:\t"+ desc +"\r\n" +
                "Amount ordered:\t\t"+(50-quantity);
    }

    public String orderInfoSupplier()
    {
        return supplier.info();
    }


    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierID() {
        return supplierID;
    }

	public Object clone() throws CloneNotSupportedException
	{
		ItemModel temp = (ItemModel)super.clone();
		if(temp.supplier != null)
			temp.supplier = (SupplierModel)supplier.clone();
		return temp;
	}
}
