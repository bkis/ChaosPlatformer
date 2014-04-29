package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import java.io.File;
import java.util.Random;


public class SoundAssetManager {
    
    //base paths
    private static final String DIR_ASSETS     = "assets/";
    private static final String DIR_SND        = "Sounds/";
    private static final String DIR_AMB        = DIR_SND   + "Ambient/";
    private static final String DIR_INSTR      = DIR_SND   + "Instruments/";
    
    //sound keys
    public static final String AMB_LVL1           = DIR_AMB        + "lvl1/";
    public static final String AMB_LVL2           = DIR_AMB        + "lvl2/";
    public static final String AMB_LVL3           = DIR_AMB        + "lvl3/";
    public static final String FEEDBACK           = DIR_SND        + "Feedback/";
    public static final String INSTR_BG           = DIR_INSTR      + "Background/";
    public static final String INSTR_PERCUSSIVE   = DIR_INSTR      + "Percussive/";
    public static final String INSTR_MELODIC      = DIR_INSTR      + "Melodic/";
    

    private Random rnd;
    private AssetManager assetManager;
    
    
    public SoundAssetManager(AssetManager assetManager){
        this.assetManager = assetManager;
        this.rnd = new Random();
    }
    
    
    public String getRndSoundPath(String soundTypeKey){
        File dir = new File(DIR_ASSETS + soundTypeKey);
        return soundTypeKey + dir.list()[rnd.nextInt(dir.list().length)];
    }
    
    
//    //DEBUG
//    public List<File> findFiles(File directory){
//        List<File> fileList = new ArrayList<File>();
//
//        if (!directory.exists()) {
//                System.out.println("\"" + directory.getName() + "\" does not exist!");
//                return fileList;
//        } else if (!directory.isDirectory()){
//                System.out.println("\"" + directory.getName() + "\" is not a directory!");
//                return fileList;
//        }
//
//        findFiles(directory, fileList);
//        return fileList;
//    }
//
//
//    private void findFiles(File directory, List<File> fileList){
//        File[] fileArray = directory.listFiles();
//
//        for (File f : fileArray){
//                if (!f.isDirectory()) fileList.add(f);
//                if (f.isDirectory()) findFiles(f, fileList);
//        }
//    }
    
}
