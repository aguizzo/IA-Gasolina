import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IAGas.*;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("For Hill Climbing, type: 1"); 
        System.out.println("For Simulated Annealing, type: 2");
        System.out.println("If you want to exit the program, type any other number");  

        Scanner input = new Scanner(System.in); 

        int operation = input.nextInt(); 

        if (operation == 1 || operation == 2) {

            System.out.println("If you want the number of centers and gas stations to be created randomly, type: 1"); 
            System.out.println("If not, type: 0"); 

            int is_random = input.nextInt(); 

            int seed; 
            int explicit_centers; 
            int explicit_gas_stations; 
            boolean do_random; 

            if (is_random == 1) {
                do_random = true; 
                explicit_centers = 0; 
                explicit_gas_stations = 0; 
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

                }

            else {
                do_random = false; 
                seed = 0; 
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


            //Initial solution
            System.out.println("If you want to use the simple initial solution system, type: 1.");
            System.out.println("If you want to use the comlex initial solution system, type: 2.");

            if (input.nextInt() == 1) {
                state.SimpleInitialSolution();
                System.out.println("You've chosed the simple initial solution system");
            } 
            else if (input.nextInt() ==  2) {
                state.ComplexInitialSolution();
                System.out.println("You've chosed the complex initial solution system");
            }
            

            if (operation == 1) {
                GasolinaHillClimbingSearch(state);
            }

            else if (operation == 2) {
                System.out.println("Choose one of the four options with which the successor function is able to choose new states with S annealing: 1, 2, 3 o or 4 ");
                int opcio = input.nextInt(); 
                GasolinaState.opcio_s_annealing = opcio;  
                GasolinaSimulatedAnnealingSearch(state);
            }

            return; 


        }

        else {
            return; 
        }

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
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void GasolinaSimulatedAnnealingSearch (GasolinaState state) {
        System.out.println("\nTSP Simulated Annealing  -->");

        try {
            Problem problem =  new Problem(state,new GasolinaSuccessorFunction2(), new GasolinaGoalTest(),new GasolinaHeuristic());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001);    //QUE NUMEROS PONEMOS?
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());

            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).toString());
            //System.out.println("\n" + ((AzamonEstado) search.getGoalState()).correspondenciasToString());

            System.out.println("Finished");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
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









        



















        // Ejemplo salida datos

        /*
        CentrosDistribucion c = new CentrosDistribucion(10, 5, 321); //10 centros, 5 camiones


        for(int i = 0; i < c.size(); ++i) {
            System.out.println("Centro " + i + ": " + (c.get(i)).getCoordX() + " " + (c.get(i)).getCoordY());
        }


        Gasolineras g = new Gasolineras(50, 321); //50 gasolineras

        for(int i = 0; i < g.size(); i++) {
            System.out.println("Gasolinera " + i + ": " + g.get(i).getCoordX() + " " + g.get(i).getCoordY() + " tiene " + g.get(i).getPeticiones() + " peticiones");
        }
        */
        
        //END Ejemplo salida datos




}
