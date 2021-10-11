import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;

public class Main {
    public static void main(String[] args) throws Exception {





        // Ejemplo


        CentrosDistribucion c = new CentrosDistribucion(10, 5, 321); //10 centros, 5 camiones


        for(int i = 0; i < c.size(); ++i) {
            System.out.println("Centro " + i + ": " + (c.get(i)).getCoordX() + " " + (c.get(i)).getCoordY());
        }


        Gasolineras g = new Gasolineras(50, 321); //50 gasolineras

        for(int i = 0; i < g.size(); i++) {
            System.out.println("Gasolinera " + i + " " + g.get(i).getCoordX() + " " + g.get(i).getCoordY() + " tiene " + g.get(i).getPeticiones() + " peticiones");
        }




    }
}
