package worldobjects;

import items.*;
import creation.*;
import sprites.*;

import java.util.ArrayList;

public class Oven extends Exchange {

    private ArrayList<String> storage = new ArrayList<>();

    public Oven(String extra, int x, int y) {
        super("Oven", extra, x, y, 80);
        size = 120;
        resize(1);

    }

    public Item getDrop(Player player) {
        if (player.getInventory().getSelected() == null){
            if (storage.contains("Wood") && storage.contains("Batter"))
                return Creator.createItem("Cake", "none", player.getX(), player.getY());
        } else {
            storage.add(player.getInventory().getSelected().getType());
            player.getInventory().removeSelected();
        }
        return null;
    }
}