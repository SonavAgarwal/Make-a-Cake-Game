package worldobjects;

import items.*;
import sprites.*;
import creation.*;
import gui.*;


public class Cactus extends Exchange {
    
    public Cactus(String extra, int x, int y) {
        super("Cactus", extra, x, y, 80);
        size = 160;
        resize(1);
    }

    // public Item hit(Player player) {
    //     if (player.getInventory().getSelected() != null){
    //         player.getInventory().removeSelected();
    //         return Creator.createItem("none", "none", player.getX(), player.getY());
    //     }
    //     return null;
    // }
}