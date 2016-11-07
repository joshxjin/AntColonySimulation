package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	//int x, y, pheromone;
	int x, y;
	String type;
	//ArrayList<Node> neighbours = new ArrayList<Node>();
	HashMap<Node, Integer> neighbours = new HashMap<Node, Integer>();
	
	public Node(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
		//this.pheromone = 150;
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

	/*
	public ArrayList<Node> getNeighbours() {
		return neighbours;
	}
	*/
	
	public HashMap<Node, Integer> getNeighbours() {
		return neighbours;
	}
}
