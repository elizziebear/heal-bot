package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.Encoder;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Ally;
import entity.Player;
import tile.tileManager;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 96; //revert 1920
    final int scale = 1; //revert 0.05 ... double

    //actual tile size
    public final int tileSize = originalTileSize * scale; //revert originalTileSize * scale ... cast to int
    public final int maxScreenCol = 10;
    public final int maxScreenRow = 6;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //world settings
    public final int maxWorldCol = 19;
    public final int maxWorldRow = 13;
    //cant do more than 14 vertically
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    tileManager tileM = new tileManager(this);

    KeyHandler kh = new KeyHandler();

    Thread gameThread;

    public CollisionChecker checker = new CollisionChecker(this);

    //player and allies
    public Player player= new Player(this, kh);

    public ArrayList<Ally> allies = new ArrayList<Ally>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);

        //set allies
        allies.add(new Ally(this, 700, 500));
        allies.add(new Ally(this, 900, 450));
        allies.add(new Ally(this, 1100, 700));
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //0.016666 seconds
        double healthInterval = drawInterval * 100;

        double nextHealthTime = System.nanoTime() + healthInterval;
        double nextDrawTime = System.nanoTime() + drawInterval; 

        while(gameThread != null) {
            //update char position
            update();
            //draw screen with updated info
            repaint(); //this is how you call paintComponent (built in)

            if (System.nanoTime() > nextHealthTime) {
                for (Ally ally : allies) {
                    if (ally.life > 0) {
                        ally.life -= 1;
                    }
                }
                nextHealthTime = System.nanoTime()+healthInterval;
            }
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        for (Ally ally : allies) {
            if (ally.life > 0) {
                ally.draw(g2);
            }
        }
        player.draw(g2);

        g2.dispose();
    }
}