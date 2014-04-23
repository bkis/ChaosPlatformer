package pt.edj.cp.world.platforms.sfx;


public class RhythmPattern {
    
    private boolean[] pattern;
    private int nextIndex;
    
    
    public RhythmPattern(int length, float soundDuration){
        this();
        this.pattern = createRandomPattern(length, calculateSaturation(soundDuration));
    }
    
    
    public RhythmPattern(boolean[] pattern, float soundDuration){
        this();
        this.pattern = createRandomPattern(pattern, calculateSaturation(soundDuration));
    }
    
    
    public RhythmPattern(boolean[] pattern){
        this.pattern = pattern;
    }
    
    
    private RhythmPattern(){
        this.nextIndex = 0;
    }
    
    
    public int getNrOfEvents(){
        return getNrOfEvents(pattern);
    }
    
    
    public int getNrOfEvents(boolean[] pattern){
        int count = 0;
        
        for (int i = 0; i < pattern.length; i++)
            if (pattern[i]) count++;
        
        return count;
    }
    
    
    public boolean nextEvent(){
        return this.pattern[nextIndex++ % pattern.length];
    }
    
    
//    public float getSaturation(){
//        return saturation;
//    }
//    
//    
//    public int getLength(){
//        return pattern.length;
//    }
//    
//    
//    public boolean[] getPattern(){
//        return pattern;
//    }
//    
//    
//    public void randomizePattern(){
//        pattern = createRandomPattern(pattern.length, saturation);
//    }
    
    
    private float calculateSaturation(float soundDuration){
        float sat;
        
        if (soundDuration > 0.6)
            sat = 0.09f;
        else
            sat = (1 - (soundDuration % 1))/3;
        
        return sat;
    }
    
    
    private boolean[] createRandomPattern(int length, float sat){
        boolean[] pat = new boolean[length];
        return createRandomPattern(pat, sat);
    }
    
    
    private boolean[] createRandomPattern(boolean[] pat, float sat){
        for (int i = 0; i < pat.length; i++)
            if (Math.random() < sat)
                pat[i] = true;
        
        if (getNrOfEvents(pat) == 0)
            return createRandomPattern(pat, sat);
        else
            return pat;
    }
    
    
}
