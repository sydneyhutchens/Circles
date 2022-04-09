package circles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test the Circles application.
 * @author tcolburn
 */
public class CirclesTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java 8 Lab Exercise");
        primaryStage.setScene(new Scene(new Circles()));
        primaryStage.show();
    }

    @org.junit.Test
    public void testCircles() {
        launch();
    }
    
}
