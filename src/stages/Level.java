package stages;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import things.Animal;
import things.Entity;
import things.HpBar;
import things.Milton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a Level.
 * All levels extend from this class as they share much of the same code
 * @author Arsalan Khan
 */

public class Level {

    private Group root = new Group();
    private static Random random = new Random();
    //a flexible array that doesn't have any fixed length
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<HpBar> hpBars = new ArrayList<HpBar>();

    private static ImageView bg; //this will display the background
    private Stage primaryStage; //a reference point for the stage outside of constructor
    private Disp d; //a reference point for the Display (Disp for short) class outside of constructor
    private Scene level; //a reference point for the scene outside of constructor
    private Milton milton; //a reference point for Milton(player) outside of constructor

    //for the dimensions of the level
    public static final int CELL_SIZE = 50;
    private int cellR; //The amount of rows of cells in the scene
    private int cellC; //The amount of columns of cells in the scene

    /**
     * Constructs the Level
     * @param x starting x coordinate of Milton (player)
     * @param y starting y coordinate  of Milton (player)
     * @param d the display of the program
     * @param primaryStage the primary stage of the program
     * @param MAX_WIDTH the width of the scene
     * @param MAX_HEIGHT the height of the scene
     * @param image the background image of the scene
     */
    public Level(int x, int y, Disp d, Stage primaryStage, int MAX_WIDTH, int MAX_HEIGHT, Image image) {
        this.d = d;
        this.primaryStage = primaryStage;

        d.setLevel(this); //sets the level of the display of the program as itself
        setCells(MAX_HEIGHT, MAX_WIDTH); //sets up the dimensions of the world as x # of rows and columns

        //sets the background
        bg = new ImageView(image);
        root.getChildren().add(bg);

        //creates the scene
       level = new Scene(root, MAX_WIDTH,MAX_HEIGHT);

       //creates an instance of milton(the player) on the level at given x and y coordinates
       milton = new Milton(this, x, y);

       //sends input information to Milton's class
       level.setOnKeyPressed(milton.movementHandler);

       //sets the scene of the display as this level's scene
       primaryStage.setScene(level);

    }

    /**
     * Runs when Milton (the player) dies
     *
     */
    public void death() {
        //rewrites the amount of food Milton has in the file for when the level changes
        updateFood();

        System.out.println("Imagine dying lol"); //motivation message for players

        //increases Milton's archives by 1 only if he hasn't already died on this level
        // (the player is supposed to unlock one archive for dying once on each stage, not for just dying in general)
        if (!getMilton().getDied()) {
            getMilton().addArchives(1);
        }
        restart(); //restarts the level
    }

    /**
     * runs when the player advances to the next stage
     */
    public void advance() {
        updateFood();
        getMilton().addArchives(1);
        change(); //changes the level to the next
    }

    /**
     * stores the amount of food Milton has in the "food" file for when the level changes
     */
    public void updateFood() {
        String add = Integer.toString(getMilton().getFood());
        try {
            FileWriter n = new FileWriter("food");
            BufferedWriter b = new BufferedWriter(n);

            b.write(add);

            b.close();
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    /**
     * This method is called every quarter of a second alongside the act() method to check if the program should advance to the next stage
     *
     * this method will be overridden by child classes.
     */
    public void canAdvance() {}

    /**
     *This method is called whenever the game advances to the next level
     *
     * This method will be overridden by child classes.
     */
    public void change() {}

    /**
     *This method is called whenever the game restarts the level
     *
     * This method will be overridden by child classes.
     */
    public void restart() {}

    /**
     *Checks if the program should advance once every quarter second
     *
     *Calls the act method for each hp bar in the world
     *
     *Calls the act method for each entity in the world depending on their speed variable or "SPEEDM"
     */
    public void act() {
        canAdvance();

        //create a copy of current actors and their hp bars so that new actors created this turn don't get to act.
        //also, the state of the list cannot change while this loop is executing.
        ArrayList<Entity> currentEntities = (ArrayList<Entity>) entities.clone();
        ArrayList<HpBar> currentHpBars = (ArrayList<HpBar>) hpBars.clone();

        for (HpBar b : currentHpBars) {
                b.act();
        }

        for (Entity e : currentEntities) {
            if (d.getCounter()%(e.SPEEDM) == 0) {
                //runs only if the amount of quarter seconds that have passed is divisible by the entity's variable SPEEDM
                //so if SPEEDM is 2 then this will run for them once every 2 quarter seconds.
                e.act();
            }
        }
    }

    /**
     *Adds an entity to this Level.
     *
     * @param entity the entity to add
     */
    public void add(Entity entity) {
        entities.add(entity);
        root.getChildren().add(entity.getNode());
    }

    /**
     * Removes an entity from this level.
     *
     * @param entity the entity to remove
     */
    public void remove(Entity entity) {
        entities.remove(entity);
        root.getChildren().remove(entity.getNode());
    }

    /**
     *Adds an hpBar to this Level.
     *
     * @param hpBar the hp bar to add
     */
    public void addBar(HpBar hpBar) {
        hpBars.add(hpBar);
    }

    /**
     *removes an hpBar to this Level.
     *
     * @param hpBar the hp bar to remove
     */
    public void removeBar(HpBar hpBar) {
        hpBars.remove(hpBar);
        hpBar.remove();
    }

    /**
     * Gets entity closest to given location within given radius
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param r the radius we are checking within
     * @return An entity of class type "prey" within the given radius, closest to the given coordinates
     */
    public Animal getTargetBy(int x, int y, int r, Animal not) {

        Animal target = null; //initializes the animal to be returned as null so that null would be returned if no entity is found in the radius

        for (Entity entity : entities) {
            //the total distance from the given coordinate
            int dist = (int) Math.sqrt(Math.pow(entity.getX() - x, 2) + Math.pow(entity.getY() - y, 2));

            //if distance is within radius then return entity
            if (dist <= r && entity != not) {
                target = (Animal) entity; //stores the entity in the variable to be returned, casting it as an animal
                r = dist; //reduces the radius to the distance of the entity so that the closest entity of type c in the radius will be found.
            }
        }

        //if no animal within radius found returns null
        return target;
    }

    /**
     * returns the Milton of this level
     *
     * @return the Milton of this level
     */
    public Milton getMilton() {return milton;}

    /**
     * returns the scene of the program
     *
     * @return the scene of the program
     */
    public Scene getScene(){return level;}

    /**
     * returns the amount of rows of cells of the program
     *
     * @return the amount of rows of cells of the program
     */
    public int getCellR() {return cellR;}

    /**
     * returns the amount of columns of cells of the program
     *
     * @return the amount of columns of cells of the program
     */
    public int getCellC() {return cellC;}

    /**
     * Generates a random number between the given range (inclusive).
     *
     * @param min the minimum number possible to generate
     * @param max the maximum number possible to generate
     * @return a random number within the given range
     */
    public static int getRandomInt(int min, int max){
        if(min > max){
            //ensure parameters are in correct order.
            return getRandomInt(max, min);
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * returns an array of entities
     *
     * @return an array of entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * returns the primary stage of the program
     *
     * @return the primary stage of the program
     */
    public Stage getPrimaryStage() {return primaryStage;}

    /**
     * Returns the container for all visuals of this World.
     *
     * @return the root node
     */
    public Group getRoot() {return root;}

    /**
     * sets the dimensions of the world as columns and rows
     *
     * @param height the height of the scene in pixels
     * @param width the width of the scene in pixels
     */
    public void setCells(int height, int width) {
        this.cellR = height/CELL_SIZE;
        this.cellC = width/CELL_SIZE;
    }
}
