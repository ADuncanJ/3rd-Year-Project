package engine.graph;

import engine.Scene.Scene;
import engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;


public class Render {

    private SceneRender sceneRender;
    private GUIRender guiRender;
    private SkyBoxRender skyBoxRender;

    public Render(Window window){
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        sceneRender = new SceneRender();
        guiRender = new GUIRender(window);
        skyBoxRender = new SkyBoxRender();
    }

    public void cleanup(){
        sceneRender.cleanup();
        guiRender.cleanup();
    }

    public void render(Window window, Scene scene){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        skyBoxRender.render(scene);
        sceneRender.render(scene);
        guiRender.render(scene);
    }

    public void resize(int width, int height){
        guiRender.resize(width, height);
    }

}
