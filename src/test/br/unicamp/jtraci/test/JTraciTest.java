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

import br.unicamp.jtraci.entities.*;
import br.unicamp.jtraci.simulation.SumoSimulation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JTraciTest {

    private int port = 4011;
    private static SumoSimulation sumoSimulation;

    private String pathMap = "/Users/du/Repository/maps/twinT/twinT.sumocfg";
    //private String pathMap = "/home/andre/Unicamp/Pos Doc/Projetos/CMwCA/d4/sumoExamples/twinT/twinT.sumocfg";

    private static boolean setUpIsDone = false;


    @Before
    public void setUp(){

        if (setUpIsDone) {
            return;
        }

        sumoSimulation = SumoSimulation.getInstance();
        sumoSimulation.runSumoGui(pathMap, port);

        for (int i = 0; i < 172; i++)
            sumoSimulation.nextStep();


        setUpIsDone = true;

    }

    @Test
    public void getAllVehicles(){

        List<Vehicle> vehicles = sumoSimulation.getAllVehicles();

        vehicles.get(0).getPosition();
        vehicles.get(0).getEdges();
        vehicles.get(0).getSpeed();
        vehicles.get(0).getAngle();
        vehicles.get(0).getLaneID();

    }

    @Test
    public void getAllLanes(){

        List<Lane> lanes = sumoSimulation.getAllLanes();

        lanes.get(0).getLastStepVehicleIds();
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
    public void getAllEdges(){

        List<Edge> edges = sumoSimulation.getAllEdges();
        edges.get(0).getCo2Emissions();
        edges.get(0).getCoEmissions();
        edges.get(0).getCurrentTravelTime();
        edges.get(0).getElectricityConsumption();
        edges.get(0).getFuelConsumption();
        edges.get(0).getHcEmissions();
        edges.get(0).getLastStepMeanSpeed();
        edges.get(0).getLastStepMeanVehicleLength();
        edges.get(0).getLastStepOccupancy();
        edges.get(0).getLastStepPersonIds();
        edges.get(0).getLastStepVehicleIds();
        edges.get(0).getLastStepVehicleNumber();
        edges.get(0).getNoiseEmission();
        edges.get(0).getNoxEmissions();
        edges.get(0).getPmxEmissions();
        edges.get(0).getWaitingTime();

    }

    @Test
    public void getAllJunctions(){
        List<Junction> junctions = sumoSimulation.getAllJunctions();

        junctions.get(0).getIncommingEdges();
        junctions.get(0).getOutgoingEdges();
        junctions.get(0).getPosition();
        junctions.get(0).getShape();
    }

    @Test
    public void getAllTrafficLights(){

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();

        trafficLights.get(0).getState();
        trafficLights.get(0).getCurrentPhaseDuration();
        trafficLights.get(0).getCurrentPhase();
        trafficLights.get(0).getCurrentProgram();
        trafficLights.get(0).getAssumedTimeOfNextSwitch();
        trafficLights.get(0).getCompleteDefinition();
        trafficLights.get(0).getControlledLinks();

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


}
