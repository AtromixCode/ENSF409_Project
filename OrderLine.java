package com.company;

public class OrderLine {
    private Item item;
    private String orderLine;

    public OrderLine(Item i)
    {
        item = i;
        orderLine = "\r\n"+i.orderInfoItem()+ "\r\n" + i.orderInfoSupplier();
    }
    public String toString()
    {
        return orderLine;
    }
}
