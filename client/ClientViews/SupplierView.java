package client.ClientViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Supplier view displays the suppliers of the shop.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
 */
class SupplierView {

	/**
	 * Main supplier JFrame.
	 */
	private JFrame supplierWindow;

	/**
	 * Button to add a supplier.
	 */
	private JButton addSupplier;

	/**
	 * Button to "close" (turn off visibility) the window.
	 */
	private JButton closeWindow;

	/**
	 * The list of supplier information to display in the GUI.
	 */
	private JList<String> supplierList;

	/**
	 * The list of supplier information.
	 */
	private DefaultListModel<String> supplierListModel;

	/**
	 * Default constructor to assemble the view.
	 * By default, it is not visible.
	 */
	protected SupplierView()
	{
		supplierWindow = new JFrame("Logistics & Information General Management Application");
		supplierWindow.setSize(800,500);
		supplierWindow.setResizable(false);
		supplierWindow.add(createButtonPanel(), "South");
		supplierWindow.add(createListPanel(),"Center");
		supplierWindow.setVisible(false);
		addCloseButtonListener();
	}

	/**
	 * Creates a panel of the buttons for the supplier view.
	 *
	 * @return The button panel.
	 */
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		addSupplier = new JButton("Add Supplier");
		closeWindow = new JButton("Close");
		buttonPanel.add(addSupplier);
		buttonPanel.add(closeWindow);
		return buttonPanel;
	}

	/**
	 * Creates a panel of the supplier lists for the supplier view.
	 *
	 * @return The list panel.
	 */
	private JPanel createListPanel(){
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		JLabel suppliersLabel = new JLabel("Current Suppliers");
		suppliersLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(suppliersLabel, "North");
		labelPanel.add(Box.createRigidArea(new Dimension(10,10)));
		labelPanel.add(new JLabel("Supplier ID:              Supplier Name:                                       " +
				"    Supplier Address:                                                        Contact:"), "South");
		listPanel.add(labelPanel, "North");
		supplierListModel = new DefaultListModel<String>();
		supplierList = new JList<String>(supplierListModel);
		supplierList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(supplierList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	/**
	 * Getter function for the supplier information list.
	 *
	 * @return The information of all the suppliers, as Strings.
	 */
	protected DefaultListModel<String> getSupplierListModel(){return supplierListModel;}

	/**
	 * Set the visibility of the supplier window.
	 *
	 * @param visible True if the supplier window should be visible, false if not.
	 */
	protected void setSupplierWindowVisibility(boolean visible)
	{
		supplierWindow.setVisible(visible);
	}

	/**
	 * Sets the action listener for the add supplier button.
	 *
	 * @param b an action listener containing logic for the add supplier button.
	 */
	public void addSupplierActionListener(ActionListener b)
	{
		addSupplier.addActionListener(b);
	}

	/**
	 * Adds an anonymous inner class ActionListener to the close buttons
	 * of the supplier window, in order to turn off visibility of the window.
	 */
	protected void addCloseButtonListener(){
		closeWindow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				supplierWindow.setVisible(false);
			}
		});
	}
}
