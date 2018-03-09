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
import java.awt.geom.Point2D;
import java.net.InetAddress;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import br.unicamp.jtraci.simulation.SumoSimulation;

public class JTraciTest {

    private int port = 4044;
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
        try {
            sumoSimulation.connect(InetAddress.getByName("127.0.0.1"), port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 170; i++)
            sumoSimulation.nextStep();


        setUpIsDone = true;

    }

    @Test
    public void getAllVehicles(){

        List<Vehicle> vehicles = sumoSimulation.getAllVehicles();

        for (Vehicle vehicle: vehicles) {
            vehicle.getPosition();
            vehicle.getEdges();
            vehicle.getSpeed();
            vehicle.getAngle();
            vehicle.getLaneID();
        }

    }

    @Test
    public void getAllLanes(){

        List<Lane> lanes = sumoSimulation.getAllLanes();

        Point2D lightPosition = lanes.get(0).getShape().getCurrentPoint();        
        
        List<String> vehicleIdList = lanes.get(0).getLastStepVehicleIds();
        
        for(String vehicleId: vehicleIdList){
			
			Vehicle vehicle = new Vehicle();
			vehicle.setID(vehicleId);
			
			try{
				
				Double V = vehicle.getSpeed();
				Point2D X = vehicle.getPosition();					
				
			}catch(Exception e){
				
				//if the vehicle does not exist at this point, just do not add any info
			}
																							
		}
        
        
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

        for (Edge edge: edges) {
            edge.getCo2Emissions();
            edge.getCoEmissions();
            edge.getCurrentTravelTime();
            edge.getElectricityConsumption();
            edge.getFuelConsumption();
            edge.getHcEmissions();
            edge.getLastStepMeanSpeed();
            edge.getLastStepMeanVehicleLength();
            edge.getLastStepOccupancy();
            edge.getLastStepPersonIds();
            edge.getLastStepVehicleIds();
            edge.getLastStepVehicleNumber();
            edge.getNoiseEmission();
            edge.getNoxEmissions();
            edge.getPmxEmissions();
            edge.getWaitingTime();
        }
    }

    @Test
    public void getAllJunctions(){
        List<Junction> junctions = sumoSimulation.getAllJunctions();

        for (Junction junction: junctions) {
            junction.getPosition();
            junction.getShape();
        }

    }
    
    @Test
    public void getAllInductionLoops(){
    	
        List<InductionLoop> inductionLoops = sumoSimulation.getAllInductionLoops();
        
        if(inductionLoops.size()>0){
        	
            inductionLoops.get(0).getLaneID();
            inductionLoops.get(0).getLastStepMeanSpeed();
            inductionLoops.get(0).getLastStepMeanVehicleLength();
            inductionLoops.get(0).getLastStepOccupancy() ;
            inductionLoops.get(0).getLastStepsTimeSinceLastDetection() ;
            inductionLoops.get(0).getLastStepVehicleIds() ;           
            inductionLoops.get(0).getLastStepVehicleNumber() ;
            inductionLoops.get(0).getPosition() ;  
            inductionLoops.get(0).getLastStepsVehicleData() ;
            inductionLoops.get(0).getLane();
        	  	
        }      
        
    }

    @Test
    public void getAllTrafficLights(){

        List<TrafficLight> trafficLights = sumoSimulation.getAllTrafficLights();

        for (TrafficLight trafficLight: trafficLights) {
            trafficLight.getState();
            trafficLight.getCurrentPhaseDuration();
            trafficLight.getCurrentPhase();
            trafficLight.getCurrentProgram();
            trafficLight.getAssumedTimeOfNextSwitch();
            trafficLight.getCompleteDefinition();
            trafficLight.getControlledLinks();
        }

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
    public void nextStepTest(){
        sumoSimulation.nextStep(1);
        sumoSimulation.nextStep(10);
        sumoSimulation.nextStep(100);
        sumoSimulation.nextStep(1000);
        sumoSimulation.nextStep(10000);
    }


    @Test
    public void getCurrentTime(){

        int initialCurrentTime = sumoSimulation.getCurrentTime("0");


        sumoSimulation.nextStep();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int finalCurrentTime = sumoSimulation.getCurrentTime("0");


    }


}
