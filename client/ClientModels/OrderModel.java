package client.ClientModels;

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
    private File orderFile;
    private ArrayList<OrderLineModel> orderLines;
    private Date date;
    private SimpleDateFormat format;
    String dateString;
    private int orderID;
	
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
}
