package Audios;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
   private String clearLinePath = "src/Audios/clear.wav";
   private String gameOverPath = "src/Audios/game_over.wav";
   private String titlePath = "src/Audios/title.wav";

   private Clip clearLineSound, gameOverSound, titleSound;

   public AudioPlayer() {
      try {
         clearLineSound = AudioSystem.getClip();
         gameOverSound = AudioSystem.getClip();
         titleSound = AudioSystem.getClip();

         clearLineSound.open(AudioSystem.getAudioInputStream(new File(clearLinePath)));
         gameOverSound.open(AudioSystem.getAudioInputStream(new File(gameOverPath)));
         titleSound.open(AudioSystem.getAudioInputStream(new File(titlePath)));

      } 
      catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
         System.out.println("Error loading audio files: " + e.getMessage());
      }
   }

   public void playClearLine() {
      clearLineSound.setFramePosition(0); // rewind to the start
      clearLineSound.start();
   }

   public void playGameOver() {
      gameOverSound.setFramePosition(0); // rewind to the start
      gameOverSound.start();

      // Adjust the volume
      FloatControl volumeControl = (FloatControl) titleSound.getControl(FloatControl.Type.MASTER_GAIN);
      float volumeReduction = -20.0f; // Reduce volume by 10 decibels (adjust as needed)
      volumeControl.setValue(volumeReduction);
   }

   public void playTitle() {
      titleSound.setFramePosition(0); // rewind to the start
      titleSound.loop(Clip.LOOP_CONTINUOUSLY); // loop indefinitely

      // Adjust the volume
      FloatControl volumeControl = (FloatControl) titleSound.getControl(FloatControl.Type.MASTER_GAIN);
      float volumeReduction = -20.0f; // Reduce volume by 10 decibels (adjust as needed)
      volumeControl.setValue(volumeReduction);
   }
   

}
