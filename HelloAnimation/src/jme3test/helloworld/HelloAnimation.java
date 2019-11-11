package jme3test.helloworld;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Sample 7 - 使用channel 、controller、AnimEventListener， 加载OgreXML模型并播放动画
 */
// AnimEventListener：允许用户通过编程来接收多个关于AnimControl的事件
public class HelloAnimation extends SimpleApplication
        implements AnimEventListener {
//    AnimChannel：提供诸如播放、暂停、加速的控制操作。一个模型可以有多个channel，用于独立控制身体的各个部分

    private AnimChannel channel;
//    AnimControl is a Spatial control that allows manipulation of skeletal animation.
    private AnimControl control;
    Node player;

    public static void main(String[] args) {
        HelloAnimation app = new HelloAnimation();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.LightGray);  // 设置背景颜色
        initKeys();  // 自定义按键，按空格走路
//        添加光源
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);
//        加载食人魔模型
        player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        player.setLocalScale(0.5f);
        rootNode.attachChild(player);
//        getControl:返回给定控制类的一个实例，通过controller对象可以访问有效的动画序列
        control = player.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();  // controller可以有多个channel，每个通道一次运行一个动画序列
        channel.setAnim("stand");  // 该通道是站立动画序列
    }

    @Override
//    动画周期结束时候调用，这里走完了就恢复站立状态
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Walk")) {
            channel.setAnim("stand", 0.5f);  // 0.5：动画合成时间,如果为0，新动画将立即应用
            channel.setLoopMode(LoopMode.DontLoop);  // 动画不循环
            channel.setSpeed(1f);
        }
    }

    @Override
//    动画播放时调用
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }

    /**
     * 自定义按键绑定
     */
    private void initKeys() {
        inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Walk");
    }
    private ActionListener actionListener = (String name, boolean keyPressed, float tpf) -> {
        if (name.equals("Walk") && !keyPressed) {
            if (!channel.getAnimationName().equals("Walk")) {
                channel.setAnim("Walk", 0.50f);
                channel.setLoopMode(LoopMode.Loop);
            }
        }
    };
}
