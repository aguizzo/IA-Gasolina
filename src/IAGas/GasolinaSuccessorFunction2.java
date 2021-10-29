package IAGas;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;


//SIMULATED ALEANNING
public class GasolinaSuccessorFunction2 implements SuccessorFunction {
    public ArrayList<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        GasolinaState board = (GasolinaState) state;
        GasolinaState new_state = new GasolinaState(board);
        int opcio = GasolinaState.opcio_s_annealing; 

        int numero_camions = GasolinaState.centros.size(); 
        int numero_gasolineras = GasolinaState.gas.size(); 

        //Es tria de forma random si es fa add o swap. LLavors es tria de forma random un camio i gas (o camio i camio)
        //Si no es pot realitzar loperacio desitjada per aquests dos, llavors es tria un altre camio i gasolinera randoms per fer aquesta operacio. 
        /* Opcio 1 no la considerem, ja que dona bucle infinit
        if (opcio == 1) {
            int tipus_metode = randInt(0, 100); // 0 = add    1 = swap
            double addsDisp = board.addsDisponibles();
            double swapsDisp = board.swapsDisponibles();
            double totalDisp = (addsDisp + swapsDisp);
            double percentatgeAdds;
            if (totalDisp != 0) percentatgeAdds = (addsDisp/(addsDisp + swapsDisp)) * 100;
            else percentatgeAdds = 50;
            int camio_1;
        

            if (tipus_metode < percentatgeAdds) {
                int gasolinera; 
                do {
                    camio_1 =  randInt(0, numero_camions-1); 
                    gasolinera = randInt(0, numero_gasolineras-1); 
                } while (!(new_state.addGasolinera(camio_1,gasolinera))); 

                StringBuffer S = new StringBuffer();
                S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                retval.add(new Successor(S.toString(), new_state));


            }

            else if (tipus_metode == 1) {
                int camio_2; 
                do {
                    camio_1 =  randInt(0, numero_camions-1); 
                    do {
                        camio_2 = randInt(0, numero_camions-1); 
                    } while (camio_1 == camio_2); 
                } while (!(new_state.swap(camio_1,camio_2))); 

                StringBuffer S = new StringBuffer();
                S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                retval.add(new Successor(S.toString(), new_state));
            }



        }

        */


        //Igual que la opcio 1, pero si no es pot fer la operacio pel camio i gas triats (o camio i camio), no es fa res
        if (opcio == 2) {
            int tipus_metode = randInt(0, 100); // 0 = add    1 = swap
            double addsDisp = board.addsDisponibles();
            double swapsDisp = board.swapsDisponibles();
            double totalDisp = (addsDisp + swapsDisp);
            double percentatgeAdds;
            if (totalDisp != 0) percentatgeAdds = (addsDisp/(addsDisp + swapsDisp)) * 100;
            else percentatgeAdds = 50;
            int camio_1 = randInt(0, numero_camions-1); 

            if (tipus_metode <= percentatgeAdds) {
                int gasolinera = randInt(0, numero_gasolineras-1); 
                if (new_state.addGasolinera(camio_1,gasolinera)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
            }

            else {
                int camio_2; 
                do {
                    camio_2 = randInt(0, numero_camions-1); 
                } while (camio_1 == camio_2); 
                if (new_state.swap(camio_1,camio_2)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
            }
        }

       
        //Igual que la opció 1, pero si no es pot fer la operacio pel camio i gas triasts (o camio i camio), s'intenta fer l'altre operacio. Si cap de les dos funciona, no es fa res
        else if (opcio == 3) {
            int tipus_metode = randInt(0, 100); // 0 = add    1 = swap
            double addsDisp = board.addsDisponibles();
            double swapsDisp = board.swapsDisponibles();
            double totalDisp = (addsDisp + swapsDisp);
            double percentatgeAdds;
            if (totalDisp != 0) percentatgeAdds = (addsDisp/(addsDisp + swapsDisp)) * 100;
            else percentatgeAdds = 50;
            int camio_1 = randInt(0, numero_camions-1);

            if (tipus_metode < percentatgeAdds) {
                int gasolinera = randInt(0, numero_gasolineras-1); 
                if (new_state.addGasolinera(camio_1,gasolinera)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
                else { //intentar laltre operacio
                    int camio_2; 
                    do {
                        camio_2 = randInt(0, numero_camions-1); 
                    } while (camio_1 == camio_2); 
                    if (new_state.swap(camio_1,camio_2)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                    }
                }

            }

            else if (tipus_metode == 1) {
                int camio_2; 
                do {
                    camio_2 = randInt(0, numero_camions-1); 
                } while (camio_1 == camio_2); 
                if (new_state.swap(camio_1,camio_2)) {
                    StringBuffer S = new StringBuffer();
                    S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                    retval.add(new Successor(S.toString(), new_state));
                }
                else { //intentar laltre operacio
                    int gasolinera = randInt(0, numero_gasolineras-1); 
                    if (new_state.addGasolinera(camio_1,gasolinera)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                    }
                }
            }


        }


        //Igual que la opció 1, pero si la operacio no es pot fer, abans de triar un altre camio i gas (o camio i camio), s'intenta fer laltre operacio. Aixo tambe es fa pels successius camions i gasolineres (o camions i camions)
        else if (opcio == 4) {
            int tipus_metode = randInt(0, 100); // 0 = add    1 = swap

            int camio_1; 
            int camio_2; 
            int gasolinera;

            double addsDisp = board.addsDisponibles();
            double swapsDisp = board.swapsDisponibles();
            double totalDisp = (addsDisp + swapsDisp);
            double percentatgeAdds;
            if (totalDisp != 0) percentatgeAdds = (addsDisp/(addsDisp + swapsDisp)) * 100;
            else percentatgeAdds = 50;
            


            if (tipus_metode <= percentatgeAdds) {
                do {
                    camio_1 =  randInt(0, numero_camions-1); 
                    gasolinera = randInt(0, numero_gasolineras-1); 
                    do {
                        camio_2 = randInt(0, numero_camions-1); 
                    } while (camio_1 == camio_2); 
                    if (new_state.addGasolinera(camio_1,gasolinera)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                        break;
                    }
                    else if (new_state.swap(camio_1,camio_2)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                        break; 
                    }
                } while (true); 


            }

            else {
                do {
                    camio_1 =  randInt(0, numero_camions-1); 
                    gasolinera = randInt(0, numero_gasolineras-1);
                    do {
                        camio_2 = randInt(0, numero_camions-1); 
                    } while (camio_1 == camio_2); 
                    if (new_state.swap(camio_1,camio_2)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha fet swap entre el ultim viatge del camió " + camio_1 + " i el ultim del camió " + camio_2 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                        break; 
                    }
                    else if (new_state.addGasolinera(camio_1,gasolinera)) {
                        StringBuffer S = new StringBuffer();
                        S.append("s'ha afegit la gasolinera " + gasolinera + " al camió " + camio_1 + ". Beneficis: " + new_state.beneficis);
                        retval.add(new Successor(S.toString(), new_state));
                        break; 
                    }
                } while (true); 
            }



        }


        return retval; 
    }

    //random int entre min inclusive i max inclusive
    public int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt(max-min + 1)+min;
        return randomNum;
    }



}
