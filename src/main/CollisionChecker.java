package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldx + entity.solidArea.x;
        int entityRightWorldX = entity.worldx + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldy + entity.solidArea.y;
        int entityBotWorldY = entity.worldy + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBotRow = entityBotWorldY / gp.tileSize;

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

