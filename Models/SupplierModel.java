package Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * SupplierModel class.
 * Contains information about a supplier.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 5, 2019
 */
public class SupplierModel implements Serializable, Cloneable
{
    /**
     * Serializable SupplierModel serialVersionUID number.
     */
    static final long serialVersionUID = 61L;

    /**
     * Supplier ID.
     */
    private int id;

    /**
     * Supplier company name.
     */
    private String companyName;

    /**
     * Company address.
     */
    private String address;

    /**
     * Supplier sales contact.
     */

    private String salesContact;

    /**
     * Supplier constructor, using the attribute parameters.
     *
     * @param id The supplier id.
     * @param cn The supplier name.
     * @param address The supplier address.
     * @param sc The supplier contact name.
     */
    public SupplierModel(int id, String cn, String address, String sc)
    {
        this.id = id;
        this.companyName = cn;
        this.address = address;
        this.salesContact = sc;
    }

    /**
     * Adds an the supplier to the supplied items.
     * @param items the list of items to add the supplier to.
     */
    public void addItem(ArrayList<ItemModel> items)
    {
        for (ItemModel i: items){
            if(id == i.getSupplierID()) {
                i.setSupplier(this);
            }
        }
    }

    /**
     * Gets info of the supplier.
     * @return a string of the info of the supplier.
     */
    protected String info()
    {
        return "SupplierModel:\t\t"+companyName;
    }

    /**
     * The toString() method of the supplier.
     * @return the supplier as a string.
     */
    public String toString()
    {
        return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
    }

    /**
     * The clone function of the supplier.
     */
    public Object clone() throws CloneNotSupportedException
    {
        SupplierModel temp = (SupplierModel)super.clone();
        return temp;
    }

    /**
     * Gets the id of the supplier.
     * @return an int with the id of the supplier.
     */
    public int getId (){
        return id;
    }

    /**
     * Gets the name of the company of the supplier.
     * @return a string with the supplier company name.
     */
    public String getCompanyName (){
        return companyName;
    }

    /**
     * Gets the address of the supplier.
     * @return a string with the address of the supplier.
     */
    public String getAddress (){
        return address;
    }

    /**
     * Gets the contact for the supplier.
     * @return a string with the contact of the supplier.
     */
    public String getSalesContact (){
        return salesContact;
    }

}
