package sprites;

import gui.*;

import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Player {
    private int x, y;
    private BufferedImage[] playerImages;
    private int playerFrame = 0;
    private int halfScreenWidth, halfScreenHeight;
    private int shaking = 0;
    private int shakeX = 0;
    private int shakeY = 0;

    private Hotbar inventory = new Hotbar();

    public Player(int halfScreenWidth, int halfScreenHeight) {
        playerImages = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            try {
                playerImages[i] = ImageIO.read(new File("Images/Player/Player" + (i % 7) + ".png"));
            } catch (IOException e) {
                System.out.println("Couldn't find image");
            }
        }
        x = 0;
        y = 0;

        this.halfScreenWidth = halfScreenWidth;
        this.halfScreenHeight = halfScreenHeight;
    }

    public void render(Graphics g, double angleToMouse) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(halfScreenWidth, halfScreenHeight);
        if (shaking > 0) g2d.translate(shakeX, shakeY);
        g2d.rotate(-angleToMouse);
        g2d.drawImage(playerImages[playerFrame / 3], -50, -50, 100, 100, null);
        g2d.setTransform(oldTransform);
        if (playerFrame > 0) playerFrame--;
    }

    public void shake() {
        shaking += 4;
    }

    public void tick() {
        shakeX = rand(-5, 5);
        shakeY = rand(-5, 5);
        if (shaking > 0) shaking -= 3;
    }

    public int rand(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1) + min);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void punch() {
        playerFrame = 18;
    }

    public Hotbar getInventory() {
        return inventory;
    }
}