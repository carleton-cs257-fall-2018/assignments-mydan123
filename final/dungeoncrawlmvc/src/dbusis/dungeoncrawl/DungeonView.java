package dbusis.dungeoncrawl;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

public class DungeonView extends Group {
    public final static double MAP_WIDTH = 120.0;
    public final static double MAP_HEIGHT = 120.0;
    public final static double MAP_LOC_X = 640.0;
    public final static double MAP_LOC_Y = 150.0;

    private Rectangle[][] dungeonMap;

    private Map<String,javafx.scene.shape.Shape> displayPolies = new HashMap<>();
    private Map<String, Color> defaultColors = new HashMap<>();
    private Color OPEN_COLOR = Color.valueOf("#333333");
    private Color DOOR_COLOR = Color.valueOf("#684c0f");

    @FXML private int dungeonRows=10;
    @FXML private int dungeonColumns=10;

    public int getDungeonRows() {
        return this.dungeonRows;
    }

    public void setDungeonRows(int newRows) {
        this.dungeonRows = newRows;
    }

    public int getDungeonColumns() {
        return this.dungeonColumns;
    }

    public void setDungeonColumns(int newColumns) {
        this.dungeonColumns = newColumns;
    }

    public DungeonView() {
        InitializeDungeonBG();
        InitializeMap();
    }

