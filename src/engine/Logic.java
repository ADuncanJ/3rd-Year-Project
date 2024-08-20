package engine;

import engine.Scene.Scene;
import engine.graph.Render;
/*Derived from:
* A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public interface Logic {

    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

    void update(Window window, Scene scene, long diffTimeMillis);

}
