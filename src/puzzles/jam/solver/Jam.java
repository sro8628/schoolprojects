package puzzles.jam.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class Jam  {
    public static void main(String[] args) throws FileNotFoundException {
        String filename;


        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        }else{
            filename = args[0];

             JamConfig  chicken = new JamConfig(filename);

            List<Configuration> final_path = new ArrayList<>();
            Solver solve = new Solver();

            final_path =  solve.getShortestPath(chicken);

            for(int i = 0; i<final_path.size();i++){
                System.out.println("step "+ i);
                System.out.println(" ");
                System.out.println(final_path.get(i));


            }


           //System.out.println(solve.getShortestPath(chicken));



        }


    }
}