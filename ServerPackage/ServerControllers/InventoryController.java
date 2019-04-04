package ServerPackage.ServerControllers;

import Models.ItemModel;
import Models.OrderModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryController
{

    protected ArrayList<ItemModel> items;
    private DataBaseController data;

    public InventoryController(DataBaseController dc)
    {

        data = dc;
        items = dc.itemListFromDataBase();
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



    protected void updateItem (ItemModel item){
        for (ItemModel temp : items) {
            if(temp.getId() == item.getId()){
                temp.copyAttributes(temp);
                return;
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
