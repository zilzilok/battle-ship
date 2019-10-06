package battleship.func.ships;

import battleship.func.Ocean;

public abstract class Ship {
    private int bowRow;         // the row (0 to 9) which contains the bow (front) of the ship
    private int bowColumn;      // the column (0 to 9) which contains the bow (front) of the ship
    private int length;         // the number of squares occupied by the ship
    private boolean horizontal; // true if the ship occupies a single row, false otherwise
    private boolean[] hit;      // an array of booleans telling whether that part of the ship has been hit

    public int getBowRow() {
        return bowRow;
    }

    void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }

    public void setHit(boolean[] hit) {
        this.hit = hit;
    }

    public String getShipType(){
        return "Ship";
    }

    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean)
            throws ArrayIndexOutOfBoundsException {
        if (horizontal){
            for (int i = 0; i < getLength(); i++) {
                if (ocean.isOccupied(row, column + i))
                    return false;
            }
        } else{
            for (int i = 0; i < getLength(); i++) {
                if (ocean.isOccupied(row + i, column))
                    return false;
            }
        }
        return true;
    }

    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean){
        if (okToPlaceShipAt(row, column, horizontal, ocean)){
            if (horizontal){
                for (int i = 0; i < getLength(); i++) {
                    setBowColumn(column);
                    setBowRow(row);
                    setHorizontal(true);
                    ocean.getShips()[row][column + i] = this;
                }
            } else{
                for (int i = 0; i < getLength(); i++) {
                    setBowColumn(column);
                    setBowRow(row);
                    setHorizontal(false);
                    ocean.getShips()[row + i][column] = this;
                }
            }
        }
    }

    public boolean shootAt(int row, int column){
        if (isHorizontal()){
            if (getBowRow() == row && column >= getBowColumn() && column <= getBowColumn() + getLength() && !isSunk())
                return  true;
        } else {
            if (getBowColumn() == column && row >= getBowRow() && row <= getBowRow() + getLength() && !isSunk())
                return  true;
        }
        return false;
    }

    public boolean isSunk(){
        for (boolean currHit : hit){
            if (!currHit)
                return false;
        }
        return  true;
    }

    public String toString(){
        if (isSunk())
            return "x";
        return "s";
    }
}
