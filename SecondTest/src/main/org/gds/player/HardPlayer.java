package org.gds.player;

import org.gds.GameBoard;
import org.gds.RealOne;

/**
 * This is a Concrete class in the Template design pattern
 */
public class HardPlayer extends AbstractPlayer {

    private static final int MAX_WINNING_SCORE = 999999;
    private static final int MIN_WINNING_SCORE = -999999;

    private static final int DEPTH = 8;

    @Override
    public int getMoveChoice(char playerColor) {
        //TODO: constant for Depth
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
     * @param playerColor the colour of the counter this player is using
     * @param alpha best score found so far by this player (always the maximising player)
     * @param beta best score found so far by opponent (the minimising player)
     * @param depth the number of moves it is looking ahead
     */
    private int alphaBetaSearch(int alpha, int beta, int depth) {
        int[] decision = maxValue(new GameBoard(gameState.getGameGrid()), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        return decision[0];
    }

    private int[] maxValue(GameBoard board, int depth, int alpha, int beta) {
        int score = calcScore(board.getGameGrid());

        if (finished(depth, board, score)) {
            return new int[]{-1, score};
        }

        int[] max = new int[]{-1, 0};

        for (int column = 0; column < GameBoard.NUM_COLS; column++) {
            GameBoard newBoard = new GameBoard(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                newBoard.playMove(RealOne.YELLOW, column);
                int[] next = minValue(newBoard, depth + 1, alpha, beta);

                if (max[0] == -1 || next[1] > max[1]) {
                    max[0] = column;
                    max[1] = next[1];
                    alpha = next[1];
                }

                if(beta <= alpha)
                    return max;
            }
        }

        return max;
    }

    private int[] minValue(GameBoard board, int depth, int alpha, int beta) {
        int score = calcScore(board.getGameGrid());

        if (finished(depth, board, score))
            return new int[]{-1, score};

        int[] min = new int[]{-1, 0};

        for (int column = 0; column < GameBoard.NUM_COLS; column++) {
            GameBoard newBoard = new GameBoard(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                newBoard.playMove(RealOne.RED, column);
                int[] next = maxValue(newBoard, depth + 1, alpha, beta);

                if (min[0] == -1 || next[1] < min[1]) {
                    min[0] = column;
                    min[1] = next[1];
                    beta = next[1];
                }

                if(beta <= alpha)
                    return min;
            }
        }
        return min;
    }

    public boolean finished(int depth, GameBoard gameBoard, int score)
    {
        boolean isFinished = depth >= DEPTH || gameBoard.noMoreTurns() ||
            score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
        return isFinished;
    }

    public int calcScore(char[][] grid)
    {
        int vertical_points=0, horizontal_points=0, descDiagonal_points=0, ascDiagonal_points=0, total_points=0;

        for (int row = 0; row < GameBoard.NUM_ROWS - 3; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS; column++) {
                int tempScore = calcScorePosition(grid, row, column, 1, 0);
                vertical_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < GameBoard.NUM_ROWS ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 3; column++) {
                int tempScore = calcScorePosition(grid, row, column, 0, 1);
                horizontal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < GameBoard.NUM_ROWS - 3 ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 3; column++) {
                int tempScore = calcScorePosition(grid, row, column, 1, 1);
                descDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 3; row < GameBoard.NUM_ROWS  ; row++) {
            for (int column = 0; column < GameBoard.NUM_COLS - 4; column++) {
                int tempScore = calcScorePosition(grid, row, column, -1, 1);
                ascDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        total_points = vertical_points + horizontal_points + descDiagonal_points + ascDiagonal_points;
        return total_points;
    }

    private int calcScorePosition(char[][] grid, int row, int column, int increment_row, int increment_col)
    {
        int ai_points = 0, player_points = 0;

        for (int i = 0; i < 4; i++) //connect "4"
        {
            if(grid[column][row] == RealOne.YELLOW)
            {
                ai_points++;
            }
            else if (grid[column][row] == RealOne.RED)
            {
                player_points++;
            }

            row += increment_row;
            column += increment_col;
        }

        if (player_points == 4) {
            return MIN_WINNING_SCORE;
        }
        else if (ai_points == 4) {
            return MAX_WINNING_SCORE;
        }
        else {
            return ai_points;
        }
    }

}
