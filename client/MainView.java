package client;

import javax.swing.*;
import java.awt.*;

public class MainView {

	private JFrame mainWindow;
	private JButton[] buttons = new JButton[6];
	private String[] buttonNames = {"Sell Item", "Order Item", "Remove Item", "Add Item", "View Orders", "View Suppliers"};
	private JList<String> itemList;
	private DefaultListModel<String> itemListModel;

	protected MainView()
	{
		mainWindow = new JFrame("Logistics & Information General Management Application");
		mainWindow.setSize(600,500);

		mainWindow.add(createTitlePanel(), "North");
		mainWindow.add(createButtonPanel(), "West");
		mainWindow.add(createListPanel(), "Center");
		mainWindow.setVisible(true);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	protected JPanel createTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Logistics & Information General Management Application");
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		titlePanel.add (titleLabel);
		return titlePanel;
	}

	protected JPanel createListPanel()
	{
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		JLabel itemsLabel = new JLabel("Available Items");
		itemsLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(itemsLabel, "North");
		labelPanel.add(Box.createRigidArea(new Dimension(10,10)));
		labelPanel.add(new JLabel("Item ID:          Item Name:               Quantity:          Price:            Supplier ID:"),"South");
		listPanel.add(labelPanel, "North");
		itemListModel = new DefaultListModel<String>();
		itemList = new JList<String>(itemListModel);
		for (int i=0; i<100; i++) {
			itemListModel.addElement(1000+i+"               Knock Bits                88                      $12.67            8015");
		}
		itemList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(itemList);
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
			if (i<3)
			{
				buttons[i].setEnabled(false);
			}
			buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(buttons[i]);
			buttonPanel.add(Box.createRigidArea(new Dimension(100,10)));
		}
		return buttonPanel;
	}
	protected void setMainWindowVisibility(boolean visible)
	{
		mainWindow.setVisible(visible);
	}
}
