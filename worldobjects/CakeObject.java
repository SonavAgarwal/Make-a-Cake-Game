package worldobjects;

import items.*;
import sprites.*;

import java.awt.*;

public class CakeObject extends WorldObject {
    public CakeObject(String extra, int x, int y) {
        super("Cake", x, y, 160);
        size = 200;
        resize(1);
    }

    @Override
    public String toString() {
        return "a cake " + x + " " + y;
    }

    public String hit() {
        return "break";
    }
    public Item getDrop(Player p) {
        if (p.getInventory().getSelected() == null) {
            return new Cake("none", x, y);
        } else if (p.getInventory().getSelected().getType().equals("Icing")) {
            return new IcedCake("I", x, y);
        }
        return null;
    }

    public boolean isColliding(int playerX, int playerY) {
        int pX = playerX - 40;
		int pY = playerY - 40;
		int pWidth = 80;
		int pHeight = 80;
		
		int tX = x - 70;
		int tY = y - 50;
		int tWidth = 140;
		int tHeight = 100;
        if (pX+pWidth >= tX && pX <= tX + tWidth  &&  pY+pHeight >= tY && pY <= tY + tHeight) return true;
        return false;
    }
}