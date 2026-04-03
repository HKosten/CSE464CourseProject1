import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.ParserException;
import org.jgrapht.Graph;
import org.jgrapht.nio.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

        g.addVertex("a");
        g.addVertex("b");
        g.addVertex("c");
        g.addVertex("d");

        g.addEdge("a", "b");
        g.addEdge("b", "c");
        g.addEdge("c", "d");
        //act
        myg.parseGraph("ParseInput.dot");

        //assert
        Assertions.assertEquals(g.toString(), myg.g.toString());
    }

    @Test
    public void testParseGraphNoFile(){
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            myg.parseGraph("NotAFile.txt");
        });
    }

    @Test
    public void testParseGraphSyntaxError() {
        Assertions.assertThrows(ImportException.class, () -> {
            myg.parseGraph("ParseSyntaxErrorInput.dot");
        });
    }

    @Test
    public void testToString() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        String str = "([a, b, c, d], [(a,b), (b,c), (c,d)])";

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
    public void testAddNodes() throws IOException {
        String[] labels = {"a", "b", "c"};
        myg.addNodes(labels);

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddNodesExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testAddEdge() throws IOException {
        myg.addNode("a");
        myg.addNode("b");

        myg.addEdge("a", "b");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddEdgeExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testAddEdgeNoNodes() throws IOException {
        myg.addEdge("a", "b");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("AddEdgeNoNodesExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testOutputDOTGraph() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        myg.outputDOTGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("GeneralExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testOutputGraphics() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        myg.outputGraphics("Output", "png");

        Assertions.assertTrue(Files.exists(Path.of("Output.png")));
    }

    @Test
    public void testOutputGraphicsExtension() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        myg.outputGraphics("Output", "png");
        Path p = Path.of("Output.png");
        String type = Files.probeContentType(p);

        Assertions.assertEquals("image/png", type);
    }

    @Test
    public void testRemoveNode() throws Exception {
        myg.parseGraph("GeneralInput.dot");

        myg.removeNode("a");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("RemoveNodeExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testRemoveNodeEmpty() throws Exception {
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            myg.removeNode("a");
        });

        Assertions.assertEquals("Node a does not exist", e.getMessage());
    }

    @Test
    public void testRemoveNodes() throws Exception {
        myg.parseGraph("GeneralInput.dot");

        String[] label = {"a", "b"};
        myg.removeNodes(label);

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("RemoveNodesExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testRemoveEdge() throws Exception {
        myg.parseGraph("GeneralInput.dot");

        myg.removeEdge("a", "b");

        myg.outputGraph("Output.dot");

        Assertions.assertEquals(Files.readAllLines(Path.of("RemoveEdgeExpected.dot")), Files.readAllLines(Path.of("Output.dot")));
    }

    @Test
    public void testRemoveEdgeEmpty(){
        Exception e = Assertions.assertThrows(Exception.class, () -> {
            myg.removeEdge("a", "b");
        });

        Assertions.assertEquals("Edge a -> b does not exist", e.getMessage());
    }

    @Test
    public void testGraphSearchBFS() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        String[] p = myg.graphSearch("a", "c", Algorithm.BFS).p;

        String[] a = new String[16];
        a[0] = "a";
        a[1] = "b";
        a[2] = "c";

        Assertions.assertArrayEquals(a, p);
    }

    @Test
    public void testGraphSearchDFS() throws IOException {
        myg.parseGraph("GeneralInput.dot");

        String[] p = myg.graphSearch("a", "c", Algorithm.DFS).p;

        String[] a = new String[16];
        a[0] = "a";
        a[1] = "b";
        a[2] = "c";

        Assertions.assertArrayEquals(a, p);
    }
}