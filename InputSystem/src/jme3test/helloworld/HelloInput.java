package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import org.lwjgl.input.Mouse;

/**
 * Sample 5 - 将鼠标和按键映射到相应动作
 */
public class HelloInput extends SimpleApplication {

    public static void main(String[] args) {
        HelloInput app = new HelloInput();
        app.start();
    }

    protected Geometry player;
    private boolean isRunning = true;

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        player = new Geometry("Player", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
        initKeys(); // 自定义按键绑定
    }

    /**
     * 自定义按键绑定：将已命名的动作映射到某输入
     */
    private void initKeys() {
        flyCam.setEnabled(false); // 关闭第一人称视角的控制器
        inputManager.setCursorVisible(true);

        // 将一个或多个输入映射到已命名的动作（注册映射名及其触发器）
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J), new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K), new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_H), new KeyTrigger(keyInput.KEY_W),
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));  // false：向上滚动
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_L), new KeyTrigger(keyInput.KEY_S),
                 new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));  // true：向下滚动
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // 可以是多个
        inputManager.addMapping("Move", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE)); // 用鼠标中键移动

        // 将动作名添加到动作监听器（注册上面的触发器映射）
        inputManager.addListener(actionListener, "Pause"); // actionListener：用于接收数字量输入的事件
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down", "Rotate", "Move"); // analogListener：用于接收模拟量输入的事件
//        除了使用ActionListener和AnalogListener外，还可以自定义CombinedListener（同时实现ActionListener和AnalogListener，
//        可接收两种类型的事件）
//        inputManager.addListener(combinedListener, new String[]{"Pause", "Left", "Right", "Rotate"});
    }

//    Pause等动作的具体实现
    private final ActionListener actionListener = (String name, boolean keyPressed, float tpf) -> {
//        实现onAction方法：按下P键放开才会触发动作
        if (name.equals("Pause") && !keyPressed) {
            isRunning = !isRunning;
        }
    };

    private final AnalogListener analogListener = (String name, float value, float tpf) -> {
//        实现onAnalog方法
        if (isRunning) {
            if (name.equals("Rotate")) {
//                    value: Value of the axis,(0,1),一般value==tpf; speed == 1
                player.rotate(0, value * speed, 0);
//                    System.out.println(value+"=="+tpf);
            }
            if (name.equals("Right")) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x + value * speed, v.y, v.z);
//                    player.move(value*speed, 0, 0); // 也可以
            }
            if (name.equals("Left")) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x - value * speed, v.y, v.z);
            }
            if (name.equals("Up")) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x, v.y + value * speed, v.z);
            }
            if (name.equals("Down")) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x, v.y - value * speed, v.z);
            }

            if (name.equals("Move")) {
                player.move(Mouse.getDX() * tpf, Mouse.getDY() * tpf, 0);
                System.out.println(Mouse.getDX());
            }
        } else {
            System.out.println("Press P to unpause.");
        }
    };
}
