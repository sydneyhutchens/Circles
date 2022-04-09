package circles;

import java.util.stream.Stream;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * A class to introduce Java 8 lambdas, streams, and JavaFX
 * @author Sydney Hutchens
 */
public class Circles extends VBox {
    
    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 100;
    public static final int RADIUS = 25;
    private HBox controls; 
    private Pane canvas;
    private Button starter;
    private Spinner rowSpinner;
    private Spinner colSpinner;
    private Slider cellSize;
    private Spinner xScale;
    private Spinner yScale; 
    private int row;
    private int col;

    public Circles() {
        setAlignment(Pos.CENTER);
       
        canvas = new Pane();
        canvas.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        
        starter = new Button("Circles");
        controls = new HBox();
        controls.setAlignment(Pos.CENTER);
        
        rowSpinner = new Spinner(1,5,3);
        colSpinner = new Spinner(1,5,3);
        rowSpinner.setPrefWidth(60);
        colSpinner.setPrefWidth(60);
        cellSize = new Slider(50,150,100);
        
        
        
         xScale = new Spinner(-3,3,0);
         yScale = new Spinner(-3,3,0);
         xScale.setPrefWidth(60.0);
         yScale.setPrefWidth(60.0);
        
        rowSpinner.valueProperty().addListener( e -> { canvas.getChildren().clear();
            makeAllRows();});
        
        colSpinner.valueProperty().addListener(e -> { canvas.getChildren().clear();
            makeAllRows();});
        xScale.valueProperty().addListener(e -> { canvas.getChildren().clear();
            makeAllRows();});
        yScale.valueProperty().addListener(e -> { canvas.getChildren().clear();
            makeAllRows();});
        
        cellSize.valueProperty().addListener(e -> {  canvas.getChildren().clear();
            makeAllRows();});
        
         Label cellLabel = new Label();
       cellLabel.setPrefWidth(30.0);
         cellLabel.textProperty().bind((ObservableValue)Bindings.createStringBinding(() -> String.format("%3d", (int)cellSize.getValue()), (Observable[])new Observable[]{cellSize.valueProperty()}));        
       controls.getChildren().addAll((Node[])(Object[])new Node[]{makeLabeledNode("Rows", (Node)rowSpinner), makeLabeledNode("Columns", (Node)colSpinner),makeLabeledNode("Cell Size", (Node)new HBox(new Node[]{cellSize, cellLabel})), makeLabeledNode("X Scale", (Node)xScale),makeLabeledNode("Y Scale", (Node)yScale)});
        
        getChildren().addAll((Node[])(Object[]) new Node[]{canvas, controls});
        
        addButtonHandler();  // You must write
    }
    
     private VBox makeLabeledNode(String label, Node node){
        VBox vb = new VBox(10.0, new Node[]{new Label(label), node});
        vb.setAlignment(Pos.CENTER);
        return vb;
    }   
    
    /**
     * This method adds the handler to the button that gives
     * this application its behavior.
    */ 
    private void addButtonHandler() {
        starter.setOnAction(e -> {
            makeAllRows();
        });
    }
    
    
    public void addAllRowsToCanvas(Stream<Stream<Circle>> circle){
        row = 0;
        circle.forEach(r -> {addRowToCanvas(r); row++;});
    }
    
   private void makeAllRows() {
        canvas.getChildren().clear();
        Stream<Stream<Circle>> circles = Stream.generate(() -> this.makeRow()).limit((int)rowSpinner.getValue());
        row = 0;
        circles.forEach(r -> {
            addRowToCanvas(r);
        });
    }
    
   private Stream <Circle> makeRow() {
        return Stream.generate(() -> new Circle(RADIUS)).limit((int)colSpinner.getValue());
    }
    
    private void addRowToCanvas(Stream <Circle> circle) {
        col = 0;
        circle.forEach(c -> {addToCanvas(c);});
        row++;
    }
    
    private void addToCanvas(Circle circle){
        circle.setFill((Paint) new Color(Math.random(), Math.random(), Math.random(), 1));
        double toX = col * (int)cellSize.getValue() + ((int)cellSize.getValue()/2);
        double toY = row * (int)cellSize.getValue() + ((int)cellSize.getValue()/2);
        double fromX = ((int)colSpinner.getValue()*(int)cellSize.getValue()) - ((int)cellSize.getValue()/2);
        double fromY = ((int)rowSpinner.getValue()*(int)cellSize.getValue()) - ((int)cellSize.getValue()/2);
        circle.setCenterX(fromX);
        circle.setCenterY(fromY);
        canvas.getChildren().add((Circle)circle);
        col++;
        TranslateTransition tt = new TranslateTransition(Duration.millis((double)500));
        tt.setNode((Node)circle);
        tt.setByX(toX - fromX);
        tt.setByY(toY - fromY);
        tt.play();
        ScaleTransition st = new ScaleTransition(Duration.millis((double)(500 * Math.random() + 500)));
        st.setNode((Node)circle);
        st.setByX((int)xScale.getValue());
        st.setByY((int)yScale.getValue());
        st.setCycleCount(-1);
        st.setAutoReverse(true);
        st.play();
       
    }
}    