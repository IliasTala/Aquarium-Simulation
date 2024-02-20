
public class BlueFish extends Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int speed = Board.speedUpgradeBlueFish;
    public double maxDistance = 2000;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    public String getType() {
        return "blueFish";
    }

    @Override
    public String getPathToImage() {
        return "blue_fish.png";
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
    public BlueFish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super(pos_x, pos_y, pos_x_target, pos_y_target, speed);

    }

    @Override
    public void update() {
        updateBlueFish();
        fishMovement();
        couplingFish();
        ifPelletEat();
        ifInsectEat();
        ifFishFoodEat();
        modeActive();
    }

    ///////////////////////////////////////////////////////////
    /// On vérifie si les poissons ciblés sont mauves, bleus
    /// et on oublie pas de s'exclure de la condition
    ///////////////////////////////////////////////////////////
    public void updateBlueFish() {
        double maxDistance = Double.MAX_VALUE;
        for (int i = 0; i < Board.fishList.size(); i++) {
            if (Board.fishList.get(i).getType().equals("purpleFish")
                    || Board.fishList.get(i).getType().equals("blueFish")
                            && !Board.fishList.get(i).equals(this)) {

                int x_dist = Board.fishList.get(i).getPos_x() - getPos_x();
                int y_dist = Board.fishList.get(i).getPos_y() - getPos_y();

                double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                if (distance < maxDistance) {
                    setPos_x_target(Board.fishList.get(i).getPos_x());
                    setPos_y_target(Board.fishList.get(i).getPos_y());
                    maxDistance = distance;

                }
            }

        }

    }

}
