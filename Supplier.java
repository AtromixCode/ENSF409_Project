package com.company;

import java.util.ArrayList;

public class Supplier {
    private int id;
    private String companyName;
    private String address;
    private String salesContact;
    private ArrayList<Item> items;

    public Supplier(int id, String cn, String address, String sc)
    {
        this.id = id;
        this.companyName = cn;
        this.address = address;
        this.salesContact = sc;
        items = new ArrayList<Item>();
    }

    public void addItem(ArrayList<Item> items)
    {
        for (Item i: items){
            if(id==i.getSupplierID()) {
                this.items.add(i);
                i.assignSupplier(this);
            }
        }
    }

    public String info()
    {
        return "Supplier:\t\t"+companyName;
    }

    public String toString()
    {
        return "Supplier ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
    }

}
