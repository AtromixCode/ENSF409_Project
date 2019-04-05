package Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OrderModel implements Serializable, Cloneable
{
    static final long serialVersionUID = 62L;


    private PrintWriter fileWrite;
    private ArrayList<OrderLineModel> orderLines;
    private Date date;
    private SimpleDateFormat format;
    private String dateString;
    private int orderID;

    public OrderModel()
    {
        orderLines = new ArrayList<OrderLineModel>();
        date = new Date ();
        format = new SimpleDateFormat("MMMM dd, yyyy");
        dateString = format.format(date);
        orderID = (int)(Math.random() * 90000) + 10000;
    }

    public OrderModel (int id, String date, ArrayList<OrderLineModel> lines){
        orderID = id;
        dateString = date;
        orderLines = lines;
    }


    /**
     *
     * TODO: I think this is a problem because you end up replacing order lines with wrong info.
     */

    public void addLine(ItemModel item)
    {
        OrderLineModel ol = new OrderLineModel(item, dateString, orderID);
        checkOrders(ol);

    }

    public void createOrder (ItemModel i, int quantity){
        OrderLineModel ol = new OrderLineModel(i, dateString, orderID);
        ol.setOrderLine(i.custumOrderInfo(quantity));
        checkOrders(ol);
    }

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

    public String getDateString(){return dateString;}

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

    public int getOrderID (){
        return orderID;
    }

    public void setOrderLines(ArrayList<OrderLineModel> orders){
        orderLines = orders;
    }

    public ArrayList <OrderLineModel> getOrderLines(){
        return orderLines;
    }

}
