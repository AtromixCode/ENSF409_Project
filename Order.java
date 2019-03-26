package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Order {
    private PrintWriter fileWrite;
    private File orderFile;
    private ArrayList<OrderLine> orderLines;
    private Date date;
    private SimpleDateFormat format;
    String dateString;
    private int orderID;

    public Order(File file)
    {
        orderFile = file;
        orderLines = new ArrayList<OrderLine>();
        date = new Date ();
        format = new SimpleDateFormat("MMMM dd, yyyy");
        dateString = format.format(date);
        orderID = (int)(Math.random() * 90000) + 10000;
    }


    public void addLine(Item item)
    {
        OrderLine ol = new OrderLine(item);
        orderLines.add(ol);
    }

    public void createOrder(boolean newLine) throws FileNotFoundException
    {
        fileWrite = new PrintWriter(new FileOutputStream(orderFile, true));
        if (newLine) {
            fileWrite.println("**********************************************************************");
            fileWrite.println("ORDER ID:\t\t" + orderID + "\r\n" +
                              "Date Ordered:\t\t" + dateString);
        }
        for (OrderLine o: orderLines)
            fileWrite.println(o);
        fileWrite.close();
    }

    public String getDateString(){return dateString;}
}
