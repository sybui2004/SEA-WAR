import java.io.Serializable;
public class Position implements Serializable, Comparable<Position> {
    private int x, y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    @Override
    public int compareTo(Position o) {
        return this.x - o.x;
    }
}
