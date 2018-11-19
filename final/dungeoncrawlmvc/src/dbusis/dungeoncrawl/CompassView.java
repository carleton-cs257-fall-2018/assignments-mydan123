package dbusis.dungeoncrawl;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * A class that displays what the player has picked up in a DungeonModel.
 */
public class CompassView extends Group {
    ImageView compView;

    public CompassView() {
        Image compassPic = new Image("/res/compass.png");
        ImageView compassView = new ImageView();
        compassView.setFitWidth(100);
        compassView.setFitHeight(100);
        compassView.setSmooth(true);
        compassView.setCache(true);
        compassView.setImage(compassPic);
        this.getChildren().add(compassView);
        compView = compassView;
    }

    /**
     * Creates the empty key object within the inventory.
     */
    public void update(DungeonModel model){
        switch (model.getPlayerDirection()){
            case NORTH:
                compView.setRotate(0.0);
                break;
            case WEST:
                compView.setRotate(90.0);
                break;
            case SOUTH:
                compView.setRotate(180.0);
                break;
            case EAST:
                compView.setRotate(270.0);
                break;
        }
    }
}
