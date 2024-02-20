public class FishFood extends FixedGameElement {

    public FishFood(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String getType() {
        return "fishFood";
    }

    @Override
    public String getPathToImage() {
        return "fishFood.png";
    }

}
