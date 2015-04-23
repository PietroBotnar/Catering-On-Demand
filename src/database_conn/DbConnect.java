/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_conn;

import COD_main.Customer;
import COD_main.Item;
import COD_main.Order;
import COD_main.User;
import GUI.ReturnPanel;
import java.awt.HeadlessException;

import java.awt.List;
import java.io.IOException;
import java.sql.*;
import static java.sql.Types.NULL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class DbConnect {

    private Connection con;
    private Statement st;
    private ResultSet rt;
        
    JTable t = new JTable();
    JTable t2 = new JTable();

    String user;
    String pass;
    String url;
    String name;

    private int last_order_id;
    private DecimalFormat df = new DecimalFormat("#.00");

    public DbConnect() {

        user = "root";
        pass = "botnar";
        url = "jdbc:mysql://localhost:3306/11express";
        name = "11express";

        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
    }

    public void InsertCustomer(String username, String name, String surname, String password, String phone) {
        try {
            String query = "INSERT INTO `11express`.`customer` (`username`, `name`, `surname`, `password`, `vipstatus`, `spending_balance`, `login_status`, `phone_number`) "
                    + "VALUES ('" + username + "', '" + name + "', '" + surname + "', '" + password + "', NULL, '0', '0','" + phone + "');";
            st.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public boolean isUsernameAvailable(String username) {
        try {
            String query = "SELECT username FROM customer WHERE username='" + username + "';";
            rt = st.executeQuery(query);
            if (rt.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public Customer SearchCustomer(String username, String password) throws SQLException {
        Customer customer = null;
        int id = 0;
        String u = null;
        String p = null;
        double b = 0;
        double credit = 0;
        String vip = null;
        try {
            String query = "SELECT * FROM customer WHERE username = '" + username + "' AND password = '" + password + "';";
            rt = st.executeQuery(query);
            while (rt.next()) {
                id = rt.getInt("customer_id");
                u = rt.getString("username");
                p = rt.getString("password");
                b = rt.getDouble("spending_balance");
                vip = rt.getString("vipstatus");
            }
            customer = new Customer(id, u, p, b, vip);
            return customer;
        } catch (Exception e) {
            System.out.println(e);
        }
        return customer;
    }

    public User SearchUser(String username, String password) throws SQLException {
        User user = null;
        int id = 0;
        String u = null;
        String p = null;
        String type = null;
        try {
            String query = "SELECT * FROM staff WHERE username = '" + username + "' AND password = '" + password + "';";
            rt = st.executeQuery(query);
            while (rt.next()) {
                id = rt.getInt("staff_id");
                u = rt.getString("username");
                p = rt.getString("password");
                type = rt.getString("type");
                user = new User(id, u, p, type);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public ArrayList<Item> PopulateMenu(String type) throws SQLException {
        ArrayList<Item> results = new ArrayList<Item>();
        try {
            String query = "SELECT * FROM menu WHERE type = '" + type + "'";
            rt = st.executeQuery(query);
            while (rt.next()) {
                Item item = new Item(rt.getString("menu_id"), rt.getString("title"), rt.getInt("preparationTime"), rt.getDouble("price"), rt.getBoolean("isAvailable"), rt.getString("type"));
                results.add(item);
            }
        } catch (Exception e) {

        }

        return results;
    }

    public void databaseBackup(String path) {
        Process runtimeProcess;
        String executeCmd = "C:\\wamp\\bin\\mysql\\mysql5.6.17\\bin\\mysqldump -u" + user + " -p" + pass
                + " --add-drop-database -B " + name + " -r" + path;

        //      ^ if pass used : " -p" + pass +
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("backup");
                JOptionPane.showMessageDialog(null, "Backup successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Backup unsuccessful!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //JOptionPane.showMessageDialog(null, "Nope");
    }

    public void databaseRestore(String path) {
        String[] executeCmd = new String[]{"C:\\wamp\\bin\\mysql\\mysql5.6.17\\bin\\mysql", "--user=" + user, "--password=" + pass, name, "-e", " source " + path};

        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Restored successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Could not restore the backup!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InsertOrder(Order order) {
        try {
            String query = "INSERT INTO `11express`.`orders` (`order_id`, `customer_id`, `order_timedate`, `order_priority`, `order_status`, `tot_amount`, `order_type`) VALUES (NULL, '" + order.getCustomer().getUser_id() + "', CURRENT_TIMESTAMP, b'" + order.getPriority() + "', '" + order.getStatus() + "', '" + order.getTotal_price() + "', '"+order.getType()+"');";
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rt = st.getGeneratedKeys();
            while (rt.next()) {
                last_order_id = rt.getInt(1);
            }
            for (Item i : order.getItems()) {
                String query2 = "INSERT INTO `11express`.`order_items` (`id`,`order_id`, `menu_id`, `quantity`) VALUES (NULL ,'" + last_order_id + "', '" + i.getItem_id() + "', '" + i.getOrdered_quantity() + "');";
                st.executeUpdate(query2);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<Order> getOrders(Customer c) {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String query = "SELECT * FROM orders WHERE customer_id = '" + c.getUser_id() + "'";
            rt = st.executeQuery(query);
            while (rt.next()) {
                Order o = new Order(rt.getInt("order_id"), rt.getInt("customer_id"), rt.getBoolean("order_priority"), rt.getString("order_status"), rt.getDouble("tot_amount"), rt.getTimestamp("order_timedate").toString(), rt.getString("order_type"));
                o.setReturned(rt.getBoolean("returned"));
                orders.add(o);
            }
            return orders;
        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    public ArrayList<Item> getOrderItems(Customer c, Order o) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            String query = "SELECT menu.title, menu.price, order_items.quantity FROM order_items "
                    + "INNER JOIN menu ON order_items.menu_id=menu.menu_id INNER JOIN orders ON order_items.order_id=orders.order_id "
                    + "WHERE orders.customer_id='" + c.getUser_id() + "' AND orders.order_id='" + o.getOrder_id() + "'";
            rt = st.executeQuery(query);
            while (rt.next()) {
                Item i = new Item(rt.getString("title"), rt.getDouble("price"), rt.getInt("quantity"));
                items.add(i);
            }
            return items;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void InsertReturn(Order o) {
        try {
            String query = "UPDATE `11express`.`orders` SET `returned` = '1' WHERE `orders`.`order_id` = " + o.getOrder_id() + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void InsertPayment(String name, String cn, String ccv, String exp, String address) {
        try {
            String query = "INSERT INTO `11express`.`payment` (`payment_id`, `order_id`, `name_on_card`, `credit_card_number`, `security_number`, `payment_datetime`, `address`, `exp`) "
                    + "VALUES (NULL, '" + last_order_id + "', '" + name + "', '" + cn + "', '" + ccv + "', CURRENT_TIMESTAMP, '" + address + "', '" + exp + "');";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LogCustomer(int id, int b) {
        try {
            String query = "UPDATE `11express`.`customer` SET `login_status` = '" + b + "' WHERE `customer`.`customer_id` = " + id + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SetCustomerSpending(int id, double spending) {
        try {
            String query = "UPDATE `11express`.`customer` SET `spending_balance` = '" + spending + "' WHERE `customer`.`customer_id` = " + id + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SetVipStatus(int id, String status) {
        try {
            String query = "UPDATE `11express`.`customer` SET `vipstatus` = '" + status + "' WHERE `customer`.`customer_id` = " + id + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> GetCustomerDetails(int id) {
        ArrayList<String> details = new ArrayList<>();
        try {
            String query = "SELECT * FROM customer WHERE customer_id = '" + id + "'";
            rt = st.executeQuery(query);
            while (rt.next()) {
                details.add(rt.getString("name"));
                details.add(rt.getString("surname"));
                details.add(df.format(rt.getDouble("spending_balance")));
                details.add(rt.getString("phone_number"));
                details.add(rt.getString("vipstatus"));
            }
            return details;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Order> getStaffOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            String query = "SELECT * FROM orders";
            rt = st.executeQuery(query);
            while (rt.next()) {
                Order o = new Order(rt.getInt("order_id"), rt.getInt("customer_id"), rt.getBoolean("order_priority"), rt.getString("order_status"), rt.getDouble("tot_amount"), rt.getTimestamp("order_timedate").toString(), rt.getString("order_type"));
                orders.add(o);
            }
            return orders;
        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

   public JTable ShowStuff(String DbTable) {
        try {
            String query = "SELECT * FROM " + DbTable + "";
            rt = st.executeQuery(query);
            
            t.setModel(buildTableModel(rt));

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return t;
    }

     public JTable ManageAcc() {
        try {
            String query = ("SELECT * FROM customer");
            rt = st.executeQuery(query);

            t.setModel(buildTableModel(rt));
            
            t.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    try {
                        String columnName = t.getColumnName(e.getColumn());
                        int selectedColumn = t.getSelectedColumn();
                        int selectedRow = t.getSelectedRow();
                        Object selected = t.getValueAt(selectedRow, selectedColumn);
                        String entered = selected.toString();

                        String query2 = "UPDATE customer SET " + columnName + "='" + entered + "' WHERE customer_id =" + t.getValueAt(selectedRow, 0) + "";
                        st.executeUpdate(query2);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
return t;
        
    }

    public JTable ManageAcc2() {
        try {
            String query = ("SELECT * FROM staff");
            rt = st.executeQuery(query);

            t2.setModel(buildTableModel(rt));
                        
            t2.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    try {
                        String columnName = t2.getColumnName(e.getColumn());
                        int selectedColumn = t2.getSelectedColumn();
                        int selectedRow = t2.getSelectedRow();
                        Object selected = t2.getValueAt(selectedRow, selectedColumn);
                        String entered = selected.toString();

                        String query2 = "UPDATE staff SET " + columnName + "='" + entered + "' WHERE staff_id =" + t.getValueAt(selectedRow, 0) + "";
                        st.executeUpdate(query2);
                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return t2;

    }

    public JTable EditMenu() {
        try {
            String query = ("SELECT * FROM menu");
            rt = st.executeQuery(query);

            t.setModel(buildTableModel(rt));

            t.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    try {
                        String columnName = t.getColumnName(e.getColumn());
                        int selectedColumn = t.getSelectedColumn();
                        int selectedRow = t.getSelectedRow();
                        Object selected = t.getValueAt(selectedRow, selectedColumn);
                        String entered = selected.toString();

                        String query2 = "UPDATE menu SET " + columnName + "='" + entered + "' WHERE menu_id =" + t.getValueAt(selectedRow, 0) + "";
                        st.executeUpdate(query2);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return t;

    }

    public void AddStock() {
        try {
            String product = JOptionPane.showInputDialog(null, "Product:");
            String quantity = JOptionPane.showInputDialog(null, "Amount:");

            int num = Integer.parseInt(quantity);

            String query = "INSERT INTO stock(product_name,quantity,price) VALUES('" + product + "','" + num + "','" + num * 2 + "')";
            st.executeUpdate(query);
            
            String query2 = "SELECT * FROM stock";
            rt = st.executeQuery(query2);
            
            t.setModel(buildTableModel(rt));
            
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            System.out.println(ex);
        }
    }

    public void AddStaff() {
        
        try {
            
            String query = "INSERT INTO staff VALUES('" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "','staff')";
            st.executeUpdate(query);
            
        String query2 = "SELECT * FROM staff";
            rt = st.executeQuery(query2);

         t2.setModel(buildTableModel(rt));
         
        }catch(Exception ex){
            System.out.println(ex);
            
        }
        
    }
        
       

    public void AddMenu() {
        try {
            String query = "INSERT INTO menu VALUE('" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "','" + NULL + "')";
            st.executeUpdate(query);
            
            String query2 = "SELECT * FROM menu";
            rt = st.executeQuery(query2);
            
            t.setModel(buildTableModel(rt));
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }

    public void MonthlyReport() {
        try {
            
            String query = "SELECT customer.customer_id, customer.name, customer.surname, orders.order_id, orders.order_timedate, orders.order_priority, orders.order_status, staff.staff_id FROM customer join orders ON customer.customer_id = orders.customer_id join staff ON orders.staff_id = staff.staff_id WHERE (orders.order_timedate between  DATE_FORMAT(NOW() ,'%Y-%m-01') AND NOW() )";
            rt = st.executeQuery(query);
        
            t.setModel(buildTableModel(rt));
            
        } catch (Exception ex) {
            System.out.println(ex); 
        }
       
    }
    
 public void SpendingReport(){
        try {
            
            String query = "SELECT customer.customer_id, customer.name, SUM(tot_amount) FROM customer, orders WHERE orders.customer_id = customer.customer_id AND (orders.order_timedate between  DATE_FORMAT(NOW() ,'%Y-%m-01') AND NOW() ) GROUP BY customer.name";
            rt = st.executeQuery(query);
        
            t.setModel(buildTableModel(rt));
            
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }


    public void DeleteStaff() {
        try {

            String ID = JOptionPane.showInputDialog(null, "Enter the id of the employee you want to remove");
            int IDnum = Integer.parseInt(ID);

            String query = "DELETE FROM staff WHERE staff_id='" + IDnum + "' AND type != 'owner' OR 'manager'";
            st.executeUpdate(query);
            
            String query2 = ("SELECT * FROM staff");
            rt = st.executeQuery(query2);
            
            t2.setModel(buildTableModel(rt));
            
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            System.out.println(ex);
        }

    }
    
     public void DeleteFromMenu() {
        try {

            String ID = JOptionPane.showInputDialog(null, "Enter the id of the menu item you want to remove");
            int IDnum = Integer.parseInt(ID);

            String query = "DELETE FROM menu WHERE menu_id='" + IDnum + "'";
            st.executeUpdate(query);
            
            String query2 = ("SELECT * FROM menu");
            rt = st.executeQuery(query2);
            
            t.setModel(buildTableModel(rt));
            
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            System.out.println(ex);
        }

     }
     
     public JTable ReportsTable(){
        try {
            String query = "SELECT customer.customer_id, customer.name, customer.surname, orders.order_id, orders.order_timedate, orders.order_priority, orders.order_status, staff.staff_id FROM customer join orders ON customer.customer_id = orders.customer_id join staff ON orders.staff_id = staff.staff_id LIMIT 0";
            rt = st.executeQuery(query);
            
            t.setModel(buildTableModel(rt));
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return t;
     }

    //merge 
    public void InsertOrderStatus(Order order, int id) {
        try {
            String query = "UPDATE `11express`.`orders` SET `order_status` = '" + order.getStatus() + "',`staff_id` = '"+id+"' WHERE `orders`.`order_id` = " + order.getOrder_id() + ";";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void SetVipThreshold(int s, int g, int d) {
        try {
            String query = "UPDATE `11express`.`vip_threshold` SET `silver` = '" + s + "', `gold` = '" + g + "', `diamond` = '" + d + "' WHERE `vip_threshold`.`id` = 1;";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Integer> GetVipThreshold() {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM `11express`.`vip_threshold` WHERE `vip_threshold`.`id` = 1;";
            rt = st.executeQuery(query);
            while (rt.next()) {
                list.add(rt.getInt("silver"));
                list.add(rt.getInt("gold"));
                list.add(rt.getInt("diamond"));
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void SetDiscount(double perc, String type, boolean active) {
        try {
            int act = (active) ? 1 : 0;
            String query = "UPDATE `11express`.`discount` SET `percentage` = '" + perc + "', `type` = '" + type + "',  `active` = '"+act+"' WHERE `discount`.`id` = 1;";
            st.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public ArrayList<Object> GetDiscount(){
        ArrayList<Object> list = new ArrayList<>();
        try{
            String query = "SELECT * FROM `11express`.`discount` WHERE `discount`.`id` = 1;";
            rt = st.executeQuery(query);
            while (rt.next()) {
                list.add(rt.getDouble("percentage"));
                list.add(rt.getString("type"));
                list.add(rt.getBoolean("active"));
            }
            return list;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    //merge
    
    public ArrayList<Item> getStaffOrderItems(Order o) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            String query = "SELECT menu.title, menu.price, order_items.quantity FROM order_items "
                    + "INNER JOIN menu ON order_items.menu_id=menu.menu_id INNER JOIN orders ON order_items.order_id=orders.order_id "
                    + "WHERE orders.order_id='" + o.getOrder_id() + "'";
            rt = st.executeQuery(query);
            while (rt.next()) {
                Item i = new Item(rt.getString("title"), rt.getDouble("price"), rt.getInt("quantity"));
                items.add(i);
            }
            return items;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void LoggedReport(){
        try {
            
            String query = "SELECT customer.customer_id, customer.name, customer.surname, customer.login_status, orders.order_status FROM customer, orders WHERE customer.login_status = 1 AND orders.order_status = 'Pending' AND orders.customer_id = customer.customer_id";
            rt = st.executeQuery(query);
            
            t.setModel(buildTableModel(rt));
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        
    }
    
    public void RefundReport(){
        try {
            
            String query = "SELECT refunds.refund_id, refunds.refund_date, refunds.order_id, customer.customer_id, orders.tot_amount, orders.returned FROM refunds, customer, orders WHERE refunds.order_id = orders.order_id AND customer.customer_id = orders.customer_id AND orders.returned = 1";
            rt = st.executeQuery(query);
            
            t.setModel(buildTableModel(rt));
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public ArrayList<ReturnPanel> getReturnedOrders(){
        ArrayList<ReturnPanel> list = new ArrayList<>();
        try{
            String query = "SELECT orders.order_id, customer.name, customer.surname, orders.order_timedate, orders.staff_id, orders.tot_amount FROM orders INNER JOIN customer ON orders.customer_id = customer.customer_id WHERE orders.returned = 1 AND orders.checked = '0'";
            rt = st.executeQuery(query);
            while(rt.next()){
                ReturnPanel rp = new ReturnPanel(rt.getInt("order_id"),rt.getString("name"),rt.getString("surname"),rt.getTimestamp("order_timedate").toString(),rt.getInt("staff_id"),rt.getDouble("tot_amount"));
                list.add(rp);
            }
            return list;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    public void InsertRefund(int id){
        try{
            String query = "INSERT INTO `11express`.`refunds` (`refund_id`, `order_id`, `refund_date`) VALUES (NULL, '"+id+"', CURRENT_TIMESTAMP);";
            st.executeUpdate(query);
            String query2 = "UPDATE `11express`.`orders` SET `checked` = '1' WHERE `orders`.`order_id` = "+id+";";
            st.executeUpdate(query2);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void CheckOrder(int id){
        try{
            String query2 = "UPDATE `11express`.`orders` SET `checked` = '1' WHERE `orders`.`order_id` = "+id+";";
            st.executeUpdate(query2);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
