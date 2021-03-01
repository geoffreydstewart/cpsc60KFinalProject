package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
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
import sample.player.AbstractPlayer;
import sample.player.PlayerFactory;
import sample.player.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends Application {

    private static final int TILE_SIZE = 90;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    //TODO: this needs to be looked at
    private boolean redMove = true;
    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    AbstractPlayer player;

    private Stage mainStage;
    private Scene scene1;
    private Scene scene2;

    private Pane discRoot = new Pane();


    private Parent createContent() {
        Pane root = new Pane();
        root.getChildren().add(discRoot);

        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }

    private Shape makeGrid() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 7) + TILE_SIZE / 5);
                circle.setTranslateY(y * (TILE_SIZE + 7) + TILE_SIZE / 5);

                shape = Shape.subtract(shape, circle);
            }
        }
        shape.setFill(Color.BLUE);
        return shape;
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rectangle = new Rectangle(TILE_SIZE, (ROWS + 1 ) * TILE_SIZE);
            rectangle.setTranslateX(x * (TILE_SIZE + 7) + TILE_SIZE / 5);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setOnMouseEntered(i -> rectangle.setFill(Color.rgb(211, 211, 211, 0.3)));
            rectangle.setOnMouseExited((i -> rectangle.setFill(Color.TRANSPARENT)));

            final int column = x;
            rectangle.setOnMouseClicked(e -> {
                boolean gameEnded = placeDisc(new Disc(redMove), column);

                if (!gameEnded && player.isPlayerNeedsMoveChoice()) {
                    int opponentColumnChoice = player.getMoveChoice();
                    placeDisc(new Disc(redMove), opponentColumnChoice);
                }

            });
            list.add(rectangle);
        }
        return list;
    }

    private boolean placeDisc(Disc disc, int column) {
        int row = ROWS - 1;
        do {
            if (!getDisc(column, row).isPresent())
                break;

            row--;
        } while (row >= 0);

        if (row < 0)
            return false;

        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 7) + TILE_SIZE / 5);
        disc.setTranslateY(row * (TILE_SIZE + 7) + TILE_SIZE / 5);
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

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if (disc.red == redMove) {
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
        System.out.println("Winner: " + (redMove ? "RED" : "YELLOW"));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test");
        alert.setHeaderText("This is a test.");
        alert.setResizable(false);
        alert.setContentText("Select okay or cancel this alert.");
        alert.showAndWait();

        grid = new Disc[COLUMNS][ROWS];
        discRoot.getChildren().clear();
        mainStage.setScene(scene1);
    }

    private Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= COLUMNS
            || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[column][row]);
    }

    private static class Disc extends Circle {
        private final boolean red;
        public Disc(boolean red) {
            super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
            this.red = red;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

    @Override
    public void start(Stage stage) throws Exception{
        mainStage = stage;
        stage.setTitle("Test Gameboard");

        ListView<String> listView = new ListView<>();

        Map<Integer, String> playerTypes = PlayerType.getPlayerTypes();

        for (Map.Entry<Integer, String> p : playerTypes.entrySet()) {
            listView.getItems().add(p.getValue());
        }

        Label label1 = new Label("Welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            System.out.println("Executing...");

            player = PlayerFactory.getPlayer(selectedIndex, redMove ? 'R' : 'Y');

            mainStage.setScene(scene2);

        });

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, listView, button1);
        scene1 = new Scene(layout1, 200, 200);

        Button button2 = new Button("This scene rocks, but let's go back");
        button2.setOnAction(e -> mainStage.setScene(scene1));

        scene2 = new Scene(createContent());

        mainStage.setScene(scene1);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
