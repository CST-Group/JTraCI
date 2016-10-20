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
public class Lane extends Entity {
	
	private ReadQuery<Lane> laneReadQuery;
	
	/** Returns the id of the edge this lane belongs to */
	private String edgeId;
	
	/** Returns the length of the named lane [m] */
	private Double length;
	
	/** Returns the maximum speed allowed on this lane [m/s]  */
	private Double vMax;
	
	/** Returns the width of the named lane [m]  */
	private Double width;
	
	public Lane(){
		
		laneReadQuery = new ReadQuery<Lane>(SumoSimulation.getInstance().getConnection(), Lane.class);
		
	}

	public String getEdgeId() {
		
		edgeId = (String)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_EDGE_ID, getID(), String.class));
		
		return edgeId;
		
	}

	public Double getLength() {
		
		length = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_LENGTH, getID(), Double.class));
		
		return length;
		
	}

	public Double getVMax() {
		
		vMax = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_VMAX, getID(), Double.class));
		
		return vMax;
		
	}

	public Double getWidth() {
		
		width = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_WIDTH, getID(), Double.class));
		
		return width;
		
	}
	
	

}
