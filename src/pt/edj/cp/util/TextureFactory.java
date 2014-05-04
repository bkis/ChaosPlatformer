package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;
import java.io.File;
import java.util.Random;


public class TextureFactory {
    
    private static final String ASSET_DIR = "assets/";
    private static final String TEXTURES_PATH = "Textures/PlatformTextures/";
    
    
    public static Texture getRndTexture(AssetManager assetManager){
        Random rnd = new Random();
        String[] paths = new File(ASSET_DIR + TEXTURES_PATH).list();
        return assetManager.loadTexture(TEXTURES_PATH + paths[rnd.nextInt(paths.length)]);
    }
    
    
}
