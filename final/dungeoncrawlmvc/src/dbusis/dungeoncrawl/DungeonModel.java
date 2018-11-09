package dbusis.dungeoncrawl;

import java.util.HashMap;

public class DungeonModel {

    public enum SquareValue {
        EMPTY, WALL, GOAL, KEY
    }

    public enum PlayerDirection {
        NORTH, EAST, SOUTH, WEST
    }

    private PlayerDirection playerDir;
    private int playerRow;
    private int playerColumn;

    private SquareValue[][] dungeonLayout;
    private boolean[][] discoveredSquares;

    private boolean keyAcquired = false;

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
        } else if (getSquareValue(newRow,newColumn) == SquareValue.KEY) {
            playerRow = newRow;
            playerColumn = newColumn;
            this.keyAcquired = true;
            dungeonLayout[newRow][newColumn] = SquareValue.EMPTY;
        } else if (getSquareValue(newRow,newColumn) == SquareValue.GOAL && this.keyAcquired){
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }

    public SquareValue getSquareValue(int row, int column) {
        if (isPosValid(new int[] {row, column})){
            return dungeonLayout[row][column];
        }
        else{
            return SquareValue.WALL;
        }
    }

    public SquareValue getSquareValue(int[] pos) {
        return getSquareValue(pos[0], pos[1]);
    }

    public HashMap<String, int[]> getVisibleSquares() {
        HashMap<String, int[]> visibleSquares = new HashMap<>();

        if (this.getPlayerDirection() == DungeonModel.PlayerDirection.NORTH) {
            visibleSquares.put("left",new int[] {this.getPlayerRow(), this.getPlayerColumn()-1});
            visibleSquares.put("front",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()});
            visibleSquares.put("right",new int[] {this.getPlayerRow(), this.getPlayerColumn()+1});
            visibleSquares.put("frontLeft",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()-1});
            visibleSquares.put("frontRight",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()+1});
        } else if(this.getPlayerDirection() == DungeonModel.PlayerDirection.EAST) {
            visibleSquares.put("left",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()});
            visibleSquares.put("front",new int[] {this.getPlayerRow(), this.getPlayerColumn()+1});
            visibleSquares.put("right",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()});
            visibleSquares.put("frontLeft",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()+1});
            visibleSquares.put("frontRight",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()+1});
        } else if(this.getPlayerDirection() == DungeonModel.PlayerDirection.SOUTH) {
            visibleSquares.put("left",new int[] {this.getPlayerRow(), this.getPlayerColumn()+1});
            visibleSquares.put("front",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()});
            visibleSquares.put("right",new int[] {this.getPlayerRow(), this.getPlayerColumn()-1});
            visibleSquares.put("frontLeft",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()+1});
            visibleSquares.put("frontRight",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()-1});
        } else if(this.getPlayerDirection() == DungeonModel.PlayerDirection.WEST) {
            visibleSquares.put("left",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()});
            visibleSquares.put("front",new int[] {this.getPlayerRow(), this.getPlayerColumn()-1});
            visibleSquares.put("right",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()});
            visibleSquares.put("frontLeft",new int[] {this.getPlayerRow()+1, this.getPlayerColumn()-1});
            visibleSquares.put("frontRight",new int[] {this.getPlayerRow()-1, this.getPlayerColumn()-1});
        }

        return visibleSquares;
    }

    public void updateDiscoveredSquares(){
        HashMap<String, int[]> curVisibleSquares = getVisibleSquares();

        discoveredSquares[playerRow][playerColumn] = true;
        int[] leftSquare = curVisibleSquares.get("left");
        if (isPosValid(leftSquare)) discoveredSquares[leftSquare[0]][leftSquare[1]] = true;
        int[] rightSquare = curVisibleSquares.get("right");
        if (isPosValid(rightSquare)) discoveredSquares[rightSquare[0]][rightSquare[1]] = true;
        int[] frontSquare = curVisibleSquares.get("front");
        if (isPosValid(frontSquare)) discoveredSquares[frontSquare[0]][frontSquare[1]] = true;
        if (getSquareValue(curVisibleSquares.get("front")) != SquareValue.WALL) {
            int[] frontLeftSquare = curVisibleSquares.get("frontLeft");
            if (isPosValid(frontLeftSquare)) discoveredSquares[frontLeftSquare[0]][frontLeftSquare[1]] = true;
            int[] frontRightSquare = curVisibleSquares.get("frontRight");
            if (isPosValid(frontRightSquare)) discoveredSquares[frontRightSquare[0]][frontRightSquare[1]] = true;
        }
    }

    private boolean isPosValid(int[] squarePos){
        int row = squarePos[0];
        int column = squarePos[1];
        return (row>=0 && row<this.getDungeonRows() && column>=0 && column<this.getDungeonColumns());
    }

    public boolean isDiscovered(int squareRow, int squareColumn){
        if (isPosValid(new int[] {squareRow, squareColumn})) {
            return discoveredSquares[squareRow][squareColumn];
        } else {
            return false;
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

    public int getDungeonRows(){
        return this.dungeonLayout.length;
    }

    public int getDungeonColumns(){
        return this.dungeonLayout[0].length;
    }

    public DungeonModel(int dungeonRows, int dungeonColumns) {
        this.dungeonLayout = new SquareValue[dungeonRows][dungeonColumns];
        this.discoveredSquares = new boolean[dungeonRows][dungeonColumns];
        this.dungeonLayout = new SquareValue[][]{
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.GOAL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.WALL},
                {SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.EMPTY, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY},
                {SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.WALL, SquareValue.EMPTY, SquareValue.KEY, SquareValue.EMPTY}
        };
        playerDir = PlayerDirection.NORTH;
        playerRow = 9;
        playerColumn = 1;
        updateDiscoveredSquares();
    }
}
