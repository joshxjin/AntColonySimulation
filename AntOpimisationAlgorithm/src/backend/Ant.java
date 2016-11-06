package backend;

import java.util.Random;
import java.util.Stack;

public class Ant {
	Stack<Node> path = new Stack<Node>();
	Node currentNode, previousNode;
	Boolean returning;
	private static int pathLength = 100;
	int currentPathLength;
	
	public Ant(Node startNode) {
		this.currentNode = startNode;
		this.returning = false;
		this.currentPathLength = 0;
		this.path.push(currentNode);
	}
	
	public void move() {
		if (returning) {
			currentNode.pheromone += 50;
			currentNode = path.pop();
			if (currentNode.type.equals("start")) {
				path.push(currentNode);
				returning = false;
			}
		} else {
			int counter = 0;
			for (Node node: currentNode.getNeighbours()) {
				if (previousNode == null || !previousNode.equals(node) && !node.getType().equals("start")) {
					counter += node.pheromone;
				}
			}
			Random r = new Random();
			int randNum = r.nextInt(counter);
			for (Node node: currentNode.getNeighbours()) {
				if (previousNode == null || !previousNode.equals(node) && !node.getType().equals("start")) {
					randNum -= node.pheromone;
					if (randNum <= 0) {
						previousNode = currentNode;
						currentNode = node;
						if (currentNode.type.equals("end")) {
							returning = true;
						}
						path.push(currentNode);
						return;
					}
				}
			}
		}
	}

	public Node getCurrentNode() {
		return currentNode;
	}
}
