package battleship.func;

import battleship.func.ships.*;

public class Ocean {
    Ship[][] ships; // Used to quickly determine which ship is in any given location
    int shotsFired; // The total number of shots fired by the user
    int hitCount;   // The number of times a shot hit a ship
    int shipsSunk;  // The number of ships sunk (10 ships in all)

    public Ocean(){
        ships = new Ship[10][10];
        initializeShips();
    }

    private void initializeShips(){
        for(var _ships : ships){
            for(var ship : _ships){
                ship = new EmptySea();
            }
        }
    }
}
