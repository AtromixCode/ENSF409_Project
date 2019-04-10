package client.ClientViews;

import client.ClientControllers.ClientController;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 * GUI controller manages the GUI.
 * Client-server interaction is prompted by events.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
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
		setUpCC();
		addActionListeners();
		retrieveAndDisplayItems();
	}

	/**
	 * Sends display areas to the client controller for its use.
	 */
	protected void setUpCC()
	{
		cc.setItemDisplay(mv.getItemListModel());
		cc.setSupplierDisplay(sv.getSupplierListModel());
		cc.setOrderDisplay(ov.getOrderDisplay());
	}


	/**
	 * Assigns all action listeners for buttons on the front end.
	 */
	protected void addActionListeners()
	{
		mv.addWindowListener(new WindowClose());
		mv.addListSelectionListener(new SelectItem());
		mv.addSearchAreaDocumentListener(new searchItem());
		mv.addButton4ActionListener(new AddItem());
		mv.addButton5ActionListener(new ViewOrders());
		mv.addButton6ActionListener(new ViewSuppliers());
		mv.addButton7ActionListener(new ImportItems());
		mv.addButton8ActionListener(new ImportSuppliers());
		mv.addButton9ActionListener(new Refresh());
		sv.addSupplierActionListener(new AddSupplier());
	}

	/**
	 * Retrieves and displays data from server. Displays
	 * and error box to the user if this is unsuccessful.
	 */
	protected void retrieveAndDisplayItems()
	{
		if(!cc.fetchAndDisplayItems(mv.getSearchText())) {
			JOptionPane.showMessageDialog(null, "Error communicating with server!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{

		}
	}

	protected void retrieveAndDisplaySuppliers()
	{
		if (!cc.fetchAndDisplaySuppliers()) {
			JOptionPane.showMessageDialog(null, "Error communicating with server!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void retrieveAndDisplayOrders()
	{
		if (!cc.fetchAndDisplayOrders())
		{
			JOptionPane.showMessageDialog(null, "Error communicating with server!", "Error", JOptionPane.ERROR_MESSAGE);
		}
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
		 * Default constructor assigns the actions listeners to the 3 buttons
		 * dependent on there being at least one item to select.
		 */
		protected SelectItem()
		{
			mv.addButton1ActionListener(new SellItem());
			mv.addButton2ActionListener(new OrderItem());
			mv.addButton3ActionListener(new RemoveItem());
			retrieveAndDisplayItems();
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
		 * will appear. Handles errors and tells the user what went wrong.
		 */
		public class SellItem implements ActionListener {

			/**
			 *
			 * @param e The event that triggers the call to the method.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity sold:"));
					if (quantity>0) {
						JOptionPane.showMessageDialog(null, cc.sellItem(id, quantity), "Result", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter an integer!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				mv.setButtonsClickable(false);
				retrieveAndDisplayItems();
			}
		}

		/**
		 * Inner ActionListener class listens for the "Order Item" button
		 * to be pressed, whereupon a dialogue box asking for a specified amount
		 * to order will appear. Handles errors and tells the user what went wrong.
		 */
		public class OrderItem implements ActionListener {

			/**
			 * Implementation of the action performed method that displays a
			 * text box to the user for input on quantity for item ordering.
			 *
			 * @param e The event that triggers the call to the method.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity to order:"));
					if (quantity>0) {
						JOptionPane.showMessageDialog(null, cc.orderItem(id, quantity, mv.getSearchText()), "Result", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter an integer!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				mv.setButtonsClickable(false);
				retrieveAndDisplayItems();
			}
		}

		/**
		 * Inner ActionListener class listens for the "Remove Item" button
		 * to be pressed, whereupon a dialogue box asking for confirmation will appear.
		 */
		public class RemoveItem implements ActionListener {

			/**
			 * Implementation of the action performed method that displays a
			 * text box to the user to confirm an item removal.
			 * @param e The event that triggers the call to the method.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "Please confirm you would like to remove Item #"+id+" from the store.");
				if (a==0) {
					JOptionPane.showMessageDialog(null, cc.removeItem(id, mv.getSearchText()), "Result", JOptionPane.INFORMATION_MESSAGE);
					mv.setButtonsClickable(false);
				}
				mv.setButtonsClickable(false);
				retrieveAndDisplayItems();
			}
		}
	}

	/**
	 * Inner ActionListener class listens for the "Add Item" button
	 * to be pressed, whereupon error checking is done and an item
	 * is added to the store.
	 *
	 */
	public class AddItem implements ActionListener {

		/**
		 * The panel that is displayed to the user for inputting item information.
		 */
		UserPromptPanel panel;

		/**
		 * Implementation of the action performed method that displays a
		 * text box to the user for inputting parameters to add an item.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			retrieveAndDisplayItems();
			panel = new UserPromptPanel();
			int value = JOptionPane.showConfirmDialog(null, panel, "Add new item", JOptionPane.OK_CANCEL_OPTION);
			if (value == JOptionPane.OK_OPTION) {
				try
				{
					int id = Integer.parseInt(panel.id.getText());
					int quantity = Integer.parseInt(panel.quantity.getText());
					float price = Float.parseFloat(panel.price.getText());
					int index = panel.suppliers.getSelectedIndex();
					Scanner scan = new Scanner(panel.list[index]);
					int supId = scan.nextInt();
					String name = panel.name.getText();
					if (name.equals(""))
					{
						JOptionPane.showMessageDialog(null, "Item name was left blank!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					else if(quantity<0||price<0)
					{
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, cc.addItem(id, name, quantity, price, supId, mv.getSearchText()),
								"Result", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "The id, quantity, price, or supplier was not entered correctly.",
							"Error", JOptionPane.ERROR_MESSAGE);
					//ex.printStackTrace();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "There was an error adding the item.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			mv.setButtonsClickable(false);
			retrieveAndDisplayItems();
		}

		/**
		 * An inner class to the AddItem class that creates a panel with
		 * appropriate fields for adding a new item.
		 */
		public class UserPromptPanel extends JPanel {

			/**
			 * The text field for the item id.
			 */
			private JTextField id = new JTextField(5);

			/**
			 * The text field for the item name.
			 */
			private JTextField name = new JTextField(10);

			/**
			 * The text field for the item quantity.
			 */
			private JTextField quantity = new JTextField(5);

			/**
			 * The text field for the item price.
			 */
			private JTextField price = new JTextField(8);

			/**
			 * The list that is used for the combobox.
			 */
			private String [] list = cc.supplierIdsandNames();

			/**
			 * The combo box of the suppliers to be added for the item.
			 */
			private JComboBox<String> suppliers = new JComboBox<String>(list);

			/**
			 * The constructor that assembles the panel.
			 */
			public UserPromptPanel() {
				this.add(new JLabel("ID:"));
				this.add(id);
				this.add(new JLabel("Item Name:"));
				this.add(name);
				this.add(new JLabel("Item Quantity:"));
				this.add(quantity);
				this.add(new JLabel("Item Price:"));
				this.add(price);
				this.add(new JLabel("Supplier List:"));
				this.add(suppliers);
			}
		}
	}

	/**
	 * Inner ActionListener class listens for the "Add Supplier" button
	 * to be pressed, whereupon user input is taken to add a supplier,
	 * and the supplier list is updated.
	 *
	 */
	public class AddSupplier implements ActionListener
	{
		/**
		 * The panel that is displayed to the user for inputting supplier information.
		 */
		UserPromptPanel panel = new UserPromptPanel();

		/**
		 * Implementation of the action performed method that displays a
		 * text box to the user for inputting parameters to add a supplier.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			retrieveAndDisplaySuppliers();
			int value = JOptionPane.showConfirmDialog(null, panel, "Add new supplier", JOptionPane.OK_CANCEL_OPTION);
			if (value == JOptionPane.OK_OPTION)
			{
				try
				{
					int id = Integer.parseInt(panel.id.getText());
					if (id>=0) {
						String name = panel.name.getText();
						String address = panel.address.getText();
						String contact = panel.contact.getText();
						if (name.equals("")||address.equals("")||contact.equals(""))
						{
							JOptionPane.showMessageDialog(null, "One of the fields was left blank!",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, cc.addSupplier(id, name, address, contact),
									"Result", JOptionPane.INFORMATION_MESSAGE);
							panel.id.setText("");
							panel.name.setText("");
							panel.address.setText("");
							panel.contact.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a positive number!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			mv.setButtonsClickable(false);
			retrieveAndDisplaySuppliers();
		}

		/**
		 * An inner class to the AddSupplier class that creates a panel with
		 * appropriate fields for adding a new supplier.
		 */
		public class UserPromptPanel extends JPanel
		{
			/**
			 * The text field for the supplier id.
			 */
			private JTextField id = new JTextField(5);

			/**
			 * The text field for the supplier name.
			 */
			private JTextField name = new JTextField(20);

			/**
			 * The text field for the supplier address.
			 */
			private JTextField address = new JTextField(50);

			/**
			 * The text field for the supplier contact name.
			 */
			private JTextField contact = new JTextField(20);

			/**
			 * The constructor that assembles the panel.
			 */
			public UserPromptPanel()
			{
				this.add(new JLabel("ID:"));
				this.add(id);
				this.add(new JLabel("Company Name:"));
				this.add(name);
				this.add(new JLabel("Company Address"));
				this.add(address);
				this.add(new JLabel("Contact Name:"));
				this.add(contact);
			}
		}
	}

	/**
	 * Inner ActionListener class listens for the "View Orders" button
	 * to be pressed, whereupon the order window will be made visible.
	 */
	public class ViewOrders implements ActionListener
	{

		/**
		 * Implementation of the action performed method that displays a
		 * window to the user to view the orders made by the shop.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			retrieveAndDisplayOrders();
			ov.setWindowVisibility(true);
			mv.setButtonsClickable(false);
			retrieveAndDisplayOrders();
		}
	}

	/**
	 * Inner ActionListener class listens for the "View Suppliers" button
	 * to be pressed, whereupon the supplier window will be made visible.
	 */
	public class ViewSuppliers implements ActionListener
	{

		/**
		 * Implementation of the action performed method that displays a
		 * window to the user to view the suppliers attached to the shop.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			retrieveAndDisplaySuppliers();
			sv.setSupplierWindowVisibility(true);
			mv.setButtonsClickable(false);
			retrieveAndDisplaySuppliers();
		}
	}

	/**
	 * Inner ActionListener class listens for the "Import Items" button
	 * to be pressed, whereupon a prompt to import a file of items will appear.
	 */
	public class ImportItems implements ActionListener
	{
		/**
		 * Stores the name of the file to be read in.
		 */
		String filename;

		/**
		 *	Implementation of the action performed method that ensures input was
		 *  correct, telling the user if otherwise, and calling the readItems
		 * 	method in the client controller, passing the filename.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			mv.clearSearchText();
			retrieveAndDisplayItems();
			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if (filename != null) {
				if (!filename.equals("")) {
					JOptionPane.showMessageDialog(null, cc.readItems(filename, mv.getSearchText()),
							"Result", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter a filename!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			mv.setButtonsClickable(false);
			retrieveAndDisplayItems();
		}
	}

	/**
	 * Inner ActionListener class listens for the "Import Suppliers" button
	 * to be pressed, whereupon a prompt to import a file of suppliers will appear.
	 */
	public class ImportSuppliers implements ActionListener
	{
		/**
		 * Stores the name of the file to be read in.
		 */
		String filename;

		/**
		 * 	Implementation of the action performed method that ensures input was
		 *  correct, telling the user if otherwise, and calling the readSuppliers
		 *  method in the client controller, passing the filename.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			retrieveAndDisplaySuppliers();
			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if (filename != null) {
				if (!filename.equals("")) {
					JOptionPane.showMessageDialog(null, cc.readSuppliers(filename),
							"Result", JOptionPane.INFORMATION_MESSAGE);
					sv.setSupplierWindowVisibility(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a filename!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			mv.setButtonsClickable(false);
			retrieveAndDisplaySuppliers();
		}
	}

	/**
	 * Inner ActionListener class listens for the "Refresh" button to be pressed,
	 * whereupon a the displays are updated and a loading circle is shown.
	 */
	public class Refresh implements ActionListener {

		/**
		 * Implementation of the action performed method that updates
		 * the user displays and shows a loading circle.
		 *
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			mv.clearSearchText();
			retrieveAndDisplayItems();
			mv.loadingOn();
			LoadThread lt = new LoadThread();
			lt.start();
			mv.setButtonsClickable(false);
		}

		/**
		 * An inner class for the Refresh class used to display
		 * the loading circle for a certain period of time.
		 */
		public class LoadThread extends Thread
		{
			/**
			 * Sleeps the thread for 1 second before having it
			 * removing the loading circle from being displayed.
			 */
			@Override
			public void run()
			{
				try {
					sleep(1000);
				}catch(InterruptedException ex)
				{ }
				mv.loadingOff();
			}
		}

	}

	/**
	 * Inner ActionListener class listens for main window events, mainly
	 * when the window is closed. Ends the program when this happens.
	 */
	public class WindowClose implements WindowListener
	{
		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowOpened(WindowEvent e) { }

		/**
		 * Terminates the program when the main window is closed.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			cc.terminate();
		}

		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowClosed(WindowEvent e) { }

		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowIconified(WindowEvent e) { }

		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowDeiconified(WindowEvent e) { }

		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowActivated(WindowEvent e) { }

		/**
		 * Unused.
		 * @param e The event that triggers the call to the method.
		 */
		@Override
		public void windowDeactivated(WindowEvent e) { }
	}

	public class searchItem implements DocumentListener
	{

		@Override
		public void insertUpdate(DocumentEvent e) {
			mv.setButtonsClickable(false);
			cc.displayItems(mv.getSearchText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			mv.setButtonsClickable(false);
			cc.displayItems(mv.getSearchText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			mv.setButtonsClickable(false);
			cc.displayItems(mv.getSearchText());
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
