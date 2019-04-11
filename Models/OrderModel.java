package Models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Order class.
 * controls the all the orders
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 5, 2019
 */
public class OrderModel implements Serializable, Cloneable
{
    /**
     * SerialVersionUID, for serializing and deserialization.
     */
    static final long serialVersionUID = 62L;

    /**
     * List of orders.
     */
    private ArrayList<OrderLineModel> orderLines;

    /**
     * Date object to use current date.
     */
    private Date date;

    /**
     * Format of the date.
     */
    private SimpleDateFormat format;

    /**
     * The date in a string.
     */
    private String dateString;

    /**
     * The id of the order.
     */
    private int orderID;

    /**
     * Constructs an order with an empty order list and generates a date and id.
     */
    public OrderModel()
    {
        orderLines = new ArrayList<OrderLineModel>();
        date = new Date ();
        format = new SimpleDateFormat("MMMM dd, yyyy");
        dateString = format.format(date);
        orderID = (int)(Math.random() * 90000) + 10000;
    }

    /**
     * Adds a line to the list of order lines.
     * @param item the item to create the list around.
     */
    public void addLine(ItemModel item)
    {
        generateDate();
        generateID();
        OrderLineModel ol = new OrderLineModel(item, dateString, orderID);
        checkOrders(ol);
    }

    /**
     * Generates a date and formats it into the date string.
     */
    protected void generateDate()
    {
        date = new Date ();
        format = new SimpleDateFormat("MMMM dd, yyyy");
        dateString = format.format(date);
    }

    /**
     * Generates an id if the date is different from the previous order.
     */
    protected void generateID()
    {
        if (orderLines.isEmpty())
        {
            return;
        }
        if(dateString.equals(orderLines.get(orderLines.size()-1).getDateString()))
        {
            return;
        }
        orderID = (int)(Math.random() * 90000) + 10000;
    }

    /**
     * Creates an order of an item with the given quantity.
     * @param i the item to create the order of.
     * @param quantity the amount to order.
     */
    public OrderLineModel createOrder (ItemModel i, int quantity){
        generateDate();
        generateID();
        OrderLineModel ol = new OrderLineModel(i, dateString, orderID);
        ol.setOrderLine(i.customOrderInfo(quantity));
        checkOrders(ol);
        return ol;
    }

    /**
     * Checks if there is an order with the same date and uses
     * that order parameters if there is and adds it to the list.
     * @param ol the order line to check.
     */
    private void checkOrders(OrderLineModel ol){
        if(!orderLines.isEmpty()) {
            for (OrderLineModel temp : orderLines) {
                if (temp.getDateString().equals(dateString)) {
                    ol.copyAttributes(temp);
                    break;
                }
            }
        }
        orderLines.add(ol);
    }


    /**
     * Clones the order model.
     * @return the clone of the order model.
     * @throws CloneNotSupportedException if it doesn't support cloning.
     */
    public Object clone() throws CloneNotSupportedException
    {
        OrderModel temp = (OrderModel)super.clone();
        if(temp.orderLines != null)
        {
            temp.orderLines = new ArrayList<OrderLineModel>();
            for(OrderLineModel orderLineInList : orderLines)
                temp.orderLines.add((OrderLineModel)orderLineInList.clone());
        }

        if(temp.date != null)
            temp.date = (Date)date.clone();

        if(temp.format != null)
            temp.format = (SimpleDateFormat)format.clone();

        return temp;
    }

    /**
     * Sets the order lines to a given list or order lines.
     * @param orders the lines to set.
     */
    public void setOrderLines(ArrayList<OrderLineModel> orders){
        orderLines = orders;
    }

    /**
     * Gets the order lines.
     * @return the order lines.
     */
    public ArrayList <OrderLineModel> getOrderLines(){
        return orderLines;
    }

}
