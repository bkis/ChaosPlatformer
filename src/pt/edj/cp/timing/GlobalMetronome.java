package pt.edj.cp.timing;


public class GlobalMetronome {
    private static final float INITIAL_BPM = 120.0f;
    
    private static GlobalMetronome instance = new GlobalMetronome(INITIAL_BPM);
    
    float bpm;
    float time;
    long lastBeat;
    
    private GlobalMetronome(float bpm) {
        this.bpm = bpm;
        this.time = 0.0f;
        this.lastBeat = System.currentTimeMillis();
    }
    
    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
    
    private final void updateTime() {
        long nowTime = System.currentTimeMillis();
        
        if (nowTime > lastBeat) {
            int delta = (int) (nowTime - lastBeat);
        }
    }
}
