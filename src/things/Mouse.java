package things;

import javafx.scene.image.Image;
import stages.Level;

public class Mouse extends Animal {

    static Image image = new Image("images/Mouse.png");

    /**
     * Create a new Mouse. This will also add this Mouse to the level and set the image.
     *
     * @param level    the level this Mouse lives in
     * @param x       the x-coordinate of this Mouse
     * @param y       the y-coordinate of this Mouse
     */
    public Mouse(Level level, int x, int y) {
        super(level, x, y, 2, image, 40, 1, 1);
    }

    /**
     * checks if this Mouse should be dead
     * if Milton is within distance 4, runs away from Milton
     * otherwise wanders around
     */
    @Override
    public void act() {
        die();
        if (findMilton() <=4) {
            chase(-1);
        } else {
            wander();
        }
    }

    /**
     * Returns a String representation of this Mouse.
     *
     * @return a String representation of this Mouse.
     */
    @Override
    public String toString() {
        return "Mouse{" + super.toString() + "}";
    }
}
