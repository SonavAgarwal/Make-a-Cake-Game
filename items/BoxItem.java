package items;

public class BoxItem extends Item {
    public BoxItem (String extra, int x, int y) {
        super("Box", x, y, 1);
    }

    @Override
    public String use() {
        return "Box";
    }
}