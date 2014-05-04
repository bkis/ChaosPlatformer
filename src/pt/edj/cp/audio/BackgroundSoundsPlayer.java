package pt.edj.cp.audio;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource.Status;
import java.util.concurrent.Callable;
import pt.edj.cp.timing.events.ChordChangeEvent;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.util.MusicScales;
import pt.edj.cp.util.Randoms;
import pt.edj.cp.util.SoundPathManager;


public class BackgroundSoundsPlayer implements IEventListener{
    
    private static final int CHANGE_AMB_EVERY_X_BEATS = 16 * 16;
    private static final int CHANGE_PAD_EVERY_X_BEATS = 16 * 8;
    
    private static final float AMBIENT_VOLUME = 0.5f;
    private static final float PAD_VOLUME     = 0.4f;

    
    private SoundPathManager sam;
    private SimpleApplication app;
    private float[] pitches;
    
    private AudioNode amb;
    private AudioNode pad;
    
    private double beatCount;
    
    
    
    public BackgroundSoundsPlayer(Application app){
        this.app = (SimpleApplication) app;
        this.sam = new SoundPathManager();
        this.pitches = MusicScales.CHORD_MINOR_TONIC;
        this.beatCount = 0;
        
        initAmbientNode();
        initPadNode();
    }
    
    
    public void start(){
        amb.play();
        pad.playInstance();
    }
    
    
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent){
            if (++beatCount % CHANGE_AMB_EVERY_X_BEATS == 0){
                changeAmbient();
            }
            if (++beatCount % CHANGE_PAD_EVERY_X_BEATS == 0){
                changePad();
            }
            if (amb.getStatus() == Status.Stopped){
                replayAmbient();
            }
        } else if (e instanceof ChordChangeEvent){
            pitches = ((ChordChangeEvent)e).getChordPitches();
            updatePadPitch();
        }
    }
    
    
    private void changeAmbient(){
        app.enqueue(stopAmbient);
        initAmbientNode();
        app.enqueue(playAmbient);
    }
    
    
    private void changePad(){
        app.enqueue(stopPad);
        initPadNode();
        app.enqueue(playPad);
    }
    
    
    private void replayAmbient(){
        String path = amb.getUserData("path");
        initAmbientNode(path);
        app.enqueue(playAmbient);
    }
    
    
    private void initAmbientNode(){
        String path = sam.getRndSoundPath(SoundPathManager.AMB_LVL1);
        initAmbientNode(path);
    }
    
    
    private void initAmbientNode(String path){
        amb = new AudioNode(app.getAssetManager(), path, true);
        amb.setUserData("path", path);
        amb.setPositional(false);
        amb.setVolume(AMBIENT_VOLUME);
    }
    
    
    private void initPadNode(){
        pad = new AudioNode(app.getAssetManager(),
                sam.getRndSoundPath(SoundPathManager.INSTR_BG));
        pad.setPositional(false);
        pad.setLooping(true);
        pad.setVolume(PAD_VOLUME);
        updatePadPitch();
    }
    
    
    private void updatePadPitch(){
        app.enqueue(pitchPad);
    }
    
    
    private Callable<Boolean> pitchPad = new Callable<Boolean>() {
        public Boolean call(){
            pad.setPitch(pitches[Randoms.rndInt(0, pitches.length)]);
            return true;
        }
    };
    
    
    private Callable<Boolean> playAmbient = new Callable<Boolean>() {
        public Boolean call(){
            amb.play();
            return true;
        }
    };
    
    
    private Callable<Boolean> playPad = new Callable<Boolean>() {
        public Boolean call(){
            pad.play();
            return true;
        }
    };
    
    
    private Callable<Boolean> stopAmbient = new Callable<Boolean>() {
        public Boolean call(){
            amb.stop();
            return true;
        }
    };
    
    
    private Callable<Boolean> stopPad = new Callable<Boolean>() {
        public Boolean call(){
            pad.stop();
            return true;
        }
    };
    
    
}
