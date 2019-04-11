package client.ClientViews;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartView {

	private JFrame cartWindow;

	private JButton closeWindow;

	private JButton checkout;

	private JButton removeFromCart;

	private JList<String> cartList;

	private DefaultListModel<String> cartListModel;

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

	protected DefaultListModel<String> getCartListModel(){return cartListModel;}

	protected JList<String> getCartList(){return cartList;}

	protected void setButtonClickable(boolean clickable){removeFromCart.setEnabled(clickable);}

	protected void setCartWindowVisibility(boolean visible) { cartWindow.setVisible(visible); }

	protected void addCheckoutButtonListener(ActionListener b) { checkout.addActionListener(b); }

	protected void addListSelectionListener(ListSelectionListener l) { cartList.addListSelectionListener(l); }

	protected void addRemoveCartButtonListener (ActionListener b) {removeFromCart.addActionListener(b);}

	protected void addCloseButtonListener(){
		closeWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartWindow.setVisible(false);
			}
		});
	}

}
