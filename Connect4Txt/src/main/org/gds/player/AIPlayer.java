package org.gds.player;

import org.gds.GameBoard;
import org.gds.Main;

/**
 * This is a Concrete class in the Template design pattern
 */
public class AIPlayer extends AbstractPlayer {

    private static final int MAX_WINNING_SCORE = 1000000;
    private static final int MIN_WINNING_SCORE = -1000000;

    private static final class IntegerTuple {
        public Integer x;
        public Integer y;
        public IntegerTuple(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

    private final int searchDepth;
    private final char opponentColor;

    public AIPlayer(int searchDepth, char playerColor) {
        super(playerColor);
        this.searchDepth = searchDepth;
        if (playerColor == Main.YELLOW)
            opponentColor = Main.RED;
        else
            opponentColor = Main.YELLOW;
    }

    @Override
    public int getMoveChoice() {
        int moveChoice = alphaBetaSearch(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        System.out.print("Computer player " + playerColor + " choose column: " + moveChoice);
        System.out.println();
        return moveChoice;
    }

    /**
     * Determine a move by maximising the minimum gain given by opponent. This is an implementation of the
     * alpha-beta search algorithm [1]
     * This is the top level function, which initiates the recursive calls to max_value and min_value
     *
     * [1] S. J. Russell and P. Norvig, Artificial Intelligence A Modern Approach, 3rd ed. Upper Saddle River, NJ:
     *     Prentice Hall, 2010, pp. 170
     * @param alpha best score found so far by this player (always the maximising player)
     * @param beta best score found so far by opponent (the minimising player)
     * @param depth the number of moves it has looked ahead so far
     */
    private int alphaBetaSearch(int alpha, int beta, int depth) {
        IntegerTuple decision = maxValue(new GameBoard(gameState.getGameGrid()), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return decision.x;
    }

    private IntegerTuple maxValue(GameBoard board, int depth, int alpha, int beta) {
        int score = eval(board.getGameGrid());

        if (finished(depth, board, score)) {
            return new IntegerTuple(-1, score);
        }

        IntegerTuple max = new IntegerTuple(-1, 0);

        for (int column = 0; column < GameBoard.NUM_COLS; column++) {
            GameBoard newBoard = new GameBoard(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                newBoard.playMove(playerColor, column);
                IntegerTuple next = minValue(newBoard, depth + 1, alpha, beta);

                if (max.x == -1 || next.y > max.y) {
                    max.x = column;
                    max.y = next.y;
                    alpha = next.y;
                }

                if(beta <= alpha)
                    return max;
            }
        }
        return max;
    }

    private IntegerTuple minValue(GameBoard board, int depth, int alpha, int beta) {
        int score = eval(board.getGameGrid());

        if (finished(depth, board, score))
            return new IntegerTuple(-1, score);

        IntegerTuple min = new IntegerTuple(-1, 0);

        for (int column = 0; column < GameBoard.NUM_COLS; column++) {
            GameBoard newBoard = new GameBoard(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                newBoard.playMove(opponentColor, column);
                IntegerTuple next = maxValue(newBoard, depth + 1, alpha, beta);

                if (min.x == -1 || next.y < min.y) {
                    min.x = column;
                    min.y = next.y;
                    beta = next.y;
                }

                if(beta <= alpha)
                    return min;
            }
        }
        return min;
    }

    public boolean finished(int depth, GameBoard gameBoard, int score) {
        return depth >= searchDepth || gameBoard.noMoreTurns() ||
            score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
    }

    /**
     * This is the evaluation, or heuristic function used by the alpha-beta search algorithm, specific
     * in this case, to Connect 4
     * @param grid
     * @return
     */
    public int eval(char[][] grid) {
        int horizontalPoints = 0, verticalPoints = 0, ascendDiagonalPoints = 0, descendDiagonalPoints = 0;

        for (int row = 0; row < GameBoard.NUM_ROWS ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 3; column++) {
                int tempScore = calcPositionScore(grid, row, column, 0, 1);
                horizontalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < GameBoard.NUM_ROWS - 3; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS; column++) {
                int tempScore = calcPositionScore(grid, row, column, 1, 0);
                verticalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 3; row < GameBoard.NUM_ROWS  ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 4; column++) {
                int tempScore = calcPositionScore(grid, row, column, -1, 1);
                ascendDiagonalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < GameBoard.NUM_ROWS - 3 ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 3; column++) {
                int tempScore = calcPositionScore(grid, row, column, 1, 1);
                descendDiagonalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        return horizontalPoints + verticalPoints + ascendDiagonalPoints + descendDiagonalPoints;
    }

    private int calcPositionScore(char[][] grid, int row, int column, int rowChange, int colChange) {
        int computerPoints = 0;
        int playerPoints = 0;

        for (int i = 0; i < 4; i++) {
            if(grid[row][column] == playerColor) {
                computerPoints++;
            }
            else if (grid[row][column] == opponentColor) {
                playerPoints++;
            }

            row += rowChange;
            column += colChange;
        }

        if (playerPoints == 4) {
            return MIN_WINNING_SCORE;
        }
        else if (computerPoints == 4) {
            return MAX_WINNING_SCORE;
        }
        else {
            return computerPoints;
        }
    }

}
