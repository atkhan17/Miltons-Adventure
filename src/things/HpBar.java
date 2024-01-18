package things;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GREEN;

/**
 * This class represents an hp bar.
 *
 * @author Arsalan Khan
 */

public class HpBar {

    private Rectangle front; //the rectangle that will make up the front green part of the hp bar
    private Rectangle back; //the rectangle that will make up the back red part of the hp bar
    private Animal a; //the animal this hp bar belongs to
    Group root; //the root this hp bar will be attached to

    /**
     * creates a new hp bar and will add it to the screen
     *
     * @param x the x coordinate of the animal this hp bar will belong to
     * @param y the y coordinate of the animal this hp bar will belong to
     * @param root the scene this hp bar will be on
     * @param a the animal this hp bar belongs to
     */
    public HpBar(int x, int y, Group root, Animal a) {
        this.a = a;
        this.root = root;
        //hp bar will be above animal's head
        x = a.getX()*50 - a.HPM/5;
        y = a.getY()*50 - 10;

        //creates the front and back rectangles
        front = new Rectangle(x, y, 50, 5);
        front.setFill(GREEN);
        front.setStroke(BLACK);

        back = new Rectangle(x, y, a.HPM, 5);
        back.setFill(Color.DARKRED);
        back.setStroke(BLACK);

        root.getChildren().add(back);
        root.getChildren().add(front);

        a.getLevel().addBar(this);
    }

    /**
     * updates the hp bar to accurately reflect character's hp and position
     */
    public void act() {
        front.setX(a.getX()*50 - a.HPM/5);
        front.setY(a.getY()*50 - 10);

        front.setWidth(a.getHp());

        back.setX(a.getX()*50 - a.HPM/5);
        back.setY(a.getY()*50 - 10);

    }

    /**
     * removes the hp bar rectangles from the scene
     */
    public void remove() {
        root.getChildren().remove(front);
        root.getChildren().remove(back);
    }
}