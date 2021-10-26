import IAGas.*;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        GasolinaState state = new GasolinaState(1, 1234, 10, 100, false);
        //state.ComplexInitialSolution();
        GasolinaState.tipus_heuristica = 1;
        GasolinaState.opcio_s_annealing = 4;
        long timeStart = System.currentTimeMillis();
        GasolinaHillClimbingSearch(state);
        long timeEstimated = System.currentTimeMillis() - timeStart;
        System.out.println("Execution Time: " + (timeEstimated/1000.0) + " seconds");

        /*
        System.out.println("For Hill Climbing, type: 1"); 
        System.out.println("For Simulated Annealing, type: 2");
        System.out.println("If you want Simulated Annealing, and compare all the S.A parameters into a CSV file, type: 3");
        System.out.println("If you want to exit the program, type any other number");  

        Scanner input = new Scanner(System.in); 

        int operation = input.nextInt(); 

        if (operation == 1 || operation == 2 || operation == 3) {

            System.out.println("If you want the number of centers and gas stations to be created randomly, type: 1"); 
            System.out.println("If not, type: 0"); 

            int is_random = input.nextInt(); 

            int seed; 
            int explicit_centers; 
            int explicit_gas_stations; 
            boolean do_random; 

            //SEED
            System.out.println("Introduce a seed number, or '0', if you want a random seed:"); 
            seed = input.nextInt(); 
            if (seed == 0) {
                Random random = new Random(); 
                seed = random.nextInt(); 
                System.out.println("You've chosed a random seed"); 
            }
            else {
                System.out.println("You've chosed the seed: " + seed); 
            }

            if (is_random == 1) {
                do_random = true; 
                explicit_centers = 0; 
                explicit_gas_stations = 0; 
                
            }

            else {
                do_random = false; 
                System.out.println("Introduce the exact number of centers you want to create on the simulation"); 
                explicit_centers = input.nextInt(); 

                System.out.println("Introduce the exact number of gas stations you want to create on the simulation"); 
                explicit_gas_stations = input.nextInt(); 

            }

            //Number of trucks
            System.out.println("Introduce the number of trucks per distribution center. If you want to use the default number (1), type -1");
            Integer number_trucks = input.nextInt(); 
            if (number_trucks <= 0) {
                number_trucks = 1; 
            }
                System.out.println("You've chosed " + number_trucks + " number of trucks per distribution center");

            //state creation
            GasolinaState state = new GasolinaState(number_trucks, seed, explicit_centers, explicit_gas_stations, do_random); 

            System.out.println(GasolinaState.numero_centros + " centers have been created.");
            System.out.println(GasolinaState.numero_gasolineras + " gas stations have been created.");


            //Initial solution
            System.out.println("If you want to use the simple initial solution system, type: 1.");
            System.out.println("If you want to use the comlex initial solution system, type: 2.");

            int complexity = input.nextInt();
            if (complexity == 1) {
                state.SimpleInitialSolution();
                System.out.println("You've chosed the simple initial solution system");
            }
            else if (complexity ==  2) {
                state.ComplexInitialSolution();
                System.out.println("You've chosed the complex initial solution system");
            }


            System.out.println("If you want to use the first heuristic, type: 1");
            System.out.println("If you want to use the second heuristic, type: 2");
            GasolinaState.tipus_heuristica = input.nextInt(); 

            if (operation == 1) {
                long timeStart = System.currentTimeMillis();
                GasolinaHillClimbingSearch(state);
                long timeEstimated = System.currentTimeMillis() - timeStart; 
                System.out.println("Execution Time: " + (timeEstimated/1000.0) + " seconds");
            }

            else if (operation == 2) {
                System.out.println("Choose one of the four options with which the successor function is able to choose new states with S annealing: 1, 2, 3 o or 4 ");
                int opcio = input.nextInt(); 
                GasolinaState.opcio_s_annealing = opcio;  
                int steps, stiter, k; 
                double lamb;  
                System.out.println("Choose the parameter 'steps'. Type a number");
                steps = input.nextInt(); 
                System.out.println("Choose the parameter 'stiter'. Type a number");
                stiter = input.nextInt(); 
                System.out.println("Choose the parameter 'k'. Type a number");
                k = input.nextInt(); 
                System.out.println("Choose the parameter 'lamb'. Type a number (double) ");
                lamb = input.nextDouble(); 
                long timeStart = System.currentTimeMillis();
                GasolinaSimulatedAnnealingSearch(state, steps, stiter, k, lamb);
                long timeEstimated = System.currentTimeMillis() - timeStart; 
                System.out.println("Execution Time: " + (timeEstimated/1000.0) + " seconds");
            }

            else if (operation == 3) {
                System.out.println("Choose one of the four options with which the successor function is able to choose new states with S annealing: 1, 2, 3 o or 4 ");
                int opcio = input.nextInt(); 
                GasolinaState.opcio_s_annealing = opcio;  
                String filename = "seed = " + seed + ", " + "centers = " + GasolinaState.numero_centros + ", " + "gas stations = " + GasolinaState.numero_gasolineras + ".csv"; 
                FileWriter fw = new FileWriter(filename, true); 
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw); 

                pw.println("Steps" + "," + "stiter" + "," + "k" + "," + "lamb" + "," + "profits" + "," + "execution time");

                for (int steps_current = 500; steps_current <= 10000; steps_current += 500) {
                    int stiter_increment = 1; 
                    for (int stiter_current = 1; stiter_current <= steps_current; stiter_current += stiter_increment) {
                        int k_increment = 1; 
                        for (int k_current = 1; k_current <= 5000; k_current += k_increment) {
                            for (double lamb_current = 0.000000001; lamb_current <= 10; lamb_current *= 10) {
                                GasolinaState state_current = new GasolinaState(state); 
                                long timeStart = System.currentTimeMillis();
                                int profits = GasolinaSimulatedAnnealingSearch_not_print_return_profits(state_current, steps_current, stiter_current, k_current, lamb_current); 
                                double timeEstimated = (System.currentTimeMillis() - timeStart)/1000.0; 
                                pw.println(steps_current + "," + stiter_current + "," + k_current + "," + lamb_current + "," + profits + "," + timeEstimated);
                                System.out.println("Steps = " + steps_current + ", " + "stiter = " + stiter_current + ", " + "k = " + k_current + ", " + "lamb = " + lamb_current + " DONE");
                            }
                            if (k_current == 5) {
                                k_increment = 5; 
                            }
                            else if (k_current == 10) {
                                k_increment = 40;
                            }
                            else if (k_current == 50) {
                                k_increment = 50; 
                            }
                            else if (k_current == 500) {
                                k_increment = 500; 
                            }
                            else if (k_current == 1000) {
                                k_increment = 4000; 
                            }
                        }
                        if (stiter_current == 1) {
                            stiter_increment = 9; 
                        }
                        else if (stiter_current == 10) {
                            stiter_increment = 40; 
                        }
                        else if (stiter_current == 50) {
                            stiter_increment = 50; 
                        }
                        else if (stiter_current == 100) {
                            stiter_increment = 100; 
                        }
                        else if (stiter_current == 500) {
                            stiter_increment = 500; 
                        }
                    }
                }

                pw.flush();
                pw.close();

            }
            

            return; 


        }

        else {
            return; 
        }

         */

    }

    private static void GasolinaHillClimbingSearch (GasolinaState state) {
        System.out.println("\nTSP HillClimbing  -->");

        try {
            
            Problem problem =  new Problem(state,new GasolinaSuccessorFunction1(), new GasolinaGoalTest(),new GasolinaHeuristic());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());

            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).toString());
            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).correspondenciasToString());

            System.out.println("Finished");
            
            System.out.println();
            GasolinaState last = (GasolinaState) search.getGoalState();
            System.out.println("Last state: ");
            last.imprimirState();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void GasolinaSimulatedAnnealingSearch (GasolinaState state, int steps, int stiter, int k, double lamb) {
        System.out.println("\nTSP Simulated Annealing  -->");

        try {

            Problem problem =  new Problem(state,new GasolinaSuccessorFunction2(), new GasolinaGoalTest(),new GasolinaHeuristic());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,stiter,k,lamb);   
            search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            //printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());

            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).toString());
            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).correspondenciasToString());

            System.out.println("Finished");



            System.out.println();
            GasolinaState last = (GasolinaState) search.getGoalState();
            System.out.println("Last state: ");
            last.imprimirState(); 
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Same as the other S A function, but instead of printing information, it returns a value, which are the profits
    private static int GasolinaSimulatedAnnealingSearch_not_print_return_profits (GasolinaState state, int steps, int stiter, int k, double lamb) {
        //System.out.println("\nTSP Simulated Annealing  -->");

        try {

            Problem problem =  new Problem(state,new GasolinaSuccessorFunction2(), new GasolinaGoalTest(),new GasolinaHeuristic());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,stiter,k,lamb);   
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            
            //System.out.println();
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());

            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).toString());
            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).correspondenciasToString());

            //System.out.println("Finished");



            //System.out.println();
            GasolinaState last = (GasolinaState) search.getGoalState();
            //System.out.println("Last state: ");
            //last.imprimirState(); 

            return last.beneficis; 

        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1; 
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

}

