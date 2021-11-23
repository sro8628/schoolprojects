package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class JamModel {
    /** the collection of observers of this model */
    private  List<Observer<JamModel, JamClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;
    private String filename ;
    private Car selected_car;
    private boolean car_selected ;
    private int car_selected_col;
    private int car_selected_row;
    private  ArrayList<Car> cars;
    private JamModel model;
    private String[][] board_pieces;


    public JamModel(String filename) throws FileNotFoundException {

        currentConfig = new JamConfig(filename);
        this.filename = filename;
        car_selected = false;

        // add this observer so we can notify them of updates
        this.observers = new LinkedList<>();
    }

    public JamConfig getCurrentConfig() {
        return currentConfig;
    }

    public void Load (String filename) {

        try{
            this.filename = filename;
            currentConfig = new JamConfig(filename);

            alertObservers(new JamClientData("loaded "+filename));

        }
        catch(Exception e){


            alertObservers(new JamClientData("Error file not found Failed to load: "+filename));


        }





    }
    public void hint() {

        Solver solve = new Solver();


        List<Configuration> final_path = new ArrayList<>();
         final_path =  solve.getShortestPath(currentConfig);

        if(final_path == null){


            alertObservers(new JamClientData("hint not found"));

        }


        else {

            if(currentConfig.isGoal()){


                alertObservers(new JamClientData(" Solution already found"));
            }else if (final_path.size() == 0){



                alertObservers(new JamClientData("No solution found "));
            }
            else{

                currentConfig = (JamConfig)final_path.get(1);

                alertObservers(new JamClientData("hint found Next step!"));
            }



        }






    }
    public void select(int select_row,int select_col ) {

      int col =  currentConfig.getCol();
      int  row = currentConfig.getRow();
       board_pieces =  currentConfig.getBoard();
      ArrayList<Car> cars = currentConfig.getCars();




      if(car_selected == false){


          if(!board_pieces[select_row][select_col].equals(".")){
              car_selected_col =select_col;
              car_selected_row =select_row;
              for(int i =0; i <  cars.size(); i++ ){
                  if(board_pieces[select_row][select_col].equals(cars.get(i).getCar_name())){
                      selected_car = cars.get(i);
                      car_selected = true;
                      alertObservers(new JamClientData("Car Selected "+ " '"+selected_car.getCar_name() + " '"+" ( "+ select_row+","+ select_col+" )"));
                  }


              }



          }else{


              alertObservers(new JamClientData("there is no car there" + " ( "+ select_row+","+ select_col+" )"));

          }


      }else{

           if(board_pieces[select_row][select_col].equals(".")){ // if there is a not there then true



               // horixaoontal x= x
              if (selected_car.getStart_x() == selected_car.getEnd_x() && !(selected_car.getStart_x() < select_row||selected_car.getStart_x() > select_row)) { // car is horizontal
                    int car_length =0;


                    for(int i =0; i <  col; i++ ){// cars length
                      if(board_pieces[select_row][i].equals(selected_car.getCar_name())){
                          car_length++;

                      }

                  }

                  int car_obstructed = 0;
                  if(selected_car.getEnd_y() <=  select_col ){// east direction

                      for(int  i=selected_car.getEnd_y()+ 1;  i <=  select_col; i++ ) {

                          if (!board_pieces[select_row][i].equals(".")) {// if any cars are in path
                              car_obstructed = 1;


                          }

                      }

                      if(car_obstructed == 1){
                          car_selected = false; // cant move car here
                         alertObservers(new JamClientData("Can't move " + "( "+ car_selected_row+","+ car_selected_col+" )" +  "to"+"( "+ select_row+","+ select_col+" )"));

                      }else{

                          for(int i =0; i <  col; i++ ) {// remove car clear it
                              if (board_pieces[select_row][i].equals(selected_car.getCar_name())) {
                                  board_pieces[select_row][i] = ".";

                              }
                          }

                          for(int k =0; k <  car_length; k++ ) { // replace car


                              board_pieces[select_row][select_col - k] = selected_car.getCar_name();
                              if(k==car_length-1){ // set the coordinate for the new start y

                                  for(int i =0; i <  cars.size(); i++ ){// east direction
                                      if(board_pieces[select_row][select_col].equals(cars.get(i).getCar_name())){
                                          cars.get(i).setStart_y(select_col - k); // col minus how long the car is at each stage
                                          cars.get(i).setEnd_y(select_col);
                                      }

                                  }

                              }


                          }
                          car_selected = false; // cant move car here
                          alertObservers(new JamClientData("moved " + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));

                      }



                }// / east direction

                  else {// West direction
                      for(int  i=selected_car.getStart_y() - 1;  i >=  select_col; i-- ) {

                          if (!board_pieces[select_row][i].equals(".")) {// if any cars are in path
                              car_obstructed = 1;


                          }

                      }

                      if(car_obstructed == 1){
                          car_selected = false; // cant move car here
                          alertObservers(new JamClientData("Can't move " + "( "+ car_selected_row+","+ car_selected_col+" )" +  "to"+"( "+ select_row+","+ select_col+" )"));

                      }else{

                          for(int i =0; i <  col; i++ ) {// remove car clear it
                              if (board_pieces[select_row][i].equals(selected_car.getCar_name())) {
                                  board_pieces[select_row][i] = ".";

                              }
                          }



                          int count = select_col;// coordinate of care to go to until car is done



                          for(int k =0; k <  car_length; k++ ) { // replace car


                              board_pieces[select_row][count] = selected_car.getCar_name();

                              if(k==car_length-1){ // set the coordinate for the new start y

                                  for(int i =0; i <  cars.size(); i++ ){// West direction
                                      if(board_pieces[select_row][count].equals(cars.get(i).getCar_name())){
                                          cars.get(i).setStart_y(select_col); // col minus how long the car is at each stage
                                          cars.get(i).setEnd_y(count);
                                      }

                                  }

                              }
                              count++;

                          }
                          car_selected = false; // can move car here
                          alertObservers(new JamClientData("moved " + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));

                      }



                } // end of west direction

              }                 // vertical y = y
              else if (selected_car.getStart_y() == selected_car.getEnd_y() && !(selected_car.getStart_y() < select_col||selected_car.getStart_y() > select_col)) { // if car is vertical
                  int car_length =0;


                  for(int i =0; i <  row; i++ ){// cars length
                      if(board_pieces[i][select_col].equals(selected_car.getCar_name())){
                          car_length++;

                      }

                  }
                  int car_obstructed = 0;
                  if(selected_car.getEnd_x() <=  select_row){// south direction


                      for(int  i=selected_car.getEnd_x()+ 1;  i <=  select_row; i++ ) {

                          if (!board_pieces[i][select_col].equals(".")) {// if any cars are in path
                              car_obstructed = 1;


                          }

                      }

                      if(car_obstructed == 1){
                          car_selected = false; // cant move car here
                          alertObservers(new JamClientData("Can't move " + "( "+ car_selected_row+","+ car_selected_col+" )" +  "to"+"( "+ select_row+","+ select_col+" )"));

                      }else{

                          for(int i =0; i <  row; i++ ) {// remove car clear it
                              if (board_pieces[i][select_col].equals(selected_car.getCar_name())) {
                                  board_pieces[i][select_col] = ".";

                              }
                          }

                          for(int k =0; k <  car_length; k++ ) { // replace car


                              board_pieces[select_row -k][select_col ] = selected_car.getCar_name();
                              if(k==car_length-1){ // set the coordinate for the new start y

                                  for(int i =0; i <  cars.size(); i++ ){// south direction
                                      if(board_pieces[select_row][select_col].equals(cars.get(i).getCar_name())){
                                          cars.get(i).setStart_x(select_row - k); // col minus how long the car is at each stage
                                          cars.get(i).setEnd_x(select_row);
                                      }

                                  }

                              }


                          }
                          car_selected = false; // cant move car here
                          alertObservers(new JamClientData("moved " + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));

                      }



                  }// / south direction
                  else {// north  direction

                      for(int  i=selected_car.getStart_x() - 1;  i >=  select_row; i-- ) {

                          if (!board_pieces[i][select_col].equals(".")) {// if any cars are in path
                              car_obstructed = 1;


                          }

                      }

                      if(car_obstructed == 1){
                          car_selected = false; // cant move car here
                          alertObservers(new JamClientData("Can't move " + "( "+ car_selected_row+","+ car_selected_col+" )" +  "to"+"( "+ select_row+","+ select_col+" )"));

                      }else{

                          for(int i =0; i <  row; i++ ) {// remove car clear it
                              if (board_pieces[i][select_col].equals(selected_car.getCar_name())) {
                                  board_pieces[i][select_col] = ".";

                              }
                          }
                          int count = select_row; // coordinate of care to go to until car is done
                          for(int k =0; k <  car_length; k++ ) { // replace car


                              board_pieces[count][select_col] = selected_car.getCar_name();

                              if(k==car_length-1){ // set the coordinate for the new start y

                                  for(int i =0; i <  cars.size(); i++ ){//north  direction
                                      if(board_pieces[count][select_col].equals(cars.get(i).getCar_name())){
                                          cars.get(i).setStart_x(select_row); // col minus how long the car is at each stage
                                          cars.get(i).setEnd_x(count);
                                      }

                                  }

                              }
                              count++;

                          }
                          car_selected = false; // cant move car here
                         alertObservers(new JamClientData("moved " + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));

                      }



                  } // end of west direction






              }else{
                  car_selected = false; // cant move car here
                  alertObservers(new JamClientData("Can't move" + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));
              }

          }else{ // pos car there at coordiante

               car_selected = false; // cant move car here
               alertObservers(new JamClientData("Can't move" + "( "+ car_selected_row+","+ car_selected_col+" )" + "to"+"( "+ select_row+","+ select_col+" )"));


           }



      }


    }
    public void reset () {

        try{

            currentConfig = new JamConfig(this.filename);

            alertObservers(new JamClientData("reloaded "+ this.filename));

        }
        catch(Exception e){
            alertObservers(new JamClientData("Error file not found "));


        }





    }
    public boolean getGameStatus()  {

        String[][] board_piece = currentConfig.getBoard();
        ArrayList<Car> cars = currentConfig.getCars();
        int  col = currentConfig.getCol();


        for (int i = 0; i < cars.size(); i++) { // check for car x

            if (cars.get(i).getCar_name().equals("X")) {
                if(cars.get(i).getEnd_y() == col -1){ // reachs end of row is goal


                    return true;

                }

            }


        }

        return false;


    }





    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */



    public void addObserver(Observer<JamModel, JamClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(JamClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
    public String toString() {

        StringBuilder board = new StringBuilder();

        int col =  currentConfig.getCol();
        int row = currentConfig.getRow();
        String[][] board_pieces = currentConfig.getBoard();




        board.append("  ");
        for (int c = 0; c < col; ++c) {
            board.append(" " + c +' ');
        }
        board.append("\n");
        board.append("  ");
        for (int c = 0; c < col; ++c) {
            board.append(" " + "_"+' ');
        }

        board.append("\n");

        for (int w = 0; w < row; w++) { // print board to make sure cars are where they need to be
            board.append(w+"|");
            for (int j = 0; j < col; j++){

                board.append(" ");
                board.append(board_pieces[w][j]);
                board.append(" ");

            }
            board.append("\n");
        }

        String single = board.toString();



        return single;
    }

}







