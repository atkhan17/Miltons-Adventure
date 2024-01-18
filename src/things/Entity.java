package things;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import stages.Level;

import static stages.Level.CELL_SIZE;

/**
 * This class represents an Entity.
 * All entities extend from this class as they share much of the same code
 * @author Arsalan Khan
 */

public class Entity {

    //creates reference points outside of constructor
    private final Level level;
    private int x;
    private int y;
    public final int SPEEDM;
    private ImageView imageView;

    //this handles smooth movement from one tile to the next.
    private TranslateTransition mover;

    private TranslateTransition moverFast;

    /**
     * Create a new Entity. This will also add this Entity to the world and set the image.
     * @param level the level this Entity lives in
     * @param x the x-coordinate of this Entity
     * @param y the y-coordinate of this Entity
     * @param speedm determines speed of this Entity
     * @param image the image that represents this Entity
     */
    public Entity(Level level, int x, int y, int speedm, Image image){
        this.level = level;
        this.x = x;
        this.y = y;
        SPEEDM = speedm;

        imageView = new ImageView(image);
        imageView.setX(x * CELL_SIZE);
        imageView.setY(y * CELL_SIZE);

        mover = new TranslateTransition(Duration.millis(500), imageView);
        mover.setCycleCount(1);

        moverFast = new TranslateTransition(Duration.millis(100), imageView);
        moverFast.setCycleCount(1);


        level.add(this);
    }

    /**
     * This method is called by the Level once every x number of quarter seconds where x is SPEEDM
     *
     * This method will be overridden by any child classes.
     */
    public void act() {}

    /**
     * Animates the Entity moving to the given location in half a second.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void moveTo(int x, int y){

        //makes sure x and y are in bounds
        if (x >= level.getCellC()) {
            x -= 2;
        }
        else if (y >= level.getCellR()) {
            y -= 2;
        }
        else if (x < 0) {x += 1;
            x += 2;
        }
        else if (y < 0) {
            y += 2;
        }

        mover.setByX((x - this.x) * CELL_SIZE);
        mover.setByY((y - this.y) * CELL_SIZE);

        //animate the movement
        mover.play();

        //update coordinates
        this.x = x;
        this.y = y;
    }

    /**
     * Animates the Entity moving to the given location in a tenth of a second.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void moveFastTo(int x, int y){

        //makes sure x and y are in bounds
        if (x >= level.getCellC()) {
            x -= 2;
        }
        else if (y >= level.getCellR()) {
            y -= 2;
        }
        else if (x < 0) {x += 1;
            x += 2;
        }
        else if (y < 0) {
            y += 2;
        }

        moverFast.setByX((x - this.x) * CELL_SIZE);
        moverFast.setByY((y - this.y) * CELL_SIZE);

        //animate the movement
        moverFast.play();

        //update coordinates
        this.x = x;
        this.y = y;
    }

    /**
     * returns the distance of Milton from this entity
     *
     * @return the distance of Milton from this entity
     */
    public int findMilton() {
        int x = getX();
        int y = getY();
        Milton milton = getMilton();

        //the total distance from the given coordinate
        return (int) Math.sqrt(Math.pow(milton.getX() - x, 2)
                + Math.pow(milton.getY() - y, 2));
    }

    public int findTarget(Animal target) {
        int x = getX();
        int y = getY();

        //the total distance from the given coordinate
        return (int) Math.sqrt(Math.pow(target.getX() - x, 2)
                + Math.pow(target.getY() - y, 2));
    }

    /**
     * returns the Milton of the level (the player)
     *
     * @return the Milton of the level (the player)
     */
    public Milton getMilton() {
        return level.getMilton();
    }

    /**
     * Returns the current x-coordinate of the Entity.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the current y-coordinate of the Entity.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the World that contains this Entity.
     *
     * @return this Entity's World
     */
    public Level getLevel(){
        return level;
    }

    /**
     * Returns the visual component of this Entity.
     *
     * @return the visual component of this Entity
     */
    public Node getNode() {return imageView;}

    /**
     * shifts x coordinate by "sign" amount
     *
     * This method is only used by Milton as he moves too fast for the mover animation to register
     *
     * @param sign amount by which x coordinate is shifted by
     */
    public void mX(int sign) {
        int X = x + sign;
        if (X < (level.getCellC() - 1) && X >= 0) {
            x = X;
            imageView.setX(x * CELL_SIZE);
        }
    }

    /**
     * shifts y coordinate by "sign" amount
     *
     * This method is only used by Milton as he moves too fast for the mover animation to register
     *
     * @param sign amount by which y coordinate is shifted by
     */
    public void mY(int sign) {
        int Y = y + sign;
        if (Y < level.getCellR() && Y >= 0) {
            y = Y;
            imageView.setY(y * CELL_SIZE);
        }
    }

    /**
     * rotates imageView around given angle
     *
     * @param theta angle by which imageview will be rotated by
     */
    public void rotateImageView(int theta) {
        imageView.setRotate(theta);
    }

    /**
     * Returns a String representation of this Entity.
     *
     * @return a String representation of this Entity.
     */
    @Override
    public String toString() {
        return "Entity{x=" + x + ", y=" + y + '}';
    }

}
