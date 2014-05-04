package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;
import java.io.File;
import java.util.Random;


public class TextureFactory {
    
    private static final String TEXTURES_PATH = "Textures/PlatformTextures/";
    
    
    public static Texture getRndTexture(AssetManager assetManager){
        Random rnd = new Random();
        String[] paths = new File(TEXTURES_PATH).list();
        return assetManager.loadTexture(paths[rnd.nextInt(paths.length)]);
    }
    
    
}
