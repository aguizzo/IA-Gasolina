package IAGas;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {







        // Ejemplo salida datos

        /*
        CentrosDistribucion c = new CentrosDistribucion(10, 5, 321); //10 centros, 5 camiones


        for(int i = 0; i < c.size(); ++i) {
            System.out.println("Centro " + i + ": " + (c.get(i)).getCoordX() + " " + (c.get(i)).getCoordY());
        }


        Gasolineras g = new Gasolineras(100, 321); //50 gasolineras

        for(int i = 0; i < g.size(); i++) {
            System.out.println("Gasolinera " + i + ": " + g.get(i).getCoordX() + " " + g.get(i).getCoordY() + " tiene " + g.get(i).getPeticiones() + " peticiones");
        }
        */


        GasolinaState state = new GasolinaState();

        Problem problem =  new Problem(state,new GasolinaSuccessorFunction1(), new GasolinaGoalTest(),new GasolinaHeuristic1());
        Search search =  new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem,search);

        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());



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
