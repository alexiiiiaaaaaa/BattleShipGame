/** 
 * @author Chanieva Anna-Oeksandra, 2616332
 * @version 1.0
 * project Battleship game
 * class Sound contains SOUND_ON field which enables/desables sound 
 * and play() method to play a sound effect
 * **/

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    // SOUND_ON setting to enable/disable sound effects
    private static boolean SOUND_ON     =   true; 
    public static String SOUND_EXPLODE  =   "sounds/explode.wav";
    public static String SOUND_MISS     =   "sounds/wind.wav";
    public static String SOUND_WIN      =   "sounds/tada.wav";
    
    /**  
     * void setSoundOn(boolean val) - set sound on/off
     * @return void
     **/
    public static void setSoundOn(boolean val)
    {
        SOUND_ON = val;
    }

    /**  
     * void play(String soundFilePath) - plays sound
     * @return void
     **/
    public void play(String soundFilePath)
    {
        if (SOUND_ON) {
            try {
                // Open an audio input stream.
                URL url = this.getClass().getClassLoader().getResource(soundFilePath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
                
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

}
