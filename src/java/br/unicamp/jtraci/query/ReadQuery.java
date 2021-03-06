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

package br.unicamp.jtraci.query;

import java.util.List;

import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.CommandResult;
import br.unicamp.jtraci.communication.SumoConnection;
import br.unicamp.jtraci.entities.Edge;
import br.unicamp.jtraci.entities.Entity;
import br.unicamp.jtraci.entities.InductionLoop;
import br.unicamp.jtraci.entities.Junction;
import br.unicamp.jtraci.entities.Lane;
import br.unicamp.jtraci.entities.DataSimulation;
import br.unicamp.jtraci.entities.TrafficLight;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.util.Constants;

public class ReadQuery<E extends Entity> {

	private  SumoConnection sumoConnection;

	private Class<E> classE;

	public  ReadQuery(SumoConnection sumoConnection, Class<E> classE){
		this.sumoConnection = sumoConnection;
		this.classE = classE;
	}

	public synchronized List<E> getAll(){

		int commandByte = chooseCommand();
		int varID = Constants.ID_LIST;
		String objectID = "";

		Command command = new Command(commandByte, varID, objectID);
		CommandResult commandResult = sumoConnection.sendCommand(command);

		List<E> entities = (List<E>) commandResult.convertToEntity(this.classE);

		return entities;

	}

	public synchronized Object getAttributeValue(int varID, String objectID, Class<?> attributeType){

		int commandByte = chooseCommand();

		Command command = new Command(commandByte, varID, objectID);
		CommandResult commandResult = sumoConnection.sendCommand(command);

		Object attribute = commandResult.convertToEntityAttribute(attributeType);

		return attribute;
	}


	public synchronized List<Object> getCompoundAttributeValue(int varID, String objectID, List<Object> attributeTypes){

		int commandByte = chooseCommand();

		Command command = new Command(commandByte, varID, objectID);
		CommandResult commandResult = sumoConnection.sendCommand(command);

		List<Object> objects = commandResult.convertCompoundAttribute(attributeTypes);

		return objects;
	}



	private synchronized int chooseCommand(){

		int command = 0;

		if(this.classE.isAssignableFrom(Vehicle.class)){
			
			command = Constants.CMD_GET_VEHICLE_VARIABLE;

		} else if(this.classE.isAssignableFrom(TrafficLight.class)){
			
			command = Constants.CMD_GET_TRAFFIC_LIGHT_VARIABLE;

		}  else if(this.classE.isAssignableFrom(Lane.class)){
			
			command = Constants.CMD_GET_LANE_VARIABLE;

		} else if(this.classE.isAssignableFrom(Junction.class)){

			command = Constants.CMD_GET_JUNCTION_VARIABLE;

		} else if(this.classE.isAssignableFrom(Edge.class)){

			command = Constants.CMD_GET_EDGE_VARIABLE;
			
		} else if(this.classE.isAssignableFrom(InductionLoop.class)){

			command = Constants.CMD_GET_INDUCTION_LOOP_VARIABLE;
			
		} else if(this.classE.isAssignableFrom(DataSimulation.class)){

			command = Constants.CMD_GET_SIM_VARIABLE;
			
		}

		return command;
	}


}
