package client.ClientViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Order view displays the orders of the shop.
 * 
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
 */
class OrderView {
	/**
	 * Main JFrame of the window.
	 */
	private JFrame orderWindow;
	
	/**
	 * Button to "turn off" (visibility of) the window.
	 */
	private JButton closeWindow;
	
	/**
	 * The display area of the orders.
	 */
	private JTextArea orderDisplay;

	/**
	 * Default order constructor constructs the frame.
	 * By default, is not visible.
	 */
	protected OrderView()
	{
		orderWindow = new JFrame();
		orderWindow.setSize(550,500);
		orderWindow.add("North",createLabelPanel());
		orderWindow.add(createDisplayPanel());
		orderWindow.add("South", createButtonPanel());
		orderWindow.setVisible(false);
		orderWindow.setResizable(false);
		addCloseButtonListener();
	}

	/**
	 * Create the panel of labels for the list of orders.
	 * 
	 * @return The label panel.
	 */
	private JPanel createLabelPanel()
	{
		JPanel labelPanel = new JPanel();
		JLabel titleLabel = new JLabel("Order History");
		titleLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(titleLabel);
		return labelPanel;
	}

	/**
	 * Create the panel to display the order information.
	 * 
	 * @return The order information panel.
	 */
	private JPanel createDisplayPanel()
	{
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new BorderLayout());
		orderDisplay = new JTextArea();
		orderDisplay.setFont(new Font("Courier New", Font.BOLD, 12));
		orderDisplay.setEditable(false);
		JScrollPane orderScroll = new JScrollPane(orderDisplay);
		displayPanel.add("Center",orderScroll);
		displayPanel.add("East",Box.createRigidArea(new Dimension(10,10)));
		displayPanel.add("West",Box.createRigidArea(new Dimension(10,10)));
		return displayPanel;
	}

	/**
	 * Create the panel for the buttons to close the window.
	 * 
	 * @return The button panel.
	 */
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		closeWindow = new JButton("Close");
		buttonPanel.add(closeWindow);
		return buttonPanel;
	}

	/**
	 * Set the visibility of the Order window.
	 * 
	 * @param visibility True if the order window should be visible, false if not.
	 */
	protected void setWindowVisibility(boolean visibility)
	{
		orderWindow.setVisible(visibility);
	}

	/**
	 * Adds an anonymous inner class ActionListener to the close buttons
	 * of the order window, in order to set off visibility.
	 */
	protected void addCloseButtonListener(){
		closeWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				orderWindow.setVisible(false);
			}
		});
	}

	/**
	 * Gets the order display text area.
	 * @return a text area that contains the order display.
	 */
	public JTextArea getOrderDisplay(){return orderDisplay;}
}
