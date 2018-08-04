import java.util.*;

/* Best First Search
 * used for dijkstra algorithm
 */
public class BFS {
	public void dijkstra(List<GraphNode> graph) {
		
		//set a hashmap to check the conditions of neighbors
		Map<GraphNode, Integer> hashMap = new HashMap<>();//record current weight
		Set<GraphNode> hashSet = new HashSet<GraphNode>();//record all expanded nodes
		
		PriorityQueue<GraphNode> pq = new PriorityQueue<GraphNode>(new Comparator<GraphNode>() {
			@Override
			public int compare(GraphNode a, GraphNode b) {
				return a.weight < b.weight ? -1:1;
			}
		});
		
		GraphNode startNode = graph.get(0);
		pq.offer(startNode); 
		hashMap.put(startNode, 0);
		
		while (!pq.isEmpty()) {
			
			GraphNode currNode = pq.peek();
			while (hashSet.contains(currNode)) {
				currNode = pq.poll();
			}
			
			if (currNode == null) {
				break;
			} else {
				hashSet.add(currNode);//
			}
			for (GraphNode node:currNode.neighbors) {
				System.out.println(currNode.key + " " + node.key);
				pq.offer(graph.get(node.key));
				//pq.offer(node);
				if (!hashSet.contains(node)) {
					if (!hashMap.containsKey(node) || currNode.weight + node.weight < hashMap.get(node)) {
						hashMap.put(node, currNode.weight + node.weight);
					}
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
		a.neighbors.add(new GraphNode(1, 1));
		a.neighbors.add(new GraphNode(2, 1));
		a.neighbors.add(new GraphNode(4, 10));
				
		GraphNode b = new GraphNode(1, 0);
		b.neighbors.add(new GraphNode(0, 1));
		
		GraphNode c = new GraphNode(2, 0);
		c.neighbors.add(new GraphNode(0, 1));
		c.neighbors.add(new GraphNode(3, 1));
		
		GraphNode d = new GraphNode(3, 0);
		d.neighbors.add(new GraphNode(4, 1));
		d.neighbors.add(new GraphNode(2, 1));
		d.neighbors.add(new GraphNode(5, 1));
		
		GraphNode e = new GraphNode(4, 0);
		e.neighbors.add(new GraphNode(3, 1));
		e.neighbors.add(new GraphNode(0, 10));
		e.neighbors.add(new GraphNode(5, 1));
		
		GraphNode f = new GraphNode(5, 0);
		f.neighbors.add(new GraphNode(4, 1));
		f.neighbors.add(new GraphNode(3, 1));
		
		graph.add(a);
		graph.add(b);
		graph.add(c);
		graph.add(d);
		graph.add(e);
		graph.add(f);
		
		bfs.dijkstra(graph);	
	}
}

