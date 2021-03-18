package worldobjects;

import items.*;

public class Cow extends Exchange {
    public Cow(String extra, int x, int y) {
        super("Cow", extra, x, y, 80);
        size = 120;
        resize(1);
    }
}