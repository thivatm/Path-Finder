package Main;

import Entity.Cell;
import Entity.GridMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author THIVAGAR MAHENDRAN 2016349 w1654202
 */

public class Main extends Application {

    /**
     * A VBox to contain all the options and
     * text fields
     */
    private VBox options = new VBox();
    private HBox container1 = new HBox();
    private HBox container2 = new HBox();
    private HBox container3 = new HBox();
    private HBox container4 = new HBox();
    private HBox container5 = new HBox();
    private HBox container6 = new HBox();
    private HBox container7 = new HBox();
    private HBox container8 = new HBox();

    private Label startCoordsLbl = new Label("STARTING POINT");
    private TextField startX = new TextField("X");
    private TextField startY = new TextField("Y");

    private Label endCoordsLbl = new Label("END POINT");
    private TextField endX = new TextField("X");
    private TextField endY = new TextField("Y");

    private Label costLbl = new Label("Cost : ");
    private Label costDisLbl = new Label(" ");
    private Label tymLbl = new Label("Time : ");
    private Label tymDisLbl = new Label(" ");

    private Rectangle rectangleCell;
    private Rectangle tRect;

    private ToggleGroup radioGroup = new ToggleGroup();
    private RadioButton rb1 = new RadioButton("Manhattan");
    private RadioButton rb2 = new RadioButton("Euclidean");
    private RadioButton rb3 = new RadioButton("Chebyshev");

    private ToggleGroup radioGroup2 = new ToggleGroup();
    private RadioButton rbGrid1 = new RadioButton("20x20");
    private RadioButton rbGrid2 = new RadioButton("40x40");

    private Button findPathBtn = new Button("FIND PATH");;
    private Button clearBtn =  new Button("CLEAR ALL");

    private GridPane grids;
    private BorderPane mainLayout;


    private Cell startCell;
    private Cell endCell;
    private PriorityQueue<Cell> pathCells;
    private char ch = 'm';

    GridMap map;

    private int totalHCost;
    private int totalGCost;
    private int finalCost;
    /**
     * A 2D array which contains the weight of each cell
     */
//    private int [][] myAray = {
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
//            {3, 3, 0, 3, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
//            {3, 3, 3, 3, 3, 3, 0, 0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0},
//            {3, 3, 3, 3, 3, 3, 0, 0, 1, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0},
//            {0, 0, 3, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 0, 0, 1, 1, 0, 0},
//            {0, 3, 3, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0},
//            {3, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
//            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3},
//            {0, 0, 1, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3},
//            {0, 1, 2, 2, 2, 2, 1, 1, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0},
//            {0, 1, 2, 1, 1, 1, 2, 1, 3, 0, 0, 0, 3, 3, 3, 3, 1, 0, 0, 0},
//            {0, 1, 1, 0, 0, 0, 3, 3, 3, 3, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0},
//            {0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4},
//            {0, 0, 3, 3, 3, 3, 0, 0, 0, 1, 1, 4, 4, 0, 0, 0, 0, 0, 0, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 4, 4, 0, 0, 4, 4, 4},
//            {0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4},
//            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4},
//    };

    static int[][] gridCosts = {{1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,1,1,1,1,1},
                                {4,4,1,4,1,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1},
                                {4,4,4,4,4,4,1,1,2,3,3,3,2,1,1,1,1,1,1,1},
                                {4,4,4,4,4,4,1,1,2,3,3,3,3,2,1,1,1,1,1,1},
                                {1,1,4,1,1,1,1,1,2,2,3,3,3,2,1,1,2,2,1,1},
                                {1,4,4,1,2,2,1,1,1,2,2,2,2,2,1,2,2,2,1,1},
                                {4,2,1,1,2,2,1,1,1,1,1,1,1,1,1,2,2,1,1,1},
                                {1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4},
                                {1,1,2,3,3,2,2,1,1,1,1,1,1,1,1,1,1,4,4,4},
                                {1,2,3,3,3,3,2,2,1,1,1,1,4,4,4,4,4,4,4,1},
                                {1,2,3,2,2,2,3,2,4,1,1,1,4,4,4,4,2,1,1,1},
                                {1,2,2,1,1,1,4,4,4,4,1,1,1,1,1,1,1,1,1,1},
                                {1,1,4,4,4,4,4,4,4,4,1,1,1,1,1,1,1,1,1,1},
                                {4,4,4,4,4,4,4,4,4,1,1,5,1,1,1,1,1,1,1,5},
                                {1,1,4,4,4,4,1,1,1,2,2,5,5,1,1,1,1,1,1,5},
                                {1,1,1,1,1,1,1,1,1,1,2,1,5,5,5,1,1,5,5,5},
                                {1,2,2,2,2,2,1,1,1,2,1,1,1,5,5,5,5,5,5,5},
                                {2,2,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5},
                                {1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5},
                                {1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,5,5,5}};

