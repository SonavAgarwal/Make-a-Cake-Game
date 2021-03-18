package worldobjects;

import items.*;
import creation.*;
import sprites.*;

public class Box extends WorldObject {

    public Box(String extra, int x, int y) {
        super("Box", x, y, 160);
        size = 200;
        resize(1);

    }
    public boolean isColliding(int playerX, int playerY) {
        int pX = playerX - 40;
		int pY = playerY - 40;
		int pWidth = 80;
		int pHeight = 80;
		
		int tX = x - size / 2;
		int tY = y - size / 2;
		int tWidth = size;
		int tHeight = size;
        if (pX+pWidth >= tX && pX <= tX + tWidth  &&  pY+pHeight >= tY && pY <= tY + tHeight) return true;
        return false;
    }

    public String hit() {
        return "break";
    }

    public Item getDrop(Player player) {
        return new BoxItem("none", x, y);
    }
}