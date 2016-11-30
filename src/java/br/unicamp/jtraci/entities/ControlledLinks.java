/**
 * 
 */
package br.unicamp.jtraci.entities;

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
	
	private Link[][] links;


	public ControlledLinks(Link[][] links){
		setLinks(links);
	}

	/** Controlled links s*/ /**
	 * @return the links
	 */
	public Link[][] getLinks() {
		
		return links;
	}


	public void setLinks(Link[][] links) {
		this.links = links;
	}
}
