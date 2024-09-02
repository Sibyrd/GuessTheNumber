package edu.theobird;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Interface extends JFrame {
    Boolean debounce = false;
    JPanel panel = new JPanel(new GridLayout(2,1));
    JLabel title = new JLabel("Guess The Number");
    JButton play = new JButton("Play");
    JButton leaderboard = new JButton("Leaderboard");


    public Interface() {
        super("Guess The Number");
        setSize(300, 150);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setPreferredSize(new Dimension(300, 40));
        panel.add(title, BorderLayout.NORTH);
        panel.add(play, BorderLayout.CENTER);
        panel.add(leaderboard, BorderLayout.SOUTH);

        //listener
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ( debounce == false ){
                    debounce = true;
                    if (Main.game()) {
                        debounce = false;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "You are already playing!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        leaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lbinterface.appearance(); lbinterface frame = new lbinterface();
            }
        });


        //start
        add(panel);
        setVisible(true);
    }

    public static void appearance() {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
