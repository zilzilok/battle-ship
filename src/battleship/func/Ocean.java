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

    private final Ship[] FLEET = {new Battleship(), new Cruiser(), new Cruiser(),
            new Destroyer(), new Destroyer(), new Destroyer(), new Submarine(), new Submarine(), new Submarine(), new Submarine()};

    public Ocean(){
        ships = new Ship[MAX_SIZE][MAX_SIZE];
        initializeShips();
        placeAllShipsRandomly();
    }

    public Ship[][] getShips() {
        return ships;
    }

    private void initializeShips() {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                ships[i][j] = new EmptySea();
            }

        }
    }

    public boolean isOccupied(int row, int column){
        return !ships[row][column].getShipType().equals("EmptySea");
    }

    public void placeAllShipsRandomly() {
        for (Ship ship : FLEET){
            placeShipRandomly(ship);
        }
    }

    public void placeShipRandomly(Ship ship){
        while (true){
            int column = Random.getIntNumber(MIN_SIZE, MAX_SIZE - 1);
            int row = Random.getIntNumber(MIN_SIZE, MAX_SIZE - 1);
            boolean horizontal = Random.getBoolean();
            try{
                ship.okToPlaceShipAt(row, column, horizontal, this);
                ship.placeShipAt(row, column, horizontal, this);
                break;
            } catch (ShipException ex) {}
        }
    }

    public void print(){
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < MAX_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < MAX_SIZE; j++) {
                if (isOccupied(i, j))
                    System.out.print("X ");
                else
                    System.out.print("O ");
            }
            System.out.println();
        }
    }
}
