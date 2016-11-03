package frontend;

import java.util.ArrayList;
import java.util.Random;

import backend.Ant;
import backend.Node;
import backend.World;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	final private int CANVAS_WIDTH = 900;
	final private int CANVAS_HEIGHT = 800;
	final private int RIGHT_PANEL_WIDTH = 200;
	final private int MIDDLE_NODES_NUMBER = 15;
	
	private GraphicsContext gc;
	private Random r = new Random();
	private World world;
	
	@Override
	public void start(Stage primaryStage) {
		
        BorderPane root = new BorderPane(); //root is a BorderPane
        
        VBox rightPanel = new VBox(); //right panel (buttons) is VBox
        setupRightPanel(rightPanel);
        root.setRight(rightPanel);
        
        Canvas centerPanel = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT); //center panel is Canvas
        gc = centerPanel.getGraphicsContext2D();
        world = new World(CANVAS_WIDTH, CANVAS_HEIGHT, MIDDLE_NODES_NUMBER, gc);
        setupCenterPanel();
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
                new Ant(gc, world.nodeList.get(0));
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
        
	}
	
	private void setupCenterPanel() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
	private void generateNodes() {
		setupCenterPanel();
		world.generateNodes();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
