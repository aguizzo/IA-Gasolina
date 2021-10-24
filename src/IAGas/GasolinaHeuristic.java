package IAGas;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristic implements HeuristicFunction {
    public double getHeuristicValue(Object n){


        if (GasolinaState.tipus_heuristica == 1) {
            return ((GasolinaState) n).heuristic();
        }
        else {
            return ((GasolinaState) n).heuristic_2();
        }
        
    }
}
