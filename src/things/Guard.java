package things;

import javafx.scene.image.Image;
import stages.Level;

public class Guard extends Animal {

    static Image image = new Image("images/Dog.png");

    /**
     * Create a new Guard. This will also add this Guard to the level and set the image.
     *
     * @param level    the level this Guard lives in
     * @param x       the x-coordinate of this Guard
     * @param y       the y-coordinate of this Guard
     */
    public Guard(Level level, int x, int y) {
        super(level, x, y, 8, image, 200, 8, 3);
    }

    /**
     * checks if this Guard should be dead
     * if Milton is within distance 1, swipes at Milton
     * otherwise chases Milton
     */
    @Override
    public void act() {
        die();
        if (findMilton() <= 1) {
            swipe(getMilton());
        } else {
            chase(1);
        }
    }

    /**
     * Returns a String representation of this Guard.
     *
     * @return a String representation of this Guard.
     */
    @Override
    public String toString() {
        return "Guard{" + super.toString() + "}";
    }

}
