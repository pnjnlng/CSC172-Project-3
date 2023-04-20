import java.util.*;
public class Graph {
	Map<String, double[]> nodeCoordinates;
	Map<String, Node> nodes;
	int numNodes;
	ArrayList<Edge> edges;
	int numEdges;
	List<String> path;
	public Graph(){
		nodeCoordinates = new HashMap<String, double[]>();
		nodes = new HashMap<String, Node>();
		edges = new ArrayList<Edge>();
		numEdges = 0;
		path = new ArrayList<String>();
	}
	public Graph(int N){
		edges = new ArrayList<Edge>();
		numEdges = 0;
		path = new ArrayList<String>();
	}
	public void addNode(String name, double Latitude, double Longitude){
		nodeCoordinates.put(name, new double[]{Longitude,Latitude});
	}
	public double getEdgeLength(String node1, String node2){
		for (Edge edge : edges) {
            if ((edge.getFrom().equals(node1) && edge.getTo().equals(node2)) ||
                    (edge.getFrom().equals(node2) && edge.getTo().equals(node1))) {
                return edge.getLength();
            }
        }
        return Double.POSITIVE_INFINITY;
	}
	public void addEdge(String ID, String from, String to){
		Edge edge = new Edge(ID, from, to, 0);
        edge.cacheLength(nodeCoordinates);
        edges.add(edge);
        numEdges++;;
	}
	public void modifyGraph(){
		for (String name : nodeCoordinates.keySet()) {
            nodes.put(name, new Node());
        }
        this.numEdges = edges.size();
        for (int j = 0; j < numEdges; j++) {
            Edge edge = edges.get(j);
            nodes.get(edge.getFrom()).getEdges().add(edge);
            nodes.get(edge.getTo()).getEdges().add(edge);
        }
	}
	//Shortest distance from a given node to all other nodes
	public void dijkstra(String start, String end){//Starting with any node
		String next = start;
		nodes.get(next).setDistance(0);
		for(String name:nodes.keySet()){//Iterate over all nodes
			List<Edge> temp = new ArrayList<Edge>();
			if(nodes.get(end).isVisited()){
				break;
				//If we already visited end, no need to continue.
			}
			if(next.equals("")){
				break;
			}
			temp = nodes.get(next).getEdges();
			for(int j=0;j<temp.size();j++){//Iterate over all edges of this node
				String neighbor = temp.get(j).getNeighbor(next);
				if(!nodes.get(neighbor).isVisited()){//Not yet visited
					double dist = nodes.get(next).getDistance()+temp.get(j).getLength();
					if(dist<nodes.get(neighbor).getDistance()){
						nodes.get(neighbor).setDistance(dist);
						nodes.get(neighbor).setPrev(next);
					}
				}
			}
			nodes.get(next).setVisited(true);
			next = getPath();
		}
	}
	public String getPath(){
		String storage="";
		double storage2 = Integer.MAX_VALUE;
		for(String key:nodes.keySet()){
			double temp = nodes.get(key).getDistance();
			if(!(nodes.get(key).isVisited())&&(temp < storage2)){
				storage2 = temp;
				storage = key;
			}
		}
		return storage;
	}
	public List<String> getShortestPath(String a, String b){
		List<String> path = new ArrayList<String>();
		this.dijkstra(a,b);
		if(a.equals(b)){
			path.add(a);
			return path;
		}
		else if(nodes.get(b).getDistance()==Integer.MAX_VALUE){
			return path;
		}
		else{
			Stack<String> stack = new Stack<String>();
			String temp;
			temp = b;
			stack.push(b);
			nodes.get(b).setHighlighted(true);
			while(!(nodes.get(temp).getPrev().equals(a))){
				temp = nodes.get(temp).getPrev();
				nodes.get(temp).setHighlighted(true);
				stack.push(temp);
			}
			stack.push(a);
			nodes.get(a).setHighlighted(true);
			while(!stack.isEmpty()){
				path.add(stack.pop());
			}
			return path;
		}
	}
}