/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COD_main;

import database_conn.DbConnect;

/**
 *
 * @author Petru Botnar
 */
//Customer class that extends user object
//this class represents customers extending user type objects
public class Customer extends User {
//declaring the customer fields and defining initial values
    private double spending = 0;
    private boolean isLoggedIn = false;
    private String vip_status = null;
    private DbConnect db = new DbConnect();

    //customer constructor with fewer arguments
    public Customer(int user_id, String username, String password, double spending) {
        super(user_id, username, password);
        this.spending = spending;
        super.setType("customer");
    }
    //customer constructor with a complete set of fields
    public Customer(int user_id, String username, String password, double spending, String vip_status) {
        super(user_id, username, password);
        super.setType("customer");
        this.spending = spending;
        this.vip_status = vip_status;
    }
    //status getter
    public String getVip_status() {
        return vip_status;
    }
    //status setter with database update
    public void setVip_status(String vip_status) {
        this.vip_status = vip_status;
        db.SetVipStatus(super.getUser_id(), vip_status);
    }
    //spending getter
    public double getSpending() {
        return spending;
    }
    //spending getter with database update
    public void addSpending(double spending) {
        this.spending += spending;
        db.SetCustomerSpending(super.getUser_id(), this.spending);
    }
    //isLoggedIn getter
    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }
    //isLoggedIn setter with database update
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        db.LogCustomer(super.getUser_id(), isLoggedIn ? 1 : 0);
    }
}
