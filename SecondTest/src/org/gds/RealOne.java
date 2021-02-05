package org.gds;

public class RealOne {

    public static void main(String[] args) {
        drawBoard();
    }

    private static void drawBoard() {
        char[][] grid = GameGridSingleton.getInstance().getGameGrid();

        System.out.println();
        System.out.println("---------------");
        for (int row = 0; row < grid.length; row++){
            System.out.print("|");
            for (int col = 0; col < grid[0].length; col++){
                System.out.print(grid[row][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }
}
