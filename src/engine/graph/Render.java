package engine.graph;

import engine.Scene.Scene;
import engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL13.*;

/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Render {

    private SceneRender sceneRender;
    private GUIRender guiRender;
    private SkyBoxRender skyBoxRender;
    private ShadowRender shadowRender;

    public Render(Window window){
        GL.createCapabilities();
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        sceneRender = new SceneRender();
        guiRender = new GUIRender(window);
        skyBoxRender = new SkyBoxRender();
        shadowRender = new ShadowRender();
    }

    public void cleanup(){
        sceneRender.cleanup();
        guiRender.cleanup();
        skyBoxRender.cleanup();
        shadowRender.cleanup();
    }

    public void render(Window window, Scene scene){
        shadowRender.render(scene);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        skyBoxRender.render(scene);
        sceneRender.render(scene, shadowRender);
        guiRender.render(scene);
    }

    public void resize(int width, int height){
        guiRender.resize(width, height);
    }

}
