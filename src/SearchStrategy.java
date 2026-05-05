import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Iterator;

public abstract class SearchStrategy{
    public abstract Iterator<String> iteratorAlg(Graph<String, DefaultEdge> g, String src);

    public Path tracePath(Iterator<String> a, Path p, String dst){
        for(int i = 0; a.hasNext(); i++){
            String v = a.next();
            p.addNodeAtIndex(v, i);
            p.printPath();
            if(v.equals(dst)){
                return p;
            }
        }
        return null;
    }
}
