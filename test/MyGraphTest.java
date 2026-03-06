import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static guru.nidi.graphviz.model.Factory.*;

public class MyGraphTest {
    MyGraph myg;

    @BeforeEach
    public void setup(){
        myg = new MyGraph();
    }

    @Test
    public void testParseGraph() throws IOException {
        //arrange
        MutableGraph mutg = mutGraph().setDirected(true);
        MutableNode a = mutNode("a");
        MutableNode b = mutNode("b");
        MutableNode c = mutNode("c");
        MutableNode d = mutNode("d");

        a.addLink(b);
        b.addLink(c);
        c.addLink(d);

        mutg.add(a);
        mutg.add(b);
        mutg.add(c);
        mutg.add(d);

        //act
        myg.parseGraph("ParseInput.dot");

        //assert
        Assertions.assertEquals(mutg.toString(), myg.mutGraph.toString());
    }

    @Test
    public void testParseGraphNoFile(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            myg.parseGraph("NotAFile.txt");
        });
    }

    @Test
    public void testParseGraphSyntaxError() {
        Assertions.assertThrows(ParserException.class, () -> {
            myg.parseGraph("ParseSyntaxErrorInput.dot");
        });
    }

    @Test
    public void testToString() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        String str = "digraph {\n" +
                "\"a\" -> \"b\"\n" +
                "\"b\" -> \"c\"\n" +
                "\"c\" -> \"d\"\n" +
                "}";

        Assertions.assertEquals(str, myg.toString());
    }

    @Test
    public void testOutputGraph() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("GeneralExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testAddNode() throws IOException {
        myg.addNode("a");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddNodeExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testAddDuplicateNode() throws IOException {
        myg.addNode("a");
        myg.addNode("a");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddNodeExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public  void testAddNodes() throws IOException {
        String[] labels = {"a", "b", "c"};
        myg.addNodes(labels);

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddNodesExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }
}
