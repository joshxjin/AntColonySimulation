package backend;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class World {
	final int PHEROMONE_DEC = 10;
	
	public static ArrayList<Node> nodeList = new ArrayList<Node>();
	public static ArrayList<Ant> antList = new ArrayList<Ant>();
	
	int worldWidth, worldHeight, numOfMiddleNodes;
	
	Random r = new Random();
	
	public World(int canvasWidth, int canvasHeight, int numOfMiddleNodes) {
		this.worldWidth = canvasWidth;
		this.worldHeight = canvasHeight;
		this.numOfMiddleNodes = numOfMiddleNodes;
	}
	
	
	public void generateNodes() {
		nodeList.clear();
		antList.clear();
		
		Node startNode = new Node(50, 50, "start");
		nodeList.add(startNode);
		Node endNode = new Node(worldWidth - 50, worldHeight - 50, "end");
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
			Node newNode = new Node(newX, newY, type);
			nodeList.add(newNode);
		}
	}
	
	private void generatePaths() {
		int linkDistance = 270;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			for (Node neighbourNode: nodeList) {
				if (!neighbourNode.equals(node) && neighbourNode.calculateDistance(node.x, node.y) <= linkDistance && !neighbourNode.neighbours.contains(node)) {
					node.neighbours.add(neighbourNode);
					neighbourNode.neighbours.add(node);
				}
			}
			if (node.neighbours.isEmpty()) {
				linkDistance += 50;
				i--;
			} else {
				linkDistance = 270;
			}
		}
	}
	
	public Ant addAnt() {
		Ant ant = new Ant(nodeList.get(0));
		
		antList.add(ant);
		
		return ant;
	}
	
	
	public void updateWorld() {
		for (Node node: nodeList) {
			node.pheromone -= PHEROMONE_DEC;
			if (node.pheromone <= 1) {
				node.pheromone = 1;
			}
		}
		
		for (Ant ant: antList) {
			ant.move();
		}
	}
	
}
