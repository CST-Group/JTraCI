package br.unicamp.jtraci.simulation;

import br.unicamp.jtraci.SumoSimulation;

/**
 * Created by Du on 27/09/16.
 */
public class TestSimulation {
    /**
     * TODO - This main is only for fast testing purposes. Should be deleted later.
     *
     * @param args
     */
    public static void main(String[] args) {

        /*if(args.length!=2)
        {
            System.out.println("Usage: SumoProxy <P1> <P2>");
            System.out.println("<P1> = Server IP");
            System.out.println("<P2> = Server port");
            return;
        }

        String ipServidor = args[0];*/

        //int port = Integer.valueOf(args[1]);

        int port = 8081;

        SumoSimulation sumoSimulation = new SumoSimulation();

        sumoSimulation.runSumoGui("/Users/Du/Documents/Faculdades/Unicamp/Projeto/maps/twinT/twinT.sumocfg", port);

        while(true){

            try {

                sumoSimulation.nextStep();

                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

    }
}
