/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import COD_main.Customer;
import COD_main.Item;
import COD_main.Order;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 *
 * @author Petru Botnar;
 */
//checkout panel definition
//this class is used to display a summary of the items selected
public class CheckoutPanel extends javax.swing.JPanel {

    JPanel checkoutOrderPanel = new JPanel();
    JScrollPane scrollp;
    MainFrame mf;
    PaymentCheckoutPanel payment;
    ArrayList<Order> orders;
    Customer customer;

    //checkout panel constructor
    public CheckoutPanel(MainFrame m, ArrayList<Order> o, Customer c) {
        this.mf = m;
        this.orders = o;
        this.customer = c;
        //setting layout and padding border
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        checkoutOrderPanel.setLayout(new BoxLayout(checkoutOrderPanel, BoxLayout.Y_AXIS));
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        //for each order the customer makes(singular or multiple advanced)
        //the items ordered are displayed as labels
        for (Order order : orders) {
            for (Item i : order.getItems()) {
                JLabel l = new JLabel("x" + i.getOrdered_quantity() + " " + i.getTitle() + " £" + i.getOrdered_quantity() * i.getPrice());
                l.setBorder(BorderFactory.createCompoundBorder(paddingBorder, paddingBorder));
                checkoutOrderPanel.add(l);
            }
        }
        //initialiasing and adding containers 
        scrollp = new JScrollPane(checkoutOrderPanel);
        scrollp.setPreferredSize(new Dimension(300,150));
        payment = new PaymentCheckoutPanel(mf, orders, customer);
        add(scrollp);
        add(payment);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
