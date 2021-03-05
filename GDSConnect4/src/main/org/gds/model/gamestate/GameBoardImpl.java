package org.gds.model.gamestate;

import org.gds.Constants;
import org.gds.model.disc.Disc;
import org.gds.model.disc.UIDisc;
import org.gds.model.disc.VirtualDisk;
import org.gds.model.player.IntegerTuple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is the Real Subject in the Proxy Design Pattern
 */
public class GameBoardImpl implements GameBoard{

    private static final int MAX_TURNS = 42;

    public static final int UNPLACED_ROW = -1;

    private Disc[][] gameGrid = new Disc[Constants.COLUMNS][Constants.ROWS];

    private boolean redMove = true;
    private int numTurns = 0;

    public GameBoardImpl() {
    }

    public GameBoardImpl(Disc[][] currentGameGrid) {
        for (int col = 0; col < currentGameGrid.length; col++) {
            for (int row = 0; row < currentGameGrid[0].length; row++) {
                if (Optional.ofNullable(currentGameGrid[col][row]).isPresent()) {
                    gameGrid[col][row] = new VirtualDisk(currentGameGrid[col][row].isRed());
                }
                gameGrid[col][row] = currentGameGrid[col][row];
            }
        }
    }

    public boolean isRedMove() {
        return redMove;
    }

    public void toggleRedMove() {
        redMove = !redMove;
    }

    public Disc[][] getGameGrid() {
        return gameGrid;
    }

    public void reset() {
        numTurns = 0;
        redMove = true;
        gameGrid = new Disc[Constants.COLUMNS][Constants.ROWS];
    }

    public int placeDisc(Disc disc, int column) {
        int row = Constants.ROWS - 1;
        while (row >= 0) {
            if (getDisc(column, row).isEmpty())
                break;
            row--;
        }

        if (row < 0)
            return UNPLACED_ROW;

        gameGrid[column][row] = disc;
        numTurns++;
        return row;
    }

    public boolean isValidMove(int columnChoice) {
        if (columnChoice < 0 || columnChoice >= gameGrid.length) {
            return false;
        }
        return Optional.ofNullable(gameGrid[columnChoice][0]).isEmpty();
    }

    public boolean isGameOver(int column, int row) {
        if (noMoreTurns()) {
            return true;
        }
        return isWinner(column, row);
    }

    public boolean noMoreTurns() {
        return numTurns >= MAX_TURNS;
    }

    private boolean isWinner(int column, int row) {
        List<IntegerTuple> vertical = IntStream.rangeClosed(row - 3, row + 3)
            .mapToObj(r -> new IntegerTuple(column, r))
            .collect(Collectors.toList());

        List<IntegerTuple> horizontal = IntStream.rangeClosed(column - 3, column + 3)
            .mapToObj(c -> new IntegerTuple(c, row))
            .collect(Collectors.toList());

        IntegerTuple topLeft = new IntegerTuple(column - 3, row - 3);
        List<IntegerTuple> diagonal1 = IntStream.rangeClosed(0, 6)
            .mapToObj(i -> topLeft.add(i, i))
            .collect(Collectors.toList());

        IntegerTuple botLeft = new IntegerTuple(column - 3, row + 3);
        List<IntegerTuple> diagonal2 = IntStream.rangeClosed(0, 6)
            .mapToObj(i -> botLeft.add(i, -i))
            .collect(Collectors.toList());

        return checkRange(vertical) || checkRange(horizontal)
            || checkRange(diagonal1) || checkRange(diagonal2);
    }

    private boolean checkRange(List<IntegerTuple> points) {
        int chain = 0;

        for (IntegerTuple p : points) {
            int column = p.x;
            int row = p.y;

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

        return Optional.ofNullable(gameGrid[column][row]);
    }
}
