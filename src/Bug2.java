public class Bug2 extends Insects {

    public Bug2(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    public String getType() {
        return "bug2";
    }

    @Override
    public String getPathToImage() {
        return "bug2.png";
    }

}
