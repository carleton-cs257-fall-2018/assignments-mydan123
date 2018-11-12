package dbusis.dungeoncrawl;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * View which displays a mini-map of discovered and undiscovered squares within the dungeon.
 */
public class MapView extends Group {
    public final static double MAP_WIDTH = 120.0;
    public final static double MAP_HEIGHT = 120.0;
    private Rectangle[][] dungeonMap;

    private int dungeonRows=10;
    private int dungeonColumns=10;

    public MapView() {
        InitializeMap();
    }

    /**
     * Creates the Rectangles that are the grid used to display the MapView.
     */
    private void InitializeMap(){
        int rows = this.dungeonRows;
        int columns = this.dungeonColumns;
        double square_width = this.MAP_WIDTH/columns;
        double square_height = this.MAP_HEIGHT/rows;
        this.dungeonMap = new Rectangle[rows][columns];

        for(int row = 0; row < rows; row++){
            for (int column = 0; column<columns; column++){
                Rectangle curRect = new Rectangle();
                curRect.setX(column*square_width);
                curRect.setY(row*square_height);
                curRect.setWidth(square_width);
                curRect.setHeight(square_height);
                this.dungeonMap[row][column] = curRect;
                this.getChildren().add(curRect);
            }
        }
    }

    /**
     * Uses a DungeonModel to display cells in the correct colors based on their contents.
     * @param model The DungeonModel being viewed.
     */
    private void updateMap(DungeonModel model) {
        for (int row = 0; row < this.dungeonRows; row++) {
            for (int column = 0; column < this.dungeonColumns; column++) {
                DungeonModel.SquareValue curSquareVal = model.getSquareValue(row, column);
                Color curSquareColor;
                if (!model.isDiscovered(row, column)) {
                    curSquareColor = Color.DARKSLATEGRAY;
                } else if (row == model.getPlayerRow() && column == model.getPlayerColumn()) {
                    curSquareColor = Color.BLUE;
                } else if (curSquareVal == DungeonModel.SquareValue.WALL) {
                    curSquareColor = Color.valueOf("#404040");
                } else if (curSquareVal == DungeonModel.SquareValue.EMPTY) {
                    curSquareColor = Color.valueOf("#808080");
                } else if (curSquareVal == DungeonModel.SquareValue.KEY) {
                    curSquareColor = Color.ORANGE;
                } else if (curSquareVal == DungeonModel.SquareValue.GOAL) {
                    curSquareColor = Color.valueOf("#684c0f");
                } else {
                    curSquareColor = Color.BLUE;
                }
                dungeonMap[row][column].setFill(curSquareColor);
            }
        }
    }

    /**
     * Updates te map to match a given DungeonModel.
     * @param model A DungeonModel to be viewed.
     */
    public void update(DungeonModel model) {
        updateMap(model);
    }

}
