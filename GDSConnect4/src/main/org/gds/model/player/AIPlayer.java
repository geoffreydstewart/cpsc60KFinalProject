package org.gds.model.player;

import org.gds.Constants;
import org.gds.model.gamestate.GameBoard;
import org.gds.model.gamestate.GameBoardImpl;
import org.gds.model.gamestate.GameState;
import org.gds.model.disc.Disc;
import org.gds.model.disc.VirtualDisc;

/**
 * This is a Concrete class in the Template Method Design Pattern. 
 * 
 * This is also the class which implements the Artificially Intelligent computer opponent.
 * See the {@link #alphaBetaSearch} method below for details.
 */
public class AIPlayer extends AbstractPlayer {

    private static final int MAX_WINNING_SCORE = 1000000;
    private static final int MIN_WINNING_SCORE = -1000000;

    // Allowing a configurable search depth for the alpha-beta search algorithm
    // controls the number of future game states the algorithm will search.
    // A higher value results in a more difficult opponent.
    private final int searchDepth;
    String computerColor;
    String opponentColor;

    public AIPlayer(int searchDepth, String playerColor) {
        playerMustChooseMove = true;
        this.searchDepth = searchDepth;
        this.computerColor = playerColor;
        if (playerColor == Constants.YELLOW)
            opponentColor = Constants.RED;
        else
            opponentColor = Constants.YELLOW;
    }

    @Override
    public int makeChoice() {
        int moveChoice = alphaBetaSearch(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        return moveChoice;
    }

    /**
     * Determine a move by maximizing the minimum gain given by opponent. This is an implementation of the
     * Minimax search algorithm with the alpha-beta [1]
     * This is the top level function, which initiates the recursive calls to max_value and min_value
     *
     * [1] S. J. Russell and P. Norvig, Artificial Intelligence A Modern Approach, 3rd ed. Upper Saddle River, NJ:
     *     Prentice Hall, 2010, pp. 170
     * @param alpha best score found so far by this player (always the maximizing player)
     * @param beta best score found so far by opponent (the minimizing player)
     * @param depth the number of moves it has looked ahead so far
     */
    private int alphaBetaSearch(int alpha, int beta, int depth) {
        GameBoard gameBoard = new GameBoardImpl(GameState.getInstance().getGameGrid());
        IntegerTuple decision = maxValue(gameBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return decision.x;
    }

    private IntegerTuple maxValue(GameBoard board, int depth, int alpha, int beta) {
        int score = eval(board.getGameGrid());

        if (finished(depth, board, score)) {
            return new IntegerTuple(INVALID_COLUMN_CHOICE, score);
        }

        IntegerTuple max = new IntegerTuple(INVALID_COLUMN_CHOICE, 0);

        for (int column = 0; column < Constants.COLUMNS; column++) {
            GameBoardImpl newBoard = new GameBoardImpl(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                Disc disc = new VirtualDisc(computerColor == Constants.RED);
                newBoard.placeDisc(disc, column);
                IntegerTuple next = minValue(newBoard, depth + 1, alpha, beta);

                if (max.x == INVALID_COLUMN_CHOICE || next.y > max.y) {
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
            return new IntegerTuple(INVALID_COLUMN_CHOICE, score);

        IntegerTuple min = new IntegerTuple(INVALID_COLUMN_CHOICE, 0);

        for (int column = 0; column < Constants.COLUMNS; column++) {
            GameBoardImpl newBoard = new GameBoardImpl(board.getGameGrid());
            if (newBoard.isValidMove(column)) {
                Disc disc = new VirtualDisc(opponentColor == Constants.RED);
                newBoard.placeDisc(disc, column);
                IntegerTuple next = maxValue(newBoard, depth + 1, alpha, beta);

                if (min.x == INVALID_COLUMN_CHOICE || next.y < min.y) {
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
     * This is the evaluation, or heuristic function used by the alpha-beta pruning technique, specific
     * in this case, to Connect 4 [2]
     * 
     * [2] X. Kang, Y. Wang, Y Hu, "Research on Different Heuristics for Minimax Algorithm Insight from Connect-4 Game", 
     *     Journal of Intelligent Learning Systems and Applications, Volume 11, Number 2, 2019, Article ID: 90972.
     * @param grid
     * @return score - the heuristic score of this game state
     */
    public int eval(Disc[][] grid) {
        int horizontalPoints = 0, verticalPoints = 0, ascendDiagonalPoints = 0, descendDiagonalPoints = 0;

        for (int column = 0; column < Constants.COLUMNS - 3; column++) {
            for (int row = 0; row < Constants.ROWS ; row++) {
                int tempScore = calcPositionScore(grid, row, column, 0, 1);
                horizontalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int column = 0; column < Constants.COLUMNS; column++) {
            for (int row = 0; row < Constants.ROWS - 3; row++) {
                int tempScore = calcPositionScore(grid, row, column, 1, 0);
                verticalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int column = 0; column < Constants.COLUMNS - 4; column++) {
            for (int row = 3; row < Constants.ROWS  ; row++) {
                int tempScore = calcPositionScore(grid, row, column, -1, 1);
                ascendDiagonalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int column = 0; column < Constants.COLUMNS - 3; column++) {
            for (int row = 0; row < Constants.ROWS - 3 ; row++) {
                int tempScore = calcPositionScore(grid, row, column, 1, 1);
                descendDiagonalPoints += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        return horizontalPoints + verticalPoints + ascendDiagonalPoints + descendDiagonalPoints;
    }

    private int calcPositionScore(Disc[][] grid, int row, int column, int rowChange, int colChange) {
        int computerPoints = 0;
        int playerPoints = 0;

        for (int i = 0; i < 4; i++) {
            if((grid[column][row] != null &&
                ((grid[column][row].isRed() ? Constants.RED : Constants.YELLOW) == computerColor))) {
                computerPoints++;
            }
            else if ((grid[column][row] != null &&
                ((grid[column][row].isRed() ? Constants.RED : Constants.YELLOW) == opponentColor))) {
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
