/*******************************************************************************
 * Copyright (c) 2016  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * A. L. O. Paraense, E. M. Froes, R. R. Gudwin
 ******************************************************************************/

package br.unicamp.jtraci.test;

import br.unicamp.jtraci.simulation.SumoSimulation;

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

        SumoSimulation sumoSimulation = SumoSimulation.getInstance();

        sumoSimulation.runSumoGui("/home/efroes/Items/MAPS/twinT/twinT.sumocfg", port);

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
