package frontend;

import java.util.ArrayList;
import java.util.Random;

import backend.Node;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	final private int CANVAS_WIDTH = 900;
	final private int CANVAS_HEIGHT = 700;
	final private int RIGHT_PANEL_WIDTH = 200;
	final private int MIDDLE_NODES_NUMBER = 15;
	private GraphicsContext gc;
	private Random r = new Random();
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	
	@Override
	public void start(Stage primaryStage) {

        BorderPane root = new BorderPane(); //root is a BorderPane
        
        VBox rightPanel = new VBox(); //right panel (buttons) is VBox
        setupRightPanel(rightPanel);
        root.setRight(rightPanel);
        
        
        Canvas centerPanel = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT); //center panel is Canvas
        gc = centerPanel.getGraphicsContext2D();
        setupCenterPanel();
        root.setCenter(centerPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + RIGHT_PANEL_WIDTH, CANVAS_HEIGHT);

        primaryStage.setTitle("Ant Optimisation Algorithm");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void setupRightPanel(VBox rightPanel) {
		Button btn = new Button();
        btn.setText("Generate Nodes");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                generateNodes();
            }
        });
        
        rightPanel.getChildren().add(btn);
	}
	
	private void setupCenterPanel() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
	private void generateNodes() {
		setupCenterPanel();
		nodeList.clear();
		Node startNode = new Node(50, 50, gc, "start");
		nodeList.add(startNode);
		Node endNode = new Node(CANVAS_WIDTH - 50, CANVAS_HEIGHT - 50, gc, "end");
		nodeList.add(endNode);
		
		for (int i = 0; i < MIDDLE_NODES_NUMBER; i++) {
			generateNextNode("middle");
		}
	}
	
	private void generateNextNode(String type) {
		int newX = r.nextInt(CANVAS_WIDTH - 60) + 30;
		int newY = r.nextInt(CANVAS_HEIGHT - 60) + 30;
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

	public static void main(String[] args) {
		launch(args);
	}

}
