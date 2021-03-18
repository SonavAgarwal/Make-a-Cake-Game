package creation;

import java.awt.*;
import java.util.ArrayList;

public class Words {
    private static final int duration = 160;
    private static int time = duration - 5;
    private static Font myFont = new Font("Arial", Font.BOLD, 30);
    private static int drawX = 0;
    private static int drawY = 0;
    private static String line = "     ";
    private static String drawnLine = "     ";

    public static void setUp(int ww, int wh) {
        drawX = 10;
        drawY = wh - 15;
    }

    public static void render(Graphics g, int worldScreenX, int worldScreenY) {
        drawnLine = line.substring(0, limit(time, 0, line.length()));
        if (time < duration) {
            g.setFont(myFont);
            g.drawString(drawnLine, drawX - (g.getFontMetrics().stringWidth(drawnLine) / 2) + worldScreenX, drawY + worldScreenY); 
            time++;
        }
    }

    public static void showLine(String l, int showerx, int showery, int size) {
        myFont = new Font("Arial", Font.BOLD, size);
        drawX = showerx;
        drawY = showery;
        time = 0;
        line = l;
    }
    
    public static boolean hasLine() {
        return time < duration;
    }

    public static int limit(int x, int min, int max) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public static void end() {
        time = duration;
    }
}