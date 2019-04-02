package client.ClientViews;

import client.ClientControllers.ClientController;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	}

	public class SelectItem implements ListSelectionListener {
		String data;
		int id;
		String item;
		int quantity;

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
				item = "";
				scan.next();
				id = scan.nextInt();
			}
		}

		public class SellItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selling Item "+id);
			}
		}

		public class OrderItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Ordering Item "+id);
			}
		}

		public class RemoveItem implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Removing Item "+id);
			}
		}
	}

	public class AddItem implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public static void main(String args[])
	{
		ClientController client = new ClientController("localhost", 8428);
		GUI g = new GUI(client);
	}
}