    static int[][] gridCosts1 = {{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 1 , 1 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 1 , 1 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 },

            { 1 , 1 , 4 , 4 , 4 , 4 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 4 , 4 , 4 , 4 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 },
            { 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 },
            { 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 },
            { 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 },
            { 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 },
            { 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 3 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 },

            { 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 3 , 3 , 2 , 2 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 3 , 3 , 2 , 2 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 5 , 5 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 },
            { 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 5 , 5 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 5 , 5 , 5 , 5 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 4 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 5 , 5 , 5 , 5 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 },

            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 },
            { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 }};



    private Rectangle[][] rectangleList = new Rectangle[gridCosts.length][gridCosts.length];

    /**
     * This function colors the cells according to the weight of each cell
     *
     * @param x
     * @param y
     * @param cell
     * @param map
     */
    public void setCellColors(int x, int y, Rectangle cell, GridMap map){

        if (map.getCells()[x][y].getMovementCost() == 5){
            cell.setFill(Color.BLACK);
        }else if (map.getCells()[x][y].getMovementCost() == 4){
            cell.setFill(Color.GREY);
        }else if (map.getCells()[x][y].getMovementCost() == 3){
            cell.setFill(Color.DARKGREY);
        }else if (map.getCells()[x][y].getMovementCost() == 2){
            cell.setFill(Color.LIGHTGREY);
        }else{
            cell.setFill(Color.WHITE);
        }


    }

    /**
     * Sets the color of both the start cell and end cell
     *
     * @param e
     */
    public void setCells(MouseEvent e){
        tRect = (Rectangle) e.getSource();
        Cell tCell = (Cell) tRect.getUserData();

        if (startCell == null){
            startCell = tCell;
            if (startCell.getMovementCost() != 5) {
                tRect.setFill(Color.GREEN);
                //Setting the coordinates to the TextFields
                startX.setText(String.valueOf(startCell.getX()));
                startY.setText(String.valueOf(startCell.getY()));
                System.out.println("SX :" + startCell.getX() + " SY :" + startCell.getY());
                System.out.println(startCell.getMovementCost());
            }else{
                startCell = null;
                System.out.println("Cant select");
            }
        }else if (endCell == null) {
            endCell = tCell;
            if (endCell.getMovementCost() != 5) {
                tRect.setFill(Color.RED);
                //Setting the coordinates to the TextFields
                endX.setText(String.valueOf(endCell.getX()));
                endY.setText(String.valueOf(endCell.getY()));
                System.out.println("EX :" + endCell.getX() + " EY :" + endCell.getY());
                System.out.println(endCell.getMovementCost());
            } else {
                endCell = null;
                System.out.println("Cant select");
            }

        }
    }

    public void clearAll(){
        if(startCell != null || endCell != null) {
            startCell = null;
            endCell = null;
            startY.setText("");
            startX.setText("");
            endX.setText("");
            endY.setText("");
            pathCells = null;
        }

    }

    /**
     * Draws the path in the gridMap
     *
     * @param path
     */
    public void drawPath(PriorityQueue<Cell> path){
        for (Cell pathCell: path){
            for (int x = 0; x < path.size(); x++){
                for (int y = 0; y < path.size(); y++){
                    rectangleList[pathCell.getX()][pathCell.getY()].setFill(Color.PURPLE);
                }
            }
        }
    }

    public void calculateGCost(PriorityQueue<Cell> path){
        for (Cell pathCell: path){
            totalGCost += pathCell.getMovementCost();
        }

    }


    public static void main(String[] args){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Path Finder");

        rb1.setToggleGroup(radioGroup);
        rb2.setToggleGroup(radioGroup);
        rb3.setToggleGroup(radioGroup);

        startCoordsLbl.setStyle("-fx-font-size: 40px");
        container1.getChildren().add(startCoordsLbl);
        container1.setPadding(new Insets(20,20,20,20));
        container1.setAlignment(Pos.CENTER);

        container2.getChildren().addAll(startX, startY);
        container2.setPadding(new Insets(20,20,20,20));
        container2.setAlignment(Pos.CENTER);

        endCoordsLbl.setStyle("-fx-font-size: 40px");
        container3.getChildren().add(endCoordsLbl);
        container3.setPadding(new Insets(20,20,20,20));
        container3.setAlignment(Pos.CENTER);

        container4.getChildren().addAll(endX,endY);
        container4.setPadding(new Insets(20,20,20,20));
        container4.setAlignment(Pos.CENTER);

        container5.getChildren().addAll(findPathBtn, clearBtn);
        container5.setPadding(new Insets(20,20,20,20));
        container5.setAlignment(Pos.CENTER);

        container6.getChildren().addAll(rb1,rb2,rb3);
        container6.setAlignment(Pos.CENTER);

        costLbl.setStyle("-fx-font-size: 30px");
        costDisLbl.setStyle("-fx-font-size: 30px");
        tymLbl.setStyle("-fx-font-size: 30px");
        tymDisLbl.setStyle("-fx-font-size: 30px");

        container7.getChildren().addAll(costLbl, costDisLbl, tymLbl, tymDisLbl);
        container7.setPadding(new Insets(20,10,20,10));
        container7.setAlignment(Pos.CENTER);

        container8.getChildren().addAll(rbGrid1,rbGrid2);
        container8.setAlignment(Pos.CENTER);

        options.getChildren().addAll(container1,container2,container3,container4,container6,container5, container7, container8);
        options.setPadding(new Insets(100,20,50,20));

        rb1.setSelected(true);

        grids = new GridPane();
        mainLayout = new BorderPane();
        rbGrid1.setSelected(true);

        map = new GridMap(gridCosts);

        for (int x = 0; x < gridCosts.length; x++){
            for (int y = 0; y < gridCosts.length; y++){

                rectangleCell = new Rectangle(40,40);//Rectangle cell to be added in GridPane

                //Creating a cell data variable to store each cell in each respective cells
                Cell cellData = map.getCells()[x][y];
                rectangleCell.setUserData(cellData);
                rectangleList[x][y] = rectangleCell;

                //Calling setCellColors method
                setCellColors(x, y, rectangleList[x][y], map);
                //Border color of each rectangle
                rectangleCell.setStroke(Color.BLUE);

                grids.add(rectangleList[x][y], y, x);

                rectangleCell.setOnMouseClicked(e -> setCells(e));

            }
        }

        findPathBtn.setOnMouseClicked(event -> {

            if (rb1.isSelected()){
                ch = 'm';

            }else if(rb2.isSelected()){
                ch = 'c';
            }else if (rb3.isSelected()){
                ch = 'e';
            }
            long timeS = System.nanoTime();

            pathCells = map.pathFinder(startCell.getX(), startCell.getY(), endCell.getX(), endCell.getY(), ch);

            drawPath(pathCells);

            long timeE = System.nanoTime();

            calculateGCost(pathCells);
            System.out.println((timeE - timeS)/1000000 + "mS");
            System.out.println("TG" + totalGCost);

            costDisLbl.setText(String.valueOf(totalGCost) + " ");
            tymDisLbl.setText(String.valueOf((timeE - timeS)/1000000) + "ms");

        });

        clearBtn.setOnMouseClicked(event -> {
            clearAll();
        });



        mainLayout.setLeft(grids);
        mainLayout.setRight(options);
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
