/**
 * Controller.java
 * @author Daniel Busis, 2018
 *
 * Handles MouseEvents and KeyEvents for the DungeonCrawl application,
 * and directs the Views by having them be displayed or not displayed
 * at appropriate times.
 *
 * Whenever an event is handled, updates all views in the application.
 *
 * Also directs audio for the application to play/stop at appropriate times,
 * as well as directing audio classes to adjust their volume.
 *
 * Thanks to bensound.com for the royalty-free music.
 */

package dbusis.dungeoncrawl;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Controller implements EventHandler<Event> {
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

    /**
     * @TODO: Add instructions to close options menu
     * @TODO: Disable most actions when options menu is open
     * @TODO: Add sound effects for key and door
     * @TODO: Improve footsteps sound effect?
     * @TODO: Move volume settings to the model? Or an additional model?
     * @TODO: Add a HelpView with instructions
     * @TODO: Add visual for entering the door room. (Staircase?)
     * @TODO: COMMENTS!
     * @TODO: Map/inv label toggles
     * @TODO: Strings to public final static Strings.
     * @TODO: 10x10 hardcoded square limit?
     */

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
        backgroundMusicPlayer.setVolume(0.30);
        backgroundMusicPlayer.setAutoPlay(true);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        File footstepFile = new File("src/res/footstep-trim.wav");
        footstepMusicPlayer = new AudioClip(footstepFile.toURI().toString());
        footstepMusicPlayer.setVolume(0.80);
    }

    public boolean getMapShown() {
        return this.mapView.isVisible();
    }

    public boolean getInventoryShown() {
        return this.inventoryView.isVisible();
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
        this.optionsView.update(this.dungeonModel);
    }

    private void toggleMusic(){
        if (this.backgroundMusicPlayer.getStatus() == MediaPlayer.Status.STOPPED){
            backgroundMusicPlayer.play();
        } else {
            backgroundMusicPlayer.stop();
        }
    }

    @SuppressWarnings("Duplicates")
    private void changeMusicVolume(Double changeAmount){
        double curVolume = backgroundMusicPlayer.getVolume();
        double newVolume = curVolume + changeAmount;
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

    @SuppressWarnings("Duplicates")
    private void changeSFXVolume(Double changeAmount){
        double curVolume = footstepMusicPlayer.getVolume();
        double newVolume = curVolume + changeAmount;
        if (newVolume >= 0.0 && newVolume <= 1.0){
            footstepMusicPlayer.setVolume(newVolume);
        } else {
            if (changeAmount > 0){
                footstepMusicPlayer.setVolume(1.0);
            } else {
                footstepMusicPlayer.setVolume(0.0);
            }
        }
    }

    public double getMusicVolume(){
        return backgroundMusicPlayer.getVolume();
    }

    public double getSFXVolume(){
        return footstepMusicPlayer.getVolume();
    }

    private void toggleInventoryView(){
        this.inventoryView.setVisible(!this.inventoryView.isVisible());
        this.inventoryView.setManaged(!this.inventoryView.isManaged());
    }
    
    private void toggleMapView(){
        this.mapView.setVisible(!this.mapView.isVisible());
        this.mapView.setManaged(!this.mapView.isManaged());
    }

    private void toggleOptionsView(){
        this.optionsView.setVisible(!this.optionsView.isVisible());
        this.optionsView.setManaged(!this.optionsView.isManaged());
    }

    @Override
    public void handle(Event event) {
        if (event instanceof KeyEvent) {
            this.handleKeyEvent((KeyEvent) event);
        } else if (event instanceof MouseEvent) {
            this.handleMouseEvent((MouseEvent) event);
        }

        event.consume();
        this.update();
    }

    private void handleMouseEvent(MouseEvent mouseEvent){
        String eventSourceID = ((Node) mouseEvent.getSource()).getId();
        switch (eventSourceID) {
            case "musicVolDown":
                changeMusicVolume(-0.1);
                break;
            case "musicVolUp":
                changeMusicVolume(0.1);
                break;
            case "sfxVolDown":
                changeSFXVolume(-0.1);
                break;
            case "sfxVolUp":
                changeSFXVolume(0.1);
                break;
            case "toggleMap":
                toggleMapView();
                break;
            case "toggleInv":
                toggleInventoryView();
                break;
        }
    }

    private void handleKeyEvent(KeyEvent keyEvent){
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
            this.toggleMapView();
        } else if (code == KeyCode.I) {
            this.toggleInventoryView();
        } else if (code == KeyCode.N && this.dungeonModel.isExitReached()) {
            this.dungeonModel = new DungeonModel();
        } else if (code == KeyCode.P) {
            this.toggleMusic();
        } else if (code == KeyCode.ESCAPE) {
            this.toggleOptionsView();
        } else {
            keyRecognized = false;
        }

        if (keyRecognized) {
            this.dungeonModel.updateDiscoveredSquares();
        }
    }
}
