package client.ClientViews;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartView {

	/**
	 * Main JFrame for the cart window.
	 */
	private JFrame cartWindow;

	/**
	 * JButton to close the cart window.
	 */
	private JButton closeWindow;

	/**
	 * JButton to checkout/buy from the store.
	 */
	private JButton checkout;

	/**
	 * JButton to remove an item from the cart.
	 */
	private JButton removeFromCart;

	/**
	 * The list of items in the cart.
	 */
	private JList<String> cartList;

	/**
	 * The list of the item models in the cart.
	 */
	private DefaultListModel<String> cartListModel;

	/**
	 * The main cart window view.
	 */
	protected CartView()
	{
		cartWindow = new JFrame("Specialized for Users - General Management Application");
		cartWindow.setSize(500,500);
		cartWindow.setResizable(false);
		cartWindow.add(createButtonPanel(), "South");
		cartWindow.add(createListPanel(),"Center");
		cartWindow.setVisible(false);
		addCloseButtonListener();
	}

	/**
	 * The main button panel for the cart view.
	 * @return The JPanel for the buttons
	 */
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		closeWindow = new JButton("Close");
		buttonPanel.add(closeWindow);
		checkout = new JButton("Checkout");
		buttonPanel.add(checkout);
		removeFromCart = new JButton("Remove From Cart");
		buttonPanel.add(removeFromCart);
		removeFromCart.setEnabled(false);
		return buttonPanel;
	}

	/**
	 * Panel to display the list of items in the cart.
	 * @return The JPanel with the list of items.
	 */
	private JPanel createListPanel(){
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		JLabel cartLabel = new JLabel("Current Cart");
		cartLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(cartLabel, "North");
		labelPanel.add(Box.createRigidArea(new Dimension(10,10)));
		labelPanel.add(new JLabel("Item ID:                      Item Name:          Quantity Selected:         " +
				"      Total:"), "South");
		listPanel.add(labelPanel, "North");
		cartListModel = new DefaultListModel<String>();
		cartList = new JList<String>(cartListModel);
		cartList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(cartList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	/**
	 * Get the list of models for the items in the cart
	 * @return The list of cart item strings.
	 */
	protected DefaultListModel<String> getCartListModel(){return cartListModel;}

	/**
	 * Get the list of items in String form for the cart.
	 * @return The list of cart item strings.
	 */
	protected JList<String> getCartList(){return cartList;}

	/**
	 * Set the clickability of the remove from cart button.
	 * @param clickable True if the button should be clickable, false if not.
	 */
	protected void setButtonClickable(boolean clickable){removeFromCart.setEnabled(clickable);}

	/**
	 * Set the visibility of the cart window.
	 * @param visible True if the window should be clickable, false if not.
	 */
	protected void setCartWindowVisibility(boolean visible) { cartWindow.setVisible(visible); }

	/**
	 * Add the actionlistener for the checkout button.
	 * @param b The actionlistener for the button presses.
	 */
	protected void addCheckoutButtonListener(ActionListener b) { checkout.addActionListener(b); }

	/**
	 * Add the actionListener for the user selection.
	 * @param l The actionlistener for the list selection.
	 */
	protected void addListSelectionListener(ListSelectionListener l) { cartList.addListSelectionListener(l); }

	/**
	 * Add the action listener for the remove item from cart button.
	 * @param b The actionlistener for the button presses.
	 */
	protected void addRemoveCartButtonListener (ActionListener b) {removeFromCart.addActionListener(b);}

	/**
	 * Inner class for the close button listener.
	 */
	protected void addCloseButtonListener(){
		/**
		 * Add the action listener for the close cart view window button.
		 * @param The action listener for the close cart view button.
		 */
		closeWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartWindow.setVisible(false);
			}
		});
	}

}
