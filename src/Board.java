import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    public final static int B_WIDTH = 1280;
    public final static int B_HEIGHT = 720;

    private final int DELAY = 140;

    private Timer timer = new Timer(DELAY, this);

    // all final param
    public final static int sizeBoardHitBoxFishX = 1280 - Board.fishWidth;
    public final static int sizeBoardHitBoxFishY = 720 - Board.fishHeight;

    public final static int fishWidth = 100;
    public final static int fishHeight = 50;

    private final int speedUpgradeRedFish = 13;
    private final int speedDowngradeRedFish = 7;

    public final static int decorationWidth = 150;
    public final static int decorationHeight = 200;

    public final static int maxSpeed = 20;

    // speedFish Purple and blue
    public static int speedUpgradePurpleFish;
    public static int speedUpgradeBlueFish = 12;

    // var mode
    static boolean pelletModeActive = false;
    static boolean accouplementModeActive = false;
    static boolean insectModeActive = false;
    static boolean stopFish = false;

    // default param
    private int void_x = 0;
    private int void_y = 0;
    private int defaultX = 0;
    private int defaultY = 0;
    private int defaultSpeed = 10;
    private int target_x = 0;
    private int target_y = 0;

    // list
    public static ArrayList<Fish> fishList;
    public static ArrayList<Pellet> pelletList;
    public static ArrayList<Decoration> decorationList;
    public static ArrayList<FishFood> fishFoodList;
    public static ArrayList<Insects> bugList;
    public static ArrayList<FixedGameElement> allFixedGameElements;

    // hashmap
    HashMap<String, ImageIcon> allGameElementHashMap;
    HashMap<String, ImageIcon> allFixedGameElementHashMap;

    // objects
    BlueFish blueFish = new BlueFish(defaultX, defaultY, target_x, target_y, defaultSpeed);
    RedFish redFish = new RedFish(defaultX, defaultY, target_x, target_y, defaultSpeed);
    PurpleFish purpleFish = new PurpleFish(defaultX, defaultY, target_x, target_y, defaultSpeed);
    OrangeFish orangeFish = new OrangeFish(defaultX, defaultY, target_x, target_y, defaultSpeed);
    YellowFish yellowFish = new YellowFish(defaultX, defaultY, target_x, target_y, defaultSpeed);

    Pellet pellet = new Pellet(defaultX, defaultY);
    Decoration decoration = new Decoration(defaultX, defaultY);
    FishFood fishFood = new FishFood(defaultX, defaultY);

    Bug1 bug1 = new Bug1(defaultX, defaultY);
    Bug2 bug2 = new Bug2(defaultX, defaultY);
    Bug3 bug3 = new Bug3(defaultX, defaultY);

    private ImageIcon imageBackground;

    public Board() {
        initBoard();

    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        imageBackground = new ImageIcon("seaScape.png");
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();

    }

    private void loadImages() {

        allGameElementHashMap = new HashMap<String, ImageIcon>();
        allFixedGameElementHashMap = new HashMap<String, ImageIcon>();

        ImageIcon ibf = new ImageIcon(blueFish.getPathToImage());
        allGameElementHashMap.put("blueFish", ibf);

        ImageIcon iof = new ImageIcon(orangeFish.getPathToImage());
        allGameElementHashMap.put("orangeFish", iof);

        ImageIcon ipf = new ImageIcon(purpleFish.getPathToImage());
        allGameElementHashMap.put("purpleFish", ipf);

        ImageIcon irf = new ImageIcon(redFish.getPathToImage());
        allGameElementHashMap.put("redFish", irf);

        ImageIcon iyf = new ImageIcon(yellowFish.getPathToImage());
        allGameElementHashMap.put("yellowFish", iyf);

        ImageIcon iPellet = new ImageIcon(pellet.getPathToImage());
        allFixedGameElementHashMap.put("pellet", iPellet);

        ImageIcon iDeco = new ImageIcon(decoration.getPathToImage());
        allFixedGameElementHashMap.put("decoration", iDeco);

        ImageIcon iFishFood = new ImageIcon(fishFood.getPathToImage());
        allFixedGameElementHashMap.put("fishFood", iFishFood);

        ImageIcon iBug1 = new ImageIcon(bug1.getPathToImage());
        allFixedGameElementHashMap.put("bug1", iBug1);

        ImageIcon iBug2 = new ImageIcon(bug2.getPathToImage());
        allFixedGameElementHashMap.put("bug2", iBug2);

        ImageIcon iBug3 = new ImageIcon(bug3.getPathToImage());
        allFixedGameElementHashMap.put("bug3", iBug3);
    }

    private void initGame() {

        allFixedGameElements = new ArrayList<FixedGameElement>();

        decorationIntoList();
        positionDecoration();

        pelletIntoList();
        positionPellet();

        bugIntoList();
        positionBug();

        fishFoodIntoList();
        positionFishFood();

        fishIntoList();
        positionFish();

        allFixedGameElements.addAll(pelletList);
        allFixedGameElements.addAll(fishFoodList);
        allFixedGameElements.addAll(decorationList);
        allFixedGameElements.addAll(bugList);

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageBackground.getImage(), 0, 0, this);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        for (Fish elem : fishList) {
            g.drawImage(allGameElementHashMap.get(elem.getType()).getImage(), elem.getPos_x(), elem.getPos_y(), this);
        }
        for (FixedGameElement elemF : allFixedGameElements) {
            g.drawImage(allFixedGameElementHashMap.get(elemF.getType()).getImage(), elemF.getPosX(), elemF.getPosY(),
                    this);
        }
        Toolkit.getDefaultToolkit().sync();

    }

    ////////////////////
    //////// FISH //////
    ////////////////////
    public void fishIntoList() {
        // Ajouter des poissons dans la liste
        fishList = new ArrayList<Fish>();

        while (fishList.size() < 8) {

            fishList.add(new RedFish(void_x, void_y,
                    target_x, target_y, defaultSpeed));

            fishList.add(new BlueFish(void_x, void_y,
                    target_x, target_y, defaultSpeed));

            fishList.add(new OrangeFish(void_x, void_y, target_x, target_y,
                    defaultSpeed));

            fishList.add(new PurpleFish(void_x, void_y, target_x, target_y,
                    defaultSpeed));

            fishList.add(new YellowFish(void_x, void_y, target_x, target_y,
                    defaultSpeed));
        }

    }

    private void positionFish() {

        int fish_posX = 0;
        int fish_posy = 0;

        for (Fish objFish : fishList) {
            fish_posX = getRandomPositionX();
            fish_posy = getRandomPositionY();
            while (!checkInitialPositionElement(fish_posX, fish_posy)) {
                fish_posX = getRandomPositionX();
                fish_posy = getRandomPositionY();
            }
            objFish.setPos_x(fish_posX);
            objFish.setPos_y(fish_posy);

        }

    }

    //////////////
    /// PELLET ///
    //////////////

    public void pelletIntoList() {
        pelletList = new ArrayList<Pellet>();

        for (int i = 1; i < 2; i++) {
            pelletList.add(new Pellet(void_x, void_y));
        }

    }

    private void positionPellet() {

        int posX = 0;
        int posy = 0;

        for (Pellet objPellet : pelletList) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
            while (!checkInitialPositionElement(posX, posy)) {
                posX = getRandomPositionX();
                posy = getRandomPositionY();
            }
            objPellet.setPosX(posX);
            objPellet.setPosY(posy);

        }
    }
    //////////////
    /// INSECT ///
    //////////////

    public void bugIntoList() {
        bugList = new ArrayList<Insects>();

        for (int i = 1; i < 2; i++) {
            bugList.add(new Bug1(void_x, void_y));
            bugList.add(new Bug2(void_x, void_y));
            bugList.add(new Bug3(void_x, void_y));
        }
    }

    public void positionBug() {
        int posX = 0;
        int posy = 0;

        for (Insects objBug : bugList) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
            while (!checkInitialPositionElement(posX, posy)) {
                posX = getRandomPositionX();
                posy = getRandomPositionY();
            }
            objBug.setPosX(posX);
            objBug.setPosY(posy);

        }
    }
    /////////////////////////
    /////// FISH FOOD ///////
    /////////////////////////

    public void fishFoodIntoList() {
        fishFoodList = new ArrayList<FishFood>();

        for (int i = 1; i < 2; i++) {
            fishFoodList.add(new FishFood(void_x, void_y));
        }
    }

    private void positionFishFood() {
        int posX = 0;
        int posy = 0;

        for (FishFood objFishFood : fishFoodList) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
            while (!checkInitialPositionElement(posX, posy)) {
                posX = getRandomPositionX();
                posy = getRandomPositionY();
            }
            objFishFood.setPosX(posX);
            objFishFood.setPosY(posy);

        }
    }

    //////////////////
    /// DECORATION ///
    //////////////////

    public void decorationIntoList() {
        decorationList = new ArrayList<Decoration>();

        for (int i = 0; i < (int) (Math.random() * 3) + 1; i++) {
            decorationList.add(new Decoration(void_x, void_y));
            if (speedUpgradePurpleFish < maxSpeed) {
                speedUpgradePurpleFish = 10 + decorationList.size();
            }

        }
    }

    private void positionDecoration() {
        int posXDeco = 0;
        int posYDeco = 0;
        for (Decoration objDeco : decorationList) {

            posXDeco = (int) (Math.random() * 113);
            ;
            posYDeco = (int) (Math.random() * 52);
            ;

            objDeco.setPosX(posXDeco * 10);
            objDeco.setPosY(posYDeco * 10);

        }
    }

    //////////////////
    ///// OTHER //////
    //////////////////

    // Méthode utiliser pour faire spawn les fish et les props sans qu'ils soient
    // dans des décorations
    public static boolean checkInitialPositionElement(int pos_x, int pos_y) {

        for (Decoration each : decorationList) {

            if (pos_x >= each.getPosX() - fishWidth && pos_y >= each.getPosY() - fishHeight
                    && pos_x <= each.getPosX() + decorationWidth + fishWidth
                    && pos_y <= each.getPosY() + decorationHeight + fishHeight) {
                return false;
            }

        }
        return true;
    }

    // Deux méthodes random utiliser pour les positions des fish et des props hors
    // décorations
    static int getRandomPositionX() {

        int r = (int) (Math.random() * (sizeBoardHitBoxFishX / 10));
        return r * 10;
    }

    static int getRandomPositionY() {

        int r = (int) (Math.random() * (sizeBoardHitBoxFishY / 10));
        return r * 10;

    }

    //////////////////////////////////////////////////
    ///// Différentes méthodes pour les touches //////
    //////////////////////////////////////////////////

    public void resetAquarium() {

        imageBackground = new ImageIcon("seaScape.png");

        fishFoodList.removeAll(fishFoodList);
        decorationList.removeAll(decorationList);
        pelletList.removeAll(pelletList);
        fishList.removeAll(fishList);
        bugList.removeAll(bugList);

        allFixedGameElements.removeAll(allFixedGameElements);

        pelletModeActive = false;
        accouplementModeActive = false;
        insectModeActive = false;
        Fish.fishColorStopped = null;

        initGame();

    }

    // Méthode pour le boutton 9, ajouter des poissons aléatoirement
    public void addFish() {
        int fish_posX = 0;
        int fish_posy = 0;
        fish_posX = getRandomPositionX();
        fish_posy = getRandomPositionY();
        while (!checkInitialPositionElement(fish_posX, fish_posy)) {
            fish_posX = getRandomPositionX();
            fish_posy = getRandomPositionY();
        }
        int randomChoiceColorFish = (int) (Math.random() * 5);
        if (randomChoiceColorFish == 0) {
            fishList.add(new BlueFish(fish_posy, fish_posy, target_x, target_y, defaultSpeed));
        } else if (randomChoiceColorFish == 1) {
            fishList.add(new RedFish(fish_posy, fish_posy, target_x, target_y, defaultSpeed));
        } else if (randomChoiceColorFish == 2) {
            fishList.add(new PurpleFish(fish_posy, fish_posy, target_x, target_y, defaultSpeed));
        } else if (randomChoiceColorFish == 3) {
            fishList.add(new OrangeFish(fish_posy, fish_posy, target_x, target_y, defaultSpeed));
        } else if (randomChoiceColorFish == 4) {
            fishList.add(new YellowFish(fish_posy, fish_posy, target_x, target_y, defaultSpeed));
        }

    }

    // Méthode pour le boutton 5, ajouter une pastille aléatoirement
    public void addPellet() {

        int posX = getRandomPositionX();
        int posy = getRandomPositionY();
        while (!checkInitialPositionElement(posX, posy)) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
        }
        Pellet newPellet = new Pellet(posX, posy);
        pelletList.add(newPellet);
        allFixedGameElements.add(newPellet);

    }

    public void addFishFood() {
        int posX = getRandomPositionX();
        int posy = getRandomPositionY();
        while (!checkInitialPositionElement(posX, posy)) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
        }
        FishFood newFishFood = new FishFood(posX, posy);
        fishFoodList.add(newFishFood);
        allFixedGameElements.add(newFishFood);
    }

    // Méthode pour le boutton 4, ajouter un insecte aléatoirement
    public void addInsect() {
        int posX = getRandomPositionX();
        int posy = getRandomPositionY();
        while (!checkInitialPositionElement(posX, posy)) {
            posX = getRandomPositionX();
            posy = getRandomPositionY();
        }
        Bug1 newBug1 = new Bug1(posX, posy);
        Bug2 newBug2 = new Bug2(posX, posy);
        Bug3 newBug3 = new Bug3(posX, posy);

        int randomChoiceTypeInsect = (int) (Math.random() * 3);
        if (randomChoiceTypeInsect == 0) {
            bugList.add(newBug1);
            allFixedGameElements.add(newBug1);
        } else if (randomChoiceTypeInsect == 1) {
            bugList.add(newBug2);
            allFixedGameElements.add(newBug2);
        } else if (randomChoiceTypeInsect == 2) {
            bugList.add(newBug3);
            allFixedGameElements.add(newBug3);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < fishList.size(); i++) {
            if (!pelletModeActive && !insectModeActive && !accouplementModeActive) {

                fishList.get(i).update();
            } else if (pelletModeActive || insectModeActive || accouplementModeActive) {

                fishList.get(i).modeActive();

            }
        }

        for (Fish fish : fishList) {
            fish.comportementPelletAndInsects();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_0)) {
                resetAquarium();
            }
            if ((key == KeyEvent.VK_1)) {
                // setBackground(tempCold);
                imageBackground = new ImageIcon("seaScapeCold.png");
                for (Fish rFish : fishList) {

                    if (rFish.getType().equals("redFish")) {
                        rFish.setSpeed(speedDowngradeRedFish);
                    }
                }
            }
            if ((key == KeyEvent.VK_2)) {
                imageBackground = new ImageIcon("seaScape.png");
            }
            if ((key == KeyEvent.VK_3)) {
                imageBackground = new ImageIcon("seaScapeHot.png");
                for (Fish rFish : fishList) {
                    if (rFish.getType().equals("redFish")) {
                        rFish.setSpeed(speedUpgradeRedFish);
                    }
                }

            }
            if ((key == KeyEvent.VK_4)) {
                addInsect();
            }
            if ((key == KeyEvent.VK_5)) {
                addPellet();
            }
            if ((key == KeyEvent.VK_6)) {
                insectModeActive = true;
            }
            if ((key == KeyEvent.VK_7)) {
                pelletModeActive = true;
            }
            if ((key == KeyEvent.VK_8)) {
                accouplementModeActive = true;
            }
            if ((key == KeyEvent.VK_9)) {
                addFish();
            }

            if (key == KeyEvent.VK_R) {
                for (Fish fish : fishList) {
                    if (!(fish instanceof RedFish)) {
                        fish.setSpeed(0);
                        Fish.stopOtherColorsCounter = Integer.MAX_VALUE;
                    }
                }
            }
            if (key == KeyEvent.VK_B) {
                for (Fish fish : fishList) {
                    if (!(fish instanceof BlueFish)) {
                        fish.setSpeed(0);
                        Fish.stopOtherColorsCounter = Integer.MAX_VALUE;
                    }
                }
            }
            if (key == KeyEvent.VK_M) {
                for (Fish fish : fishList) {
                    if (!(fish instanceof PurpleFish)) {
                        fish.setSpeed(0);
                        Fish.stopOtherColorsCounter = Integer.MAX_VALUE;
                    }
                }
            }
            if (key == KeyEvent.VK_O) {
                for (Fish fish : fishList) {
                    if (!(fish instanceof OrangeFish)) {
                        fish.setSpeed(0);
                        Fish.stopOtherColorsCounter = Integer.MAX_VALUE;
                    }
                }
            }
            if (key == KeyEvent.VK_Y) {
                for (Fish fish : fishList) {
                    if (!(fish instanceof YellowFish)) {
                        fish.setSpeed(0);
                        Fish.stopOtherColorsCounter = Integer.MAX_VALUE;

                    }
                }
            }
            if (key == KeyEvent.VK_F) {
                addFishFood();
            }

        }
    }

}
