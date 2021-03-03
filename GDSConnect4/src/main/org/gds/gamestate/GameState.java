package org.gds.gamestate;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import org.gds.Constants;
import org.gds.disc.Disc;
import org.gds.disc.UIDisc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameState {

    private static final int MAX_TURNS = 42;

    private Pane discPane = new Pane();
    private Disc[][] grid = new Disc[Constants.COLUMNS][Constants.ROWS];
    private int numTurns = 0;

    private static final GameState instance = new GameState();

    private GameState() {
    }

    public static GameState getInstance() {
        return instance;
    }

    public Disc[][] getGameGrid() {
        return grid;
    }
    
    public Pane getDiskPane() {
        return discPane;
    }

    public void reset() {
        grid = new Disc[Constants.COLUMNS][Constants.ROWS];
        discPane.getChildren().clear();
    }

    public boolean isGameOver(boolean redMove, int column, int row) {
        if (noMoreTurns()) {
            System.out.println("It's a tie!");
            return true;
        }
        return isWinner(redMove, column, row);
    }

    public boolean isWinner(boolean redMove, int column, int row) {
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
            .mapToObj(r -> new Point2D(column, r))
            .collect(Collectors.toList());

        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
            .mapToObj(c -> new Point2D(c, row))
            .collect(Collectors.toList());

        Point2D topLeft = new Point2D(column - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
            .mapToObj(i -> topLeft.add(i, i))
            .collect(Collectors.toList());

        Point2D botLeft = new Point2D(column - 3, row + 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
            .mapToObj(i -> botLeft.add(i, -i))
            .collect(Collectors.toList());

        return checkRange(vertical, redMove) || checkRange(horizontal, redMove)
            || checkRange(diagonal1, redMove) || checkRange(diagonal2, redMove);
    }

    private boolean checkRange(List<Point2D> points, boolean redMove) {
        int chain = 0;

        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new UIDisc(!redMove));

            if (disc.isRed() == redMove) {
                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }
        return false;
    }

    private Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= Constants.COLUMNS
            || row < 0 || row >= Constants.ROWS)
            return Optional.empty();

        Disc[][] gameGrid = GameState.getInstance().getGameGrid();
        return Optional.ofNullable(gameGrid[column][row]);
    }

    public void incrementTurns() {
        numTurns++;
    }

    public boolean noMoreTurns() {
        return numTurns >= MAX_TURNS;
    }
}
