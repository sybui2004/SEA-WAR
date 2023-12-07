import javax.sound.sampled.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Controller controller = new Controller();
        controller.controlGame();
    }
}