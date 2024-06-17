package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class tileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public tileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];

        getTileImage();
        loadMap("/res/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/guy.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/background.png"));
            tile[2].collision = true;
        }
        catch (IOException e) {
            //e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                col = 0;

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                    col ++;
                }
                row ++;
            }
            br.close();
        } catch (Exception e) {
        }
    }
    public void draw (Graphics2D g2) {
        
        int worldCol = 0;
        int worldRow = 0;

        while (worldRow < gp.maxWorldRow) {
            worldCol = 0;

            while (worldCol < gp.maxWorldCol) {
                int tileNum = mapTileNum[worldRow][worldCol];

                //check tiles world pos
                int worldy = worldRow * gp.tileSize;
                int worldx = worldCol * gp.tileSize;
                //get screen pos of tile
                int screenx = worldx - gp.player.worldx + gp.player.screenx;
                int screeny = worldy - gp.player.worldy + gp.player.screeny;

            //only draw tiles around player, cut out processing
            //if (worldx + gp.tileSize> gp.player.worldx - gp.player.screenx && worldx - gp.tileSize < gp.player.worldx + gp.player.screenx && worldy + gp.tileSize > gp.player.worldy - gp.player.screeny && worldy - gp.tileSize < gp.player.worldy + gp.player.screeny) {
                g2.drawImage(tile[tileNum].image, screenx, screeny, gp.tileSize, gp.tileSize, null);
            //}
                worldCol ++;

            }
            worldRow ++;
        }
    }
}
