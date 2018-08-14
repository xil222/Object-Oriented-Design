
public class Edge {
	private int weight;
	private GraphNode start;
	private GraphNode end;
	
	public Edge(GraphNode start, GraphNode end, int weight) {
		this.weight = weight;
		this.start = start;
		this.end = end;
	}
	
	public GraphNode getStart(){
		return this.start;
	}
	
	public GraphNode getEnd() {
		return this.end;
	}
	
	public int getWeight() {
		return this.weight;
	}
}
