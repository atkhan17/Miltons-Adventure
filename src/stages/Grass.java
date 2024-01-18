package stages;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import things.Entity;
import things.Mouse;
import things.Stray;

/**
 * This class creates the 1st level of the game: The Grassland.
 *
 * @author Arsalan Khan
 */

public class Grass extends Level{
    static Image image = new Image("images/grass.png"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 906;
    public static final int MAX_HEIGHT = 840;

    //creates reference points outside the constructor
    private Stage primaryStage;
    private Disp d;

    /**
     * Constructs the Grassland Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Grass(Disp d, Stage primaryStage) {
        super(9, 16, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        new Stray(this, 3, 3);
        new Mouse(this, 10, 3);
    }

    /**
     * progresses the program to the next level
     */
    @Override
    public void change() {
        new Bridge(d, primaryStage);
    }

    /**
     * restarts the level
     */
    @Override
    public void restart() {
        new Grass(d, primaryStage);
    }

    /**
     * checks if the program can advance to the next level
     *
     * if there is a Stray still in the level then the program cannot advance
     */
    @Override
    public void canAdvance() {
        for (Entity entity : getEntities()) {
            if (entity instanceof Stray) {
                return;
            }
        }
        advance();
    }


}
