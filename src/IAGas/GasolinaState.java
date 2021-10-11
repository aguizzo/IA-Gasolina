package IAGas;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;

import java.util.*;



public class GasolinaState {
    private static int maxViajes = 5;
    private static int maxKm = 640; 
    private static int maxSpeed = 80; //km/h
    private static int maxTime = 8; // horas diarias
    private static int valor_incial_deposito = 1000; 
    private static int costeKm = 2 // 2/km

    private ArrayList<ArrayList<Double>> d_truck_gas; //Distancia de cada centre (de cada camio) a cada gasolinera

    private ArrayList<ArrayList<Double>> d_gas_gas; //Distancia de cada gasolinera a cada gasolinera

    

    //Constructora por defecto
    public GasolinaState () {
        centros = new CentrosDistribucion(10, 1, 123);
        gas = new Gasolineras(100, 123);
    }

    //Constructora por copia
    public GasolinaState(GasolinaState gb){


    }


    //Para que el numero de centros y gasolineras sea aleatorio
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt(max-min)+min;
        return randomNum;
    }
}
