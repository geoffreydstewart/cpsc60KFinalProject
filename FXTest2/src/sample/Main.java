package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int TILE_SIZE = 90;
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private Parent createContent() {
        Pane root = new Pane();

        Shape gridShape = makeBoard();
        root.getChildren().add(gridShape);

        return root;
    }

    private Shape makeBoard() {
        Shape rectangle = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 7) + TILE_SIZE / 5);
                circle.setTranslateY(y * (TILE_SIZE + 7) + TILE_SIZE / 5);

                rectangle = Shape.subtract(rectangle, circle);
            }
        }
        rectangle.setFill(Color.BLUE);
        return rectangle;
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Test Gameboard");
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
