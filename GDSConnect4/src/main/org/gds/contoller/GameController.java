package org.gds.contoller;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.gds.Constants;
import org.gds.model.disc.Disc;
import org.gds.model.GameBoard;
import org.gds.model.GameState;
import org.gds.contoller.player.AbstractPlayer;
import org.gds.contoller.player.PlayerFactory;

public class GameController {

    AbstractPlayer opponentPlayer;
    final Pane discPane;

    public GameController(Pane discPane) {
        this.discPane = discPane;
    }

    public void initializeOpponentPlayer(int playerIndex) {
        // RED always goes first, the opponent is YELLOW
        opponentPlayer = PlayerFactory.getPlayer(playerIndex, Constants.YELLOW);
    }

    public boolean placeDisc(Disc disc, int column) {
        GameState gameState = GameState.getInstance();
        int rowPosition = gameState.placeDisc(disc, column);
        if (rowPosition == GameBoard.UNPLACED_ROW) {
            return false;
        }
        discPane.getChildren().add((Node) disc);
        disc.setTranslateX(column * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        disc.setTranslateY(rowPosition * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        boolean gameOver = gameState.isGameOver(column, rowPosition);
        if (!gameOver)
            gameState.toggleRedMove();
        return gameOver;
    }

    public boolean isComputerOpponent() {
        return opponentPlayer.isPlayerMustChooseMove();
    }

    public boolean placeComputerOpponentDisc(Disc disc) {
        GameState gameState = GameState.getInstance();
        int opponentColumnChoice = opponentPlayer.getColumnChoice();
        if (opponentColumnChoice == AbstractPlayer.INVALID_COLUMN_CHOICE)
            return false;

        int rowPosition = gameState.placeDisc(disc, opponentColumnChoice);
        if (rowPosition == GameBoard.UNPLACED_ROW)
            return false;

        discPane.getChildren().add((Node) disc);
        disc.setTranslateX(opponentColumnChoice * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        disc.setTranslateY(rowPosition * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        boolean gameOver = gameState.isGameOver(opponentColumnChoice, rowPosition);
        if (!gameOver)
            gameState.toggleRedMove();
        return gameOver;
    }

    public void resetGameState() {
        GameState.getInstance().reset();
        discPane.getChildren().clear();
    }
}
