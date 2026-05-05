import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MyGraph myg = new MyGraph();
        myg.parseGraph("input.dot");
        myg.graphSearch("a", "c", Algorithm.RWS);
    }
}