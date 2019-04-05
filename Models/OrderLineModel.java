package Models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderLineModel implements Serializable, Cloneable
{
    static final long serialVersionUID = 63L;

    private transient ItemModel item;
    private String orderLine;
    private transient Date date;
    private transient SimpleDateFormat format;
    private String dateString;
    private int orderID;

    public OrderLineModel(ItemModel i, String dateS, int id)
    {
        item = i;
        dateString = dateS;
        orderID = id;
        orderLine = "\r\n"+i.orderInfoItem()+ "\r\n" + i.orderInfoSupplier();
    }

    protected void copyAttributes (OrderLineModel ol){
        dateString = ol.dateString;
        orderID = ol.orderID;
    }

    public OrderLineModel (int id, String date, String line){
        dateString = date;
        orderID = id;
        orderLine = line;
    }
    public String toString()
    {
        return orderLine;
    }

    public Object clone() throws CloneNotSupportedException
    {
        OrderLineModel temp = (OrderLineModel)super.clone();
        if(temp.item != null)
            temp.item = (ItemModel)temp.item;

        return temp;
    }

    public String getOrderLine (){
        return orderLine;
    }

    public int getOrderID (){
        return orderID;
    }

    public String getDateString (){
        return dateString;
    }
}
