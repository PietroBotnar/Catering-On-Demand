package GUI;

import COD_main.User;
import database_conn.DbConnect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.xml.crypto.dsig.Manifest;

public class viewStock extends JPanel{
	
         //This class contains the buttons that generate the stock tables and 
         //displays a JFrame
	JPanel jNorth = new JPanel();
	JPanel jCenter = new JPanel();
	JPanel jSouth = new JPanel();
	JList<List> menuList = new JList<List>();
	JScrollPane listScroll = new JScrollPane();
	JButton editStock = new JButton("Add Stock");
	JButton goBack = new JButton("Back");
        ManagerDashboard md;
        DbConnect dbc = new DbConnect();            
        
	viewStock(ManagerDashboard m){
            try { 
                
                //Sets the size of the JPanel and makes it visible
                this.md = m;
                this.setPreferredSize(new Dimension(600, 400));
		this.setVisible(true);
		
                //Adds various Jpanel components in different areas
		this.setLayout(new BorderLayout());
		this.add(jNorth, BorderLayout.NORTH);
		this.add(jCenter, BorderLayout.CENTER);
		this.add(jSouth, BorderLayout.SOUTH);                       
                
                //Sets the JScrollPane to the result of the ShowStuff query               
                listScroll = new JScrollPane(dbc.ShowStuff("stock"));
		listScroll.setPreferredSize(new Dimension(600, 332));
                
                //Goes back to the previous JFrame
		jNorth.setLayout(new BorderLayout());
		jNorth.add(goBack, BorderLayout.WEST);
	    goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
         md.removeStocksPanel();
            
            }
        }); 
		//Adds the listScroll to the center of the JPanel			
		jCenter.add(listScroll);  
               
                //Fires the AddStock method which allows the user to add a new stock item to the database
                //and JTable
		jSouth.setLayout(new FlowLayout());
		jSouth.add(editStock); 
                editStock.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                   dbc.AddStock();
                }
            });
                
                }catch(Exception ex){
                 System.out.println(ex);
}      
                
        }
        }


