package things;

import javafx.scene.image.Image;
import stages.Level;

public class Cat extends Animal {

    private int pTimer = 0; //keeps count for seconds that pass while animal is getting ready to pounce
    int damageM; //creates reference point outside of constructor

    /**
     * Create a new Cat. This will also add this Cat to the level and set the image.
     *
     * @param level    the level this Entity lives in
     * @param x       the x-coordinate of this Cat
     * @param y       the y-coordinate of this Cat
     * @param image   the image that represents this Cat
     * @param maxHp the max hp of this Cat
     * @param drop the food drop Milton gets from killing this Cat
     */
    public Cat(Level level, int x, int y, Image image, int maxHp, int damageM, int drop) {
        super(level, x, y, 3, image, maxHp, damageM, drop);
        this.damageM = damageM;
    }

    /**
     * pounces at target
     *
     * @param target that this Cat pounces at
     */
    public void pounce(Animal target) {
        pTimer = 0;
        moveTo(target.getX(), target.getY()); //handles animation
        target.setHp(-20 * damageM); //decreases target's hp
        System.out.println(this + " pounced at " + target);
    }

    /**
     * fails to pounce at target
     *
     * resets pTimer
     */
    public void pounceFail() {
        setpTimer(0);
        System.out.println("target went out of range");
    }

    /**
     * returns pounce timer of this Cat
     *
     * @return pounce timer of this cat
     */
    public int getpTimer() {
        return pTimer;
    }

    /**
     * increases pounce timer of this Cat
     */
    public void addPTimer() {
        this.pTimer += 1;
    }

    /**
     * sets pounce timer of this Cat as int n
     *
     * @param n value that pounce timer will be set to
     */
    public void setpTimer(int n) {this.pTimer = n;}

    /**
     * Returns a String representation of this Cat.
     *
     * @return a String representation of this Cat.
     */
    @Override
    public String toString() {
        return "Cat{" + super.toString() + "}";
    }
}
