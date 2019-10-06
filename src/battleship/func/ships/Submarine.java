package battleship.func.ships;

public class Submarine extends Ship {
    public  Submarine(){
        setLength(1);
        setHit(new boolean[getLength()]);
    }

    public String getShipType(){
        return "Submarine";
    }
}
