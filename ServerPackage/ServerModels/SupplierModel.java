package ServerPackage.ServerModels;

import ServerPackage.ServerModels.ItemModel;

import java.io.Serializable;
import java.util.ArrayList;

public class SupplierModel implements Serializable, Cloneable
{
	static final long serialVersionUID = 61L;
	
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
            if(id == i.getSupplierID()) {
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
	
	public Object clone() throws CloneNotSupportedException
	{
		SupplierModel temp = (SupplierModel)super.clone();
		if(temp.items != null) 
		{
			temp.items = new ArrayList<ItemModel>();
			for(ItemModel itemInList : items)
				temp.items.add((ItemModel)itemInList.clone());
		}
		return temp;
	}

}