    private void InitializeDungeonBG() {
        Polygon leftWedge = new Polygon();
        leftWedge.getPoints().addAll(
                0.0, 0.0,
                25.0, 0.0,
                110.0, 25.0,
                110.0, 360.0,
                20.0, 450.0,
                0.0, 450.0
        );
        leftWedge.setFill(Color.valueOf("#999999"));
        this.displayPolies.put("leftWedge", leftWedge);
        this.defaultColors.put("leftWedge", Color.valueOf("#999999"));
        this.getChildren().add(leftWedge);

        Polygon rightWedge = new Polygon();
        rightWedge.getPoints().addAll(
                600.0, 0.0,
                575.0, 0.0,
                490.0, 25.0,
                490.0, 360.0,
                580.0, 450.0,
                600.0, 450.0
        );
        rightWedge.setFill(Color.valueOf("#999999"));
        this.displayPolies.put("rightWedge", rightWedge);
        this.defaultColors.put("rightWedge", Color.valueOf("#999999"));
        this.getChildren().add(rightWedge);

        Polygon topWedge = new Polygon();
        topWedge.getPoints().addAll(
                25.0, 0.0,
                110.0, 25.0,
                490.0, 25.0,
                575.0, 0.0
        );
        topWedge.setFill(Color.valueOf("#888888"));
        this.displayPolies.put("topWedge", topWedge);
        this.defaultColors.put("topWedge", Color.valueOf("#888888"));
        this.getChildren().add(topWedge);

        Polygon bottomWedge = new Polygon();
        bottomWedge.getPoints().addAll(
                20.0, 450.0,
                110.0, 360.0,
                490.0, 360.0,
                580.0, 450.0
        );
        bottomWedge.setFill(Color.valueOf("#777777"));
        this.displayPolies.put("bottomWedge", bottomWedge);
        this.defaultColors.put("bottomWedge", Color.valueOf("#777777"));
        this.getChildren().add(bottomWedge);

        Polygon smallLeftWedge = new Polygon();
        smallLeftWedge.getPoints().addAll(
                110.0, 25.0,
                140.0, 40.0,
                140.0, 320.0,
                110.0, 360.0
        );
        smallLeftWedge.setFill(Color.GREY);
        this.displayPolies.put("farLeftWedge", smallLeftWedge);
        this.defaultColors.put("farLeftWedge", Color.GREY);
        this.getChildren().add(smallLeftWedge);

        Polygon smallRightWedge = new Polygon();
        smallRightWedge.getPoints().addAll(
                490.0, 25.0,
                460.0, 40.0,
                460.0, 320.0,
                490.0, 360.0
        );
        smallRightWedge.setFill(Color.GREY);
        this.displayPolies.put("farRightWedge", smallRightWedge);
        this.defaultColors.put("farRightWedge", Color.GREY);
        this.getChildren().add(smallRightWedge);

        Polygon smallTopWedge = new Polygon();
        smallTopWedge.getPoints().addAll(
                110.0, 25.0,
                140.0, 40.0,
                460.0, 40.0,
                490.0, 25.0
        );
        smallTopWedge.setFill(Color.valueOf("#707070"));
        this.displayPolies.put("farTopWedge", smallTopWedge);
        this.defaultColors.put("farTopWedge", Color.valueOf("#707070"));
        this.getChildren().add(smallTopWedge);

        Polygon smallBottomWedge = new Polygon();
        smallBottomWedge.getPoints().addAll(
                140.0, 320.0,
                110.0, 360.0,
                490.0, 360.0,
                460.0, 320.0
        );
        smallBottomWedge.setFill(Color.valueOf("#555555"));
        this.displayPolies.put("farBottomWedge", smallBottomWedge);
        this.defaultColors.put("farBottomWedge", Color.valueOf("#555555"));
        this.getChildren().add(smallBottomWedge);

        Rectangle hallNext = new Rectangle();
        hallNext.setFill(Color.valueOf("#333333"));
        hallNext.setX(140.0);
        hallNext.setY(40.0);
        hallNext.setWidth(320.0);
        hallNext.setHeight(280.0);
        this.displayPolies.put("farEnd", hallNext);
        this.defaultColors.put("farEnd", Color.valueOf("#333333"));
        this.getChildren().add(hallNext);

        Rectangle deadEnd = new Rectangle();
        deadEnd.setFill(Color.GREEN);
        deadEnd.setX(110.0);
        deadEnd.setY(25.0);
        deadEnd.setWidth(380.0);
        deadEnd.setHeight(335.0);
        this.displayPolies.put("closeEnd", deadEnd);
        this.defaultColors.put("closeEnd", Color.DARKGRAY);
        this.getChildren().add(deadEnd);

        Polygon key = new Polygon();
        key.getPoints().addAll(
            300.0,300.0,
                290.0,320.0,
                300.0,340.0,
                310.0,320.0
        );
        key.setFill(Color.GOLD);
        key.setVisible(false);
        this.displayPolies.put("key", key);
        this.defaultColors.put("key", Color.GOLD);
        this.getChildren().add(key);

        Polygon keyHole = new Polygon();
        keyHole.getPoints().addAll(
            300.0,200.0,
                290.0,220.0,
                300.0,240.0,
                310.0,220.0
        );
        keyHole.setFill(Color.BLACK);
        keyHole.setVisible(false);
        this.displayPolies.put("keyHole", keyHole);
        this.defaultColors.put("keyHole", Color.BLACK);
        this.getChildren().add(keyHole);
    }

    private void updateDungeonBG(DungeonModel model) {
        BGHideAll();
        BGSetAllDefaultColors();

        HashMap<String, int[]> wallPositions = model.getVisibleSquares();

        displayPolies.get("topWedge").setVisible(true);
        displayPolies.get("bottomWedge").setVisible(true);

        BGDrawWall(displayPolies.get("leftWedge"), model.getSquareValue(wallPositions.get("left")));
        BGDrawWall(displayPolies.get("rightWedge"), model.getSquareValue(wallPositions.get("right")));

        if (model.getSquareValue(wallPositions.get("front")) == DungeonModel.SquareValue.WALL
                || model.getSquareValue(wallPositions.get("front")) == DungeonModel.SquareValue.GOAL) {
            BGDrawCloseWall(displayPolies.get("front"), model.getSquareValue(wallPositions.get("front")));
        } else {
            displayPolies.get("farLeftWedge").setVisible(true);
            displayPolies.get("farRightWedge").setVisible(true);
            displayPolies.get("farTopWedge").setVisible(true);
            displayPolies.get("farBottomWedge").setVisible(true);
            displayPolies.get("farEnd").setVisible(true);

            if (model.getSquareValue(wallPositions.get("front")) == DungeonModel.SquareValue.KEY){
                displayPolies.get("key").setVisible(true);
            }

            displayPolies.get("farTopWedge").setFill(defaultColors.get("farTopWedge"));
            displayPolies.get("farBottomWedge").setFill(defaultColors.get("farBottomWedge"));
            displayPolies.get("farEnd").setFill(defaultColors.get("farEnd"));
            if (model.getSquareValue(wallPositions.get("frontLeft")) == DungeonModel.SquareValue.WALL){
                displayPolies.get("farLeftWedge").setFill(defaultColors.get("farLeftWedge"));
            } else {
                displayPolies.get("farLeftWedge").setFill(this.OPEN_COLOR);
            }
            if (model.getSquareValue(wallPositions.get("frontRight")) == DungeonModel.SquareValue.WALL){
                displayPolies.get("farRightWedge").setFill(defaultColors.get("farRightWedge"));
            } else {
                displayPolies.get("farRightWedge").setFill(this.OPEN_COLOR);
            }
        }
    }

