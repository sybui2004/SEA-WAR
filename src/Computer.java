import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
public class Computer extends Player{
    private final Ship[] shipList = new Ship[5];
    public void setShipOnBoardAutoComputer(){
        yourBoard.initBoard();
        cntShip = 0;
        Random rand = new Random();
        Controller control = new Controller();
        while (cntShip < 5) {
            int sizeShip;
            String name;
            if (cntShip < 2) {
                name = "Patrol Boat";
                sizeShip = 2;
            } else if (cntShip == 2) {
                name = "Destroyer Boat";
                sizeShip = 4;
            } else if (cntShip == 3) {
                name = "Submarine";
                sizeShip = 3;
            } else {
                name = "Battle Ship";
                sizeShip = 5;
            }
            shipList[cntShip] = new Ship();
            shipList[cntShip].setSizeShip(sizeShip);
            shipList[cntShip].setName(name);
            int direction;
            Position posStart, posEnd;
            do {
                int x1 = rand.nextInt(10) + 1;
                int y1 = rand.nextInt(10) + 1;
                direction = rand.nextInt(2);
                int x2, y2;
                if (direction == 0) {
                    x2 = x1 + sizeShip - 1;
                    y2 = y1;
                } else {
                    x2 = x1;
                    y2 = y1 + sizeShip - 1;
                }
                posStart = new Position(y1, x1);
                posEnd = new Position(y2, x2);
                shipList[cntShip].setPosShip(posStart, posEnd);
            } while (!control.checkPosShipOnBoard(yourBoard, shipList[cntShip]));
            // Đặt thuyền lên bảng
            if (direction == 0) {
                for (int i = shipList[cntShip].getPosStart().getY(); i <= shipList[cntShip].getPosEnd().getY(); i++) {
                    Position pos = new Position(shipList[cntShip].getPosStart().getX(), i);
                    yourBoard.setCell(pos, String.valueOf(shipList[cntShip].getName().charAt(0)));
                }
            } else {
                for (int i = shipList[cntShip].getPosStart().getX(); i <= shipList[cntShip].getPosEnd().getX(); i++) {
                    Position pos = new Position(i, shipList[cntShip].getPosStart().getY());
                    yourBoard.setCell(pos, String.valueOf(shipList[cntShip].getName().charAt(0)));
                }
            }
            cntShip++;
            ClearScreen.clrscr();
        }
    }
    public void hitHardMode() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ClearScreen.clrscr();
        oppBoard.printOppBoard();
        Position posHited = findPointHit();
        if (Objects.equals(oppBoard.getCell(posHited), "S")||Objects.equals(oppBoard.getCell(posHited), "D")||Objects.equals(oppBoard.getCell(posHited), "B")||Objects.equals(oppBoard.getCell(posHited), "P")) {
            ClearScreen.clrscr();
            System.out.println("Boom! Computer Hit Point: " + posHited.getY() + " " + posHited.getX() + "!");
            String tmp = oppBoard.getCell(posHited);
            oppBoard.setCell(posHited, "*");
            oppBoard.printOppBoard();
            Music.Boom();
            Wait.wait(3);
            oppBoard.setCell(posHited, tmp + "X");
            if (checkWinner()) {
                ClearScreen.clrscr();
                printBoard(yourBoard, oppBoard);
                System.out.println("Congratulations!!! Computer is Winner!!!");
                return;
            }
            // Check for ship destruction and Win
            System.out.println("Continue Computer's Turn!");
            Wait.wait(3);
            hitHardMode();
        } else {
            System.out.println("Computer Hit Point: " + posHited.getY() + " " + posHited.getX() + " ! Computer Miss!");
            Music.Miss();
            oppBoard.setCell(posHited, "M");
            System.out.println("Change Turn!");
            Wait.wait(3);
            ClearScreen.clrscr();
        }
    }
    public Position findUnexploredPoint(){
        Controller controller = new Controller();
        Random rand = new Random();
        int x1, y1;
        do{
            x1 = rand.nextInt(10) + 1;
            y1 = rand.nextInt(10) + 1;
        }while(!controller.checkHit(oppBoard, new Position(y1, x1)));

        return new Position(y1, x1);
    }
    public Position[] getSurroundingPoints(Position pos) {
        // Trả về mảng các điểm xung quanh của một điểm
        return new Position[]{
                new Position(pos.getX() - 1, pos.getY()),
                new Position(pos.getX() + 1, pos.getY()),
                new Position(pos.getX(), pos.getY() - 1),
                new Position(pos.getX(), pos.getY() + 1)
        };
    }
    public Position findPointHit() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                Position pos = new Position(i, j);
                if (Objects.equals(oppBoard.getCell(pos), "PX")||Objects.equals(oppBoard.getCell(pos), "BX")||Objects.equals(oppBoard.getCell(pos), "DX")||Objects.equals(oppBoard.getCell(pos), "SX")) {
                    Position[] surroundingPoints = getSurroundingPoints(pos);
                    for (Position surroundingPos : surroundingPoints) {
                        if(oppBoard.isInsideBoard(surroundingPos) && oppBoard.getCell(surroundingPos)!=null &&
                                (Objects.equals(oppBoard.getCell(surroundingPos), "DX")||Objects.equals(oppBoard.getCell(surroundingPos), "BX")||Objects.equals(oppBoard.getCell(surroundingPos), "SX")||Objects.equals(oppBoard.getCell(surroundingPos), "PX"))){
                            if(pos.getX() == surroundingPos.getX()){
                                Position posLeft = new Position(pos.getX(), pos.getY()-1);
                                Position posRight = new Position(pos.getX(), pos.getY()+1);
                                if(oppBoard.isInsideBoard(posLeft) &&oppBoard.getCell(posLeft) != null && oppBoard.getCell(posLeft).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posLeft).charAt(1)), "X")){
                                    if(oppBoard.isInsideBoard(posRight) &&oppBoard.getCell(posRight) != null&&oppBoard.getCell(posRight).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posRight).charAt(1)), "X") && oppBoard.isInsideBoard(new Position(pos.getX(), pos.getY()-2))&&(oppBoard.getCell(new Position(pos.getX(), pos.getY()-2))==null|| Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()-2)), "B") || Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()-2)), "D") || Objects.equals(oppBoard.getCell(new Position(pos.getX(), pos.getY()-2)), "S") || Objects.equals(oppBoard.getCell(new Position(pos.getX(), pos.getY()-2)), "P")))
                                    return new Position(pos.getX(), pos.getY()-2);
                                    else if(oppBoard.isInsideBoard(posRight) && oppBoard.getCell(posRight) == null) return posRight;
                                }
                                if(oppBoard.isInsideBoard(posRight) &&oppBoard.getCell(posRight) != null&& oppBoard.getCell(posRight).length() == 2&&Objects.equals(String.valueOf(oppBoard.getCell(posRight).charAt(1)), "X")){
                                    if(oppBoard.isInsideBoard(posLeft) &&oppBoard.getCell(posLeft) != null&&oppBoard.getCell(posLeft).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posLeft).charAt(1)), "X") && oppBoard.isInsideBoard(new Position(pos.getX(), pos.getY()+2))&&(oppBoard.getCell(new Position(pos.getX(), pos.getY()+2))==null|| Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()+2)), "B") || Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()+2)), "D") || Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()+2)), "S") || Objects.equals(oppBoard.getCell(new Position(pos.getX() , pos.getY()+2)), "P")))
                                        return new Position(pos.getX(), pos.getY()+2);
                                    else if(oppBoard.isInsideBoard(posLeft) && oppBoard.getCell(posLeft) == null) return posLeft;
                                }
