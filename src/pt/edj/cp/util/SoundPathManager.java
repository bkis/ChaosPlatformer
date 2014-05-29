package pt.edj.cp.util;

import java.io.File;
import java.util.Random;


public class SoundPathManager {
    
    //base paths
    //private static final String DIR_ASSETS     = ""; //for build! Kopie von "Sound" ins Anwendungsverzeichnis!
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
    
    
    public SoundPathManager(){
        this.rnd = new Random();
    }
    
    
    public String getRndSoundPath(String soundTypeKey){
        File dir = new File(DIR_ASSETS + soundTypeKey);
        return soundTypeKey + dir.list()[rnd.nextInt(dir.list().length)];
    }
    
    
}
