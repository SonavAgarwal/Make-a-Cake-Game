package worldobjects;

import items.*;
import sprites.*;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

public class WorldObject {
    
    protected BufferedImage image;
    protected BufferedImage drawnImage;
    protected int x, y;
    protected int size;
    protected String type;

    protected int hitboxSize;

    protected BufferedImage image1, image2;

    public WorldObject(String type, int x, int y, int hitboxSize) {
        loadImage(type);
        this.x = x;
        this.y = y;
        this.type = type;
        this.hitboxSize = hitboxSize;
        size = 300;
        resize(1);
    }
    public WorldObject(String type, String extra, int x, int y, int size) {
        ;
    }

    public void loadImage(String name) {
        try {
            image = ImageIO.read(new File("Images/" + name + ".png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for type " + name);
        }
    }

    public void render(Graphics g, int worldScreenX, int worldScreenY) {
        g.drawImage(drawnImage, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);  
    }

    public void resize(int zoom) {
        drawnImage = new BufferedImage(size * zoom, size * zoom, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) drawnImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.drawImage(image, 0, 0, size * zoom, size * zoom, null);
    }

    public String toString() {
        return type + " " + x + ", " + y;
    }

    public void update(Player player) {
        ;
    }

    //not in use yet
    public boolean isInRange(int playerX, int playerY, double angleToMouse) {
        double dist = getDist(x, y, playerX, playerY);
        double angle = Math.atan2((double) (x - playerX) , (y - playerY)) + (Math.PI / 2);

        angle = Math.atan2((double) (playerY - y) , (x - playerX));

        if (dist < hitboxSize + 20 && Math.abs(angleToMouse - angle) < 0.5) return true;
        return false;
    }

    // public boolean isColliding(int playerX, int playerY, int worldScreenX, int worldScreenY) {
    //     double dist = getDist(x + worldScreenX + (size / 2), y + worldScreenY + (size / 2), playerX, playerY);
    //     if (dist < hitboxSize) return true;
    //     return false;
    // }

    public boolean isColliding(int playerX, int playerY) {
        double dist = getDist(x, y, playerX, playerY);
        if (dist < hitboxSize) return true;
        return false;
    }

    public double getDist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public Item getDrop(Player player) {
        return new Item("None", x + rand(-100, 100), y + rand(-100, 100), 1);
    }

    public String hit() {
        return "none";
    }
    
    public int rand(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1) + min);
    }

    // public double getAngleTo(int playerX, int playerY) {
    //     return Math.atan2((double) (playerX - x) , (playerY - y));
    // }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public BufferedImage getImage() {
        return image;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int s) {
        size = s;
    }
    public void setHitboxSize(int hs) {
        hitboxSize = hs;
    }
}