    private void BGHideAll(){
        for (String key : displayPolies.keySet()){
            displayPolies.get(key).setVisible(false);
        }
    }

    private void BGSetAllDefaultColors(){
        for (String key : displayPolies.keySet()) {
            displayPolies.get(key).setFill(defaultColors.get(key));
        }
    }

    private void BGDrawWall(Shape wall, DungeonModel.SquareValue wallValue){
        wall.setVisible(true);
        if (wallValue == DungeonModel.SquareValue.WALL) {
            return;
        } else if (wallValue == DungeonModel.SquareValue.GOAL){
            wall.setFill(DOOR_COLOR);
        } else {
            wall.setFill(OPEN_COLOR);
        }
    }

    private void BGDrawCloseWall(Shape wall, DungeonModel.SquareValue wallValue){
        displayPolies.get("closeEnd").setVisible(true);
        if (wallValue == DungeonModel.SquareValue.WALL) {
            displayPolies.get("closeEnd").setFill(defaultColors.get("closeEnd"));
        } else if (wallValue == DungeonModel.SquareValue.GOAL){
            displayPolies.get("closeEnd").setFill(DOOR_COLOR);
            displayPolies.get("keyHole").setVisible(true);
        }
    }

    private void InitializeMap(){
        int rows = this.dungeonRows;
        int columns = this.dungeonColumns;
        double square_width = this.MAP_WIDTH/columns;
        double square_height = this.MAP_HEIGHT/rows;
        this.dungeonMap = new Rectangle[rows][columns];

        for(int row = 0; row < rows; row++){
            for (int column = 0; column<columns; column++){
                Rectangle curRect = new Rectangle();
                curRect.setX(MAP_LOC_X + column*square_width);
                curRect.setY(MAP_LOC_Y + row*square_height);
                curRect.setWidth(square_width);
                curRect.setHeight(square_height);
                this.dungeonMap[row][column] = curRect;
                this.getChildren().add(curRect);
            }
        }
    }

    private void updateMap(DungeonModel model){
        for(int row = 0; row < this.dungeonRows; row++){
            for (int column = 0; column<this.dungeonColumns; column++){
                DungeonModel.SquareValue curSquareVal = model.getSquareValue(row, column);
                Color curSquareColor;
                if (!model.isDiscovered(row, column)){
                    curSquareColor = Color.DARKSLATEGRAY;
                } else if (row == model.getPlayerRow() && column == model.getPlayerColumn()){
                    curSquareColor = Color.BLUE;
                }
                else if (curSquareVal == DungeonModel.SquareValue.WALL) {
                    curSquareColor = Color.valueOf("#404040");
                }
                else if (curSquareVal == DungeonModel.SquareValue.EMPTY) {
                    curSquareColor = Color.valueOf("#808080");
                } else if (curSquareVal == DungeonModel.SquareValue.KEY){
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

    public void update(DungeonModel model) {
        updateDungeonBG(model);
        updateMap(model);
    }
}
