package client;

import javax.swing.*;
import java.awt.*;

class GUI {

	private JFrame mainWindow;
	//private JButton sellItem, orderItem, removeItem, addItem, viewOrders, viewSuppliers;
	private JButton[] buttons = new JButton[6];
	private String[] buttonNames = {"Sell Item", "Order Item", "Remove Item", "Add Item", "View Orders", "View Suppliers"};
	private JList<String> itemList;
	private DefaultListModel<String> itemListModel;

	protected GUI()
	{
		mainWindow = new JFrame("Logistics & Information General Management Application");
		mainWindow.setSize(500,500);

		mainWindow.add(createTitlePanel(), "North");
		mainWindow.add(createButtonPanel(), "West");
		mainWindow.add(createListPanel(), "Center");
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	protected JPanel createTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		titlePanel.add (new JLabel("Logistics & Information General Management Application"));
		return titlePanel;
	}

	protected JPanel createListPanel()
	{
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setSize(400,500);
		listPanel.add(new JLabel("Items"), "North");
		JScrollPane listPane = new JScrollPane();
		listPane.setSize(400,400);
		itemListModel = new DefaultListModel<String>();
		itemList = new JList<String>(itemListModel);
		String width = "1234567890123456789012345678901234567890";
		itemList.setPrototypeCellValue(width);
		listPane.add(itemList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	protected JPanel createButtonPanel()
	{

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
		return buttonPanel;
	}


	public static void main(String args[])
	{
		GUI g = new GUI ();
		System.out.println("Hi");
	}
}
