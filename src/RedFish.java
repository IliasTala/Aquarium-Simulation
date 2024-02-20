
public class RedFish extends Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int speed = 10;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    public String getType() {
        return "redFish";
    }

    @Override
    public String getPathToImage() {
        return "red_fish.png";
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;

    }
    ///////////////////////////////////////
    ///////////// CONSTRUCTOR /////////////
    ///////////////////////////////////////

    public RedFish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super(pos_x, pos_y, pos_x_target, pos_y_target, speed);
    }

    @Override
    public void update() {
        updateRedFish();
        fishMovement();
        couplingFish();
        ifPelletEat();
        ifInsectEat();
        ifFishFoodEat();
    }

    public void updateRedFish() {
        double maxDistance = Double.MAX_VALUE;
        for (int i = 0; i < Board.fishList.size(); i++) {

            if (!Board.fishList.get(i).getType().equals("redFish")) {

                int x_dist = Board.fishList.get(i).getPos_x() - getPos_x();
                int y_dist = Board.fishList.get(i).getPos_y() - getPos_y();

                double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                if (distance < maxDistance) {
                    setPos_x_target(Board.fishList.get(i).getPos_x());
                    setPos_y_target(Board.fishList.get(i).getPos_y());

                    maxDistance = distance;
                }
                // Vérifie si le redFish est sur un sa proie et elle disparait
                if (this.getPos_x() + Board.fishWidth >= Board.fishList.get(i).getPos_x()
                        && this.getPos_y() + Board.fishHeight >= Board.fishList.get(i).getPos_y()
                        && this.getPos_x() <= Board.fishList.get(i).getPos_x() + Board.fishWidth / 4
                        && this.getPos_y() <= Board.fishList.get(i).getPos_y() + Board.fishHeight / 4) {

                    Board.fishList.remove(i);
                }
            }
        }
    }

}
