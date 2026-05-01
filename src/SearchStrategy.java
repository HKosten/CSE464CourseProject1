import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Iterator;

public abstract class SearchStrategy{
    public abstract Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src);
}
