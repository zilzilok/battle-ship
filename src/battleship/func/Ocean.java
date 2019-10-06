package battleship.func;

import battleship.func.ships.*;

public class Ocean {

    public static final int MIN_SIZE = 0;
    public static final int MAX_SIZE = 10;

    private Ship[][] ships; // Used to quickly determine which ship is in any given location
    private int shotsFired; // The total number of shots fired by the user
    private int hitCount;   // The number of times a shot hit a ship
    private int shipsSunk;  // The number of ships sunk (10 ships in all)

    public Ocean(){
        ships = new Ship[MAX_SIZE][MAX_SIZE];
        initializeShips();
    }

    public Ship[][] getShips() {
        return ships;
    }

    private void initializeShips(){
        for(var ships : getShips()){
            for(var ship : ships){
                ship = new EmptySea();
            }
        }
    }

    public boolean isOccupied(int row, int column){
        return !ships[row][column].getShipType().equals("EmptySea");
    }

    public void placeAllShipsRandomly(){
        int column, row;
        boolean horizontal;
        for (int i = 0; i < 10; i++) {
            try {
                column = Random.getIntNumber(MIN_SIZE, MAX_SIZE);
                row = Random.getIntNumber(MIN_SIZE, MAX_SIZE);

            }catch (ArrayIndexOutOfBoundsException ex){
                i--;
                continue;
            }
        }
    }


}
