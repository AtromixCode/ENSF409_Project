package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Inventory implements Constants{

    private ArrayList<Item> items;

    public Inventory()
    {
        items = new ArrayList<Item>();
    }

    public void addItemsFromFile(String fileName) throws IOException
    {
        FileInputStream fileIn= new FileInputStream(fileName);
        BufferedReader read = new BufferedReader(new InputStreamReader(fileIn));
        String line = read.readLine();
        while (line != null){
            Item a = readValues(line);
            items.add(a);
            line = read.readLine();
        }
    }

    public Item readValues(String r)
    {
        Scanner s = new Scanner(r).useDelimiter(";");
        int id = s.nextInt();
        String desc = s.next();
        int quantity = s.nextInt();
        double price = s.nextDouble();
        int supID = s.nextInt();
        return new Item (id, desc, quantity, price, supID);
    }

    public Item findItem(int id)
    {
        for (Item a: items) {
            if(a.getId()==id)
                return a;
        }
        return null;
    }

    public Item findItem(String desc)
    {
        for (Item a: items){
            if (a.getDesc().equals(desc))
                return a;
        }
        return null;
    }

    public boolean checkIfOrder()
    {
        for (Item i: items){
            if (i.getQuantity()<THRESHOLD)
                return true;
        }
        return false;
    }

    public void generateOrder(Order o, boolean stars)throws FileNotFoundException
    {
        for (Item i: items){
            if (i.getQuantity()<THRESHOLD){
                o.addLine(i);
                i.setQuantity(50);
            }
        }
        o.createOrder(stars);
    }


    public int decreaseItemQuan(int id, int quantity)
    {
        Item i = findItem(id);
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
        Item i = findItem(id);
        if (i!=null)
            return i.toString();
        else
            return "Item not found!";
    }
    public String getItemInfo(String desc)
    {
        Item i = findItem(desc);
        if (i!=null)
            return i.toString();
        else
            return "Item not found!";
    }

    public void printAllItems()
    {
        for (Item i: items)
            System.out.println(i);
    }
    public ArrayList<Item> getItems()
    {
        return items;
    }
}
