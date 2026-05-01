import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.RandomWalkVertexIterator;

import java.util.Iterator;

public class RWS extends SearchStrategy{
    @Override
    public Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src){
        System.out.println("random testing");
        return new RandomWalkVertexIterator<>(g, src);
    }
}