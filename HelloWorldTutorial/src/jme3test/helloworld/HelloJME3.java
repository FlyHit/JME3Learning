package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * 显示蓝色立方体，通过WASD键查看其所有的面
 * rootNode， Spatial，node，Geometry的概念
 */
public class HelloJME3 extends SimpleApplication {

    public static void main(String[] args) {
        HelloJME3 app = new HelloJME3();
        app.start(); // 启动游戏
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1); // 创建一个立方体
//        Geometry：对象的外观，如形状，颜色，材质， 透明度
        Geometry geom = new Geometry("Box", b);  // 创建立方体Geometry（3D对象）
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // 创建材料
        mat.setColor("Color", ColorRGBA.Blue);   // 设置材料的颜色
        geom.setMaterial(mat);                   // 设置几何体的材料
        rootNode.attachChild(geom);              // 将node或Geometry添加到rooNode便可以其添加到Scene
    }
}
