package dbusis.dungeoncrawl;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller implements EventHandler<KeyEvent> {
    @FXML private Label messageLabel;
    @FXML private DungeonView dungeonView;
    private DungeonModel dungeonModel;

    public Controller() {
    }

    public void initialize() {
        this.dungeonModel = new DungeonModel(10,10);
        this.update();
    }

    private void update() {
        this.dungeonView.update(this.dungeonModel);
        this.messageLabel.setText("Use the keys surrounding the S to run from the daleks.");
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyRecognized = true;
        KeyCode code = keyEvent.getCode();

        if (code == KeyCode.LEFT || code == KeyCode.A) {
            this.dungeonModel.rotatePlayerCounterclockwise();
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            this.dungeonModel.rotatePlayerClockwise();
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            this.dungeonModel.movePlayer(true);
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            this.dungeonModel.movePlayer(false);
        } else {
            keyRecognized = false;
        }

        if (keyRecognized) {
            this.update();
            keyEvent.consume();
        }
    }
}
