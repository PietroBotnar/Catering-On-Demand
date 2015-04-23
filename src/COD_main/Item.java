/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package COD_main;

/**
 *
 * @author Petru Botnar;
 */
//item class definition
//this class represents menu items
public class Item {
    private String item_id;
    private String title;
    private int preparationTime;
    private double price;
    private boolean isAvailable;
    private String menu_type;
    private int ordered_quantity = 0;
    
    //item constructor with complete set of fields
    public Item(String item_id, String title, int preparationTime, double price, boolean isAvailable, String menu_type) {
        this.item_id = item_id;
        this.title = title;
        this.preparationTime = preparationTime;
        this.price = price;
        this.isAvailable = isAvailable;
        this.menu_type = menu_type;
    }
    //item constructor with fewer fields
    public Item(String title, double price, int q){
        this.title = title;
        this.price = price;
        this.ordered_quantity = q;
    }
    
    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }
    
     public int getOrdered_quantity() {
        return ordered_quantity;
    }

    public void setOrdered_quantity(int ordered_quantity) {
        this.ordered_quantity = ordered_quantity;
    }
}
