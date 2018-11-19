package dbusis.dungeoncrawl;

import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Controller implements EventHandler<KeyEvent> {
    @FXML private Label messageLabel;
    @FXML private DungeonView dungeonView;
    @FXML private MapView mapView;
    @FXML private InventoryView inventoryView;
    @FXML private CompassView compassView;
    @FXML private OptionsView optionsView;
    private MediaPlayer backgroundMusicPlayer;
    private AudioClip footstepMusicPlayer;
    private DungeonModel dungeonModel;

    public Controller() {
    }

    public void initialize() {
        this.dungeonModel = new DungeonModel();
        initializeOptionsView();
        initializeSoundPlayers();
        this.update();
    }

    private void initializeOptionsView(){
        this.optionsView.setParentController(this);
        this.optionsView.toFront();
        this.optionsView.setVisible(false);
        this.optionsView.setManaged(false);
    }

    private void initializeSoundPlayers(){
        File musicFile = new File("src/res/bensound-epic.mp3");
        Media musicMedia = new Media(musicFile.toURI().toString());
        backgroundMusicPlayer = new MediaPlayer(musicMedia);
        backgroundMusicPlayer.setVolume(0.3);
        backgroundMusicPlayer.setAutoPlay(true);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        File footstepFile = new File("src/res/footstep-trim.wav");
        footstepMusicPlayer = new AudioClip(footstepFile.toURI().toString());
        footstepMusicPlayer.setVolume(0.8);
    }

    private void update() {
        this.dungeonView.update(this.dungeonModel);
        this.mapView.update(this.dungeonModel);
        this.inventoryView.update(this.dungeonModel);
        this.compassView.update(this.dungeonModel);
        if (this.dungeonModel.isExitReached()) {
            this.messageLabel.setText("You've found the exit! Press N to ascend to the next floor!");
        } else {
            this.messageLabel.setText("Use WASD or the Arrow Keys to find the key and then the exit!");
        }
    }

    private void toggleMusic(){
        if (this.backgroundMusicPlayer.getStatus() == MediaPlayer.Status.STOPPED){
            backgroundMusicPlayer.play();
        } else {
            backgroundMusicPlayer.stop();
        }
    }

    private void changeMusicVolume(Double changeAmount){
        Double curVolume = backgroundMusicPlayer.getVolume();
        Double newVolume = curVolume + changeAmount;
        if (newVolume >= 0.0 && newVolume <= 1.0){
            backgroundMusicPlayer.setVolume(newVolume);
        } else {
            if (changeAmount > 0){
                backgroundMusicPlayer.setVolume(1.0);
            } else {
                backgroundMusicPlayer.setVolume(0.0);
            }
        }
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
            boolean didMove = this.dungeonModel.movePlayer(true);
            if (didMove) this.footstepMusicPlayer.play();
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            boolean didMove = this.dungeonModel.movePlayer(false);
            if (didMove) this.footstepMusicPlayer.play();
        } else if (code == KeyCode.M) {
            this.mapView.setVisible(!this.mapView.isVisible());
            this.mapView.setManaged(!this.mapView.isManaged());
        } else if (code == KeyCode.I) {
            this.inventoryView.setVisible(!this.inventoryView.isVisible());
            this.inventoryView.setManaged(!this.inventoryView.isManaged());
        } else if (code == KeyCode.N && this.dungeonModel.isExitReached()) {
            this.dungeonModel = new DungeonModel();
        } else if (code == KeyCode.P) {
            toggleMusic();
        } else if (code == KeyCode.K) {
            changeMusicVolume(.1);
        } else if (code == KeyCode.J) {
            changeMusicVolume(-.1);
        } else if (code == KeyCode.ESCAPE) {
            this.optionsView.setVisible(!this.optionsView.isVisible());
            this.optionsView.setManaged(!this.optionsView.isManaged());
        } else {
            keyRecognized = false;
        }

        if (keyRecognized) {
            this.dungeonModel.updateDiscoveredSquares();
            this.update();
            keyEvent.consume();
        }
    }
}
