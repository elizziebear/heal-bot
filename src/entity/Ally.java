package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Ally extends Entity {

    GamePanel gp;

    public int worldx;
    public int worldy;

    public Ally (GamePanel gp, int worldx, int worldy) {
        this.gp = gp;
        this.worldx = worldx;
        this.worldy = worldy;

        maxLife = 10;
        life = maxLife;
    
        //collision dimensions
        solidArea = new Rectangle(0, 0, 40, 96);
    
        int solidAreaDefaultX = solidArea.x;
        int solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        try {
            allyImage = ImageIO.read(getClass().getResourceAsStream("/res/npc/ally.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        //get screen pos of tile
        int screenx = worldx - gp.player.worldx + gp.player.screenx;
        int screeny = worldy - gp.player.worldy + gp.player.screeny;

        g2.drawImage(allyImage, screenx, screeny, gp.tileSize, gp.tileSize, null);
    }
}
