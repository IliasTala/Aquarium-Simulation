public class Pellet extends FixedGameElement {

    public Pellet(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String getType() {
        return "pellet";
    }

    @Override
    public String getPathToImage() {
        return "pellet.png";
    }

}
