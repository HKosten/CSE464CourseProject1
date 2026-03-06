import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.graph;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MyGraph {
    public guru.nidi.graphviz.model.MutableGraph mutGraph;

    public void parseGraph2(String filepath) throws IOException {
        File f = new File(filepath);
        Parser p = new Parser();
        mutGraph = p.read(f);
    }

    public String toString2(){
        return mutGraph.toString();
    }

    public void outputGraph2(String filepath) throws IOException {
        File f = new File(filepath);
        Graphviz.fromGraph(mutGraph).render(Format.DOT).toFile(f);
    }

    public MyGraph(){
        mutGraph = graph("My MyGraph").toMutable();
    }
}