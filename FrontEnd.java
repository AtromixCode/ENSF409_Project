

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FrontEnd {
    private boolean run;
    private Scanner input;

    public FrontEnd()
    {
        run = false;
        input = new Scanner(System.in);
    }

    public void startProgram(Shop shop){
        run = true;
        System.out.println("Welcome to ShopManager! Please enter the filename where your supplier information is stored:");
        try {
            takeFileInput(shop);
            runFrontEnd(shop);
        } catch (IOException e){
            System.out.println("There was a problem with the file name entered or a failure in creating the orders.txt file. Exiting program...");
            System.exit(0);
        }
    }

    public void takeFileInput(Shop shop) throws IOException
    {
        String fileName = input.nextLine();
        shop.addSuppliersFromFile(fileName);
        System.out.println("Now please enter the filename where your inventory information is stored:");
        fileName = input.nextLine();
        shop.addInvFromFile(fileName);
        System.out.println("Successfully loaded suppliers and inventory and created orders.txt file.\n");
    }


    public void runFrontEnd(Shop shop) {
        try{
            mainMenu(shop);
        } catch(Exception e) {
            System.out.println("Input error, restarting...");
            String clear = input.nextLine();
            runFrontEnd(shop);
        }
    }


    public void mainMenu(Shop shop) throws Exception
        {
            while(run) {
                switch(menu()) {
                    case 1:
                        shopSell(shop);
                        break;
                    case 2:
                        shopView(shop);
                        break;
                    case 3:
                        shopSearch(shop);
                        break;
                    case 4:
                        quitProg(shop);
                        break;
                    default:
                        otherOption();
                        break;
                }
            }
    }

    public int menu () throws Exception
    {
        System.out.println("What would you like to do?\n" +
                "-Press 1 and Enter if a sale has been made.\n" +
                "-Press 2 and Enter to view all items and their quantities.\n" +
                "-Press 3 and Enter to search for an item to see its info & quantity.\n" +
                "-Press 4 and Enter to quit the program");
        int a = input.nextInt();
        String clear = input.nextLine();
        return a;
    }

    public void shopSell(Shop s)throws Exception
    {
        try {
            System.out.println("Please enter the item ID and quantity sold.");
            int id = input.nextInt();
            int quan = input.nextInt();
            String clear = input.nextLine();
            int success = s.sell(id, quan);
            shopSellResult(success);
            pressKey();
        }
        catch(FileNotFoundException e){
            System.out.println("Problem writing to orders.txt file. Quitting...");
            System.exit(0);
        }
    }

    public void shopSellResult(int success)
    {
        if (success==1)
            System.out.println("Successfully sold item and removed quantity!");
        else if (success==2)
            System.out.println("Item does not exist!");
        else
            System.out.println("You don't have enough quantity of this item to sell that amount!");
    }

    public void shopView(Shop s)
    {
        System.out.println("Here is a list of all items and their information:");
        s.printInventory();
        pressKey();
    }


    public void shopSearch(Shop s) throws Exception{
        int option;
        do {
            System.out.println("Press 1 and Enter to search by item ID or Press 2 and Enter to search by item name");
            option = input.nextInt();
            String clear = input.nextLine();
            if (option != 1 && option != 2)
                System.out.println("Invalid character entered, please try again.");
            else
                break;
        } while (true);
        shopSearchDo(option, s);
    }

    public void shopSearchDo(int option, Shop s) throws Exception
    {
        if (option == 1)
            findItemShopID(s);
        else
            findItemShopDesc(s);
        pressKey();
    }

    public void findItemShopID(Shop s) throws Exception
    {
        System.out.println("Enter item ID");
        int id = input.nextInt();
        String clear = input.nextLine();
        System.out.println(s.getItemInfo(id));
    }

    public void findItemShopDesc(Shop s) throws Exception
    {
        System.out.println("Enter item name");
        String name = input.nextLine();
        System.out.println(s.getItemInfo(name));
    }


    public void quitProg(Shop s)throws FileNotFoundException
    {
        s.closeOrderFile();
        System.out.println("Quitting ShopMaster...");
        run = false;
    }

    public void otherOption()
    {
        System.out.println("An invalid input was entered, please try again.");
        pressKey();
    }


    public void pressKey()
    {
        System.out.println( "\n----------------------------------------------------\n" +
                            "                 Press Enter to continue              \n" +
                            "------------------------------------------------------");
        String clear = input.nextLine();
    }



    public static void main(String[] args)
    {
	   ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
	   ArrayList<Order> orders = new ArrayList<Order>();
	   Inventory inventory = new Inventory();
	   Shop s = new Shop (inventory, suppliers, orders);
	   FrontEnd fe = new FrontEnd();
	   fe.startProgram(s);
    }
}
