package backend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ant {
	Node currentNode;
	GraphicsContext gc;
	
	public Ant(GraphicsContext gc, Node startNode) {
		this.currentNode = startNode;
		this.gc = gc;
		gc.setFill(Color.BROWN);
		gc.fillOval(currentNode.x, currentNode.y, 10, 10);
	}
	
	public void move() {
		
	}
}
