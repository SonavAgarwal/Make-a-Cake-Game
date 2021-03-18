package worldobjects;

import items.*;
import sprites.*;
import creation.*;
import gui.*;

public class Exchange extends WorldObject {
    private String input;
    private String output;
    public Exchange (String type, String extra, int x, int y, int hitboxSize) {
        super(type, x, y, hitboxSize);
        input = extra.substring(0, extra.indexOf(":"));
        output = extra.substring(extra.indexOf(":") + 1);
    }

    public String hit() {
        return "drop";
    }

    public Item getDrop(Player player) {
        // System.out.println("here");
        if (player.getInventory().getSelected() != null){
            if (player.getInventory().getSelected().getType().equals(input)) {
                player.getInventory().removeSelected();
                // System.out.println("input: " + input);
                // System.out.println("output: " + output);
                return Creator.createItem(output, "none", player.getX(), player.getY());
            } 
        }
        return null;
    }
}