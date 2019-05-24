package fr.umlv.info2.graphs;

import java.util.ArrayDeque;
import java.util.Arrays;

public class ShortestPathFromOneVertex {
	private final int source;
	private final int[] d;
	private final int[] pi;

	public ShortestPathFromOneVertex(int source, int[] d, int[] pi) {
		this.source = source;
		this.d = d;
		this.pi = pi;
	}

	public void printShortestPathToRec(int destination) {
		for(int i = 0; i < pi.length; i++) {
			if(pi[i] == destination) {
				System.out.print(" --> " +i);
				printShortestPathToRec(i);
				return;
			}
		}
	}
	
	public void printShortestPathTo(int destination) {
	
		for(int i = 0; i < d.length; i++) {
			if(pi[destination] == source) {
				System.out.print(source+" --> "+ destination);
				printShortestPathToRec(destination);
				System.out.print("\n");
			}
		}
		
	}

	public void printShortestPaths() {
		for (int i = 0; i < d.length; i++) {
			if (i == source) {
				continue;
			}
			printShortestPathTo(i);
		}
	}

	@Override
	public String toString() {
		return source + " " + Arrays.toString(d) + " " + Arrays.toString(pi);
	}
}
