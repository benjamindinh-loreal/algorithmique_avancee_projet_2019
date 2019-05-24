package fr.umlv.info2.graphs;

public class Node implements Comparable<Node> {
	private final int id;
	private final float distance;

	public Node(int id, float f) {
		this.id = id;
		this.distance = f;
	}

	public int getId() {
		return id;
	}
	
	public float getDistance() {
		return distance;
	}

	@Override
	public int compareTo(Node o) {
		
		var d1 = this.distance ;
		var d2 = o.distance ;
		
		if(d1 > d2)
			return 1 ;
		else if(d1 == d2)
			return 0 ;
		else
			return -1 ;
	}

}
