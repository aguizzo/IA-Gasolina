package IAGas;
import java.util.ArrayList;
import java.util.List;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.Random;


//SIMULATED ALEANNING
public class GasolinaSuccessorFunction2 implements SuccessorFunction {
    public List getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        GasolinaState board = (GasolinaState) state;
        Random myRandom=new Random();

        GasolinaState new_state = new GasolinaState(board);

        int camion = myRandom.nextInt(GasolinaState.centros.size()); 

        //añadir_gasolinera_aleatoria_compatible(camion, new_state, retval);  Laltre opcio, en que llavors de buscar un camio i gas aleatories, intentem primer buscar gasolineres aleatories compatibles amb el camio que hem elegit de forma aleatoria



        return retval;

    }


    private void buscar_gasolinera_camio_aleatoris_compatibles
    //...


    /*
    private void añadir_gasolinera_aleatoria_compatible (int camion, GasolinaState new_state, ArrayList<Successor> retval) {
        Random myRandom=new Random();
        int gasolinera = myRandom.nextInt(GasolinaState.gas.size());

        if (new_state.addGasolinera(camion,gasolinera)) {
            StringBuffer S = new StringBuffer();
            S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camion + ". Beneficis: " + new_state.beneficis);
            retval.add(new Successor(S.toString(), new_state));
        }

        else {
            añadir_gasolinera_aleatoria_compatible(camion, new_state, retval);
        }
    }
    */



}
