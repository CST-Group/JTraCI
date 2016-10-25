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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unicamp.jtraci.entities.TrafficLight;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.simulation.SumoSimulation;

public class GetAllVehicleTest {

    private int port = 4011;
    private SumoSimulation sumoSimulation;

    private String pathMap = "/home/efroes/Items/MAPS/twinT/twinT.sumocfg";
    //private String pathMap = "/home/andre/Unicamp/Pos Doc/Projetos/CMwCA/d4/sumoExamples/twinT/twinT.sumocfg";

    @Before
    public void setUp(){
        sumoSimulation = SumoSimulation.getInstance();
        sumoSimulation.runSumoGui(pathMap, port);
    }

    @Test
    public void GetAllVehicles(){

        for(int i=0; i<60; i++)
            sumoSimulation.nextStep();
             

        List<Vehicle> vehicles = sumoSimulation.getAllVehicles();


        vehicles.get(0).getEdges();
        vehicles.get(0).getSpeed();
        vehicles.get(0).getAngle();
        vehicles.get(0).getLaneID();

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();
                
        trafficLights.get(0).getState();
        trafficLights.get(0).getCurrentPhaseDuration();
        trafficLights.get(0).getCurrentPhase();
        trafficLights.get(0).getCurrentProgram();
        trafficLights.get(0).getAssumedTimeOfNextSwitch();

    }
}
