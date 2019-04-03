package client.ClientControllers;

import client.ClientModels.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class FileReader {

	FileInputStream fileIn;
	BufferedReader read;

	public boolean readItemFile(String fileName, ArrayList<ItemModel> items)
	{
		try {
			fileIn = new FileInputStream(fileName);
			read = new BufferedReader(new InputStreamReader(fileIn));
			String line = read.readLine();
			while (line != null){
				ItemModel a = readItemValues(line);
				items.add(a);
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

	public boolean readSupplierFile(String fileName, ArrayList<SupplierModel> suppliers)
	{
		try {
			fileIn = new FileInputStream(fileName);
			read = new BufferedReader(new InputStreamReader(fileIn));
			String line = read.readLine();
			while (line != null){
				SupplierModel a = readSupplierValues(line);
				suppliers.add(a);
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

	public SupplierModel readSupplierValues(String r)
	{
		Scanner s = new Scanner(r).useDelimiter(";");
		int id = s.nextInt();
		String companyName = s.next();
		String address = s.next();
		String salesContact = s.next();
		return new SupplierModel(id, companyName, address, salesContact);
	}



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
