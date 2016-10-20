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
	
	private Integer currentPhaseDuration;
	
	private Integer currentPhase;
	
	private String currentProgram;
	
	private Integer assumedTimeOfNextSwitch;
	
	public TrafficLight(){
		
		trafficLightReadQuery = new ReadQuery<TrafficLight>(SumoSimulation.getInstance().getConnection(), TrafficLight.class);
		
		
	}

	public String getState() {
		
		state = (String)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_STATE, getID(), String.class));
		
		return state;
		
	}

	public Integer getCurrentPhaseDuration() {
		
		currentPhaseDuration = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE_DURATION, getID(), Integer.class));
		
		return currentPhaseDuration;
		
	}

	public Integer getCurrentPhase() {
		
		currentPhase = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE, getID(), Integer.class));
		
		return currentPhase;
		
	}

	public String getCurrentProgram() {
		
		currentProgram = (String)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PROGRAM, getID(), String.class));
		
		return currentProgram;
		
	}

	public Integer getAssumedTimeOfNextSwitch() {
		
		assumedTimeOfNextSwitch = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_ASSUMED_TIME_OF_NEXT_SWITCH, getID(), Integer.class));
		
		return assumedTimeOfNextSwitch;
		
	}

}
