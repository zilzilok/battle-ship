package battleship;

import battleship.func.Ocean;
import battleship.func.ships.ShipException;

import java.util.Scanner;

public class BattleshipGame {
    /**
     * Main method of the program.
     * @param args arguments
     */
    public static void main(String[] args) {
        startGame();
        while (!ocean.isGameOver()){
            getCoordinate();
            printInfo(ocean);
        }
        System.out.println("Game is over. My congratulations <3");
    }

    private static int row = 0;
    private static int column = 0;
    private static Ocean ocean;

    /**
     * Gets row and column of the shoot correctly.
     */
    private static void getCoordinate(){
        Scanner scanner = new Scanner(System.in);
        boolean passed = false;
        do{
            System.out.println("Write row [0, 9]: ");
            try {
                row = Integer.parseInt(scanner.nextLine());
                if (row < 0 || row > 9)
                    throw new NumberFormatException();
                passed = true;
            } catch (NumberFormatException ex) {
                System.out.println("Input error!");
            }
        }while (!passed);
        passed = false;
        do{
            System.out.println("Write column [0, 9]: ");
            try {
                column = Integer.parseInt(scanner.nextLine());
                if (column < 0 || column > 9)
                    throw new NumberFormatException();
                passed = true;
            } catch (NumberFormatException ex) {
                System.out.println("Input error!");
            }
        }while (!passed);
    }

    /**
     * Prints information about your shoot. Calls battleship.func.Ocean.print() too.
     * @param ocean current ocean
     */
    private static void printInfo(Ocean ocean){
        if (ocean != null){
            try {
                if (ocean.shootAt(row, column))
                    System.out.println("You hit :)");
                else
                    System.out.println("You miss :(");
                ocean.print();
            } catch (ShipException ex){
                System.out.println(ex.getMessage());
                ocean.print();
            }
        }
    }

    /**
     * Initializes current Ocean and prints him.
     */
    private static void startGame(){
        ocean = new Ocean();
        ocean.print();
    }
}
