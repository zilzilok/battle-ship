package battleship.func;

import battleship.func.ships.*;

import javax.swing.*;

public class Ocean {

    public static final int MIN_SIZE = 0;
    public static final int MAX_SIZE = 10;

    private Ship[][] ships;
    private int shotsFired;
    private int hitCount;
    private int shipsSunk;

    private final Ship[] FLEET = {new Battleship(),
            new Cruiser(), new Cruiser(),
            new Destroyer(), new Destroyer(), new Destroyer(),
            new Submarine(), new Submarine(), new Submarine(), new Submarine()};

    /**
     * @return array of ships
     */
    public Ship[][] getShipArray() {
        return ships;
    }

    /**
     * @return number of fired shots
     */
    public int getShotsFired() { return shotsFired; }

    /**
     * @return number of hits
     */
    public int getHitCount() { return hitCount; }

    /**
     * @return number of sunk ships
     */
    public int getShipsSunk() { return shipsSunk; }

    public Ocean(){
        ships = new Ship[MAX_SIZE][MAX_SIZE];
        initializeShips();
        placeAllShipsRandomly();
    }

    /**
     * Initialize ships array.
     */
    private void initializeShips() {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                ships[i][j] = new EmptySea();
            }
        }
    }

    /**
     * @param row current row
     * @param column current column
     * @return true if the given location contains a ship (not EmptySea ship), false if it does not
     * @throws ArrayIndexOutOfBoundsException
     */
    public boolean isOccupied(int row, int column) throws ArrayIndexOutOfBoundsException {
        return !ships[row][column].getShipType().equals("EmptySea");
    }

    /**
     * @param rowBow current row
     * @param columnBow current column
     * @param horizontal current horizontal
     * @param length current length
     * @return true if there isn't ships around tops of the ship, false otherwise
     */
    private boolean isNotOccupiedAroundTops(int rowBow, int columnBow, boolean horizontal, int length) {
        if (horizontal)
            return isNotOccupiedAroundTopsHorizontal(rowBow, columnBow, length);
        else
            return isNotOccupiedAroundTopsVertical(rowBow, columnBow, length);
    }

    /**
     * Method battleships.func.Ocean.isNotOccupiedAroundTops for horizontal type of the ship.
     * @param rowBow current row
     * @param columnBow current column
     * @param length current length
     * @return true if there isn't ships around tops of the ship, false otherwise
     */
    private boolean isNotOccupiedAroundTopsHorizontal(int rowBow, int columnBow, int length) {
        boolean result = true;
        if (columnBow != 0) {
            result = result && !isOccupied(rowBow, columnBow - 1);
            if (rowBow != 0) result = result && !isOccupied(rowBow - 1, columnBow - 1);
            if (rowBow != MAX_SIZE - 1) result = result && !isOccupied(rowBow + 1, columnBow - 1);
        }
        if (columnBow + length - 1 != MAX_SIZE - 1) {
            result = result && !isOccupied(rowBow, columnBow + length);
            if (rowBow != 0) result = result && !isOccupied(rowBow - 1, columnBow + length);
            if (rowBow != MAX_SIZE - 1) result = result && !isOccupied(rowBow + 1, columnBow + length);
        }
        return result;
    }

    /**
     * Method battleships.func.Ocean.isNotOccupiedAroundTops for vertical type of the ship.
     * @param rowBow current row
     * @param columnBow current column
     * @param length current length
     * @return true if there isn't ships around tops of the ship, false otherwise
     */
    private boolean isNotOccupiedAroundTopsVertical(int rowBow, int columnBow, int length){
        boolean result = true;
        if (rowBow != 0) {
            result = result && !isOccupied(rowBow - 1, columnBow);
            if (columnBow != 0) result = result && !isOccupied(rowBow - 1, columnBow - 1);
            if (columnBow != MAX_SIZE - 1) result = result && !isOccupied(rowBow - 1, columnBow + 1);
        }
        if (rowBow + length - 1 != MAX_SIZE - 1) {
            result = result && !isOccupied(rowBow + length, columnBow);
            if (columnBow != 0) result = result && !isOccupied(rowBow + length, columnBow - 1);
            if (columnBow != MAX_SIZE - 1) result = result && !isOccupied(rowBow + length, columnBow + 1);
        }
        return result;
    }

    /**
     * @param rowBow current row
     * @param columnBow current column
     * @param horizontal current horizontal
     * @param length current length
     * @return true if there isn't ships around sides of the ship, false otherwise
     */
    private boolean isNotOccupiedSides(int rowBow, int columnBow, boolean horizontal, int length){
        if (horizontal)
            return isNotOccupiedSidesHorizontal(rowBow, columnBow, length);
        else
            return isNotOccupiedSidesVertical(rowBow, columnBow, length);
    }

    /**
     * Method battleships.func.Ocean.isNotOccupiedSides for horizontal type of the ship.
     * @param rowBow current row
     * @param columnBow current column
     * @param length current length
     * @return true if there isn't ships around sides of the ship, false otherwise
     */
    private boolean isNotOccupiedSidesHorizontal(int rowBow, int columnBow, int length){
        boolean result = true;
        boolean isTop = rowBow == 0;
        boolean isBottom = rowBow == MAX_SIZE - 1;
        for (int i = 0; i < length; i++) {
            result = result && !isOccupied(rowBow, columnBow + i);
            if (!isTop) result = result && !isOccupied(rowBow - 1, columnBow + i);
            if (!isBottom) result = result && !isOccupied(rowBow + 1, columnBow + i);
            if (!result) return false;
        }
        return result;
    }

    /**
     * Method battleships.func.Ocean.isNotOccupiedSides for vertical type of the ship.
     * @param rowBow current row
     * @param columnBow current column
     * @param length current length
     * @return true if there isn't ships around sides of the ship, false otherwise
     */
    private boolean isNotOccupiedSidesVertical(int rowBow, int columnBow, int length){
        boolean result = true;
        boolean isLeft = columnBow == 0;
        boolean isRight = columnBow == MAX_SIZE - 1;
        for (int i = 0; i < length; i++) {
            result = result && !isOccupied(rowBow + i, columnBow);
            if (!isLeft) result = result && !isOccupied(rowBow + i, columnBow - 1);
            if (!isRight) result = result && !isOccupied(rowBow + i, columnBow + 1);
            if (!result) return false;
        }
        return result;
    }

    /**
     *
     * @param rowBow current row
     * @param columnBow current column
     * @param length current length
     * @return true if there isn't ships around sides and tops of the ship, false otherwise
     * @return
     */
    public boolean isNotOccupied(int rowBow, int columnBow, boolean horizontal, int length) {
        return isNotOccupiedAroundTops(rowBow, columnBow, horizontal, length)
                && isNotOccupiedSides(rowBow, columnBow, horizontal, length);
    }

    /**
     * Place all ten ships randomly on the (initially empty) ocean.
     */
    public void placeAllShipsRandomly() {
        for (Ship ship : FLEET){
            placeShipRandomly(ship);
        }
    }

    /**
     * Place current ship randomly on the field (ships array)
     * @param ship current ship
     */
    public void placeShipRandomly(Ship ship){
        if (ship != null){
            while (true){
                int column = Random.getIntNumber(MIN_SIZE, MAX_SIZE - 1);
                int row = Random.getIntNumber(MIN_SIZE, MAX_SIZE - 1);
                boolean horizontal = Random.getBoolean();
                try{
                    if(!ship.okToPlaceShipAt(row, column, horizontal, this))
                        continue;
                    ship.placeShipAt(row, column, horizontal, this);
                    break;
                } catch (ShipException ex) {}
            }
        }
    }

    /**
     * @param row current row
     * @param column current column
     * @return Returns true if the given location contains a "real" ship, still afloat, (not an EmptySea),
     * false if it does not. In addition, this method updates the number of shots that have been fired, and the number of hits.
     * Note: If a location contains a "real" ship, shootAt should return true every time the user shoots at that same location.
     * Once a ship has been "sunk", additional shots at its location should return false.
     * @throws ShipException
     */
    public boolean shootAt(int row, int column) throws ShipException {
        shotsFired++;
        if (getShipArray()[row][column].getNumberOfHits() == getShipArray()[row][column].getLength())
            throw new ShipException("Ship has been already sunk!");
        if (getShipArray()[row][column].shootAt(row, column)){
            hitCount++;
            if (getShipArray()[row][column].isSunk()){
                shipsSunk++;
                throw new ShipException("Congrats! The ship has been sunk.");
            }
            return true;
        }
        return false;
    }

    /**
     * @return true if all ships have been sunk, otherwise false
     */
    public boolean isGameOver() { return getShipsSunk() == 10; }

    /**
     * Print the ocean with all ships.
     */
    public void printWithShips(){
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < MAX_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < MAX_SIZE; j++) {
                if (isOccupied(i, j))
                    System.out.print("\u25A2 ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
    }

    /**
     * Print the ocean.
     */
    public void print(){
        System.out.print("Number of hits/shots: " + getHitCount() + "/" + getShotsFired() + "\n");
        System.out.print("Number of ships sunk: " + getShipsSunk() + "\n");
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        Ship currShip;
        for (int i = 0; i < MAX_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < MAX_SIZE; j++) {
                currShip = ships[i][j];
                if (isOccupied(i, j)){
                    if (currShip.isHorizontal())
                        printHorizontal(j, currShip);
                    else
                        printVertical(i, currShip);
                } else
                    System.out.print(currShip);
            }
            System.out.println();
        }
    }

    /**
     * Print appropriate symbol for current ship with horizontal type.
     * @param column current column
     * @param currShip current ship
     */
    private void printHorizontal(int column, Ship currShip){
        if (currShip.getHit()[column - currShip.getBowColumn()])
            System.out.print(currShip);
        else
            System.out.print("- ");
    }

    /**
     * Print appropriate symbol for current ship with vertical type.
     * @param row current row
     * @param currShip current ship
     */
    private void printVertical(int row, Ship currShip){
        if (currShip.getHit()[row - currShip.getBowRow()])
            System.out.print(currShip);
        else
            System.out.print("- ");
    }
}
