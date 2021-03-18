package items;

public class IcedCake extends Item {
    public IcedCake (String extra, int x, int y) {
        super("IcedCake", x, y, 1);
    }

    @Override
    public String use() {
        return "IcedCakeObject";
    }
}