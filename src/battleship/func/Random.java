package battleship.func;

public class Random {

    private static java.util.Random rng = new java.util.Random();

    /**
     *
     * @param min minimal number for random
     * @param max maximal number for random
     * @return random number from min to max
     */
    public static int getIntNumber(int min, int max){
        return min + rng.nextInt(max - min + 1);
    }

    /**
     *
     * @return random boolean (true or false)
     */
    public static boolean getBoolean(){
        return rng.nextBoolean();
    }
}
