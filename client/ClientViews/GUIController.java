package client.ClientViews;

import client.ClientControllers.ClientController;
import Models.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

class GUIController {

	private MainView mv;
	private SupplierView sv;
	private OrderView ov;
	private ClientController cc;

	protected GUIController (ClientController controller) {
		mv = new MainView();
		sv = new SupplierView();
		ov = new OrderView();
		cc = controller;
		cc.setItemDisplay(mv.getItemListModel());
		cc.setSupplierDisplay(sv.getSupplierListModel());
		mv.addListSelectionListener(new SelectItem());
		mv.addButton4ActionListener(new AddItem());
		mv.addButton5ActionListener(new ViewOrders());
		mv.addButton6ActionListener(new ViewSuppliers());
		mv.addButton7ActionListener(new ImportItems());
		mv.addButton8ActionListener(new ImportSuppliers());
		sv.addSupplierActionListener(new AddSupplier());
	}

	public class SelectItem implements ListSelectionListener {
		String data;
		int id;

		protected SelectItem()
		{
			mv.addButton1ActionListener(new SellItem());
			mv.addButton2ActionListener(new OrderItem());
			mv.addButton3ActionListener(new RemoveItem());
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int index = mv.getItemList().getSelectedIndex();
			if (index >= 0) {
				data = (String) mv.getItemListModel().get(index);
				mv.setButtonsClickable(true);
				Scanner scan = new Scanner (data);
				scan.next();
				id = scan.nextInt();
			}
		}

		public class SellItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity sold:"));
					if (quantity>0)
					{
						System.out.println("Checking if "+ quantity + " is enough and removing it here!");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public class OrderItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int quantity = Integer.parseInt(JOptionPane.showInputDialog("Please enter the quantity to order:"));
					if (quantity>0)
					{
						System.out.println("Ordering "+ quantity +" here!");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a number greater than 0!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public class RemoveItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null,
						"Please confirm you would like to remove Item #"+id+" from the store.");
				if (a==0)
				{
					System.out.println("Removing item here!");
				}
			}
		}
	}

	public class AddItem implements ActionListener {

		UserPromptPanel panel;

		@Override
		public void actionPerformed(ActionEvent e) {
			panel = new UserPromptPanel();
			int value = JOptionPane.showConfirmDialog(null, panel, "Add new supplier", JOptionPane.OK_CANCEL_OPTION);
			if (value == JOptionPane.OK_OPTION) {

			}
		}

		public class UserPromptPanel extends JPanel {
			private JTextField id = new JTextField(5);
			private JTextField name = new JTextField(10);
			private JTextField quantity = new JTextField(5);
			private JTextField price = new JTextField(8);
			private JComboBox<String> suppliers = new JComboBox<String>(cc.supplierIdsandNames());

			public UserPromptPanel() {
				this.add(new JLabel("ID:"));
				this.add(id);
				this.add(new JLabel("Item Name:"));
				this.add(name);
				this.add(new JLabel("Item Quantity:"));
				this.add(quantity);
				this.add(new JLabel("Item Price:"));
				this.add(price);
				this.add(new JLabel("Supplier List:"));
				this.add(suppliers);
			}
		}
	}
	public class AddSupplier implements ActionListener
	{
		UserPromptPanel panel = new UserPromptPanel();
		@Override
		public void actionPerformed(ActionEvent e) {
			int value = JOptionPane.showConfirmDialog(null, panel, "Add new supplier", JOptionPane.OK_CANCEL_OPTION);
			if (value == JOptionPane.OK_OPTION)
			{
				try
				{
					int id = Integer.parseInt(panel.id.getText());
					if (id>=0) {
						String name = panel.name.getText();
						String address = panel.address.getText();
						String contact = panel.contact.getText();
						if (name.equals("")||address.equals("")||contact.equals(""))
						{
							JOptionPane.showMessageDialog(null, "One of the fields was left blank!",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, cc.addSupplier(id, name, address, contact),
									"Result", JOptionPane.INFORMATION_MESSAGE);
							panel.id.setText("");
							panel.name.setText("");
							panel.address.setText("");
							panel.contact.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please enter a positive number!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Please enter an integer!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public class UserPromptPanel extends JPanel
		{
			private JTextField id = new JTextField(5);
			private JTextField name = new JTextField(20);
			private JTextField address = new JTextField(50);
			private JTextField contact = new JTextField(20);

			public UserPromptPanel()
			{
				this.add(new JLabel("ID:"));
				this.add(id);
				this.add(new JLabel("Company Name:"));
				this.add(name);
				this.add(new JLabel("Company Address"));
				this.add(address);
				this.add(new JLabel("Contact Name:"));
				this.add(contact);
			}
		}
	}

	public class ViewOrders implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			ov.setWindowVisibility(true);
		}
	}

	public class ViewSuppliers implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sv.setSupplierWindowVisibility(true);
		}
	}

	public class ImportItems implements ActionListener
	{
		String filename;
		@Override
		public void actionPerformed(ActionEvent e) {

			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if(filename != null) {
				JOptionPane.showMessageDialog(null, cc.readItems(filename),
						"Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public class ImportSuppliers implements ActionListener
	{
		String filename;
		@Override
		public void actionPerformed(ActionEvent e) {

			filename = JOptionPane.showInputDialog("Please enter the file name:");
			if(!filename.equals("")) {
				JOptionPane.showMessageDialog(null, cc.readSuppliers(filename),
						"Result", JOptionPane.INFORMATION_MESSAGE);
				sv.setSupplierWindowVisibility(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter an filename!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String args[])
	{
		ClientController client = new ClientController("localhost", 8428);
		GUIController g = new GUIController(client);
	}
}
