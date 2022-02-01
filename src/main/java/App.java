
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is a Bubble sort visualizer application that was created using the javafx11 library.
 *
 * @author Adam Wahba
 * @Version 0.7
 * @Since 2021-11-26
 */
public class App extends Application {

    private static Scene scene;
    protected static Rectangle[] rects;

    @Override
    public void start(Stage stage) throws IOException {
        /**
         * This start method is loads the first fxml file and sets it to the stage which is the native os window.
         *
         * @param the native os window (passed automatically by javafx)
         * @return nothing
         */
        scene = new Scene(loadFXML("primary"), 875, 570);
        stage.setScene(scene);
        stage.setTitle("Sorting Visualizer v0.6");
        stage.show();

        // Store the rectangles and select the first 2 by default
        rects = App.getAllRects();
        rects[0].setFill(Color.GREEN);
        rects[1].setFill(Color.GREEN);
    }

    static void setRoot(String fxml) throws IOException {
        /**
         * This method changes what fxml file is loaded to the current scene.
         * Effectively changing what is displayed on the stage or window to whatever is contained in the fxml file
         *
         * @param fxml filename to change to
         * @return nothing
         */
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        /**
         * This is a helper type method that loads an fxml file to a javafx node so that it can be used on the scene.
         * The method will return a javafx node.
         *
         * @param fxml filename to load
         * @return loaded fxml parent node (scene node)
         */
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    protected static Scene getScene() {
        /**
         * Simply just returns the current scene so it can be accessed by controllers
         *
         * @param none
         * @return current app scene
         */
        return scene;
    }

    @FXML
    protected static Rectangle[] getAllRects() {
        /**
         * Another helper type function that will return a list of all rectangle elements on screen using the fxml
         *
         * @param none
         * @return the rectangle objects(nodes) on the scene
         */

        Rectangle[] rectList = new Rectangle[7];
        int recNum = 1;
        for (int i = 0; i < rectList.length; i++)
        {
            rectList[i] = getRect("#rect" + String.valueOf(recNum));
            recNum++;
        }
        return rectList;
    }

    @FXML
    private static Rectangle getRect(String rectid)
    {
        /**
         * Method that shortens the code needed to lookup a rectangle with its id
         *
         * @param String id of a rectangle
         * @return the rectangle that was searched
         */
        Rectangle rect = (Rectangle) getScene().lookup(rectid);
        return rect;
    }

    protected static Rectangle[] getRectList() 
    {
        /**
         * Getter method that returns the static list of rectangles from the scene
         *
         * @param none
         * @return the rectangle objects/nodes from the scene
         */
        return rects;
    }

    protected static void setRects(Rectangle[] newRects)
    {
        /**
         * Setter method that stores a new list of rectangles in the app's static list variable
         *
         * @param list of new rectangle objects
         * @returns nothing
         */

        // This method should only be called by the main controller randomize function after it updates rectangle heights
        rects = newRects;
    }


    public static void main(String[] args) {
        /**
         * This is the main method that is called by the jvm to launch the application
         * @param args unused
         * @return nothing
         */
        launch();
    }

}