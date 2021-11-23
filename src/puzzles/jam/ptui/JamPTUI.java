package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class JamPTUI implements Observer<JamModel, JamClientData> {
    private JamModel model;

    public JamPTUI(String filename) throws FileNotFoundException {

        this.model = new JamModel(filename);
        initializeView();

    }

    private void initializeView() {
        this.model.addObserver(this);
    }

    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {



        System.out.println(jamModel);
        System.out.println(jamClientData.getData());
        if(model.getGameStatus()){
            System.out.println("Solution found nice job");
        }


    }


    /*
     ******************* THE CONTROLLER SECTION *********************************
     */

    /**
     * The run loop prompts for user input and makes calls into the Model.
     */

    public void run() throws FileNotFoundException {

        System.out.println(model);


        String command = "" ;



        Scanner scanner= new Scanner(System.in);
        System.out.println("H (int)        -- hint next move ");
        System.out.println("L (oad)        -- load the new puzzle file");
        System.out.println("S (elect)      -- select cell at r, c");
        System.out.println("Q (uit)        -- quit the game");
        System.out.println("R (eset)       -- reset the current game");

            while (!command.equals("Q")) {
                System.out.print(">");

                try {
                    command = scanner.next();
                }

                catch(Exception e){


                    System.out.println("Error Type again");


                }




                    if (command.equals("H")) {

                            model.hint();


                    } else if (command.equals("L")) {
                        String load_file = scanner.next();

                        model.Load(load_file);


                    } else if (command.equals("S")) {

                        try {
                        int select1 = scanner.nextInt();

                        int select2 = scanner.nextInt();
                        model.select(select1, select2);
                        }
                        catch(Exception e){


                            System.out.println("Error Type again");


                        }




                    } else if (command.equals("Q")) {

                        System.out.print("You have selected to quit the program ");

                    } else if (command.equals("R")) {

                        model.reset();

                    } else {
                        System.out.println("Invalid command");
                        System.out.println("H (int)        -- hint next move ");
                        System.out.println("L (oad)        -- load the new puzzle file");
                        System.out.println("S (elect)      -- select cell at r, c");
                        System.out.println("Q (uit)        -- quit the game");
                        System.out.println("R (eset)       -- reset the current game");


                    }

            } // end of while

    }



    /**
     * The main routine.
     *
     *
     */

    public static void main(String[] args) throws FileNotFoundException {
        String filename;

        if (args.length != 1) {
            System.out.println("File not found");
        }else{
            filename = args[0];
            System.out.println("Loaded: "+ filename);


            JamPTUI ptui = new JamPTUI(filename);

            ptui.run();




        }
    }
}
