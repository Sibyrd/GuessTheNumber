package edu.theobird;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.lang.Math.*;



public class Main {
    static ArrayList<Integer> eliminated = new ArrayList<Integer>();
    static ArrayList<Integer> scores = new ArrayList<Integer>();

    public static void setupJSON() {
        JSONObject obj = new JSONObject();
        JSONArray completedjson = new JSONArray();
        obj.put("Player 1",0);
        obj.put("Player 2",0);
        obj.put("Player 3",0);
        obj.put("Player 4",0);
        obj.put("Player 5",0);

        completedjson.add(obj);

        try (FileWriter doc = new FileWriter("scores.json")) {
            doc.write(completedjson.toJSONString());
            JOptionPane.showMessageDialog(null, "Successfully setup scores JSON file in parent directory.", "First-time Setup", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error - please debug using the IntelliJ project file", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Process launched");

        Interface.appearance();
        try {
            File check = new File("scores.json");
            if (!check.isFile()) {
                System.out.println("Scores file not found, setting up JSON.");
                setupJSON();
            }
            System.out.println("Launching interface..."); Interface frame = new Interface();
        } catch (Exception e) {
            System.out.println("Failure. See error.");
            e.printStackTrace();
        }

    }



    public static int askinput(int args) {
        Integer UserInp = null;
        try {
            UserInp = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter a number 1-100", "Player "+Integer.toString(args), JOptionPane.QUESTION_MESSAGE));
            if (UserInp < 1 || UserInp > 100) {
                JOptionPane.showMessageDialog(null, "Please enter a number from 1-100.", "Error", JOptionPane.ERROR_MESSAGE);
                return askinput(args);
            } else if (scores.contains(UserInp)) {
                JOptionPane.showMessageDialog(null, "You cannot enter a number that another player has already used.", "Error", JOptionPane.ERROR_MESSAGE);
                return askinput(args);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter an integer number.", "Error", JOptionPane.ERROR_MESSAGE);
            return askinput(args);
        }
        return UserInp;
    }

    public static int round(int randint) {


        int furthest = -1;
        double furthestdouble = 0.0;

        for (int i = 0; i < 5; i++) {
            if (eliminated.contains(i)) {
                scores.add(-1);
                System.out.println("This player is ELIMINATED.");
            }
            else {
                scores.add(askinput(i+1));
            }
        }

        System.out.println(scores);
        //check who is furthest
        for (int i = 0; i < scores.size(); i++) {
            //noinspection StatementWithEmptyBody
            if (scores.get(i) == -1) {} else if (sqrt(pow(randint-scores.get(i),2)) > furthestdouble) {
                furthestdouble = sqrt(pow(randint - scores.get(i),2));
                furthest = i;
            }
        }
        return furthest;
    }

    public static int winner() {
        for (int i = 0; i < 5; i++) {
            //noinspection StatementWithEmptyBody
            if (eliminated.contains(i)) {} else{
                return i+1;
            }
        }
        return -1;
    }

    public static boolean game(){
        System.out.println("Guess The Number Started");
        eliminated = new ArrayList<Integer>();
        Random r = new Random();
        int randint = r.nextInt(101);

        int result = 0;

        for (int i = 0; i < 4; i++) {
            result = round(randint);
            scores = new ArrayList<Integer>();
            System.out.println("Player " + (result+1) + " has been eliminated.");
            eliminated.add(result);
            JOptionPane.showMessageDialog(null, "Player " + (result+1) + " has been eliminated.", "Round results", JOptionPane.INFORMATION_MESSAGE);
        }
        //leaderboard stuff
        int winner = winner();
        JSONParser parser = new JSONParser();
        boolean finish = false;
        while(!finish) {
            try (FileReader reader = new FileReader("scores.json")) {
                Object obj = parser.parse(reader);

                JSONArray scores = (JSONArray) obj;
                JSONObject scoreobject = (JSONObject) scores.getFirst();
                long winnerscore = (long) scoreobject.get("Player "+winner);
                scoreobject.put("Player "+winner, winnerscore+1);

                try (FileWriter doc = new FileWriter("scores.json")) {
                    doc.write((((JSONArray) obj).toJSONString()));
                }

                finish = true;
            } catch (FileNotFoundException err) {
                JOptionPane.showMessageDialog(null, err.getMessage() + " - The scores JSON file seems to be have deleted since the program started, launching setup process now.", "Error", JOptionPane.ERROR_MESSAGE);
                Main.setupJSON();
            } catch (IOException | ParseException err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(err);
            }
        }


        JOptionPane.showMessageDialog(null, "The winner is player " + winner + "! The number was " + randint, "Game finished", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}

