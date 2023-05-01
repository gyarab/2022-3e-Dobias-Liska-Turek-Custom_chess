import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConditionCommandInterpreter{
    Piece piece;
    String s;
    Board board;
    HashMap<String, ArrayList<Entity>> etarr;
    int ix, iy, nx, ny;

    public Entity satisfy(){
        Entity forReturn;
        String currEntity;

        String orderType; //"Command"
        String state = "Start"; //"area selection"
        String substate = "";
        
        int i = 0;
        while(i < s.length()){
            String state_snapshot = state;
            String substate_snapshot= substate;


            if(state == "Start"){
                char ch = s.charAt(i);
                if(ch == 'X') orderType = "Command";
                else if(ch == 'E') orderType = "Any";
                else if(ch == 'V') orderType = "All";

                state = "Search4EntityType";
                i++;
            }

            if(state == "Search4EntityType"){
                char ch = s.charAt(i);
                if("psxaeq".contains(""+ch)){
                    String name = "unnamed_"+ch;
                    if(s.charAt(i+1) == '('){
                        name = s.substring(i+2, s.indexOf(")", i+2));
                        i+= name.length()+2;
                    }
                    if(!etarr.keySet().contains(name)){
                        ArrayList<Entity> arr = new ArrayList<>();
                        if(ch == 's') arr.add(new Entity<Piece>(piece));
                        if(ch == 'q')
                            for (Square[] qar : board.squarr) {
                                for (Square q : qar) {
                                    arr.add(new Entity<Square>(q));
                                }
                            }
                        else for (Piece p : board.piecist) {
                            if(ch == 'p') arr.add(new Entity<Piece>(p));
                            //else if(ch == 's')arr.add(n);
                        }
                        etarr.put(name, arr);
                    }
                    currEntity = name;
                }
                i++;
            }


            if(state.equals(state_snapshot) && substate.equals(substate_snapshot)){
                state= "Search4Instruction";
                substate= "";
            }
        } 

        return new Entity<Boolean>(new Boolean(false));
    }

    public ConditionCommandInterpreter(Board board, Piece piece, String toSatisfy, int[] twoPositions){
        this.board = board;
        this.piece = piece;
        this.s = toSatisfy;
        this.ix = twoPositions[0];
        this.iy = twoPositions[1];
        this.nx = twoPositions[2];
        this.ny = twoPositions[3];

        //this.satisfy()
    }
}