package pt.edj.cp.audio;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource.Status;
import java.util.concurrent.Callable;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.timing.events.ChordChangeEvent;
import pt.edj.cp.timing.events.IEvent;
import pt.edj.cp.timing.events.IEventListener;
import pt.edj.cp.timing.events.MetronomeBeatEvent;
import pt.edj.cp.timing.events.NewBarEvent;
import pt.edj.cp.util.MusicScales;
import pt.edj.cp.util.Randoms;
import pt.edj.cp.util.SoundPathManager;
import pt.edj.cp.util.WhiteNoiseFilter;


public class BackgroundSoundsPlayer implements IEventListener{
    
    private static final int CHANGE_AMB_EVERY_X_BARS = 16;
    private static final int CHANGE_PAD_EVERY_X_BARS = 6;
    private static final int PLAY_PAD_EVERY_X_BARS = 1;
    
    private static final float AMBIENT_VOLUME = 0.3f;
    private static final float PAD_VOLUME     = 0.4f;

    
    private SoundPathManager sam;
    private SimpleApplication app;
    private float[] pitches;
    
    private AudioNode amb;
    private AudioNode pad;
    private AudioNode noise;
    private float noiseVol;
    
    private int barCount;
    private WhiteNoiseFilter noiseGFX;
    
    
    public BackgroundSoundsPlayer(Application app, WhiteNoiseFilter noiseGFX){
        this.app = (SimpleApplication) app;
        this.sam = new SoundPathManager();
        this.pitches = MusicScales.CHORD_MINOR_TONIC;
        this.barCount = 0;
        this.noiseGFX = noiseGFX;
        
        initAmbientNode();
        initPadNode();
        initNoiseNode();
    }
    
    
    public void start(){
        amb.play();
        pad.playInstance();
    }
    
    
    public void receiveEvent(IEvent e) {
        if (e instanceof MetronomeBeatEvent){
            updateNoise(noiseGFX.getIntensity());
        } else if (e instanceof NewBarEvent){
            if (++barCount % CHANGE_AMB_EVERY_X_BARS == 0){
                changeAmbient();
            }
            if (++barCount % CHANGE_PAD_EVERY_X_BARS == 0){
                initPadNode();
            }
            if (++barCount % PLAY_PAD_EVERY_X_BARS == 0){
                playPad();
            }
            if (amb.getStatus() == Status.Stopped){
                replayAmbient();
            }
        } else if (e instanceof ChordChangeEvent){
            pitches = ((ChordChangeEvent)e).getChordPitches();
            app.enqueue(setPadPitch);
        }
    }
    
    
    private void updateNoise(float intensity){
        if (intensity > 0){
            noiseVol = intensity * 1.5f + 0.05f;
            app.enqueue(setNoise);
            if (noise.getStatus() != Status.Playing){
                app.enqueue(playNoise);
            }
        } else {
            if (noise.getStatus() == Status.Playing){
                app.enqueue(stopNoise);
            }
        }
    }
    
    
    private void playPad(){
        app.enqueue(stopPad);
        app.enqueue(playPad);
    }
    
    
    private void changeAmbient(){
        app.enqueue(stopAmbient);
        initAmbientNode();
        app.enqueue(playAmbient);
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
                sam.getRndInstrumentPath(SoundPathManager.INSTR_BG));
        pad.setPositional(false);
        pad.setLooping(false);
        pad.setVolume(PAD_VOLUME);
        app.enqueue(setPadPitch);
    }
    
    
    private void initNoiseNode(){
        noise = new AudioNode(app.getAssetManager(),
                SoundPathManager.FEEDBACK + "noise.ogg");
        noise.setPositional(false);
        noise.setLooping(true);
        noise.setVolume(0);
    }

    
    private Callable<Boolean> playAmbient = new Callable<Boolean>() {
        public Boolean call(){
            amb.play();
            return true;
        }
    };
    
    
    private Callable<Boolean> stopAmbient = new Callable<Boolean>() {
        public Boolean call(){
            amb.stop();
            return true;
        }
    };
    
    
    private Callable<Boolean> playPad = new Callable<Boolean>() {
        public Boolean call(){
            pad.play();
            return true;
        }
    };
    
    
    private Callable<Boolean> stopPad = new Callable<Boolean>() {
        public Boolean call(){
            pad.stop();
            return true;
        }
    };
    
    
    private Callable<Boolean> setPadPitch = new Callable<Boolean>() {
        public Boolean call(){
            pad.setPitch(pitches[Randoms.rndInt(0, pitches.length)]);
            return true;
        }
    };
    
    
    private Callable<Boolean> playNoise = new Callable<Boolean>() {
        public Boolean call(){
            noise.play();
            return true;
        }
    };
    
    
    private Callable<Boolean> stopNoise = new Callable<Boolean>() {
        public Boolean call(){
            noise.stop();
            return true;
        }
    };
    
    
    private Callable<Boolean> setNoise = new Callable<Boolean>() {
        public Boolean call(){
            noise.setVolume(noiseVol);
            return true;
        }
    };
    
    
}
