package things;

import javafx.scene.image.Image;
import stages.Level;

public class Rabbit extends Animal{

    static Image image = new Image("images/rabbit.png");

    /**
     * Create a new Rabbit. This will also add this Rabbit to the level and set the image.
     *
     * @param level    the level this Rabbit lives in
     * @param x       the x-coordinate of this Rabbit
     * @param y       the y-coordinate of this Rabbit
     */
    public Rabbit(Level level, int x, int y) {
        super(level, x, y, 2, image, 40, 1, 1);
    }

    /**
     * checks if this Rabbit should be dead
     * if Milton is within distance 1, swipes at Milton
     * if Milton is within distance 5, runs away from Milton
     * otherwise wanders around
     */
    @Override
    public void act() {
        die();
        if (findMilton() <= 1) {
            swipe(getMilton());
        } else if (findMilton() <=5) {
            chase(-1);
        } else {
            wander();
        }
    }

    /**
     * Returns a String representation of this Rabbit.
     *
     * @return a String representation of this Rabbit.
     */
    @Override
    public String toString() {
        return "Rabbit{" + super.toString() + "}";
    }
}
