import java.util.HashMap;
import java.util.Map;
public class FillColor {
    public Map<String, String> color = new HashMap<String, String>();
    public void initColor(){
        color.put("X", "\u001B[41m");//Hit
        color.put("M", "\u001B[47m");//Miss
        color.put("W", "\u001B[46m");//Water
        color.put("P", "\u001B[42m");//Ship 1x2
        color.put("D", "\u001B[44m");//Ship 1x4
        color.put("S", "\u001B[43m");//Ship 1x3
        color.put("B", "\u001B[45m");//Ship 1x5
    }
    public static final String BLACK = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
    //public static final String SHIP = "\u001B[43m";
    //public static final String WIN = "\u001B[32m";
}
