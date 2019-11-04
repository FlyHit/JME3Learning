package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *绕着球体旋转的方块
 */
public class Rotate extends SimpleApplication{
    public  static  void main(String[] args){
        Rotate rotate = new Rotate();
        rotate.start();
    }
    
    @Override
    public void simpleInitApp(){
//        创建蓝色方块
        Sphere bSphere = new Sphere(100, 100, 1);
        Geometry blueSphere = new Geometry("blueCube", bSphere);
        blueSphere.setLocalTranslation(-10, 0, 0);
        Material mat1 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blueSphere.setMaterial(mat1);
        
//        创建红色球体
        Sphere sphere = new Sphere(100, 100, 5);
        Geometry redSphere = new Geometry("redSphere", sphere);
        redSphere.setLocalTranslation(0, 0, 0);
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        redSphere.setMaterial(mat2);
        
//        创建旋转节点
        Node pivotNode = new Node("pivot");
        pivotNode.attachChild(blueSphere);
        
        rootNode.attachChild(pivotNode);
        rootNode.attachChild(redSphere);
    }
}
