package client.ClientViews;

import javax.swing.*;
import java.awt.*;

public class OrderView {
	private JFrame orderWindow;
	private JButton closeWindow;
	private JTextArea orderDisplay;

	protected OrderView()
	{
		orderWindow = new JFrame();
		orderWindow.setSize(500,500);
		orderWindow.add("North",createLabelPanel());
		orderWindow.add(createDisplayPanel());
		orderWindow.add("South", createButtonPanel());
		orderWindow.setVisible(false);
		orderWindow.setResizable(false);
	}

	private JPanel createLabelPanel()
	{
		JPanel labelPanel = new JPanel();
		JLabel titleLabel = new JLabel("Order History");
		titleLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(titleLabel);
		return labelPanel;
	}

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

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		closeWindow = new JButton("Close");
		buttonPanel.add(closeWindow);
		return buttonPanel;
	}

	protected void setWindowVisibility(boolean visibility)
	{
		orderWindow.setVisible(visibility);
	}
}
