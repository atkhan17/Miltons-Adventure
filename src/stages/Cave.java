package stages;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import things.Entity;
import things.NotMilton;

/**
 * This class creates the 5th level of the game: The Cave.
 *
 * @author Arsalan Khan
 */

public class Cave extends Level {
    static Image image = new Image("images/Cave.jpg"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 1050;
    public static final int MAX_HEIGHT = 667;

    //creates reference points outside the constructor
    private Stage primaryStage;
    Disp d;

    /**
     * Constructs the Cave Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Cave(Disp d, Stage primaryStage) {
        super( 9, 1, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        new NotMilton(this, 9, 12);
    }

    /**
     * progresses the program to the next level
     */
    @Override
    public void change() {
        new Box(d, primaryStage);
    }

    /**
     * restarts the level
     */
    @Override
    public void restart() {
        new Cave(d, primaryStage);
    }

    /**
     * checks if the program can advance to the next level
     *
     * if there is NotMilton still in the level then the program cannot advance
     */
    @Override
    public void canAdvance() {
        for (Entity entity : getEntities()) {
            if (entity instanceof NotMilton) {
                return;
            }
        }
        advance();
    }
}
