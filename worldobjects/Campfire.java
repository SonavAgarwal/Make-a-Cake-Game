package worldobjects;

import items.*;
import sprites.*;
import creation.*;
import gui.*;


public class Campfire extends Exchange {
    
    public Campfire(String extra, int x, int y) {
        super("Campfire1", extra, x, y, 80);
        size = 160;
        resize(1);
    }

    public String hit() {
        return "drop";
    }

    public Item getDrop(Player player) {
        if (player.getInventory().getSelected() != null){
            if (player.getInventory().getSelected().getType().equals("Wood"))
            {
                player.getInventory().removeSelected();
                loadImage("Campfire2");
                resize(1);
                return Creator.createItem("Sugar", "none", player.getX(), player.getY());
            }
        }
        return null;
    }
}