package battleship;

import battleship.func.Ocean;
import battleship.func.ships.ShipException;

import java.util.Scanner;

public class BattleshipGame {
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Ocean ocean = new Ocean();
        ocean.printWithShips();
        ocean.print();
        Scanner scanner = new Scanner(System.in);
        int row, column;
        String exit = "";
        while (!ocean.isGameOver() && !exit.equals("exit")){
            System.out.println("Write row and column: ");
            row = scanner.nextInt();
            column = scanner.nextInt();
            printInfo(ocean, row, column);
            System.out.println("To close the program, write \"exit\"");
            exit = scanner.nextLine();
        }
        System.out.println("Game is over. My congratulations <3");
    }

    /**
     * Print information about your shoot. Calls battleship.func.Ocean.print() too.
     * @param ocean current ocean
     * @param row current row
     * @param column current column
     */
    private static void printInfo(Ocean ocean, int row, int column){
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
}
