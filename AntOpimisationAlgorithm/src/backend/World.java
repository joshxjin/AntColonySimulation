package backend;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class World {
	public ArrayList<Node> nodeList = new ArrayList<Node>();
	
	int worldWidth, worldHeight, numOfMiddleNodes;
	GraphicsContext gc;
	
	Random r = new Random();
	
	public World(int canvasWidth, int canvasHeight, int numOfMiddleNodes, GraphicsContext gc) {
		this.worldWidth = canvasWidth;
		this.worldHeight = canvasHeight;
		this.numOfMiddleNodes = numOfMiddleNodes;
		this.gc = gc;
	}
	
	
	public void generateNodes() {
		nodeList.clear();
		Node startNode = new Node(50, 50, gc, "start");
		nodeList.add(startNode);
		Node endNode = new Node(worldWidth - 50, worldHeight - 50, gc, "end");
		nodeList.add(endNode);
		
		for (int i = 0; i < numOfMiddleNodes; i++) {
			generateNextNode("middle");
		}
		
		generatePaths();
	}
	
	private void generateNextNode(String type) {
		int newX = r.nextInt(worldWidth - 60) + 30;
		int newY = r.nextInt(worldHeight - 60) + 30;
		Boolean validPosition = true;
		for (Node node: nodeList) {
			double distance = node.calculateDistance(newX, newY);
			if (distance <= 150) {
				validPosition = false;
				break;
			}
		}
		
		if (!validPosition) {
			generateNextNode(type);
		} else {
			Node newNode = new Node(newX, newY, gc, type);
			nodeList.add(newNode);
		}
	}
	
	private void generatePaths() {
		int linkDistance = 300;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			for (Node neighbourNode: nodeList) {
				if (!neighbourNode.equals(node) && neighbourNode.calculateDistance(node.x, node.y) <= linkDistance && !neighbourNode.neightbours.containsKey(node)) {
					node.neightbours.put(neighbourNode, 0);
					neighbourNode.neightbours.put(node, 0);
					gc.setFill(Color.BLACK);
					gc.strokeLine(node.x + 15, node.y + 15, neighbourNode.x + 15, neighbourNode.y + 15);
				}
			}
			if (node.neightbours.isEmpty()) {
				linkDistance += 50;
				i--;
			} else {
				linkDistance = 300;
			}
		}
	}
}
