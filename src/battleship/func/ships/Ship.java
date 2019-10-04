package battleship.func.ships;

public class Ship {
    int bowRow;         // the row (0 to 9) which contains the bow (front) of the ship
    int bowColumn;      // the column (0 to 9) which contains the bow (front) of the ship
    int length;         // the number of squares occupied by the ship
    boolean horizontal; // true if the ship occupies a single row, false otherwise
    boolean[] hit;      // an array of booleans telling whether that part of the ship has been hit

    public int getBowRow() {
        return bowRow;
    }

    public void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    public void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public int getLength() {
        return length;
    }

    public String getShipType(){
        return "Ship";
    }

    
}
