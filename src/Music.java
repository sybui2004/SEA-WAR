import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    public static Clip boom, soundTrack, miss, beep, noelMode;
    public static void Miss() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\MSI-PC\\Documents\\SEA-WAR\\src\\Music\\Miss.wav"));
        miss = AudioSystem.getClip();
        miss.open(audioStream);
        miss.start();
    }
    public static void Boom() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\MSI-PC\\Documents\\SEA-WAR\\src\\Music\\Boom.wav"));
        boom = AudioSystem.getClip();
        boom.open(audioStream);
        boom.start();
    }
    public static void SoundTrack() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\MSI-PC\\Documents\\SEA-WAR\\src\\Music\\SoundTrackGame.wav"));
        soundTrack = AudioSystem.getClip();
        soundTrack.open(audioStream);
    }
    public static void Beep() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\MSI-PC\\Documents\\SEA-WAR\\src\\Music\\Beep.wav"));
        beep = AudioSystem.getClip();
        beep.open(audioStream);
        beep.start();
    }
    public static void NoelMode() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\MSI-PC\\Documents\\SEA-WAR\\src\\Music\\NoelMode.wav"));
        noelMode = AudioSystem.getClip();
        noelMode.open(audioStream);
        noelMode.start();
    }

}
