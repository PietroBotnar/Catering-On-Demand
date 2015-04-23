/*
 * class that displays the stock to the staff
 */
package GUI;

import database_conn.DbConnect;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Vic
 */
public class viewStock_Staff extends javax.swing.JPanel {

    /**
     * Creates new form viewStock_Staff
     */
    JPanel jNorth = new JPanel();
    JPanel jCenter = new JPanel();
    JPanel jSouth = new JPanel();
    JTable t = new JTable();
    JList<List> menuList = new JList<List>();
    JScrollPane listScroll = new JScrollPane();
    JButton editStock = new JButton("Add Stock");
    JButton goBack = new JButton("Back");
    ManagerDashboard md;
    StaffDashboard sd;
    DbConnect dbc = new DbConnect();
    MainFrame mf;
    
    
    public viewStock_Staff() {
        initComponents();
        
        try {
            //produces a jscroller panel which then fetches a jtable form the database to display the stock
            this.setPreferredSize(new Dimension(600, 400));
            this.setVisible(true);

            this.setLayout(new BorderLayout());
            this.add(jCenter, BorderLayout.CENTER);
            listScroll = new JScrollPane(dbc.ShowStuff("stock"));
            listScroll.setPreferredSize(new Dimension(600, 332));
            jCenter.add(listScroll);         

        } catch (Exception ex) {
            System.out.println(ex);
        }   
   
    }
    @SuppressWarnings("unchecked")
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
