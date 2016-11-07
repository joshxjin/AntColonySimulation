package backend;

import java.util.ArrayList;
import java.util.Random;

public class World {
	public static ArrayList<Node> nodeList = new ArrayList<Node>();
	public static ArrayList<Ant> antList = new ArrayList<Ant>();
	
	int worldWidth, worldHeight;
	
	Random r = new Random();
	
	public World(int canvasWidth, int canvasHeight) {
		this.worldWidth = canvasWidth;
		this.worldHeight = canvasHeight;
	}
	
	
	public void generateNodes() {
		Node startNode = new Node(Constants.NODE_BOUNDARY_LIMIT, Constants.NODE_BOUNDARY_LIMIT, "start");
		nodeList.add(startNode);
		Node endNode = new Node(worldWidth - Constants.NODE_BOUNDARY_LIMIT, worldHeight - Constants.NODE_BOUNDARY_LIMIT, "end");
		nodeList.add(endNode);
		
		for (int i = 0; i < Constants.MIDDLE_NODES_NUMBER; i++) {
			generateNextNode("middle");
		}
		
		generatePaths();
	}
	
	private void generateNextNode(String type) {
		//node is limited to set pixel from the edge of the display
		int newX = r.nextInt(worldWidth - (Constants.NODE_BOUNDARY_LIMIT * 2)) + Constants.NODE_BOUNDARY_LIMIT;
		int newY = r.nextInt(worldHeight - (Constants.NODE_BOUNDARY_LIMIT * 2)) + Constants.NODE_BOUNDARY_LIMIT;
		Boolean validPosition = true;
		for (Node node: nodeList) {
			double distance = node.calculateDistance(newX, newY);
			if (distance <= Constants.MIN_NODE_DISTANCE) { //set a minimum distance between nodes
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
		//create links (path) between nodes based on Constants.LINK_DISTANCE
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
		//add ant to the world and return ant so frontend can create circle for the ant
		Ant ant = new Ant(nodeList.get(0));
		
		antList.add(ant);
		
		return ant;
	}
	
	public void depreciatePheromone() {
		//decrease the pheromone of each node and set the minimum pheromone value
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
	
	public void cleanUp() {
		antList.clear();
		nodeList.clear();
	}
	
}
