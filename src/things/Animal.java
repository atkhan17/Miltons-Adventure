package things;

import javafx.scene.image.Image;
import stages.Level;

public class Animal extends Entity{

    //creates reference points outside of constructor
    public final int DROP;
    public final int HPM;
    private int hp;
    private int damageM;

    HpBar hpBar;

    /**
     * Create a new Animal. This will also add this Animal to the level and set the image.
     *  @param level   the level this Animal lives in
     * @param x      the x-coordinate of this Animal
     * @param y      the y-coordinate of this Animal
     * @param speedm determines speed of this Animal
     * @param image  the image that represents this Animal
     * @param maxhp the max hp of this Animal
     * @param drop the food drop Milton gets from killing this Animal
     */
    public Animal(Level level, int x, int y, int speedm, Image image, int maxhp, int damageM, int drop) {
        super(level, x, y, speedm, image);
        DROP = drop;
        HPM = maxhp;
        hp = HPM;
        this.damageM = damageM;
        hpBar = new HpBar(getX(), getY(), getLevel().getRoot(), this);
    }

    /**
     * Animal moves in random direction
     */
    public void wander() {
        int x = getX();
        int y = getY();

        switch (getLevel().getRandomInt(0, 3)){
            case 0: x++; break;
            case 1: y++;break;
            case 2: x--; break;
            case 3: y--; break;
        }
        moveTo(x, y);
    }

    /**
     * Animal swipes at target
     * @param target that Animal swipes at
     */
    public void swipe(Animal target) {
        if (target == null) { //if target is null then stops method
            System.out.println(this + " could not find any target");
            return;
        }
        target.setHp(-10*damageM); //reduces target's hp
        System.out.println(this + " swiped at " + target + "!");
    }

    /**
     * Animal chases Milton by factor of param chase
     *
     * @param chase is factor by which Animal chases Milton by
     */
    public void chase(int chase) {

        int posX = getX();
        int posY = getY();

        Milton milton = getMilton();

        int x = milton.getX();
        int y = milton.getY();

        if (posX > x) {  //if , moves to the left "chase" amount of cells
            moveTo(posX-chase, posY);
        } else if (posX < x) { //if , moves to the right "chase" amount of cells
            moveTo(posX+chase, posY);
        } else if (posY > y) { //if  , moves down "chase" amount of cells
            moveTo(posX, posY-chase);
        } else if (posY < y) { //if  moves up "chase" amount of cells
            moveTo(posX, posY+chase);
        }
    }

    /**
     * if hp is below 0 then kills target
     */
    public void die() {
        if (hp <= 0) {
            getLevel().removeBar(hpBar);
            getMilton().addFood(DROP); //gives Milton "Drop" amount of food
            getLevel().remove(this);
            System.out.println(this + "died!");
        }
    }

    /**
     * returns this Animal's hp
     *
     * @return this animal's hp
     */
    public int getHp() {return hp;}

    /**
     * changes this Animal's hp by factor n
     *
     * @param n amount that this Animal's hp changes by
     */
    public void setHp(int n) {hp += n;}

    /**
     * Returns a String representation of this Animal.
     *
     * @return a String representation of this Animal.
     */
    @Override
    public String toString() {
        return "Animal{" + super.toString() + "}";
    }

    public HpBar getHpBar() {
        return hpBar;
    }
}
