package battleship.func;

import battleship.func.ships.*;

import javax.swing.*;

public class Ocean {

    public static final int MIN_SIZE = 0;
    public static final int MAX_SIZE = 10;

    private Ship[][] ships; // Used to quickly determine which ship is in any given location
    private int shotsFired; // The total number of shots fired by the user
    private int hitCount;   // The number of times a shot hit a ship
    private int shipsSunk;  // The number of ships sunk (10 ships in all)

    private final Ship[] FLEET = {new Battleship(),
            new Cruiser(), new Cruiser(),
            new Destroyer(), new Destroyer(), new Destroyer(),
            new Submarine(), new Submarine(), new Submarine(), new Submarine()};

    public Ship[][] getShipArray() {
        return ships;
    }

    public int getShotsFired() { return shotsFired; }

    public int getHitCount() { return hitCount; }

    public int getShipsSunk() { return shipsSunk; }

    public Ocean(){
        ships = new Ship[MAX_SIZE][MAX_SIZE];
        initializeShips();
        placeAllShipsRandomly();
    }

    private void initializeShips() {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                ships[i][j] = new EmptySea();
            }
        }
    }

    public boolean isOccupied(int row, int column) throws ArrayIndexOutOfBoundsException {
        return !ships[row][column].getShipType().equals("EmptySea");
    }

    private boolean isNotOccupiedAroundTops(int rowBow, int columnBow, boolean horizontal, int length) {
        if (horizontal)
            return isNotOccupiedAroundTopsHorizontal(rowBow, columnBow, length);
        else
            return isNotOccupiedAroundTopsVertical(rowBow, columnBow, length);
    }

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

    private boolean isNotOccupiedSides(int rowBow, int columnBow, boolean horizontal, int length){
        if (horizontal)
            return isNotOccupiedSidesHorizontal(rowBow, columnBow, length);
        else
            return isNotOccupiedSidesVertical(rowBow, columnBow, length);
    }

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

    public boolean isNotOccupied(int rowBow, int columnBow, boolean horizontal, int length) {
        return isNotOccupiedAroundTops(rowBow, columnBow, horizontal, length)
                && isNotOccupiedSides(rowBow, columnBow, horizontal, length);
    }

    public void placeAllShipsRandomly() {
        for (Ship ship : FLEET){
            placeShipRandomly(ship);
        }
    }

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

    public boolean isGameOver() { return getShipsSunk() == 10; }

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

    private void printHorizontal(int column, Ship currShip){
        if (currShip.getHit()[column - currShip.getBowColumn()])
            System.out.print(currShip);
        else
            System.out.print("- ");
    }
    private void printVertical(int row, Ship currShip){
        if (currShip.getHit()[row - currShip.getBowRow()])
            System.out.print(currShip);
        else
            System.out.print("- ");
    }
}
