package org.gds;

public class GameGridSingleton {

    private static char[][] gameGrid = new char[6][7];
    private static GameGridSingleton instance = new GameGridSingleton();

    private GameGridSingleton() {
        for (int row = 0; row < gameGrid.length; row++){
            for (int col = 0; col < gameGrid[0].length; col++){
                gameGrid[row][col] = ' ';
            }
        }
    }

    public static GameGridSingleton getInstance() {
        return instance;
    }

    public char[][] getGameGrid() {
        return gameGrid;
    }
}
