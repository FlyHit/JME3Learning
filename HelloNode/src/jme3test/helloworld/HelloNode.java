package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * Sample 2 - 节点的使用
 * 可以通过操作节点来缩放旋转节点下的对象
 */
public class HelloNode extends SimpleApplication {

    public static void main(String[] args) {
        HelloNode app = new HelloNode();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        /**
         * 在坐标(1,-1,1)处创建蓝色的立方体
         * 3步创建spatial
         */
        Box box1 = new Box(1, 1, 1); //1.Create a Mesh shape
        Geometry blue = new Geometry("Box", box1); // 2.wrap it into a Geometry
        blue.setLocalTranslation(new Vector3f(1, -1, 1));
        Material mat1 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1); //3.give it a Material

        /**
         * 在坐标(1,3,1)处创建红色的立方体
         */
        Box box2 = new Box(1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        red.setLocalTranslation(new Vector3f(1, 3, 1));
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);

        /**
         * 在（0,0,0）处创建旋转节点，并添加到rootNode
         */
        Node pivot = new Node("pivot");
        pivot.setUserData("newProp", "test"); // 可以方便地为Spatial添加/读取自定义数据（属性）
        rootNode.attachChild(pivot);

        /**
         * 将两个box对象(Geometry）添加到旋转节点
         */
        pivot.attachChild(blue);
        pivot.attachChild(red);
        /**
         * 对象可以绕自身中心或者自定义的中心点旋转，这里定义了一个pivot节点，
         * 节点下的两个box将绕该节点旋转
         */
        pivot.rotate(.4f, .4f, 0f);
    }
}
