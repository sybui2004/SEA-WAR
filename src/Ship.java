public class Ship {
    private String name;
    private Position posStart, posEnd;
    private int sizeShip;
    public void setPosShip(Position Start, Position End){
        this.posStart = Start;
        this.posEnd = End;
    }
    public void setSizeShip(int sizeShip) {
        this.sizeShip = sizeShip;
    }
    public int getSizeShip() {
        return sizeShip;
    }
    public Position getPosStart(){
        return posStart;
    }
    public Position getPosEnd(){
        return posEnd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
