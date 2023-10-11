package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    public void start(Stage primaryStage) throws Exception{

        Sphere sphere = new Sphere(50);
        Box box = new Box();
        box.setWidth(110);
        box.setHeight(110);
        box.setDepth(110);
        Group group = new Group();
        group.getChildren().add(sphere);
        group.getChildren().add(box);

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        sphere.translateXProperty().set(500);
        sphere.translateYProperty().set(350);
        sphere.translateZProperty().set(-300);

        box.translateXProperty().set(600);
        box.translateYProperty().set(350);
        box.translateZProperty().set(400);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W:
                    group.translateZProperty().set(group.getTranslateZ() - 10);
                    break;
                case S:
                    group.translateZProperty().set(group.getTranslateZ() + 10);
            }
        });

        primaryStage.setTitle("3D Graphics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}