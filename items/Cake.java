package items;

public class Cake extends Item {
    public Cake (String extra, int x, int y) {
        super("Cake", x, y, 1);
    }

    @Override
    public String use() {
        return "CakeObject";
    }
}