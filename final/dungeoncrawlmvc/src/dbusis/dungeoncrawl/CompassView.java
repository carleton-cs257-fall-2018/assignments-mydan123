/**
 * CompassView.java
 * @author Daniel Busis, 2018
 *
 * A class that displays which cardinal direction the player
 * is facing in a DungeonModel.
 *
 * Thanks to bensound.com for the royalty-free music.
 */

package dbusis.dungeoncrawl;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

public class CompassView extends Group {
    private Polygon directionPointer;
    private Rotate northRotate = new Rotate(0.0,50.0,50.0);
    private Rotate northRotateUndo = new Rotate(-0.0,50.0,50.0);
    private Rotate eastRotate = new Rotate(90.0,50.0,50.0);
    private Rotate eastRotateUndo = new Rotate(-90.0,50.0,50.0);
    private Rotate southRotate = new Rotate(180.0,50.0,50.0);
    private Rotate southRotateUndo = new Rotate(-180.0,50.0,50.0);
    private Rotate westRotate = new Rotate(270.0,50.0,50.0);
    private Rotate westRotateUndo = new Rotate(-270.0,50.0,50.0);
    private Rotate previousRotateUndo = northRotateUndo;

    public CompassView() {
        initializeCompass();
    }

    private void initializeCompass(){
        Text textN = new Text();
        textN.setText("N");
        textN.setWrappingWidth(40.0);
        textN.setX(30);
        textN.setY(0);
        textN.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(textN);

        Text textE = new Text();
        textE.setText("E");
        textE.setWrappingWidth(20.0);
        textE.setX(90);
        textE.setY(50);
        textE.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(textE);

        Text textS = new Text();
        textS.setText("S");
        textS.setWrappingWidth(40.0);
        textS.setX(30);
        textS.setY(100);
        textS.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(textS);

        Text textW = new Text();
        textW.setText("W");
        textW.setWrappingWidth(20.0);
        textW.setX(-10);
        textW.setY(50);
        textW.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(textW);

        directionPointer = new Polygon();
        directionPointer.getPoints().addAll(
           25.0,65.0,
                50.0,20.0,
                75.0,65.0,
                50.0,55.0
        );
        directionPointer.setFill(Color.valueOf("#1aad57"));
        this.getChildren().add(directionPointer);
    }

    /**
     * Creates the empty key object within the inventory.
     */
    public void update(DungeonModel model){
        this.directionPointer.getTransforms().add(previousRotateUndo);
        switch (model.getPlayerDirection()){
            case NORTH:
                this.directionPointer.getTransforms().add(northRotate);
                this.previousRotateUndo = northRotateUndo;
                break;
            case EAST:
                this.directionPointer.getTransforms().add(eastRotate);
                this.previousRotateUndo = eastRotateUndo;
                break;
            case SOUTH:
                this.directionPointer.getTransforms().add(southRotate);
                this.previousRotateUndo = southRotateUndo;
                break;
            case WEST:
                this.directionPointer.getTransforms().add(westRotate);
                this.previousRotateUndo = westRotateUndo;
                break;
        }
    }
}
