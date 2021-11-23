package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JamGUI extends Application  implements Observer<JamModel, JamClientData>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;
    private JamModel model;
    private ArrayList<Car> cars;
    private int ROWS ;
    private int COLS ;
    private Label label_top,label_win;
    private String [][] board ;
    private PlayerButton [][] buttons ;
    private String loaded_file;
    private GridPane gridpane;
    private BorderPane border_pane;


    public void init() throws FileNotFoundException {
        String filename = getParameters().getRaw().get(0);


        this.model = new JamModel(filename);
        model.addObserver(this);

    }
    private void  makeGridPane(){ // makes a gridpane
         gridpane = new GridPane();

        COLS = model.getCurrentConfig().getCol();

         ROWS = model.getCurrentConfig().getRow();

        cars = model.getCurrentConfig().getCars();
         board = model.getCurrentConfig().getBoard() ;
        String car_name;
        this.buttons = new PlayerButton[ROWS][COLS];

        for (int row=0; row<ROWS; ++row) { // creates the grid of buttons

            for (int col=0; col<COLS; ++col) {

                       car_name = board[row][col];

                        // custom player button takes in row and col and cars name
                        PlayerButton button = new PlayerButton(car_name,row,col);
                        // change the car when pressed
                        buttons[row][col] = button;

                        button.setText(car_name);
                        button.setMinSize(ICON_SIZE, ICON_SIZE);
                        button.setMaxSize(ICON_SIZE, ICON_SIZE);


                        button.setOnAction(event ->{


                            model.select(button.getRow(),button.getCol());


                        });

                // JavaFX uses (x, y) pixel coordinates instead of
                // (row, col), so must invert when adding
                gridpane.add(button, col, row);


            } // end for col
        }// end for row


    }
    private class PlayerButton extends Button {


        private int row;
        private int col;
        private String car_name;

        /**
         * Create the button with the
         *
         *
         */
        public PlayerButton(String car_name, int row , int col) {

            this.row = row;
            this.col =col;
            this.car_name = car_name;


        }






        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        /**Button button1 = new Button();
        button1.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                "-fx-background-color: " + X_CAR_COLOR + ";" +
                "-fx-font-weight: bold;");
        button1.setText("X");
        button1.setMinSize(ICON_SIZE, ICON_SIZE);
        button1.setMaxSize(ICON_SIZE, ICON_SIZE);
        **/
        VBox hbox3 = new VBox(5);
        StackPane left_pane2 = new StackPane();

        TextField file_input = new TextField();
        Label label_right = new Label("Load:");
        Label label_two = new Label("Enter the file you want");
        Label label_three = new Label("Hit enter to load it");
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                String file = file_input.getText();

                model.Load(file);



            }
        };
        file_input.setOnAction(event2);

        hbox3.getChildren().add(label_right);
        hbox3.getChildren().add(label_two);
        hbox3.getChildren().add(label_three);
        hbox3.getChildren().add(file_input);

        left_pane2.getChildren().add(hbox3);


        label_win = new Label("      JAM GUI     ");

        BorderPane.setAlignment(label_win, Pos.TOP_CENTER);

        HBox hbox2 = new HBox(50); // to look nice

        BorderPane top_pane = new BorderPane(hbox2);
        label_top = new Label("Welcome Move the cars till you get X to the end of the row"); // displays connect 4 gui
        hbox2.getChildren().add(label_win);
        hbox2.getChildren().add(label_top);



        BorderPane.setAlignment(label_top, Pos.CENTER);

          makeGridPane();


        HBox hbox = new HBox(50); // to look nice
        // the translate button is in a horizontal box so it can be centered


        Button Hint = new Button("Hint");
        Button Quit = new Button("Quit");
        Button Reset = new Button("Reset");


        Hint.setOnAction((event) -> {

        model.hint();

        });
        Quit.setOnAction((event) -> {

        stage.close();

        });
        Reset.setOnAction((event) -> {

        model.reset();

        });



        hbox.getChildren().add(Hint); // add buttons to Hbox
        hbox.getChildren().add(Quit);
        hbox.getChildren().add(Reset);





        BorderPane bottom_pane = new BorderPane(hbox);


        //Label label_right = new Label("");
        Label label_left = new Label("");




         border_pane = new BorderPane(gridpane,
                top_pane, label_left, bottom_pane, left_pane2);


        border_pane.setCenter(gridpane);
        BorderPane.setAlignment(gridpane, Pos.CENTER);



        Scene scene = new Scene(border_pane);

        stage.setScene(scene);

        stage.show();
    }

    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {


            if(model.getGameStatus()){
                label_top.setText("Solution Found Nice"); // alert for first time u find sol

            }

            else {

                label_top.setText(jamClientData.getData()); // alert texts

            }
            if(jamClientData.getData().split(" ")[0].equals("loaded")){

                makeGridPane();
                border_pane.setCenter(gridpane);

            } else{
            board = model.getCurrentConfig().getBoard();
            for (int row=0; row<ROWS; ++row) { // updates buttons
                String car_name;
                for (int col=0; col<COLS; ++col) {

                    car_name = board[row][col];



                    buttons[row][col].setText(car_name);


                } // end for col
            }// end for row

        }


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
