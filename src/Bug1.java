public class Bug1 extends Insects {

    public Bug1(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    public String getType() {
        return "bug1";
    }

    @Override
    public String getPathToImage() {
        return "bug1.png";
    }

}
