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
	 * Cart view to display the items in the cart.
	 */
	private CartView cv;

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
	protected GUIController(ClientController controller) {
		mv = new MainView();
		cv = new CartView();
		cc = controller;
		setUpCC();
		addActionListeners();
		retrieveAndDisplayItems();
	}

	/**
	 * Sends display areas to the client controller for its use.
	 */
	protected void setUpCC() {
		cc.setItemDisplay(mv.getItemListModel());
		cc.setCartDisplay(cv.getCartListModel());
	}


	/**
	 * Assigns all action listeners for buttons on the front end.
	 */
	protected void addActionListeners() {
		mv.addWindowListener(new WindowClose());
		mv.addButton2ActionListener(new ViewCart());
		mv.addButton3ActionListener(new Refresh());
		mv.addListSelectionListener(new SelectItem());
		mv.addSearchAreaDocumentListener(new searchItem());
		cv.addCheckoutButtonListener(new CheckoutCart());
		cv.addListSelectionListener(new SelectCart());
	}

	/**
	 * Retrieves and displays data from server. Displays
	 * and error box to the user if this is unsuccessful.
	 */
	protected void retrieveAndDisplayItems() {
		if (!(cc.fetchAndDisplayItems(mv.getSearchText()) && cc.fetchSuppliers())) {
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
		protected SelectItem() {
			mv.addButton1ActionListener(new AddItem());
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
				mv.setButtonClickable(true);
				Scanner scan = new Scanner(data);
				scan.next();
				id = scan.nextInt();
			}
		}

		/**
		 * inner ActionListener class listens for the "Sell Item" button
		 * to be pressed, whereupon a dialogue box asking for a specified amount
		 * will appear. Handles errors and tells the user what went wrong.
		 */
		public class AddItem implements ActionListener {

			/**
			 * @param e The event that triggers the call to the method.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity you want:"));
					if (quantity > 0) {
						JOptionPane.showMessageDialog(null, cc.addItem(id, quantity), "Result", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please enter an integer!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				mv.setButtonClickable(false);
				retrieveAndDisplayItems();
			}
		}
	}

	/**
	 * Nested class selection listener for the selection of items in the cart.
	 */
	public class SelectCart implements ListSelectionListener {
		/**
		 * The data in the cart being selected.
		 */
		String data;

		/**
		 * The id of the item being selected.
		 */
		int id;

		/**
		 * Set the action listener for the remove item button
		 */
		public SelectCart() {
			cv.addRemoveCartButtonListener(new RemoveItem());
		}

		/**
		 * Get the data of a selected item in the cart.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int index = cv.getCartList().getSelectedIndex();
			if (index >= 0) {
				data = (String) cv.getCartListModel().get(index);
				cv.setButtonClickable(true);
				Scanner scan = new Scanner(data);
				scan.next();
				id = scan.nextInt();
			}
		}

		/**
		 * Nested inner class action listener for the remove item button
		 * Removes a selected item from the cart.
		 */
		public class RemoveItem implements ActionListener
		{
			/**
			 * Removes an item on button press.
			 * @param e The action event.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "Remove this item?");
				if (a==0) {
					JOptionPane.showMessageDialog(null, cc.removeItem(id), "Result", JOptionPane.INFORMATION_MESSAGE);
				}
				cc.displayCart();
				cv.setButtonClickable(false);
			}
		}
	}

	/**
	 * Inner class implements action listener to display the cart.
	 */
	public class ViewCart implements ActionListener
	{
		/**
		 * Acts on the button press to make the cart window visible.
		 * @param e Action event of button press
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			cv.setCartWindowVisibility(true);
			cc.displayCart();
			retrieveAndDisplayItems();
			mv.setButtonClickable(false);
		}
	}

	/**
	 * Actionlistener class for the checkout confirmation prompt.
	 */
	public class CheckoutCart implements ActionListener
	{
		/**
		 * Acts on an button press to prompt the user to confirm or cancel checkout.
		 * @param e ActionEvent button press.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int a = JOptionPane.showConfirmDialog(null, "Please confirm you would like to checkout now");
			if (a==0) {
				JOptionPane.showMessageDialog(null, cc.buyItems(), "Result", JOptionPane.INFORMATION_MESSAGE);
			}
			cc.displayCart();
			retrieveAndDisplayItems();
			cv.setButtonClickable(false);
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
			mv.setButtonClickable(false);
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

	/**
	 * Document listener for searching for items 
	 */
	public class searchItem implements DocumentListener
	{

		/**
		 * Documentlistener acts on document event to turn off buttons.
		 * When an insert update is made.
		 * @param e DocumentEvent 
		 */
		@Override
		public void insertUpdate(DocumentEvent e) {
			mv.setButtonClickable(false);
			cc.displayItems(mv.getSearchText());
		}

		/**
		 * Documentlistener acts on document text removed event 
		 * to turn off buttons and display the search text.
		 * @param e DocumentEvent
		 */
		@Override
		public void removeUpdate(DocumentEvent e) {
			mv.setButtonClickable(false);
			cc.displayItems(mv.getSearchText());
		}

		/**
		 * Documentlistener acts on document text changed event 
		 * to turn off buttons and display the search text.
		 * @param e DocumentEvent
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
			mv.setButtonClickable(false);
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
		ClientController client = new ClientController("10.13.146.120", 8428);
		GUIController g = new GUIController(client);
	}
}
