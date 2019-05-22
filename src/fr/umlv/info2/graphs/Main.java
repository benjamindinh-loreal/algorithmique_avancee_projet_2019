package fr.umlv.info2.graphs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.PriorityQueue;

import fr.umlv.info2.astar.Astar;
import fr.umlv.info2.parser.Parser;

public class Main {

	public static void main(String[] args) {
		
		
		/*PriorityQueue<Integer> f = new PriorityQueue<Integer>();
		
		f.offer(1) ;
		f.offer(3) ;
		
		f.forEach((i) -> {
			if(i == 1) {
				f.remo
			}
		}) ;
		
		System.out.println(f.poll()+"") ;*/
		
		try {
			
			var edges = Parser.parseGr(new File("ressources/USA-road-d.NY.gr")) ;
			var coordinates = Parser.parseCo(new File("ressources/USA-road-d.NY.co")) ;
			Graph g = AdjGraph.makeGraph(coordinates.length);
			for(var edge : edges) {
				g.addEdge(edge.getStart(), edge.getEnd(),edge.getValue());
			}
			
			var astar = new Astar(g, coordinates, 1, 2) ;
			
			/*Graph graph = AdjGraph.makeGraphFromMatrixFile(path); System.out.println(graph);
			ShortestPathFromOneVertex sp;
			System.out.println("Bellman-Ford:"); sp = Graphs.bellmanFord(graph, 0); System.out.println(sp.toSting());// sp.printShortestPaths(); System.out.println("\nDijkstra:"); sp = Graphs.dijkstra(graph, 0); System.out.println(sp); sp.printShortestPaths();*/
			
			//String toDot = graph.toGraphviz();
			//System.out.println(toDot);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
