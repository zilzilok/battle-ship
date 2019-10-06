package battleship.func;

public class Random {

    private static java.util.Random rng = new java.util.Random();

    public static int getIntNumber(int min, int max){
        return min + rng.nextInt(max - min + 1);
    }
}
