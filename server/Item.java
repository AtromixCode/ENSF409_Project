package server;

public class Item {
    private Supplier supplier;
    private int id;
    private String desc;
    private int quantity;
    private double price;
    private int supplierID;

    public Item(int id, String desc, int quantity, double price, int supplierID)
    {
        this.id = id;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
    }

    public void setSupplier(Supplier s)
    {
        supplier=s;
    }

    public String toString()
    {
        return "Item Name: " +desc+ ", Quantity: " +quantity+ ", Price " +price+ ", Item ID: "+id+", Supplier ID: "+supplierID+ ".\n";
    }

    public String orderInfoItem()
    {
        return "Item description:\t"+ desc +"\r\n" +
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
