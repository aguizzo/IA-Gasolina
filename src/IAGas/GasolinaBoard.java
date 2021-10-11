package IAGas;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class GasolinaBoard {
    private static int maxViajes = 5;
    private static int maxKm = 640;
    private static double precioDeposito = 1000;
    private static CentrosDistribucion centros;
    private static Gasolineras gas;
    private static int [][] distanciaCentroGasolinera;
    private static int [][] distanciasGasGas;


    //Constructora por defecto
    public GasolinaBoard () {
        centros = new CentrosDistribucion(2, 1, 123);
        gas = new Gasolineras(10, 123);
        distanciaCentroGasolinera = distanciaCentGas(centros, gas);
        distanciasGasGas = distanciasGs(gas);
    }

    //Constructora por copia
    public GasolinaBoard(GasolinaBoard gb) {
        //copiar parte no estatica

    }

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


    /* Métodos privados */

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


}
