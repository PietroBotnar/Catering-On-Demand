/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import COD_main.Customer;
import COD_main.Item;
import COD_main.Order;
import database_conn.DbConnect;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Petru Botnar;
 */
//menu panel class definition
//this class is used to display the menu
//it has methods to hide and display the order functionalities
public class MenuPanel extends javax.swing.JPanel {

    //field definition and initialisation
    private JPanel lunch = new JPanel();
    private JPanel dinner = new JPanel();
    private JPanel breakfast = new JPanel();
    private JPanel drinks = new JPanel();
    private JPanel specials_lunch = new JPanel();
    private JPanel specials_dinner = new JPanel();
    private JPanel specials_breakfast = new JPanel();
    private JPanel drinks_breakfast = new JPanel();
    private JPanel drinks_lunch = new JPanel();
    private JPanel drinks_dinner = new JPanel();
    private JPanel vip_breakfast = new JPanel();
    private JPanel vip_lunch = new JPanel();
    private JPanel vip_dinner = new JPanel();
    private JScrollPane lunchScroll;
    private JScrollPane dinnerScroll;
    private JScrollPane breakfastScroll;
    private JScrollPane drinksScroll;
    private JScrollPane break_vipScroll;
    private JScrollPane lunch_vipScroll;
    private JScrollPane dinner_vipScroll;
    private JPanel bottomPanel = new JPanel();
    private Checkbox priorityCheck = new Checkbox("High Priority (extra 5% charge)");
    private JButton checkout = new JButton("Checkout");
    private JButton clear = new JButton("Clear");
    private boolean itemActive;
    private Date d = new Date();
    private MainFrame mf;
    private Customer customer;
    private ArrayList<ItemPanel> breakfastList = new ArrayList<>();
    private ArrayList<ItemPanel> lunchList = new ArrayList<>();
    private ArrayList<ItemPanel> dinnerList = new ArrayList<>();
    private Order order;
    CheckoutPanel checkoutPanel;
    private String vip_club;
    private ArrayList<Order> orders = new ArrayList<>();

    private DbConnect db = new DbConnect();

