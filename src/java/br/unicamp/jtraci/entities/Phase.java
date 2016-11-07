/**
 * 
 */
package br.unicamp.jtraci.entities;

/**
 * @author andre
 * 
 * For each Phase, structure is: Duration (type + integer ) -  	Duration 1 (type + integer ) -  	Duration 2 (type + integer )  -  	Phase definition (type + stringList )
 * 
 *  * TODO - Create a list of this entity in the TrafficLight.getCompleteDefinition();
 *
 */
public class Phase extends Entity {
	
	/** red light -> 'r' */                       
	/** red light w/o deceleration -> 'R' */   
	/** yellow light -> 'y' */                  
	/** yellow light w/out deceleration -> 'Y' */ 
	/** green light -> 'g' */                        
	/** green light w/out deceleration -> 'G' */     
	/** light off -> 'O' */   
	/**  The pahse definition differs for 0x25 and 0x2b. The first (0x25) contains the old, deprecated definition which uses three strings that describe the tl's state, the first green y/n, the second for brake y/n, the third for yellow y/n. The second (0x2b) contains a single string representing the lights. Note, that the first (0x25) is also reversed. */
	private String definition;
	
	private Integer duration;

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}
}
