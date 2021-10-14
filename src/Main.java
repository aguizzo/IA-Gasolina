import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
import IAGas.GasolinaState;
import IA.Gasolina.Gasolinera;

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

        if (operation == 1) {
            System.out.println("Introduce a seed number, or '0', if you want a random seed:"); 
            Integer seed = input.nextInt(); 
            if (seed == 0) {
                Random random = new Random(); 
                seed = random.nextInt(); 
            }

            System.out.println("Introduce the number of trucks per distribution center");
            Integer number_trucks = input.nextInt(); 
            if (number_trucks <= 0) {
                System.out.println("ERROR. The number of trucks has to be a positive number, and not 0. Exiting");
                return; 
            }

            GasolinaState state = new GasolinaState(number_trucks, seed); 

            System.out.println("If you want to use the simple initial solution system, type: 1.");
            System.out.println("If you want to use the comlex initial solution system, type: 2.");
            

        }

        else if (operation ==  2) {

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
