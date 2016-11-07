package backend;

import java.util.Random;
import java.util.Stack;

public class Ant {
	Stack<Node> path = new Stack<Node>();
	Node currentNode, previousNode;
	Boolean returning;
	double dx, dy;
	
	int currentPathLength;
	
	public Ant(Node startNode) {
		this.currentNode = startNode;
		this.returning = false;
		this.currentPathLength = 0;
		this.path.push(currentNode);
	}
	
	/*
	public void move() {
		if (returning) {
			currentNode.pheromone += Constants.PHEROMON_INC;
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
			System.out.println(counter);
			int randNum = r.nextInt(counter + 1);
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
	*/
	
	public void move() {
		if (returning) {
			previousNode = currentNode;
			currentNode = path.pop();
			int pheromone = currentNode.neighbours.get(previousNode) + Constants.PHEROMON_INC;
			currentNode.neighbours.put(previousNode, pheromone);
			if (currentNode.type.equals("start")) {
				path.push(currentNode);
				returning = false;
				previousNode = null;
			}
		} else {
			int counter = 0;
			if (currentNode.getNeighbours().keySet().size() == 1) {
				for (Node node: currentNode.getNeighbours().keySet()) {
					previousNode = currentNode;
					currentNode = node;
					if (currentNode.type.equals("end")) {
						returning = true;
					} else {
						path.push(currentNode);
					}
					return;
				}
			}
			
			for (Node node: currentNode.getNeighbours().keySet()) {
				if (previousNode == null || !previousNode.equals(node) && !node.getType().equals("start")) {
					counter += currentNode.getNeighbours().get(node);
				}
			}
			Random r = new Random();
			int randNum = r.nextInt(counter + 1);
			for (Node node: currentNode.getNeighbours().keySet()) {
				if (previousNode == null || !previousNode.equals(node) && !node.getType().equals("start")) {
					randNum -= currentNode.getNeighbours().get(node);
					if (randNum <= 0) {
						previousNode = currentNode;
						currentNode = node;
						if (currentNode.type.equals("end")) {
							returning = true;
						} else {
							path.push(currentNode);
						}
						return;
					}
				}
			}
		}
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
}
