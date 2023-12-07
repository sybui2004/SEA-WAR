import java.util.Objects;

public class Board{
    FillColor fill = new FillColor();
    private final int SIZE_BOARD = 10;
    private final String[][] board = new String[SIZE_BOARD+1][SIZE_BOARD+1];
//    public void setSIZE_BOARD(int x) {
//        SIZE_BOARD = x;
//    }
    public void setCell(Position pos, String str) {
        board[pos.getX()][pos.getY()] = str;
    }
    public String getCell(Position pos) {
        return board[pos.getX()][pos.getY()];
    }
    public void initBoard(){
        for(int i = 1; i <= 10; i++){
            for(int j = 1; j <= 10; j++){
                board[i][j] = null;
            }
        }
    }
    public boolean isInsideBoard(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return x >= 1 && x <= 10 && y >= 1 && y <= 10;
    }
    public void printYourBoard(){
        fill.initColor();
        for(int i = 0; i < 44; i++) System.out.print("_");
        System.out.println();
        for (int i = 0; i <= SIZE_BOARD; i++) {
            for (int j = 0; j <= SIZE_BOARD; j++) {
                if (i == j && i == 0) System.out.print("|   ");
                else if (j == 0 || i == 0) System.out.printf("|%-3d", ((i == 0) ? j : i));
                else{
                    Position pos = new Position(i, j);
                    if(getCell(pos) == null) System.out.printf("|%-3s", fill.color.get("W") + "   " + FillColor.RESET);
                    else{
                        System.out.print("|");
                        System.out.print(fill.color.get(getCell(pos)));
                        System.out.printf("%-3s", getCell(pos));
                        System.out.print(FillColor.RESET);
                    }
                }
                if(j == SIZE_BOARD) System.out.println("|");
            }
            for(int p = 0; p < 44; p++) System.out.print("-");
            System.out.println();
        }
        System.out.println();
    }
    public void printOppBoard(){
        fill.initColor();
        for(int i = 0; i < 44; i++) System.out.print("_");
        System.out.println();
        for (int i = 0; i <= SIZE_BOARD; i++) {
            for (int j = 0; j <= SIZE_BOARD; j++) {
                if (i == j && i == 0) System.out.print("|   ");
                else if (j == 0 || i == 0) System.out.printf("|%-3d", ((i == 0) ? j : i));
                else{
                    Position pos = new Position(i, j);
                    if(getCell(pos) == null) System.out.printf("|%-3s", fill.color.get("W") + "   " + FillColor.RESET);
                    else if(Objects.equals(getCell(pos), "*") ||getCell(pos).length() == 2){
                        System.out.print("|");
                        System.out.print(fill.color.get("X"));
                        System.out.printf("%-3s", getCell(pos).charAt(0));
                        System.out.print(FillColor.RESET);
                    }else if(Objects.equals(getCell(pos), "M")){
                        System.out.print("|");
                        System.out.print(fill.color.get("M") + FillColor.BLACK);
                        System.out.printf("%-3s", getCell(pos));
                        System.out.print(FillColor.RESET);
                    }else System.out.printf("|%-3s", fill.color.get("W") + "   " + FillColor.RESET);

                }
                if(j == SIZE_BOARD) System.out.println("|");
            }
            for(int p = 0; p < 44; p++) System.out.print("-");
            System.out.println();
        }
        System.out.println();
    }
    public void printBoard(Board yourBoard, Board oppBoard) {
        System.out.println("                      Your Board                                    Opp Board");
        fill.initColor();
        for(int i = 0; i < 93; i++) System.out.print("_");
        System.out.println();
        for (int i = 0; i <= SIZE_BOARD; i++) {
            for (int j = 0; j <= SIZE_BOARD*2 + 4; j++) {
                if(j <= SIZE_BOARD){
                    if(i == j && i == 0) System.out.print("|   ");
                    else if(j == 0||i==0) System.out.printf("|%-3d", ((i==0)?j : i));
                    else {
                        Position pos = new Position(i, j);
                        if(yourBoard.getCell(pos) == null) System.out.printf("|%-3s", fill.color.get("W") + "   " + FillColor.RESET);
                        else if(Objects.equals(yourBoard.getCell(pos), "S")||Objects.equals(yourBoard.getCell(pos), "B")||Objects.equals(yourBoard.getCell(pos), "P")||Objects.equals(yourBoard.getCell(pos), "D")){
                            System.out.print("|");
                            System.out.print(fill.color.get(yourBoard.getCell(pos)));
                            System.out.printf("%-3s", yourBoard.getCell(pos));
                            System.out.print(FillColor.RESET);
                        }else if(yourBoard.getCell(pos).length() == 2){
                            System.out.print("|");
                            System.out.print(fill.color.get("X"));
                            System.out.printf("%-3s", yourBoard.getCell(pos).charAt(0));
                            System.out.print(FillColor.RESET);
                        }else{
                            System.out.print("|");
                            System.out.print(fill.color.get("M") + FillColor.BLACK);
                            System.out.printf("%-3s", "M");
                            System.out.print(FillColor.RESET);
                        }
                    }
                    if(j == SIZE_BOARD) System.out.print("|");
                }
                else if(j <= SIZE_BOARD + 3){
                    System.out.print("~");
                }
                else{
                    if (j == SIZE_BOARD + 4 && i == 0) System.out.print("|   ");
                    else if(j == SIZE_BOARD + 4||i == 0) System.out.printf("|%-3d", ((i==0)? j-14  : i ));
                    else {
                        Position pos = new Position(i, j-14);
                        if(oppBoard.getCell(pos) == null || Objects.equals(oppBoard.getCell(pos), "S")||Objects.equals(oppBoard.getCell(pos), "B")||Objects.equals(oppBoard.getCell(pos), "P")||Objects.equals(oppBoard.getCell(pos), "D"))
                            System.out.printf("|%-3s", fill.color.get("W") + "   " + FillColor.RESET);
                        else if (Objects.equals(oppBoard.getCell(pos), "*") || oppBoard.getCell(pos).length() == 2) {
                            System.out.print("|");
                            System.out.print(fill.color.get("X"));
                            System.out.printf("%-3s", oppBoard.getCell(pos).charAt(0));
                            System.out.print(FillColor.RESET);
                        } else {
                            System.out.print("|");
                            System.out.print(fill.color.get("M") + FillColor.BLACK);
                            System.out.printf("%-3s", "M");
                            System.out.print(FillColor.RESET);
                        }
                    }
                }
            }
            System.out.println("|");
            for(int p = 0; p < 93; p++){
                if(p >= 45 && p <= 47) System.out.print("~");
                else if(p==44||p==48) System.out.print("|");
                else System.out.print("-");
            }
            System.out.println();
        }
        for(int i = 0; i < 93; i++) System.out.print("_");
        System.out.println();
    }
    public void printBoardNoel(){

    }
    public void printYourBoardNoel(){

    }
}
