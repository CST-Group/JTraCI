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

package br.unicamp.jtraci.simulation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.SumoConnection;
import br.unicamp.jtraci.entities.Lane;
import br.unicamp.jtraci.entities.TrafficLight;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.util.Constants;


public class SumoSimulation {

    private SumoConnection sumoConnection;
    /**
     * The current simulation step, in ms.
     */
    private int currentStep = 0;
    
    private static SumoSimulation sumoSimulation = null;

    public static SumoSimulation getInstance(){

        if(sumoSimulation ==  null)
            sumoSimulation = new SumoSimulation();

        return sumoSimulation;
    }

    private SumoSimulation(){

    	sumoConnection = new SumoConnection();
    }

    public void connect(InetAddress address, int port) throws Exception {

        try {
            if (sumoConnection != null) {
            	sumoConnection.connect(address, port);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }

    }


    public boolean runSumoGui(String configPath, int port){

        boolean successfulRun = false;


        String[] sSumoArgs = new String[]{
                "sumo-gui",
                "-c", configPath,
                "--remote-port", Integer.toString(port),
                "-S"
        };

        try {

        	Runtime.getRuntime().exec(sSumoArgs);

            connect(InetAddress.getByName("127.0.0.1"), port);

            successfulRun = true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successfulRun;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void nextStep() {
    	
        List<Command> commands = new ArrayList<Command>();

        Command command0 = new Command(Constants.CMD_SIMSTEP2);
        command0.addContent(((++currentStep) * 1000));
        commands.add(command0);

        sumoConnection.sendCommandList(commands);

    }


    public synchronized List<Vehicle> getAllVehicles(){

        ReadQuery<Vehicle> vehicleReadQuery = new ReadQuery<Vehicle>(sumoConnection, Vehicle.class);

        return vehicleReadQuery.getAll();

    }

	public synchronized SumoConnection getConnection() {
		
		return sumoConnection;
	}

	public synchronized List<TrafficLight> getAllTrafficLights() {
		
		ReadQuery<TrafficLight> trafficLightReadQuery = new ReadQuery<TrafficLight>(sumoConnection, TrafficLight.class);

        return trafficLightReadQuery.getAll();
	}

	public synchronized List<Lane> getAllLanes() {
		
		ReadQuery<Lane> laneReadQuery = new ReadQuery<Lane>(sumoConnection, Lane.class);

        return laneReadQuery.getAll();
	}

	public void close() {			
		
		List<Command> commands = new ArrayList<Command>();

        Command command0 = new Command(Constants.CMD_CLOSE);        
        commands.add(command0);

        sumoConnection.sendCommandList(commands);
		
        sumoConnection.close();
        
	}
    
}
