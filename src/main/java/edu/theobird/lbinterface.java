package edu.theobird;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class lbinterface extends JFrame {
    JPanel panel = new JPanel(new GridLayout(6,1));
    JLabel title = new JLabel("Leaderboard");



    public lbinterface() {
        setSize(75, 150);

        setResizable(false);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setPreferredSize(new Dimension(75, 40));
        panel.add(title);


        JSONParser parser = new JSONParser();
        boolean finish = false;
        while(!finish) {
            try (FileReader reader = new FileReader("scores.json")) {
                Object obj = parser.parse(reader);

                JSONArray scores = (JSONArray) obj;
                JSONObject scoresobject = (JSONObject) scores.getFirst();
                JLabel p1 = new JLabel("Player 1: "+scoresobject.get("Player 1"));
                JLabel p2 = new JLabel("Player 2: "+scoresobject.get("Player 2"));
                JLabel p3 = new JLabel("Player 3: "+scoresobject.get("Player 3"));
                JLabel p4 = new JLabel("Player 4: "+scoresobject.get("Player 4"));
                JLabel p5 = new JLabel("Player 5: "+scoresobject.get("Player 5"));

                panel.add(p1);
                panel.add(p2);
                panel.add(p3);
                panel.add(p4);
                panel.add(p5);


                finish = true;
            } catch (FileNotFoundException err) {
                JOptionPane.showMessageDialog(null, err.getMessage() + " - The scores JSON file seems to be have deleted since the program started, launching setup process now.", "Error", JOptionPane.ERROR_MESSAGE);
                Main.setupJSON();
            } catch (IOException | ParseException err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(err);
            }
        }



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
