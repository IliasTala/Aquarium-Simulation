public class YellowFish extends Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int speed = 10;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    @Override
    public String getType() {
        return "yellowFish";
    }

    @Override
    public String getPathToImage() {
        return "yellow_fish.png";
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
    public YellowFish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super(pos_x, pos_y, pos_x_target, pos_y_target, speed);
    }

    public void update() {
        updateYellowFish();
        fishMovement();
        couplingFish();
        ifPelletEat();
        ifInsectEat();
        modeActive();
    }

    private void updateYellowFish() {
        double maxDistance = Double.MAX_VALUE;
        for (int i = 0; i < Board.fishFoodList.size(); i++) {
            if (Board.fishFoodList.get(i).getType().equals("fishFood")) {

                int x_dist = Board.fishFoodList.get(i).getPosX() - getPos_x();
                int y_dist = Board.fishFoodList.get(i).getPosY() - getPos_y();

                double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                if (distance < maxDistance) {
                    setPos_x_target(Board.fishFoodList.get(i).getPosX());
                    setPos_y_target(Board.fishFoodList.get(i).getPosY());

                    ifFishFoodEat();
                    maxDistance = distance;

                }
            }

        }
    }

}
