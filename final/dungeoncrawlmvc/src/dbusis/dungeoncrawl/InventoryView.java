package dbusis.dungeoncrawl;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class InventoryView extends Group {
    private Polyline key;

    public InventoryView() {
        InitializeInventory();
    }

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

    public void update(DungeonModel model){
        if (model.isKeyAcquired()) {
            key.setFill(Color.GOLD);
        } else {
            key.setFill(null);
        }
    }
}
