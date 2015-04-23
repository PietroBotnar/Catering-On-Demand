/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Pedro
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class newframe extends JFrame {


    
    JPanel panel = new JPanel();

    public newframe(String s) {
        super("Welcome");
        setSize(500, 500);
        setLocation(500, 280);
        panel.setLayout(null);
        JLabel welcome = new JLabel(s);
        welcome.setBounds(70, 50, 150, 60);

        panel.add(welcome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
