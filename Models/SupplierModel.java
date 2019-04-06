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
 * @since April 1, 2019
 */
public class SupplierModel implements Serializable, Cloneable
{
    /**
     * Serializable SupplierModel serialVersionUID number.
     */
    static final long serialVersionUID = 61L;

    /**
     * Supplier ID
     */
    private int id;

    /**
     * Supplier company name.
     */
    private String companyName;

    /**
     * Company adress.
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
     * adds an the supplier to the suppliable items
     * @param items the list of items to add the supplier to
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
     * gets info of the supplier
     * @return a string of the info of the supplier
     */
    protected String info()
    {
        return "SupplierModel:\t\t"+companyName;
    }

    /**
     * the to string method of the supplier
     * @return the supplier as a string
     */
    public String toString()
    {
        return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
    }

    /**
     * the clone function of the supplier
     */
    public Object clone() throws CloneNotSupportedException
    {
        SupplierModel temp = (SupplierModel)super.clone();

        return temp;
    }

    /**
     * gets the id of the supplier
     * @return an int with the id of the supplier
     */
    public int getId (){
        return id;
    }

    /**
     * gets the name of the company of the supplier
     * @return a string with the supplier company name
     */
    public String getCompanyName (){
        return companyName;
    }

    /**
     * gets the address of the supplier
     * @return a string with the address of the supplier
     */
    public String getAddress (){
        return address;
    }

    /**
     * gets the contact for the supplier
     * @return a string with the contact of the supplier
     */
    public String getSalesContact (){
        return salesContact;
    }

}
