package GUI;

import COD_main.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.xml.crypto.dsig.Manifest;

public class viewOrders extends JPanel {

    JPanel jNorth = new JPanel();
    JPanel jCenter = new JPanel();
    JPanel jSouth = new JPanel();
    JList<List> availableOrders = new JList<List>();
    JList<List> staffOrders = new JList<List>();
    JScrollPane listScroll = new JScrollPane();
    JScrollPane listScroll2 = new JScrollPane();
    JButton takeOrder = new JButton("Take Order");
    static JButton goBack = new JButton("Back");
    ManagerDashboard md;

    viewOrders(ManagerDashboard m) {
        this.md = m;
        this.setPreferredSize(new Dimension(600, 400));
        this.setVisible(true);

        this.setLayout(new BorderLayout());
        this.add(jNorth, BorderLayout.NORTH);
        this.add(jCenter, BorderLayout.CENTER);
        this.add(jSouth, BorderLayout.SOUTH);

        listScroll = new JScrollPane(availableOrders);
        listScroll.setPreferredSize(new Dimension(300, 332));

        listScroll2 = new JScrollPane(staffOrders);
        listScroll2.setPreferredSize(new Dimension(300, 332));

        jNorth.setLayout(new BorderLayout());
        jNorth.add(goBack, BorderLayout.WEST);
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                md.removeOrdersPanel();
            }
        });

        jCenter.setLayout(new BorderLayout());
        jCenter.add(listScroll, BorderLayout.WEST);
        jCenter.add(listScroll2, BorderLayout.EAST);

        jSouth.setLayout(new FlowLayout());
        jSouth.add(takeOrder);

    }

}
