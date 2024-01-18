package things;

import javafx.scene.image.Image;
import stages.Level;

public class Car extends Entity{

    static Image image = new Image("images/Car.png"); //reference point for image

    int down; //integer variable indicates whether car is going down or down

    /**
     * Create a new Entity. This will also add this Entity to the level and set the image.
     *
     * @param level   the level this Entity lives in
     * @param x      the x-coordinate of this Entity
     * @param y      the y-coordinate of this Entity
     * @param down     indicates whether car is going down or down
     */
    public Car(Level level, int x, int y, int down) {
        super(level, x, y, 1, image);
        this.down = down;

        //makes car face up if it will be going up
        if (down == -1) {
            rotateImageView(180);
        }
    }

    /**
     * drives forward
     * checks if this Car has crashed into Milton
     */
    @Override
    public void act() {
        drive(down);
        crash();
    }

    /**
     * changes y coordinate by param down
     * @param down amount by which y coordinate changes
     */
    public void drive(int down) {
        if(getY() == 15 && down == 1) { //if car is going down and reaches bottom of screen moves back to top
            new Car(getLevel(), getX(), 0, down);
            getLevel().remove(this);
            return;
        } else if (getY() == 0 && down == -1) { //if car is going up and reaches top of screen moves back to bottom
            new Car(getLevel(), getX(), 15, down);
            getLevel().remove(this);
            return;
        }
        moveFastTo(getX(), getY() + down); //moves car
    }

    /**
     * checks if Milton has crashed into this Car and kills him if he has
     */
    public void crash() {
        if (findMilton() == 0) {
            getLevel().death();
        }
    }

    /**
     * Returns a String representation of this Car.
     *
     * @return a String representation of this Car.
     */
    @Override
    public String toString() {
        return "Car{" +  super.toString() + "}";
    }
}
