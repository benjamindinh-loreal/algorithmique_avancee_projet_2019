package fr.umlv.info2.graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public interface Graph {
	public final static int maxGraph = 1000;
	public final static int minGraph = -1000;

	/**
	 * Le nombre d'ar�tes du graphe.
	 * @return le nombre d'ar�tes du graphe
	 */
	int numberOfEdges();
	
	/**
	 * Le nombre de sommets du graphe.
	 * @return le nombre de sommets du graphe
	 */
	int numberOfVertices();

	default void addEdge(int i, int j) {
		addEdge(i, j, 1);
	}

	/**
	 * Permet d'ajouter une ar�te orient�e et pond�r�e au graphe.
	 * @param i la premi�re extr�mit� de l'ar�te
	 * @param j la seconde extr�mit� de l'ar�te
	 * @param value le poids de l'ar�te
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 * @throws IllegalArgumentException si value vaut 0 ou si il existe d�j� une ar�te entre i et j
	 */
	void addEdge(int i, int j, int value);

	/**
	 * Teste l'existence d'une ar�te donn�e
	 * @param i la premi�re extr�mit� de l'ar�te
	 * @param j la seconde extr�mit� de l'ar�te
	 * @return true s'il existe une ar�te entre i et j; false sinon
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 */
	boolean isEdge(int i, int j);

	/**
	 * Renvoie le poids d'une ar�te donn�e.
	 * @param i la premi�re extr�mit� de l'ar�te
	 * @param j la seconde extr�mit� de l'ar�te
	 * @return le poids de l'ar�te entre i et j
	 * @throws IndexOutOfBoundsException si i ou j n'est pas un sommet du graphe
	 */
	int getWeight(int i, int j);

	/**
	 * Un it�rateur sur tous les voisins d'un sommet donn�.
	 * @param i le sommet � partir duquel partent les ar�tes fournies par l'it�rateur
	 * @return un it�rateur sur tous les voisins du sommet i
	 * @throws IndexOutOfBoundsException si i n'est pas un sommet du graphe
	 */
	Iterator<Edge> edgeIterator(int i);

	/**
	 * Effectue une action sur tous les voisins (les ar�tes) d'un sommet donn�.
	 * @param i le sommet � partir duquel partent les ar�tes trait�es
	 * @param consumer l'acction effectu�e sur toutes les ar�tes voisines de i
	 * @throws IndexOutOfBoundsException si i n'est pas un sommet du graphe
	 */
	void forEachEdge(int i, Consumer<Edge> consumer);

	/**
	 * Fournit une r�pr�sentaiuon du graphe au format .dot
	 * @return une r�pr�sentaiuon du graphe au format .dot
	 */
	default String toGraphviz() {
		StringBuffer bf = new StringBuffer();
		bf.append("digraph G {\n\t").append(numberOfVertices() - 1).append(";\n");

		for (int i = 0; i < numberOfVertices(); i++) {
			final int fi = i;
			bf.append("\t").append(fi).append(";\n");

			forEachEdge(i, neighbour -> bf.append("\t").append(fi).append("->").append(neighbour.getEnd())
					.append("[label =\"").append(neighbour.getValue()).append("\"] ;\n"));
		}

		bf.append("}\n");
		return bf.toString();
	}

	default void toGraphviz(Path path) {
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(toGraphviz());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cr�ation d'un graphe al�atoire avec un nombre de sommets et d'ar�tes fix�
	 * @param n nombre de sommets
	 * @param nbEdges nombre d'ar�tes
	 * @param wmax poids maximal (en valeur absolue) sur les ar�tes
	 * @param factory une m�thode qui �tant donn� un nombre de sommets n, fabrique et renvoie yun graphe vide � n sommets
	 * @return un graphe al�atoire construit � l'aide de factory yant exactement n sommets et nbEdges ar�tes
	 * @throws IllegalArgumentException si le nombre d'ar�tes est trop �lev� par rapport au nombre de sommets ou si le poids max est nul
	 */
	public static Graph makeRandomGraph(int n, int nbEdges, int wmax, IntFunction<Graph> factory){
		Graph g = factory.apply(n);
		if (nbEdges > n * n) {
			throw new IllegalArgumentException("Trop d'ar�tes pour " + n + " sommets!");
		}
		if (wmax==0) {
			throw new IllegalArgumentException("Le poids max doit �tre non nul");
		}
		Random r = new Random();
		int v1, v2;
		for (int k = 0; k < nbEdges; k++) {
			do {
				v1 = r.nextInt(n);
				v2 = r.nextInt(n);

			} while (g.isEdge(v1, v2));
			if(r.nextBoolean()){
				g.addEdge(v1, v2, 1 + r.nextInt(wmax));
			}
			else{
				g.addEdge(v1, v2, -(1 + r.nextInt(wmax)));
			}	
		}
		return g;
	}

	/**
	 * Cr�ation d'un graphe � partir d'un fichier contenant le nombre de sommets et sa matrice
	 * @param path le chemin du fichier contenant la matrice du graphe
	 * @param factory une m�thode qui �tant donn� un nombre de sommets n, fabrique et renvoie yun graphe vide � n sommets
	 * @return un graphe construit � l'aide de factory et dont les ar�tes sont donn�es dans le fihier indiqu� dans path
	 * @throws IOException
	 */
	public static Graph makeGraphFromMatrixFile(Path path, IntFunction<Graph> factory) throws IOException {
		Graph g;

		try (BufferedReader reader = Files.newBufferedReader(path)) {
			int size = Integer.parseInt(reader.readLine());
			g = factory.apply(size);

			for (int i = 0; i < size; i++) {
				String[] s = reader.readLine().split("\\s+");
				for (int j = 0; j < size; j++) {
					int w = Integer.parseInt(s[j]);
					if (w != 0) {
						g.addEdge(i, j, w);
					}
				}
			}
		}

		return g;
	}

	// fabrique un graphe vide de la m�me taille.
	public Graph newGraphOfSameSize();

	default public Graph transpose() {
		Graph t = newGraphOfSameSize();
		IntStream.range(0, numberOfVertices()).forEach(i -> {
			forEachEdge(i, neighbour -> {
				t.addEdge(neighbour.getEnd(), i, neighbour.getValue());
			});
		});
		return t;
	}
}
