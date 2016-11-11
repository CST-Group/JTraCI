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
	private String definition;
	
	private Integer duration1;
	private Integer duration2;
	private Integer duration3;


	public Phase(Integer duration1, Integer duration2, Integer duration3, String definition){
		setDuration1(duration1);
		setDuration2(duration2);
		setDuration3(duration3);
		setDefinition(definition);
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration1() {
		return duration1;
	}

	/**  The pahse definition differs for 0x25 and 0x2b. The first (0x25) contains the old, deprecated definition which uses three strings that describe the tl's state, the first green y/n, the second for brake y/n, the third for yellow y/n. The second (0x2b) contains a single string representing the lights. Note, that the first (0x25) is also reversed. */ /**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	public Integer getDuration2() {
		return duration2;
	}

	public void setDuration2(Integer duration2) {
		this.duration2 = duration2;
	}

	public Integer getDuration3() {
		return duration3;
	}

	public void setDuration3(Integer duration3) {
		this.duration3 = duration3;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setDuration1(Integer duration1) {
		this.duration1 = duration1;
	}
}
