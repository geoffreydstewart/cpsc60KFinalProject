package org.gds;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.gds.disc.Disc;
import org.gds.disc.UIDisc;
import org.gds.gamestate.GameState;
import org.gds.player.AbstractPlayer;
import org.gds.player.PlayerFactory;
import org.gds.player.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends Application {

    AbstractPlayer player;

    private Stage mainStage;
    private Scene menuScene;
    private Scene gameScene;

    private boolean redMove = true;

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
            // System.out.println("Selected index was " + selectedIndex);
            player = PlayerFactory.getPlayer(selectedIndex, redMove ? Constants.RED : Constants.YELLOW);
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
        root.getChildren().add(GameState.getInstance().getDiskPane());
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
                boolean gameEnded = placeDisc(new UIDisc(redMove), column);
                int opponentColumnChoice = player.getColumnChoice();
                if (!gameEnded && (opponentColumnChoice != AbstractPlayer.DUMMY_COLUMN_CHOICE)) {
                    placeDisc(new UIDisc(redMove), opponentColumnChoice);
                }
            });
            columnHighlightBlocksList.add(rectangle);
        }
        return columnHighlightBlocksList;
    }

    private boolean placeDisc(Disc disc, int column) {
        int row = Constants.ROWS - 1;
        while (row >= 0) {
            if (getDisc(column, row).isEmpty())
                break;
            row--;
        }

        if (row < 0)
            return false;

        GameState gameState = GameState.getInstance();
        Disc[][] gameGrid = gameState.getGameGrid();
        Pane discPane = gameState.getDiskPane();
        gameGrid[column][row] = disc;
        discPane.getChildren().add((Node) disc);
        disc.setTranslateX(column * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        disc.setTranslateY(row * (Constants.TILE_SIZE + 7) + Constants.TILE_SIZE / 5);
        //if (gameState.isGameOver(redMove, column, row)) {
        if (gameEnded(column, row)) {
            gameOver();
            return true;
        }
        redMove = !redMove;
        return false;

        // TODO: there could be an animation with the piece being dropped.
    }

    private boolean gameEnded(int column, int row) {
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

        return checkRange(vertical) || checkRange(horizontal)
            || checkRange(diagonal1) || checkRange(diagonal2);
    }

    private boolean checkRange(List<Point2D> points) {
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

    private void gameOver() {
        String winningColor = redMove ? Constants.RED : Constants.YELLOW;
        String message = "Game Over. The Winner is " + winningColor + "!";
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(winningColor + " Won!");
        alert.setHeaderText(message);
        alert.setResizable(false);
        alert.setContentText("Select okay or close this to go back to the menu and start a new game.");
        alert.showAndWait();

        GameState gameState = GameState.getInstance();
        gameState.reset();
        mainStage.setScene(menuScene);
    }

    private Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= Constants.COLUMNS
            || row < 0 || row >= Constants.ROWS)
            return Optional.empty();

        Disc[][] gameGrid = GameState.getInstance().getGameGrid();
        return Optional.ofNullable(gameGrid[column][row]);
    }

}
