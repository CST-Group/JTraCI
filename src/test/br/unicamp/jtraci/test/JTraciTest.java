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

import br.unicamp.jtraci.entities.Lane;
import br.unicamp.jtraci.entities.TrafficLight;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.simulation.SumoSimulation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JTraciTest {

    private int port = 4011;
    private SumoSimulation sumoSimulation;

    private String pathMap = "/Users/Du/Documents/Faculdades/Unicamp/Projeto/maps/twinT/twinT.sumocfg";
    //private String pathMap = "/home/andre/Unicamp/Pos Doc/Projetos/CMwCA/d4/sumoExamples/twinT/twinT.sumocfg";

    @Before
    public void setUp(){
        sumoSimulation = SumoSimulation.getInstance();
        sumoSimulation.runSumoGui(pathMap, port);

        for(int i=0; i<60; i++)
            sumoSimulation.nextStep();
    }

    @Test
    public void getAllVehicles(){

        List<Vehicle> vehicles = sumoSimulation.getAllVehicles();

        vehicles.get(0).getEdges();
        vehicles.get(0).getSpeed();
        vehicles.get(0).getAngle();
        vehicles.get(0).getLaneID();

        sumoSimulation.close();
    }

    @Test
    public void getAllLanes(){

        List<Lane> lanes = sumoSimulation.getAllLanes();

        lanes.get(0).getShape();
        lanes.get(0).getEdgeId();
        lanes.get(0).getLength();
        lanes.get(0).getVMax();
        lanes.get(0).getWidth();
        lanes.get(0).getLastStepVehicleNumber();
        lanes.get(0).getLastStepMeanSpeed();
        lanes.get(0).getLastStepOccupancy();
        lanes.get(0).getlastStepMeanVehicleLength();
        lanes.get(0).getWaitingTime();

    }

    @Test
    public void getAllTrafficLights(){
        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();

        trafficLights.get(0).getState();
        trafficLights.get(0).getCurrentPhaseDuration();
        trafficLights.get(0).getCurrentPhase();
        trafficLights.get(0).getCurrentProgram();
        trafficLights.get(0).getAssumedTimeOfNextSwitch();
    }

    @Test
    public void setTrafficLightState(){

        /** red light -> 'r' */
        /** red light w/o deceleration -> 'R' */
        /** yellow light -> 'y' */
        /** yellow light w/out deceleration -> 'Y' */
        /** green light -> 'g' */
        /** green light w/out deceleration -> 'G' */
        /** light off -> 'O' */

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();
        trafficLights.get(0).getState();

        trafficLights.get(0).setState("ggggggGggG");
        sumoSimulation.nextStep();

        trafficLights.get(0).setState("yyyyyyYyyY");
        sumoSimulation.nextStep();

        trafficLights.get(0).setState("rrrrrrrRrr");
        sumoSimulation.nextStep();

    }

    @Test
    public void getControlledLinksFromTrafficLight(){

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();

        trafficLights.get(0).getControlledLinks();

    }

    @Test
    public void getProgramDefinition(){

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();

        trafficLights.get(0).getCompleteDefinition();

    }

}
