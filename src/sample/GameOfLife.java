package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.security.SecureRandom;
import java.util.Random;

public class GameOfLife {

    private Random gen = new SecureRandom();
    private GridPane root = new GridPane();
    private Cell[][] cells;
    private int cols, rows;
    private Scene scene;

    public GameOfLife(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        cells = new Cell[cols][rows];
        initializeBoard();
        scene = new Scene(root);
    }

    private void initializeBoard() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell();
                GridPane.setConstraints(cells[i][j].getCell(), i, j);
                root.getChildren().add(cells[i][j].getCell());

                if (gen.nextDouble() > 0.7) {
                    cells[i][j].makeAlive();
                }
            }
        }
//        cells[3][3].makeAlive();
//        cells[3][4].makeAlive();
//        cells[4][3].makeAlive();
//
//        cells[4][4].makeAlive();
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
        Timeline gameTimeline = new Timeline(new KeyFrame(Duration.millis(millis), ae -> {
            play();
        }));

        gameTimeline.setCycleCount(Animation.INDEFINITE);
        gameTimeline.play();
    }

}
