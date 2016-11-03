package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	int x, y;
	String type;
	HashMap<Node, Integer> neightbours = new HashMap<Node, Integer>();
	//ArrayList<Node> neighbours = new ArrayList<Node>();
	
	public Node(int x, int y, String type) {
		this.x = x + 15;
		this.y = y + 15;
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

	public HashMap<Node, Integer> getNeightbours() {
		return neightbours;
	}
}
