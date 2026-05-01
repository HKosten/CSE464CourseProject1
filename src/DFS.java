import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;

public class DFS extends MyGraph{
    @Override
    public Iterator<String> iteratorAlg(String src){
        return new DepthFirstIterator<>(g, src);
    }
}

