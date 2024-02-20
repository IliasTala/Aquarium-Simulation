import java.util.ArrayList;
import java.util.Collections;

public class OrangeFish extends Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int speed = 10;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    public String getType() {
        return "orangeFish";
    }

    @Override
    public String getPathToImage() {
        return "orange_fish.png";
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
    public OrangeFish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super(pos_x, pos_y, pos_x_target, pos_y_target, speed);
    }

    @Override
    public void update() {

        fishMovement();
        updateOrangeFish();
        couplingFish();
        ifPelletEat();
        ifInsectEat();
        ifFishFoodEat();
        modeActive();
    }

    ////////////////////////////////////////////////////
    // Vérifie les collisions avec les murs/décoration//
    ////// et donne une nouvelle position random ///////
    ////////////////////////////////////////////////////
    public void updateOrangeFish() {

        if (getPos_x_target() == 0 && getPos_y_target() == 0) {
            randomMovement();
        }

        if (this.getPos_x() <= 0 || this.getPos_x() >= Board.sizeBoardHitBoxFishX - getSpeed() || this.getPos_y() == 0
                || this.getPos_y() >= Board.sizeBoardHitBoxFishY - getSpeed()
                || decorationColision(this.getPos_x(), this.getPos_y())) {
            randomMovement();
        }
    }

    /////////////////////////////////////////////
    // Mouvement aléatoire des poissons//////////
    /////////////////////////////////////////////
    public void randomMovement() {
        int randomMovement = (int) (Math.random() * 3);
        if (randomMovement == 0) {
            setPos_x_target(0);
            setPos_y_target((int) (Math.random() * Board.sizeBoardHitBoxFishY));
        } else if (randomMovement == 1) {
            setPos_x_target(Board.sizeBoardHitBoxFishX);
            setPos_y_target((int) (Math.random() * Board.sizeBoardHitBoxFishY));
        } else if (randomMovement == 2) {
            setPos_x_target((int) (Math.random() * Board.sizeBoardHitBoxFishX));
            setPos_y_target(0);
        } else if (randomMovement == 3) {
            setPos_x_target((int) (Math.random() * Board.sizeBoardHitBoxFishX));
            setPos_y_target(Board.sizeBoardHitBoxFishY);
        }
    }

    ////////////////////////////////////////////////
    // Un Override de la méthode de la classe Fish//
    //// Pour pouvoir permettre aux OrangeFish /////
    //////// de rebondir sur les décorations ///////
    ////////////////////////////////////////////////
    @Override
    public void fishMovement() {

        ArrayList<Integer> x_moveOptions = new ArrayList<Integer>();
        ArrayList<Integer> y_moveOptions = new ArrayList<Integer>();
        ArrayList<Double> distances = new ArrayList<Double>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                int test_pos_x = getPos_x() + i * getSpeed();
                int test_pos_y = getPos_y() + j * getSpeed();

                if (isValidPosition(test_pos_x, test_pos_y)) {
                    x_moveOptions.add(test_pos_x);
                    y_moveOptions.add(test_pos_y);
                }
            }
        }
        for (int i = 0; i < x_moveOptions.size(); i++) {
            Double distance = getDistance(getPos_x_target(), getPos_y_target(), x_moveOptions.get(i),
                    y_moveOptions.get(i));
            distances.add(distance);

        }

        double min = Collections.min(distances);
        int min_index = distances.indexOf(min);

        setPos_x(x_moveOptions.get(min_index));
        setPos_y(y_moveOptions.get(min_index));

    }

}
