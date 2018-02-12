package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {

    private static double size = 30;

    private Rectangle cell;
    private Boolean alive;
    private static int state = 0;

    public Cell() {
        cell = new Rectangle(size, size);
        cell.setFill(Color.WHITE);
        cell.setStroke(Color.GREY);
        alive = false;
    }

    public Rectangle getCell() {
        return cell;
    }

    public static void setState(int newState) {
        state = newState;
    }

    public static void setSize(double newSize) {
        size = newSize;
    }

    public void makeAlive() {
        switch (state) {
            case 0:
                cell.setFill(Color.BLACK);
                break;
            case 1:
                cell.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                break;
            case 2:
                cell.setFill(Color.color(Math.random(), Math.random(), Math.random()).grayscale());
                break;
            case 3:
                cell.setFill(Color.color(Math.random(), Math.random(), Math.random()).brighter());
                break;
            case 4:
                cell.setFill(Color.color(Math.random(), Math.random(), Math.random()).darker());
                break;
            case 5:
                cell.setFill(Color.color(Math.random(), Math.random(), Math.random()).saturate());
                break;
        }
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
