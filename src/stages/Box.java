package stages;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class creates the 6th 'level' of the game: The Desert.
 *
 * This isn't actually a level where the player can move around but is where the lore comes into play. (or should anyway)
 * @author Arsalan Khan
 */

public class Box extends Level{

    static Image image = new Image("images/Cave.jpg"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 1500;
    public static final int MAX_HEIGHT = 1000;

    //creates reference points outside the constructor
    private Stage primaryStage;
    Disp d;

    /**
     * Constructs the Box Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Box(Disp d, Stage primaryStage) {
        super( 5, 5, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        //adds a black rectangle that covers the scene to make the scene appear black
        Rectangle r = new Rectangle(0, 0, MAX_WIDTH, MAX_HEIGHT);
        r.setFill(Color.BLACK);
        getRoot().getChildren().add(r);
    }

    /**
     * checks if the program can advance to the next level
     *
     * In truth, this level is different from the rest where it doesn't "advance" the program to any next level but
     * just ends the game with a good/bad message indicating whether you won or lost.
     */
    @Override
    public void canAdvance() {
        if (getMilton().getArchives() < 10) {
            System.out.println("You failed us.");
            getPrimaryStage().close();
        } else if (getMilton().getArchives() == 10) {
            getMilton().addArchives(1);
        } else {
            System.out.println("Congratulations, you have collected all the archives. What does it all mean though?");
        }
    }
}
