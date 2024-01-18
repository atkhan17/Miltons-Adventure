package things;

import javafx.scene.image.Image;
import stages.Level;

public class Stray extends Cat{

    static Image image = new Image("images/Stray.png");

    /**
     * Create a new Stray. This will also add this Stray to the level and set the image.
     *
     * @param level    the level this Stray lives in
     * @param x       the x-coordinate of this Stray
     * @param y       the y-coordinate of this Stray
     */
    public Stray(Level level, int x, int y) {
        super(level, x, y, image, 80, 2, 2);
    }

    /**
     * decides the course of action this Stray will take
     */
    @Override
    public void act() {
        die(); //checks if this Stray has died
        if (getpTimer() == 8 && findMilton() <= 7) { //checks if this Stray is ready to pounce and Milton is in range
            pounce(getMilton());
        } else if (getpTimer() == 8) { //if this Stray is ready to pounce but target is not in range, fails to pounce
            pounceFail();
        } else if (findMilton() <= 1 && getpTimer() > 0) { //if milton is within 1 cell away then swipes at him
            swipe(getMilton());
        } else if (findMilton() <= 4) { //if Milton is within 4 cells away then chases him
            chase(1);
        } else if (getMilton().getpTimer() == 5) { //if Milton is readying his pounce then take 2 steps away from milton
            chase(-2);
        }  else if (findMilton() <= 7) { //if Milton is within 7 cells away then readies pounce
            addPTimer();
        } else {  //otherwise wanders randomly
            wander();
        }
    }

    /**
     * Returns a String representation of this Stray.
     *
     * @return a String representation of this Stray.
     */
    @Override
    public String toString() {
        return "Stray{" + super.toString() + "}";
    }

}
