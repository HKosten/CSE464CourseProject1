import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;

public class DFS extends SearchStrategy{
    @Override
    public Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src){
        System.out.println("DFS testing");
        return new DepthFirstIterator<>(g, src);
    }
}

