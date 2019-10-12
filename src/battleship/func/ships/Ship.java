package battleship.func.ships;

import battleship.func.Ocean;

public abstract class Ship {
    private int bowRow;         // the row (0 to 9) which contains the bow (front) of the ship
    private int bowColumn;      // the column (0 to 9) which contains the bow (front) of the ship
    private int length;         // the number of squares occupied by the ship
    private boolean horizontal; // true if the ship occupies a single row, false otherwise
    private boolean[] hit;      // an array of booleans telling whether that part of the ship has been hit
    private int numberOfHits;

    public int getBowRow() {
        return bowRow;
    }
    private void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }
    private void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
    private void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public int getLength() {
        return length;
    }
    void setLength(int length) {
        this.length = length;
    }

    public boolean[] getHit() { return hit; }
    void setHit(boolean[] hit){
        if (hit != null)
            this.hit = hit;
    }

    public int getNumberOfHits() { return numberOfHits; }
    void setNumberOfHits(int numberOfHits) { this.numberOfHits = numberOfHits; }

    public String getShipType(){
        return "Ship";
    }

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


    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
        if (ocean != null){
            if (horizontal)
                placeShipAtHorizontal(row, column, ocean);
            else
                placeShipAtVertical(row, column, ocean);
        }
    }

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

    public boolean isSunk(){
        for (boolean currHit : hit){
            if (!currHit)
                return false;
        }
        return true;
    }

    public String toString(){
        if (isSunk())
            return "x ";
        return "S ";
    }
}
