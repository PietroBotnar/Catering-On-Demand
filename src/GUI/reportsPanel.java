package GUI;

import COD_main.User;
import database_conn.DbConnect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.xml.crypto.dsig.Manifest;

public class reportsPanel extends JPanel {

    //This class contains the buttons that generate the report tables and 
    //displays a JFrame
    JPanel jNorth = new JPanel();
    JPanel jCenter = new JPanel();
    JPanel jSouth = new JPanel();
    JList<List> reportList = new JList<List>();
    JScrollPane listScroll = new JScrollPane();
    JButton generateReport = new JButton("Generate Report");
    JButton customerSpending = new JButton("Customer Report");
    JButton loggedin = new JButton("Logged in Customers/Pending reports");
    JButton refund = new JButton("Refund Report");
    JButton perform = new JButton("Staff Performance");
    JButton viewPreviousReports = new JButton("View Previous Reports");
    JButton goBack = new JButton("Back");
    JButton print = new JButton("Print");
    ManagerDashboard md;
    MainFrame mf;
    DbConnect dbc = new DbConnect();
    
    reportsPanel(ManagerDashboard d, MainFrame mf) {
        
        //Sets the size of the JPanel and makes it visible
        this.md = d;
        this.mf = mf;
        this.setPreferredSize(new Dimension(1100, 500));
        this.setVisible(true);

        //Adds various Jpanel components in different areas
        this.setLayout(new BorderLayout());
        this.add(jNorth, BorderLayout.NORTH);
        this.add(jCenter, BorderLayout.CENTER);
        this.add(jSouth, BorderLayout.SOUTH);

        //Sets the JScrollPane to the result of the ReportsTable query
        listScroll = new JScrollPane(dbc.ReportsTable());
        listScroll.setPreferredSize(new Dimension(1100, 332));

        //Adds the listScrolls to the center of the JPanel	
        jCenter.add(listScroll);
        
        //Goes back to the previous JFrame
        jNorth.setLayout(new BorderLayout());
        jNorth.add(goBack, BorderLayout.WEST);
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                md.removeReportsPanel();

            }
        });

        //Fires the PritnActionPerformed method which prints the report when this 
        //button is pressed
        jNorth.add(print, BorderLayout.EAST);  
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PritnActionPerformed(e);       
            }
        });
        
        //Sets the position of the button and causes it to generate a general orders 
        //report when pressed
        jSouth.setLayout(new FlowLayout());
        jSouth.add(generateReport, BorderLayout.SOUTH);
        generateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dbc.MonthlyReport();
                
            }
        });
        //Sets the position of the button and causes it to generate a report of customers 
        //spending when pressed
          jSouth.setLayout(new FlowLayout());
        jSouth.add(customerSpending, BorderLayout.SOUTH);
        customerSpending.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dbc.SpendingReport();
                
            }
        });
        //Sets the position of the button and causes it to generate a report of those logged in when pressed
         jSouth.setLayout(new FlowLayout());
        jSouth.add(loggedin, BorderLayout.SOUTH);
        loggedin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dbc.LoggedReport();
                        
            }
        });
        //Sets the position of the button and causes it to generate a refund report when pressed
        jSouth.setLayout(new FlowLayout());
        jSouth.add(refund, BorderLayout.SOUTH);
        refund.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                dbc.RefundReport();
                        
            }
        });
        
    }
    //This button prints the report that's currently being looked at
    private void PritnActionPerformed(java.awt.event.ActionEvent e) 
    {
    // TODO add your handling code here:
        Toolkit tkp = this.getToolkit();
        PrintJob pjp = tkp.getPrintJob(mf, null, null);
        Graphics g = pjp.getGraphics();
        this.print(g);
        g.dispose();
        pjp.end();
        
    }

}
