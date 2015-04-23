package GUI;

import database_conn.DbConnect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class editMenuPanel extends JPanel{
	
     //This class contains the buttons that generate the menu tables and 
    //displays a JFrame
	JPanel jNorth = new JPanel();
	JPanel jCenter = new JPanel();
	JPanel jSouth = new JPanel();
	JList<List> menuList = new JList<List>();
	JScrollPane listScroll = new JScrollPane();
	JButton AddtoMenu = new JButton("Add to Menu");
	JButton DeleteFromMenu = new JButton("Delete from Menu");
        JButton goBack = new JButton("Back");
        ManagerDashboard md;
        DbConnect dbc = new DbConnect();
        
	editMenuPanel(ManagerDashboard d){
            
            try { 
            
                //Sets the size of the JPanel and makes it visible
		this.md = d;
		this.setPreferredSize(new Dimension(600, 400));
		this.setVisible(true);
		
                //Adds various Jpanel components in different areas
		this.setLayout(new BorderLayout());
		this.add(jNorth, BorderLayout.NORTH);
		this.add(jCenter, BorderLayout.CENTER);
		this.add(jSouth, BorderLayout.SOUTH);
		
                //Sets the JScrollPane to the result of the EditMenu query
		listScroll = new JScrollPane(dbc.EditMenu());
		listScroll.setPreferredSize(new Dimension(600, 332));

                //Goes back to the previous JFrame
		jNorth.setLayout(new BorderLayout());
		jNorth.add(goBack, BorderLayout.WEST);
	    goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
           md.removeEditMenuPanel();
            
            }
        }); 
				
                //Adds the listScroll to the center of the JPanel	
		jCenter.add(listScroll);
		
                //Fires the AddStock method which allows the user to add a new menu item to the database
                //and JTable
		jSouth.setLayout(new FlowLayout());
		jSouth.add(AddtoMenu);
                AddtoMenu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    dbc.AddMenu();
         
                }
            });
                
                //Fires the DeleteFromMenu method which allows the user to remove 
                //a stock item from the database and JTable
                jSouth.setLayout(new FlowLayout());
		jSouth.add(DeleteFromMenu);
                DeleteFromMenu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    dbc.DeleteFromMenu();
         
                }
            });
                
                
                 }catch(
                         Exception ex){
                 System.out.println(ex);
}      	
		
	}

}
