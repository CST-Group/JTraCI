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
	
	/** The number of vehicles on this lane within the last time step.  */
	private Integer lastStepVehicleNumber;
	
	/** Returns the mean speed of vehicles that were on this lane within the last simulation step [m/s] */
	private Double lastStepMeanSpeed;
	
	/** Returns the total lengths of vehicles on this lane during the last simulation step divided by the length of this lane */
	private Double lastStepOccupancy;
	
	/** The mean length of vehicles which were on this lane in the last step [m] */
	private Double lastStepMeanVehicleLength;
	
	/** Returns the waiting time for all vehicles on the lane [s]  */
	private Double waitingTime;
	
	public Lane(){
		
		laneReadQuery = new ReadQuery<Lane>(SumoSimulation.getInstance().getConnection(), Lane.class);
		
	}

	public String getEdgeId() {
		
		edgeId = (String)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_EDGE_ID, ID, String.class));
		
		return edgeId;
		
	}

	public Double getLength() {
		
		length = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_LENGTH, ID, Double.class));
		
		return length;
		
	}

	public Double getVMax() {
		
		vMax = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_VMAX, ID, Double.class));
		
		return vMax;
		
	}

	public Double getWidth() {
		
		width = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_WIDTH, ID, Double.class));
		
		return width;
		
	}

	public Integer getLastStepVehicleNumber() {
		
		lastStepVehicleNumber = (Integer) (laneReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_VEHICLE_NUMBER, ID, Integer.class));

        return lastStepVehicleNumber;
		
	}

	public Double getLastStepMeanSpeed() {
		
		lastStepMeanSpeed = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_SPEED, ID, Double.class));
		
		return lastStepMeanSpeed;
		
	}

	public Double getLastStepOccupancy() {
		
		lastStepOccupancy = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_OCCUPANCY, ID, Double.class));
		
		return lastStepOccupancy;
		
	}

	public Double getlastStepMeanVehicleLength() {
		
		lastStepMeanVehicleLength = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_VEHICLE_LENGTH, ID, Double.class));
		
		return lastStepMeanVehicleLength;
		
	}

	public Double getWaitingTime() {
		
		waitingTime = (Double)(laneReadQuery.getAttributeValue(Constants.VAR_LANE_WAITING_TIME, ID, Double.class));
		
		return waitingTime;
		
	}
	
	

}
