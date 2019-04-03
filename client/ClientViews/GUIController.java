package client.ClientViews;

import client.ClientControllers.ClientController;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

class GUI {

	private MainView mv;
	private SupplierView sv;
	private OrderView ov;
	private ClientController cc;

	protected GUI(ClientController controller){
		mv = new MainView();
		sv = new SupplierView();
		ov = new OrderView();
		cc = controller;
		mv.addListSelectionListener(new SelectItem());
		mv.addButton5ActionListener(new ViewOrders());
		mv.addButton6ActionListener(new ViewSuppliers());
		mv.addButton7ActionListener(new ImportItems());
		mv.addButton8ActionListener(new ImportSuppliers());
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

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public class AddSupplier implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

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
				JOptionPane.showMessageDialog(null, cc.readItems(filename, mv.getItemListModel()),
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
			if(filename != null) {
				JOptionPane.showMessageDialog(null, cc.readSuppliers(filename, sv.getSupplierListModel()),
						"Result", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void main(String args[])
	{
		ClientController client = new ClientController("localhost", 8428);
		GUI g = new GUI(client);
	}
}
