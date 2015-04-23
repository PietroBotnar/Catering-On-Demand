/*
 * This class is the class that produces the panel that holds each individual 
    order that is displayed to the staff.
 */

package GUI;

import COD_main.Customer;
import COD_main.Item;
import COD_main.Order;
import COD_main.User;
import database_conn.DbConnect;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

/**
 *
 * @author Petru Botnar;
 */
public class StaffOrdersPanel extends javax.swing.JPanel {

    /**
     * Creates new form MyOrdersPanel and initalises variables
     */
    DbConnect db = new DbConnect();
    Customer customer;
    Border paddingBorder;
    private Date d = new Date();
    
    JPanel breakfastOrders;
    JPanel lunchOrders;
    JPanel dinnerOrders;
    
    JPanel breakfastOrdersP;
    JPanel lunchOrdersP;
    JPanel dinnerOrdersP;
    
    JScrollPane breakfastScroll;
    JScrollPane breakfastScrollP;
    
    JScrollPane lunchScroll;
    JScrollPane lunchScrollP;
    
    JScrollPane dinnerScroll;
    JScrollPane dinnerScrollP;
    User user;
    
    public StaffOrdersPanel(User u) {
        this.user = u;

        // initialize
        breakfastOrders = new JPanel();
        lunchOrders = new JPanel();
        dinnerOrders = new JPanel();
        
        breakfastOrdersP = new JPanel();
        lunchOrdersP = new JPanel();
        dinnerOrdersP = new JPanel();
        //setlayouts
        breakfastOrdersP.setLayout(new BoxLayout(breakfastOrdersP, BoxLayout.Y_AXIS));
        breakfastOrders.setLayout(new BoxLayout(breakfastOrders, BoxLayout.Y_AXIS));
        
        lunchOrdersP.setLayout(new BoxLayout(lunchOrdersP, BoxLayout.Y_AXIS));
        lunchOrders.setLayout(new BoxLayout(lunchOrders, BoxLayout.Y_AXIS));
                
        dinnerOrdersP.setLayout(new BoxLayout(dinnerOrdersP, BoxLayout.Y_AXIS));
        dinnerOrders.setLayout(new BoxLayout(dinnerOrders, BoxLayout.Y_AXIS));
        //add labels
        breakfastOrdersP.add(new JLabel("Priority Orders"));
        breakfastOrders.add(new JLabel("Regular Orders"));
        
        lunchOrdersP.add(new JLabel("Priority Orders"));
        lunchOrders.add(new JLabel("Regular Orders"));
        
        dinnerOrdersP.add(new JLabel("Priority Orders"));
        dinnerOrders.add(new JLabel("Regular Orders"));
        
        paddingBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        String status = "Pending";
        String breakfast = "breakfast";
        String lunch = "lunch";
        String dinner = "dinner";
        
         
        //for loop to get all the orders available
        for(Order o : db.getStaffOrders()){
            //check if status is pending and if its a priority order
           if((o.getPriority() == 1) && o.getStatus().equals(status)){
               
           OrderPanel op = new OrderPanel(o, this);
           op.setBorder(BorderFactory.createCompoundBorder(paddingBorder,paddingBorder));   
            //check what type the order is, depending on the type adds to appriopriate panel
                if(o.getType().equals(breakfast))
                        breakfastOrdersP.add(op);
                if(o.getType().equals(lunch))
                        lunchOrdersP.add(op);
                if(o.getType().equals(dinner))
                        dinnerOrdersP.add(op);
               
           }    
           //check if status is pending and if its a regular order
           if(o.getStatus().equals(status) && (o.getPriority() == 0))
           {
               
           OrderPanel op = new OrderPanel(o, this);
           op.setBorder(BorderFactory.createCompoundBorder(paddingBorder,paddingBorder));   
           //check what type the order is, depending on the type adds to appriopriate panel
                if(o.getType().equals(breakfast))
                        breakfastOrders.add(op);
                if(o.getType().equals(lunch))
                        lunchOrders.add(op);
                if(o.getType().equals(dinner))
                    dinnerOrders.add(op);                                    
           }
           
        }
        
        //adds the appriopriate panels to their jscrollpanes
         breakfastScroll = new JScrollPane(breakfastOrders);
         breakfastScroll.setPreferredSize(new Dimension(700, 200));
         breakfastScrollP = new JScrollPane(breakfastOrdersP);
         breakfastScrollP.setPreferredSize(new Dimension(700, 200));
    
         lunchScroll = new JScrollPane(lunchOrders);
         lunchScroll.setPreferredSize(new Dimension(600, 200));
         lunchScrollP = new JScrollPane(lunchOrdersP);
         lunchScrollP.setPreferredSize(new Dimension(700, 200));
         dinnerScroll = new JScrollPane(dinnerOrders);
         dinnerScroll.setPreferredSize(new Dimension(700, 200));
         dinnerScrollP = new JScrollPane(dinnerOrdersP);
         dinnerScrollP.setPreferredSize(new Dimension(700, 200));
         
         
       
        
        
 
         
         //creates a tabbed panel 
        JTabbedPane jtbExample = new JTabbedPane();
        
        //checks what time it is, to display the panels through out the day
        JPanel jplInnerPanel2 = null;
        JPanel jplInnerPanel1 = null;
      if (d.getHours() <= 12) {
          //creates a tab within the panel and adds the scroll panes to display the orders
            jplInnerPanel1 = createInnerPanel();
            jtbExample.addTab("Breakfast", null, jplInnerPanel1);
            jplInnerPanel1.setLayout(new BoxLayout(jplInnerPanel1, BoxLayout.Y_AXIS));
            jplInnerPanel1.add(breakfastScroll);
            jplInnerPanel1.add(breakfastScrollP);

        }

       if (d.getHours() <= 17) {
            jtbExample.remove(jplInnerPanel1);
            jplInnerPanel2 = createInnerPanel();
            jtbExample.addTab("Lunch", null, jplInnerPanel2);
            jplInnerPanel2.setLayout(new BoxLayout(jplInnerPanel2, BoxLayout.Y_AXIS));
            jplInnerPanel2.add(lunchScroll);
            jplInnerPanel2.add(lunchScrollP);

        }

        JPanel jplInnerPanel3 = createInnerPanel();
        jtbExample.addTab("Dinner", null, jplInnerPanel3, "Tab 3");
        jplInnerPanel3.setLayout(new BoxLayout(jplInnerPanel3, BoxLayout.Y_AXIS));
        jplInnerPanel3.add(dinnerScroll);
        jplInnerPanel3.add(dinnerScrollP);
        
        //this.setPreferredSize(new Dimension(500,500));
        this.add(jtbExample);
    }
    
    //method used to update the orders, repaints and revalidates the panels
    public void updateOrders(){
      
        
        breakfastOrders.repaint();
        breakfastOrdersP.repaint();
        breakfastOrders.revalidate();
        breakfastOrdersP.revalidate();
        
        lunchOrders.repaint();
        lunchOrdersP.repaint();
        lunchOrders.revalidate();
        lunchOrdersP.revalidate();
        
        dinnerOrders.repaint();
        dinnerOrdersP.repaint();
        dinnerOrders.revalidate();
        dinnerOrdersP.revalidate();
        
        
        repaint();
        revalidate();
        
    }
    
    //method used to create the tabs for the tabbed panel
    protected JPanel createInnerPanel() {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new GridLayout(1, 1));
        return jplPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
