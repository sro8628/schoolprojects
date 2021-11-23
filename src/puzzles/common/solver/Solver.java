package puzzles.common.solver;

import java.util.*;

public class Solver {

    private  Map<Configuration, Configuration> predecessors = new HashMap<>();// construct the predecessors data structure
    private  List<Configuration> queue = new LinkedList<Configuration>();
    List<Configuration> final_path = new LinkedList<>();

    public List<Configuration> getFinal_path() {
        return final_path;
    }

    public List<Configuration> getShortestPath(Configuration start) {





        queue.add(start);// prime the queue with the starting node

        // put the starting node in, and just assign itself as predecessor
        predecessors.put(start, null);



        // loop until either the finish node is found, or the queue is empty
        // (no path)
        Configuration final_node = null;
        Configuration current = null;

        while (!queue.isEmpty()) {
            // the next node to process is at the front of the queue
            current = queue.remove(0);

            if (current.isGoal()) {

                 final_node = current;

                // final_path = constructPath(predecessors,  final_node);

                return constructPath(predecessors,  final_node);
                // break;




            }
            // loop over all neighbors of current
             for (Configuration nbr : current.getSuccessors()) {
                // process unvisited neighbors
                if(!predecessors.containsKey(nbr)) {
                    predecessors.put(nbr, current);
                    queue.add(nbr);
                }
            }
        }

        // construct the path from the predecessor map and return the
        // sequence from start to finish nodes
        return constructPath(predecessors,  final_node);
    }

    /**
     * Method to return a path from the starting to finishing path
     *
     * .
     *
     * @param predecessors Map used to reconstruct the path
     * @param  final_node final node
     *
     * @return a list containing the sequence of nodes comprising the path.
     * An empty list if no path exists.
     */
    private List<Configuration> constructPath(Map<Configuration,Configuration> predecessors,
                                       Configuration  final_node) {
        // use predecessors to work backwards from finish to start,
        // all the while dumping everything into a linked list
        List<Configuration> path = new LinkedList<>();


            Configuration currNode =   final_node;
            while (currNode != null) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode);
            }



        return path;
    }




}
