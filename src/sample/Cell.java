package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {

    private static int size = 30;

    private Rectangle cell;
    private Boolean alive;

    public Cell() {
        cell = new Rectangle(size, size);
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.GREY);
        alive = false;
    }

    public Rectangle getCell() {
        return cell;
    }

    public void makeAlive() {
        cell.setFill(Color.BLACK);
        alive = true;
    }

    public void kill() {
        cell.setFill(Color.WHITE);
        alive = false;
    }

    public Boolean isAlive() {
        return alive;
    }
}
