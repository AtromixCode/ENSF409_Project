package ServerPackage;

import java.util.ArrayList;

public class SupplierModel {
    private int id;
    private String companyName;
    private String address;
    private String salesContact;
    private ArrayList<ItemModel> items;

    public SupplierModel(int id, String cn, String address, String sc)
    {
        this.id = id;
        this.companyName = cn;
        this.address = address;
        this.salesContact = sc;
        items = new ArrayList<ItemModel>();
    }

    public void addItem(ArrayList<ItemModel> items)
    {
        for (ItemModel i: items){
            if(id==i.getSupplierID()) {
                this.items.add(i);
                i.setSupplier(this);
            }
        }
    }

    public String info()
    {
        return "SupplierModel:\t\t"+companyName;
    }

    public String toString()
    {
        return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
    }

}