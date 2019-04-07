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
public class ItemModel implements Serializable, Cloneable{

    /**
     * Serializable ID.
     */
	static final long serialVersionUID = 1L;

    /**
     * The supplier of the item.
     */
	private transient SupplierModel supplier;

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
     * @param desc the name of the item.
     * @param quantity the quantity of the item.
     * @param price the price of the item.
     * @param supplierID the id of the supplier
     */
    public ItemModel(int id, String desc, int quantity, float price, int supplierID)
    {
        this.id = id;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
    }

    /**
     * Sets the supplier for the item.
     * @param s supplier to supply the item.
     */
    public void setSupplier(SupplierModel s)
    {
        supplier = s;
    }

    /**
     * Item toString() method displays the attributes of the item.
     */
    @Override
    public String toString()
    {
        return "In shop. ItemModel Name: " +desc+ ", Quantity: " +quantity+ ", Price " +price+ ", ItemModel ID: "+id+", SupplierModel ID: "+supplierID+ ".";
    }

    /**
     * Generates the default order info for the item.
     * @return the info for the default order.
     */
    public String orderInfoItem()
    {
        return "ItemModel description:\t"+ desc +"\r\n" +
                "Amount ordered:\t\t"+(50-quantity);
    }

    /**
     * Generates the string for a custom order with a certain quantity.
     * @param quantity the quantity to order.
     * @return the generated string to order.
     */
    public String customOrderInfo(int quantity){
        return "ItemModel description:\t" + desc + "\r\n" +
                "Amount ordered:\t\t" + quantity+"\r\n" + orderInfoSupplier();
    }

    /**
     * Get the info for the supplier.
     * @return the items supplier info.
     */
    public String orderInfoSupplier()
    {
        return supplier.info();
    }

    /**
     * Gets the id of the item.
     * @return an integer containing the item id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the item.
     * @return a string containing an item description.
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Gets the quantity of the item.
     * @return an integer containing the item quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     * @param quantity the new quantity of the item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the item's supplier id .
     * @return an integer that is the supplier id for the item.
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * Copies the attributes of a given item into the current item.
     * @param item the item to copy the parameters from.
     */
    public void copyAttributes (ItemModel item){
        supplier = item.supplier;
        desc = item.desc;
        quantity = item.quantity;
        price = item.price;
        supplierID = item.supplierID;
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

    /**
     * Gets the price of the item.
     * @return a float with the price of the item.
     */
    public float getPrice(){
        return price;
    }

    /**
     * Gets the supplier for the item.
     * @return the supplier for the item.
     */
    public SupplierModel getSupplier (){
        return supplier;
    }
}
