package engine;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private Vector2f currentPos;
    private Vector2f previousPos;
    private Vector2f dispVec;
    private boolean inWindow;
    private  boolean leftButtonPressed;
    private boolean rightButtonPressed;

    public MouseInput(long windowHandle){
        previousPos = new Vector2f(-1, -1);
        currentPos = new Vector2f();
        dispVec = new Vector2f();
        leftButtonPressed = false;
        rightButtonPressed = false;
        inWindow = false;

        glfwSetCursorPosCallback(windowHandle, (handle, xpos, ypos) -> {
            currentPos.x = (float) xpos;
            currentPos.y = (float) ypos;
        });
        glfwSetCursorEnterCallback(windowHandle, (handle, entered) -> inWindow = entered);
        glfwSetMouseButtonCallback(windowHandle, (handle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getCurrentPos(){
        return currentPos;
    }

    public Vector2f getDispVec(){
        return dispVec;
    }

    public boolean isLeftButtonPressed(){
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed(){
        return rightButtonPressed;
    }

    public void input(){
        dispVec.x = 0;
        dispVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow){
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX){
                dispVec.y = (float) deltax;
            }
            if(rotateY){
                dispVec.x = (float) deltay;
            }
        }
    }
}
