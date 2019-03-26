package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private ArrayList<Order> orders;
    private File orderFile;
    private String fileName;
    private ArrayList<Supplier> suppliers;
    private Inventory inv;
    private int orderIndex;


    public Shop (Inventory i, ArrayList<Supplier> s, ArrayList<Order> orders)
    {
        this.suppliers = s;
        this.inv = i;
        this.orders = orders;
        fileName = "orders.txt";
        orderFile = new File (fileName);
        orderIndex = -1;
    }

    public void addInvFromFile(String filename) throws IOException
    {
        inv.addItemsFromFile(filename);
        assignItemsToSupplier();
        createOrderFile();
        inventoryOrder();
    }


    public void addSuppliersFromFile(String fileName) throws IOException
    {
        FileInputStream fileIn= new FileInputStream(fileName);
        BufferedReader read = new BufferedReader(new InputStreamReader(fileIn));
        String line = read.readLine();
        while (line != null){
            Supplier a = readValues(line);
            suppliers.add(a);
            line = read.readLine();
        }
    }

    public Supplier readValues(String r)
    {
        Scanner s = new Scanner(r).useDelimiter(";");
        int id = s.nextInt();
        String companyName = s.next();
        String address = s.next();
        String salesContact = s.next();
        return new Supplier (id, companyName, address, salesContact);
    }

    public void createOrderFile() throws IOException
    {
        orderFile.createNewFile();
        PrintWriter write = new PrintWriter(fileName);
        write.close();
    }

    public void inventoryOrder()throws FileNotFoundException
    {
        boolean check = inv.checkIfOrder();
        if (check) {
            Order o = new Order (orderFile);
            orders.add(o);
            orderIndex++;
            if (orderIndex==0)
                inv.generateOrder(o, true);
            else
                inv.generateOrder(o, orderDateCheck(o));
        }
    }

    public boolean orderDateCheck(Order o)
    {
        if (o.getDateString().equals(orders.get(orderIndex-1).getDateString())){
            return false;
        }
        return true;
    }

    public int sell(int id, int quantity)throws FileNotFoundException
    {
        int check = inv.decreaseItemQuan(id, quantity);
        inventoryOrder();
        return check;
    }

    public String getItemInfo(int id)
    {
        return inv.getItemInfo(id);
    }
    public String getItemInfo(String desc)
    {
        return inv.getItemInfo(desc);
    }

    public void printInventory()
    {
        inv.printAllItems();
    }


    public void assignItemsToSupplier()
    {
        for (Supplier s: suppliers) {
            s.addItem(inv.getItems());
        }
    }

    public void closeOrderFile()throws FileNotFoundException
    {
        PrintWriter write = new PrintWriter (new FileOutputStream(orderFile, true));
        write.println("**********************************************************************");
        write.close();
    }
}