//                                if(oppBoard.isInsideBoard(posRight) && oppBoard.getCell(posRight)==null) return posRight;
//                                if(oppBoard.isInsideBoard(posLeft) && oppBoard.getCell(posLeft)==null) return posLeft;
                            }else{
                                Position posUp = new Position(pos.getX()-1, pos.getY());
                                Position posDown = new Position(pos.getX()+1, pos.getY());
                                if(oppBoard.isInsideBoard(posUp)&&oppBoard.getCell(posUp) != null &&oppBoard.getCell(posUp).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posUp).charAt(1)), "X")){
                                    if(oppBoard.isInsideBoard(posDown) &&oppBoard.getCell(posDown) != null&&oppBoard.getCell(posDown).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posDown).charAt(1)), "X") && oppBoard.isInsideBoard(new Position(pos.getX()-2, pos.getY()))&&(oppBoard.getCell(new Position(pos.getX()-2, pos.getY()))==null|| Objects.equals(oppBoard.getCell(new Position(pos.getX() - 2, pos.getY())), "B") || Objects.equals(oppBoard.getCell(new Position(pos.getX() - 2, pos.getY())), "D") || Objects.equals(oppBoard.getCell(new Position(pos.getX() - 2, pos.getY())), "S") || Objects.equals(oppBoard.getCell(new Position(pos.getX() - 2, pos.getY())), "P")))
                                        return new Position(pos.getX()-2, pos.getY());
                                    else if(oppBoard.isInsideBoard(posDown) && oppBoard.getCell(posDown) == null) return posDown;
                                }
                                if(oppBoard.isInsideBoard(posDown) &&oppBoard.getCell(posDown) != null&&oppBoard.getCell(posDown).length() == 2&& Objects.equals(String.valueOf(oppBoard.getCell(posDown).charAt(1)), "X")){
                                    if(oppBoard.isInsideBoard(posUp) &&oppBoard.getCell(posUp) != null&&oppBoard.getCell(posUp).length() == 2&&Objects.equals(String.valueOf(oppBoard.getCell(posUp).charAt(1)), "X") && oppBoard.isInsideBoard(new Position(pos.getX()+2, pos.getY()))&&(oppBoard.getCell(new Position(pos.getX()+2, pos.getY()))==null|| Objects.equals(oppBoard.getCell(new Position(pos.getX() + 2, pos.getY())), "B") || Objects.equals(oppBoard.getCell(new Position(pos.getX() + 2, pos.getY())), "D") || Objects.equals(oppBoard.getCell(new Position(pos.getX() + 2, pos.getY())), "S") || Objects.equals(oppBoard.getCell(new Position(pos.getX() + 2, pos.getY())), "P")))
                                        return new Position(pos.getX(), pos.getY()+2);if(oppBoard.isInsideBoard(posUp) && oppBoard.getCell(posUp)==null) return posUp;
//                                if(oppBoard.isInsideBoard(posDown) && oppBoard.getCell(posDown)==null) return posDown;
                                    else if(oppBoard.isInsideBoard(posUp) && oppBoard.getCell(posUp) == null) return posUp;
                                }
