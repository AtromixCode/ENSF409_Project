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
	 * List of the items that the supplier supplies.
	 */
	private ArrayList<ItemModel> items;

	/**
	 * Cloneable SupplierModel clone() method.
	 */
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

	/**
	 * Supplier constructor, using the attribute parameters.
	 *
	 * @param id The supplier id.
	 * @param cName The supplier name.
	 * @param address The supplier address.
	 * @param sContact The supplier contact name.
	 */
	public SupplierModel(int id, String cName, String address, String sContact)
	{
		this.id = id;
		this.companyName = cName;
		this.address = address;
		this.salesContact = sContact;
	}

	public int getId(){return id;}


	/**
	 * Supplier toString() displays the information of the supplier.
	 */
	@Override
	public String toString()
	{
		return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
	}

	public String idAndName()
	{
		return id + " " + companyName;
	}

	/**
	 * Tabbed display information of the item, using HTML for preformatted text.
	 * This is intended for use by the GUI.
	 *
	 * @return The preformatted text of the item's attributes.
	 */
	public String displayString(){
		if(companyName.length()>23)
		{
			return "<html><pre> "+id+"\t\t"+companyName+"\t"+address+"\t\t"+salesContact+"</pre></html>";
		}
		else if (companyName.length()>15)
		{
			return "<html><pre> "+id+"\t\t"+companyName+"\t\t"+address+"\t\t"+salesContact+"</pre></html>";
		}
		return "<html><pre> "+id+"\t\t"+companyName+"\t\t\t"+address+"\t\t"+salesContact+"</pre></html>";
	}
}
