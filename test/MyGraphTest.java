import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

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
        myg.parseGraph2("ParseInput.dot");

        //assert
        Assertions.assertEquals(mutg.toString(), myg.mutGraph.toString());
    }

    @Test
    public void testParseGraphNoFile(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            myg.parseGraph2("NotAFile.txt");
        });
    }

    @Test
    public void testParseGraphSyntaxError() {
        Assertions.assertThrows(ParserException.class, () -> {
            myg.parseGraph2("ParseSyntaxErrorInput.dot");
        });
    }

    @Test
    public void testToString() throws IOException {
        myg.parseGraph2("GeneralInput.dot");

        String str = "digraph {\n" +
                "\"a\" -> \"b\"\n" +
                "\"b\" -> \"c\"\n" +
                "\"c\" -> \"d\"\n" +
                "}";

        Assertions.assertEquals(str, myg.toString2());
    }

    @Test
    public void testOutputGraph() throws IOException {
        myg.parseGraph2("GeneralInput.dot");

        myg.outputGraph2("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("GeneralExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }
}
