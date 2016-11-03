package frontend;

import java.util.ArrayList;
import java.util.Random;

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
		world.generateNodes();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