    /**
     * Creates new form MenuPanel
     */
    //menu panel constructor
    //arguments:
    //main frame, a boolean to display the order functionalities and a string for the vip club
    public MenuPanel(MainFrame mf, boolean b, String v) throws SQLException {
        itemActive = b;
        this.mf = mf;
        this.vip_club = v;
        
        //each menu section is populated through a for each loop,
        //for each item present in the database, a new ItemPanel object is created
        //to hold, select and display the item

        //lunch
        lunch.setLayout(new BoxLayout(lunch, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("lunch")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            lunch.add(ip);
            lunchList.add(ip);
        }
        lunchScroll = new JScrollPane(lunch);

        //specials lunch
        specials_lunch.setLayout(new BoxLayout(specials_lunch, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("lunch-special")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            specials_lunch.add(ip);
            lunchList.add(ip);
        }
        JScrollPane specials_lunchScroll = new JScrollPane(specials_lunch);

        //dinner
        dinner.setLayout(new BoxLayout(dinner, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("dinner")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            dinner.add(ip);
            dinnerList.add(ip);
        }
        dinnerScroll = new JScrollPane(dinner);

        //specials dinner
        specials_dinner.setLayout(new BoxLayout(specials_dinner, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("dinner-special")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            specials_dinner.add(ip);
            dinnerList.add(ip);
        }
        JScrollPane specials_dinnerScroll = new JScrollPane(specials_dinner);

        //breakfast
        breakfast.setLayout(new BoxLayout(breakfast, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("breakfast")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            breakfast.add(ip);
            breakfastList.add(ip);
        }
        breakfastScroll = new JScrollPane(breakfast);

        //specials breakfast
        specials_breakfast.setLayout(new BoxLayout(specials_breakfast, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("breakfast-special")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            specials_breakfast.add(ip);
            breakfastList.add(ip);
        }
        JScrollPane specials_breakfastScroll = new JScrollPane(specials_breakfast);

        //drinks breakfast
        drinks_breakfast.setLayout(new BoxLayout(drinks_breakfast, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("drink")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            drinks_breakfast.add(ip);
            breakfastList.add(ip);
        }
        JScrollPane breakfast_drinksScroll = new JScrollPane(drinks_breakfast);

        //drinks lunch
        drinks_lunch.setLayout(new BoxLayout(drinks_lunch, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("drink")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            drinks_lunch.add(ip);
            lunchList.add(ip);
        }
        JScrollPane lunch_drinksScroll = new JScrollPane(drinks_lunch);

        //drinks dinner
        drinks_dinner.setLayout(new BoxLayout(drinks_dinner, BoxLayout.Y_AXIS));
        for (Item i : db.PopulateMenu("drink")) {
            ItemPanel ip = new ItemPanel(i);
            ip.SetOrderButtonsVisibility(itemActive);
            drinks_dinner.add(ip);
            dinnerList.add(ip);
        }
        JScrollPane dinner_drinksScroll = new JScrollPane(drinks_dinner);

        //vip section
        //visibile only if the vip club passed as argument is not null
        if (vip_club != null) {

            //vip breakfast
            vip_breakfast.setLayout(new BoxLayout(vip_breakfast, BoxLayout.Y_AXIS));
            for (Item i : db.PopulateMenu(vip_club + "-breakfast")) {
                ItemPanel ip = new ItemPanel(i);
                ip.SetOrderButtonsVisibility(itemActive);
                vip_breakfast.add(ip);
                breakfastList.add(ip);
            }
            break_vipScroll = new JScrollPane(vip_breakfast);

            //vip lunch
            vip_lunch.setLayout(new BoxLayout(vip_lunch, BoxLayout.Y_AXIS));
            for (Item i : db.PopulateMenu(vip_club + "-lunch")) {
                ItemPanel ip = new ItemPanel(i);
                ip.SetOrderButtonsVisibility(itemActive);
                vip_lunch.add(ip);
                lunchList.add(ip);
            }
            lunch_vipScroll = new JScrollPane(vip_lunch);

            //vip dinner
            vip_dinner.setLayout(new BoxLayout(vip_dinner, BoxLayout.Y_AXIS));
            for (Item i : db.PopulateMenu(vip_club + "-dinner")) {
                ItemPanel ip = new ItemPanel(i);
                ip.SetOrderButtonsVisibility(itemActive);
                vip_dinner.add(ip);
                dinnerList.add(ip);
            }
            dinner_vipScroll = new JScrollPane(vip_dinner);
        }
        
        //declaration and initialisation of the tabbed panes
        JTabbedPane mainTab = new JTabbedPane();
        JTabbedPane breakfastTab = new JTabbedPane();
        JTabbedPane lunchTab = new JTabbedPane();
        JTabbedPane dinnerTab = new JTabbedPane();

        //population of tabbed panes
        mainTab.addTab("Breakfast", breakfastTab);
        breakfastTab.addTab("Regular", breakfastScroll);
        breakfastTab.addTab("Specials", specials_breakfastScroll);
        breakfastTab.addTab("Drinks", breakfast_drinksScroll);
        if (vip_club != null) {
            breakfastTab.addTab("VIP Offers(" + vip_club + ")", break_vipScroll);
        }

        mainTab.addTab("Lunch", lunchTab);
        lunchTab.addTab("Regular", lunchScroll);
        lunchTab.addTab("Specials", specials_lunchScroll);
        lunchTab.addTab("Drinks", lunch_drinksScroll);
        if (vip_club != null) {
            lunchTab.addTab("VIP Offers(" + vip_club + ")", lunch_vipScroll);
        }

        //remove menu sections after a certain hour
        //only in the customer menu
        if (b) {
            if (d.getHours() >= 12) {
                mainTab.remove(breakfastTab);
            }
            if (d.getHours() >= 17) {
                mainTab.remove(lunchTab);
            }
        }
        mainTab.addTab("Dinner", dinnerTab);
        dinnerTab.addTab("Regular", dinnerScroll);
        dinnerTab.addTab("Specials", specials_dinnerScroll);
        dinnerTab.addTab("Drinks", dinner_drinksScroll);
        if (vip_club != null) {
            dinnerTab.addTab("VIP Offers(" + vip_club + ")", dinner_vipScroll);
        }

        this.setLayout(new BorderLayout());
        checkout.setPreferredSize(new Dimension(300, 70));
        clear.setPreferredSize(new Dimension(70, 70));
        mainTab.setPreferredSize(new Dimension(300, 300));
        add(mainTab, BorderLayout.CENTER);
        //adding a panel at the bottom screen to position the checkout functionalities
        //clear button to clear selection, checkout button to proceed to the payment
        //a checkbox to select high priority
        bottomPanel.add(clear, BorderLayout.WEST);
        bottomPanel.add(checkout, BorderLayout.CENTER);
        bottomPanel.add(priorityCheck, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        //checkout button action listener implementation
        checkout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //when the button is pressed, 3 separate orders are initialised
                order = new Order(customer);
                Order breakfastOrder = new Order(customer);
                Order lunchOrder = new Order(customer);
                Order dinnerOrder = new Order(customer);

                //for each menu type, a search is made to include into the order
                //the selected items and set the propper order type
                for (ItemPanel ip : breakfastList) {
                    if (ip.quant > 0) {
                        breakfastOrder.addItem(ip.item);
                    }
                }
                breakfastOrder.setType("breakfast");

                for (ItemPanel ip : lunchList) {
                    if (ip.quant > 0) {
                        lunchOrder.addItem(ip.item);
                    }
                }
                lunchOrder.setType("lunch");

                for (ItemPanel ip : dinnerList) {
                    if (ip.quant > 0) {
                        dinnerOrder.addItem(ip.item);
                    }
                }
                dinnerOrder.setType("dinner");
                
                //if the orders are not with total price zero
                //are added to a list of orders
                if (breakfastOrder.getTotal_price() > 0) {
                    breakfastOrder.setPriority(priorityCheck.getState());
                    orders.add(breakfastOrder);
                }
                if (lunchOrder.getTotal_price() > 0) {
                    lunchOrder.setPriority(priorityCheck.getState());
                    orders.add(lunchOrder);
                }
                if (dinnerOrder.getTotal_price() > 0) {
                    dinnerOrder.setPriority(priorityCheck.getState());
                    orders.add(dinnerOrder);
                }

                //if the order list is not empty
                if (!orders.isEmpty()) {
                    //checkout panel is initialised with the list of orders and a reference to the customer
                    //finally added to the frame
                    checkoutPanel = new CheckoutPanel(getMf(), orders, customer);
                    getParent().remove(getThis());
                    getMf().add(checkoutPanel, BorderLayout.SOUTH);
                    getMf().pack();
                } else {
                    //if order list is empty, a message is displayed to the customer
                    JOptionPane.showMessageDialog(null, "Order is empty, please select some items!");
                }
            }
        });
        //clear button action listener, clears the current menu selection
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                reset();
            }
        });
    }

    //method to set the bottom panel visibility
    public void SetCheckoutVisible(boolean b) {
        bottomPanel.setVisible(b);
    }

    //metho to set the order buttons visibility
    public void SetOrderButtons(boolean b) {
        itemActive = b;
        revalidate();
    }

    public MainFrame getMf() {
        return mf;
    }

    public MenuPanel getThis() {
        return this;
    }

    public CheckoutPanel getCheckoutPanel() {
        return checkoutPanel;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //this metho iterates through all the menu items and resets the selection to default
    public void reset() {
        for (ItemPanel ip : breakfastList) {
            if (ip.quant > 0) {
                ip.reset();
            }
        }
        for (ItemPanel ip : lunchList) {
            if (ip.quant > 0) {
                ip.reset();
            }
        }
        for (ItemPanel ip : dinnerList) {
            if (ip.quant > 0) {
                ip.reset();
            }
        }
        orders.clear();
    }
}
