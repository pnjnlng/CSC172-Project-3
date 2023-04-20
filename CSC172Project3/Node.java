import java.util.*;
public class Node {
    //Global variables
    private boolean highlighted;
	private double latitude;
	private double longitude;
	private double distance = Integer.MAX_VALUE;
	private boolean visited;
	private List<Edge> edges = new ArrayList<Edge>();
	private String prev;
    //Get distance between points method used in Dijkstra's Algo to show distance
    public double getDistance() {
		return distance;
	}
    //Set distance for nodes method
	public void setDistance(double distance) {
		this.distance = distance;
	}
    //Checks to see if node has already been visited in Dijkstra's Algo (Priority Queue)
	public boolean isVisited() {
		return visited;
    }
    //Set to node to be visited method
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
    //List to hold the edges
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		longitude = longitude;
	}
	public boolean isHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	public void addEdge(Edge edge) {
	}
}