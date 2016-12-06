/**
 * 
 */
package br.unicamp.jtraci.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andre
 * 
 * For each Logic, structure is: SubID (type + string ) -  	Type (type + integer ) -  	SubParameter (type + compound) -  	Current phase index (type + integer ) -  	Number of phases (type + integer ) - 	Phase 1 	... 	Phase n .
 *
 * A list of this entity is created in the TrafficLight.getCompleteDefinition();
 *
 */
public class Logic extends Entity {
	
	private List<Phase> phases;
	
	private int currentPhase;
	
	private String subID;


	public Logic(){
		phases = new ArrayList<Phase>();
	}

	public Logic(String subID, int currentPhase, List<Phase> phases){
		setSubID(subID);
		setCurrentPhase(currentPhase);
		setPhases(phases);
	}

	/** */ /**
	 * @return the phases
	 */
	public List<Phase> getPhases() {
		return phases;
	}


	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public int getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(int currentPhase) {
		this.currentPhase = currentPhase;
	}

	public String getSubID() {
		return subID;
	}

	public void setSubID(String subID) {
		this.subID = subID;
	}
}
