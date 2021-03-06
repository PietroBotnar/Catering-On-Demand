/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import COD_main.Customer;
import COD_main.Order;
import COD_main.User;
import database_conn.DbConnect;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Petru Botnar;
 */
//customer dashboard constructor
//this class is used to display the customer's use cases
public class CustomerDashboard extends javax.swing.JPanel {

    Customer customer;
    MainFrame mf;
    MenuPanel menu;
    AccountPanel account;
    MyOrdersPanel orders;
    DbConnect db = new DbConnect();
    boolean isVip = true;
    ArrayList<Integer> clubThreshold;

    //customer dashborad contructor
    public CustomerDashboard(Customer customer, MainFrame mf, boolean vip) throws SQLException {
        this.customer = customer;
        this.mf = mf;
        this.isVip = vip;
        this.customer.setIsLogged(true);
        //get vip threshold information
        clubThreshold = db.GetVipThreshold();

        //if customer has spent over a certain threshold vip status is set accordingly
        if (customer.getSpending() > clubThreshold.get(0)) {
            if (customer.getSpending() > clubThreshold.get(1)) {
                if (customer.getSpending() > clubThreshold.get(2)) {
                    customer.setVip_status("diamond");
                } else {
                    customer.setVip_status("gold");
                }
            } else {
                customer.setVip_status("silver");
            }
            isVip = true;
        }else{
            customer.setVip_status(null);
            isVip = false;
        }
        //intialising account panel
        account = new AccountPanel(customer, isVip);
        initComponents();
        //displaying hello message to the customer
        jLabel1.setText("Hello " + customer.getUsername() + " !!!");
        //intialising menu panel
        menu = new MenuPanel(mf, true, customer.getVip_status());
        //adding menu to main frame
        mf.add(menu, BorderLayout.SOUTH);
        mf.pack();
        //setting customer to view the correct menu
        menu.setCustomer(customer);
        //initialising customer's orders panel
        orders = new MyOrdersPanel(customer);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        ordersButton = new javax.swing.JButton();
        accountButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();

        jLabel1.setText("Hello Customer!");

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        ordersButton.setText("myOrders");
        ordersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordersButtonActionPerformed(evt);
            }
        });

        accountButton.setText("myAccount");
        accountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountButtonActionPerformed(evt);
            }
        });

        menuButton.setText("View Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ordersButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(menuButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(accountButton))
                    .addComponent(jLabel1))
                .addGap(38, 38, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(logoutButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ordersButton)
                    .addComponent(menuButton)
                    .addComponent(accountButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
//when logout button is pressed, showing panels are removed
        if (account.isShowing()) {
            mf.remove(account);
        }
        if (menu.checkoutPanel != null) {
            mf.remove(menu.checkoutPanel);
        }
        if (orders.isShowing()) {
            mf.remove(orders);
        }
        if (menu.isShowing()) {
            mf.remove(menu);
        }
        if (orders.isShowing()) {
            mf.remove(orders);
        }
        mf.remove(this);
        //calling method to display the initial home dashboard
        mf.homeDash();
        //log off the customer
        this.customer.setIsLoggedIn(false);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void accountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountButtonActionPerformed
//when my account button is pressed, showing panels are removed and account panel with updated details is added
        if (menu.checkoutPanel != null) {
            mf.remove(menu.checkoutPanel);
        }
        if (orders.isShowing()) {
            mf.remove(orders);
        }
        if (!account.isShowing()) {
            mf.add(account, BorderLayout.SOUTH);
            account.UpdateDetails();
        }
        if (menu.isShowing()) {
            mf.remove(menu);
        }

        mf.pack();
    }//GEN-LAST:event_accountButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        //when view menu button is pressed, showing panels are removed and menu panel is added
        if (menu.checkoutPanel != null) {
            mf.remove(menu.checkoutPanel);
        }
        if (account.isShowing()) {
            mf.remove(account);
        }
        if (orders.isShowing()) {
            mf.remove(orders);
        }
        if (!menu.isShowing()) {
            mf.add(menu, BorderLayout.SOUTH);
        }
        mf.pack();
    }//GEN-LAST:event_menuButtonActionPerformed

    private void ordersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordersButtonActionPerformed
        //when my orders button is pressed, showing panels are removed and an updated orders panel is added
        if (menu.checkoutPanel != null) {
            mf.remove(menu.checkoutPanel);
            orders.updateOrders();
        }
        if (account.isShowing()) {
            mf.remove(account);
            orders.updateOrders();
        }
        if (menu.isShowing()) {
            mf.remove(menu);
            orders.updateOrders();
        }
        if (!orders.isShowing()) {
            mf.add(orders, BorderLayout.SOUTH);
            orders.updateOrders();
        }
        mf.pack();

    }//GEN-LAST:event_ordersButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton menuButton;
    private javax.swing.JButton ordersButton;
    // End of variables declaration//GEN-END:variables
}
