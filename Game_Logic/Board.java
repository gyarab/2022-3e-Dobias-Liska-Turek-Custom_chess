import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    int width;
    int height;
    int ply, turn;
    Square[][] squarr;
    boolean[][][] binboard;
    ArrayList<Piece> piecist = new ArrayList<>();
    HashMap<Piece, ArrayList<int[]>> validMoves = new HashMap<>();
    

    public Board(int width, int height /*Rules rules, layout */) {
        this.width = width;
        this.height = height;
        this.squarr = new Square[height][width]; 

        fillWithSquares();
        binboard = new boolean[height][width][3];
    }

    public Piece gimme(int x, int y){
        return squarr[x][y].occupant;
    }

    public void place(Piece p, int x, int y){
        squarr[y][x].occupant = p;
        p.x = x;
        p.y = y;
        piecist.add(p);
        validMoves.put(p, null);
    }

    public void print(){
        String charBoard = "";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if(squarr[i][j].occupant != null){
                    charBoard += squarr[i][j].occupant.print();
                }
                else{
                    charBoard += (i + j) % 2 == 0 ? (char) '#' : (char) 'O';
                }
                charBoard += " ";
            }
            charBoard += "\n";
        }
        System.out.println(charBoard);
    }

    public void updateBoard(String s){
        //ConditionCommandInterpreter coci = new ConditionCommandInterpreter(this,);
        Piece p;
        String command;
        int lastX, lastY, x, y;
        String[] sarr = s.split("_", 0);

        String[] sarr2 = sarr[0].split("[^0-9]", 2);
        lastX = Integer.parseInt(sarr2[0]);
        lastY = Integer.parseInt(sarr2[1]);
        p = this.gimme(lastX, lastY);
        x = p.x; y = p.y;

        for (int i = 0; i < p.moveNames.length; i++) {
            if(p.moveNames[i].equals(sarr[1])) command = p.execute[i];
        }
        for (int i = 2; i < sarr.length; i++) {
            sarr2 = sarr[0].split("[^0-9]", 2);
            x = Integer.parseInt(sarr2[0]);
            y = Integer.parseInt(sarr2[1]);
            //offer case to coci
        }
        this.squarr[lastX][lastY].occupant = null;
        this.squarr[x][y].occupant = p;
        
    }

    /*public void showMoves(HashSet<int[]> hs){
        String charBoard = "";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if(squarr[i][j].occupant != null){
                    charBoard += squarr[i][j].occupant.print();
                }
                else{
                    for (int[] its : hs) {
                        if(its.equals(new int[] {i,j}))charBoard += "\033[41m" + "_";
                    }
                    charBoard += (i + j) % 2 == 0 ? (char) '#' : (char) 'O';
                    charBoard += "\033[0m";
                }
                charBoard += " ";
            }
            charBoard += "\n";
        }
        System.out.println(charBoard);
    }
    */

    private void fillWithSquares(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                squarr[i][j] = new Square();
            }
        }
    }

}
