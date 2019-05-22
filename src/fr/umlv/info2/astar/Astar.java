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

import fr.umlv.info2.graphs.Edge;
import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Node;
import fr.umlv.info2.parser.Parser;

public class Astar {
	
	private long[][] coordinate;
		
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
	
	public Astar(Graph graph, long[][] coordinate , int source, int dest) {
		
		this.coordinate = coordinate ;
		
		Node[][] voisins = new Node[graph.numberOfVertices()][2] ;
		
		HashMap<Integer, Node> saveNode = new HashMap<>() ;
		
		int[] pi = new int[graph.numberOfVertices()] ;
		int[] distance = new int[graph.numberOfVertices()] ;
		float[] heuristique = new float[graph.numberOfVertices()] ;
		
		PriorityQueue<Node> f = new PriorityQueue<Node>(graph.numberOfVertices()) ;
		
		IntStream.range(0, graph.numberOfVertices()).forEach((index)->{
			// Init distance
			if(index == 0)
				distance[index] = 0 ;
			else
				distance[index] = Integer.MAX_VALUE ;
			
			//Init heuristique
			initH(heuristique,dest);
		});
		
		
		
		
		
		//border.remove(min);
		
		Set border = new HashSet<>();
		Set computed = new HashSet<>();
		
		//IntStreamm
		
		
		
	}
	
}
