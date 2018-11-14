package dbusis.dungeoncrawl;

import java.util.HashMap;

/**
 * Class that acts as a model of a 10x10 dungeon. Each square in the dungeon can be empty,
 * a wall, the exit, or the key. Also holds information about player position and direction,
 * which squares have been discovered by the player, and whether the player has picked up
 * the key.
 *
 * @author Daniel Busis
 */
public class DungeonModel {
    /**
     * Possible contents of each dungeon square
     */
    public enum SquareValue {
        EMPTY, WALL, GOAL, KEY
    }

    /**
     * Directions the player can be facing
     */
    public enum PlayerDirection {
        NORTH, EAST, SOUTH, WEST
    }

    private PlayerDirection playerDir;
    private int playerRow;
    private int playerColumn;

    private SquareValue[][] dungeonLayout;
    private boolean[][] discoveredSquares;

    private boolean keyAcquired = false;
    private boolean exitReached = false;

    public boolean isKeyAcquired() {
        return keyAcquired;
    }

    public boolean isExitReached() {
        return exitReached;
    }

    /**
     * Changes stored player direction to one direction counter-clockwise.
     */
    public void rotatePlayerCounterclockwise(){
        if (this.exitReached) {
            return;
        }

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

    /**
     * Changes stored player direction to one direction clockwise.
     */
    public void rotatePlayerClockwise(){
        if (this.exitReached) {
            return;
        }

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

    /**
     * Moves the player into a new square, if that square is a valid destination.
     * @param forward Moves the player in the direction he is facing if true,
     *                in the opposite direction if false.
     */
    public void movePlayer(boolean forward){
        if (this.exitReached) {
            return;
        }

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
            this.exitReached = true;
        }
    }

    /**
     * Gets the contents of aa single square in the dungeon. Any square outside of
     * the bounds of the dungeon is treated as a wall.
     * @param row Row of the square whose contents is to be returned.
     * @param column Column of the square whose contents is to be returned.
     * @return The value of the square in position row, column.
     */
    public SquareValue getSquareValue(int row, int column) {
        if (isPosValid(new int[] {row, column})){
            return dungeonLayout[row][column];
        }
        else{
            return SquareValue.WALL;
        }
    }

    /**
     * Overload of getSquareValue to accept an int array.
     * @param pos Array of [row, column].
     * @return Value of the square in position row, column.
     */
    public SquareValue getSquareValue(int[] pos) {
        return getSquareValue(pos[0], pos[1]);
    }

    /**
     * Returns a hash map of the positions of the five squares potentially within the player's
     * field of view.
     * @return A hash map with keys "left", "front", "right", "frontLeft", "frontRight" matched
     * with [row, column] positions.
     */
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

    /**
     * Any squares that the player can see are set to be visible.
     * Player can see squares to the left, right, and front, and if
     * the square in front is empty, they can also see the squares to
     * the front left and the front right.
     */
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

    /**
     * Returns whether a square position is within the bounds of the map.
     * @param squarePos [row, column] position of a square.
     * @return true if the square is within the map, false otherwise
     */
    private boolean isPosValid(int[] squarePos){
        int row = squarePos[0];
        int column = squarePos[1];
        return (row>=0 && row<this.getDungeonRows() && column>=0 && column<this.getDungeonColumns());
    }

    /**
     * Returns wether a square position is marked as discovered.
     * @param squareRow The row of the square
     * @param squareColumn The column of the square
     * @return True if the square has been discovered, false otherwise.
     */
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

    /**
     * Constructor for the DungeonModel class. Currently, hard-codes a specific 10x10 dungeon.
     */
    public DungeonModel() {
        //this.dungeonLayout = new SquareValue[dungeonRows][dungeonColumns];
        this.discoveredSquares = new boolean[10][10];
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
