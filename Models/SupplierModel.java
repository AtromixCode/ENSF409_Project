package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SupplierModel implements Serializable, Cloneable
{
    static final long serialVersionUID = 61L;

    private int id;
    private String companyName;
    private String address;
    private String salesContact;

    public SupplierModel(int id, String cn, String address, String sc)
    {
        this.id = id;
        this.companyName = cn;
        this.address = address;
        this.salesContact = sc;
    }

    public void addItem(ArrayList<ItemModel> items)
    {
        for (ItemModel i: items){
            if(id == i.getSupplierID()) {
                i.setSupplier(this);
            }
        }
    }

    protected String info()
    {
        return "SupplierModel:\t\t"+companyName;
    }

    public String toString()
    {
        return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
    }

    public Object clone() throws CloneNotSupportedException
    {
        SupplierModel temp = (SupplierModel)super.clone();

        return temp;
    }

    public int getId (){
        return id;
    }

    public String getCompanyName (){
        return companyName;
    }

    public String getAddress (){
        return address;
    }

    public String getSalesContact (){
        return salesContact;
    }

}
