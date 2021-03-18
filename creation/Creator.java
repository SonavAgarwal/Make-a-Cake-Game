package creation;

import worldobjects.*;
import items.*;
import sprites.*;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Creator {
    public static Item createItem(String type, String extra, int x, int y) {
        if (type.equals("none")) return null;
        try {
            Constructor c = Class.forName("items." + type).getConstructor(String.class, Integer.TYPE, Integer.TYPE);
            return (Item) c.newInstance(extra, x, y); 
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return null;
    }

    public static WorldObject createWorldObject(String type, String extra, int x, int y) {
        if (type.equals("none")) return null;
        try {
            Constructor c = Class.forName("worldobjects." + type).getConstructor(String.class, Integer.TYPE, Integer.TYPE);
            return (WorldObject) c.newInstance(extra, x, y); 
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return null;
    }

    public static NPC createNPC(String skin, int x, int y, ArrayList<String> lines) {
        return new NPC(skin, x, y, lines);
    }
}