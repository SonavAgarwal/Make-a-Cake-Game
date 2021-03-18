import worldobjects.*;
import items.*;
import gui.*;
import sprites.*;
import creation.*;

import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

import java.lang.reflect.*;

public class World {
    private ArrayList<WorldObject> worldObjects = new ArrayList<WorldObject>(); 
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<NPC> npcs = new ArrayList<NPC>();
    private Hotbar inventory;

    private Player player;

    private int worldX, worldY;
    private int prevWorldX, prevWorldY;
    private double xVel, yVel;
    private int maxVel = 7;
    private boolean[] moving = {false, false, false, false};

    private int mouseX = 0; 
    private int mouseY = 0;
    private double angleToMouse;

    private int halfScreenWidth;
    private int halfScreenHeight;

    private Color worldColor;
    private Map map;

    private Words words;

    public World(String worldName, int screenWidth, int screenHeight, Player p) {

        try {
            File worldData = new File("Worlds/" + worldName + ".txt");
            Scanner worldReader = new Scanner(worldData);

            worldColor = new Color(worldReader.nextInt(), worldReader.nextInt(), worldReader.nextInt());

            while (worldReader.hasNextLine()) {
                String objectType = worldReader.next();
                String className = worldReader.next();
                String extraInfo = worldReader.next();
                int objX = worldReader.nextInt();
                int objY = worldReader.nextInt();
                //random position

                if (objX == 7777777) objX = rand(-1900, 1900);
                if (objY == 7777777) objY = rand(-1900, 1900);

                if (objectType.equals("WorldObject")) {
                    worldObjects.add(Creator.createWorldObject(className, extraInfo, objX, objY));
                } else if (objectType.equals("Item")) {
                    items.add(Creator.createItem(className, extraInfo, objX, objY));
                } else if (objectType.equals("NPC")) {
                    ArrayList<String> lines = new ArrayList<>();
                    String garbagio = worldReader.nextLine();
                    int lineCt = Integer.parseInt(extraInfo);
                    for (int i = 0; i < lineCt; i++) {
                        lines.add(worldReader.nextLine());
                    }
                    npcs.add(Creator.createNPC(className, objX, objY, lines));
                }
                
            }
            worldReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // System.out.println(this.getClass().getCanonicalName());

        
        map = new Map(worldObjects, worldColor, screenWidth, screenHeight);        

        halfScreenHeight = screenHeight / 2;
        halfScreenWidth = screenWidth / 2;

        worldX = 0;
        worldY = 0;
        prevWorldX = 0;
        prevWorldY = 0;
        xVel = 0;
        yVel = 0;
        
        player = p;
        inventory = p.getInventory();
    }

    public void render(Graphics g) {

        g.setColor(worldColor);
        g.fillRect(-2000, -2000, 4000, 4000);

        for (Item it : items) {
            it.render(g, getScreenX(worldX), getScreenY(worldY));
        }

        for (NPC npc : npcs) {
            npc.render(g, getScreenX(worldX), getScreenY(worldY));
        }

        player.render(g, angleToMouse);

        for (WorldObject obj : worldObjects) {
            obj.render(g, getScreenX(worldX), getScreenY(worldY));
        }
        
        inventory.render(g);
        map.render(g, player.getX(), player.getY());
        Words.render(g, getScreenX(worldX), getScreenY(worldY));
    }

    public void tick(int mx, int my) {
        updateMousePos(mx, my);
        movePlayer();
        updateWorld();
        pickUpItems();
    }

    public void movePlayer() {
        if (moving[0]) {
            yVel = limit(yVel - 1, -maxVel, maxVel);
        }
        if (moving[1]) {
            xVel = limit(xVel - 1, -maxVel, maxVel);
        }
        if (moving[2]) {
            yVel = limit(yVel + 1, -maxVel, maxVel);
        }
        if (moving[3]) {
            xVel = limit(xVel + 1, -maxVel, maxVel);
        }
        xVel = Math.round(xVel * 90) / 100.0;
        yVel = Math.round(yVel * 90) / 100.0;

        if (Math.abs(xVel) < 0.1) xVel = 0;
        if (Math.abs(yVel) < 0.1) yVel = 0;

        prevWorldX = worldX;
        prevWorldY = worldY;
        worldX = limit((int) (worldX - xVel), -2000, 2000);
        worldY = limit((int) (worldY - yVel), -2000, 2000);
        player.setPos(-worldX, -worldY);

        // System.out.println("pos: " + worldX + " " + worldY);

        for (WorldObject w : worldObjects) {
            if (w.isColliding(player.getX(), player.getY())) {
                worldX = prevWorldX;
                worldY = prevWorldY;
                player.setPos(-worldX, -worldY);
                xVel = 0;
                yVel = 0;
                break;
            }
        }
    }

    public void updateWorld() {
        for (WorldObject w : worldObjects) {
            w.update(player);
        }
        for (NPC npc : npcs) {
            npc.update(player);
        }
    }

    public void pickUpItems() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isInRange(player.getX(), player.getY())) {
                if (inventory.addItem(items.get(i))) {
                    items.remove(i);
                } else {
                    continue;
                }
            }
        }
    }

    public void addItem(Item it) {
        items.add(it);
    }

    public void interpretKeyPress(int keyCode) {
        if (keyCode == 87) {
            moving[0] = true;
        } else if (keyCode == 65) {
            moving[1] = true;
        } else if (keyCode == 83) {
            moving[2] = true;
        } else if (keyCode == 68) {
            moving[3] = true;
        } else if (keyCode == 49) {
            inventory.setSelected(0);
        } else if (keyCode == 50) {
            inventory.setSelected(1);
        } else if (keyCode == 51) {
            inventory.setSelected(2);
        } else if (keyCode == 52) {
            inventory.setSelected(3);
        } else if (keyCode == 81) {
            Item i = inventory.drop(player.getX(), player.getY());
            if (i != null) items.add(i);
        }
    }
    public void interpretKeyRelease(int keyCode) {
        if (keyCode == 87) {
            moving[0] = false;
        } else if (keyCode == 65) {
            moving[1] = false;
        } else if (keyCode == 83) {
            moving[2] = false;
        } else if (keyCode == 68) {
            moving[3] = false;
        }
    }
    public void interpretMouseClick() {
        player.punch();
        for (WorldObject w : worldObjects) {
            if (w.isInRange(player.getX(), player.getY(), angleToMouse)) {
                String action = w.hit();
                if (action.equals("break")) {
                    Item dropStor = w.getDrop(player);
                    if (dropStor != null) items.add(dropStor);
                    worldObjects.remove(w);
                } else if (action.equals("drop")) {
                    Item dropStor = w.getDrop(player);
                    if (dropStor != null) items.add(dropStor);
                } else if (action.equals("use")) {
                    w.getDrop(player);
                } else if (action.equals("none"));
                
                return;
            }
        }
        if (inventory.getSelected() != null) {
            String objStor = inventory.getSelected().use();
            if (objStor != null) {
                worldObjects.add(Creator.createWorldObject(objStor, "none", player.getX() + (int) (150 * Math.cos(-angleToMouse)), player.getY() + (int) (150 * Math.sin(-angleToMouse))));
                inventory.removeSelected();
            } 
        }
    }
    public void interpretWheel(int notches) {
        if (notches >= 1) {
            inventory.changeSelected(1);
        } else if (notches <= -1) {
            inventory.changeSelected(-1);
        }
    }

    public void updateMousePos(int mx, int my) {
        mouseX = mx;
        mouseY = my;
        angleToMouse = Math.atan2((double) (halfScreenHeight - mouseY) , (mouseX - halfScreenWidth));
    }

    public void setPos(int px, int py) {
        worldX = -px;
        worldY = -py;
    }

    public int getScreenX(int wx) {
        return wx + halfScreenWidth;
    }
    public int getScreenY(int wy) {
        return wy + halfScreenHeight;
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public int rand(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1) + min);
    }

    public int limit(int x, int min, int max) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
    public double limit(double x, double min, double max) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

}