package dbusis.dungeoncrawl;

import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * A class that displays what the player has picked up in a DungeonModel.
 */
public class OptionsView extends Region {
    private Controller parentController;

    public OptionsView() {
        initializeOptionsMenu();
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }

    private void initializeOptionsMenu(){
        Text textN = new Text(40.0,40.0,"THIS IS A TEXT MOUSE OVER IT");
        textN.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(textN);
        textN.setId("HELLOMEOLCHUMS");
        textN.setOnMouseClicked(event -> {
            System.out.println(event.toString());
            System.out.println(event.getButton());
            System.out.println(event.getX());
            System.out.println(event.getTarget());
            System.out.println(((Text) event.getTarget()).getText());
        });
        textN.setOnMouseClicked(parentController);
    }

    /**
     * Creates the empty key object within the inventory.
     */
    public void update(DungeonModel model){
    }
}
