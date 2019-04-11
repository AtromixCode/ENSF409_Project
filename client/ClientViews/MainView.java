package client.ClientViews;

import javax.swing.*;
import javax.swing.event.DocumentListener;
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
	private JButton[] buttons = new JButton[3];
	
	/**
	 * Descriptive names of the 8 main functional buttons of the GUI.
	 */
	private String[] buttonNames = {"Add Item","View Cart","Refresh"};

	/**
	 * The search text field for finding an item.
	 */
	private JTextField searchArea;

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
		mainWindow = new JFrame("Specialized for Users - General Management Application");
		mainWindow.setSize(700,500);
		mainWindow.add(createTitlePanel(), "North");
		mainWindow.add(createButtonPanel(), "West");
		mainWindow.add(createListPanel(), "Center");
		mainWindow.setVisible(true);
		mainWindow.setResizable(false);

	}

	/**
	 * Creates the panel for the program title.
	 * 
	 * @return the title panel.
	 */
	private JPanel createTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Specialized for Users - General Management Application");
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		titlePanel.add (titleLabel);
		return titlePanel;
	}

	/**
	 * Creates the panel for displaying the item list information.
	 * 
	 * @return the item list display panel.
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
				"      Price:"),"South");
		listPanel.add(labelPanel, "North");
		itemListModel = new DefaultListModel<String>();
		itemList = new JList<String>(itemListModel);
		itemList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(itemList);
		listPanel.add(listPane, "Center");
		listPanel.add(createSearchPanel(),"South");
		return listPanel;
	}

	/**
	 * Creates a panel with a text box and label used for searching items.
	 *
	 * @return a panel containing a text box and label.
	 */
	private JPanel createSearchPanel()
	{
		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("Search Items: "));
		searchArea = new JTextField(20);
		searchPanel.add(searchArea);
		return searchPanel;
	}


	/**
	 * Creates the panel for the sidebar of functional buttons.
	 * 
	 * @return the button panel.
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
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(buttons[i]);
			buttonPanel.add(Box.createRigidArea(new Dimension(100,10)));
		}
		setButtonClickable(false);
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
	 * Clear the search item text in the GUI.
	 */
	protected void clearSearchText(){searchArea.setText("");}

	/**
	 * Gets the text in the search box used for finding an item.
	 *
	 * @return a string containing user input.
	 */
	protected String getSearchText(){return searchArea.getText();}


	/**
	 * Toggle whether or not the first 3 functional buttons are enabled.
	 * These buttons require at least one item to be functional.
	 * 
	 * @param clickable True if the buttons are clickable, false if not.
	 */
	protected void setButtonClickable(boolean clickable)
	{
		buttons[0].setEnabled(clickable);
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
	protected void addListSelectionListener(ListSelectionListener l) { itemList.addListSelectionListener(l); }

	protected void addButton1ActionListener(ActionListener b) { buttons[0].addActionListener(b); }
	
	protected void addButton2ActionListener(ActionListener b) { buttons[1].addActionListener(b); }

	protected void addButton3ActionListener(ActionListener b) { buttons[2].addActionListener(b); }

	/**
	 * Adds an Document Listener for the text field used used to search for items.
	 *
	 * @param b The document listener for changes to the text field.
	 */
	protected void addSearchAreaDocumentListener(DocumentListener b) { searchArea.getDocument().addDocumentListener(b); }

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
