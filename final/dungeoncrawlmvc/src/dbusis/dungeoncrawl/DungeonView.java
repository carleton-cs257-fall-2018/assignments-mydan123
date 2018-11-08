package dbusis.dungeoncrawl;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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
        leftWedge.getPoints().addAll(new Double[]{
                0.0, 0.0,
                25.0, 0.0,
                110.0, 25.0,
                110.0, 360.0,
                20.0, 450.0,
                0.0, 450.0
        });
        leftWedge.setFill(Color.valueOf("#999999"));
        this.displayPolies.put("leftWedge", leftWedge);
        this.defaultColors.put("leftWedge", Color.valueOf("#999999"));
        this.getChildren().add(leftWedge);

        Polygon rightWedge = new Polygon();
        rightWedge.getPoints().addAll(new Double[]{
                600.0, 0.0,
                575.0, 0.0,
                490.0, 25.0,
                490.0, 360.0,
                580.0, 450.0,
                600.0, 450.0
        });
        rightWedge.setFill(Color.valueOf("#999999"));
        this.displayPolies.put("rightWedge", rightWedge);
        this.defaultColors.put("rightWedge", Color.valueOf("#999999"));
        this.getChildren().add(rightWedge);

        Polygon topWedge = new Polygon();
        topWedge.getPoints().addAll(new Double[]{
                25.0, 0.0,
                110.0, 25.0,
                490.0, 25.0,
                575.0, 0.0
        });
        topWedge.setFill(Color.valueOf("#888888"));
        this.displayPolies.put("topWedge", topWedge);
        this.defaultColors.put("topWedge", Color.valueOf("#888888"));
        this.getChildren().add(topWedge);

        Polygon bottomWedge = new Polygon();
        bottomWedge.getPoints().addAll(new Double[]{
                20.0, 450.0,
                110.0, 360.0,
                490.0, 360.0,
                580.0, 450.0
        });
        bottomWedge.setFill(Color.valueOf("#777777"));
        this.displayPolies.put("bottomWedge", bottomWedge);
        this.defaultColors.put("bottomWedge", Color.valueOf("#777777"));
        this.getChildren().add(bottomWedge);

        Polygon smallLeftWedge = new Polygon();
        smallLeftWedge.getPoints().addAll(new Double[]{
                110.0, 25.0,
                140.0, 40.0,
                140.0, 320.0,
                110.0, 360.0
        });
        smallLeftWedge.setFill(Color.GREY);
        this.displayPolies.put("farLeftWedge", smallLeftWedge);
        this.defaultColors.put("farLeftWedge", Color.GREY);
        this.getChildren().add(smallLeftWedge);

        Polygon smallRightWedge = new Polygon();
        smallRightWedge.getPoints().addAll(new Double[]{
                490.0, 25.0,
                460.0, 40.0,
                460.0, 320.0,
                490.0, 360.0
        });
        smallRightWedge.setFill(Color.GREY);
        this.displayPolies.put("farRightWedge", smallRightWedge);
        this.defaultColors.put("farRightWedge", Color.GREY);
        this.getChildren().add(smallRightWedge);

        Polygon smallTopWedge = new Polygon();
        smallTopWedge.getPoints().addAll(new Double[]{
                110.0, 25.0,
                140.0, 40.0,
                460.0, 40.0,
                490.0, 25.0
        });
        smallTopWedge.setFill(Color.valueOf("#707070"));
        this.displayPolies.put("farTopWedge", smallTopWedge);
        this.defaultColors.put("farTopWedge", Color.valueOf("#707070"));
        this.getChildren().add(smallTopWedge);

        Polygon smallBottomWedge = new Polygon();
        smallBottomWedge.getPoints().addAll(new Double[]{
                140.0, 320.0,
                110.0, 360.0,
                490.0, 360.0,
                460.0, 320.0
        });
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
    }

    private void updateDungeonBG(DungeonModel model) {
        DungeonModel.SquareValue[] openWalls = findOpenWalls(model);

        displayPolies.get("topWedge").setFill(defaultColors.get("topWedge"));
        displayPolies.get("bottomWedge").setFill(defaultColors.get("bottomWedge"));
        if (openWalls[0] == DungeonModel.SquareValue.WALL) {
            displayPolies.get("leftWedge").setFill(defaultColors.get("leftWedge"));
        } else {
            displayPolies.get("leftWedge").setFill(this.OPEN_COLOR);
        }
        if (openWalls[2] == DungeonModel.SquareValue.WALL) {
            displayPolies.get("rightWedge").setFill(defaultColors.get("rightWedge"));
        } else {
            displayPolies.get("rightWedge").setFill(this.OPEN_COLOR);
        }
        if (openWalls[1] == DungeonModel.SquareValue.WALL) {
            displayPolies.get("closeEnd").setVisible(true);
            displayPolies.get("closeEnd").setFill(defaultColors.get("closeEnd"));
            displayPolies.get("farLeftWedge").setVisible(false);
            displayPolies.get("farRightWedge").setVisible(false);
            displayPolies.get("farTopWedge").setVisible(false);
            displayPolies.get("farBottomWedge").setVisible(false);
            displayPolies.get("farEnd").setVisible(false);
        } else {
            displayPolies.get("closeEnd").setVisible(false);
            displayPolies.get("farLeftWedge").setVisible(true);
            displayPolies.get("farRightWedge").setVisible(true);
            displayPolies.get("farTopWedge").setVisible(true);
            displayPolies.get("farBottomWedge").setVisible(true);
            displayPolies.get("farEnd").setVisible(true);

            displayPolies.get("farTopWedge").setFill(defaultColors.get("farTopWedge"));
            displayPolies.get("farBottomWedge").setFill(defaultColors.get("farBottomWedge"));
            displayPolies.get("farEnd").setFill(defaultColors.get("farEnd"));
            if (openWalls[3] == DungeonModel.SquareValue.WALL){
                displayPolies.get("farLeftWedge").setFill(defaultColors.get("farLeftWedge"));
            } else {
                displayPolies.get("farLeftWedge").setFill(this.OPEN_COLOR);
            }
            if (openWalls[4] == DungeonModel.SquareValue.WALL){
                displayPolies.get("farRightWedge").setFill(defaultColors.get("farRightWedge"));
            } else {
                displayPolies.get("farRightWedge").setFill(this.OPEN_COLOR);
            }
        }
    }

    /**
     *
     * @param model
     * @return list in the form of whether the following cells are open:
     * [closeLeft, closeFront, closeRight, farLeft, farRight]
     */
    private DungeonModel.SquareValue[] findOpenWalls(DungeonModel model) {
        DungeonModel.SquareValue[] openWalls = new DungeonModel.SquareValue[5];
        int[][] squarePositions = new int[5][2]; //Each int pair is a location in the DungeonLayout
        if (model.getPlayerDirection() == DungeonModel.PlayerDirection.NORTH) {
            squarePositions[0] = new int[] {model.getPlayerRow(), model.getPlayerColumn()-1};
            squarePositions[1] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()};
            squarePositions[2] = new int[] {model.getPlayerRow(), model.getPlayerColumn()+1};
            squarePositions[3] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()-1};
            squarePositions[4] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()+1};
        } else if(model.getPlayerDirection() == DungeonModel.PlayerDirection.EAST) {
            squarePositions[0] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()};
            squarePositions[1] = new int[] {model.getPlayerRow(), model.getPlayerColumn()+1};
            squarePositions[2] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()};
            squarePositions[3] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()+1};
            squarePositions[4] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()+1};
        } else if(model.getPlayerDirection() == DungeonModel.PlayerDirection.SOUTH) {
            squarePositions[0] = new int[] {model.getPlayerRow(), model.getPlayerColumn()+1};
            squarePositions[1] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()};
            squarePositions[2] = new int[] {model.getPlayerRow(), model.getPlayerColumn()-1};
            squarePositions[3] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()+1};
            squarePositions[4] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()-1};
        } else if(model.getPlayerDirection() == DungeonModel.PlayerDirection.WEST) {
            squarePositions[0] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()};
            squarePositions[1] = new int[] {model.getPlayerRow(), model.getPlayerColumn()-1};
            squarePositions[2] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()};
            squarePositions[3] = new int[] {model.getPlayerRow()+1, model.getPlayerColumn()-1};
            squarePositions[4] = new int[] {model.getPlayerRow()-1, model.getPlayerColumn()-1};
        }

        for (int i=0; i<squarePositions.length; i++){
            openWalls[i] = model.getSquareValue(squarePositions[i][0],squarePositions[i][1]);
        }

        return openWalls;
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
                curRect.setFill(Color.GREEN);
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
                if (row == model.getPlayerRow() && column == model.getPlayerColumn()){
                    curSquareColor = Color.BLUE;
                }
                else if (curSquareVal == DungeonModel.SquareValue.WALL) {
                    curSquareColor = Color.valueOf("#404040");
                }
                else if (curSquareVal == DungeonModel.SquareValue.EMPTY) {
                    curSquareColor = Color.valueOf("#808080");
                } else {
                    curSquareColor = Color.ORANGE;
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
