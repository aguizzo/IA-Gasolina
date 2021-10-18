package IAGas;
import java.util.ArrayList;
import java.util.List;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

//HILL CLIMBING
public class GasolinaSuccessorFunction1 implements SuccessorFunction {
    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        GasolinaState board = (GasolinaState) state;

        GasolinaState new_state = new GasolinaState(board);
        // Per a cada cmió. fer un estat per a totes les gasolineres.
        for (int i = 0; i < GasolinaState.centros.size(); ++i) {
            for (int j = 0; j < GasolinaState.gas.size(); ++j) {
                if (new_state.addGasolinera(i,j)) retval.add(new Successor(new String(), new_state));
            }
        }
        return retval;
    }
}
