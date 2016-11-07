/**
 * 
 */
package br.unicamp.jtraci.entities;

import java.util.List;

/**
 * @author andre
 * 
 * For each Logic, structure is: SubID (type + string ) -  	Type (type + integer ) -  	SubParameter (type + compound) -  	Current phase index (type + integer ) -  	Number of phases (type + integer ) - 	Phase 1 	... 	Phase n .
 *
 * TODO - Create a list of this entity in the TrafficLight.getCompleteDefinition();
 *
 */
public class Logic extends Entity {
	
	/** */
	private List<Phase> phases;

	/**
	 * @return the phases
	 */
	public List<Phase> getPhases() {
		return phases;
	}
	
	

}
