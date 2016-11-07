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
			if (distance <= Constants.MIN_NODE_DISTANCE) {
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
		int linkDistance = Constants.LINK_DISTANCE;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			for (Node neighbourNode: nodeList) {
				if (!neighbourNode.equals(node) && neighbourNode.calculateDistance(node.x, node.y) <= linkDistance && !neighbourNode.neighbours.containsKey(node)) {
					node.neighbours.put(neighbourNode, Constants.START_PHEROMONE);
					neighbourNode.neighbours.put(node, Constants.START_PHEROMONE);
				}
			}
			if (node.neighbours.isEmpty()) {
				linkDistance += Constants.LINK_DISTANCE_INC;
				i--;
			} else {
				linkDistance = Constants.LINK_DISTANCE;
			}
		}
	}
	
	public Ant addAnt() {
		Ant ant = new Ant(nodeList.get(0));
		
		antList.add(ant);
		
		return ant;
	}
	
	
	public void updateWorld() {
		depreciatePheromone();
		
		for (Ant ant: antList) {
			ant.move();
		}
	}
	
	/*
	public void depreciatePheromone() {
		for (Node node: nodeList) {
			node.pheromone -= Constants.PHEROMONE_DEC;
			if (node.pheromone <= Constants.MIN_PHEROMONE) {
				node.pheromone = Constants.MIN_PHEROMONE;
			}
		}
	}
	*/
	
	public void depreciatePheromone() {
		for (Node node: nodeList) {
			for (Node neighbourNode: node.getNeighbours().keySet()) {
				Integer pheromone = node.getNeighbours().get(neighbourNode);
				pheromone -= Constants.PHEROMONE_DEC;
				if (pheromone <= Constants.MIN_PHEROMONE) {
					pheromone = Constants.MIN_PHEROMONE;
				}
				node.getNeighbours().replace(neighbourNode, pheromone);
			}
		}
	}
	
}
