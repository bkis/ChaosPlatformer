package pt.edj.cp.world.platforms;


class RhythmPattern {
    
    private boolean[] pattern;
    private int nextIndex;
    
    
    public RhythmPattern(int length, float saturation){
        this();
        this.pattern = createRandomPattern(length, saturation);
    }
    
    
    public RhythmPattern(boolean[] pattern, float saturation){
        this();
        this.pattern = createRandomPattern(pattern, saturation);
    }
    
    
    public RhythmPattern(boolean[] pattern){
        this.pattern = pattern;
    }
    
    
    private RhythmPattern(){
        this.nextIndex = 0;
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
    
    
    private boolean[] createRandomPattern(int length, float sat){
        boolean[] pat = new boolean[length];
        return createRandomPattern(pat, sat);
    }
    
    
    private boolean[] createRandomPattern(boolean[] pat, float sat){
        for (int i = 0; i < pat.length; i++)
            if (Math.random() < sat)
                pat[i] = true;
        return pat;
    }
    
    
}
