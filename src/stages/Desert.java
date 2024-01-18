package stages;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import things.Entity;
import things.Rabbit;
import things.Stray;

/**
 * This class creates the 4th level of the game: The Desert.
 *
 * @author Arsalan Khan
 */

public class Desert extends Level {
    static Image image = new Image("images/desert_floor.png"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 1050;
    public static final int MAX_HEIGHT = 617;

    //creates reference points outside the constructor
    private Stage primaryStage;
    Disp d;

    /**
     * Constructs the Desert Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Desert(Disp d, Stage primaryStage) {
        super( 20, 11, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        new Rabbit(this, 21, 1);
        new Rabbit(this, 19, 1);
        new Rabbit(this, 20, 2);
        new Stray(this, 3, 4);
        new Stray(this, 4, 5);
    }

    /**
     * progresses the program to the next level
     */
    @Override
    public void change() {
        new Cave(d, primaryStage);
    }

    /**
     * restarts the level
     */
    @Override
    public void restart() {
        new Desert(d, primaryStage);
    }

    /**
     * checks if the program can advance to the next level
     *
     * if there is a rabbit still in the level then the program cannot advance
     */
    @Override
    public void canAdvance() {
        for (Entity entity : getEntities()) {
            if (entity instanceof Rabbit) {
                return;
            }
        }

        if (getMilton().getX() == 0 && getMilton().getY() == 0) {
            advance();
        }
    }
}
