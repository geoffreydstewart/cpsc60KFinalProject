package org.gds;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.gds.model.disc.Disc;
import org.gds.model.disc.UIDisc;
import org.gds.model.gamestate.GameState;
import org.gds.model.player.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameView extends Application {

    private Stage mainStage;
    private Scene menuScene;
    private Scene gameScene;

    private final Pane discPane = new Pane();
    private final GameController gameController = new GameController(discPane);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        mainStage = stage;
        stage.setTitle("GDSConnect4");

        ListView<String> listView = new ListView<>();

        Map<Integer, String> playerTypes = PlayerType.getPlayerTypes();

        for (Map.Entry<Integer, String> p : playerTypes.entrySet()) {
            listView.getItems().add(p.getValue());
        }

        Label menuLabel = new Label("Welcome to GDSConnect4");
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            gameController.initializeOpponentPlayer(selectedIndex);
            mainStage.setScene(gameScene);
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(menuLabel, listView, startGameButton);
        menuScene = new Scene(layout, 325, 250);
        gameScene = new Scene(createContent());
        mainStage.setScene(menuScene);
        mainStage.show();
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.getChildren().add(discPane);
        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());
        return root;
    }

    private Shape makeGrid() {
        //Make a rectangle for the game board
        Shape shape = new Rectangle((Constants.COLUMNS + 1) * Constants.TILE_SIZE, (Constants.ROWS + 1) * Constants.TILE_SIZE);

        //Punch out holes in the game board
        for (int y = 0; y < Constants.ROWS; y++) {
            for (int x = 0; x < Constants.COLUMNS; x++) {
                Circle circle = new Circle(Constants.TILE_RADIUS);
                circle.setCenterX(Constants.TILE_RADIUS);
                circle.setCenterY(Constants.TILE_RADIUS);
                circle.setTranslateX(x * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
                circle.setTranslateY(y * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
                shape = Shape.subtract(shape, circle);
            }
        }
        shape.setFill(Color.BLUE);
        return shape;
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> columnHighlightBlocksList = new ArrayList<>();

        //Make each column selectable, for a UI player to choose the column for the move
        for (int x = 0; x < Constants.COLUMNS; x++) {
            Rectangle rectangle = new Rectangle(Constants.TILE_SIZE, (Constants.ROWS + 1 ) * Constants.TILE_SIZE);
            rectangle.setTranslateX(x * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setOnMouseEntered(i -> rectangle.setFill(Color.rgb(211, 211, 211, 0.3)));
            rectangle.setOnMouseExited((i -> rectangle.setFill(Color.TRANSPARENT)));

            final int column = x;
            rectangle.setOnMouseClicked(e -> {
                Disc disc = new UIDisc(GameState.getInstance().isRedMove());
                boolean gameEnded = gameController.placeDisc(disc, column);
                if (!gameEnded && gameController.isComputerOpponent()) {
                    Disc opponentDisc = new UIDisc(GameState.getInstance().isRedMove());
                    gameEnded = gameController.placeComputerOpponentDisc(opponentDisc);
                }
                if (gameEnded)
                    gameOver();
            });
            columnHighlightBlocksList.add(rectangle);
        }
        return columnHighlightBlocksList;
    }

    private void gameOver() {
        GameState gameState = GameState.getInstance();
        String message;
        String title;
        if (gameState.noMoreTurns()) {
            message = "Game Over. It's a tie!";
            title = "Tie Game!";
        } else {
            String winningColor = gameState.isRedMove() ? Constants.RED : Constants.YELLOW;
            message = "Game Over. The Winner is " + winningColor + "!";
            title = winningColor + " Won!";
        }
        System.out.println(message);
        Alert gameOverAlert = getGameOverAlert(message, title);
        gameOverAlert.showAndWait();
        gameController.resetGameState();
        mainStage.setScene(menuScene);
    }

    Alert getGameOverAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setResizable(false);
        alert.setContentText("Select okay or close this to go back to the menu and start a new game.");
        return alert;
    }

}
