package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler kh;

    public final int screenx;
    public final int screeny;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;

        screenx = (gp.screenWidth / 2) - (gp.tileSize /2);
        screeny = (gp.screenHeight / 2) - (gp.tileSize /2);

        //solid area of the character
        solidArea = new Rectangle(0, 58, 40, 38);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldx = gp.tileSize * 9;
        worldy = gp.tileSize * 6;
        speed = 3;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/bot_right_2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        int entityLeftWorldX = worldx + solidArea.x;
        int entityRightWorldX = worldx + solidArea.x + solidArea.width;
        int entityTopWorldY = worldy + solidArea.y;
        int entityBotWorldY = worldy + solidArea.y + solidArea.height;

        if (kh.healPresses > 0) {
                for (Ally ally : gp.allies) {
                    if (Math.abs(((worldx + (solidArea.width) / 2) - ally.worldx + (ally.solidArea.width / 2))) < 100) {
                        if (Math.abs(((worldy + (solidArea.height) / 2) - ally.worldy + (ally.solidArea.height / 2))) < 100) {
                            if (ally.life < ally.maxLife) {
                                ally.life += 1;
                            }
                        }
                    }
            }
            kh.healPresses = 0;
        }


        if (kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {
            //update position
            if(kh.upPressed == true) {
                direction = "up";
            }
            else if(kh.downPressed == true) {
                direction = "down";
            }
            else if(kh.leftPressed == true) {
                direction = "left";
            }
            else if(kh.rightPressed == true) {
                direction = "right";
            }

            //collision
            collisionOn = false;
            //player is subclass of entity so can be recieved as ent.
            gp.checker.checkCollision(this, gp.allies);

            //if col is false player can move
            if (collisionOn == false) {
                switch(direction) {
                case "up":
                    worldy -= speed;
                    break;
                case "down":
                    worldy += speed;
                    break;
                case "left":
                    worldx -= speed;
                    break;
                case "right":
                    worldx += speed;
                    break;
                }
            }
            spriteCounter ++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
        case "up":
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break;
        }
        g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
    }
}
