package client.ClientModels;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderModel {
	private PrintWriter fileWrite;
	private File orderFile;
	private ArrayList<OrderLineModel> orderLines;
	private Date date;
	private SimpleDateFormat format;
	String dateString;
	private int orderID;
}
