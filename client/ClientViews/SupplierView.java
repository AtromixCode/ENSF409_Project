package client.ClientViews;

import javax.swing.*;
import java.awt.*;

public class SupplierView {

	private JFrame supplierWindow;
	private JButton addSupplier;
	private JButton closeWindow;
	private JList<String> supplierList;
	private DefaultListModel<String> supplierListModel;

	protected SupplierView()
	{
		supplierWindow = new JFrame("Logistics & Information General Management Application");
		supplierWindow.setSize(600,500);
		supplierWindow.setResizable(false);
		supplierWindow.add(createLabelPanel(),"North");
		supplierWindow.add(createButtonPanel(), "South");
		supplierWindow.add(createListPanel(),"Center");
		supplierWindow.setVisible(true);
	}

	protected JPanel createLabelPanel()
	{
		JPanel labelPanel = new JPanel();
		JLabel titleLabel = new JLabel("Supplier List");
		titleLabel.setFont(new Font("Arial", Font.BOLD,20));
		return labelPanel;
	}

	protected JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		addSupplier = new JButton("Add Supplier");
		closeWindow = new JButton("Close");
		buttonPanel.add(addSupplier);
		buttonPanel.add(closeWindow);
		return buttonPanel;
	}

	protected JPanel createListPanel(){
		JPanel listPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new BorderLayout());
		JLabel suppliersLabel = new JLabel("Current Suppliers");
		suppliersLabel.setFont(new Font("Arial", Font.PLAIN,16));
		labelPanel.add(suppliersLabel, "North");
		labelPanel.add(Box.createRigidArea(new Dimension(10,10)));
		labelPanel.add(new JLabel("Supplier ID:              Supplier Name:                          " +
				"Supplier Address:                                      Contact:"),"South");
		listPanel.add(labelPanel, "North");
		supplierListModel = new DefaultListModel<String>();
		supplierList = new JList<String>(supplierListModel);
		for (int i=0; i<100; i++) {
			supplierListModel.addElement("<html><pre>"+8000+i+"\t\tGrommet Builders\t788 30th St., SE, Calgary\tFred</pre></html>");
		}
		supplierList.setVisibleRowCount(20);
		JScrollPane listPane = new JScrollPane(supplierList);
		listPanel.add(listPane, "Center");
		return listPanel;
	}

	protected void setSupplierWindowVisibility(boolean visible)
	{
		supplierWindow.setVisible(visible);
	}



}
