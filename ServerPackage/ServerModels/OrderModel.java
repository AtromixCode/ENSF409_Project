package ServerPackage.ServerModels;

import ServerPackage.ServerModels.ItemModel;
import ServerPackage.ServerModels.OrderLineModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public OrderModel(){

    }

    public String getDateString(){return dateString;}
}
