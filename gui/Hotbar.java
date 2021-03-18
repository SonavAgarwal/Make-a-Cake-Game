package gui;

import worldobjects.*;
import items.*;
import creation.SP;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

public class Hotbar {
    
    private Item[] inventory = {null, null, null, null};
    private int selected = 0;
    private Color selectedColor = new Color(0, 0, 0, 50);

    private BufferedImage image, drawnImage;

    public Hotbar() {
        try {
            image = ImageIO.read(new File("Images/Hotbar.png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for hotbar");
        }
        resize(100);
    }

    public void render(Graphics g) {
        g.setColor(selectedColor);
        g.fillRect(0, 150, 100, 400);
        for (int i = 0; i < 4; i++) {
            if (i == selected) g.fillRect(0, 150 + i * 100, 100, 100);
            g.drawImage(drawnImage, 0, 150 + i * 100, null);
            if (inventory[i] != null) inventory[i].renderInBar(g, 15, 165 + i * 100);
        }
    }

    public void resize(int size) {
        drawnImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) drawnImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.drawImage(image, 0, 0, size, size, null);
    }

    public void clear() {
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = null;
        }
    }

    public boolean addItem(Item it) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = it;
                it.setLocation(2);
                SP.playSound("Pop");
                return true;
            } 
        }
        return false;
    }

    public void setSelected(int s) {
        selected = s;
    }

    public Item drop(int playerX, int playerY) {
        if (inventory[selected] == null) return null;
        Item i = inventory[selected];
        i.drop(playerX, playerY);
        inventory[selected] = null;
        return i;
    }

    public void changeSelected(int n) {
        selected = (selected + n) % 4;
        if (selected < 0) selected += 4;
        // System.out.println("hotbar: " + selected);
    }

    public Item getSelected() {
        return inventory[selected];
    }
    public void removeSelected() {
        inventory[selected] = null;
    }

    public boolean contains(String type) {
        for (Item it : inventory) {
            if (it != null && it.getType().equals(type)) return true;
        }
        return false;
    }
}