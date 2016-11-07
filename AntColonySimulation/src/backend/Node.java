package backend;

import java.util.HashMap;

public class Node {
	int x, y;
	String type;
	HashMap<Node, Integer> neighbours = new HashMap<Node, Integer>(); //directional pheromone
	
	public Node(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public double calculateDistance(int x, int y) {
		return Math.hypot(this.x - x, this.y - y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getType() {
		return type;
	}
	
	public HashMap<Node, Integer> getNeighbours() {
		return neighbours;
	}
}
