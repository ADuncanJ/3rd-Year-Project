import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
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

        camera.setNearClip(0);
        camera.setFarClip(1000);

        Transform panRight = new Rotate(1, new Point3D(0, 1, 0));
        Transform panLeft = new Rotate(1, new Point3D(0, 1, 0));

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W:
                    camera.translateZProperty().set(camera.getTranslateZ() + 10);
                    break;
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 10);
                    break;
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() + 10);
                    break;
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 10);
                    break;
                case SPACE:
                    camera.translateYProperty().set(camera.getTranslateY() - 10);
                    break;
                case C:
                    camera.translateYProperty().set(camera.getTranslateY() + 10);
                    break;
                case E:
                    camera.getTransforms().add(panRight);
                    break;
                case Q:
                    camera.getTransforms().add(panLeft);
                    break;
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