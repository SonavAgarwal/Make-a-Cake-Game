import sprites.*;
import creation.*;
import items.*;
import gui.*;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;



public class Screen extends JPanel implements KeyListener, MouseListener, MouseWheelListener {

    private World currentWorld;
    private int selectedWorldNum;
    private World[] worlds;
    private World tutorial;

    private JFrame fr;
    private int screenWidth = 1000;
    private int screenHeight = 700;

    private int spaceCt = 0;

    private Player player;

    private Font myFont = new Font("Arial", Font.BOLD, 20);

    private String[] quests = {"Find wood : 1", "Make a cake : 2", "Get an iced cake : 3", "Get gold."};
    private int currentQuest = 0;

    private boolean inGame = false;
    private boolean fin = false;

    private BufferedImage landingImage, finImage;

	public Screen(JFrame fr){

        try {
            landingImage = ImageIO.read(new File("Images/Background.png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for background");
        }

        try {
            finImage = ImageIO.read(new File("Images/FinImage.png"));
        } catch (IOException e) {
            System.out.println("Couldn't find image for finish");
        }

        player = new Player(screenWidth / 2, screenHeight / 2);
        Words.setUp(screenWidth, screenHeight);
        
        worlds = new World[2];
        for (int i = 0; i < worlds.length; i++) {
            worlds[i] = new World("World" + (i + 1), screenWidth, screenHeight, player);
        }
        currentWorld = worlds[0];
        selectedWorldNum = 0;

        tutorial = new World("Tutorial", screenWidth, screenHeight, player);

        this.fr = fr;

        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(screenWidth, screenHeight);
	}
	
	public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(myFont);
        if (fin) {
            g.drawImage(finImage, 0, 0, null);
        } else {
            if (inGame) {
                currentWorld.render(g);
                g.drawString(quests[currentQuest], screenWidth - 10 - g.getFontMetrics().stringWidth(quests[currentQuest]), 30);
            } else {
                g.drawImage(landingImage, 0, 0, null);
                g.drawString("Make A Cake", (screenWidth / 2) - (g.getFontMetrics().stringWidth("Make A Cake") / 2), 200);
                g.drawString("Start", (screenWidth / 2) - g.getFontMetrics().stringWidth("Start") - 20, 400);
                g.drawRect((screenWidth / 2) - g.getFontMetrics().stringWidth("Start") - 30, 370, g.getFontMetrics().stringWidth("Start") + 20, 50);
                g.drawString("Instructions", (screenWidth / 2) + 20, 400);
                g.drawRect((screenWidth / 2) + 10, 370, g.getFontMetrics().stringWidth("Instructions") + 20, 50);
            }
        }
    }
    
	public void animate() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				System.out.println("yeeted");
            }
            
            if (inGame) {
                currentWorld.tick(MouseInfo.getPointerInfo().getLocation().x - fr.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - fr.getLocationOnScreen().y);

                if (spaceCt > 5) player.shake();
                player.tick();

                if (spaceCt > 70) {
                    nextWorld();
                    spaceCt = 0;
                }

                checkQuests();
            }

			repaint();
		}
    }

    public void checkQuests() {
        if (currentQuest == 0) {
            if (player.getInventory().contains("Wood")) {
                currentQuest = 1;
                SP.playSound("Chime");
            }
        } else if (currentQuest == 1) {
            if (player.getInventory().contains("Cake")) {
                currentQuest = 2;
                SP.playSound("Chime");
            }
        } else if (currentQuest == 2) {
            if (player.getInventory().contains("IcedCake")) {
                currentQuest = 0;
                fin = true;
                player.getInventory().clear();
                SP.playSound("Chime");
            }
        } else if (currentQuest == 3) {
            if (player.getInventory().contains("Gold")) {
                currentQuest = 0;
                inGame = false;
                currentWorld = worlds[0];
            }
        }
    }
    
    public void nextWorld() {
        selectedWorldNum = (selectedWorldNum + 1) % worlds.length;
        currentWorld = worlds[selectedWorldNum];
        currentWorld.setPos(player.getX(), player.getY());
        // player.setPos(0, -200);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 32) {
            spaceCt++;
        } else if (e.getKeyCode() == 80) {
            if (currentQuest == 0) {
                currentWorld.addItem(new Wood("none", player.getX(), player.getY()));
            } else if (currentQuest == 1) {
                currentWorld.addItem(new Cake("none", player.getX(), player.getY()));
            } else if (currentQuest == 2) {
                currentWorld.addItem(new IcedCake("none", player.getX(), player.getY()));
            }
            
        }
        currentWorld.interpretKeyPress(e.getKeyCode());
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            spaceCt = 0;
            Words.end();
        }
        currentWorld.interpretKeyRelease(e.getKeyCode());
    }
    @Override
    public void keyTyped(KeyEvent e)  {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (inGame) currentWorld.interpretWheel(e.getWheelRotation());
     }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (inGame) currentWorld.interpretMouseClick();
        else {
            if (e.getX() > screenWidth / 2) {
                inGame = true;
                currentWorld = tutorial;
                currentQuest = 3;
            } else if (e.getX() < screenWidth / 2) {
                inGame = true;
                currentWorld = worlds[0];
                currentQuest = 0;
                player.getInventory().clear();
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
}