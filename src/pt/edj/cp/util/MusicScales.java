package pt.edj.cp.util;


public class MusicScales {
    
    //frequency pitch factors
    public static final float[] PITCH_OCTAVE = {1.000000f,    //Unisono
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
    public static final float[] PITCH_MINOR = {PITCH_OCTAVE[0],
                                               PITCH_OCTAVE[2],
                                               PITCH_OCTAVE[3],
                                               PITCH_OCTAVE[5],
                                               PITCH_OCTAVE[7],
                                               PITCH_OCTAVE[8],
                                               PITCH_OCTAVE[10],
                                               PITCH_OCTAVE[12]};
    
    //chords minor
    public static final float[] CHORD_MINOR_TONIC       = {PITCH_MINOR[0],
                                                           PITCH_MINOR[2],
                                                           PITCH_MINOR[4]};
    
    public static final float[] CHORD_MINOR_DOMINANT    = {PITCH_MINOR[4],
                                                           PITCH_MINOR[6],
                                                           PITCH_MINOR[1]};
    
    public static final float[] CHORD_MINOR_SUBDOMINANT = {PITCH_MINOR[3],
                                                           PITCH_MINOR[5],
                                                           PITCH_MINOR[7]};
    
}
