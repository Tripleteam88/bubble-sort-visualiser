import java.io.IOException;
import java.util.Random;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The primary controller class handles the logic for the main application screen/scene.
 * The bubble sort algorithm is managed here.
 *
 * @author Adam Wahba
 * @version 0.7
 * @since 2021-11-26
 */
public class MainScreen {

    private Rectangle[] rects = new Rectangle[7];

    // Each sort pointer will point to a different rectangle to allow for a bubble sort comparison
    private int pointerOne = 0; // Cannot be greater than 5
    private int pointerTwo = 1; // Cannot be greater than 6
    
    private int fullPasses = 0;


    @FXML
    public void initialize() throws IOException
    {
        /**
         * This method is called immediately after the scene is switched but right before the fxml content is loaded completely.
         * Storing the values of rectangles works at this level however no actions can be preformed on them.
         * As a result, the values I am storing are not the rectangle objects themselves but just the future memory addresses of the objects.
         *
         * @param none
         * @return none
         */


        // Call the loader to load the xml again before u try this
        rects = App.getRectList();

    }

    @FXML
    public void fullSort() throws IOException, InterruptedException
    {
        /**
         * This method will completely sort the rectangles.
         * It does this by calling the step sort algorithm over and over again until the rectangles are all sorted.
         *
         * @param none
         * @return none
         */

        while (isSorted() == false)
        {
            // Calls step sort as many times as needed
            stepSort();
        }

        // Displays "solved" at the end
        TextField isSortedTxt = (TextField) App.getScene().lookup("#isSolvedTxt");
        isSortedTxt.setText("Yes");
    }


    @FXML
    public void stepSort() throws InterruptedException {
        /**
         * This method handles the bulk of the sorting operations.
         * It does comparison between rectangles and most data processes that are necessary to ensure the program functions correctly.
         * Please read line comments for more explanations
         *
         * @param none
         * @return none
         */

        // Used Text fields
        TextField fullPassesTxt = (TextField) App.getScene().lookup("#fullPassesTxt");
        TextField isSortedTxt = (TextField) App.getScene().lookup("#isSolvedTxt");

        // Check to make sure the list is not null (DO THIS CHECK FOR ALL BUTTONS)
        if (rects == null) {
            rects = App.getRectList();

            // Print for debugging
            System.out.println("IM null");
            System.out.println(isSorted());
        }


        // Check if it is sorted
        if (isSorted()) {
            // Reset pointers and colours
            pointerOne = 0;
            pointerTwo = 1;
            resetColors();
            // Display solved text
            isSortedTxt.setText("Yes");
        } else {
            // This condition is executed when the rectangles are not in order

            // Print for debugging
            System.out.println("Start Pointer one: " + pointerOne);
            System.out.println("Start Pointer two: " + pointerTwo);
            System.out.println(rects);

            // Store the previous height values
            double rect1Height = rects[pointerOne].getHeight();
            double rect2Height = rects[pointerTwo].getHeight();

            // Store the rectangle objects
            Rectangle rect1 = rects[pointerOne];
            Rectangle rect2 = rects[pointerTwo];

            // Swap only if rectangles are out of order (i.e, when left is taller than right)
            if (rect1Height > rect2Height) {

                // Swap positions as well as place in the array
                swapRects(rect1, rect2);
                rects[pointerOne] = rect2;
                rects[pointerTwo] = rect1;
            }

            // Reset the colour pf the rectangles since they are no longer "selected" by the algorithm
            rect1.setFill(Color.DODGERBLUE);
            rect2.setFill(Color.DODGERBLUE);

            // These conditions handle what happens when the end of the list is reached and when the algorithm is still moving within the list
            if (pointerTwo == 6 || pointerOne == 5) {
                // A full pass has been completed, increment by one and display to the user
                fullPasses += 1;
                fullPassesTxt.setText(String.valueOf(fullPasses));

                // Reset pointers since the end of the list has been reached
                pointerOne = 0;
                pointerTwo = 1;

                // Select new rectangles
                rects[pointerOne].setFill(Color.GREEN);
                rects[pointerTwo].setFill(Color.GREEN);
            } else {

                pointerOne += 1;
                pointerTwo += 1;

                // Reset the color of previously selected rectangles to dodgerblue
                rect1.setFill(Color.DODGERBLUE);
                rect2.setFill(Color.DODGERBLUE);

                // Colour the newly selected rectangles green
                rects[pointerOne].setFill(Color.GREEN);
                rects[pointerTwo].setFill(Color.GREEN);
            }

        }

        // Print for debugging
        System.out.println("End Pointer one: " +pointerOne);
        System.out.println("End Pointer two: "+pointerTwo);


    }

