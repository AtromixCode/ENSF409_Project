package client.ClientViews;

import client.ClientControllers.ClientController;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

/**
 * GUI controller manages the GUI.
 * Client-server interaction is prompted by events.
 * 
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since March 29, 2019
 */
class GUIController {

	/**
	 * Main view is always seen.
	 */
	private MainView mv;
	
	/**
	 * Supplier list view.
	 */
	private SupplierView sv;
	
	/**
	 * Order list view.
	 */
	private OrderView ov;
	
	/**
	 * Client to communicate with the server.
	 */
	private ClientController cc;

	/**
	 * Constructor, using a given client.
	 * Creates the views of the GUI.
	 * 
	 * @param controller
	 */
	protected GUIController (ClientController controller){
		mv = new MainView();
		sv = new SupplierView();
		ov = new OrderView();
		cc = controller;
		mv.addListSelectionListener(new SelectItem());
		mv.addButton5ActionListener(new ViewOrders());
		mv.addButton6ActionListener(new ViewSuppliers());
		mv.addButton7ActionListener(new ImportItems());
		mv.addButton8ActionListener(new ImportSuppliers());
	}

	/**
	 * inner ListSelectionListener class handles objects that have been selected by the user.
	 * involves actions that require at least one object/item to be present.
	 */
	public class SelectItem implements ListSelectionListener {
		
		/**
		 * The String representation of the selected object's data/attributes.
		 */
		String data;
		
		/**
		 * The id of the object.
		 */
		int id;
		
		/**
		 * default constructor assigns the actions listeners to the 3 buttons
		 * dependent on there being at least one item to select.
		 */
		protected SelectItem()
		{
			mv.addButton1ActionListener(new SellItem());
			mv.addButton2ActionListener(new OrderItem());
			mv.addButton3ActionListener(new RemoveItem());
		}
		
		/**
		 * Called whenever there is a change in selection.
		 * Gets the information of the selected item.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int index = mv.getItemList().getSelectedIndex();
			if (index >= 0) {
				data = (String) mv.getItemListModel().get(index);
				mv.setButtonsClickable(true);
				Scanner scan = new Scanner (data);
				scan.next();
				id = scan.nextInt();
			}
		}

		/**
		 * inner ActionListener class listens for the "Sell Item" button
		 * to be pressed, whereupon a dialogue box asking for a specified amount
		 * will appear.
		 */
		public class SellItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity sold:"));
					if (quantity>0)
					{
						System.out.println("Checking if "+ quantity + " is enough and removing it here!");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		/**
		 * inner ActionListener class listens for the "Order Item" button
		 * to be pressed, whereupon a dialogue box asking for a specified amount
		 * to order will appear.
		 */
		public class OrderItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity to order:"));
					if (quantity>0)
					{
						System.out.println("Ordering "+ quantity +" here!");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		/**
		 * inner ActionListener class listens for the "Remove Item" button
		 * to be pressed, whereupon a dialogue box asking for confirmation will appear.
		 */
		public class RemoveItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null,
						"Please confirm you would like to remove Item #"+id+" from the store.");
				if (a==0)
				{
					System.out.println("Removing item here!");
				}
			}
		}
	}
	
	/**
	 * inner ActionListener class listens for the "Add Item" button
	 * to be pressed, whereupon
	 * 
	 * I DUNNO TODO
	 * TODO 
	 * TODO
	 */
	public class AddItem implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	/**
	 * inner ActionListener class listens for the "Add Item" button
	 * to be pressed, whereupon
	 * 
	 * I DUNNO TODO
	 * TODO 
	 * TODO
	 */
	public class AddSupplier implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	/**
	 * inner ActionListener class listens for the "View Orders" button
	 * to be pressed, whereupon the order window will be made visible.
	 */
	public class ViewOrders implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			ov.setWindowVisibility(true);
		}
	}

	/**
	 * inner ActionListener class listens for the "View Suppliers" button
	 * to be pressed, whereupon the supplier window will be made visible.
	 */
	public class ViewSuppliers implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sv.setSupplierWindowVisibility(true);
		}
	}

	/**
	 * inner ActionListener class listens for the "Import Items" button
	 * to be pressed, whereupon a prompt to import a file of items will appear.
	 */
	public class ImportItems implements ActionListener
	{
		String filename;
		@Override
		public void actionPerformed(ActionEvent e) {

			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if(filename != null) {
				JOptionPane.showMessageDialog(null, cc.readItems(filename, mv.getItemListModel()),
						"Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * inner ActionListener class listens for the "Import Suppliers" button
	 * to be pressed, whereupon a prompt to import a file of suppliers will appear.
	 */
	public class ImportSuppliers implements ActionListener
	{
		String filename;
		@Override
		public void actionPerformed(ActionEvent e) {

			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if(filename != null) {
				JOptionPane.showMessageDialog(null, cc.readSuppliers(filename, sv.getSupplierListModel()),
						"Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Main method for the program.
	 * Creates the GUI.
	 * Also creates and runs a client, which tries to connect to a game server
	 * on port 8428.
	 *
	 * @param args Command line arguments when starting the program.
	 */
	public static void main(String args[])
	{
		ClientController client = new ClientController("localhost", 8428);
		GUIController g = new GUIController(client);
	}
}
