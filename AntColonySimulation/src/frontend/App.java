package frontend;

import java.util.HashMap;

import backend.Ant;
import backend.Node;
import backend.World;
import javafx.animation.KeyFrame;
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
	final private int RIGHT_PANEL_WIDTH = 100;
	final private Duration DURATION = Duration.millis(33); //~30FPS

	private Pane centerPanel;
	private World world;
	private HashMap<Ant, Circle> liveAntsList = new HashMap<Ant, Circle>();
	private KeyFrame keyFrame;
	private Timeline timeline = new Timeline();
	private EventHandler<ActionEvent> onFinished;
	private int counter = 0;
	private boolean adding50Ants = false;
	private boolean adding200Ants = false;
	private boolean startSimAnts = false;
	
	private Button simluateWorldBtn, addAntsBtn1, addAntsBtn2;
		
	@Override
	public void start(Stage primaryStage) {
		
        BorderPane root = new BorderPane(); //root is a BorderPane
        
        VBox rightPanel = new VBox(); //right panel (buttons) is VBox
        setupRightPanel(rightPanel);
        root.setRight(rightPanel);
        
        centerPanel = new Pane(); //center panel is Canvas
        setupCenterPanel();
        world = new World(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(centerPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + RIGHT_PANEL_WIDTH, CANVAS_HEIGHT);
        
        
        onFinished = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent t) {
        		world.depreciatePheromone();
        		
        		//adding 1 ant per frame until counter reaches the correct ants number
        		if (adding50Ants || adding200Ants) {
        			addAnt();
        			counter++;
        			if (adding50Ants && counter >= 50) {
        				counter = 0;
        				adding50Ants = false;
        				addAntsBtn1.setDisable(false);
                    	addAntsBtn2.setDisable(false);
        			} else if (adding200Ants && counter >= 200) {
        				counter = 0;
        				adding200Ants = false;
        				addAntsBtn1.setDisable(false);
                    	addAntsBtn2.setDisable(false);
        			} else if (startSimAnts && counter >= 500) {
        				counter = 0;
        				startSimAnts = false;
        				addAntsBtn1.setDisable(false);
                    	addAntsBtn2.setDisable(false);
        			}
        		}
        		
        		//update ants
        		for (Ant ant: liveAntsList.keySet()) {
        			Circle circle = liveAntsList.get(ant);
        			Boolean atX = Math.abs(circle.getCenterX() - ant.getCurrentNode().getX()) <= 2;
        			Boolean atY = Math.abs(circle.getCenterY() - ant.getCurrentNode().getY()) <= 2;
        			if (atX && atY) { //ant get new node target to move to
        				ant.move();
        				ant.setDx((ant.getCurrentNode().getX() - circle.getCenterX()) / 30);
        				ant.setDy((ant.getCurrentNode().getY() - circle.getCenterY()) / 30);
        			} else { // move towards the target node
        				circle.setCenterX(circle.getCenterX() + ant.getDx());
        				circle.setCenterY(circle.getCenterY() + ant.getDy());
        			}
        		}
        	}
        };
        
        keyFrame = new KeyFrame(DURATION, onFinished);
        
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        primaryStage.setTitle("Ant Colony Simluation");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void setupRightPanel(VBox rightPanel) { //right panel buttons
		
		Button genNodesBtn = new Button();
		genNodesBtn.setText("Generate Nodes");
		genNodesBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	adding200Ants = false;
            	adding50Ants = false;
                generateNodes();
                simluateWorldBtn.setDisable(false);
                addAntsBtn1.setDisable(true);
            	addAntsBtn2.setDisable(true);
                timeline.stop();
            }
        });
		
		simluateWorldBtn = new Button();
		simluateWorldBtn.setDisable(true);
		simluateWorldBtn.setText("Start Simluation");
		simluateWorldBtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	startSimAnts = true;
            	simluateWorldBtn.setDisable(true);
            	timeline.play();
            }
        });
		
		addAntsBtn1 = new Button();
		addAntsBtn1.setDisable(true);
		addAntsBtn1.setText("Add 50 Ants");
		addAntsBtn1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                adding50Ants = true;
                addAntsBtn1.setDisable(true);
            	addAntsBtn2.setDisable(true);
            }
        });
		
		addAntsBtn2 = new Button();
		addAntsBtn2.setDisable(true);
		addAntsBtn2.setText("Add 200 Ants");
		addAntsBtn2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                adding200Ants = true;
                addAntsBtn1.setDisable(true);
            	addAntsBtn2.setDisable(true);
            }
        });
		
        rightPanel.getChildren().add(genNodesBtn);
        rightPanel.getChildren().add(simluateWorldBtn);
        rightPanel.getChildren().add(addAntsBtn1);
        rightPanel.getChildren().add(addAntsBtn2);
        
        
	}
	
	private void setupCenterPanel() { //center, animation panel
		centerPanel.getChildren().clear();
		centerPanel.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		centerPanel.setStyle("-fx-background-color: white");
		liveAntsList.clear();
	}
	
	private void generateNodes() {
		setupCenterPanel();
		world.cleanUp();
		world.generateNodes();
		liveAntsList.clear();
		centerPanel.getChildren().clear();
		drawNodes();
	}
	
	private void drawNodes() { //drawing node circles
		for (Node node: World.nodeList) {
			if (node.getType().equals("start"))
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.RED));
			else if (node.getType().equals("end"))
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.BLUE));
			else
				centerPanel.getChildren().add(new Circle(node.getX(), node.getY(), 15, Color.SANDYBROWN));
			
			for (Node neighbour: node.getNeighbours().keySet()) {
				centerPanel.getChildren().add(new Line(node.getX(), node.getY(), neighbour.getX(), neighbour.getY()));
			}
		}
	}
	
	public void addAnt() { //adding ants and circles to center panel
		Ant ant = world.addAnt();
        Circle circle = new Circle(ant.getCurrentNode().getX(), ant.getCurrentNode().getY(), 5, Color.BLACK);

        liveAntsList.put(ant, circle);
        centerPanel.getChildren().add(circle);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
