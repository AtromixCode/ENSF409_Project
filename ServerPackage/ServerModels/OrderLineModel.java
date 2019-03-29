package ServerPackage.ServerModels;

public class OrderLineModel {
    private ItemModel item;
    private String orderLine;

    public OrderLineModel(ItemModel i)
    {
        item = i;
        orderLine = "\r\n"+i.orderInfoItem()+ "\r\n" + i.orderInfoSupplier();
    }
    public String toString()
    {
        return orderLine;
    }
}
