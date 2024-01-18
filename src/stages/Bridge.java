package stages;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import things.Entity;
import things.Guard;

/**
 * This class creates the 2nd level of the game: The Bridge.
 *
 * @author Arsalan Khan
 */

public class Bridge extends Level{
    static Image image = new Image("images/bridge_floor.jpg"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 1000;
    public static final int MAX_HEIGHT = 1000;

    //creates reference points outside the constructor
    private Stage primaryStage;
    private Disp d;

    /**
     * Constructs the Bridge Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Bridge(Disp d, Stage primaryStage) {
        super( 1, 7, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        new Guard(this, 15, 5);
    }

    /**
     * progresses the program to the next level
     */
    @Override
    public void change() {
        new Roads(d, primaryStage);
    }

    /**
     * restarts the level
     */
    @Override
    public void restart() {
        new Bridge(d, primaryStage);
    }

    /**
     * checks if the program can advance to the next level
     *
     * if there is a Guard still in the level then the program cannot advance
     */
    @Override
    public void canAdvance() {
        for (Entity entity : getEntities()) {
            if (entity instanceof Guard) {
                return;
            }
        }
        if (getMilton().getX() == 18) {
            advance();
        }
    }
}
