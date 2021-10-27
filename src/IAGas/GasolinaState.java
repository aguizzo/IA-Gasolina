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
    public int beneficisTomorrow = 0;

    int [][]estatCamions; //Km restants; viatges restants; diposit (0 = buit, 1 = mig, 2 = complet);

    List<List<Integer>> peticions;
    List<List<int[]>> state; // = //camions, recorregut (màxim 5 viatges en un dia)

    public static int opcio_s_annealing; 

    public static int numero_centros; 
    public static int numero_gasolineras; 

    public static int tipus_heuristica; 



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
        this.beneficisTomorrow = gb.beneficisTomorrow;

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
        for (int i = 0; i < centros.size(); ++i) {                                  //Aixo es que abans feia la complex
            for (int j = 0; j < gas.size() && estatCamions[i][1] > 0; j++) {
                addGasolinera(i,j);
            }
        }
    }
    

    public void ComplexInitialSolution() { //Intentem fer totes aquelles peticions que tinguin 3 o mes dies
        for (int i = 0; i < gas.size(); i++) {
            int contador_peticions_a_resoldre = 0; 
            for (int j = 0; j < peticions.get(i).size(); j++) {  
                if (peticions.get(i).get(j) >= 3) {
                    contador_peticions_a_resoldre++;
                }
            }
            if (contador_peticions_a_resoldre > 0) {
                Boolean trobat_almenys_un = false;
                //apuntem la distancia del camio mes petita
                int index_camio = -1; 
                int distancia_camio = -1; 
                for (int k = 0; k < centros.size(); k++) {
                    if (nomes_comprova_add(k, i)) {
                        if (!trobat_almenys_un) {
                            trobat_almenys_un = true; 
                            index_camio = k; 

                            //calculem distancia fins gasolinera del camio actual
                            if (state.get(k).isEmpty()) {
                                distancia_camio = distanciaCentroGasolinera[k][i]; 
                            }
                            else {
                                if (state.get(k).get(state.get(k).size()-1)[1] != -1) {
                                    distancia_camio = distanciasGasGas[state.get(k).get(state.get(k).size()-1)[0]][i]; 
                                }
                                else {
                                    distancia_camio = distanciaCentroGasolinera[k][i]; 
                                }
                            }
                            
                        }
                        else {

                            //calculem distancia fins gasolinera del camio actual
                            int distancia_camio_actual; 

                            if (state.get(k).isEmpty()) {
                                distancia_camio_actual = distanciaCentroGasolinera[k][i]; 
                            }
                            else {
                                if (state.get(k).get(state.get(k).size()-1)[1] != -1) {
                                    distancia_camio_actual = distanciasGasGas[state.get(k).get(state.get(k).size()-1)[0]][i]; 
                                }
                                else {
                                    distancia_camio_actual = distanciaCentroGasolinera[k][i]; 
                                }
                            }


                            if (distancia_camio_actual < distancia_camio) {
                                index_camio = k;
                                distancia_camio = distancia_camio_actual; 
                            }
                        }
                    }
                }

                if (trobat_almenys_un) {
                    for (int y = 0; y < contador_peticions_a_resoldre; y++) {
                        addGasolinera(index_camio, i); 
                    }
                }

            }


        }

    }
            

    public double heuristic() {
        return -beneficis;
    }


    //heuristica que tambe te en compte amortitzar els costos pels propers dies, anant primer a les peticions que ja porten molts dies
    public double heuristic_2() {
        double bT = beneficisTomorrow;
        return -beneficis-bT;
        
    }

    public double addsDisponibles() {
        double disponibles = 0;
        for (int i = 0; i < centros.size(); ++i) {
            for (int j = 0; j < gas.size(); ++j) {
                if (nomes_comprova_add(i, j)) ++disponibles;
            }
        }

        return disponibles;
    }

    public double swapsDisponibles() {
        double disponibles = 0;
        for (int i = 0; i < centros.size(); ++i) {
            for (int j = i + 1; j < centros.size(); ++j) {
                if (nomes_comprova_swap(i, j)) ++disponibles;
            }
        }
        return disponibles;
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
                int numPeticio = peticio_mes_dies(j); 
                if (peticions.get(j).get(numPeticio) == 0) b *= 1.02;
                else b *= (1 - (Math.pow(2, peticions.get(j).get(numPeticio))) / 100);
                beneficis += b;

                beneficis -= (distanciaCentroGasolinera[i][j] * costeKm);
                estatCamions[i][0] -= (distanciaCentroGasolinera[i][j]);
                estatCamions[i][2] -= 1;
                int[]v = new int[2];
                v[0] = j;
                v[1] = peticions.get(j).get(numPeticio);
                state.get(i).add(v);
                peticions.get(j).remove(numPeticio);

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
                int numPeticio = peticio_mes_dies(j); 
                if (peticions.get(j).get(numPeticio) == 0) b *= 1.02;
                else b *= (1 - (Math.pow(2, peticions.get(j).get(numPeticio))) / 100);
                beneficis += b;
                beneficis -= (distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j] * costeKm);
                estatCamions[i][0] -= (distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j]);
                estatCamions[i][2] -= 1;
                int[]v = new int[2];
                v[0] = j;
                v[1] = peticions.get(j).get(numPeticio);
                state.get(i).add(v);
                peticions.get(j).remove(numPeticio);

                camio2Centro(i,j);
                return true;
            }
        }
        return false;
    }

    private boolean nomes_comprova_add(int i, int j) {     //Mateix que addGasolinera, pero nomes comprova, no fa add
        if ((state.get(i).isEmpty() || state.get(i).get(state.get(i).size()-1)[1] == -1) && estatCamions[i][1] > 0) {
            if ((estatCamions[i][0]) - (distanciaCentroGasolinera[i][j] * 2) >= 0 && !peticions.get(j).isEmpty()) {
                return true; 
            }
        }
        else if (estatCamions[i][0] - distanciaCentroGasolinera[i][j] - distanciasGasGas[state.get(i).get(state.get(i).size()-1)[0]][j] >= 0 && !peticions.get(j).isEmpty() && estatCamions[i][1] > 0) {
            return true; 
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
                removeGasolinera(j);
                if (!addGasolinera(i,l2)) {
                    //tornem on estavem abans del swap ja que no es pot fer swap
                    addGasolinera(j, l2); 
                    return false; 
                }
            }
            else if (l2 == -1) {
                removeGasolinera(i);
                if (!addGasolinera(j,l1)) {
                    //tornem on estavem abans del swap ja qe no es pot fer swap
                    addGasolinera(i, l1); 
                    return false; 
                }
            }
            else {
                removeGasolinera(i);
                removeGasolinera(j);
                if (!addGasolinera(i,l2)) {
                    //tornem on estavem abans del swap ja que no es pot fer swap
                    addGasolinera(i, l1); 
                    addGasolinera(j, l2); 
                    return false; 
                }
                if (!addGasolinera(j,l1)) {
                    //tornem on estavem abans del swap ja que no es pot fer swap
                    addGasolinera(i, l1); 
                    addGasolinera(j, l2); 
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean nomes_comprova_swap(int i, int j) {    //Mateix que swap, pero nomes fa la comprovacio
        int l1 = lastGasolinera(i);
        int l2 = lastGasolinera(j);
        if (l1 != -1 || l2 != -1) {
            if (l1 == -1) {
                removeGasolinera(j);
                if (!nomes_comprova_add(i, l2)) {
                    //tornem on estavem abans del swap
                    addGasolinera(j, l2); 
                    return false; 
                }
                else {
                    //tornem on estavem abans del swap, nomes estem comprovant
                    removeGasolinera(i); 
                    addGasolinera(j, l2); 
                    return true; 
                }
            }
            else if (l2 == -1) {
                removeGasolinera(i);
                if (!nomes_comprova_add(j, l1)) { 
                    //tornem on estavem abans del swap
                    addGasolinera(i, l1); 
                    return false; 
                }
                else {
                    //tornem on estavem abans del swap, nomes estem comprovant
                    removeGasolinera(j); 
                    addGasolinera(i, l1); 
                    return true;
                }
            }
            else {
                removeGasolinera(i);
                removeGasolinera(j);
                if (!nomes_comprova_add(i, l2)) { 
                    //tornem on estavem abans del swap
                    addGasolinera(i, l1); 
                    addGasolinera(j, l2); 
                    return false; 
                }
                else {
                    if (!nomes_comprova_add(j, l1)) { 
                        //tornem on estavem abans del swap
                        addGasolinera(i, l1); 
                        addGasolinera(j, l2); 
                        return false;

                    }
                    else {
                        //tornem on estavem abans del swap, nomes estem comprovant
                        removeGasolinera(i); 
                        removeGasolinera(j); 
                        addGasolinera(i, l1); 
                        addGasolinera(j, l2); 
                        return true; 
                    }

                }
            }
        }
        return false;
    }

    public boolean addTomorrow(int i, int j) {
        if (!peticions.get(j).isEmpty()) {
            int numPeticio = peticio_mes_dies(j);
            int b = precioDeposito;
            if (peticions.get(j).get(numPeticio)+1 == 0) b *= 1.02;
            else b *= (1 - (Math.pow(2, peticions.get(j).get(numPeticio)+1)) / 100);
            beneficisTomorrow += b;
            //System.out.println(beneficisTomorrow);
            peticions.get(j).remove(numPeticio);
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


    private int peticio_mes_dies (int numero_gasolinera) {  //donada una gasolinera, retorna el numero de peticio amb mes dies
        int numero_peticio_mes_gran = -1; 
        int dies_peticio_mes_gran = -1;
        for (int i = 0; i < peticions.get(numero_gasolinera).size(); i++) {
            if (peticions.get(numero_gasolinera).get(i) > dies_peticio_mes_gran) {
                numero_peticio_mes_gran = i; 
                dies_peticio_mes_gran = peticions.get(numero_gasolinera).get(i);
            }
        }

        return numero_peticio_mes_gran; 
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
        int numP = 0;
        for (int i = 0; i < gas.size(); ++i) {
            numP += peticions.get(i).size();
        }
        System.out.println("beneficis: " + beneficis);
        System.out.println("beneficis demà: " + beneficisTomorrow);
        System.out.println("total: " + (beneficis+beneficisTomorrow));
        System.out.println("numP: " + numP);
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
