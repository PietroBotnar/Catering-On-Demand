/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import COD_main.User;
import database_conn.DbConnect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

public class ManageAccount extends JPanel{
    
    //This class contains the buttons that generate the ManageAccount tables and 
    //displays a JFrame
        JPanel jNorth = new JPanel();
	JPanel jCenter = new JPanel();
	JPanel jSouth = new JPanel();
        JList<List> menuList = new JList<List>();
	JScrollPane listScroll = new JScrollPane();
        JScrollPane listScroll2 = new JScrollPane();
        JScrollPane listScroll3 = new JScrollPane();
	JButton deleteCustomer = new JButton("Delete Customer");
        JButton deleteStaff = new JButton("Delete Staff");
        JButton AddStaff = new JButton("Add Staff");
	JButton goBack = new JButton("Back");
        ManagerDashboard md;
        DbConnect dbc = new DbConnect();  
        
	ManageAccount(ManagerDashboard m){
          try{  
              
              //Sets the size of the JPanel and makes it visible
                this.md = m;
                this.setPreferredSize(new Dimension(800, 550));
		this.setVisible(true);
		
                //Adds various Jpanel components in different areas
		this.setLayout(new BorderLayout());
		this.add(jNorth, BorderLayout.NORTH);
		this.add(jCenter, BorderLayout.CENTER);
		this.add(jSouth, BorderLayout.SOUTH);                       
               
                //Creates new JScrollpanes containing the results of the ManageAcc query
                listScroll = new JScrollPane(dbc.ManageAcc());
		listScroll.setPreferredSize(new Dimension(800, 300));
                listScroll2 = new JScrollPane(dbc.ManageAcc2());
                listScroll2.setPreferredSize(new Dimension(600, 200));
                
                //Goes back to the previous JFrame
		jNorth.setLayout(new BorderLayout());
		jNorth.add(goBack, BorderLayout.WEST);
	    goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
         md.removeManageAccount();
            
            }
        }); 
		//Adds the listScrolls to the center of the JPanel		
		jCenter.add(listScroll);  
                jCenter.add(listScroll2);		
                
                //Fires the DeleteFromMenu method which allows the user to remove 
                //a staff member from the database and JTable as long as it's not an owner or manager
                jSouth.setLayout(new FlowLayout());
		jSouth.add(deleteStaff, BorderLayout.SOUTH);
                deleteStaff.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        
                        dbc.DeleteStaff();
                        
                    }
                });
                
                //Fires the AddStaff method which allows the user to add a new staff member
                //to the database and JTable
                jSouth.setLayout(new FlowLayout());
		jSouth.add(AddStaff, BorderLayout.SOUTH);
                AddStaff.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                    
                        dbc.AddStaff();
                                               }
                });          
                }catch(Exception ex){
                 System.out.println(ex);
}         
        }
    
}
