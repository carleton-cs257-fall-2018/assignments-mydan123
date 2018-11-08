package dbusis.dungeoncrawl;

import java.util.Random;

public class DungeonModel {

    public enum SquareValue {
        EMPTY, WALL, GOAL
    };

    public enum PlayerDirection {
        NORTH, EAST, SOUTH, WEST
    };

    private PlayerDirection playerDir;
    private int playerRow;
    private int playerColumn;

    public void rotatePlayerCounterclockwise(){
        switch (playerDir){
            case NORTH:
                playerDir = PlayerDirection.WEST;
                break;
            case WEST:
                playerDir = PlayerDirection.SOUTH;
                break;
            case SOUTH:
                playerDir = PlayerDirection.EAST;
                break;
            case EAST:
                playerDir = PlayerDirection.NORTH;
                break;
        }
    }

    public void rotatePlayerClockwise(){
        switch (playerDir){
            case NORTH:
                playerDir = PlayerDirection.EAST;
                break;
            case EAST:
                playerDir = PlayerDirection.SOUTH;
                break;
            case SOUTH:
                playerDir = PlayerDirection.WEST;
                break;
            case WEST:
                playerDir = PlayerDirection.NORTH;
                break;
        }
    }

    public void movePlayer(boolean forward){
        int move_dir;
        if (forward) {
            move_dir = 1;
        }
        else{
            move_dir = -1;
        }

        int newRow;
        int newColumn;
        switch (playerDir){
            case NORTH:
                newRow = playerRow-move_dir;
                newColumn = playerColumn;
                break;
            case EAST:
                newRow = playerRow;
                newColumn = playerColumn+move_dir;
                break;
            case SOUTH:
                newRow = playerRow+move_dir;
                newColumn = playerColumn;
                break;
            default: //West
                newRow = playerRow;
                newColumn = playerColumn-move_dir;
                break;
        }

        if (getSquareValue(newRow,newColumn) == SquareValue.EMPTY) {
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }

    public SquareValue getSquareValue(int row, int column) {
        if (row>=0 && row<this.getDungeonRows() && column>=0 && column<this.getDungeonColumns()){
            return dungeonLayout[row][column];
        }
        else{
            return SquareValue.WALL;
        }
    }

    public PlayerDirection getPlayerDirection(){
        return playerDir;
    }

    public int getPlayerRow(){
        return playerRow;
    }

    public int getPlayerColumn(){
        return playerColumn;
    }

    private SquareValue[][] dungeonLayout;

    public int getDungeonRows(){
        return this.dungeonLayout.length;
    }

    public int getDungeonColumns(){
        return this.dungeonLayout[0].length;
    }

    public DungeonModel(int dungeonRows, int dungeonColumns) {
        this.dungeonLayout = new SquareValue[dungeonRows][dungeonColumns];
        this.dungeonLayout = new SquareValue[][]{
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.WALL},
                {SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY}
        };
        playerDir = PlayerDirection.NORTH;
        playerRow = 9;
        playerColumn = 1;
    }
}
