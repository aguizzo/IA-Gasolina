package IAGas;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;



public class GasolinaBoard {
    private static int maxViajes = 5;
    private static int maxKm = 640;
    private static CentrosDistribucion centros;
    private static Gasolineras gas;



    //Constructora por defecto
    public GasolinaBoard () {
        centros = new CentrosDistribucion(10, 1, 123);
        gas = new Gasolineras(100, 123);
    }

    //Constructora por copia
    public GasolinaBoard(GasolinaBoard gb){


    }
}
