import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class MoveInterpreter {
    Board board;
    private MoveInterpreter olderMI;

    boolean mustCapture, mustNotCapture, leap, hop;
    int stepA, stepB, start, end;

    HashMap<String, HashSet<int[]>> stm; //přístup mají i gVM od rekurzivně vytvářených MI, viz memorize()
    HashMap<String, HashSet<String>> stms;
    Set<int[]> dirs;

    public HashSet<int[]> getValidMoves(String s, int x, int y, Piece p){
        System.out.println(s);
        HashSet<int[]> validMoves = new HashSet<>();
        int i = 0;
        System.out.println("gVM");

        if(ch(s, i) == 'i' && p != null){
            if(p.counters.get("moved").intValue() > 0) return null;
            i++;
        }

        mustCapture = false;
        mustNotCapture = false;
        if(ch(s, i) == 'c') {mustCapture = true; i++;}
        if(ch(s, i) == 'o') {mustNotCapture = true; i++;}
        leap = false;
        hop = false;

        start = 1;
        end = 1;
        boolean intervalFound = false;

        
        boolean continues = true;
        while(continues){
            continues = false;
            if(i >= s.length()) return validMoves; //>=
            //m1 , n0X n0() , 1/2
            //minibinboard

            if(ch(s, i) == 'm'){ // odkaz na jiné move figury
                String[] sarr = s.split("m", 2);
                String[] sarr2 = sarr[1].split("[^0-9]", 2);
                String[] sarr3 = sarr[1].split(sarr2[0], 2);
                try {//           vse pred m,             urcite remoat move,                                              rozdelovaci znak po cisle indexu + zbytek
                    String newS = sarr[0].concat("(").concat(p.moves[Integer.parseInt(sarr2[0]) - 1]).concat(")").concat(sarr3[1]);
                    System.out.println(newS);
                    return this.getValidMoves(newS, x, y, p);
                } catch (IndexOutOfBoundsException e) {
                    //System.out.println("ajaj tady pak musis vratit prazdny seznam");
                }
            }
            
            if(ch(s, i) == 'n'|| (s.substring(i, i+1)).matches("[0-9]")){
                char c = '°';
                /* 
                if(ch(s, i) == 'n'){
                    start = 1;
                    end = Integer.MAX_VALUE;
                    if (ch(s, i-1) == '0') start = 0;
                    i++;
                    //intervalFound = true;
                }
                */
                {
                    int at;
                    String[] sarr = s.substring(i).split("[^0-9]", 2);
                    c = s.charAt(i + (at = sarr[0].length()));
                    String[] sarr2 = s.substring(i + ++at).split("[^0-9]", 2);

                    stepA = 1;
                    stepB = 1;
                    at += sarr2[0].length();
                    boolean notActually = false;

                    switch(c){
                        
                        case 'n':
                        continues = true;
                        //i+= sarr[0].length();
                        if (ch(s, i) == '0') start = 0;
                        end = 50;//int max
                        intervalFound = true;
                        i += at;
                        break;
                        
                        case '/':
                        if(!intervalFound){start = 1; end = 1;}

                        stepA = Integer.parseInt(sarr[0]);
                        stepB = 0;
                        if(sarr2[0] != "") stepB= Integer.parseInt(sarr2[0]);
                        i += at;
                        break;

                        case '-':
                        start = Integer.parseInt(sarr[0]);
                        end = start;
                        if(sarr2[0] != "") end = Integer.parseInt(sarr2[0]);
                        i += at;
                        c = ch(s, i);
                        i += 1;
                        intervalFound = true;
                        break;

                        default:
                        if(sarr[0] != "") start = Integer.parseInt(sarr[0]);
                        end = start;
                        System.out.println(start);
                        System.out.println("def");
                        i += at;
                    }
                    if(!notActually) intervalFound = true;

                }
                //Iterator callllll
                if("()".contains(""+c)){continues = true; continue;}
                if(intervalFound){
                    System.out.println("i = " + i);
                    Iterator itr = new Iterator(p, s, c, ch(s, i), this, x, y);
                    validMoves.addAll(itr.gimmeSet());//ale ja nechci AbstractCollection.add ale Set.add
                    System.out.println("postIrer: " + validMoves.toString());
                    //memorize(key(x,y,s), validMoves);
                    int dot = s.indexOf('.');
                    if(dot != -1) i = dot;
                }
            }

            if(ch(s, i) == '(' || ch(s, i) ==')'){i++; continues = true;}
            System.out.println(ch(s,i));
            if(ch(s, i) == '.'){
                //HashSet<int[]> hs = new HashSet<>(validMoves.size()*8);
                String newS = s.substring(++i);
                System.out.println(newS);//oooo
                System.out.println("newMIs");
                for (int[] js : validMoves) {
                    MoveInterpreter mi = new MoveInterpreter(this);
                    //memorize(js[0] + js[1] + newS, 
                    memorize(key(js[0], js[1], newS), mi.getValidMoves(newS, js[0], js[1], p));;
                    /*for (HashSet<int[]> hs : mi.stm.values()) {
                        memorize(key(js[0], js[1], newS), hs);
                    }
                    */
                }
            }

            System.out.println("i: " + i);
        }



        memorize(key(x,y,s), validMoves);
        System.out.println("gVMend");

        //if(olderMI == null){
        return remember(key(x, y, s));
        //}else return null;

        //return validMoves;
    }

    public static String key(int x, int y, String s) {
        String keyS = x + "," + y + "_" + s;
        return keyS;
    }

    private static char ch(String s, int i){
        try {
            return s.charAt(i);
        } catch (IndexOutOfBoundsException e) {
            return '°';
        }
    }

    private void memorize(String s, HashSet<int[]> hs){
        if (olderMI != null) olderMI.memorize(s, hs);
        else {
            HashSet<String> hs2 = new HashSet<>();
            for (int[] its : hs) {
                String str = "" + (s.contains(".") ? -its[0] : its[0]) + "," + its[1];
                hs2.add(str);
            }
            this.stm.put(s, hs);
            this.stms.put(s, hs2);
        }

    }

    public void forget() { //only the oldest
        this.stm.clear();
    }

    public HashSet<int[]> remember(String s) {
        if(this.olderMI == null){
            System.out.println("remember");
            return this.stm.get(s);
        }
        else return olderMI.remember(s);
    }

    public MoveInterpreter (MoveInterpreter olderMI){
        if (olderMI != null) {
            this.olderMI = olderMI;
            this.board = olderMI.board;
        }
        else{
            this.stm = new HashMap<String, HashSet<int[]>>();
            this.stms = new HashMap<String, HashSet<String>>();
        }
    }

    public void setBoard(Board board){
        this.board = board;
    }
}
