package IAGas;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.*;
import static java.lang.Math.abs;



public class GasolinaState {
    private static int maxViajes = 5;
    private static int maxKm = 640; 
    private static int maxSpeed = 80; //km/h
    private static int maxTime = 8; // horas diarias
    private static int precioDeposito = 1000; 
    private static int costeKm = 2; // 2/km
    private static CentrosDistribucion centros;
    private static Gasolineras gas;

    private static int [][] distanciaCentroGasolinera; //Distancia de cada centre (de cada camio) a cada gasolinera
    private static int [][] distanciasGasGas; //Distancia de cada gasolinera a cada gasolinera



    //Constructora por defecto
    public GasolinaState (int number_trucks, int seed) {
        int numero_centros = randInt(10, 100); 
        int numero_gasolineras = randInt(10, 100); 

        centros = new CentrosDistribucion(numero_centros, number_trucks, seed);
        gas = new Gasolineras(numero_gasolineras, seed);

        distanciaCentroGasolinera = distanciaCentGas(centros, gas);
        distanciasGasGas = distanciasGs(gas);
    }

    //Constructora por copia
    public GasolinaState(GasolinaState gb){
        //copiar parte no estatica
    }


    public void SimpleInitialSolution() {

    }

    public void ComplexInitialSolution() {
        
        int [][]state = new int[centros.size()][5]; //camions, recorregut (màxim 5 viatges en un dia)
        int [][]estatCamions = new int[centros.size()][3]; //Km restants; viatges restants; diposit (0 = buit, 1 = mig, 2 = complet);
        int [][]peticions = new int[gas.size()][3];
        int beneficis = 0;

        for (int i = 0; i < gas.size(); ++i) {
            ArrayList<Integer> p = (gas.get(i)).getPeticiones();
            for (int j = 0; j < 3; ++j) {
                if (j < p.size()) peticions[i][j] = p.get(j);
                else peticions[i][j] = -1;
            }
        }

        for (int i = 0; i < centros.size(); ++i) {
            for (int j = 0; j < 3; ++j) {
                estatCamions[i][0] = maxKm;
                estatCamions[i][1] = maxViajes;
                estatCamions[i][2] = 2;
            }
        }

        for (int i = 0; i < centros.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                state[i][j] = -1000;
            }
        }


        for (int i = 0; i < centros.size(); ++i) {
            int l = 0;
            for (int j = 0; j < gas.size() && estatCamions[i][1] > 1 && estatCamions[i][2] > 0; j++) {
                if ((((estatCamions[i][0]) - (distanciaCentroGasolinera[i][j] * 2)) >= 0) && ((peticions[j][0] != -1) || (peticions[j][1] != -1) || (peticions[j][2] != -1))) {
                    for (int k = 0; k < 3 && estatCamions[i][1] > 1 && estatCamions[i][2] > 0; ++k) {
                        int b = valor_incial_deposito;
                        if (peticions[j][k] == 0) b *= 1.02;
                        else b *= (1 - (Math.pow(2, peticions[j][k])) / 100);
                        beneficis += b;
                        estatCamions[i][2] -= 1;
                        peticions[j][k] = -1;
                    }
                    beneficis -= (distanciaCentroGasolinera[i][j] * 2 * costeKm);
                    estatCamions[i][0] -= (distanciaCentroGasolinera[i][j] * 2);
                    estatCamions[i][1] -= 2;
                    state[i][l] = j;
                    ++l;
                    state[i][l] = -i; //Negatiu significa centre de distribució.
                    ++l;
                    if (estatCamions[i][2] == 0) estatCamions[i][2] = 2;
                }
            }
        }

        imprimirState(state,beneficis);
    }



    //Para que el numero de centros y gasolineras sea aleatorio
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt(max-min)+min;
        return randomNum;
    }


    public boolean isGoalState() { //Always returns false in local search
        return false; 
    }


    //About imprimir
    public void imprimirDistanciasCentroGas() {
        for(int i = 0; i < distanciaCentroGasolinera.length; i++) {
            for(int j = 0; j < distanciaCentroGasolinera[0].length; j++) {
                System.out.println("Distancia del Centro " + i + " a Gasolinera "
                        + j +": " + distanciaCentroGasolinera[i][j] );
            }
        }
    }

    public void imprimirDistanciasGasGas() {
        for(int i = 0; i < distanciasGasGas.length; i++) {
            for(int j = 0; j < distanciasGasGas[0].length; j++) {
                System.out.println("Distancia de la Gasolinera " + i + " a Gasolinera "
                        + j +": " + distanciasGasGas[i][j] );
            }
        }
    }

    public void imprimirPeticiones() {
        for(int i = 0; i < gas.size(); i++) {
            ArrayList<Integer> p = (gas.get(i)).getPeticiones();
            System.out.println("Gasolinera " + i + " tiene " + p.size() + " peticiones: ");
            for(int j = 0; j < p.size(); j++) {
                int dias = p.get(j);
                double b = precioDeposito;
                if (dias == 0)
                    b *= 1.02;
                else
                    b *= (1 - (Math.pow(2, dias))/100);
                System.out.println("Petición " + j + " de la Gasolinera " + i +
                        " tiene " + dias + " días y un beneficio de: " + b);
            }
        }
    }
    
    public void imprimirState(int[][] state, int beneficis) {
        for (int i = 0; i < centros.size(); i++) {
            for (int j = 0; j < 5 && state[i][j] != -1000; j++) {
                if (state[i][j] < 0) {
                    System.out.println("El camion " + i + " ha ido al centro " + -state[i][j]);
                }
                else System.out.println("El camion " + i + " ha ido a la gasolinera " + state[i][j]);
            }
        }
        System.out.println(beneficis);
    }    
    
    //END About imprimir



    //About distancias
    private int calcularDistancia(Distribucion d, Gasolinera g) {
        int distancia = abs(d.getCoordX() - g.getCoordX());
        distancia += abs(d.getCoordY() - d.getCoordY());
        return distancia;
    }

    private int[][] distanciaCentGas(CentrosDistribucion cd, Gasolineras gs) {
        int filas = cd.size();
        int columnas = gs.size();
        int m[][] = new int[filas][columnas];
        for(int i = 0; i < filas; i++) {
            for(int j = 0; j < columnas; j++) {
                m[i][j] = calcularDistancia(cd.get(i), gs.get(j));
            }
        }
        return  m;
    }

    private int calcularDistanciaGas(Gasolinera g1, Gasolinera g2) {
        int distancia = abs(g1.getCoordX() - g2.getCoordX());
        distancia += abs(g1.getCoordY() - g2.getCoordY());
        return distancia;
    }

    private int[][] distanciasGs(Gasolineras gs) {
        int n = gs.size();
        int m[][] = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                m[i][j] = calcularDistanciaGas(gs.get(i), gs.get(j));
            }
        }
        return  m;
    }
    //END About distancias


}
