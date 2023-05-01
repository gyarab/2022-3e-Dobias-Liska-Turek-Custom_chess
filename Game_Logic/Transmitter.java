import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Transmitter {
    Board board;

    // XXX
    public HashMap<String, ArrayList<String>> getPieces(){
        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        
        for (Piece p: board.piecist) {
            if(!hm.containsKey(p.name)){
                ArrayList<String> al = new ArrayList<>();
                hm.put(p.name, al);
            }
            hm.get(p.name).add(""+p.x+","+p.y);
        }
        return hm;
    }

    // XX
    public HashMap<String, HashSet<String>> getPosition2MoveNames(){
        HashMap<String, HashSet<String>> hm = new HashMap<>();
        for (Piece p : board.piecist) {
            HashSet<String> names = new HashSet<>();
            for (String s : p.moveNames) {
                names.add(s);
            }
            //hm.put(new int[] {p.x,p.y}, names);
            hm.put(""+ p.x + "," + p.y, names);
        }
        return hm;
    }

    // X
    public HashMap<String, HashMap<String, HashSet<String>>> getMNaP2RandomAccessPathTree(){
        HashMap<String, HashMap<String, HashSet<String>>> hm = new HashMap<>();
        for (Piece p : board.piecist) {
            for (int i = 0; i < p.moveNames.length; i++) {
                MoveInterpreter mi = new MoveInterpreter(null);
                mi.setBoard(board);
                /*System.out.println("----");
                System.out.println(p.moves[i]);
                System.out.println(p.name);
                System.out.println("---");*/
                mi.getValidMoves(p.moves[i], p.x, p.y, p);
                //HashMap<String, HashSet<int[]>> hm2 = mi.stm;
                hm.put(""+ p.x +","+ p.y +"_"+ p.moveNames[i], mi.stms);
                //stm jest HashMap<String, HashSet<int[]>>
            }
        }
        return hm;
    }

    public Transmitter(Board board) {
        this.board = board;
    
    }

    public String validMovesToJSON(){
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type bundleType = new TypeToken<Bundle>(){}.getType();

        Bundle bundle = new Bundle();
        bundle.X   = getMNaP2RandomAccessPathTree();
        bundle.XX  = getPosition2MoveNames();
        bundle.XXX = getPieces();

        String str = gson.toJson(bundle);
        return str;
        /*
        Type XXX = new TypeToken<HashMap<String, String>>(){}.getType();
        Type XX  = new TypeToken<HashMap<String, HashSet<String>>>(){}.getType();
        Type X   = new TypeToken<HashMap<String, HashMap<String, HashSet<String>>>>(){}.getType();
        Type XS  = new TypeToken<HashMap<String, Type>>(){}.getType(); 
        */
    }

    public Bundle validMovesFromJSON(String jsonString) {
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type bundleType = new TypeToken<Bundle>(){}.getType();
        Bundle bundle = gson.fromJson(jsonString, bundleType);
        return bundle;
    }
    
}
