package worldobjects;

import items.*;

public class Mill extends Building {
    public Mill(String extra, int x, int y) {
        super("Mill", extra, x, y, 400);
        viewRange = 300;
        hitboxSize = 200;
    }

    @Override
    public boolean isColliding(int playerX, int playerY) {
        double dist = getDist(x, y, playerX, playerY);
        if (dist < hitboxSize && dist > hitboxSize - 40) return true;
        return false;
    }
}