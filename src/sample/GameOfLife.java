package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Random;

public class GameOfLife {

    private Random gen = new Random();
    private GridPane boardGrid = new GridPane();
    private Cell[][] cells;
    private int cols, rows;
    private Scene scene;
    private Slider slider;
    private Timeline gameTimeline;
    private VBox root;
    private HBox buttonsBox;

    public GameOfLife(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        Cell.setSize(600 / cols);

        slider = new Slider(0, 4, 1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        initializeButtons();
        root = new VBox(15, new Label("Animation Speed:"), slider, buttonsBox);
        root.setPadding(new Insets(15));
        root.setPrefWidth(650);
        root.setPrefHeight(650);
        root.setAlignment(Pos.CENTER);
        slider.setMaxWidth(root.getPrefWidth() / 2);

        resetBoard();
    }

    private void initializeButtons() {
        Button[] buttons = new Button[6];
        buttons[0] = new Button("Black");
        buttons[1] = new Button("Random Colors");
        buttons[2] = new Button("Grayscale Colors");
        buttons[3] = new Button("Bright Colors");
        buttons[4] = new Button("Dark Colors");
        buttons[5] = new Button("Saturated Colors");

        for (int i = 0; i < buttons.length; i++) {
            int state = i;
            buttons[i].setOnAction(e -> Cell.setState(state));
        }

        buttonsBox = new HBox(buttons);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(5);
    }

    private void resetBoard() {
        cells = new Cell[cols][rows];
        initializeBoard();
        root.getChildren().add(boardGrid);
        scene = new Scene(root);
    }

    private void initializeBoard() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell();
                GridPane.setConstraints(cells[i][j].getCell(), i, j);
                boardGrid.getChildren().add(cells[i][j].getCell());

                if (gen.nextDouble() > 0.7) {
                    cells[i][j].makeAlive();
                }
            }
        }

    }

    public Scene getScene() {
        return scene;
    }

    public void processCell(int[][] cellsStates) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = cells[i][j];
                int neighbors = cellsStates[i][j];

                if (cell.isAlive() && neighbors < 2) {
                    cell.kill();
                } else if (cell.isAlive() && (neighbors == 3 || neighbors == 2)) {
                    cell.makeAlive();
                } else if (cell.isAlive() && neighbors > 3) {
                    cell.kill();
                } else if (!cell.isAlive() && neighbors == 3) {
                    cell.makeAlive();
                }
            }
        }
    }

    public void play() {
        int[][] cellsStates = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Check the 8 neighbors
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int x = (((i + dx) % cols) + cols) % cols;
                        int y = (((j + dy) % rows) + rows) % rows;
                        if (dx == dy && dy == 0) continue;
                        if (cells[x][y].isAlive())
                            cellsStates[i][j]++;
                    }
                }
            }
        }
        processCell(cellsStates);
    }

    public void start(int millis) {
        gameTimeline = new Timeline(new KeyFrame(Duration.millis(millis), ae -> play()));
        slider.valueProperty().addListener(e -> gameTimeline.setRate(slider.getValue()));
        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();
    }

}
