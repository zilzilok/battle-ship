package battleship.func.ships;


public class EmptySea extends Ship{
    public EmptySea(){
        setLength(1);
    }

    public boolean shootAt(int row, int column) { return false; }

    public boolean isSunk() { return false; }
}
