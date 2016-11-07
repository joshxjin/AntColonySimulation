package backend;

public class Constants {
	public static final int MIDDLE_NODES_NUMBER = 20; //number of nodes between start and end nodes
	public static final int MIN_NODE_DISTANCE = 150; //minimum distance between nodes during node generation
	public static final int LINK_DISTANCE = 270; //maximum distance to link 2 nodes when generation links/path
	public static final int LINK_DISTANCE_INC = 50; //increment to increase max link distance if node is unable to generate a single path from base value
	
	public static final int MIN_PHEROMONE = 1; //minimum pheromone value
	public static final int START_PHEROMONE = 1; //starting pheromone value
	public static final int PHEROMONE_DEC = 20; //value to decrement pheromone by
	public static final int PHEROMON_INC = 100; //value to increment pheromone by
}
