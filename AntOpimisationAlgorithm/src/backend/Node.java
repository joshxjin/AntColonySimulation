package backend;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Node {
	int x, y;
	String type;
	HashMap<Node, Integer> neightbours = new HashMap<Node, Integer>();
	//ArrayList<Node> neighbours = new ArrayList<Node>();
	
	public Node(int x, int y, GraphicsContext gc, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
		switch (type) {
		case ("start"):
			gc.setFill(Color.RED);
			break;
		case ("end"):
			gc.setFill(Color.BLUEVIOLET);
			break;
		case ("middle"):
			gc.setFill(Color.GREENYELLOW);
			break;
		}
		
		gc.fillOval(x, y, 30, 30);
	}
	
	public double calculateDistance(int x, int y) {
		return Math.hypot(this.x - x, this.y - y);
	}
}
