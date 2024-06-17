package main;

import java.util.ArrayList;

import entity.Ally;
import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkCollision(Entity entity, ArrayList<Ally> allies) {
        int entityLeftWorldX = entity.worldx + entity.solidArea.x;
        int entityRightWorldX = entity.worldx + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldy + entity.solidArea.y;
        int entityBotWorldY = entity.worldy + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBotRow = entityBotWorldY / gp.tileSize;

        //ally check
        for (Ally ally : allies) {
            switch(entity.direction) {
                case "up":
                    if ((entityTopWorldY <= (ally.worldy + ally.solidArea.height)) && (entityBotWorldY > ally.worldy + ally.solidArea.height)) {
                        if((ally.worldx < entityLeftWorldX && entityLeftWorldX < ally.worldx + ally.solidArea.width) || (ally.worldx < entityRightWorldX && entityRightWorldX < ally.worldx + ally.solidArea.width)) {
                            entity.collisionOn = true;
                        }   
                    }

                    break;
        
                case "down":
                    if ((entityBotWorldY >= (ally.worldy)) && (entityTopWorldY < ally.worldy)) {
                        if((ally.worldx < entityLeftWorldX && entityLeftWorldX < ally.worldx + ally.solidArea.width) || (ally.worldx < entityRightWorldX && entityRightWorldX < ally.worldx + ally.solidArea.width)) {
                            entity.collisionOn = true;
                        }   
                    }
                    break;
        
                case "left":
                    if ((entityLeftWorldX <= (ally.worldx + ally.solidArea.width)) && (entityRightWorldX > ally.worldx)) {
                        if((ally.worldy < entityTopWorldY && entityTopWorldY < ally.worldy + ally.solidArea.height) || (ally.worldy < entityBotWorldY && entityBotWorldY < ally.worldy + ally.solidArea.height)) {
                            entity.collisionOn = true;
                        }   
                    }
                    break;
        
                case "right":
                    if ((entityRightWorldX >= (ally.worldx)) && (entityLeftWorldX < ally.worldx)) {
                        if((ally.worldy < entityTopWorldY && entityTopWorldY < ally.worldy + ally.solidArea.height) || (ally.worldy < entityBotWorldY && entityBotWorldY < ally.worldy + ally.solidArea.height)) {
                            entity.collisionOn = true;
                        }   
                    }
                     break;
            }
        }

        //tiles checked (player right and left shoulder)
        int tileNum1, tileNum2;

        switch(entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;

        case "down":
            entityBotRow = (entityBotWorldY + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityBotRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum[entityBotRow][entityRightCol];

            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

            break;

        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum [entityTopRow][entityLeftCol];
            tileNum2 = gp.tileM.mapTileNum [entityBotRow][entityLeftCol];

            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
            
            break;

        case "right":
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;

            tileNum1 = gp.tileM.mapTileNum [entityTopRow][entityRightCol];
            tileNum2 = gp.tileM.mapTileNum [entityBotRow][entityRightCol];

            if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }

             break;
        }
    }
}

