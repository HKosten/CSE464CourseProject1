import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Iterator;

public interface SearchStrategy{
    public Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src);
}
