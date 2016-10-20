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
	
	/** Returns the named tl's state as a tuple of light definitions from rRgGyYoO, for red, green, yellow, off, where lower case letters mean that the stream has to decelerate */
	private String state;
	
	/** Returns the default duration of the currently active phase [s]; note that this is not the remaining duration, but the complete; use "assumed time till next switch (0x2d)" for obtaining the remaining duration */
	private Integer currentPhaseDuration;
	
	/** Returns the index of the current phase in the current program  */
	private Integer currentPhase;
	
	/** Returns the id of the current program */
	private String currentProgram;
	
	/** Returns the assumed time (in milliseconds) at which the tls changes the phase. Please note that the time to switch is not relative to current simulation step (the result returned by the query will be absolute time, counting from simulation start); to obtain relative time, one needs to subtract current simulation time from the result returned by this query. Please also note that the time may vary in the case of actuated/adaptive traffic lights */
	private Integer assumedTimeOfNextSwitch;
	
	public TrafficLight(){
		
		trafficLightReadQuery = new ReadQuery<TrafficLight>(SumoSimulation.getInstance().getConnection(), TrafficLight.class);
		
		
	}

	public String getState() {
		
		state = (String)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_STATE, ID, String.class));
		
		return state;
		
	}

	public Integer getCurrentPhaseDuration() {
		
		currentPhaseDuration = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE_DURATION, ID, Integer.class));
		
		return currentPhaseDuration;
		
	}

	public Integer getCurrentPhase() {
		
		currentPhase = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE, ID, Integer.class));
		
		return currentPhase;
		
	}

	public String getCurrentProgram() {
		
		currentProgram = (String)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PROGRAM, ID, String.class));
		
		return currentProgram;
		
	}

	public Integer getAssumedTimeOfNextSwitch() {
		
		assumedTimeOfNextSwitch = (Integer)(trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_ASSUMED_TIME_OF_NEXT_SWITCH, ID, Integer.class));
		
		return assumedTimeOfNextSwitch;
		
	}

}
