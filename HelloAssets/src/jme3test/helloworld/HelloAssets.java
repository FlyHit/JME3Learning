package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * Sample 3 - 加载OBJ，OgreXML模型和材质，
 * 可以从默认路径加载也可以从自定义路径加载(assetManager.registerLocator)或者从.j3o file文件加载
 * （可以将模型场景文件转为.j3o file）
 */
public class HelloAssets extends SimpleApplication {

    public static void main(String[] args) {
        HelloAssets app = new HelloAssets();
        app.start();
    }

    @Override
    public void simpleInitApp() {
//        game assets指所有的多媒体文件，如模型，材质等
//        可通过AssetManager对象访问assets
        Spatial teapot = assetManager.loadModel("Models/Teapot/Teapot.obj");
        Material mat_default = new Material(
                assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        teapot.setMaterial(mat_default);
        rootNode.attachChild(teapot);

        // 使用简单的材质创建墙
        Box box = new Box(2.5f, 2.5f, 1.0f);
        Spatial wall = new Geometry("Box", box);
        Material mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(2.0f, -2.5f, 0.0f);
        rootNode.attachChild(wall);

        // 以默认字体显示一行字
        // guiNode：a special node for flat (orthogonal) display elements       
        guiNode.detachAllChildren(); // 清除文字
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt"); 
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Hello World");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);

        // 加载模型(含模型，材质，纹理）
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.scale(0.05f, 0.05f, 0.05f);
        ninja.rotate(0.0f, -3.0f, 0.0f);
        ninja.setLocalTranslation(0.0f, -5.0f, -2.0f);
        rootNode.attachChild(ninja);
        // 必须添加光源来使材料（模型）可见，茶壶和墙非自带材质不用添加光源也可见
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        
////        使用自定义路径加载模型，ZipLocator：寻找zip格式的资源
////        此外还有ClasspathLocator, FileLocator, HttpZipLocator, and UrlLocator
//        assetManager.registerLocator("town.zip", ZipLocator.class);
//        Spatial gameLevel = assetManager.loadModel("main.scene"); // 加载S、scene的方法和model一样
//        gameLevel.setLocalTranslation(0, -5.2f, 0);
//        gameLevel.setLocalScale(2);
//        rootNode.attachChild(gameLevel);
       
//        AssetManager相对路径相对于assets/…,以下是加载模型最常用的方式:从assets路径加载
        Spatial gameLevel = assetManager.loadModel("Scenes/town/main.scene");
        gameLevel.setLocalTranslation(0, -5.2f, 0);
        gameLevel.setLocalScale(2);
        rootNode.attachChild(gameLevel);
        
////        从.j3o file加载模型
//        Spatial gameLevel = assetManager.loadModel("Scenes/town/main.j3o");
//        gameLevel.setLocalTranslation(0, -5.2f, 0);
//        gameLevel.setLocalScale(2);
//        rootNode.attachChild(gameLevel);
    }
}