    @FXML
    public void swapRects(Rectangle rectOne, Rectangle rectTwo)
    {
        /**
         * This is a simple method that will swap 2 rectangles on screen.
         *
         * @param 2 rectangles to switch
         * @return none
         */

        // Store both X positions of the rectangles
        double rect1x = rectOne.getLayoutX();
        double rect2x = rectTwo.getLayoutX();
        // Swap their X positions
        rectOne.setLayoutX(rect2x);
        rectTwo.setLayoutX(rect1x);
    }

    @FXML
    public boolean isSorted()
    {
        /**
         * This is a helper style method that loops over the rectangles and checks if they are sorted.
         * Bubble sort algorithms determine if their data set has been solved by iterating over all the elements.
         * If no swap has been performed by the data set must be in order.
         * This method will initially assume that the array is solved, if it detects a need to swap then the it will know that the list is not ordered.
         *
         * @param none
         * @return whether or not the array of rectangles is sorted
         */

        // Necessary check; Bugs can occur without this
        if (rects == null)
        {
            rects = App.getRectList();
        }

        // Initial assumption
        boolean sorted = true;

        // The loop will check 2 rectangles at a time
        for (int i = 0; i < rects.length - 1; i++)
        {
            if (rects[i].getHeight() > rects[i+1].getHeight())
            {
                // If a swap is needed (left rectangle is taller than right) it will know that the list is not sorted
                sorted = false;
            }
        }
        return sorted;
    }

    @FXML
    private void resetColors()
    {
        /**
         * This is a simple method that will reset the color of all but the first 2 rectangles to their original dodgerblue color.
         * This method should be called in the randomizer method after the rectangles have been reset.
         *
         * @param none
         * @return none
         */

        // Null check; Also needed to prevent bugs
        if (rects == null)
            rects = App.getRectList();

        // First 2 rectangles are set to green since they are the first ones "selected" by the algorithm
        rects[0].setFill(Color.GREEN);
        rects[1].setFill(Color.GREEN);

        // Loops over all rectangles except the first 2
        for (int i = 2; i < rects.length; i++)
        {
            rects[i].setFill(Color.DODGERBLUE);
        }

    }

    @FXML
    public void randomize()
    {
        /**
         * The randomize method will randomly produce height values to assign to the existing rectangles on screen.
         * This new data set can be sorted again using the step sort and full sort methods.
         *
         * @param none
         * @return none
         */


        // Null condition
        if (rects == null)
            rects = App.getRectList();

        // Setup for randomization
        Random fateMachine = new Random();
        int minHeight = 80; // Height must not be less than this
        int heightRange = 250; // height must not exceed this

        // These variables will store values that will be used in the x coordinate evaluation
        double prevHeight;
        double prevY;
        double randHeight;

        // Evaluation Formula
        // The y value will increase proportionally as the value of the height decreases
        // The y valie will decrease proportionally as the height value value increases

        // Begin iterating over all rectangles
        for (int i = 0; i < rects.length; i++)
        {
            // Generate a valid random value
            randHeight = (double) fateMachine.nextInt(heightRange) + minHeight;

            // Assign it to the rectangle
            prevHeight = rects[i].getHeight();
            prevY = rects[i].getLayoutY();
            rects[i].setHeight(randHeight);

            // Conditions for evaluations
            if (prevHeight >= rects[i].getHeight()) {
                // The rectangle has become smaller
                // The layoutY has increased by the difference of the previous and new height
                // The new layoutY position can be found by evaluating it using the difference
                double diff = prevHeight - randHeight;
                double yNew = prevY + diff;
                rects[i].setLayoutY(yNew);
            } else {
                // The rectangle has become larger
                // the layoutY has decreased by the difference of the new and previous height
                // The new layoutY position can be found by evaluating it using the difference
                double diff = randHeight - prevHeight;
                double yNew = prevY - diff;
                rects[i].setLayoutY(yNew);
            }

            // Update the new list of rectangles by storing it statically with the app class
            App.setRects(rects);

            // Reset some display values
            fullPasses = 0;
            TextField fullPassesTxt = (TextField) App.getScene().lookup("#fullPassesTxt");
            fullPassesTxt.setText("0");
            TextField isSortedTxt = (TextField) App.getScene().lookup("#isSolvedTxt");
            isSortedTxt.setText("No");
        }

    }

}
