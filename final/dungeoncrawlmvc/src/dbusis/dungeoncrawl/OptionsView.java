package dbusis.dungeoncrawl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * A class that displays what the player has picked up in a DungeonModel.
 */
public class OptionsView extends Region {
    private Controller parentController;
    private Rectangle[] musicVolBars = new Rectangle[10];
    private Rectangle[] sfxVolBars = new Rectangle[10];

    public OptionsView() {
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
        initializeOptionsMenu();
    }

    private Background buttonBackground = new Background(new BackgroundFill(Color.RED,new CornerRadii(20.0),null));
    private Border buttonBorder = new Border(new BorderStroke(null,BorderStrokeStyle.SOLID,new CornerRadii(20.0),null));
    private void makeOptionsButton(Label curButton){
        curButton.setOnMouseClicked(parentController);
        curButton.setPadding(new Insets(10.0));
        curButton.setBackground(buttonBackground);
        curButton.setBorder(buttonBorder);
    }

    private void initializeOptionsMenu(){
        HBox optionsTitleBox = new HBox();
        optionsTitleBox.setPrefWidth(400.0);
        optionsTitleBox.setAlignment(Pos.CENTER);
        optionsTitleBox.setTranslateY(10.0);
        Label optionsTitle = new Label("Options:");
        optionsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
        optionsTitleBox.getChildren().add(optionsTitle);
        this.getChildren().add(optionsTitleBox);

        initializeMusicVolBox();
        initializeSFXVolBox();
    }

    private void initializeMusicVolBox() {
        HBox musicVolBox = new HBox(5);
        musicVolBox.setPrefWidth(400.0);
        musicVolBox.setTranslateY(50.0);
        musicVolBox.setAlignment(Pos.CENTER_LEFT);

        Label musicVol = new Label("Music Volume:  ");
        musicVol.setPadding(new Insets(10.0));
        musicVol.setStyle("-fx-font-weight: bold");
        musicVolBox.getChildren().add(musicVol);

        Label volDown = new Label("-");
        volDown.setId("musicVolDown");
        makeOptionsButton(volDown);
        musicVolBox.getChildren().add(volDown);

        for (int i = 0; i< this.musicVolBars.length; i++){
            Rectangle newBar = new Rectangle(10,30.0);
            newBar.setFill(Color.WHITE);
            this.musicVolBars[i] = newBar;
            musicVolBox.getChildren().add(newBar);
        }

        Label volUp = new Label("+");
        volUp.setId("musicVolUp");
        makeOptionsButton(volUp);
        musicVolBox.getChildren().add(volUp);

        this.getChildren().add(musicVolBox);
    }

    private void initializeSFXVolBox() {
        HBox sfxVolBox = new HBox(5);
        sfxVolBox.setPrefWidth(400.0);
        sfxVolBox.setTranslateY(110.0);
        sfxVolBox.setAlignment(Pos.CENTER_LEFT);

        Label sfxVol = new Label("Effects Volume: ");
        sfxVol.setPadding(new Insets(10.0));
        sfxVol.setStyle("-fx-font-weight: bold");
        sfxVolBox.getChildren().add(sfxVol);

        Label volDown = new Label("-");
        volDown.setId("sfxVolDown");
        makeOptionsButton(volDown);
        sfxVolBox.getChildren().add(volDown);

        for (int i = 0; i< this.sfxVolBars.length; i++){
            Rectangle newBar = new Rectangle(10,30.0);
            newBar.setFill(Color.WHITE);
            this.sfxVolBars[i] = newBar;
            sfxVolBox.getChildren().add(newBar);
        }

        Label volUp = new Label("+");
        volUp.setId("sfxVolUp");
        makeOptionsButton(volUp);
        sfxVolBox.getChildren().add(volUp);

        this.getChildren().add(sfxVolBox);

    }

    /**
     * Creates the empty key object within the inventory.
     */
    public void update(DungeonModel model){
        double curMusicVol = parentController.getMusicVolume();
        int musicBarCutoff = (int)(curMusicVol* musicVolBars.length);
        for (int i = 0; i< musicVolBars.length; i++){
            if (i < musicBarCutoff) {
                musicVolBars[i].setFill(Color.LIGHTGREEN);
            } else {
                musicVolBars[i].setFill(Color.WHITE);
            }
        }

        double curSFXVol = parentController.getSFXVolume();
        int sfxBarCutoff = (int)(curSFXVol* sfxVolBars.length);
        for (int i = 0; i< sfxVolBars.length; i++){
            if (i < sfxBarCutoff) {
                sfxVolBars[i].setFill(Color.LIGHTGREEN);
            } else {
                sfxVolBars[i].setFill(Color.WHITE);
            }
        }
    }
}
