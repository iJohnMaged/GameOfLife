package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        GameOfLife game = new GameOfLife(20, 20);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(game.getScene());
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        game.start(400);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
