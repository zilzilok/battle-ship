package battleship.func.ships;


public class EmptySea extends Ship{
    public EmptySea(){
        setLength(1);
    }

    @Override
    public boolean shootAt(int row, int column) {
        setNumberOfHits(getNumberOfHits() + 1);
        return false;
    }

    @Override
    public boolean isSunk() { return false; }

    @Override
    public String getShipType(){
        return "EmptySea";
    }

    @Override
    public String toString() {
        if (getNumberOfHits() > 0)
            return "+ ";
        return "- ";
    }
}
