package fr.umlv.info2.graphs;

public class Graphs {

	private static final Integer MAX_VALUE = 999;
	private static int[] d;
	private static int[] pi;
	
	public static int compare(int a1, int a2) {
		
		return (a1 < a2)? a1 : a2;
		
		
	}
	
	public static ShortestPathFromOneVertex bellmanFord(Graph g, int source) {
		
		d = new int[g.numberOfVertices()];
		pi = new int[g.numberOfVertices()];
		
		for (var s = 1; s <= g.numberOfVertices(); s++){
            d[s] = MAX_VALUE;
            pi[s] = -1;
        }
 
        d[source] = 0;
        pi[source] = source;
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
	
	
	
	public static ShortestPathFromOneVertex dijkstra(Graph g, int source) {
		
		int sommet = 0;
		d = new int[g.numberOfVertices()];
		pi = new int[g.numberOfVertices()];
		
		for (var s = 1; s <= g.numberOfVertices(); s++){
            d[s] = MAX_VALUE;
            pi[s] = -1;
        }
		
		d[0] = 0;
		pi[0]= 0;
		
		while(sommet !=-1) {
			g.forEachEdge(sommet, (edge)->{
				if(d[sommet] + edge.getValue() < d[edge.getEnd()]) {
    				d[edge.getEnd()] = d[sommet] + edge.getValue();
    				pi[edge.getEnd()] = sommet;
    			}
			});
		}
		
		return new ShortestPathFromOneVertex(0, d, pi);
	}
}
