package stages;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import things.Entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class runs the display. It also has some basic helper methods.
 *
 * @author Arsalan Khan
 */

public class Disp extends Application{

    private static Random random = new Random();
    private ArrayList<Entity> entities = new ArrayList<Entity>(); //a flexible array that doesn't have to have a fixed length.

    private Level level; //the level being run
    private long lastRun = 0;
    private int counter = 0; //counts number of 4ths of a second that have passed

    /**
     * Launches the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        //rewrites 0 as the first line of the food file to wipe data from previous runs
        try {
            FileWriter n = new FileWriter("food");
            BufferedWriter b = new BufferedWriter(n);

            b.write(Integer.toString(0));

            b.close();
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }

        launch(args);
    }

    /**
     * Sets up the visuals.
     *
     * @param primaryStage the Program's Stage
     */
    public void start(Stage primaryStage) {

        //set the scene
        level = (Grass) new Grass(this, primaryStage);

        //timer for running methods per x seconds
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //now is in nanoseconds.
                long quartS = 250000000; //stores the value of quarter a second

                if (now - lastRun > quartS) {
                    lastRun = now;

                    //runs once per quarter second
                    level.act();
                    counter +=1;

                }
            }
        };

        at.start();

        primaryStage.setTitle("He Who Lived");
        primaryStage.show();
    }

    /**
     * returns how many cycles have passed
     * @return value of variable counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * sets the level of the program
     * @param level the level that the program will be set to
     */
    public void setLevel(Level level) {
        this.level = level;
    }
}
