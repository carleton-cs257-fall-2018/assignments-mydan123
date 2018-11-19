/**
 * Main.java
 * @author Daniel Busis, 2018
 *
 * Initializes the JavaFX window for this DungeonCrawl application.
 * The window is set to be an un-resizeable 800x600
 *
 * Thanks to bensound.com for the royalty-free music.
 */

package dbusis.dungeoncrawl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dungeon.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("DungeonCrawl");
        primaryStage.setResizable(false);

        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = 800;
        double sceneHeight = 600;
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
