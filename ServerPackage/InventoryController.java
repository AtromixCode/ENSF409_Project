package ServerPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryController implements Constants{

    private ArrayList<ItemModel> items;

    public InventoryController()
    {
        items = new ArrayList<ItemModel>();
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
        double price = s.nextDouble();
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
            if (i.getQuantity()<THRESHOLD)
                return true;
        }
        return false;
    }

    public void generateOrder(OrderModel o, boolean stars)throws FileNotFoundException
    {
        for (ItemModel i: items){
            if (i.getQuantity()<THRESHOLD){
                o.addLine(i);
                i.setQuantity(50);
            }
        }
        o.createOrder(stars);
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

    public void printAllItems()
    {
        for (ItemModel i: items)
            System.out.println(i);
    }
    public ArrayList<ItemModel> getItems()
    {
        return items;
    }
}
