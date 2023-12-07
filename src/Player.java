import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Player extends Board{
    private String name;
    public Board yourBoard = new Board();
    public Board oppBoard = new Board();
    private final Ship[] shipList = new Ship[5];
    public int cntShip = 0;
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public boolean checkWinner(){
        int cntX = 0;
         for(int i = 1; i <= 10; i++){
             for(int j = 1; j <= 10; j++){
                 Position pos = new Position(i, j);
                 if(Objects.equals(oppBoard.getCell(pos), "PX")||Objects.equals(oppBoard.getCell(pos), "BX")||Objects.equals(oppBoard.getCell(pos), "SX")||Objects.equals(oppBoard.getCell(pos), "DX")) cntX++;
             }
         }
        return cntX == 16;
    }
    public void setShipOnBoardAuto(){
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
            // Tạo vị trí ngẫu nhiên
            Position posStart, posEnd;
            do {
                int x1 = rand.nextInt(10) + 1;
                int y1 = rand.nextInt(10) + 1;
                // Ngẫu nhiên chọn hướng (ngang hoặc dọc)
                direction = rand.nextInt(2);
                int x2, y2;
                if (direction == 0) {
                    // Ngang
                    x2 = x1 + sizeShip - 1;
                    y2 = y1;
                } else {
                    // Dọc
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
        }
        System.out.println("All your Ship are set on Board");
        yourBoard.printYourBoard();
    }
    public void hitShip() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Controller controller = new Controller();
        FillColor fill = new FillColor();
        fill.initColor();
        printBoard(yourBoard, oppBoard);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the coordinate: ");
        System.out.println("x, y: ");
        int x = sc.nextInt();
        int y = sc.nextInt();
        Position posHited = new Position(y, x);
        while(!controller.checkHit(oppBoard, posHited)){
            System.out.println("Coordinate is invalid! Please enter again: ");
            System.out.println("x, y: ");
            x = sc.nextInt();
            y = sc.nextInt();
            posHited.setPosition(y, x);
        }
        if(Objects.equals(oppBoard.getCell(posHited), "S")||Objects.equals(oppBoard.getCell(posHited), "D")||Objects.equals(oppBoard.getCell(posHited), "B")||Objects.equals(oppBoard.getCell(posHited), "P")){
            ClearScreen.clrscr();
            System.out.println("Boom!" + name + " Hit!");
            String tmp = oppBoard.getCell(posHited);
            oppBoard.setCell(posHited, "*");
            printBoard(yourBoard, oppBoard);
            Music.Boom();
            Wait.wait(3);
            oppBoard.setCell(posHited, tmp + "X");
            if(checkWinner()){
                ClearScreen.clrscr();
                printBoard(yourBoard, oppBoard);
                System.out.println("Congratulations!!! " + name + " is Winner!!!");
                return;
            }
            //checkShipDestroyed and Win
            System.out.println("Continue Your Turn!");
            ClearScreen.clrscr();
            System.out.println(name + "'s turn!");
            hitShip();
        }else{
            System.out.println("Oh no! You Miss!");
            Music.Miss();
            oppBoard.setCell(posHited, "M");
            System.out.println("Change Turn!");
            PressEnterToContinue.pressEnterToContinue();
            ClearScreen.clrscr();
        }
    }
    public void setShipOnBoard(){
        while (cntShip < 5) {
            System.out.println("Let's set up ship on your sea!!!");
            yourBoard.printYourBoard();
            Scanner sc = new Scanner(System.in);
            String name;
            int sizeShip;
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
            System.out.println("Set your " + name + " with size " + sizeShip + " on your board:");
            shipList[cntShip] = new Ship();
            shipList[cntShip].setSizeShip(sizeShip);
            shipList[cntShip].setName(name);
            System.out.println("Enter coordinate with form x1, y1, x2, y2: ");
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            Position posStart = new Position(y1, x1);
            Position posEnd = new Position(y2, x2);
            shipList[cntShip].setPosShip(posStart, posEnd);
            Controller control = new Controller();
            while(!control.checkPosShipOnBoard(yourBoard, shipList[cntShip])){
                System.out.println("Coordinate is invalid! Please enter again: ");
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                x2 = sc.nextInt();
                y2 = sc.nextInt();
                posStart.setPosition(y1, x1);
                posEnd.setPosition(y2, x2);
                shipList[cntShip].setPosShip(posStart, posEnd);
            }
            if(shipList[cntShip].getPosStart().getX() == shipList[cntShip].getPosEnd().getX()){
                for(int i = Math.min(shipList[cntShip].getPosStart().getY(), shipList[cntShip].getPosEnd().getY()); i <= Math.max(shipList[cntShip].getPosStart().getY(), shipList[cntShip].getPosEnd().getY()); i++){
                    Position pos = new Position(shipList[cntShip].getPosStart().getX(), i);
                    yourBoard.setCell(pos, String.valueOf(shipList[cntShip].getName().charAt(0)));
                }
            }else{
                for(int i = Math.min(shipList[cntShip].getPosStart().getX(), shipList[cntShip].getPosEnd().getX()); i <= Math.max(shipList[cntShip].getPosStart().getX(), shipList[cntShip].getPosEnd().getX()); i++){
                    Position pos = new Position(i, shipList[cntShip].getPosStart().getY());
                    yourBoard.setCell(pos, String.valueOf(shipList[cntShip].getName().charAt(0)));
                }
            }
            cntShip++;
            ClearScreen.clrscr();
        }
        System.out.println("All your Ship are set on Board");
        yourBoard.printYourBoard();
        PressEnterToContinue.pressEnterToContinue();
    }
}
