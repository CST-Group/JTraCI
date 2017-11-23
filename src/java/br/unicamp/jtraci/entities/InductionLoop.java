/**
 * 
 */
package br.unicamp.jtraci.entities;

import java.util.List;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

/**
 * @author andre
 *
 */
public class InductionLoop extends Entity {
	
	private ReadQuery<InductionLoop> inductionLoopReadQuery;
	
	/**
	 *  Returns the position of the induction loop at it's lane, counted from the lane's begin, in meters.
	 */
	private Double position;

	/**
	 * Returns the ID of the lane the induction loop is placed at.
	 */
	private String laneID; 
	
	/**
	 * Returns the number of vehicles that were on the named induction loop within the last simulation step [#]
	 */
	private Integer lastStepVehicleNumber; 
	
	/**
	 *  Returns the mean speed of vehicles that were on the named induction loop within the last simulation step [m/s]
	 */
	private Double lastStepMeanSpeed;
	    
	/**
	 *  Returns the list of ids of vehicles that were on the named induction loop in the last simulation step
	 */
	private List<String> lastStepVehicleIds;
	
	/**
	 * Returns the percentage of time the detector was occupied by a vehicle [%]
	 */
    private Double lastStepOccupancy; 
	
    /**
     *  The mean length of vehicles which were on the detector in the last step [m]
     */
    private Double lastStepMeanVehicleLength;
	
    /**
     *  The time since last detection [s]
     */
    private Double lastStepsTimeSinceLastDetection;
	
    /**
     *  A complex structure containing several information about vehicles which passed the detector;
     */
    private List<VehicleData> lastStepsVehicleData;     
    
    public InductionLoop(){
    	
    	inductionLoopReadQuery = new ReadQuery<InductionLoop>(SumoSimulation.getInstance().getConnection(), InductionLoop.class);
    	
    }

    public void updateEntityState(){
		getPosition();
		getLaneID();
		getLastStepVehicleNumber();
		getLastStepMeanSpeed();
		getLastStepVehicleIds();
		getLastStepOccupancy();
		getLastStepMeanVehicleLength();
		getLastStepsTimeSinceLastDetection();
	}

	/**
	 * @return the position
	 */
	public Double getPosition() {
		
		position = (Double)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_POSITION, ID, Double.class));
		
		return position;
	}

	/**
	 * @return the laneID
	 */
	public String getLaneID() {
		
		laneID = (String)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_ID, ID, String.class));
		
		return laneID;
	}

	/**
	 * @return the lastStepVehicleNumber
	 */
	public Integer getLastStepVehicleNumber() {
		
		lastStepVehicleNumber = (Integer) (inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_VEHICLE_NUMBER, ID, Integer.class));
		
		return lastStepVehicleNumber;
	}

	/**
	 * @return the lastStepMeanSpeed
	 */
	public Double getLastStepMeanSpeed() {
		
		lastStepMeanSpeed = (Double)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_SPEED, ID, Double.class));		
		
		return lastStepMeanSpeed;
	}

	/**
	 * @return the lastStepVehicleIds
	 */
	public List<String> getLastStepVehicleIds() {
		
		lastStepVehicleIds = (List<String>)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_VEHICLE_IDS, ID, List.class));
		
		return lastStepVehicleIds;
	}

	/**
	 * @return the lastStepOccupancy
	 */
	public Double getLastStepOccupancy() {
		
		lastStepOccupancy = (Double)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_OCCUPANCY, ID, Double.class));
		
		return lastStepOccupancy;
	}

	/**
	 * @return the lastStepMeanVehicleLength
	 */
	public Double getLastStepMeanVehicleLength() {
		
		lastStepMeanVehicleLength = (Double)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_VEHICLE_LENGTH, ID, Double.class));
		
		return lastStepMeanVehicleLength;
	}

	/**
	 * @return the lastStepsTimeSinceLastDetection
	 */
	public Double getLastStepsTimeSinceLastDetection() {
		
		lastStepsTimeSinceLastDetection = (Double)(inductionLoopReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_TIME_SINCE_LAST_DETECTION, ID, Double.class));
		
		return lastStepsTimeSinceLastDetection;
	}

	/**
	 * @return the lastStepsVehicleData
	 */
	public List<VehicleData> getLastStepsVehicleData() {
		
		//TODO - complex structure: http://www.sumo.dlr.de/wiki/TraCI/Induction_Loop_Value_Retrieval
		
		// Constants.VAR_LAST_STEPS_VEHICLE_DATA
		
		return lastStepsVehicleData;
	}
}
