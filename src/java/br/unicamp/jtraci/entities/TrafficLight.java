/**
 * 
 */
package br.unicamp.jtraci.entities;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

/**
 * @author andre
 *
 */
public class TrafficLight extends Entity {
	
	private ReadQuery<TrafficLight> trafficLightReadQuery;
	
	private String state;
	
	public TrafficLight(){
		
		trafficLightReadQuery = new ReadQuery<TrafficLight>(SumoSimulation.getInstance().getConnection(), TrafficLight.class);
		
		
	}

	public String getState() {
		
		state = (String)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_STATE, getID(), String.class));
		
		return state;
		
	}

}
