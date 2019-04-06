package client.ClientControllers;

import Models.ItemModel;
import Models.SupplierModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads files using given filenames, reading their text contents into
 * Items and Suppliers and into given lists.
 * 
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
 */
public class FileReader {

	/**
	 * File input stream to read files.
	 */
	FileInputStream fileIn;
	
	/**
	 * BufferReader input stream to read from the FileInputStream
	 */
	BufferedReader read;

	/**
	 * Using a given file name, reads a file and converts its contents into items,
	 * adding them to the given list of items. Checks to see if item already exists.
	 * (Note: no clearing or overwriting)
	 * 
	 * @param fileName The filename of the file to be read.
	 * @param items The list of ItemModel to add the file items to.
	 * @return true if the operation was successful, false if not.
	 */
	public boolean readItemFile(String fileName, ArrayList<ItemModel> items)
	{
		try {
			fileIn = new FileInputStream(fileName);
			read = new BufferedReader(new InputStreamReader(fileIn));
			String line = read.readLine();
			while (line != null){
				ItemModel a = readItemValues(line);
				boolean check = false;
				for (ItemModel i:items)
				{
					if (i.getId()==a.getId())
					{
						check = true;
					}
				}
				if (check == false) {
					items.add(a);
				}
				line = read.readLine();
			}
			return true;

		} catch (IOException e) {
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Using a given file name, reads a file and converts its contents into suppliers,
	 * adding them to the given list of suppliers. Checks to see if supplier already exists.
	 * (Note: no clearing or overwriting)
	 * 
	 * @param fileName The filename of the file to be read.
	 * @param suppliers list of SupplierModel to add the file items to.
	 * @return true if the operation was successful, false if not.
	 */
	public boolean readSupplierFile(String fileName, ArrayList<SupplierModel> suppliers)
	{
		try {
			fileIn = new FileInputStream(fileName);
			read = new BufferedReader(new InputStreamReader(fileIn));
			String line = read.readLine();
			while (line != null){
				SupplierModel a = readSupplierValues(line);
				boolean check = false;
				for (SupplierModel s:suppliers)
				{
					if (s.getId()==a.getId())
					{
						check = true;
					}
				}
				if (check == false) {
					suppliers.add(a);
				}
				line = read.readLine();
			}
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Converts a string into a SupplierModel.
	 * SupplierModel attributes are separated by ';'
	 * 
	 * @param r The String to convert into a supplier. 
	 * @return The supplier version of the string.
	 */
	public SupplierModel readSupplierValues(String r)
	{
		Scanner s = new Scanner(r).useDelimiter(";");
		int id = s.nextInt();
		String companyName = s.next();
		String address = s.next();
		String salesContact = s.next();
		return new SupplierModel(id, companyName, address, salesContact);
	}

	/**
	 * Converts a string into a ItemModel.
	 * ItemModel attributes are separated by ';'
	 * 
	 * @param r The String to convert into an item. 
	 * @return The item version of the string.
	 */
	public ItemModel readItemValues(String r)
	{
		Scanner s = new Scanner(r).useDelimiter(";");
		int id = s.nextInt();
		String desc = s.next();
		int quantity = s.nextInt();
		float price = s.nextFloat();
		int supID = s.nextInt();
		return new ItemModel (id, desc, quantity, price, supID);
	}
}
