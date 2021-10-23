package IAGas;
import java.util.ArrayList;
import java.util.List;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

public class GasolinaSuccessorFunction1 implements SuccessorFunction {
    public ArrayList<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        GasolinaState board = (GasolinaState) state;


        // Per a cada camió. fer un estat per a totes les gasolineres.
        for (int i = 0; i < GasolinaState.centros.size(); ++i) {
            for (int j = 0; j < GasolinaState.gas.size(); ++j) {
                GasolinaState new_state = new GasolinaState(board);
                if (new_state.addGasolinera(i,j)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha afegit la gasolinera " + j + " al camió " + i + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
            }
        }


        for (int i = 0; i < GasolinaState.centros.size(); ++i) {
            for (int j = i + 1; j < GasolinaState.centros.size(); ++j) {
                GasolinaState new_state = new GasolinaState(board);
                if (new_state.swap(i,j)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha fet swap entre el ultim viatge del camió " + i + " i el ultim del camió " + j + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
            }
        }



        return retval;
    }
}
