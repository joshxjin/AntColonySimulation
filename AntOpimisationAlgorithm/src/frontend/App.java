package frontend;

import java.util.HashMap;
import java.util.Random;

import backend.Ant;
import backend.Node;
import backend.World;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class App extends Application {
	final private int CANVAS_WIDTH = 900;
	final private int CANVAS_HEIGHT = 800;
	final private int RIGHT_PANEL_WIDTH = 200;
	final private int MIDDLE_NODES_NUMBER = 18;

	private Pane centerPanel;
	private Random r = new Random();
	private World world;
	private HashMap<Circle, Ant> liveAntsList = new HashMap<Circle, Ant>();
		
	@Override
	public void start(Stage primaryStage) {
		
        BorderPane root = new BorderPane(); //root is a BorderPane
        
        VBox rightPanel = new VBox(); //right panel (buttons) is VBox
        setupRightPanel(rightPanel);
        root.setRight(rightPanel);
        
        centerPanel = new Pane(); //center panel is Canvas
        setupCenterPanel();
        world = new World(CANVAS_WIDTH, CANVAS_HEIGHT, MIDDLE_NODES_NUMBER);
        root.setCenter(centerPanel);
        

        Scene scene = new Scene(root, CANVAS_WIDTH + RIGHT_PANEL_WIDTH, CANVAS_HEIGHT);

        primaryStage.setTitle("Ant Optimisation Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void setupRightPanel(VBox rightPanel) {
		Button genNodesBtn = new Button();
		genNodesBtn.setText("Generate Nodes");
		genNodesBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                generateNodes();
            }
        });
		
		Button addAntBtn = new Button();
		addAntBtn.setText("Add Ant");
		addAntBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Ant ant = new Ant(World.nodeList.get(0));
                Circle circle = new Circle(ant.getCurrentNode().getX(), ant.getCurrentNode().getY(), 5, Color.CHOCOLATE);
                liveAntsList.put(circle, ant);
                centerPanel.getChildren().add(circle);
            }
        });
		
		Button updateWorldBtn = new Button();
		updateWorldBtn.setText("Update World");
		updateWorldBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                world.updateWorld();
            }
        });
        
        rightPanel.getChildren().add(genNodesBtn);
        rightPanel.getChildren().add(addAntBtn);
        rightPanel.getChildren().add(updateWorldBtn);
        
	}
	
	private void setupCenterPanel() {
		centerPanel.getChildren().clear();
		centerPanel.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		centerPanel.setStyle("-fx-background-color: white");
	}
	
	private void generateNodes() {
		setupCenterPanel();
		world.generateNodes();
		drawNodes();
	}
	
	private void drawNodes() {
		for (Node node: World.nodeList) {
			if (node.getType().equals("start"))
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.RED));
			else if (node.getType().equals("end"))
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.BLUE));
			else
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.SANDYBROWN));
			for (Node neighbour: node.getNeightbours().keySet()) {
				centerPanel.getChildren().add(new Line(node.getX(), node.getY(), neighbour.getX(), neighbour.getY()));
			}
		}
	}
	
	private void drawAnts() {
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
