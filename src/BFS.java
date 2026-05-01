import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Iterator;

public class BFS extends SearchStrategy{
    @Override
    public Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src){
        return new BreadthFirstIterator<>(g, src);
    }
}

