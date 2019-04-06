package ServerPackage.ServerControllers;

import Models.ItemModel;
import Models.OrderModel;
import Models.SupplierModel;
import java.util.ArrayList;

/**
 * Inventory controller class.
 * This class is used to manipulate the inventory and the items inside it.
 *
 * @author Victor Sanchez
 * @author Shamez Meghji
 * @author Jake Lui
 * @version 2.0
 * @since April 5, 2019
 */
public class InventoryController
{

    /**
     * The items kept by the inventory.
     */
    protected ArrayList<ItemModel> items;

    /**
     * The database controller to update it.
     */
    private DataBaseController data;

    /**
     * Constructs the inventory with items found on the database.
     * @param dc the database controller gotten from the shop controller.
     */
    public InventoryController(DataBaseController dc)
    {
        data = dc;
        items = dc.itemListFromDataBase();
    }


    /**
     * Finds the item using the id, returns null if not found.
     *
     * @param id the id of the item to find.
     * @return the item found with the matching id.
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
     * Finds the item using the name/description.
     *
     * @param desc the name of the item to find.
     * @return the item found or null if not found.
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
     * Checks if there is a need to order items.
     * @return true if there is a need to order.
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
     * Generates an order and updates the necessary list.
     * @param o the order model used to create an order line.
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
     * Updates a pre-existing item to a given item and adds it to the database.
     * @param item the item to be added.
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
     * Joins the items and the suppliers by updating the suppliers in the items.
     * @param supp the list of suppliers to add.
     */
    public void updateItemsSuppliers (ArrayList<SupplierModel> supp){
        for (SupplierModel tempSupp: supp) {
            tempSupp.addItem(items);
        }
    }

    /**
     * Removes an item from the item list.
     * @param item the item to remove.
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
     * Gets the list of items.
     * @return the list of items.
     */
    protected ArrayList<ItemModel> getItems() { return items; }

    /**
     * Sets the list of items.
     * @param listItem the items to set the list of items to.
     */
    protected void updateItemList (ArrayList<ItemModel> listItem){ items = listItem; }
}
