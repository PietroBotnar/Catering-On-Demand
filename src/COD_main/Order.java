/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COD_main;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Petru Botnar;
 */
//order class definition
//this class represents an order
public class Order {

    private ArrayList<Item> order = new ArrayList<>();
    private int order_id;
    private int customer_id;
    private double total_price;
    private Customer customer;
    private boolean priority;
    private String status = "Pending";
    private String time;
    private boolean returned = false;
    private String type;
    
    //order constructor with customer only
    public Order(Customer c) {
        this.customer = c;
    }
    //order constructor with complete set of fields
    public Order(int order_id, int customer_id, boolean priority, String status, double total_price, String time, String type) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.priority = priority;
        this.status = status;
        this.total_price = total_price;
        this.time = time;
        this.type = type;
    }
    //method to add item to the order
    public void addItem(Item i) {
        order.add(i);
        //total price of the order is updated
        total_price += i.getPrice() * i.getOrdered_quantity();
    }

    public double getTotal_price() {
        return total_price;
    }

    public ArrayList<Item> getItems() {
        return order;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
    //priority getter that returns the equivalent integer
    public int getPriority() {
        return (priority) ? 1 : 0;
    }
    //priority setter that adds extra %5 charge
    public void setPriority(boolean priority) {
        this.priority = priority;
        if (priority) {
            double extra = total_price * 0.05;
            total_price += extra;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public boolean isPriority() {
        return priority;
    }

    public String getTime() {
        return time;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
    //method to add a discount to the order, updates the total price
    public void addDiscount(double discount) {
        if (discount != 0) {
            double discount_amount = total_price * (discount / 100);
            total_price -= discount_amount;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
