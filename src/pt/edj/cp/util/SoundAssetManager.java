package pt.edj.cp.util;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import java.io.File;
import java.util.Random;


public class SoundAssetManager {
    
    //frequency pitch factors
    private static final float[] PITCH = {1.000000f,    //Unisono
                                          1.059463f,    //kleine Sekunde
                                          1.122462f,    //große Sekunde
                                          1.189207f,    //kleine Terz
                                          1.259921f,    //große Terz
                                          1.334840f,    //reine Quarte
                                          1.414214f,    //überm. Quarte / verm. Quinte
                                          1.498307f,    //reine Quinte
                                          1.587401f,    //kleine Sexte
                                          1.681793f,    //große Sexte
                                          1.781797f,    //kleine Septime
                                          1.887749f,    //große Septime
                                          2.000000f};   //Oktave
    //scales pitch factors
    private static final float[] PITCH_MINOR = {PITCH[0],
                                                PITCH[2],
                                                PITCH[3],
                                                PITCH[5],
                                                PITCH[7],
                                                PITCH[8],
                                                PITCH[10],
                                                PITCH[12]};
    //base paths
    private static final String DIR_ASSETS     = "assets/";
    private static final String DIR_SND        = "Sounds/";
    private static final String DIR_AMB        = DIR_SND   + "Ambient/";
    private static final String DIR_INSTR      = DIR_SND   + "Instruments/";
    private static final String DIR_INSTR_PERC = DIR_INSTR + "Percussive/";
    private static final String INSTR_MEL      = DIR_INSTR + "Melodic/";
    
    //sound keys
    public static final String AMB_LVL1     = DIR_AMB        + "lvl1/";
    public static final String AMB_LVL2     = DIR_AMB        + "lvl2/";
    public static final String AMB_LVL3     = DIR_AMB        + "lvl3/";
    public static final String FEEDBACK     = DIR_SND        + "Feedback/";
    public static final String INSTR_BG     = DIR_INSTR      + "Background/";
    public static final String INSTR_PERC_S = DIR_INSTR_PERC + "Short/";
    public static final String INSTR_PERC_M = DIR_INSTR_PERC + "Medium/";
    public static final String INSTR_PERC_L = DIR_INSTR_PERC + "Long/";
    
    private static final String[] melodicSounds = new File(DIR_ASSETS + INSTR_MEL).list();
    
    private Random rnd;
    private AssetManager assetManager;
    
    
    
    
    public SoundAssetManager(AssetManager assetManager){
        this.assetManager = assetManager;
        this.rnd = new Random();
    }
    
    
    public AudioNode getRndSound(String soundKey){
        File dir = new File(DIR_ASSETS + soundKey);
        File[] files = dir.listFiles();
        String path = soundKey + files[rnd.nextInt(files.length)].getName();
        
        AudioNode audioNode = new AudioNode(assetManager, path);
        return prepareAudioNode(audioNode);
    }
    
    
    public AudioNode getRndMelodicNote(String instrumentID){
        AudioNode audioNode = new AudioNode(assetManager, INSTR_MEL + instrumentID);
        audioNode.setPitch(PITCH_MINOR[rnd.nextInt(PITCH_MINOR.length)]);
        return prepareAudioNode(audioNode);
    }
    
    
    public AudioNode[] getRndMelodicNotes(String instrumentID, int nrOfSounds){
        AudioNode[] sounds = new AudioNode[nrOfSounds];
        
        for (int i = 0; i < sounds.length; i++) {
            AudioNode audioNode = new AudioNode(assetManager, INSTR_MEL + instrumentID);
            audioNode.setPitch(PITCH_MINOR[rnd.nextInt(PITCH_MINOR.length)]);
            prepareAudioNode(audioNode);
            sounds[i] = audioNode;
        }
        
        return sounds;
    }
    
    
    public String getRndMelodicInstrumentID(){
        return melodicSounds[rnd.nextInt(melodicSounds.length)];
    }
    
    
    private AudioNode prepareAudioNode(AudioNode an){
        an.setPositional(false);
        return an;
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
