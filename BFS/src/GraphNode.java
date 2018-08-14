import java.util.*;


public class GraphNode {	
	public int key;
	public int weight; //weight stands for initial weight
	public List<Edge> neighbors;
	//list neighbors are storing their neigh	bor nodes, and distance to them 
	public GraphNode (int key, int weight) {
		this.key = key;
		this.weight = weight;
		this.neighbors = new ArrayList<Edge>(); 
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
	
	
	public void updateWeight(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return this.weight;
	}
	//may need a function update the weight
	
}
