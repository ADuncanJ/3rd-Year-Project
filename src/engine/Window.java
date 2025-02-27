package engine;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.pmw.tinylog.Logger;

import java.util.concurrent.Callable;

/*Derived from:
 * A. H. Benjarano, “3D Game Development with LWJGL 3” https://ahbejarano.gitbook.io/lwjglgamedev*/
public class Window {

    private final long windowHandle;
    private int height;
    private Callable<Void> resizeFunc;
    private int width;
    private MouseInput mouseInput;

    public Window(String title, WindowOptions opts, Callable<Void> risezeFunc){
        this.resizeFunc = risezeFunc;
        if(!glfwInit()){
            throw new IllegalStateException("Unable to Initialise GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        if (opts.antiAliasing){
            glfwWindowHint(GLFW_SAMPLES, 4);
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        if(opts.compatibleProfile){
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        }else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }

        if (opts.width > 0 && opts.height > 0){
            this.width = opts.width;
            this.height = opts.height;
        }else{
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            width = vidMode.width();
            height = vidMode.height();
        }

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if(windowHandle == NULL){
            throw new RuntimeException("Failed to create the GFLW window");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));

        glfwSetErrorCallback((int errorCode, long msgPtr) -> Logger.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr)));

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            keyCallBack(key, action);
        });

        glfwMakeContextCurrent(windowHandle);

        if(opts.fps > 0){
            glfwSwapInterval(0);
        }else{
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowHandle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);
        width = arrWidth[0];
        height = arrWidth[0];

        mouseInput = new MouseInput(windowHandle);
    }

    public void cleanup(){
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if(callback != null){
            callback.free();
        }
    }

    public MouseInput getMouseInput(){
        return mouseInput;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public long getWindowHandle(){
        return windowHandle;
    }

    public boolean isKeyPressed(int keyCode){
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void keyCallBack(int key, int action){
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
            glfwSetWindowShouldClose(windowHandle, true);
        }
    }

    public void pollEvents(){
        glfwPollEvents();
    }

    protected void resized(int width, int height){
        this.width = width;
        this.height = height;
        try{
            resizeFunc.call();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        glfwSwapBuffers(windowHandle);
    }

    public boolean windowShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }

    public static class WindowOptions{
        public boolean antiAliasing;
        public boolean compatibleProfile;
        public int fps;
        public int height;
        public int width;
        public int ups = Engine.TARGET_UPS;
    }
}
