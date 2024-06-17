package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main {
    JFrame window;

    static JButton startGame;
    static JButton scores;
    static JButton closeGame;

    static JLabel title;

    Graphics2D graphics2d;
    GamePanel gamePanel;
    public static void main(String[] args) throws Exception {
        gameOver();
    }

    public void startGame() {
        GamePanel gp = new GamePanel();
        window.add(gp);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.startGameThread();
    }

    public static void gameOver() {
        JFrame window = new JFrame();
        startGame = new JButton("<html><center>PLAY<center></html>");
        scores = new JButton("<html><center>SCORES<center></html>");
        closeGame = new JButton("<html><center>EXIT<center></html>");
        title = new JLabel("<html><center>HEALBOT<center></html>");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("HEALBOT");

        window.setSize(1280, 720);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.getContentPane().setBackground(Color.decode("#000000"));
        window.setVisible(true);

        window.add(title);
        title.setBounds(265, 200, 750, 150);;
        title.setForeground(Color.decode("#000000"));

        window.add(startGame);
        startGame.setBounds(515, 360, 250, 50);
        startGame.setVerticalAlignment(SwingConstants.CENTER);
        startGame.setForeground(Color.decode("#000000"));
        startGame.setBackground(Color.decode("#FFFFFF"));
        startGame.setOpaque(true);
        startGame.setBorderPainted(false);
        startGame.setFocusPainted(false);

        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                window.dispose();

                JFrame gameWindow = new JFrame();
                GamePanel gp = new GamePanel();
                gameWindow.add(gp);
        
                gameWindow.pack();
        
                gameWindow.setLocationRelativeTo(null);
                gameWindow.setVisible(true);
        
                gp.startGameThread();
            }
        });
       
        //attributes for the scores buttton
        window.add(scores);
        scores.setBounds(515, 435, 250, 50);
        scores.setVerticalAlignment(SwingConstants.CENTER);
        scores.setForeground(Color.decode("#000000"));
        scores.setBackground(Color.decode("#FFFFFF"));
        scores.setOpaque(true);
        scores.setBorderPainted(false);
        scores.setFocusPainted(false);

        scores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    String path = (System.getProperty("user.dir") + "\\highScore.txt");
                    File file = new File(path);

                    String lastHighScore = "0";

                    if (file.exists()) {
                        Scanner reader;
                        try {
                            //read from file and store the top three scores
                            reader = new Scanner(file);
                            if (reader.hasNext()) {
                                lastHighScore = reader.nextLine();
                            }
                            reader.close();
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (lastHighScore == "0") {
                        JOptionPane.showMessageDialog(null, "No High Score");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, lastHighScore + " minutes");
                    }
            }
        });

        //attributes for the exit buttton
        window.add(closeGame);
        closeGame.setBounds(515, 510, 250, 50);
        closeGame.setVerticalAlignment(SwingConstants.CENTER);
        closeGame.setForeground(Color.decode("#000000"));
        closeGame.setBackground(Color.decode("#FFFFFF"));
        closeGame.setOpaque(true);
        closeGame.setBorderPainted(false);
        closeGame.setFocusPainted(false);

        closeGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}