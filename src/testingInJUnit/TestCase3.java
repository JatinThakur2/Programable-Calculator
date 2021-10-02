package testingInJUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.stage.Stage;

import org.junit.Test;

import Calculator.TableUserInterface;


public class TestCase3 {
	@Test
	public void testA() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel(); // Initializes the JavaFx Platform
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							new TableUserInterface().start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000); // Time to use the app, with out this, the thread
								// will be killed before you can tell.
	}
}
