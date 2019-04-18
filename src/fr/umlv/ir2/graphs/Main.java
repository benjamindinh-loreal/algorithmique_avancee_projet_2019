package fr.umlv.ir2.graphs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		try {
			Path path = Paths.get("resources/td4_exo7_3.mat");
			Graph graph = AdjGraph.makeGraphFromMatrixFile(path); System.out.println(graph);
			ShortestPathFromOneVertex sp;
			System.out.println("Bellman-Ford:"); sp = Graphs.bellmanFord(graph, 0); System.out.println(sp.toString());// sp.printShortestPaths(); System.out.println("\nDijkstra:"); sp = Graphs.dijkstra(graph, 0); System.out.println(sp); sp.printShortestPaths();
			
			//String toDot = graph.toGraphviz();
			//System.out.println(toDot);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
