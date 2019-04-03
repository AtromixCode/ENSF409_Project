package client.ClientViews;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MainView {

	private JFrame mainWindow;
	private JButton[] buttons = new JButton[8];
	private String[] buttonNames = {"Sell Item", "Order Item", "Remove Item", "Add Item", "View Orders", "View Suppliers", "Import Items", "Import Suppliers"};
	private JList<String> itemList;
	private DefaultListModel<String> itemListModel;

	protected MainView()
	{
		mainWindow = new JFrame("Logistics & Information General Management Application");
		mainWindow.setSize(700,500);
		mainWindow.add(createTitlePanel(), "North");
		mainWindow.add(createButtonPanel(), "West");
		mainWindow.add(createListPanel(), "Center");
		mainWindow.setVisible(true);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private JPanel createTitlePanel()
	{
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Logistics & Information General Management Application");
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		titlePanel.add (titleLabel);
		return titlePanel;
	}

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
//		for (int i=0; i<100; i++) {
//			itemListModel.addElement("<html><pre> "+(1000+i)+"\t\tKnock Bits\t88\t\t$12.67\t\t8015 </pre></html>");
//		}
		itemList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(itemList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	private JPanel createButtonPanel()
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

	protected void clearText()
	{
		itemListModel.clear();
	}


	protected void setButtonsClickable(boolean clickable)
	{
		for (int i = 0; i<3; i++)
		{
			buttons[i].setEnabled(clickable);
		}
	}

	protected JList<String> getItemList(){return itemList;}

	protected DefaultListModel<String> getItemListModel() {return itemListModel;}

	protected void addListSelectionListener(ListSelectionListener l)
	{
		itemList.addListSelectionListener(l);
	}

	protected void addButton1ActionListener(ActionListener b)
	{
		buttons[0].addActionListener(b);
	}


	protected void addButton2ActionListener(ActionListener b)
	{
		buttons[1].addActionListener(b);
	}

	protected void addButton3ActionListener(ActionListener b)
	{
		buttons[2].addActionListener(b);
	}

	protected void addButton4ActionListener(ActionListener b)
	{
		buttons[3].addActionListener(b);
	}

	protected void addButton5ActionListener(ActionListener b)
	{
		buttons[4].addActionListener(b);
	}

	protected void addButton6ActionListener(ActionListener b)
	{
		buttons[5].addActionListener(b);
	}

	protected void addButton7ActionListener(ActionListener b)
	{
		buttons[6].addActionListener(b);
	}

	protected void addButton8ActionListener(ActionListener b)
	{
		buttons[7].addActionListener(b);
	}


}
