package dbusis.dungeoncrawl;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 * A class that displays what the player has picked up in a DungeonModel.
 */
public class InventoryView extends Group {
    private Polyline key;

    public InventoryView() {
        InitializeInventory();
    }

    /**
     * Creates the empty key object within the inventory.
     */
    private void InitializeInventory() {
        this.key = new Polyline();
        key.getPoints().addAll(
                10.0,0.0,
                0.0,20.0,
                10.0,40.0,
                20.0,20.0,
                10.0,0.0
                );
        this.getChildren().add(key);
    }

    /**
     * Updates the inventory to fill in with the colors of collected objects.
     * @param model The DungeonModel which is being viewed with this view.
     */
    public void update(DungeonModel model){
        if (model.isKeyAcquired()) {
            key.setFill(Color.GOLD);
        } else {
            key.setFill(null);
        }
    }
}
