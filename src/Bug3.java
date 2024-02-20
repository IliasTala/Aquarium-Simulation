public class Bug3 extends Insects {

    public Bug3(int pos_x, int pos_y) {
        super(pos_x, pos_y);

    }

    public String getType() {
        return "bug3";
    }

    @Override
    public String getPathToImage() {
        return "bug3.png";
    }

}
