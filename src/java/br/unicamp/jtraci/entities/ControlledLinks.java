/**
 * 
 */
package br.unicamp.jtraci.entities;

import java.util.List;

/**
 * @author andre
 * 
 * Structure of compound object controlled links
 * 
 * If you request the list of links, the compound object is structured as following. Attention, each part is fowarded by a byte which represents its data type, except "length".
 * 
 *  Length (number of signals) [integer] - 	links controlled by signal 0 [controlled links ]	... 	links controlled by signal n-1 [controlled links ] 
 *
 */
public class ControlledLinks extends Entity {
	
	/** Controlled links s*/
	private List<Link> links;

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		
		//TODO - implement this compound object according to structure in http://www.sumo.dlr.de/wiki/TraCI/Traffic_Lights_Value_Retrieval
		
		return links;
	}
	
	

}
