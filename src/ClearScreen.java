import java.io.IOException;
public class ClearScreen {
    public static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {}
            FillColor fill = new FillColor();
        fill.initColor();
            //System.out.print(fill.color.get("W"));
            System.out.println("                                                                                                  \n" +
                    "              __________        _____       _____     ___      _______             __________                \n" +
                    "             /  _______/\\       \\  \\ \\     / /| |\\   /  /\\    /    \\  \\           /  /---\\ \\ \\    \n" +
                    "            /  / ______\\/        \\  \\ \\   / / | ||  /  / /   /  /_\\ \\  \\         /  /     | | |       \n" +
                    "           /  / /                 \\  \\ \\ / / /| || /  / /   /  /___\\ \\  \\       /  /_____/ / /         \n" +
                    "          /  /_/_____              \\  \\./ / / | |./  / /   /  /|____\\ \\  \\     /  / |---\\ \\ \\        \n" +
                    "         /__________/\\              \\____/ /  |_____/ /   /__/ /     \\_\\__\\   /_ / /     \\_\\_\\       \n" +
                    "         \\__________\\/               \\___\\/   \\_____\\/    \\__\\/      |_|__/   \\_ \\/      |_|_/      \n");
            System.out.print(FillColor.RESET);
    }
}
