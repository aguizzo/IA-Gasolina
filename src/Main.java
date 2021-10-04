import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;

public class Main {
    public static void main(String[] args) throws Exception {

        CentrosDistribucion c = new CentrosDistribucion(10, 1, 321);


        for(int i = 0; i < c.size(); ++i) {
            System.out.println("Centro " + i + ": " + (c.get(i)).getCoordX() + " " + (c.get(i)).getCoordY());
        }


    }
}
