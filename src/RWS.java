import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.RandomWalkVertexIterator;

import java.util.Arrays;
import java.util.Iterator;

public class RWS extends SearchStrategy{
    public Graph<String, DefaultEdge> g;
    public String src;
    @Override
    public Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src){
        System.out.println("random testing");
        this.g = g;
        this.src = src;
        return new RandomWalkVertexIterator<>(g, src);
    }

    @Override
    public Path tracePath(Iterator<String> a, Path p, String dst){
        while(p.length == 0 || !p.p[p.length-1].equals(dst)) {
            a = new RandomWalkVertexIterator<>(g, src);
            p = new Path();
            for (int i = 0; a.hasNext(); i++) {
                String v = a.next();

                if (Arrays.asList(p.p).contains(v)){
                    break;
                }

                p.addNodeAtIndex(v, i);
                p.printPath();

                if (v.equals(dst)) {
                    return p;
                }
            }
        }
        p.printPath();
        return null;
    }
}