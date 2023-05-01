
public class OwOExecutionist {
    public static void main(String[] args) {
        Board board = new Board(8, 8);
        
        String[] sarr = {"1-2(1/1>)", "om1.(1/3)"};
        Piece p1 = new Piece(sarr);
        p1.mark = "N";
        p1.moveNames[0] = "ale";
        board.place(p1, 4, 4);

        Piece p2 = new Piece(sarr);
        p2.mark = "P";
        p2.moveNames[0] = "copak";
        p2.color = "\033[0;36m";
        board.place(p2, 1, 5);
        
        String[] sarr2 = {"1-2>.1="};
        Piece p3 = new Piece(sarr2);
        p3.mark = "T";
        p3.moveNames[0] = "maleVelkeT";
        p3.color = "\033[0;36m";
        board.place(p3, 2, 0);

        board.print();

        MoveInterpreter mi = new MoveInterpreter(null);
        mi.setBoard(board);

        //board.showMoves(mi.getValidMoves(p3.moves[0], 2, 0, p3));
        /*
        HashSet<int[]> hs = mi.getValidMoves(p1.moves[0], p1.x, p1.y, p1);
        java.util.Iterator<int[]> itr = hs.iterator();
        //board.showMoves(hs);
        System.out.println("pos: " + p1.x + ", " + p1.y);
        while(itr.hasNext()){
            System.out.print(Arrays.toString(itr.next()));
        }
        */

        Transmitter t = new Transmitter(board);
        String jsonString = t.validMovesToJSON();
        System.out.println(jsonString);
        System.out.println(board.piecist.toString());
        Bundle b = t.validMovesFromJSON(jsonString);
        System.out.println(b.X.get("1,5_copak").toString());

    }
}
