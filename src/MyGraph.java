import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static java.lang.module.ModuleDescriptor.read;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MyGraph {
    public Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
    SearchStrategy ss;

    public void parseGraph(String filepath) throws IOException {
        FileReader f = new FileReader(filepath);

        DOTImporter<String, DefaultEdge> i = new DOTImporter<>();
        setupImporter(i);

        i.importGraph(g, f);
    }

    public void setupImporter(DOTImporter<String, DefaultEdge> i){
        i.setVertexFactory(id -> id);
        i.setEdgeWithAttributesFactory(e -> new DefaultEdge());
    }

    public String toString(){
        return g.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        FileWriter f = new FileWriter(filepath);
        DOTExporter<String, DefaultEdge> e = new DOTExporter<>(v -> v);
        e.exportGraph(g, f);
    }

    public void addNode(String label){
        for(String v : g.vertexSet()){
            if(v.equals(label)){
                return;
            }
        }
        g.addVertex(label);
    }

    public void addListOfNodes(String[] labels){
        for(String str : labels){
            addNode(str);
        }
    }

    public void addEdge(String srcLabel, String dstLabel){
        String srcNode = null;
        String dstNode = null;
        for(String v : g.vertexSet()){
            if(v.equals(srcLabel)){
                srcNode = v;
            }
            if(v.equals(dstLabel)){
                dstNode = v;
            }
        }
        if(srcNode != null && dstNode != null) {
            g.addEdge(srcLabel, dstLabel);
        }
    }

    public void outputDOTGraph(String path) throws IOException {
        FileWriter f = new FileWriter(path);
        DOTExporter<String, DefaultEdge> e = new DOTExporter<>(v -> v);
        e.exportGraph(g, f);
    }

    public void outputGraphics(String path, String format) throws IOException {
        outputDOTGraph("temp.dot");
        File f1 = new File("temp.dot");
        File f2 = new File(path + "." + format);
        executeOutput(f1, f2);
    }

    public void executeOutput(File input, File output) throws IOException {
        Parser p = new Parser();
        MutableGraph mutGraph = p.read(input);
        Graphviz.fromGraph(mutGraph).render(Format.PNG).toFile(output);
    }

    public void removeNode(String label) throws Exception {
        if(g.vertexSet().contains(label)){
            g.removeVertex(label);
        }
        else {
            throw new Exception("Node " + label + " does not exist");
        }
    }

    public void removeListOfNodes(String[] labels) throws Exception {
        for(String s : labels){
            removeNode(s);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) throws Exception {
        for (DefaultEdge edge : g.edgeSet()) {
            if (edgeHasSrcAndDst(edge, srcLabel, dstLabel)) {
                g.removeEdge(edge);
                return;
            }
        }
        throw new Exception("Edge a -> b does not exist");
    }

    public boolean edgeHasSrcAndDst(DefaultEdge edge, String srcLabel, String dstLabel){
        return g.getEdgeSource(edge).equals(srcLabel) && g.getEdgeTarget(edge).equals(dstLabel);
    }

    public final Path graphSearch(String src, String dst, Algorithm algo){
        setSearchStrategy(algo);

        Iterator<String> a = null;
        a = ss.iteratorAlg(g, src);

        Path p = new Path();
        return tracePath(a, p, dst);
    }

    public Path tracePath(Iterator<String> a, Path p, String dst){
        for(int i = 0; a.hasNext(); i++){
            String v = a.next();
            p.addNodeAtIndex(v, i);
            if(v.equals(dst)){
                return p;
            }
        }
        return null;
    }

    public void setSearchStrategy(Algorithm algo){
        if(algo == Algorithm.BFS){
            this.ss = new BFS();
        }
        if(algo == Algorithm.DFS){
            this.ss = new DFS();
        }
    }
}