//
                            }
                        }
                    }
                    for (Position surroundingPos : surroundingPoints) {
                        if(oppBoard.isInsideBoard(surroundingPos) &&
                                (Objects.equals(oppBoard.getCell(surroundingPos), "S")||Objects.equals(oppBoard.getCell(surroundingPos), "B")||Objects.equals(oppBoard.getCell(surroundingPos), "D")||Objects.equals(oppBoard.getCell(surroundingPos), "P")||oppBoard.getCell(surroundingPos)==null)){
                            return surroundingPos;
                        }
                    }
                }
            }
        }
        return findUnexploredPoint();
    }
    public void hitEasyMode() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ClearScreen.clrscr();
        oppBoard.printOppBoard();
        Random rand = new Random();
        Controller controller = new Controller();
        Position posHited = new Position(0,0);
        do{
            int x1 = rand.nextInt(10) + 1;
            int y1 = rand.nextInt(10) + 1;
            posHited.setPosition(y1, x1);
        }while(!controller.checkHit(oppBoard, posHited));
        if(Objects.equals(oppBoard.getCell(posHited), "S")||Objects.equals(oppBoard.getCell(posHited), "D")||Objects.equals(oppBoard.getCell(posHited), "B")||Objects.equals(oppBoard.getCell(posHited), "P")){
            ClearScreen.clrscr();
            System.out.println("Boom! Computer Hit Point: " + posHited.getY() + " "+ posHited.getX() + " !");
            String tmp = oppBoard.getCell(posHited);
            oppBoard.setCell(posHited, "*");
            oppBoard.printOppBoard();
            Music.Boom();
            Wait.wait(3);
            oppBoard.setCell(posHited, tmp + "X");
            if(checkWinner()){
                ClearScreen.clrscr();
                printBoard(yourBoard, oppBoard);
                System.out.println("Congratulations!!!Computer is Winner!!!");
                return;
            }
            //checkShipDestroyed and Win
            System.out.println("Continue Computer's Turn!");
            Wait.wait(3);
            hitEasyMode();
        }else{
            System.out.println("Computer Hit Point: " + posHited.getY() + " " +  posHited.getX() + " !Computer Miss!");
            Music.Miss();
            oppBoard.setCell(posHited, "M");
            System.out.println("Change Turn!");
            Wait.wait(3);
            ClearScreen.clrscr();
        }
    }
}
