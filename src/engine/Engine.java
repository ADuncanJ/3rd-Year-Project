package engine;

import engine.graph.Render;
import engine.Scene.Scene;
/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Engine {

    public static final int TARGET_UPS = 30;
    private final Logic logic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int targetFPS;
    private int targetUPS;

    public Engine(String windowTitle, Window.WindowOptions opts, Logic logic){
        window = new Window(windowTitle, opts, () -> {resize(); return null;});
        targetFPS = opts.fps;
        targetUPS = opts.ups;
        this.logic = logic;
        render = new Render(window);
        scene = new Scene(window.getWidth(), window.getHeight());
        logic.init(window, scene, render);
        running = true;
    }

    private void cleanup(){
        logic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize(){
        int width = window.getWidth();
        int height = window.getHeight();
        scene.resize(width, height);
        render.resize(width, height);
    }

    private void run(){
        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUPS;
        float timeR = targetFPS > 0 ? 1000.0f / targetFPS : 0;
        float deltaUpdate = 0;
        float deltaFPS = 0;

        long updateTime = initialTime;
        GUIInstance guiInstance = scene.getGuiInstance();
        while (running && !window.windowShouldClose()){
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;
            deltaFPS += (now - initialTime) / timeR;

            if (targetFPS <= 0 || deltaFPS >= 1){
                window.getMouseInput().input();
                boolean inputConsumed = guiInstance != null ? guiInstance.handleGuiInput(scene, window) : false;
                logic.input(window, scene, now - initialTime, inputConsumed);
            }

            if (deltaUpdate >= 1){
                long diffTimeMillis = now - updateTime;
                logic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate --;
            }

            if(targetFPS <= 0 || deltaFPS >= 1){
                render.render(window, scene);
                deltaFPS --;
                window.update();
            }
            initialTime = now;
        }
        cleanup();
    }

    public void start(){
        running = true;
        run();
    }

    public void stop(){
        running = false;
    }
}
