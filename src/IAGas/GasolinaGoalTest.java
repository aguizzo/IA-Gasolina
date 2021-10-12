package IAGas;

import aima.search.framework.GoalTest;

public class GasolinaGoalTest implements GoalTest {

    public boolean isGoalState(Object aState) {
        GasolinaState state = (GasolinaState) aState; 
        return (state.isGoalState()); 
    }
    
}
