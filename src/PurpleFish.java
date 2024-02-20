public class PurpleFish extends Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int speed = Board.speedUpgradePurpleFish;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    public String getType() {
        return "purpleFish";
    }

    @Override
    public String getPathToImage() {
        return "purple_fish.png";
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
    public PurpleFish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super(pos_x, pos_y, pos_x_target, pos_y_target, speed);
    }

    @Override
    public void update() {

        updatePurpleFish();
        fishMovement();
        couplingFish();
        ifPelletEat();
        ifInsectEat();
        ifFishFoodEat();
        modeActive();

    }

    private void updatePurpleFish() {
        double maxDistance = Double.MAX_VALUE;
        int targetX = 0;
        int targetY = 0;
        for (int i = 0; i < Board.fishList.size(); i++) {

            if (Board.fishList.get(i).getType().equals("redFish")) {

                int x_dist = Board.fishList.get(i).getPos_x() - getPos_x();
                int y_dist = Board.fishList.get(i).getPos_y() - getPos_y();

                double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                if (distance < maxDistance) {
                    maxDistance = distance;
                    targetX = Board.fishList.get(i).getPos_x();
                    targetY = Board.fishList.get(i).getPos_y();
                }
            }
        }

        // Si un poisson rouge a été trouvé, déplace le poisson purple dans la direction
        // opposée
        if (maxDistance != Double.MAX_VALUE) {
            setPos_x_target(2 * getPos_x() - targetX);
            setPos_y_target(2 * getPos_y() - targetY);
        }
    }
}
