import java.util.*;


public class GraphNode {	
	public int key;
	public int weight;
	public List<GraphNode> neighbors;
	//list neighbors are storing their neighbor nodes, and distance to them 
	public GraphNode (int key, int weight) {
		this.key = key;
		this.weight = weight;
		this.neighbors = new ArrayList<GraphNode>(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof GraphNode)) {
			return false;
		}
		
		GraphNode temp = (GraphNode) obj;
		return temp.key == this.key;
	}
	
	@Override
	public int hashCode() {
		return key * 101;
	}
	
}
