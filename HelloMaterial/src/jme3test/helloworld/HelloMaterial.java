package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/**
 * Sample 6 - 材料和纹理的使用，含透明发光材料
 */
public class HelloMaterial extends SimpleApplication {

    public static void main(String[] args) {
        HelloMaterial app = new HelloMaterial();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        /**
         * 简单无阴影的纹理
         */
        Box cube1Mesh = new Box(1f, 1f, 1f);
        Geometry cube1Geo = new Geometry("My Textured Box", cube1Mesh);
        cube1Geo.setLocalTranslation(new Vector3f(-3f, 1.1f, 0f));
        Material cube1Mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/Logo/Monkey.jpg");  // 贴图作纹理
        cube1Mat.setTexture("ColorMap", cube1Tex); // name:材质名(defined in the material definition)
        cube1Geo.setMaterial(cube1Mat);
        rootNode.attachChild(cube1Geo);

        /**
         * 使用半透明/透明的纹理：
         * 1.含alpha通道的纹理
         * 2.纹理的blend mode为：BlendMode.Alpha
         * 3.将Geometry放入“Bucket.Transparent“ render bucket
         * 绘制透明/半透明物体时要按如下顺序来，不透明物体则可以任意顺序绘制
         */
        Box cube2Mesh = new Box(1f, 1f, 0.01f);
        Geometry cube2Geo = new Geometry("window frame", cube2Mesh);
        Material cube2Mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // 不发光（无阴影）材料基于Unshaded.jsmd
        cube2Mat.setTexture("ColorMap",
                assetManager.loadTexture("Textures/ColoredTex/Monkey.png"));  // Monkey.png with an alpha channel
//        混合模式（blend mode）开启后，输入的像素将和已经在色彩缓冲池的像素混合
        cube2Mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);  
//        setQueueBucket：决定该spatial在渲染的哪个阶段被渲染 
//        Bucket：渲染队列，指定渲染的时候spatial将会被放入哪个bucket
        cube2Geo.setQueueBucket(Bucket.Transparent);  // Bucket.Transparent：for object with transparency
        cube2Geo.setMaterial(cube2Mat);
        rootNode.attachChild(cube2Geo);

        /**
         * 发光的凹凸不平的石头
         */
        Sphere sphereMesh = new Sphere(32, 32, 2f);
        Geometry sphereGeo = new Geometry("Shiny rock", sphereMesh);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // 改变TexttureMode，使平面的纹理更好的在球体中显示
        TangentBinormalGenerator.generate(sphereMesh);           // 为mesh生成TangentBinormals以便使用环境光层（NormalMap layer）
        Material sphereMat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");  // 发光材料基于Lighting.j3md
        sphereMat.setTexture("DiffuseMap",
                assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));  //为漫反射层（DiffuseMap layer）设置石头纹理
        sphereMat.setTexture("NormalMap",
                assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));  // 为NormalMap设置纹理，该层含凹凸的效果
        sphereMat.setBoolean("UseMaterialColors", true);  
        sphereMat.setColor("Diffuse", ColorRGBA.White);  // 漫反射为白色，DiffUse、Specular都是定义好的颜色名
        sphereMat.setColor("Specular", ColorRGBA.Red); // 镜面反射为白色(类似光泽），设为黑色则物体不发光
        sphereMat.setFloat("Shininess", 64f);  // [0,128]，值越小越模糊看起来越粗糙，设为0不能让物体不发光
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.setLocalTranslation(0, 2, -2); // 稍微移动一下
        sphereGeo.rotate(1.6f, 0, 0);          // 旋转一下
        rootNode.attachChild(sphereGeo);

        /**
         * 任何基于Lighting.j3md的材料都必须添加光源后才可见
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }
}
