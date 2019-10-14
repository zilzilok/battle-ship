package battleship.func.ships;

public class Battleship extends Ship {
    public  Battleship(){
        setLength(4);
        setHit(new boolean[getLength()]);
    }

    @Override
    public String getShipType(){
        return "Battleship";
    }
}
