package pt.edj.cp.world.platforms.sfx;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import java.util.Random;
import java.util.concurrent.Callable;
import pt.edj.cp.app.IngameState;


/*
 * sound container for managing and playing sounds and rhythm patterns
 */
public class SoundObject{
    
    private AudioNode sound;
    private float[] pitches;
    private int nextIndex;
    private SimpleApplication app;
    private RhythmPattern pattern;
    private Random rnd;
    private boolean currentEvent;
    private boolean melodic;
    
    
    public SoundObject(Application app, String samplePath, float[] currChord){
        this.app = (SimpleApplication) app;
        this.nextIndex = 0;
        this.sound = createAudioNode(samplePath);
        this.pattern = new RhythmPattern(16, getSampleLength());
        this.pitches = createPitchPattern(currChord);
        this.rnd = new Random();
        this.melodic = samplePath.contains("elodic"); //TEMP
    }
    
    
    public final float getSampleLength(){
        if (sound != null)
            return sound.getAudioData().getDuration();
        else
            return 0.5f;
    }
    
    
    public void playNextEvent(){
        if (pattern.nextEvent()){
            if (melodic) sound.setPitch(pitches[nextIndex++ % pitches.length]);
            app.enqueue(playSound);
            currentEvent = true;
        } else {
            currentEvent = false;
        }
    }
    
    
    public void playBaseSound(){
        app.enqueue(playSound);
    }
    
    
    public boolean getCurrentEvent(){
        return currentEvent;
    }
    
    
    public void changeChord(float[] newChord) {
        pitches = createPitchPattern(newChord);
    }
    
    
    Callable<Boolean> playSound = new Callable<Boolean>() {
        public Boolean call(){
            sound.playInstance();
            return true;
        }
    };
    
    
    private AudioNode createAudioNode(String samplePath){
        AudioNode an = new AudioNode(app.getAssetManager(), samplePath);
        an.setPositional(false);
        return an;
    }

    
    private float[] createPitchPattern(float[] chord) {
        if (rnd == null) rnd = new Random();
        float[] pitchPattern = new float[pattern.getNrOfEvents()];
        
        for (int i = 0; i < pitchPattern.length; i++)
            pitchPattern[i] = chord[rnd.nextInt(chord.length)];
        
        return pitchPattern;
    }

    
}
