package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.start();
    }

    Spatial scene;

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);

//        加载场景
        scene = assetManager.loadModel("Scenes/3DScene.j3o");
        scene.setName("myScene");
        scene.setLocalTranslation(0, 0, 0);
        scene.setLocalScale(0.01f);
        rootNode.attachChild(scene);

//        添加光源
        viewPort.setBackgroundColor(ColorRGBA.White);
        DirectionalLight dLight = new DirectionalLight(new Vector3f(0, 0, 0), ColorRGBA.White);
        rootNode.addLight(dLight);

//        设置摄像机位置
        cam.setLocation(new Vector3f(10, 10, 0));
        cam.lookAt(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        cam.update();

        initKey();
    }

    private void initKey() {
        inputManager.addMapping("ZoomDown", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("ZoomUp", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addListener(anologListener, "ZoomDown", "ZoomUp", "up", "down", "left", "right");
    }

    private final AnalogListener anologListener = (String name, float keyPressed, float tpf) -> {
        Vector3f location = cam.getLocation();

        switch (name) {
            case "ZoomUp":
                cam.setLocation(location.scaleAdd(0.23f, location));
                cam.update();
                break;
            case "ZoomDown":
                cam.setLocation(location.scaleAdd(-0.23f, location));
                cam.update();
                break;
            case "left":
                cam.setLocation(location.add(new Vector3f(0, 0, -10 * tpf)));
                cam.update();
                break;
            case "right":
                cam.setLocation(location.add(new Vector3f(0, 0, 10 * tpf)));
                cam.update();
                break;
            case "up":
                cam.setLocation(location.add(new Vector3f(10 * tpf, 0, 0)));
                cam.update();
                break;
            case "down":
                cam.setLocation(location.add(new Vector3f(-10 * tpf, 0, 0)));
                cam.update();
                break;
        }
    };
}
