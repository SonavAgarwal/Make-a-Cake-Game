package worldobjects;

public class Safe extends Exchange {

    public Safe(String extra, int x, int y) {
        super("Safe", extra, x, y, 200);
        size = 300;
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
}