package Models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OrderLine class.
 * Contains information about an order.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 1, 2019
 */
public class OrderLineModel implements Serializable, Cloneable
{
    /**
     * serialVersionUID, for serializing and deserialization
     */
    static final long serialVersionUID = 63L;

    /**
     * The item to be ordered
     */
    private transient ItemModel item;

    /**
     * The information of the order.
     */
    private String orderLine;

    /**
     * The date of the order.
     */
    private String dateString;

    /**
     * The id of the order.
     */
    private int orderID;

    public OrderLineModel(ItemModel i, String dateS, int id)
    {
        item = i;
        dateString = dateS;
        orderID = id;
        orderLine = "\r\n"+i.orderInfoItem()+ "\r\n" + i.orderInfoSupplier();
    }

    /**
     * copies the attributes of a given order
     * @param ol the order model to copy attributes from
     */
    protected void copyAttributes (OrderLineModel ol){
        dateString = ol.dateString;
        orderID = ol.orderID;
    }

    /**
     * constructs an order with a given id, date and line
     * @param id the id of the order
     * @param date the date of the order
     * @param line the description of the order
     */
    public OrderLineModel (int id, String date, String line){
        dateString = date;
        orderID = id;
        orderLine = line;
    }

    /**
     * the to string method of the orderline function
     * @return the orderline as a string
     */
    @Override
    public String toString()
    {
        return orderLine;
    }

    /**
     * Cloneable OrderLineModel clone function
     */
    public Object clone() throws CloneNotSupportedException
    {
        OrderLineModel temp = (OrderLineModel)super.clone();
        if(temp.item != null)
            temp.item = (ItemModel)temp.item;

        return temp;
    }

    /**
     * Gets the order information of the order line.
     * @return a string containing the order line.
     */
    public String getOrderLine (){
        return orderLine;
    }

    /**
     * Gets the id of the order line.
     * @return the id of the order line.
     */
    public int getOrderID (){
        return orderID;
    }

    /**
     * gets the date of the order
     * @return the date of the order in a string
     */
    public String getDateString (){
        return dateString;
    }

    /**
     * sets the line of the order
     * @param line the description of the order
     */
    public void setOrderLine (String line){
        orderLine = line;
    }
}
