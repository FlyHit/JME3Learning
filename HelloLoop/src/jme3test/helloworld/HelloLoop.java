package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Sample 4 - 在Update loop中触发重复动作
 * 每个游戏含三个阶段：
 * 1.初始化（init）：simpleInitApp()只在开始处执行一次
 * 2.更新（Update）:simpleUpdate()在整个游戏期间重复执行
 * 3.渲染（Render）：每次更新完，JME自动重绘画面
 */
public class HelloLoop extends SimpleApplication {
    public boolean isGrow = true; 

    public static void main(String[] args) {
        HelloLoop app = new HelloLoop();
        app.start();
    }

    protected Geometry player1, player2;

//    开发大型应用时，应将此处的代码块迁移至AppStates
    @Override
    public void simpleInitApp() {
        /**
         * 蓝色方块作为游戏人物
         */
        Box b = new Box(1, 1, 1);
        player1 = new Geometry("blue cube1", b);
        player2 = new Geometry("blue cube2", b);
        Material mat1 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        
        mat1.setColor("Color", ColorRGBA.Blue);
       
        player1.setMaterial(mat1);
        player2.setMaterial(mat1);
        player2.setLocalTranslation(5, 0, 0);
        rootNode.attachChild(player1);
        rootNode.attachChild(player2);
    }

//    simpleUpdate（）：the update loop，可在此处更新分数，生命值，检测碰撞等等
//    该方法在simpleInitApp后执行，JME3将重复地尽快地执行该方法
//    开发大型应用时，应将此处的代码块迁移至Custom Controls
    @Override
    public void simpleUpdate(float tpf) {
        // 令游戏人物旋转
        // "tpf" variable: 每帧所花时间,根据当前的帧率为动作确定完成的时间，确保在不同的机器上蓝块能有相同的旋转速度
        // 这样旋转相同角度所花的时间一样
        player1.rotate(0, 10 * tpf, 0); 
        if(isGrow){
            // 同样保证缩放所花时间在不同的机器上一致，这里还起到慢慢变大的作用，每次放大倍率为3tpf
            player2.scale(1+(3f*tpf)); 
        }else{
            player2.scale(1-3f*tpf);
        }
        
        Vector3f s = player2.getLocalScale(); // 缩放是三个维度的事
//        放大1.2倍后缩小，缩小0.8倍后放大
        if (s.getX() > 1.2f) {
            isGrow = false;
        } else if (s.getX() < 0.8f) {
            isGrow = true;
        }
    }
}
