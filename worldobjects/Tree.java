package worldobjects;

import items.*;
import sprites.*;
import gui.*;

public class Tree extends WorldObject{
    public Tree(String extra, int x, int y) {
        super("Tree", x, y, 50);
    }

    public String hit() {
        return "drop";
    }

    @Override
    public Item getDrop(Player player) {
        if (player.getInventory().getSelected() != null) {
            if (player.getInventory().getSelected().getType().equals("Axe")) {
                return new Wood("none", x + rand(-100, 100), y + rand(-100, 100));
            } 
        }
        return new Leaves("none", x + rand(-100, 100), y + rand(-100, 100));
    }
}