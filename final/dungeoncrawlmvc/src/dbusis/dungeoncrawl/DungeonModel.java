package dbusis.dungeoncrawl;

import java.util.*;

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
     * Meant to represent a single square in the dungeon
     */
    private class DungeonSquare {
        private boolean visible = false;
        private SquareValue contents;

        public DungeonSquare(SquareValue squareContents){
            this.contents = squareContents;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public SquareValue getContents() {
            return contents;
        }

        public void setContents(SquareValue contents) {
            this.contents = contents;
        }
    }

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

    private DungeonSquare[][] dungeonLayout;

    private boolean keyAcquired = false;
    private boolean exitReached = false;

    public boolean isKeyAcquired() {
        return keyAcquired;
    }

    public boolean isExitReached() {
        return exitReached;
    }

    private DungeonSquare[][] makeMaze(int rows, int cols){
        DungeonSquare[][] newMaze = new DungeonSquare[rows][cols];
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                newMaze[i][j] = new DungeonSquare(SquareValue.WALL);
            }
        }

        List<int[]> visitedPositions = new ArrayList<>();
        List<int[]> positionsToVisit = new ArrayList<>();
        int[] playerPos = chooseRandomEdgePos(rows, cols);
        positionsToVisit.add(playerPos);
        this.playerRow = playerPos[0];
        this.playerColumn = playerPos[1];

        while(positionsToVisit.size()>0){
            int[] curPos = positionsToVisit.remove(0);
            visitedPositions.add(curPos);

            if (emptyAdjacentSquares(curPos, newMaze)<=1) {
                newMaze[curPos[0]][curPos[1]].setContents(SquareValue.EMPTY);
                int[][] curAdjPos = getAdjacentPositions(curPos[0], curPos[1]);
                for (int[] pos: curAdjPos) {
                    if (isRealWall(pos[0], pos[1], newMaze) &&
                            !isPosInList(pos, visitedPositions) &&
                            !isPosInList(pos, positionsToVisit)) {
                        positionsToVisit.add(pos);
                    }
                }
            }
            Collections.shuffle(positionsToVisit);
        }

        int[] goalPos;
        int[] keyPos;
        do {
            goalPos = chooseRandomDeadEnd(newMaze);
        } while (Arrays.equals(goalPos, playerPos));
        newMaze[goalPos[0]][goalPos[1]].setContents(SquareValue.GOAL);
        do {
            keyPos = chooseRandomDeadEnd(newMaze);
        } while (Arrays.equals(keyPos, playerPos));
        newMaze[keyPos[0]][keyPos[1]].setContents(SquareValue.KEY);

        return newMaze;
    }

    private int[] chooseRandomEdgePos(int rows, int cols){
        Random rand = new Random();
        int whichWall = rand.nextInt(4);
        switch (whichWall) {
            case 0:
                return new int[] {0,rand.nextInt(cols)};
            case 1:
                return new int[] {rand.nextInt(rows),cols-1};
            case 2:
                return new int[] {rows-1,rand.nextInt(cols)};
            default:
                return new int[] {rand.nextInt(rows), 0};
        }
    }

    private int[] chooseRandomDeadEnd(DungeonSquare[][] maze){
        Random rand = new Random();
        boolean validSquare = false;
        int row;
        int col;
        do {
            row = rand.nextInt(maze.length);
            col = rand.nextInt(maze[0].length);
            if (maze[row][col].getContents()==SquareValue.EMPTY){
                if (emptyAdjacentSquares(new int[] {row, col}, maze) == 1){
                    validSquare = true;
                }
            }
        } while (!validSquare);
        return new int[] {row,col};
    }

    private int emptyAdjacentSquares (int[] startPos, DungeonSquare[][] maze){
        int[][] adjPos = getAdjacentPositions(startPos[0], startPos[1]);

        int adjEmpty = 0;
        for (int[] curAdjPos : adjPos){
            if (isRealWall(curAdjPos[0], curAdjPos[1], maze) &&
                    !(curAdjPos[0] == startPos[0] && curAdjPos[1] == startPos[1]) &&
                    maze[curAdjPos[0]][curAdjPos[1]].getContents() == SquareValue.EMPTY) {
                adjEmpty++;
            }
        }

        return adjEmpty;
    }

    private int[][] getAdjacentPositions(int row, int col){
        return new int[][] {{row-1,col},{row,col+1},{row+1,col},{row,col-1}};
    }

    private boolean isRealWall(int row, int col, DungeonSquare[][] maze){
        return !((row < 0 || row >= maze.length) || (col < 0 || col >= maze[0].length));
    }

    private boolean isPosInList(int[] pos, List<int[]> posList){
        for (int[] curPos : posList){
            if(curPos[0] == pos[0] && curPos[1] == pos[1]) {
                return true;
            }
        }
        return false;
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
            dungeonLayout[newRow][newColumn].setContents(SquareValue.EMPTY);
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
            return dungeonLayout[row][column].getContents();
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

        dungeonLayout[playerRow][playerColumn].setVisible(true);
        int[] leftSquare = curVisibleSquares.get("left");
        if (isPosValid(leftSquare)) dungeonLayout[leftSquare[0]][leftSquare[1]].setVisible(true);
        int[] rightSquare = curVisibleSquares.get("right");
        if (isPosValid(rightSquare)) dungeonLayout[rightSquare[0]][rightSquare[1]].setVisible(true);
        int[] frontSquare = curVisibleSquares.get("front");
        if (isPosValid(frontSquare)) dungeonLayout[frontSquare[0]][frontSquare[1]].setVisible(true);
        if (getSquareValue(curVisibleSquares.get("front")) != SquareValue.WALL) {
            int[] frontLeftSquare = curVisibleSquares.get("frontLeft");
            if (isPosValid(frontLeftSquare)) dungeonLayout[frontLeftSquare[0]][frontLeftSquare[1]].setVisible(true);
            int[] frontRightSquare = curVisibleSquares.get("frontRight");
            if (isPosValid(frontRightSquare)) dungeonLayout[frontRightSquare[0]][frontRightSquare[1]].setVisible(true);
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
            return dungeonLayout[squareRow][squareColumn].isVisible();
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
        this.dungeonLayout = this.makeMaze(10,10);
        playerDir = PlayerDirection.NORTH;
        updateDiscoveredSquares();
    }
}
