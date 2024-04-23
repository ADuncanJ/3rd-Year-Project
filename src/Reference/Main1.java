package Reference;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main1 extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    public void start(Stage primaryStage) throws Exception{
        //creates shapes and sets their sizes
        Sphere sphere = new Sphere(50);
        Box box = new Box();
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/wood.jpeg")));
        //material.setSpecularColor(Color.WHITE);
        material.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/spec-map.jpeg")));

        Box table = new Box();

        table.setWidth(300);
        table.setDepth(300);
        table.setHeight(10);

        table.translateZProperty().set(400);
        table.translateXProperty().set(600);
        table.translateYProperty().set(400);



        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(600,350, 400));
        pointLight.setRotationAxis(Rotate.Z_AXIS);



        box.setWidth(110);
        box.setHeight(110);
        box.setDepth(110);
        box.setMaterial(material);
        //adds shapes to a group container
        Group group = new Group();
        group.getChildren().add(sphere);
        group.getChildren().add(box);
        group.getChildren().add(table);
        group.getChildren().add(pointLight);
        //group.getChildren().addAll(prepLight());

        //creates scene by adding group and adding camera
        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        //sets position of sphere
        sphere.translateXProperty().set(600);
        /*sphere.translateYProperty().set(350);
        sphere.translateZProperty().set(200);*/
        PhongMaterial glow = new PhongMaterial();
        glow.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/resources/ill-map.jpg")));
        sphere.setMaterial(glow);
        sphere.getTransforms().setAll(pointLight.getTransforms());
        sphere.rotateProperty().bind(pointLight.rotateProperty());
        sphere.rotationAxisProperty().bind(pointLight.rotationAxisProperty());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pointLight.setRotate(pointLight.getRotate() + 1);
            }
        };
        timer.start();
        //sets position of box
        box.translateXProperty().set(600);
        box.translateYProperty().set(350);
        box.translateZProperty().set(400);
        //sets camera clip boundaries
        camera.setNearClip(0.1);
        camera.setFarClip(1000);

        Transform panRight = new Rotate(1, new Point3D(0, 1, 0));
        Transform panLeft = new Rotate(-1, new Point3D(0, 1, 0));
        //camera controls
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
                    group.getTransforms().add(panRight);
                    break;
                case Q:
                    group.getTransforms().add(panLeft);
                    break;
            }
        });



        primaryStage.setTitle("3D Graphics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node[] prepLight(){
        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(600, 350, 200));
        pointLight.setRotationAxis(Rotate.Y_AXIS);

        Sphere sphere = new Sphere(10);
        sphere.getTransforms().setAll(pointLight.getTransforms());
        sphere.rotateProperty().bind(pointLight.rotateProperty());
        sphere.rotationAxisProperty().bind(pointLight.rotationAxisProperty());
        return new Node[]{pointLight, sphere};
    }
    public static void main(String[] args) {
        launch(args);
    }
}