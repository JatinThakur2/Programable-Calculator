
package Calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p> Title: Calculator Class. </p>
 * 
 * <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2019 </p>
 * 
 * @author Lynn Robert Carter (Baseline)
 * @author Jatin Thakur
 * 
 * @version 4.01	2019-01-29 The mainline of a JavaFX-based GUI implementation of a long integer calculator
 * @version 4.02    2019-02-19 The mainline of a JavaFX-based GUI implementation of Double calculator with log base2
 * @version 4.03    2019-03-20 The mainline of a javaFX-based GUI implementation of Double Calculator with FSM
 * @version 4.04    2019-04-04 The mainline of a javaFX-based GUI implementation of Double Calculator with Error Term
 * @version 4.05    2019-09-29 The mainline of a JavaFX-based GUI Implementation of a UNumber Calculator with SquareRoot
 * @version 4.06    2019-11-24 The mainline of a JavaFX-based GUI Implementation of a Scientific Calculator with Units.
 * @version 4.07    2020-03-12 The mainline of a JavaFX-based GUI Implementation of Programmable Calculator.
 * 
 */

public class Calculator extends Application {
	
	public final static double WINDOW_WIDTH = 1000;
	public final static double WINDOW_HEIGHT = 550;
	
	public UserInterface theGUI;

	/**********
	 * This is the start method that is called once the application has been loaded into memory and is 
	 * ready to get to work.
	 * 
	 * In designing this application I have elected to IGNORE all opportunities for automatic layout
	 * support and instead have elected to manually position each GUI element and its properties in
	 * order to exercise complete control over the user interface look and feel.
	 * 
	 */
	@Override
	public void start(Stage theStage) throws Exception {
		
		theStage.setTitle("Jatin Thakur");			// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		theGUI = new UserInterface(theRoot);					// Create the Graphical User Interface
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
	
		theStage.setScene(theScene);							// Set the scene on the stage
		
		theStage.show();										// Show the stage to the user
		
		// When the stage is shown to the user, the pane within the window is visible.  This means that the
		// labels, fields, and buttons of the Graphical User Interface (GUI) are visible and it is now 
		// possible for the user to select input fields and enter values into them, click on buttons, and 
		// read the labels, the results, and the error messages.
	}
	


	/*******************************************************************************************************/

	/*******************************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 */
	public static void main(String[] args) {					// This method may not be required
		launch(args);											// for all JavaFX applications using
	}															// other IDEs.
}
