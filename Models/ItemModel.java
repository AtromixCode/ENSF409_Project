package Models;

import java.io.Serializable;

/**
 * Item class.
 * Contains information about the name, quantity, price, and supplier of the item.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 5, 2019
 */
public class ItemModel implements Serializable, Cloneable {

	/**
	 * Serializable ID.
	 */
	static final long serialVersionUID = 1L;

	/**
	 * The supplier of the item.
	 */
	private SupplierModel supplier;

	/**
	 * The id of the item.
	 */
	private int id;

	/**
	 * The name or description of the item.
	 */
	private String desc;

	/**
	 * The quantity of the item in a stock.
	 */
	private int quantity;

	/**
	 * The price of the item. (CAD)
	 */
	private float price;

	/**
	 * The id of the supplier of the item.
	 */
	private int supplierID;


	/**
	 * Item Model constructor using the given attribute parameters.
	 *
	 * @param id the id of the item.
	 * @param name the name of the item.
	 * @param quantity the quantity of the item.
	 * @param price the price of the item.
	 * @param supplier the supplier for the item.
	 */
	public ItemModel(int id, String name, int quantity, float price, SupplierModel supplier)
	{
		this.id = id;
		this.desc = name;
		this.quantity = quantity;
		this.price = price;
		this.supplier = supplier;
		this.supplierID = supplier.getId();
	}

	/**
	 * Item constructor, using the given attribute parameters.
	 *
	 * @param id The id of the item.
	 * @param name The name of the item.
	 * @param quantity The amount of the item in a stock.
	 * @param price The price of the item.
	 * @param supplierId The supplier ID of the item.
	 */
	public ItemModel(int id, String name, int quantity, float price, int supplierId)
	{
		this.id = id;
		this.desc = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierID = supplierId;
	}


	/**
	 * Tabbed display information of the item, using HTML for preformatted text.
	 * This is intended for use by the GUI.
	 *
	 * @return The preformatted text of the item's attributes.
	 */
	public String displayString() {
		if (desc.length()>7)
		{
			return "<html><pre> "+id+"\t\t"+desc+"\t"+quantity+"\t\t"+price+"\t\t"+supplierID+"</pre></html>";
		}
		return "<html><pre> "+id+"\t\t"+desc+"\t\t"+quantity+"\t\t"+price+"\t\t"+supplierID+"</pre></html>";
	}

	public String idAndName()
	{
		return id + " " + desc;
	}

	/**
	 * Gets the id of the item.
	 * @return an integer containing the item id.
	 */
	public int getId(){return id;}

    /**
     * Gets the name of the item.
     * @return a string containing an item description.
     */
    public String getDesc() { return desc; }
	
	/**
	 * Gets the quantity of the item.
	 * @return an integer containing the item quantity.
	 */
	public int getQuantity(){return quantity;}

	/**
	 * Sets the quanitity of the item.
	 * @param q the new quantity of the item.
	 */
	public void setQuantity(int q){this.quantity = q;}

	/**
	 * Gets the item's supplier id .
	 * @return an integer that is the supplier id for the item.
	 */
	public int getSupplierID(){return supplierID;}

	/**
	 * Item toString() method displays the attributes of the item.
	 */
	@Override
	public String toString() {
		return "ItemModel Name: " + desc + ", Quantity: " + quantity + ", Price " + price + ", ItemModel ID: "+ id +", SupplierModel ID: "+ supplierID + ".\n";
	}

	/**
	 * Cloneable ItemModel clone method.
	 */
	public Object clone() throws CloneNotSupportedException
	{
		ItemModel temp = (ItemModel)super.clone();
		if(temp.supplier != null)
			temp.supplier = (SupplierModel)supplier.clone();
		return temp;
	}
}
