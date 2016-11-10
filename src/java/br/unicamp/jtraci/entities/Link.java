/**
 * 
 */
package br.unicamp.jtraci.entities;

/**
 * @author andre
 * 
 * Structure:
 * 
 * number of controlled links [integer] 	link 0  [stringlist]	... 	link n-1 [stringlist] 
 * 
 * Empty strings indicate missing lanes. 
 *
 */
public class Link extends Entity {
	
	/** the lane which is incoming into the junction */
	private Lane incomingLane;
	
	/** the lane which is outgoing from the junction */
	private Lane outgoingLane;
	
	/**  the lane across the junction (junction-internal) */
	private Lane acrossLane;

	/**
	 * @return the incomingLane
	 */
	public Lane getIncomingLane() {
		
		//TODO - implement this compound object according to structure in http://www.sumo.dlr.de/wiki/TraCI/Traffic_Lights_Value_Retrieval
		
		return incomingLane;
	}
	
	

}
