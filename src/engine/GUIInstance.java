package engine;

import engine.Scene.Scene;

public interface GUIInstance {
    void drawGui();

    boolean handleGuiInput(Scene scene, Window window);
}
