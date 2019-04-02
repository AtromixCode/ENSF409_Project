package client.ClientViews;


class GUI {

	private MainView mv;
	private SupplierView sv;

	protected GUI(){
		mv = new MainView();
		sv = new SupplierView();
	}


	public static void main(String args[])
	{
		GUI g = new GUI();
	}
}
