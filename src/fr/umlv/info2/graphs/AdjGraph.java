package fr.umlv.info2.graphs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class AdjGraph implements Graph {
	private final ArrayList<LinkedList<Edge>> adj;
	private final int n;
	private int nbEdges;
	private Long[][] coordinates ;

	AdjGraph(int n, ArrayList<LinkedList<Edge>> adj) {
		if (n <= 0) {
			throw new IllegalArgumentException("Le nombre de sommets doit �tre strictement positif");
		}
		this.n = n;
		this.adj = Objects.requireNonNull(adj);
		nbEdges = 0;
	}

	public Graph newGraphOfSameSize() {
		return makeGraph(n);
	}

	static ArrayList<LinkedList<Edge>> init(int nbEdges) {
		ArrayList<LinkedList<Edge>> tmp = new ArrayList<LinkedList<Edge>>(nbEdges);
		for (int i = 0; i < nbEdges; i++) {
			tmp.add(new LinkedList<Edge>());
		}
		return tmp;
	}

	public static AdjGraph makeGraph(int n) {
		ArrayList<LinkedList<Edge>> tmp = init(n);
		return new AdjGraph(n, tmp);
	}

	public static AdjGraph makeRandomGraph(int n, int nbEdges, int wmax) {
		return (AdjGraph) Graph.makeRandomGraph(n, nbEdges, wmax, AdjGraph::makeGraph);
	}

	public static AdjGraph makeGraphFromMatrixFile(Path path) throws IOException {
		Objects.requireNonNull(path);
		return (AdjGraph) Graph.makeGraphFromMatrixFile(path, AdjGraph::makeGraph);
	}

	@Override
	public void addEdge(int i, int j, int value) {
		if (isEdge(i, j)) {
			throw new IllegalArgumentException("Cette ar�te existe d�j� : " + i + " -- " + j);
		}
		if (value == 0) {
			throw new IllegalArgumentException("Vous ne pouvez pas ajouter une ar�te de poids nul");
		}
		adj.get(i).add(new Edge(i, j, value));
		nbEdges++;
	}

	@Override
	public int numberOfEdges() {
		return nbEdges;
	}

	@Override
	public int numberOfVertices() {
		return n;
	}
	
	private void checkIndices(int i, int j) {
		Objects.checkIndex(i, n);
		Objects.checkIndex(i, n);
	}

	@Override
	public boolean isEdge(int i, int j) {
		checkIndices(i, j);
		LinkedList<Edge> list = adj.get(i);
		for (Edge neighbour : list) {
			if (neighbour.getEnd() == j) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getWeight(int i, int j) {
		checkIndices(i, j);
		int[] res = new int[1];
		res[0] = 0;
		forEachEdge(i, ngb -> {
			if (ngb.getEnd() == j) {
				res[0] = ngb.getValue();
				return;
			}
		});
		return res[0];
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		Objects.checkIndex(i, n);
		return adj.get(i).iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		Objects.checkIndex(i, n);
		Objects.requireNonNull(consumer);
		for (Edge neighbour : adj.get(i)) {
			consumer.accept(neighbour);
		}
	}

	@Override
	public String toString() {
		return adj.toString();
	}

}
