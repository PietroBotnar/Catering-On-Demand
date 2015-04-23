package GUI;

import COD_main.User;
import database_conn.DbConnect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.dsig.Manifest;

//view returns class definition
//this class is used to view the returned orders from the manager dashboard
//and select them for acceptance or denial
public class viewReturns extends JPanel {

    JPanel jNorth = new JPanel();
    JPanel jCenter = new JPanel();
    JPanel jSouth = new JPanel();
    JPanel refunds = new JPanel();
    ArrayList<ReturnPanel> refundsList;
    JScrollPane listScroll = new JScrollPane();
    JButton acceptReturn = new JButton("Accept");
    JButton denyReturn = new JButton("Deny");
    static JButton goBack = new JButton("Back");
    ManagerDashboard md;
    DbConnect db = new DbConnect();
    GridBagConstraints gbc = new GridBagConstraints();
    Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    viewReturns(ManagerDashboard m) {
        this.md = m;
        this.setPreferredSize(new Dimension(600, 400));
        this.setVisible(true);

        this.setLayout(new BorderLayout());
        this.add(jNorth, BorderLayout.NORTH);
        this.add(jCenter, BorderLayout.CENTER);
        this.add(jSouth, BorderLayout.SOUTH);
        
        //a list with the returned order details is return from the database
        refundsList = db.getReturnedOrders();
        //creating layout
        refunds.setLayout(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        //for each panel in the list is added to the panel
        for (ReturnPanel rp : refundsList) {
            rp.setBorder(BorderFactory.createCompoundBorder(paddingBorder, paddingBorder));
            refunds.add(rp, gbc);
        }

        listScroll = new JScrollPane(refunds);
        listScroll.setPreferredSize(new Dimension(600, 332));

        //go back button implementation
        jNorth.setLayout(new BorderLayout());
        jNorth.add(goBack, BorderLayout.WEST);
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                md.removeReturnsPanel();
            }
        });

        jCenter.add(listScroll);

        jSouth.setLayout(new FlowLayout());
        jSouth.add(acceptReturn);
        jSouth.add(denyReturn);

        //accept return button registers the refund into database
        acceptReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                for (ReturnPanel rp : refundsList) {
                    if (rp.isSelected()) {
                        db.InsertRefund(rp.id);
                        rp.setVisible(false);
                    }
                }
            }
        });

        //deny return buttom implementation
        denyReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                for (ReturnPanel rp : refundsList) {
                    if (rp.isSelected()) {
                        db.CheckOrder(rp.id);
                        rp.setVisible(false);
                    }
                }
            }
        });
    }
}
