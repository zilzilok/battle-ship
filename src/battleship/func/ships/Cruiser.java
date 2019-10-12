package battleship.func.ships;

public class Cruiser extends Ship {
    public  Cruiser(){
        setLength(3);
        setHit(new boolean[getLength()]);
    }

    @Override
    public String getShipType(){
        return "Cruiser";
    }
}
