public class PressEnterToContinue {
    public static void pressEnterToContinue() {
        System.out.println("Press Enter to continue");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
