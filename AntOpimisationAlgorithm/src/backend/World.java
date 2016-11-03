package backend;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class World {
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
		int linkDistance = 300;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			for (Node neighbourNode: nodeList) {
				if (!neighbourNode.equals(node) && neighbourNode.calculateDistance(node.x, node.y) <= linkDistance && !neighbourNode.neightbours.containsKey(node)) {
					node.neightbours.put(neighbourNode, 0);
					neighbourNode.neightbours.put(node, 0);
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
	
	public void addAnt() {
		antList.add(new Ant(nodeList.get(0)));
	}
	
	
	public void updateWorld() {
		/*
		int totalPheromone;
		for (Ant ant: antList) {
			totalPheromone = 0;
			for (Map.Entry<Node, Integer> neighbourNode: ant.currentNode.neightbours.entrySet()) {
				totalPheromone += neighbourNode.getValue();
			}
			
			int pathValue = r.nextInt(totalPheromone);
			for (Map.Entry<Node, Integer> neighbourNode: ant.currentNode.neightbours.entrySet()) {
				pathValue -= neighbourNode.getValue();
				if (pathValue <= 0) {
					ant.currentNode.neightbours.put(ant.currentNode, ant.currentNode.neightbours.get(ant.currentNode) + 10);
					ant.currentNode = neighbourNode.getKey();
					neighbourNode.setValue(neighbourNode.getValue() + 10);
					
					break;
				}
			}
		}
		*/
	}
	
}
