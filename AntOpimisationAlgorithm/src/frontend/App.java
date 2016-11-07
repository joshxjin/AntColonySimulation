package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import backend.Ant;
import backend.Node;
import backend.World;
import backend.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class App extends Application {
	final private int CANVAS_WIDTH = 900;
	final private int CANVAS_HEIGHT = 800;
	final private int RIGHT_PANEL_WIDTH = 200;
	final private Duration DURATION = Duration.millis(33); //~30FPS

	private Pane centerPanel;
	private World world;
	private HashMap<Ant, Circle> liveAntsList = new HashMap<Ant, Circle>();
	private KeyFrame keyFrame;
	private Timeline timeline = new Timeline();
	private EventHandler onFinished;
	private Random r = new Random();
	private int frame = 0;
		
	@Override
	public void start(Stage primaryStage) {
		
        BorderPane root = new BorderPane(); //root is a BorderPane
        
        VBox rightPanel = new VBox(); //right panel (buttons) is VBox
        setupRightPanel(rightPanel);
        root.setRight(rightPanel);
        
        centerPanel = new Pane(); //center panel is Canvas
        setupCenterPanel();
        world = new World(CANVAS_WIDTH, CANVAS_HEIGHT, Constants.MIDDLE_NODES_NUMBER);
        root.setCenter(centerPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + RIGHT_PANEL_WIDTH, CANVAS_HEIGHT);
        
        
        onFinished = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent t) {
        		world.depreciatePheromone();
        		/*
        		frame++;
        		
        		if (frame >= 30) {
        			frame = 0;
        			world.depreciatePheromone();
        		}
        		*/
        		
        		for (Ant ant: liveAntsList.keySet()) {
        			Circle circle = liveAntsList.get(ant);
        			Boolean atX = Math.abs(circle.getCenterX() - ant.getCurrentNode().getX()) <= 2;
        			Boolean atY = Math.abs(circle.getCenterY() - ant.getCurrentNode().getY()) <= 2;
        			if (atX && atY) {
        				ant.move();
        				ant.setDx((ant.getCurrentNode().getX() - circle.getCenterX()) / 30);
        				ant.setDy((ant.getCurrentNode().getY() - circle.getCenterY()) / 30);
        			} else {
        				circle.setCenterX(circle.getCenterX() + ant.getDx());
        				circle.setCenterY(circle.getCenterY() + ant.getDy());
        			}
        		}
        	}
        };
        
        keyFrame = new KeyFrame(DURATION, onFinished);
        
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
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
                Ant ant = world.addAnt();
                Circle circle = new Circle(ant.getCurrentNode().getX(), ant.getCurrentNode().getY(), 5, Color.BLACK);

                liveAntsList.put(ant, circle);
                centerPanel.getChildren().add(circle);
                //animateAnt(ant);
            }
        });
		
		Button updateWorldBtn = new Button();
		updateWorldBtn.setText("Update World");
		updateWorldBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	animateAnts();
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
		liveAntsList.clear();
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
			
			for (Node neighbour: node.getNeighbours()) {
				centerPanel.getChildren().add(new Line(node.getX(), node.getY(), neighbour.getX(), neighbour.getY()));
			}
		}
	}
	
	/*public void animateAnts() {
		ArrayList<KeyValue> antKeyValues = new ArrayList<KeyValue>();
		
		world.updateWorld();
		
		for (Map.Entry<Ant, Circle> entry: liveAntsList.entrySet()) {
			
			Ant ant = entry.getKey();
			Circle circle = (Circle) entry.getValue();
			
			//ant.move();
			
			antKeyValues.add(new KeyValue(circle.centerXProperty(), ant.getCurrentNode().getX()));
			antKeyValues.add(new KeyValue(circle.centerYProperty(), ant.getCurrentNode().getY()));
		}
		
		keyFrame = new KeyFrame(DURATION, "moveAnts", onFinished, antKeyValues);
		
		timeline = new Timeline();
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
        
	}
	*/
	
	public void animateAnts() {
		timeline.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
