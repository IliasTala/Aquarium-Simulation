import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Fish {

    ///////////////////////////////////////
    ////////////// VARIABLES //////////////
    ///////////////////////////////////////
    private int pos_x;
    private int pos_y;
    private int pos_x_target;
    private int pos_y_target;
    private int speed;
    protected static int stopOtherColorsCounter;
    protected static String fishColorStopped;

    ///////////////////////////////////////
    /////////// GETTER & SETTER ///////////
    ///////////////////////////////////////
    public abstract String getType();

    public abstract String getPathToImage();

    public int getSpeed() {
        return speed;

    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getPos_x_target() {
        return pos_x_target;
    }

    public void setPos_x_target(int pos_x_target) {
        this.pos_x_target = pos_x_target;
    }

    public int getPos_y_target() {
        return pos_y_target;
    }

    public void setPos_y_target(int pos_y_target) {
        this.pos_y_target = pos_y_target;
    }

    static String getColorFishStopped() {
        return fishColorStopped;
    }

    ///////////////////////////////////////
    ///////////// CONSTRUCTOR /////////////
    ///////////////////////////////////////

    public Fish(int pos_x, int pos_y, int pos_x_target, int pos_y_target, int speed) {
        super();
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_x_target = pos_x_target;
        this.pos_y_target = pos_y_target;

    }
    ///////////////////////////////////////
    ///////// METHOD FISH HERITAGE ////////
    ///////////////////////////////////////

    public void update() {

    }

    ////////////////////////////////////////////////////////////
    //// Check si les modes sont actives et les effectuent /////
    ////////////////////////////////////////////////////////////
    void modeActive() {
        if (Board.pelletModeActive) {
            this.fishMovement();
            pelletMode();
        } else if (Board.accouplementModeActive) {
            this.fishMovement();
            accouplementMode();
        } else if (Board.insectModeActive) {
            this.fishMovement();
            insectMode();

        }
    }

    ///////////////////////////////////////
    ///////// METHOD FISH MOVEMENT ////////
    ///////////////////////////////////////
    public void fishMovement() {

        ArrayList<Integer> x_moveOptions = new ArrayList<Integer>();
        ArrayList<Integer> y_moveOptions = new ArrayList<Integer>();
        ArrayList<Double> distances = new ArrayList<Double>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                int test_pos_x = getPos_x() + i * this.getSpeed();
                int test_pos_y = getPos_y() + j * this.getSpeed();

                if (isValidPosition(test_pos_x, test_pos_y) && !decorationColision(test_pos_x, test_pos_y)) {
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

    protected double getDistance(int pos_x0, int pos_y0, int pos_x1, int pos_y1) {
        int x_dist = pos_x1 - pos_x0;
        int y_dist = pos_y1 - pos_y0;
        return Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));
    }

    public boolean isValidPosition(int pos_x, int pos_y) {

        boolean res = true;
        if (pos_y >= Board.sizeBoardHitBoxFishY) {
            res = false;

        }

        if (pos_y < 0) {
            res = false;
        }

        if (pos_x >= Board.sizeBoardHitBoxFishX) {
            res = false;
        }

        if (pos_x < 0) {
            res = false;
        }
        return res;
    }

    ////////////////////////////////////////////////////////////
    // Check la colision avec les deco et change de direction //
    ////////////////////////////////////////////////////////////

    public static boolean decorationColision(int pos_x, int pos_y) {

        for (Decoration each : Board.decorationList) {
            if (pos_x + Board.fishWidth >= each.getPosX() && pos_y + Board.fishHeight >= each.getPosY()
                    && pos_x <= each.getPosX() + Board.decorationWidth
                    && pos_y <= each.getPosY() + Board.decorationHeight) {
                return true;
            }

        }
        return false;
    }

    ///////////////////////////////////////////////////////////
    // Méthode utiliser lorsqu'un poisson mange une fishfood //
    ///////////////////////////////////////////////////////////
    public void eatfishFood() {

        for (Fish fish : Board.fishList) {

            if (fish.getType() == this.getType()) {
                fish.setSpeed(6);
            }
        }
    }

    public void ifFishFoodEat() {
        // Vérification lié aux fishfood
        Iterator<FishFood> iterator = Board.fishFoodList.iterator();
        while (iterator.hasNext()) {
            FishFood fishFood = iterator.next();

            // Vérifie si le poisson est sur une fishfood et elle disparait
            if (this.getPos_x() + Board.fishWidth >= fishFood.getPosX()
                    && this.getPos_y() + Board.fishHeight >= fishFood.getPosY()
                    && this.getPos_x() <= fishFood.getPosX()
                    && this.getPos_y() <= fishFood.getPosY()) {

                eatfishFood();
                iterator.remove();
                Board.allFixedGameElements.remove(fishFood);

            }

        }

    }

    ///////////////////////////////////////////////////////////
    // Méthode utiliser lorsqu'un poisson mange une pastille //
    ///////////////////////////////////////////////////////////
    public void eatPellet() {
        // Arrête tous les mouvements des poissons d'autres couleurs pendant 10 secondes
        for (Fish fish : Board.fishList) {
            if (fish.getType() != this.getType()) {
                fish.setSpeed(0);

                stopOtherColorsCounter = 320;
                fishColorStopped = this.getType();
            }
        }
    }

    public void ifPelletEat() {
        // Vérification lié aux pastilles
        Iterator<Pellet> iterator = Board.pelletList.iterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();

            // Vérifie si le poisson est sur une pellet et elle disparait
            if (this.getPos_x() + Board.fishWidth - getSpeed() >= pellet.getPosX()
                    && this.getPos_y() + Board.fishHeight - getSpeed() >= pellet.getPosY()
                    && this.getPos_x() <= pellet.getPosX()
                    && this.getPos_y() <= pellet.getPosY()) {

                eatPellet();
                iterator.remove();
                Board.allFixedGameElements.remove(pellet);

            }

        }
    }

    ///////////////////////////////////////////////////////////
    /// Méthode utiliser lorsqu'un poisson mange un insecte ///
    ///////////////////////////////////////////////////////////
    public void eatInsect() {
        // Arrête tous les mouvements des poissons d'autres couleurs pendant 10 secondes
        for (int i = 0; i < Board.bugList.size(); i++) {
            if (this.getSpeed() < Board.maxSpeed - 2) {
                this.setSpeed(this.getSpeed() + 2);
                if (Board.bugList.get(i).getType().equals("bug1")) {
                    stopOtherColorsCounter = 100;
                } else if (Board.bugList.get(i).getType().equals("bug2")) {
                    stopOtherColorsCounter = 200;
                } else if (Board.bugList.get(i).getType().equals("bug3")) {
                    stopOtherColorsCounter = 300;
                }
            }
        }

    }

    public void ifInsectEat() {
        // Vérification lié aux insectes
        Iterator<Insects> iterator = Board.bugList.iterator();
        while (iterator.hasNext()) {
            Insects bug = iterator.next();

            // Vérifie si le poisson est sur un insecte et il disparait
            if (this.getPos_x() + Board.fishWidth >= bug.getPosX()
                    && this.getPos_y() + Board.fishHeight >= bug.getPosY()
                    && this.getPos_x() <= bug.getPosX()
                    && this.getPos_y() <= bug.getPosY()) {

                eatInsect();
                iterator.remove();
                Board.allFixedGameElements.remove(bug);

            }

        }
    }

    ///////////////////////////////////////////////////////////
    // Méthode utiliser dans le ActionPerformed pour vérifier /
    //////////// si des poissons ont été arreter //////////////
    ///////////////////////////////////////////////////////////
    public void comportementPelletAndInsects() {
        if (stopOtherColorsCounter > 0) {
            stopOtherColorsCounter--;
        } else {
            if (getType().equals("orangeFish")) {
                setSpeed(10);
            }
            if (getType().equals("blueFish")) {
                setSpeed(Board.speedUpgradeBlueFish);
            }
            if (getType().equals("purpleFish")) {
                setSpeed(Board.speedUpgradePurpleFish);
            }
            if (getType().equals("redFish")) {
                setSpeed(10);
            }
            if (getType().equals("yellowFish")) {
                setSpeed(10);
            }
        }
    }

    ///////////////////////////////////////////////////////////
    // Méthode utiliser lors de l'activation du pellet Mode ///
    ///////////////////////////////////////////////////////////
    public void pelletMode() {
        double maxDistance = Double.MAX_VALUE;
        if (Board.pelletList.size() >= 1) {
            if (getColorFishStopped() == this.getType() ||
                    getColorFishStopped() == null) {
                for (int i = 0; i < Board.pelletList.size(); i++) {

                    int x_dist = Board.pelletList.get(i).getPosX() - this.getPos_x();
                    int y_dist = Board.pelletList.get(i).getPosY() - this.getPos_y();
                    double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                    if (distance < maxDistance) {
                        setPos_x_target(Board.pelletList.get(i).getPosX());
                        setPos_y_target(Board.pelletList.get(i).getPosY());

                        ifPelletEat();
                        maxDistance = distance;

                    }
                }
            }
        } else {
            Board.pelletModeActive = false;
        }
    }

    ///////////////////////////////////////////////////////////
    // Méthode utiliser lors de l'activation du insect Mode ///
    ///////////////////////////////////////////////////////////
    public void insectMode() {
        double maxDistance = Double.MAX_VALUE;
        if (Board.bugList.size() >= 1) {
            if (getColorFishStopped() == this.getType() || getColorFishStopped() == null) {

                for (int i = 0; i < Board.bugList.size(); i++) {
                    int x_dist = Board.bugList.get(i).getPosX() - this.getPos_x();
                    int y_dist = Board.bugList.get(i).getPosY() - this.getPos_y();
                    double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                    if (distance < maxDistance) {
                        this.setPos_x_target(Board.bugList.get(i).getPosX());
                        this.setPos_y_target(Board.bugList.get(i).getPosY());

                        ifInsectEat();
                        maxDistance = distance;
                    }
                }
            }

        } else {
            Board.insectModeActive = false;
        }
    }
    //////////////////////////////////////////////////////////////////
    /// Méthode utiliser lors de l'activation du accouplement Mode ///
    /////////////////////////////////////////////////////////////////

    public void accouplementMode() {
        if (getColorFishStopped() == this.getType() || getColorFishStopped() == null) {
            double maxDistance = 2000;

            for (int i = 0; i < Board.fishList.size(); i++) {

                if (this.getType() == Board.fishList.get(i).getType()
                        && !Board.fishList.get(i).equals(this)) {

                    int x_dist = Board.fishList.get(i).getPos_x() - this.getPos_x();
                    int y_dist = Board.fishList.get(i).getPos_y() - this.getPos_y();

                    double distance = Math.sqrt(Math.pow(x_dist, 2) + Math.pow(y_dist, 2));

                    if (distance < maxDistance) {
                        this.setPos_x_target(Board.fishList.get(i).getPos_x());
                        this.setPos_y_target(Board.fishList.get(i).getPos_y());

                        couplingFish();
                        maxDistance = distance;
                    }
                }
            }
        } else {
            Board.accouplementModeActive = false;
        }
    }

    //////////////////////////////////////////////////////////////////////
    /// Méthode utiliser lors de l'accouplement de fish, elle supprimer //
    //////// les deux poissons s'accouplant et en crée 3 nouveaux ////////
    //////////////////////////////////////////////////////////////////////
    public void couplingFish() {
        for (int i = 0; i < Board.fishList.size(); i++) {
            {
                Fish currentFish = Board.fishList.get(i);

                // Si les deux poissons ont les mêmes coordonnées et le même type
                if (!currentFish.equals(this) && currentFish.getType() == this.getType()
                        && this.getPos_x() + Board.fishWidth >= currentFish.getPos_x()
                        && this.getPos_y() + Board.fishHeight >= currentFish.getPos_y()
                        && this.getPos_x() <= currentFish.getPos_x() + Board.fishWidth
                        && this.getPos_y() <= currentFish.getPos_y() + Board.fishHeight) {

                    int numFish = Board.fishList.size();
                    double probability = 1.0 / (numFish + 1) / 10;

                    // Au plus il y'a de poisson au moins il y'a de chances d'accouplement
                    if (Math.random() < probability && Board.fishList.size() < 20) {

                        Board.fishList.remove(this);
                        Board.fishList.remove(currentFish);
                        positionFishAccouplement();
                        Board.accouplementModeActive = false;
                    }

                }

            }
        }
    }

    ///////////////////////////////////////////////////////////////////////
    /// Méthode utiliser lors de l'accouplement de fish, elle initialise //
    /// les 3 nouveaux poissons et leurs donnent des positions aléatoire //
    ///////////////////////////////////////////////////////////////////////
    protected void positionFishAccouplement() {

        int fish_posX = 0;
        int fish_posy = 0;

        for (int i = 0; i < 3; i++) {

            fish_posX = Board.getRandomPositionX();
            fish_posy = Board.getRandomPositionY();
            while (!Board.checkInitialPositionElement(fish_posX, fish_posy)) {
                fish_posX = Board.getRandomPositionX();
                fish_posy = Board.getRandomPositionY();
            }
            if (getType().equals("orangeFish")) {
                Board.fishList.add(new OrangeFish(fish_posX, fish_posy, pos_x_target, pos_y_target, speed));
            } else if (getType().equals("blueFish")) {
                Board.fishList.add(new BlueFish(fish_posX, fish_posy, pos_x_target, pos_y_target, speed));
            } else if (getType().equals("purpleFish")) {
                Board.fishList.add(new PurpleFish(fish_posX, fish_posy, pos_x_target, pos_y_target, speed));
            } else if (getType().equals("redFish")) {
                Board.fishList.add(new RedFish(fish_posX, fish_posy, pos_x_target, pos_y_target, speed));
            } else if (getType().equals("yellowFish")) {
                Board.fishList.add(new YellowFish(fish_posX, fish_posy, pos_x_target,
                        pos_y_target, speed));
            }
        }
    }

}