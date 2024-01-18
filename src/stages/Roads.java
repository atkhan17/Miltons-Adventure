package stages;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import things.Car;

/**
 * This class creates the 3rd level of the game: The Roads.
 *
 * @author Arsalan Khan
 */

public class Roads extends Level{

    static Image image = new Image("images/Road_floor.png"); //points to the file

    //dimensions of the Level
    public static final int MAX_WIDTH = 1050;
    public static final int MAX_HEIGHT = 800;

    //creates reference points outside the constructor
    private Stage primaryStage;
    Disp d;

    /**
     * Constructs the Roads Level
     * @param d the display (Disp) class of the program
     * @param primaryStage the primary stage of the program
     */
    public Roads(Disp d, Stage primaryStage) {
        super( 1, 8, d, primaryStage, MAX_WIDTH, MAX_HEIGHT, image);
        this.d = d;
        this.primaryStage = primaryStage;

        //first road
        new Car(this, 5, 1, 1);
        new Car(this, 6, 16, -1);
        new Car(this, 6, 7, -1);

        //second road
        new Car(this, 11, 4, 1);
        new Car(this, 11, 10, 1);
        new Car(this, 12, 7, -1);
        new Car(this, 12, 14, -1);

        //third road
        new Car(this, 16, 2, -1);
        new Car(this, 16, 7, -1);
        new Car(this, 16, 14, -1);
        new Car(this, 17, 2, 1);
        new Car(this, 18, 7, 1);
        new Car(this, 17, 14, 1);


    }

    /**
     * progresses the program to the next level
     */
    @Override
    public void change() {
        new Desert(d, primaryStage);
    }

    /**
     * restarts the level
     */
    @Override
    public void restart() {
        new Roads(d, primaryStage);
    }

    /**
     * checks if the program can advance to the next level
     *
     * Only once Milton reaches the other side can the program advance
     */
    @Override
    public void canAdvance() {
        if (getMilton().getX() == 19) {
            advance();
        }
    }
}
