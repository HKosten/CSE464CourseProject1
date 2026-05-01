import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;

public class BFS extends MyGraph{
    @Override
    public Iterator<String> iteratorAlg(String src){
        return new BreadthFirstIterator<>(g, src);
    }
}