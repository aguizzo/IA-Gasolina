import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
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

            System.out.println("If you want to use the simple initial solution system, type: 1.");
            System.out.println("If you want to use the comlex initial solution system, type: 2.");
            

        }

        else if (operation ==  2) {

        }

        else {
            return; 
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
}
