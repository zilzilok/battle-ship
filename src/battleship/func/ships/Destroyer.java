package battleship.func.ships;

public class Destroyer extends Ship {
    public  Destroyer(){
        setLength(2);
        setHit(new boolean[getLength()]);
    }

    @Override
    public String getShipType(){
        return "Destroyer";
    }
}
