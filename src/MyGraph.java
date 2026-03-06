import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.mutNode;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MyGraph {
    public MutableGraph mutGraph;

    public void parseGraph(String filepath) throws IOException {
        File f = new File(filepath);
        Parser p = new Parser();
        mutGraph = p.read(f);
    }

    public String toString(){
        return mutGraph.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        File f = new File(filepath);
        Graphviz.fromGraph(mutGraph).render(Format.DOT).toFile(f);
    }

    public void addNode(String label){
        for(MutableNode mn : mutGraph.nodes()){
            if(mn.name().toString().equals(label)){
                return;
            }
        }
        MutableNode mn = mutNode(label);
        mutGraph.add(mn);
    }

    public void addNodes(String[] labels){
        for(String str : labels){
            addNode(str);
        }
    }

    public void addEdge(String srcLabel, String dstLabel){
        MutableNode srcNode = null;
        MutableNode dstNode = null;
        for(MutableNode mn : mutGraph.nodes()){
            if(mn.name().toString().equals(srcLabel)){
                srcNode = mn;
            }
            if(mn.name().toString().equals(dstLabel)){
                dstNode = mn;
            }
        }
        if(srcNode != null && dstNode != null) {
            srcNode.addLink(dstNode);
        }
    }

    public void outputDOTGraph(String path) throws IOException {
        File f = new File(path);
        Graphviz.fromGraph(mutGraph).render(Format.DOT).toFile(f);
    }

    public void outputGraphics(String path, String format) throws IOException {
        File f = new File(path + "." + format);
        Graphviz.fromGraph(mutGraph).render(Format.PNG).toFile(f);
    }

    public MyGraph(){
        mutGraph = graph().toMutable().setDirected(true);
    }
}