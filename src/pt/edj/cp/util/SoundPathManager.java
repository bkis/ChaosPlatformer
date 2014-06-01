package pt.edj.cp.util;

import java.io.File;
import java.util.Random;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.NewBarEvent;


public class SoundPathManager implements IEventListener{
    
    private static final int NUMBER_OF_SOUNDSETS = 3;
    private static final int CHANGE_SOUND_SET_EVERY_X_BARS = 32;
    
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
    public static final String INSTR_BG           = "Background/";
    public static final String INSTR_PERCUSSIVE   = "Percussive/";
    public static final String INSTR_MELODIC      = "Melodic/";
    

    private Random rnd;
    private int currSoundSet;
    private int barCount;
    
    
    public SoundPathManager(){
        this.rnd = new Random();
        this.currSoundSet = getRndSoundSetKey();
        this.barCount = 1;
    }
    
    
    public String getRndSoundPath(String soundTypeKey){
        File dir = new File(DIR_ASSETS + soundTypeKey);
        return soundTypeKey + dir.list()[rnd.nextInt(dir.list().length)];
    }
    
    
    public String getRndInstrumentPath(String instrumentTypeKey){
        File dir = new File(DIR_ASSETS + DIR_INSTR
                + currSoundSet + "/" + instrumentTypeKey);
        return DIR_INSTR + currSoundSet + "/"
                + instrumentTypeKey + dir.list()[rnd.nextInt(dir.list().length)];
    }

    public void receiveEvent(IEvent e) {
        if (e instanceof NewBarEvent){
            barCount++;
            if (barCount % CHANGE_SOUND_SET_EVERY_X_BARS == 0){
                currSoundSet = getRndSoundSetKey();
            }
        }
    }
    
    private int getRndSoundSetKey(){
        return rnd.nextInt(NUMBER_OF_SOUNDSETS) + 1;
    }
    
    
}
