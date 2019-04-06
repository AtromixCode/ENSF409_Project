package ServerPackage.ServerControllers;

import Models.ItemModel;
import Models.OrderModel;
import Models.SupplierModel;
import java.util.ArrayList;

/**
 * inventory controller class.
 * this class is used to manipulate the inventory and the items inside it
 *
 * @author Victor Sanchez
 * @author Shamez Meghji
 * @author Jake Lui
 * @version 2.0
 * @since April 1, 2019
 */
public class InventoryController
{

    /**
     * the items kept by the inventory
     */
    protected ArrayList<ItemModel> items;

    /**
     * the data base controller to update it
     */
    private DataBaseController data;

    /**
     * constructs the inventory with items found on the data base
     * @param dc the data base controller gotten from the shop controller
     */
    public InventoryController(DataBaseController dc)
    {
        data = dc;
        items = dc.itemListFromDataBase();
    }


    /**
     * finds the item using the id, returns null if not found
     * @param id the id of the item to find
     * @return the item found with the matching id
     */
    public ItemModel findItem(int id)
    {
        for (ItemModel a: items) {
            if(a.getId()==id)
                return a;
        }
        return null;
    }

    /**
     * finds the item using the name/description
     * @param desc the name of the item to find
     * @return the item found or null if not found
     */
    protected ItemModel findItem(String desc)
    {
        for (ItemModel a: items){
            if (a.getDesc().equals(desc))
                return a;
        }
        return null;
    }

    /**
     * checks if there is a need to order items
     * @return true if there is a need to order
     */
    protected boolean checkIfOrder()
    {
        for (ItemModel i: items){
            if (i.getQuantity()<40)
                return true;
        }
        return false;
    }

    /**
     * generates an order and updates the necessary list
     * @param o the order model used to create an order line
     */
    protected void generateOrder(OrderModel o)
    {

        for (ItemModel i: items){
            if (i.getQuantity()< 40 && i.getSupplier()!= null){
                o.addLine(i);
                i.setQuantity(50);
                System.out.printf("item quntity = %d\n", i.getQuantity());
                data.addItem(i);
            }
        }
        data.updateOrderList(o.getOrderLines());
    }


    /**
     * updates a pre-existing item to a given item and adds it to the data base
     * @param item the item to be added
     */

    protected void updateItem (ItemModel item){
        for (ItemModel temp : items) {
            if(temp.getId() == item.getId()){
                temp.copyAttributes(item);
                data.addItem(item);
                return;
            }
        }
    }

    /**
     * joins the items and the suppliers by updating the suppliers in the items
     * @param supp the list of suppliers to add
     */
    public void updateItemsSuppliers (ArrayList<SupplierModel> supp){
        for (SupplierModel tempSupp: supp) {
            tempSupp.addItem(items);
        }
    }

    /**
     * removes an item from the item list
     * @param item the item to remove
     */
    protected void removeItem (ItemModel item){
        for (ItemModel temp :items) {
            if(temp.getId() == item.getId()){
                items.remove(temp);
                return;
            }
        }
    }

    /**
     * gets the list of items
     * @return the list of items
     */
    protected ArrayList<ItemModel> getItems()
    {
        return items;
    }

    /**
     * sets the list of items
     * @param listItem the items to set the list of items to
     */
    protected void updateItemList (ArrayList<ItemModel> listItem){

        items = listItem;
    }
}
