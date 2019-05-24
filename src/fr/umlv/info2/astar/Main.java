package fr.umlv.info2.astar;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.umlv.info2.graphs.AdjGraph;
import fr.umlv.info2.graphs.Graph;
import fr.umlv.info2.graphs.Graphs;
import fr.umlv.info2.graphs.ShortestPathFromOneVertex;

public class Main {

	public static void main(String[] args) {
		
		try {
			
			Graph graph = AdjGraph.makeGraphFromMatrixFile(Paths.get("resources/td4_exo7_3.mat")); System.out.println(graph);
			ShortestPathFromOneVertex sp;
			System.out.println("Bellman-Ford:"); sp = Graphs.bellmanFord(graph, 0); System.out.println(sp.toString());// sp.printShortestPaths(); System.out.println("\nDijkstra:"); sp = Graphs.dijkstra(graph, 0); System.out.println(sp); sp.printShortestPaths();
			
			//String toDot = graph.toGraphviz();
			//System.out.println(toDot);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
