package fr.umlv.info2.graphs;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graphs {

	private static int[] d;
	private static int[] pi;
	
	public static int compare(int a1, int a2) {
		
		return (a1 < a2)? a1 : a2;
		
		
	}
	
	public static ShortestPathFromOneVertex bellmanFord(Graph g, int source) {
		
		d = new int[g.numberOfVertices()];
		pi = new int[g.numberOfVertices()];
		
		for (var s = 1; s < g.numberOfVertices(); s++){
            d[s] = Integer.MAX_VALUE;
            pi[s] = -1;
        }
 
        d[source] = 0;
        pi[source] = -1;
        for (int sommet = 1; sommet <= g.numberOfVertices() - 1; sommet++){ 
		
        	for (int snode = 0; snode < g.numberOfVertices(); snode++){
                
        		g.forEachEdge(snode, (edge) ->{
        			if(d[edge.getStart()] + edge.getValue() < d[edge.getEnd()]) {
        				d[edge.getEnd()] = d[edge.getStart()] + edge.getValue();
        				pi[edge.getEnd()] = edge.getStart();
        			}
        		});
            }
        }
        
        for(var i=0; i< g.numberOfVertices(); i++) {
        	g.forEachEdge(i, (edge)->{
        		if(d[edge.getStart()] + edge.getValue() < d[edge.getEnd()])
        			throw new IllegalStateException("Error it's a circuit");
        	});
        }
        
        
		return new ShortestPathFromOneVertex(source, d, pi);		
	}
	
	
	public static Integer extractMin(int[] d) {
		
		int min=0;
		
		for(var i=1; i<d.length; i++) {
			if(d[i] < d[min])
				min = i;
		}
		
		return min;
	}
	
	public static ShortestPathFromOneVertex dijkstra(Graph g, int source) {
		
		ArrayList<Integer> f = new ArrayList<>();
		d = new int[g.numberOfVertices()];
		pi = new int[g.numberOfVertices()];
		
		for (var v = source; v < g.numberOfVertices(); v++){
            f.add(v);
        }
		
		for (var s = source+1; s < g.numberOfVertices(); s++){
            d[s] = Integer.MAX_VALUE;
            pi[s] = -1;
        }
 
        d[source] = 0;
        pi[source] = -1;
		
        while(!f.isEmpty()) {
        	int min = extractMin(d);
        	var current = f.remove(min);
			g.forEachEdge(current, (edge)->{
				if(d[edge.getStart()] + edge.getValue() < d[edge.getEnd()]) {
    				d[edge.getEnd()] = d[edge.getStart()] + edge.getValue();
    				pi[edge.getEnd()] = edge.getStart();
    			}
			});
        }		
		
		return new ShortestPathFromOneVertex(0, d, pi);
	}
}
