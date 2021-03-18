package sprites;

import worldobjects.*;
import items.*;
import sprites.*;
import creation.*;

import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.util.ArrayList;

public class NPC {
    protected BufferedImage image;
    protected BufferedImage drawnImage;
    protected int x, y;
    protected int size;
    protected int hitboxSize;
    protected String skin;
    protected ArrayList<String> lines;
    protected int done;
    private boolean through = false;
    protected boolean wasInRange;
    protected int range;
    protected double rotation;

    public NPC (String skin, int x, int y, ArrayList<String> lines) {
        try {
            image = ImageIO.read(new File("Images/NPCs/" + skin + ".png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for skin " + skin);
        }
        this.x = x;
        this.y = y;
        this.lines = lines;

        done = 0;
        wasInRange = false;

        range = 150;
        size = 70;
        hitboxSize = 30;
        resize(1);
    }

    public void render(Graphics g, int worldScreenX, int worldScreenY) {

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x + worldScreenX, y + worldScreenY);
        g2d.rotate(-rotation);
        g2d.drawImage(drawnImage, -(size / 2), -(size / 2), size, size, null);
        g2d.setTransform(oldTransform);

        // g.drawImage(drawnImage, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);  
    }

    public void resize(int zoom) {
        drawnImage = new BufferedImage(size * zoom, size * zoom, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) drawnImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.drawImage(image, 0, 0, size * zoom, size * zoom, null);
    }

    public void update(Player player) {
        if (getDist(player.getX(), player.getY(), x, y) < range) {
            rotation = Math.atan2((double) (x - player.getX()) , (y - player.getY())) + Math.PI;
            if (!Words.hasLine() && !through) {
                Words.showLine(lines.get(done), x, y - (size / 2) - 20, 20);
                done++;
                if (done > lines.size() - 1) through = true;
                // done = (done + 1) % (lines.size());
            }
        } else {
            rotation = 0;
        }
    }

    public boolean isInRange(int playerX, int playerY, double angleToMouse) {
        double dist = getDist(x, y, playerX, playerY);
        double angle = Math.atan2((double) (x - playerX) , (y - playerY)) + (Math.PI / 2);

        angle = Math.atan2((double) (playerY - y) , (x - playerX));

        if (dist < hitboxSize + 20 && Math.abs(angleToMouse - angle) < 0.5) return true;
        return false;
    }

    public boolean isColliding(int playerX, int playerY) {
        double dist = getDist(x, y, playerX, playerY);
        if (dist < hitboxSize) return true;
        return false;
    }

    public double getDist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public Item hit(Player player, Item tool) {
        return new Item("None", x + rand(-100, 100), y + rand(-100, 100), 1);
    }
    
    public int rand(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1) + min);
    }
}