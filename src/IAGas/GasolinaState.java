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
    //private static int maxSpeed = 80; //km/h                  //no la necessitem
    //private static int maxTime = 8; // horas diarias          //no la necessitem
    private static int precioDeposito = 1000; 
    private static int costeKm = 2; // 2/km
    static CentrosDistribucion centros;                 //Tret private, sino Successor no pot accedir
    static Gasolineras gas;                     //Tret private, sino Successor no pot accedir

    private static int [][] distanciaCentroGasolinera; //Distancia de cada centre (de cada camio) a cada gasolinera
    private static int [][] distanciasGasGas; //Distancia de cada gasolinera a cada gasolinera
    
    public int beneficis = 0;
    int [][]estatCamions; //Km restants; viatges restants; diposit (0 = buit, 1 = mig, 2 = complet);

    List<List<Integer>> peticions;
    List<List<int[]>> state; // = //camions, recorregut (màxim 5 viatges en un dia)

    public static int opcio_s_annealing; 

    public static int numero_centros; 
    public static int numero_gasolineras; 



    //Constructora por defecto
    public GasolinaState (int number_trucks, int seed, int explicit_centers, int explicit_gas_stations, boolean random) {
        if (random) {
            numero_centros = randInt(10, 100); 
            numero_gasolineras = randInt(10, 100); 
        }

        else {
            numero_centros = explicit_centers; 
            numero_gasolineras = explicit_gas_stations; 

        }
        centros = new CentrosDistribucion(numero_centros, number_trucks, seed);
        gas = new Gasolineras(numero_gasolineras, seed);

        distanciaCentroGasolinera = distanciaCentGas(centros, gas);
        distanciasGasGas = distanciasGs(gas);

        estatCamions = calcularEstatCamions();
        peticions = calcularPeticions();
        state = calcularState();
    }

