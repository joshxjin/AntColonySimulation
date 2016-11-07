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
	
	public void move() {
		if (returning) { //returning home, but popping off the stack of node path
			previousNode = currentNode;
			currentNode = path.pop();
			int pheromone = currentNode.neighbours.get(previousNode) + Constants.PHEROMON_INC; //putting down pheromone
			currentNode.neighbours.put(previousNode, pheromone);
			if (currentNode.type.equals("start")) {
				path.push(currentNode);
				returning = false;
				previousNode = null;
			}
		} else { 
			int counter = 0;
			if (currentNode.getNeighbours().keySet().size() == 1 && !currentNode.type.equals("start")) { //applies to nodes that are dead ends (only have a return path)
				Node tempNode = previousNode;
				previousNode = currentNode;
				currentNode = tempNode;
				if (currentNode.type.equals("end")) {
					returning = true;
				} else {
					path.push(currentNode);
				}
				return;
			}
			
			/*
			 * add up all the pheromone values of all neighbour nodes (not including the previous node or starting node)
			 * randomly generate a number of the sum of pheromones
			 * move towards the neighbour node
			 */
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
