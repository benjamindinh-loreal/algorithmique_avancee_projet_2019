package fr.umlv.info2.astar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.IntStream;

import fr.umlv.info2.graphs.*;
import fr.umlv.info2.parser.Parser;

public class Astar {
	
	private long[][] coordinate;
	private ArrayList<Integer> border = new ArrayList<Integer>();
	private ArrayList<Integer> computed = new ArrayList<Integer>();
		
	private void initH(float[] h, int to) {
		
		IntStream.range(0, h.length).forEach((index)->{
			long x1 = coordinate[index][0], x2 = coordinate[to][0],
					y1 = coordinate[index][1], y2 = coordinate[to][1] ;
			
			h[index] = (long) Math.sqrt(Math.pow((x2-x1),2)+Math.pow(y2-y1,2)) ;
		});
	
	}
	
	public ArrayList<Edge> getEdges(){
		return null ;
	}
	
	public ShortestPathFromOneVertex Astar(Graph graph, long[][] coordinate , int source, int dest) {
		
		this.coordinate = coordinate ;
		Node[][] voisins = new Node[graph.numberOfVertices()][2] ;
		int[] pi = new int[graph.numberOfVertices()] ;
		int[] distance = new int[graph.numberOfVertices()] ;
		float[] heuristique = new float[graph.numberOfVertices()] ;
		PriorityQueue<Node> f = new PriorityQueue<Node>(graph.numberOfVertices()) ;
		HashMap<Integer, Node> saveNode = new HashMap<>() ;
		
		IntStream.range(0, graph.numberOfVertices()).forEach((index)->{
			// Init distance
			if(index == 0) {
				distance[index] = 0 ;
				pi[source] = source;
			}
			else {
				distance[index] = Integer.MAX_VALUE ;
				pi[source] = -1;
			}
			//Init heuristique
			initH(heuristique,dest);
		});
		
		border.add(source);
		computed.add(source);
		f.add(new Node(source,distance[source]));
		
		
		while(!border.isEmpty()) {
			
			var min = f.poll();
			int current = border.remove(min.getId());
			
			if(current == dest) {
				break;
			}
			
			computed.add(current);
			saveNode.put(source, new Node(source,distance[source]));
			
			graph.forEachEdge(current, (edge) ->{
				if(computed.contains(edge.getEnd())) {
	    			if(distance[edge.getStart()] + edge.getValue() < distance[edge.getEnd()]) {
	    				distance[edge.getEnd()] = distance[edge.getStart()] + edge.getValue();
	    				pi[edge.getEnd()] = edge.getStart();
	    				Node sn = saveNode.remove(current);
	    				f.remove(sn);
	    					    				
	    				f.add(new Node(edge.getEnd(),distance[edge.getEnd()] + heuristique[edge.getEnd()]));
	    				saveNode.put(edge.getEnd(), new Node(edge.getEnd(),distance[edge.getEnd()] + heuristique[edge.getEnd()]));
	    				
	    				if(!border.contains(edge.getEnd()))
	    					border.add(edge.getEnd());
	    			}
				}
    			else {
    				distance[edge.getEnd()] = distance[edge.getStart()] + edge.getValue();
    				f.add(new Node(edge.getEnd(),distance[edge.getEnd()] + heuristique[edge.getEnd()]));
    				border.add(edge.getEnd());
    				computed.add(edge.getEnd());
    			}
    		});
			
		}
		return new ShortestPathFromOneVertex(source, distance, pi);
		
		
	}
}