//Constructora por copia
    public GasolinaState(GasolinaState gb){
        this.beneficis = gb.beneficis;

        this.state = new ArrayList<List<int[]>>(centros.size());

        for (int i = 0; i < centros.size(); ++i) {
            List<int[]> list = new ArrayList<int[]>(gb.state.get(i).size());
            list.addAll(gb.state.get(i));
            state.add(list);
        }

        this.peticions = new ArrayList<List<Integer>>(gas.size());

        for (int i = 0; i < gas.size(); ++i) {
            List<Integer> list = new ArrayList<Integer>(gb.peticions.get(i).size());
            list.addAll(gb.peticions.get(i));
            peticions.add(list);
        }

        this.estatCamions = new int[centros.size()][3];

        for (int i = 0; i < centros.size(); ++i) {
            System.arraycopy(gb.estatCamions[i], 0, estatCamions[i], 0, 3);
        }

    }


    public void SimpleInitialSolution() {
        //centros = new CentrosDistribucion(10, 1, 1234);               //Aixo ja ho fa la constructora per defecte
        //gas = new Gasolineras(100, 1234);                             //Aixo ja ho fa la constructora per defecte
        //distanciaCentroGasolinera = distanciaCentGas(centros, gas);   //Aixo ja ho fa la constructora per defecte
        //distanciasGasGas = distanciasGs(gas);                         //Aixo ja ho fa la constructora per defecte

        //estatCamions = calcularEstatCamions();                        //Aixo ja ho fa la constructora per defecte
        //peticions = calcularPeticions();                              //Aixo ja ho fa la constructora per defecte
        //state = calcularState();                                      //Aixo ja ho fa la constructora per defecte
    }
    

    public void ComplexInitialSolution() {
        for (int i = 0; i < centros.size(); ++i) {
            for (int j = 0; j < gas.size() && estatCamions[i][1] > 0; j++) {
                addGasolinera(i,j);
            }
        }
    }

    public double heuristic() {
        return -beneficis;
    }
    
    
    private List<List<int[]>> calcularState() {
        List<List<int[]>> state = new ArrayList<List<int[]>>(centros.size());

        for (int i = 0; i < centros.size(); ++i) {
            List<int[]> empty_list = new ArrayList<int[]>(0);
            state.add(empty_list);
        }

        return state;
    }

    private int[][] calcularEstatCamions() {
        int [][]estatCamions = new int[centros.size()][3];
        for (int i = 0; i < centros.size(); ++i) {
            for (int j = 0; j < 3; ++j) {
                estatCamions[i][0] = maxKm;
                estatCamions[i][1] = maxViajes;
                estatCamions[i][2] = 2;
            }
        }
        return estatCamions;
    }


    private List<List<Integer>> calcularPeticions() {
        List<List<Integer>> peticions = new ArrayList<List<Integer>>(gas.size());
        for (int i = 0; i < gas.size(); ++i) {
            List<Integer> empty_list = new ArrayList<Integer>(0);
            peticions.add(empty_list);
        }

        for (int i = 0; i < gas.size(); ++i) {
            ArrayList<Integer> p = (gas.get(i)).getPeticiones();
            for (Integer integer : p) {
                peticions.get(i).add(integer);
            }
        }
        return peticions;
    }

    //Add Camió i a Gasolinera j
    public boolean addGasolinera(int i, int j){            //Tret private, sino Successor no pot accedir
        //Afegir trajecte de Centre de Distribució a Gasolinera

        if ((state.get(i).isEmpty() || state.get(i).get(state.get(i).size()-1)[1] == -1) && estatCamions[i][1] > 0) {
            if ((estatCamions[i][0]) - (distanciaCentroGasolinera[i][j] * 2) >= 0 && !peticions.get(j).isEmpty()) {
                if (!state.get(i).isEmpty()) {
                    if (state.get(i).get(state.get(i).size()-1)[1] != -1) beneficis += (distanciaCentroGasolinera[i][state.get(i).get(state.get(i).size()-1)[0]] * costeKm);
                }
                int b = precioDeposito;
                int numPets = peticions.get(j).size() -1;
                if (peticions.get(j).get(numPets) == 0) b *= 1.02;
                else b *= (1 - (Math.pow(2, peticions.get(j).get(numPets))) / 100);
                beneficis += b;

                beneficis -= (distanciaCentroGasolinera[i][j] * costeKm);
                estatCamions[i][0] -= (distanciaCentroGasolinera[i][j]);
                estatCamions[i][2] -= 1;
                int[]v = new int[2];
                v[0] = j;
                v[1] = peticions.get(j).get(numPets);
                state.get(i).add(v);
                peticions.get(j).remove(numPets);

                camio2Centro(i,j);
                return true;
            }
        }
        else { // Afegir trajecte de Gasolinera a Gasolinera
            if (estatCamions[i][0] - distanciaCentroGasolinera[i][j] - distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j] >= 0 && !peticions.get(j).isEmpty() && estatCamions[i][1] > 0) {
                if (!state.get(i).isEmpty()) {
                    if (state.get(i).get(state.get(i).size()-1)[1] != -1) beneficis += (distanciaCentroGasolinera[i][state.get(i).get(state.get(i).size()-1)[0]] * costeKm);
                }
                int b = precioDeposito;
                int numPets = peticions.get(j).size() -1;
                if (peticions.get(j).get(numPets) == 0) b *= 1.02;
                else b *= (1 - (Math.pow(2, peticions.get(j).get(numPets))) / 100);
                beneficis += b;
                beneficis -= (distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j] * costeKm);
                estatCamions[i][0] -= (distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j]);
                estatCamions[i][2] -= 1;
                int[]v = new int[2];
                v[0] = j;
                v[1] = peticions.get(j).get(numPets);
                state.get(i).add(v);
                peticions.get(j).remove(numPets);

                camio2Centro(i,j);
                return true;
            }
        }
        return false;
    }

    private void camio2Centro(int i, int j) {
        if (estatCamions[i][2] == 0) { // Ha de tornar al centre de distribució
            beneficis -= (distanciaCentroGasolinera[i][j] * costeKm);
            estatCamions[i][0] -= (distanciaCentroGasolinera[i][j]);
            estatCamions[i][1] -= 1;
            estatCamions[i][2] = 2;
            int[]v = new int[2];
            v[0] = i;
            v[1] = -1;
            state.get(i).add(v);
        }
        else {
            beneficis -= (distanciaCentroGasolinera[i][j] * costeKm);
        }
    }

    // Remove última gasolinera del camió i
    public boolean removeGasolinera(int i){
        if (!state.get(i).isEmpty()) {
            int l = state.get(i).get(state.get(i).size() -1)[0];
            int l1 = state.get(i).get(state.get(i).size() -1)[1]; // l1 = ultim punt visitat

            int km;
            int lastGas;
            int lastPet;
            if (state.get(i).size() == 1) {
                lastGas = l;
                km = distanciaCentroGasolinera[i][l] * 2;
                lastPet = l1;
            }
            else {
                int g = state.get(i).get(state.get(i).size() -2)[0]; // g = penultim punt visitat
                int g1 = state.get(i).get(state.get(i).size() -2)[1];

                if (l1 == -1) { //Últim punt és Centre de Distribució

                    if (state.get(i).size() == 2) {
                        km = distanciaCentroGasolinera[i][g];
                    }
                    else {
                        int c = state.get(i).get(state.get(i).size() -3)[0];
                        int c1 = state.get(i).get(state.get(i).size() -3)[1];

                        if (c1 == -1) {

                            km = distanciaCentroGasolinera[i][g] * 2;
                        }
                        else {
                            km = distanciaCentroGasolinera[i][g];
                            km += distanciasGasGas[g][c];
                            estatCamions[i][2] -= 1;
                            beneficis -= (distanciaCentroGasolinera[i][c] * costeKm);
                        }
                    }
                    state.get(i).remove(state.get(i).size() -2);
                    lastGas = g;
                    lastPet = g1;
                    estatCamions[i][1] += 1;
                }
                else {
                    lastGas = l;
                    lastPet = l1;
                    if (g1 == -1) km = distanciaCentroGasolinera[g][l]*2;
                    else {
                        km = distanciasGasGas[g][l];
                        beneficis += (distanciaCentroGasolinera[i][l] * costeKm);
                        beneficis -= (distanciaCentroGasolinera[i][g] * costeKm);
                    }
                }
            }
            peticions.get(lastGas).add(lastPet);
            int b = precioDeposito;
            if (lastPet == 0) b *= 1.02;
            else b *= (1 - (Math.pow(2, lastPet) / 100));
            beneficis -= b;
            beneficis += (km * costeKm);
            estatCamions[i][0] += km;
            state.get(i).remove(state.get(i).size() -1);
            return true;
        }
        return false;
    }
    
    //Last Gasolinera visitada del camió i
    private int lastGasolinera(int i) {
        if (!state.get(i).isEmpty()) {
            if (state.get(i).get(state.get(i).size()-1)[1] == -1) {
                return state.get(i).get(state.get(i).size()-2)[0];
            }
            else return state.get(i).get(state.get(i).size()-1)[0];
        }
        return -1;
    }

    //Fer un canvi del últim viatge de el camió i al camió j
    public boolean swap(int i, int j) {
        int l1 = lastGasolinera(i);
        int l2 = lastGasolinera(j);
        if (l1 != -1 || l2 != -1) {
            if (l1 == -1) {
                addGasolinera(i,l2);
                removeGasolinera(j);
            }
            else if (l2 == -1) {
                removeGasolinera(i);
                addGasolinera(j,l1);
            }
            else {
                removeGasolinera(i);
                removeGasolinera(j);
                addGasolinera(i,l2);
                addGasolinera(j,l1);
            }
            return true;
        }
        return false;
    }
    

    //random int entre min inclusive i max inclusive
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt(max-min + 1)+min;
        return randomNum;
    }



    /* No necessari
    public boolean isGoalState() { //Always returns false in local search
        return false; 
    }
    */


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
    
    public void imprimirState() {
        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.get(i).size(); j++) {
                int type = state.get(i).get(j)[1];
                int point = state.get(i).get(j)[0];
                if (type < 0) {
                    System.out.println("El camion " + i + " ha ido al centro " + point);
                }
                else System.out.println("El camion " + i + " ha ido a la gasolinera " + point);
            }
        }
        System.out.println("beneficis: " +    beneficis);
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
