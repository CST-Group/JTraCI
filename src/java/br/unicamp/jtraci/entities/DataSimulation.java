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

package br.unicamp.jtraci.entities;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

/**
 * @author andre
 *
 */
public class DataSimulation extends Entity {
	
	/** The number of vehicles which are in the net plus the ones still waiting to start. This number may be smaller than the actual number of vehicles still to come because of delayed route file parsing. */
	private int minVehicleNumberExpectedToLeave;

	private int currentTime;
	
	private ReadQuery<DataSimulation> simulationReadQuery;
	
	public DataSimulation(){
		simulationReadQuery = new ReadQuery<DataSimulation>(SumoSimulation.getInstance().getConnection(), DataSimulation.class);
    }
	
	public int getMinExpectedNumber(){
		
		minVehicleNumberExpectedToLeave  = (Integer)(simulationReadQuery.getAttributeValue(Constants.VAR_MIN_EXPECTED_CARS_TO_LEAVE, ID, Integer.class));

        return minVehicleNumberExpectedToLeave;
		
	}


	public int getCurrentTime(){

		currentTime  = (Integer)(simulationReadQuery.getAttributeValue(Constants.CMD_GET_CURRENT_TIME, ID, Integer.class));

		return currentTime;

	}
	

}
