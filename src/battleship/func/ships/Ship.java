package battleship.func.ships;

import battleship.func.Ocean;

public abstract class Ship {
    private int bowRow;         // the row (0 to 9) which contains the bow (front) of the ship
    private int bowColumn;      // the column (0 to 9) which contains the bow (front) of the ship
    private int length;         // the number of squares occupied by the ship
    private boolean horizontal; // true if the ship occupies a single row, false otherwise
    private boolean[] hit;      // an array of booleans telling whether that part of the ship has been hit
    private int numberOfHits;

    /**
     * @return bow's row value
     */
    public int getBowRow() {
        return bowRow;
    }
    /**
     * Sets bow's row.
     * @param bowRow row to set
     */
    private void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    /**
     * @return bow's column value
     */
    public int getBowColumn() {
        return bowColumn;
    }
    /**
     * Sets bow's column.
     * @param bowColumn column to set
     */
    private void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    /**
     * @return gorizontal value (direction of ship)
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Sets horizontal direction of the ship.
     * @param horizontal horizontal to set
     */
    private void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * @return length of the ship
     */
    public int getLength() {
        return length;
    }
    /**
     * Sets length of the ship.
     * @param length length to set.
     */
    void setLength(int length) {
        this.length = length;
    }

    /**
     * @return array of hits
     */
    public boolean[] getHit() { return hit; }

    /**
     * Sets array of hits.
     * @param hit array of hits to set
     */
    void setHit(boolean[] hit){
        if (hit != null)
            this.hit = hit;
    }

    /**
     * @return number of hits
     */
    public int getNumberOfHits() { return numberOfHits; }

    /**
     * Sets number of hits.
     * @param numberOfHits quantity to set
     */
    void setNumberOfHits(int numberOfHits) { this.numberOfHits = numberOfHits; }

    /**
     * @return ship type
     */
    public String getShipType(){
        return "Ship";
    }

    /**
     * The ship must not overlap another ship, or touch another ship (vertically, horizontally, or diagonally),
     * and it must not "stick out" beyond the array.
     * Does not actually change either the ship or the Ocean, just says whether it is legal to do so.
     * @param row current row
     * @param column current column
     * @param horizontal current direction
     * @param ocean current ocean
     * @return true if it is okay to put a ship of this length with its bow in this location,
     * with the given orientation, and returns false otherwise
     * @throws ShipException
     * @throws ArrayIndexOutOfBoundsException
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean)
            throws ShipException, ArrayIndexOutOfBoundsException {
        if (ocean != null){
            if (horizontal){
                if (column + getLength() - 1 > 9)
                    throw new ShipException("The ship out of the field!");
            } else{
                if (row + getLength() - 1 > 9)
                    throw new ShipException("The ship out of the field");
            }
            return ocean.isNotOccupied(row, column, horizontal, getLength());
        }
        return false;
    }

    /**
     * "Puts" the vertical ship in the ocean.
     * @param row current row
     * @param column current column
     * @param horizontal current direction
     * @param ocean current ocean
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
        if (ocean != null){
            if (horizontal)
                placeShipAtHorizontal(row, column, ocean);
            else
                placeShipAtVertical(row, column, ocean);
        }
    }

    /**
     * "Puts" the horizontal ship in the ocean.
     * @param row current row
     * @param column current column
     * @param ocean current ocean
     */
    private void placeShipAtHorizontal(int row, int column, Ocean ocean){
        if (ocean != null){
            for (int i = 0; i < getLength(); i++) {
                setBowColumn(column);
                setBowRow(row);
                setHorizontal(true);
                ocean.getShipArray()[row][column + i] = this;
            }
        }
    }

    /**
     * "Puts" the vertical ship in the ocean.
     * @param row current row
     * @param column current column
     * @param ocean current ocean
     */
    private void placeShipAtVertical(int row, int column, Ocean ocean){
        if (ocean != null){
            for (int i = 0; i < getLength(); i++) {
                setBowColumn(column);
                setBowRow(row);
                setHorizontal(false);
                ocean.getShipArray()[row + i][column] = this;
            }
        }
    }

    /**
     * @param row current row
     * @param column current column
     * @return if a part of the ship occupies the given row and column, and the ship hasn't been sunk,
     * mark that part of the ship as "hit" (in the hit array, 0 indicates the bow) and return true, otherwise return false
     */
    public boolean shootAt(int row, int column) {
    if (!isSunk()) {
        if (horizontal){
            if (!hit[column - bowColumn]){
                numberOfHits++;
                hit[column - bowColumn] = true;
            }
        } else {
            if (!hit[row - bowRow]){
                numberOfHits++;
                hit[row - bowRow] = true;
            }
        }
        return true;
    }
    return false;
    }

    /**
     * @return true if every part of the ship has been hit, false otherwise
     */
    public boolean isSunk(){
        for (boolean currHit : hit){
            if (!currHit)
                return false;
        }
        return true;
    }

    /**
     * @return "x" if the ship has been sunk, "S" if it has not been sunk
     */
    public String toString(){
        if (isSunk())
            return "x ";
        return "S ";
    }
}
