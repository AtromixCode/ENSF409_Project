package ServerPackage;

public class ItemModel {
    private SupplierModel supplier;
    private int id;
    private String desc;
    private int quantity;
    private double price;
    private int supplierID;

    public ItemModel(int id, String desc, int quantity, double price, int supplierID)
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

    public String toString()
    {
        return "ItemModel Name: " +desc+ ", Quantity: " +quantity+ ", Price " +price+ ", ItemModel ID: "+id+", SupplierModel ID: "+supplierID+ ".\n";
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
}
