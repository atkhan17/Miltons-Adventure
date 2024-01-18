package things;

import javafx.scene.image.Image;
import stages.Level;

public class NotMilton extends Cat{

    static Image image = new Image("images/notMilton.png");
    int pTimer = 0;

    /**
     * Create a new NotMilton. This will also add this NotMilton to the world and set the image.
     *
     * @param level    the world this NotMilton lives in
     * @param x       the x-coordinate of this NotMilton
     * @param y       the y-coordinate of this NotMilton
     */
    public NotMilton(Level level, int x, int y) {
        super(level, x, y, image, 250, 2, 5);
    }

    /**
     * decides the course of action this NotMilton will take
     */
    @Override
    public void act() {
        die();
        if (getpTimer() == 8  && findMilton() <= 7) { //checks if this Stray is ready to pounce and Milton is in range
            pounce(getMilton());
        } else if (getpTimer() == 8) { //if this Stray is ready to pounce but target is not in range, fails to pounce
            pounceFail();
        } else if (findMilton() <= 0 && getpTimer() > 1) { //if milton is within 1 cell away then swipes at him
            swipe(getMilton());
        } else if (getMilton().getpTimer() == 5) { //if Milton is readying his pounce then take 3 steps away from milton
            chase(-3);
        } else if (findMilton() <= 5) { //if Milton is within 5 cells away then chases him
            chase(1);
            addPTimer();
        } else if (findMilton() <= 7) { //if Milton is within 7 cells away then readies pounce
            addPTimer();
        } else if (findMilton() <= 24){ //otherwise if Milton is too far away, summons stray on Milton
            new Stray(getLevel(), getMilton().getX(), getMilton().getY());
        }
    }

    /**
     * Returns a String representation of this NotMilton.
     *
     * @return a String representation of this NotMilton.
     */
    @Override
    public String toString() {
        return "NotMilton{" + super.toString() + "}";
    }
}
