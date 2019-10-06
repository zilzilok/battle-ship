package battleship.func.ships;

public class Destroyer extends Ship {
    public  Destroyer(){
        setLength(2);
        setHit(new boolean[getLength()]);
    }

    public String getShipType(){
        return "Destroyer";
    }
}
