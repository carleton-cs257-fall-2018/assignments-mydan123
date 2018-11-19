/**
 * @author Daniel Busis
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
        primaryStage.setTitle("Daleks");

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
