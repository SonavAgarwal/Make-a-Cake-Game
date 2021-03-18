package items;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

public class Item {
    private BufferedImage image, drawnImage;
    private int x, y;
    private String type;
    private int bounce = 0;
    private double bounceAngle = ((int) (Math.random() * 100) / 10);
    private int location;
    private int size;
    private int pickUpCool = 20;
    private BasicStroke stroke = new BasicStroke(5);

    public Item(String type, int x, int y, int location) {
        try {
            image = ImageIO.read(new File("Images/" + type + ".png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for type " + type);
        }
        this.x = x;
        this.y = y;
        this.type = type;
        this.location = location;
        size = 56;
        resize(1);
    }

    public void render(Graphics g, int worldX, int worldY) {
        bounceAngle = ((bounceAngle + 0.1) % Math.PI);
        bounce = (int) (Math.sin(bounceAngle) * 10);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(stroke);
        g.drawImage(drawnImage, x + worldX - (size / 2), y + worldY - (size / 2) + bounce, null);  
        g.setColor(Color.BLACK);
        g2d.drawOval(x + worldX - (size / 2), y + worldY - (size / 2) + bounce, size, size);
        if (pickUpCool > 0) pickUpCool--;
    }

    public void renderInBar(Graphics g, int x, int y)  {
        g.drawImage(drawnImage, x, y, null);  
    }

    public void resize(int zoom) {
        if (location == 1) {
            drawnImage = new BufferedImage( zoom * size, zoom * size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) drawnImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
            g2d.drawImage(image, 0, 0, zoom * size, zoom * size, null);
        } else {
            drawnImage = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) drawnImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
            g2d.drawImage(image, 0, 0, 70, 70, null);
        }
    }

    public String use() {
        return null;
    }

    public double getDist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean isInRange(int playerX, int playerY) {
        if (getDist(x, y, playerX, playerY) < 40 && pickUpCool == 0) return true;
        return false;
    }

    public void setLocation(int l)  {
        location = l;
        resize(1);
    }

    public void drop(int playerX, int playerY) {
        pickUpCool = 100;
        location = 1;
        resize(1);
        x = playerX;
        y = playerY;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}