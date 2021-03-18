package worldobjects;

import items.*;
import sprites.*;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

public class Building extends WorldObject {

    protected BufferedImage image1, image2;
    protected BufferedImage drawnImage1, drawnImage2;
    protected boolean inRange;
    protected int viewRange;

    public Building(String type, String extra, int x, int y, int size) {
        super("hi", "i dont understand", 1, 2, 3);
        try {
            image1 = ImageIO.read(new File("Images/" + type + "1.png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for type " + type);
        }
        try {
            image2 = ImageIO.read(new File("Images/" + type + "2.png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for type " + type);
        }

        // if (!extra.equals("none")) {
        //     System.out.println("here"); Creator.createItem(extra, "none", x, y);
        // } 

        this.x = x;
        this.y = y;
        this.type = type;
        this.size = size;
        inRange = false;
        viewRange = 150;
        resize(1);
    }

    @Override
    public void render(Graphics g, int worldScreenX, int worldScreenY) {
        // if (inRange) g.drawImage(drawnImage1, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);
        // else g.drawImage(drawnImage2, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);

        g.drawImage(drawnImage1, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);
        g.drawImage(drawnImage2, x + worldScreenX - (size / 2), y + worldScreenY - (size / 2), null);
    }

    @Override
    public void resize(int zoom) {
        drawnImage1 = new BufferedImage(size * zoom, size * zoom, BufferedImage.TYPE_INT_ARGB);
        drawnImage2 = new BufferedImage(size * zoom, size * zoom, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) drawnImage1.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.drawImage(image1, 0, 0, size * zoom, size * zoom, null);
        g2d = (Graphics2D) drawnImage2.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.drawImage(image2, 0, 0, size * zoom, size * zoom, null);
        g2d.dispose();
    }

    public void opacitize(float opacity) {
        drawnImage2 = new BufferedImage(drawnImage2.getWidth(), drawnImage2.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) drawnImage2.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(image2, 0, 0, drawnImage2.getWidth(), drawnImage2.getHeight(), null);
        g2d.dispose();
    }

    public void update(Player player) {
        if (getDist(player.getX(), player.getY(), x, y) < viewRange) {
            if (inRange == false) opacitize(0.5f);
            inRange = true;
        } 
        else  {
            if (inRange == true) opacitize(1f);
            inRange = false;
        }
    }

    @Override
    public String hit() {
        return "none";
    }
    public Item getDrop(Player p) {
        return null;
    }

    public boolean isColliding(int playerX, int playerY) {
        int pX = playerX - 40;
		int pY = playerY - 40;
		int pWidth = 80;
		int pHeight = 80;
		
		int tX = x - (size / 4);
		int tY = y - (size / 4);
		int tWidth = size / 2;
		int tHeight = size / 2;
        if (pX+pWidth >= tX && pX <= tX + tWidth  &&  pY+pHeight >= tY && pY <= tY + tHeight) return true;
        return false;
    }

    public BufferedImage getImage() {
        return drawnImage2;
    }
}