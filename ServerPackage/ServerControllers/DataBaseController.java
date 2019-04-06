package ServerPackage.ServerControllers;

import Models.ItemModel;
import Models.OrderLineModel;
import Models.SupplierModel;

import java.sql.*;
import java.util.ArrayList;

/**
 * Data base controller class.
 * This class is in charge of making a connection with the database and manage any operations related to it.
 *
 * @author Jake Liu
 * @author Shamez Meghji
 * @author Victor Sanchez
 * @version 2.0
 * @since April 1, 2019
 */
public class DataBaseController {
    /**
     * The connection to the data base
     */
    private Connection dataCon;

    /**
     * the statement used to sent actions to the database server
     */
    private Statement statement;

    /**
     * The result set to receive all outputs of the data base
     */
    private ResultSet resultSet;

    /**
     * construct the data base with the
     */
    public DataBaseController () {
        try {
            dataCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "ENSFProjectPassword409!");
        }catch (java.sql.SQLException e){
            System.err.println("Error connecting to the data base");
            System.err.println(e.getMessage());
        }
    }

    /**
     * gets the list of items in the database and puts them in an array list to be used by the server and/or client
     * @return temp the list of items from the data base
     */
    protected synchronized ArrayList<ItemModel> itemListFromDataBase (){
        ArrayList<ItemModel> temp = new ArrayList<ItemModel>();
        try {
            statement = dataCon.createStatement();
            ResultSet rs =  statement.executeQuery("SELECT * FROM shop.items");
            while (rs.next()){
                ItemModel tempItem = new ItemModel(rs.getInt("ItemID"), rs.getString("Description"),
                        rs.getInt("Quantity"),rs.getFloat("Price"), rs.getInt("SupplierID"));
                temp.add(tempItem);
            }
        }catch (java.sql.SQLException e){
            System.err.println("Error while trying to get the list of items from the server");
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * gets the order lines list from the server and returns them to the server
     * @return orderLineList is the list of orders gotten from the data base
     */
    protected synchronized ArrayList<OrderLineModel> orderLineListFromDataBase (){
        ArrayList<OrderLineModel> orderLineList = new ArrayList<OrderLineModel>();
        try{
            statement = dataCon.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM shop.orderlines");
            while(rs.next()){
                OrderLineModel tempLine = new OrderLineModel(rs.getInt("OrderID"),rs.getString("DateOrdered"),
                        rs.getString("OrderDescription"));
            }

        }catch (java.sql.SQLException e){
            System.err.println("Error trying to retrieve the orders from the data base");
            e.printStackTrace();
        }

        return orderLineList;
    }

    /**
     * gets the supplier list from the data base
     * @return temp is the list of suppliers gotten from the data base
     */
    protected synchronized ArrayList<SupplierModel> supplierListFromDatabase (){
        ArrayList<SupplierModel> temp = new ArrayList<SupplierModel>();
        try {
            statement = dataCon.createStatement();
            ResultSet rs =  statement.executeQuery("SELECT * FROM shop.suppliers");
            while (rs.next()){
                SupplierModel tempSupplier = new SupplierModel(rs.getInt("SupplierID"), rs.getString("SupplierName"),
                        rs.getString("Address"),rs.getString("Contact"));
                temp.add(tempSupplier);
            }
        }catch (java.sql.SQLException e){
            System.err.println("Error while trying to get the list of suppliers from the server");
            e.printStackTrace();
        }
        return temp;
    }


    /**
     * adds an item to the data base
     * @param temp the item to add to the data base
     */
    protected synchronized void addItem (ItemModel temp){
        try {
            System.out.println(temp.getQuantity());
            String overrideQuerry = "SELECT * FROM items WHERE ItemID =" + temp.getId();
            statement = dataCon.createStatement();
            resultSet = statement.executeQuery(overrideQuerry);
            if (resultSet.next()) {
                String updateQuerry = "UPDATE items SET Description = ?, Quantity = ?, Price = ?, SupplierID = ? WHERE ItemID = ? ";
                PreparedStatement pStat = dataCon.prepareStatement(updateQuerry);

                pStat.setString(1, temp.getDesc());     //The description of the GIVEN item
                pStat.setInt(2, temp.getQuantity());    //The quantity of the GIVEN item
                pStat.setFloat(3, temp.getPrice());     //The price of the GIVEN item
                pStat.setInt(4, temp.getSupplierID());  //The suppliers of the GIVEN item
                pStat.setInt(5, temp.getId());          //The id of the GIVEN item
                pStat.executeUpdate();
            } else {

                insertItem(temp);
            }


        }catch (java.sql.SQLException e){
            System.err.println("Error adding items into the data base");
            e.printStackTrace();
        }


    }

    /**
     * adds a supplier to the data base, it overrides if existing
     * @param temp the supplier to add
     */
    private synchronized void addSupplier(SupplierModel temp){
        try {
            String overrideQuerry = "SELECT * FROM suppliers WHERE SupplierID ='" + temp.getId() + "'";
            statement = dataCon.createStatement();
            resultSet = statement.executeQuery(overrideQuerry);
            if (resultSet.next()) {
                String updateQuerry = "UPDATE suppliers SET SupplierName = ?, Address = ?, Contact = ? WHERE SupplierID = ? ";
                PreparedStatement pStat = dataCon.prepareStatement(updateQuerry);

                pStat.setString(1, temp.getCompanyName());
                pStat.setString(2, temp.getAddress());
                pStat.setString(3, temp.getSalesContact());
                pStat.setInt(4, temp.getId());

            } else {
                insertSupplier(temp);
            }
        }catch (java.sql.SQLException e){
            System.err.println("Error inserting a supplier into the database table");
        }
    }

    /**
     * updates the item list of the data base
     * @param updateItemList the list of items to input to the data controller
     */
    protected synchronized void updateItemList (ArrayList<ItemModel> updateItemList){
        try {
            statement = dataCon.createStatement();
            statement.executeUpdate("TRUNCATE TABLE items");

            for (ItemModel temp: updateItemList ) {
                insertItem(temp);
            }

        }catch (java.sql.SQLException e){
            System.err.println("Error updating the item table in the data base");
            e.printStackTrace();
        }
    }

    /**
     *updates the supplier list in the data base to a given list
     * @param updatedSupplierList the list to put into the data base
     */
    protected  synchronized void updateSupplierList (ArrayList<SupplierModel> updatedSupplierList){
        try {
            statement = dataCon.createStatement();
            statement.executeUpdate("TRUNCATE TABLE suppliers");

            for (SupplierModel temp: updatedSupplierList) {
                addSupplier(temp);
            }

        }catch (java.sql.SQLException e){
            System.err.println("Error updating the supplier table in the data base");
        }
    }

    /**
     * updates the list of orders in the data base to a given order list
     * @param updatedOrderList the list to put in the database
     */
    protected synchronized void updateOrderList (ArrayList<OrderLineModel> updatedOrderList){
        try {
            statement = dataCon.createStatement();
            statement.executeUpdate("TRUNCATE TABLE orderlines");
            System.out.println("here");

            for (OrderLineModel temp: updatedOrderList) {
                addOrderLine(temp);
            }

        }catch (java.sql.SQLException e){
            System.err.println("Error updating the Order  table in the data base");
        }
    }

    /**
     * adds an order to the data base
     * @param temp the order to be added
     */
    protected synchronized void addOrderLine (OrderLineModel temp){
        try {
            String insertQuery = "INSERT orderlines (OrderID, DateOrdered, OrderDescription) VALUES (?,?,?)";
            PreparedStatement pStat = dataCon.prepareStatement(insertQuery);

            pStat.setInt(1, temp.getOrderID());
            pStat.setString(2, temp.getDateString());
            pStat.setString(3, temp.getOrderLine());
            pStat.executeUpdate();

        }catch (java.sql.SQLException e){
            System.err.println("Error trying to insert an order line into the data base");
            e.printStackTrace();
        }

    }

    /**
     * inserts an item to the data base
     * @param temp the item to insert
     */
    private synchronized void insertItem (ItemModel temp ){
        try {
            String insertQuery = "INSERT items (ItemID, Description, Quantity, Price, SupplierID) VALUES (?,?,?,?,?)";
            PreparedStatement pStat = dataCon.prepareStatement(insertQuery);
            pStat.setInt(1, temp.getId());
            pStat.setString(2, temp.getDesc());
            pStat.setInt(3, temp.getQuantity());
            pStat.setFloat(4,temp.getPrice());
            pStat.setInt(5, temp.getSupplierID());

            pStat.executeUpdate();
        }catch (java.sql.SQLException e){
            System.err.println("Error inserting an item to the table");
            e.printStackTrace();
        }
    }


    /**
     * inserts a supplier to the data base
     * @param temp the supplier to be added to the data base
     */
    private  synchronized void insertSupplier (SupplierModel temp){
        try {
            String query = "INSERT suppliers (SupplierID, SupplierName, Address, Contact) VALUES (?,?,?,?)";
            PreparedStatement pStat = dataCon.prepareStatement(query);
            pStat.setInt(1, temp.getId());
            pStat.setString(2, temp.getCompanyName());
            pStat.setString(3, temp.getAddress());
            pStat.setString(4, temp.getSalesContact());
            pStat.executeUpdate();
        }catch (java.sql.SQLException e){
            System.err.println("Error inserting supplier to the data base");
            e.printStackTrace();
        }
    }


    /**
     * removes an item from the data base
     * @param item item to be removed
     */
    protected  synchronized void removeItem (ItemModel item){
        try {
            String querry = "DELETE FROM items WHERE itemID = ?";
            PreparedStatement pStat = dataCon.prepareStatement(querry);
            pStat.setInt(1, item.getId());
            pStat.execute();
        }catch (java.sql.SQLException e){
            System.err.println("Error removing an item from the data base, Already removed?");
            e.printStackTrace();
        }
    }
}

