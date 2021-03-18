package worldobjects;

import items.*;

public class Shack extends Building{
    public Shack(String extra, int x, int y) {
        super("Shack", extra, x, y, 500);
        viewRange = 200;
    }

    @Override
    public boolean isColliding(int playerX, int playerY) {
        int pX = playerX - 40;
		int pY = playerY - 40;
		int pWidth = 80;
		int pHeight = 80;
		
        if (pX+pWidth >= x - 180 && pX <= x + -150  &&  pY+pHeight >= y-100 && pY <= y + 100) return true;
        if (pX+pWidth >= x + 150 && pX <= x + 180  &&  pY+pHeight >= y-100 && pY <= y + 100) return true;
        if (pX+pWidth >= x - 180 && pX <= x + 180  &&  pY+pHeight >= y-100 && pY <= y-70) return true;
        return false;
    }

    
}