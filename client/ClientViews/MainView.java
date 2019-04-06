package client.ClientViews;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * Order view displays the orders of the shop.
 * 
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 1.0
 * @since April 5, 2019
 */
class MainView {

	/**
	 * Main frame for the view.
	 */
	private JFrame mainWindow;
	
	/**
	 * List of the 8 buttons for the main functionality of the GUI.
	 */
	private JButton[] buttons = new JButton[9];
	
	/**
	 * Descriptive names of the 8 main functional buttons of the GUI.
	 */
	private String[] buttonNames = {"Sell Item", "Order Item", "Remove Item", "Add Item", "View Orders", "View Suppliers", "Import Items", "Import Suppliers", "Refresh"};
	
	/**
	 * The list of items' display information
	 */
	private JList<String> itemList;
	
	/**
	 * The list of item's information.
	 */
	private DefaultListModel<String> itemListModel;

	/**
	 * A panel that holds the loading circle.
	 */
	private JPanel loading;

	/**
	 * The icon of the loading circle.
	 */
	Icon icon = new ImageIcon("loading.gif");

	/**
	 * Default constructor.
	 * By default, frame is visible.
	 */
	protected MainView()
	{
		mainWindow = new JFrame("Logistics & Information General Management Application");
		mainWindow.setSize(700,500);
		mainWindow.add(createTitlePanel(), "North");
		mainWindow.add(createButtonPanel(), "West");
		mainWindow.add(createListPanel(), "Center");
		mainWindow.setVisible(true);
		mainWindow.setResizable(false);
		//mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Creates the panel for the program title.
	 * 
	 * @return The title panel.
	 */
	private JPanel createTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Logistics & Information General Management Application");
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		titlePanel.add (titleLabel);
		return titlePanel;
	}

	/**
	 * Creates the panel for displaying the item list information.
	 * 
	 * @return The item list display panel.
	 */
	private JPanel createListPanel()
	{
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		JLabel itemsLabel = new JLabel("Available Items");
		itemsLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(itemsLabel, "North");
		labelPanel.add(Box.createRigidArea(new Dimension(10,10)));
		labelPanel.add(new JLabel("Item ID:                      Item Name:               Quantity:             " +
				"      Price:                        Supplier ID:"),"South");
		listPanel.add(labelPanel, "North");
		itemListModel = new DefaultListModel<String>();
		itemList = new JList<String>(itemListModel);
		itemList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(itemList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	/**
	 * Creates the panel for the sidebar of functional buttons.
	 * 
	 * @return The button panel.
	 */
	private JPanel createButtonPanel()
	{
		loading = new JPanel ();
		loading.setSize(20,20);
		loading.add(new JLabel(icon));
		loading.setVisible(false);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(150,50)));
		for (int i=0; i<buttons.length; i++)
		{
			buttons[i]= new JButton(buttonNames[i]);
			if (i<3)
			{
				buttons[i].setEnabled(false);
			}
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(buttons[i]);
			buttonPanel.add(Box.createRigidArea(new Dimension(100,10)));
		}
		buttonPanel.add(loading);
		return buttonPanel;
	}
	
	/**
	 * Set the visibility of the main window.
	 * 
	 * @param visible True if the main window should be visible, false if not.
	 */
	protected void setMainWindowVisibility(boolean visible)
	{
		mainWindow.setVisible(visible);
	}

	/**
	 * Clear the item text in the GUI.
	 */
	protected void clearText()
	{
		itemListModel.clear();
	}

	/**
	 * Toggle whether or not the first 3 functional buttons are enabled.
	 * These buttons require at least one item to be functional.
	 * 
	 * @param clickable True if the buttons are clickable, false if not.
	 */
	protected void setButtonsClickable(boolean clickable)
	{
		for (int i = 0; i<3; i++)
		{
			buttons[i].setEnabled(clickable);
		}
	}

	/**
	 * Getter function to get the list of item information formatted for the GUI display.
	 * 
	 * @return The item information string list for displaying.
	 */
	protected JList<String> getItemList(){return itemList;}

	/**
	 * Getter function to get the list of item information.
	 * 
	 * @return The item information string list.
	 */
	protected DefaultListModel<String> getItemListModel() {return itemListModel;}

	/**
	 * Adds an ActionListener for the list of selectable items.
	 * 
	 * @param l The ActionListener for the list item selection.
	 */
	protected void addListSelectionListener(ListSelectionListener l)
	{
		itemList.addListSelectionListener(l);
	}

	/**
	 * Adds an ActionListener for the "Sell Item" button
	 * 
	 * @param b The ActionListener for clicking the sell item button.
	 */
	protected void addButton1ActionListener(ActionListener b)
	{
		buttons[0].addActionListener(b);
	}
	
	/**
	 * Adds an ActionListener for the "Order Item" button
	 * 
	 * @param b The ActionListener for clicking the order item button.
	 */
	protected void addButton2ActionListener(ActionListener b)
	{
		buttons[1].addActionListener(b);
	}

	/**
	 * Adds an ActionListener for the "Remove Item" button
	 * 
	 * @param b The ActionListener for clicking the remove item button.
	 */
	protected void addButton3ActionListener(ActionListener b)
	{
		buttons[2].addActionListener(b);
	}

	/**
	 * Adds an ActionListener for the "Add Item" button
	 * 
	 * @param b The ActionListener for clicking the add item button.
	 */
	protected void addButton4ActionListener(ActionListener b)
	{
		buttons[3].addActionListener(b);
	}
	
	/**
	 * Adds an ActionListener for the "View Orders" button
	 * 
	 * @param b The ActionListener for clicking the button to view orders.
	 */
	protected void addButton5ActionListener(ActionListener b)
	{
		buttons[4].addActionListener(b);
	}

	/**
	 * Adds an ActionListener for the "View Suppliers" button
	 * 
	 * @param b The ActionListener for clicking the button to view suppliers.
	 */
	protected void addButton6ActionListener(ActionListener b)
	{
		buttons[5].addActionListener(b);
	}

	/**
	 * Adds an ActionListener for the "Import Items" button
	 * 
	 * @param b The ActionListener for clicking the button to import items from a file.
	 */
	protected void addButton7ActionListener(ActionListener b)
	{
		buttons[6].addActionListener(b);
	}

	/**
	 * Adds an ActionListener for the "Import Suppliers" button
	 * 
	 * @param b The ActionListener for clicking the button to import suppliers from a file.
	 */
	protected void addButton8ActionListener(ActionListener b)
	{
		buttons[7].addActionListener(b);
	}


	/**
	 *
	 * Adds an ActionListener for the "Refresh" button
	 *
	 * @param b The ActionListener for clicking the button to refresh.
	 */
	protected void addButton9ActionListener(ActionListener b) { buttons[8].addActionListener(b); }

	/**
	 * Displays the loading circle.
	 */
	protected void loadingOn(){loading.setVisible(true);}

	/**
	 * Hides the loading circle.
	 */
	protected void loadingOff(){loading.setVisible(false);}


	/**
	 * Adds an action listener for window events.
	 *
	 * @param w The ActionListener for window events.
	 */
	protected void addWindowListener(WindowListener w) { mainWindow.addWindowListener(w); }
}
