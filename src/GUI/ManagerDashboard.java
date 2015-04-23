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
import java.io.File;
import java.util.Scanner;

import javax.swing.*;

public class ManagerDashboard extends JPanel {

    reportsPanel rp;
    editMenuPanel emp;
    viewStock vs;
    StaffOrdersPanel sop;
    viewReturns vr;
    ManageAccount ma;
    VipDiscountPanel vdp;

    JPanel dashComponents;
    JPanel mainPanel = new JPanel();
    JPanel jNorth = new JPanel();
    JPanel jCenter = new JPanel();
    JPanel jSouth = new JPanel();
    JPanel panel = new JPanel();

    JButton viewReports;
    JButton viewReturns;
    JButton logoutButton;
    JButton sopBack = new JButton("Back");

    JButton editMenu = new JButton("Edit Menu");
    JButton viewMenu = new JButton("View Menu");
    JButton setDiscount = new JButton("Set Disounts/VIP");
    JButton manageAccounts = new JButton("Manage Accounts");
    JButton viewStock = new JButton("View Stock");
    JButton viewOrders = new JButton("View Orders");

    User user;
    MainFrame mf;

    public ManagerDashboard(User u, final MainFrame mf) {
        this.user = u;
        this.mf = mf;
        sop = new StaffOrdersPanel(u);
        dashComponents = new JPanel();
        this.add(mainPanel);
        mainPanel.setBackground(Color.yellow);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(dashComponents, BorderLayout.CENTER);
        dashComponents.setLayout(new BorderLayout());
        dashComponents.setBackground(Color.yellow);

        dashComponents.add(jNorth, BorderLayout.NORTH);
        dashComponents.add(jCenter, BorderLayout.CENTER);
        dashComponents.add(jSouth, BorderLayout.SOUTH);

        jNorth.setPreferredSize(new Dimension(400, 100));
        jCenter.setPreferredSize(new Dimension(400, 100));
        jSouth.setPreferredSize(new Dimension(400, 100));
        jSouth.setLayout(new BorderLayout());

        if (user.getType().equals("owner")) {
            jSouth.add(OwnerFunctionality(), BorderLayout.SOUTH);
        }

        jNorth.setLayout(new FlowLayout());

        viewReports = new JButton("View Reports");
        viewReturns = new JButton("View Returns");
        logoutButton = new JButton("Logout");

        jNorth.add(viewReturns);
        viewReturns.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(dashComponents);
                vr = new viewReturns(getThis());
                mainPanel.add(vr);
                mf.pack();

            }
        });

        jNorth.add(viewReports);
        viewReports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(dashComponents);
                rp = new reportsPanel(getThis(), mf);
                mainPanel.add(rp);
                mf.pack();
            }
        });
        jNorth.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mf.remove(mainPanel);
                removeThis();
                mf.homeDash();
            }
        });
        jCenter.setLayout(new FlowLayout());
        jCenter.add(viewMenu);
        viewMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(dashComponents);
                emp = new editMenuPanel(getThis());
                mainPanel.add(emp);
                mf.pack();

            }
        });

        jCenter.add(setDiscount);
        setDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mainPanel.remove(dashComponents);
                vdp = new VipDiscountPanel(getThis());
                mainPanel.add(vdp);
                mf.pack();
            }
        });

        jCenter.add(manageAccounts);
        manageAccounts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.remove(dashComponents);
                ma = new ManageAccount(getThis());
                mainPanel.add(ma);
                mf.pack();
            }
        });

        //jSouth.setLayout(new FlowLayout());
        jSouth.add(viewOrders, BorderLayout.EAST);
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        viewOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(dashComponents);
                
                panel.add(sopBack);
                panel.add(sop);

                mainPanel.add(panel);
                sopBack.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        removeOrdersPanel();
                    }
                });
                mf.pack();
            }
        });

        jSouth.add(viewStock, BorderLayout.WEST);
        viewStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mainPanel.remove(dashComponents);
                vs = new viewStock(getThis());
                mainPanel.add(vs);
                mf.pack();

            }
        });

    }

    public Component OwnerFunctionality() {
        final DbConnect db = new DbConnect();

        JPanel owner = new JPanel();

        JButton backupButton = new JButton("Database Back Up");
        JButton restoreButton = new JButton("Database Restore");
        owner.setLayout(new FlowLayout());
        owner.setVisible(true);
        owner.add(backupButton);
        owner.add(restoreButton);

        restoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //System.out.println("Selected file: " + selectedFile.getPath());
                    String path = selectedFile.getPath();
                    db.databaseRestore(path);
                }

            }
        });

        backupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                String backupDir;

                //fc.setCurrentDirectory(  );
                fc.setDialogTitle("Select where you want to save your backup");
                fc.setAcceptAllFileFilterUsed(false);

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String fileName = JOptionPane.showInputDialog(null, "Save as..");
                    backupDir = fc.getSelectedFile().getPath() + "\\" + fileName + ".sql";

                    //System.out.println(backupDir);
                    db.databaseBackup(backupDir);
                }

                //String fileName = JOptionPane.showInputDialog(owner, "Save as");
                //db.databaseBackup(fileName);
            }
        });

        return owner;

    }

    public void removeReportsPanel() {
        mainPanel.remove(rp);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeEditMenuPanel() {
        mainPanel.remove(emp);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeStocksPanel() {
        mainPanel.remove(vs);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeDiscountPanel() {
        mainPanel.remove(vdp);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeOrdersPanel() {
        mainPanel.remove(panel);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeReturnsPanel() {
        mainPanel.remove(vr);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeManageAccount() {
        mainPanel.remove(ma);
        mainPanel.add(dashComponents);
        mf.pack();
    }

    public void removeThis() {
        mf.remove(this);
    }

    public ManagerDashboard getThis() {
        return this;
    }
}
