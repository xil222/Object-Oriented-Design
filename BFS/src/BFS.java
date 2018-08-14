import java.util.*;

/* Best First Search
 * used for dijkstra algorithm
 */
public class BFS {
	
	public void dijkstra(List<GraphNode> graph, int startInx) {
		
	/**  graph	
		
		b-1-a-10-e-1-f
			|	 |   |
			1	 |   1
			|	 |   |
			c----1---d  **/ 
	
		
		//hashMap record current shortest distance
		Map<GraphNode, Integer> hashMap = new HashMap<>();//record current weight
		
		//hashSet record expanded case
		Set<GraphNode> hashSet = new HashSet<GraphNode>();//record all expanded nodes
		
		PriorityQueue<GraphNode> pq = new PriorityQueue<GraphNode>(new Comparator<GraphNode>() {
			@Override
			public int compare(GraphNode a, GraphNode b) {
				return a.weight < b.weight ? -1:1;
			}
		});
		
		GraphNode startNode = graph.get(startInx);
		pq.offer(startNode); 
		hashMap.put(startNode, 0);
		
		while (!pq.isEmpty()) {
			GraphNode currNode = pq.poll(); //this node going to be expanded

			if (!hashSet.contains(currNode)) {
				hashSet.add(currNode);
			} else {
				continue;
			}
				
			for (Edge edge:currNode.neighbors) {
				GraphNode node = edge.getEnd();
				
				//use newWeight determine whether need to update PQ, and hashMap
				int newWeight = edge.getWeight() + currNode.weight;

				if (!hashMap.containsKey(node) || hashMap.containsKey(node) && newWeight < hashMap.get(node)) {
					//if distance shorter, then update HashMap and put that shorter path into PriorityQueue
					node.updateWeight(newWeight);
					//update a new PriorityQueue with shorter distance
					pq.offer(node);
					hashMap.put(node, node.weight);
				}		
			}
		}
		
		for (GraphNode tempNode: hashMap.keySet()) {
			System.out.println("node: "+ tempNode.key +" distance: " + tempNode.weight);
		}
	}
	
	public static void main(String args[]) {
		BFS bfs = new BFS();
		List<GraphNode> graph = new ArrayList<GraphNode>();
		
		GraphNode a = new GraphNode(0, 0); //4 is key, 0 is distance
		GraphNode b = new GraphNode(1, 0);
		GraphNode c = new GraphNode(2, 0);
		GraphNode d = new GraphNode(3, 0);
		GraphNode e = new GraphNode(4, 0);
		GraphNode f = new GraphNode(5, 0);

		a.neighbors.add(new Edge(a,b,1));
		a.neighbors.add(new Edge(a,c,1));
		a.neighbors.add(new Edge(a,e,10));

		b.neighbors.add(new Edge(b,a,1));
		
		c.neighbors.add(new Edge(c,a,1));
		c.neighbors.add(new Edge(c,d,1));
		
		d.neighbors.add(new Edge(d,e,1));
		d.neighbors.add(new Edge(d,c,1));
		d.neighbors.add(new Edge(d,f,1));
		
		e.neighbors.add(new Edge(e,d,1));
		e.neighbors.add(new Edge(e,a,10));
		e.neighbors.add(new Edge(e,f,1));
		
		f.neighbors.add(new Edge(f,e,1));
		f.neighbors.add(new Edge(f,d,1));
		
		graph.add(a);
		graph.add(b);
		graph.add(c);
		graph.add(d);
		graph.add(e);
		graph.add(f);
		
		//input graph and starting index
		bfs.dijkstra(graph,0);	
	}
}

