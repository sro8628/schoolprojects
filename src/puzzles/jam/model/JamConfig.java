package puzzles.jam.model;

// TODO: implement your JamConfig for the common solver

import puzzles.common.solver.Configuration;
import puzzles.jam.solver.Jam;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JamConfig implements Configuration {




    private int col;
    private int row;
    private int total_cars;
    private ArrayList<Car> cars = new ArrayList<Car>();
    private String [][] Board;


    public ArrayList<Car> getCars() {



        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public String[][] getBoard() {
        return Board;
    }

    public int getTotal_cars() {
        return total_cars;
    }



    public int getRow() {
        return row;
    }



    public int getCol() {
        return col;
    }

    public JamConfig(String filename) throws FileNotFoundException {

        Scanner f = new Scanner(new File(filename));
        row = f.nextInt();
        col = f.nextInt();

        f.nextLine();
        String input = "";

        total_cars = f.nextInt(); // total amount of cars
        f.nextLine();


        while(f.hasNextLine()){ // goes till end of file

            input = f.nextLine(); // gets the line
            String [] separation = input.split(" "); // seperates the space so we can put cars in list
            //System.out.println(separation[0] + separation[1] +separation[2]+ separation[3]+ separation[4]);

            //Integer.parseInt(separation[1]);

            Car vroom = new Car(separation[0],Integer.parseInt(separation[1]),Integer.parseInt(separation[2]),
                    Integer.parseInt(separation[3]),Integer.parseInt(separation[4])); // makes a car with first letter than its cooridnates

            cars.add(vroom);

        }
        this.Board = new String[row][col];

        for (int i = 0; i < row; i++) { // fill board with blank spots and car spots

            for (int j = 0; j < col; j++){

                this.Board[i][j] = "."; // blank spot




            }

        }


        for (int k = 0; k < cars.size(); k++) { // fill board with cars
            String car_name =  cars.get(k).getCar_name();
            //System.out.println(car_name);
            this.Board[cars.get(k).getStart_x()][cars.get(k).getStart_y()] = car_name;
            this.Board[cars.get(k).getEnd_x()][cars.get(k).getEnd_y()] =  car_name;

            if(cars.get(k).getStart_x()== cars.get(k).getEnd_x()){// same row first cordinate horizontal


                for(int z = cars.get(k).getStart_y(); z <cars.get(k).getEnd_y(); z++){

                    this.Board[cars.get(k).getEnd_x()][z] =  car_name;


                }





            }else if(cars.get(k).getStart_y()==cars.get(k).getEnd_y()){// same col second coor vertical
                for(int z = cars.get(k).getStart_x(); z < cars.get(k).getEnd_x(); z++){

                    this.Board[z][cars.get(k).getEnd_y()] =  car_name;


                }



            }

        }



       // for (int i = 0; i < row; i++) { // print board to make sure cars are where they need to be get rid of after

           // for (int j = 0; j < col; j++){


               // System.out.print(this.Board[i][j]);

         //   }
          //  System.out.println();
 //       }





        f.close();

    }



    public JamConfig(JamConfig copy,String direction,int car) {




        this.total_cars = copy.total_cars;
        this.col = copy.col;
        this.row = copy.row;
        this.Board = new String[this.row][this.col];


        for(int i =0 ; i< this.row ; i++){
            this.Board [i] = copy.Board[i].clone(); // copy orginal board

        }




        this.cars = new ArrayList<>();

        for(int k = 0; k<total_cars ;k++){

            Car vroom2 = new Car(copy.cars.get(k).getCar_name(),copy.cars.get(k).getStart_x(),copy.cars.get(k).getStart_y(),
                    copy.cars.get(k).getEnd_x(),copy.cars.get(k).getEnd_y());

            this.cars.add(vroom2);

        }



        int i = car;
        String car_name = cars.get(i).getCar_name();

        if(direction.equals("west")){
            for (int z = 0; z < col; z++) { // clear off the row for that car
                if (this.Board[this.cars.get(i).getEnd_x()][z].equals(car_name)) {

                    this.Board[this.cars.get(i).getEnd_x()][z] = ".";

                }

            }
            this.cars.get(i).setEnd_y(this.cars.get(i).getEnd_y() - 1); // set minus 1 west
            this.cars.get(i).setStart_y(this.cars.get(i).getStart_y() - 1); // set minus 1 west



            this.Board[this.cars.get(i).getStart_x()][this.cars.get(i).getStart_y()] = car_name; // fill board with cars up
            this.Board[this.cars.get(i).getEnd_x()][this.cars.get(i).getEnd_y()] = car_name;

            for (int j = this.cars.get(i).getStart_y(); j < this.cars.get(i).getEnd_y(); j++) {

                this.Board[this.cars.get(i).getEnd_x()][j] = car_name;


            }

        }
        else if (direction.equals("east")){

            for (int z = 0; z < col; z++) { // clear off the row for that car
                if (this.Board[this.cars.get(i).getEnd_x()][z].equals(car_name)) {

                    this.Board[this.cars.get(i).getEnd_x()][z] = ".";

                }

            }


            this.cars.get(i).setEnd_y(this.cars.get(i).getEnd_y() + 1); // set add east  +1
            this.cars.get(i).setStart_y(this.cars.get(i).getStart_y() + 1); // set add east + 1



            this.Board[this.cars.get(i).getStart_x()][this.cars.get(i).getStart_y()] = car_name; // fill board with cars up
            this.Board[this.cars.get(i).getEnd_x()][this.cars.get(i).getEnd_y()] = car_name;

            for (int j = this.cars.get(i).getStart_y(); j < this.cars.get(i).getEnd_y(); j++) {

                this.Board[this.cars.get(i).getEnd_x()][j] = car_name;


            }


        }else if(direction.equals("north")){
            for (int z = 0; z < row; z++) { // clear off the col for that car
                if (this.Board[z][this.cars.get(i).getEnd_y()].equals(car_name)) {

                    this.Board[z][this.cars.get(i).getEnd_y()] = ".";

                }

            }
            this.cars.get(i).setEnd_x(this.cars.get(i).getEnd_x() - 1); // set add north  +1
            this.cars.get(i).setStart_x(this.cars.get(i).getStart_x() - 1); // set add north + 1

            this.Board[this.cars.get(i).getStart_x()][this.cars.get(i).getStart_y()] = car_name; // fill board with cars up
            this.Board[this.cars.get(i).getEnd_x()][this.cars.get(i).getEnd_y()] = car_name;

            for(int j = this.cars.get(i).getStart_x(); j < this.cars.get(i).getEnd_x(); j++){

                this.Board[j][this.cars.get(i).getEnd_y()] =  car_name;


            }



        }else if (direction.equals("south")){
            for (int z = 0; z < row; z++) { // clear off the col for that car
                if (this.Board[z][this.cars.get(i).getEnd_y()].equals(car_name)) {

                    this.Board[z][this.cars.get(i).getEnd_y()] = ".";

                }

            }
            this.cars.get(i).setEnd_x(this.cars.get(i).getEnd_x() + 1); // set add north  +1
            this.cars.get(i).setStart_x(this.cars.get(i).getStart_x() + 1); // set add north + 1

            this.Board[this.cars.get(i).getStart_x()][this.cars.get(i).getStart_y()] = car_name; // fill board with cars up
            this.Board[this.cars.get(i).getEnd_x()][this.cars.get(i).getEnd_y()] = car_name;

            for(int j = this.cars.get(i).getStart_x(); j < this.cars.get(i).getEnd_x(); j++){

                this.Board[j][this.cars.get(i).getEnd_y()] =  car_name;


            }



        }




    }

    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> successors = new ArrayList<>();


        for (int i = 0; i < this.cars.size(); i++) { // go through all cars


            int east_col = this.cars.get(i).getEnd_y() + 1; // east direction horizontal
            int west_col = this.cars.get(i).getStart_y() - 1; // west direction  horizontal
            int north_col = this.cars.get(i).getStart_x() - 1;
             int south_col = this.cars.get(i).getEnd_x()+1;

            if (this.cars.get(i).getStart_x() == this.cars.get(i).getEnd_x()) {// same row first cordinate horizontal

                if (west_col == cars.get(i).getStart_y() - 1) { // west direction


                    if (west_col >= 0 && west_col <= col-1 ) { // makes sure doesnt go off board
                        if (this.Board[this.cars.get(i).getStart_x()][west_col].equals(".")) { // check if there is no car there
                            String west = "west";

                            JamConfig copy = new JamConfig(this,west,i);

                            successors.add(copy);

                        }

                    }

                }
                if (east_col == this.cars.get(i).getEnd_y() + 1) { // east direction


                    if (east_col >= 0 && east_col <= col-1) {// makes sure doesnt go off board
                        if (this.Board[this.cars.get(i).getStart_x()][east_col].equals(".")) { // check if there is no car there
                            String east = "east";



                            JamConfig copy = new JamConfig(this,east,i);

                            successors.add(copy);


                        }
                   }


                }

            }
            else if (this.cars.get(i).getStart_y() == this.cars.get(i).getEnd_y()) {// same col second coor vertical

                if (south_col == this.cars.get(i).getEnd_x() + 1) { // south direction


                    if (south_col >= 0 && south_col <= row -1) { // makes sure doesnt go off board
                        if (this.Board[south_col][this.cars.get(i).getStart_y()].equals(".")) { // check if there is no car there
                            String south = "south";

                            JamConfig copy = new JamConfig(this,south,i);

                            successors.add(copy);

                        }

                    }

                }
                if ( north_col == this.cars.get(i).getStart_x() -1 ) { // north direction


                    if ( north_col >= 0 &&  north_col <= row-1 ) {// makes sure doesnt go off board
                        //System.out.println(cars.get(i).getEnd_y());

                        if (this.Board[north_col][this.cars.get(i).getStart_y()].equals(".")) { // check if there is no car there
                            String north = "north";



                            JamConfig copy = new JamConfig(this,north,i);

                            successors.add(copy);


                        }
                    }


                }

            }





        } // end of cars loop







        return successors;
    }






    @Override
    public boolean isGoal() {

        for (int i = 0; i < this.cars.size(); i++) { // check for car x

            if (this.cars.get(i).getCar_name().equals("X")) {
                if(this.cars.get(i).getEnd_y() == this.col -1){ // reachs end of row is goal

                   // System.out.println();
                  //  System.out.println();
                   // for (int w = 0; w < row; w++) { // print board to make sure cars are where they need to be

                       // for (int j = 0; j < col; j++){


                          //  System.out.print(this.Board[w][j]);

                        //}
                       // System.out.println();
                   // }
                    return true;

                }



            }


        }

        return false;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JamConfig jamConfig = (JamConfig) o;
        return col == jamConfig.col && row == jamConfig.row && total_cars == jamConfig.total_cars && cars.equals(jamConfig.cars); //&& Arrays.equals(Board, jamConfig.Board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(col, row, total_cars,cars);
        //result = 31 * result + Arrays.hashCode(Board);
        return result;
    }

    public String toString() {

        StringBuilder board = new StringBuilder();

        board.append(' ');
        for (int c = 0; c < col; ++c) {
            board.append("" + c );
        }
        board.append("\n");

        for (int w = 0; w < row; w++) { // print board to make sure cars are where they need to be
            board.append(w);
            for (int j = 0; j < col; j++){

                board.append(this.Board[w][j]);


            }
            board.append("\n");
        }

           String single = board.toString();

        return single;
    }


}
