package worldobjects;

import items.*;
import creation.*;
import sprites.*;

import java.util.ArrayList;

public class Mixer extends Exchange {
    private ArrayList<String> storage = new ArrayList<>();
    public Mixer(String extra, int x, int y) {
        super("Mixer", extra, x, y, 80);
        size = 120;
        resize(1);
    }
    

    public Item getDrop(Player player) {
        if (player.getInventory().getSelected() == null){
            if (storage.contains("Milk") && storage.contains("Flour") && storage.contains("Sugar"))
                return Creator.createItem("Batter", "none", player.getX(), player.getY());
        } else {
            storage.add(player.getInventory().getSelected().getType());
            player.getInventory().removeSelected();
        }
        return null;
    }
}