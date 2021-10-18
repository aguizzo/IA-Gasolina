package IAGas;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristic1 implements HeuristicFunction {
    public double getHeuristicValue(Object n){

        return ((GasolinaState) n).heuristic();
    }
}
