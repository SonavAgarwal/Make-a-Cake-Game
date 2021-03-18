package gui;

import worldobjects.*;

import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Map {

    private BufferedImage image;
    private int drawX, drawY;

    public Map(ArrayList<WorldObject> objects, Color c, int worldW, int worldH) {
        image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) image.createGraphics();
        g2d.setColor(c);
        g2d.fillRect(0, 0, 200, 200);
        for (WorldObject w : objects) {
            g2d.drawImage(w.getImage(), (w.getX() - (w.getSize() / 2)) / 20 + 100, (w.getY() - (w.getSize() / 2)) / 20 + 100, w.getSize() / 20, w.getSize() / 20, null);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 199, 199);
        g2d.dispose();
        drawX = worldW - 210;
        drawY = worldH - 210;
    }

    public void render(Graphics g, int px, int py) {
        g.drawImage(image, drawX, drawY, null);
        g.setColor(Color.WHITE);
        g.fillOval(drawX + 100 + px / 20, drawY + 100 + py / 20, 3, 3);
    }
}