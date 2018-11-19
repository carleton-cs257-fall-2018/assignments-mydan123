/**
 * OptionsView.java
 * @author Daniel Busis, 2018
 *
 * A class which displays an options menu to the user,
 * to adjust volumes and toggle UI elements.
 *
 * Thanks to bensound.com for the royalty-free music.
 */

package dbusis.dungeoncrawl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OptionsView extends Region {
    private Controller parentController;
    private Rectangle[] musicVolBars = new Rectangle[10];
    private Rectangle[] sfxVolBars = new Rectangle[10];
    private Label mapButton;
    private Label invButton;

    public OptionsView() {
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
        initializeOptionsMenu();
    }

    private Background buttonBackground = new Background(new BackgroundFill(Color.RED,new CornerRadii(20.0),null));
    private Background greenButtonBackground = new Background(new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(20.0),null));
    private Border buttonBorder = new Border(new BorderStroke(null,BorderStrokeStyle.SOLID,new CornerRadii(20.0),null));
    private void makeOptionsButton(Label curButton){
        curButton.setOnMouseClicked(parentController);
        curButton.setPadding(new Insets(10.0));
        curButton.setBackground(buttonBackground);
        curButton.setBorder(buttonBorder);
    }

    private void initializeOptionsMenu(){
        initializeOptionsTitle();
        initializeMusicVolBox();
        initializeSFXVolBox();
        initializeMapButton();
        initializeInvButton();
    }

    private void initializeOptionsTitle() {
        HBox optionsTitleBox = new HBox();
        optionsTitleBox.setPrefWidth(400.0);
        optionsTitleBox.setTranslateY(10.0);
        optionsTitleBox.setAlignment(Pos.CENTER);
        Label optionsTitle = new Label("Options:");
        optionsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
        optionsTitleBox.getChildren().add(optionsTitle);
        this.getChildren().add(optionsTitleBox);
    }

    private void initializeMusicVolBox() {
        HBox musicVolBox = new HBox(5);
        musicVolBox.setPrefWidth(400.0);
        musicVolBox.setTranslateY(60.0);
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

    private void initializeMapButton() {
        HBox mapButtonBox = new HBox(5);
        mapButtonBox.setPrefWidth(400.0);
        mapButtonBox.setTranslateY(160.0);
        mapButtonBox.setAlignment(Pos.CENTER_LEFT);

        Label mapButtonLabel = new Label("Toggle Map: ");
        mapButtonLabel.setPadding(new Insets(10.0));
        mapButtonLabel.setStyle("-fx-font-weight: bold");
        mapButtonBox.getChildren().add(mapButtonLabel);

        Label newMapButton = new Label("Hide");
        newMapButton.setId("toggleMap");
        makeOptionsButton(newMapButton);
        mapButtonBox.getChildren().add(newMapButton);

        this.mapButton = newMapButton;
        this.getChildren().add(mapButtonBox);
    }

    private void initializeInvButton() {
        HBox invButtonBox = new HBox(5);
        invButtonBox.setPrefWidth(400.0);
        invButtonBox.setTranslateY(210.0);
        invButtonBox.setAlignment(Pos.CENTER_LEFT);

        Label invButtonLabel = new Label("Toggle Inventory: ");
        invButtonLabel.setPadding(new Insets(10.0));
        invButtonLabel.setStyle("-fx-font-weight: bold");
        invButtonBox.getChildren().add(invButtonLabel);

        Label newInvButton = new Label("Hide");
        newInvButton.setId("toggleInv");
        makeOptionsButton(newInvButton);
        invButtonBox.getChildren().add(newInvButton);

        this.invButton = newInvButton;
        this.getChildren().add(invButtonBox);
    }

    public void update(DungeonModel model){
        double curMusicVol = parentController.getMusicVolume();
        int musicBarCutoff = (int)(Math.round(curMusicVol*musicVolBars.length*100.0)/100.0);
        for (int i = 0; i< musicVolBars.length; i++){
            if (i < musicBarCutoff) {
                musicVolBars[i].setFill(Color.LIGHTGREEN);
            } else {
                musicVolBars[i].setFill(Color.WHITE);
            }
        }

        double curSFXVol = parentController.getSFXVolume();
        int sfxBarCutoff = (int)(Math.round(curSFXVol*sfxVolBars.length*100.0)/100.0);
        for (int i = 0; i< sfxVolBars.length; i++){
            if (i < sfxBarCutoff) {
                sfxVolBars[i].setFill(Color.LIGHTGREEN);
            } else {
                sfxVolBars[i].setFill(Color.WHITE);
            }
        }

        updateToggleButton(this.mapButton, parentController.getMapShown());
        updateToggleButton(this.invButton, parentController.getInventoryShown());
    }

    private void updateToggleButton(Label button, boolean buttonObjectShown){
        if (buttonObjectShown) {
            button.setBackground(buttonBackground);
            button.setText("Hide");
        }
        else {
            button.setBackground(greenButtonBackground);
            button.setText("Show");
        }
    }
}
