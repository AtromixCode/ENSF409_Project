package Models;

import java.io.Serializable;

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
	 * Supplier ID
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
	 * Cloneable SupplierModel clone() method.
	 */
	public Object clone() throws CloneNotSupportedException
	{
		SupplierModel temp = (SupplierModel)super.clone();
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

	/**
	 * Gets the id of the supplier.
	 * @return an integer containing the supplier's id.
	 */
	public int getId(){return id;}

	/**
	 * Supplier toString() displays the information of the supplier.
	 */
	@Override
	public String toString()
	{
		return "SupplierModel ID: "+ id + ", Company Name: "+ companyName +", address: "+ address +", Sales Contact: " + salesContact;
	}

	/**
	 * Gets a string containing the id and name of the supplier.
	 *
	 * @return a string holding the id and company name of a supplier.
	 */
	public String idAndName()
	{ return id + " " + companyName; }

	/**
	 * Tabbed display information of the item, using HTML for pre-formatted text.
	 * This is intended for use by the GUI.
	 *
	 * @return The pre-formatted text of the item's attributes.
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
		else if (companyName.length()>7)
		{
			return "<html><pre> "+id+"\t\t"+companyName+"\t\t\t"+address+"\t\t"+salesContact+"</pre></html>";
		}
		return "<html><pre> "+id+"\t\t"+companyName+"\t\t\t\t"+address+"\t\t"+salesContact+"</pre></html>";
	}
}
