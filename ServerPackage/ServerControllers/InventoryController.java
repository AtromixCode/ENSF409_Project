package ServerPackage.ServerControllers;

import Models.ItemModel;
import Models.OrderModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryController
{

    private ArrayList<ItemModel> items;
    private DataBaseController data;

    public InventoryController(DataBaseController dc)
    {
        items = new ArrayList<ItemModel>();
        data = dc;
    }

    public void addItemsFromFile(String fileName) throws IOException
    {
        FileInputStream fileIn= new FileInputStream(fileName);
        BufferedReader read = new BufferedReader(new InputStreamReader(fileIn));
        String line = read.readLine();
        while (line != null){
            ItemModel a = readValues(line);
            items.add(a);
            line = read.readLine();
        }
    }

    public ItemModel readValues(String r)
    {
        Scanner s = new Scanner(r).useDelimiter(";");
        int id = s.nextInt();
        String desc = s.next();
        int quantity = s.nextInt();
        float price = s.nextFloat();
        int supID = s.nextInt();
        return new ItemModel(id, desc, quantity, price, supID);
    }

    public ItemModel findItem(int id)
    {
        for (ItemModel a: items) {
            if(a.getId()==id)
                return a;
        }
        return null;
    }

    public ItemModel findItem(String desc)
    {
        for (ItemModel a: items){
            if (a.getDesc().equals(desc))
                return a;
        }
        return null;
    }

    public boolean checkIfOrder()
    {
        for (ItemModel i: items){
            if (i.getQuantity()<40)
                return true;
        }
        return false;
    }

    public void generateOrder(OrderModel o)
    {
        for (ItemModel i: items){
            if (i.getQuantity()<40){
                data.addOrderLine(o.addLine(i));
                i.setQuantity(50);
                data.addItem(i);
            }
        }
    }


    public int decreaseItemQuan(int id, int quantity)
    {
        ItemModel i = findItem(id);
        if (i!=null) {
            if (i.getQuantity() >= quantity) {
                i.setQuantity(i.getQuantity() - quantity);
                return 1;
            } else
                return 3;
        }
        return 2;
    }


    public String getItemInfo(int id)
    {
        ItemModel i = findItem(id);
        if (i!=null)
            return i.toString();
        else
            return "ItemModel not found!";
    }
    public String getItemInfo(String desc)
    {
        ItemModel i = findItem(desc);
        if (i!=null)
            return i.toString();
        else
            return "ItemModel not found!";
    }

    protected void updateItem (ItemModel item){
        for (ItemModel temp : items) {
            if(temp.getId() == item.getId()){
                temp.copyAttributes(temp);
            }
        }
    }

    public void printAllItems()
    {
        for (ItemModel i: items)
            System.out.println(i);
    }

    protected void removeItem (ItemModel item){
        for (ItemModel temp :items) {
            if(temp.getId() == item.getId()){
                items.remove(temp);
                return;
            }
        }
    }
    protected ArrayList<ItemModel> getItems()
    {
        return items;
    }

    protected void updateItemList (ArrayList<ItemModel> listItem){
        items = listItem;
    }
}
