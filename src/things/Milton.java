package things;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import stages.Level;

import java.io.*;

public class Milton extends Cat {

    private static final Image image = new Image("images/Milton.png"); //reference point for image
    private Boolean pouncing = false; //stores whether or not Milton is pouncing
    private Animal pTarget; //stores the animal that Milton is attacking
    private Milton m = this;
    private int swipeT = 0; //stores the swipe attack cooldown counter
    private int archives = 0; //stores how many archives Milton has
    private int food; //stores how much food Milton has
    private boolean died = false; //stores whether or not Milton has already died on the same level

    /**
     * Create a new Milton. This will also add this Milton to the world and set the image.
     *
     * @param level    the world this Milton lives in
     * @param x       the x-coordinate of this Milton
     * @param y       the y-coordinate of this Milton
     */
    public Milton(Level level, int x, int y) {
        super(level, x, y, image, 200, 2, 0);
        try {
            FileReader n = new FileReader("food");
            BufferedReader b = new BufferedReader(n);

            food = Integer.parseInt(b.readLine());

            b.close();
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    /**
     * manages keyboard input from player
     */
    public EventHandler<KeyEvent> movementHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (pouncing == false) {
                switch (event.getCode()) {
                    case W:
                        mY(-1); //moves Milton one cell down
                        break;
                    case A:
                        mX(-1); //moves Milton one cell left
                        break;
                    case S:
                        mY(1); //moves Milton one cell down
                        break;
                    case D:
                        mX(1); //moves Milton one cell right
                        break;
                    case P:
                        pTarget = getLevel().getTargetBy(getX(), getY(), 9, m); //finds target to pounce at within radius of 9
                        if (pTarget != null) { //if target is found then activates pounce
                            pouncing = true;
                        } else {
                            System.out.println("no Target in range");
                        }
                        break;
                    case L: //swipe attack
                        activateSwipe();
                        break;
                    case H: //heal
                        heal();
                        break;
                }
            }
        }
    };

    /**
     * decides the course of action this Stray will take
     */
    @Override
    public void act() {
        death(); //checks if Milton has died
        swipeT++;

        if (getpTimer() == 6 && findTarget(pTarget) <= 9) { //if Milton is ready to pounce and target is in range, then pounces
            pouncing = false;
            pounce(pTarget);
        } else if (getpTimer() == 6) { //if Milton is ready to pounce but target isn't in range, fails to pounce
            pouncing = false;
            pounceFail();
        }
        //increases pouncing timer while Milton is pouncing
        if (pouncing) {
            addPTimer();
        }
    }

    /**
     * activates Swipe attack
     */
    public void activateSwipe() {

        if (swipeT >= 5) { //checks if Milton's swipe cool-down is over
            swipe(getLevel().getTargetBy(getX(), getY(), 1, m));
            swipeT = 0;
        } else {
            System.out.println("Swipe is not ready");
        }
    }

    /**
     * checks if Milton has died
     */
    public void death() {
        if (getHp() <= 0) {
            die();
            died = true; //records that Milton has died once at this level
            getLevel().death();
        }
    }

    /**
     * Milton pounces at target
     *
     * Milton moves to target and decreases target's hp accordingly
     *
     * @param target that this Milton pounces at
     */
    @Override
    public void pounce(Animal target) {
        setpTimer(0);
        mX(target.getX() - getX());
        mY(target.getY() - getY());
        target.setHp(-20 * damageM);
        System.out.println(this + " pounced at " + target);
    }

    /**
     * Milton heals himself at the expense of one of his food supply
     */
    public void heal() {
        if (food != 0 && getHp()+20 < HPM) {
            food -=1;
            setHp(+20);
        } else {
            System.out.println("No food or ur max hp");
        }
    }

    /**
     * returns number of archives Milton has collected
     *
     * @return number of archives Milton has collected
     */
    public int getArchives() {
        return archives;
    }

    /**
     * adds to the archives Milton has collected
     *
     * @param archives value by which Milton's archives are increased by
     */
    public void addArchives(int archives) {
        this.archives += archives;
    }

    /**
     * returns the amount of food Milton has
     *
     * @return the amount of food Milton has
     */
    public int getFood() {return food;}

    /**
     * radds to the amount of food Milton has
     *
     * @param food the amount by which Milton's food supply increases by
     */
    public void addFood(int food) {
        this.food += food;
    }

    /**
     * returns if Milton has died on this stage already
     *
     * @return if Milton has died on this stage already
     */
    public boolean getDied() {return died;}

    /**
     * Returns a String representation of this Milton.
     *
     * @return a String representation of this Milton.
     */
    @Override
    public String toString() {
        return "Milton{" + super.toString() + "}";
    }
}
