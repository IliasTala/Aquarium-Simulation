public class Decoration extends FixedGameElement {

    public Decoration(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String getType() {
        return "decoration";
    }

    @Override
    public String getPathToImage() {
        return "decoration.png";
    }

}