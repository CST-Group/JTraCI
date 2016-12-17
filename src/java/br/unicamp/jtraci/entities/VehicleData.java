/**
 * 
 */
package br.unicamp.jtraci.entities;

/**
 * @author andre
 * 
 * Filled by InductionLoop.getLastStepVehicleData
 *
 */
public class VehicleData {
	
	private String vehicleID;
	
	private Double vehicleLength;
	
	private Double entryTimeSeconds;
	
	private Double leaveTimeSeconds;
	
	private String vehicleTypeID;

	/**
	 * @return the vehicleID
	 */
	public String getVehicleID() {
		return vehicleID;
	}

	/**
	 * @param vehicleID the vehicleID to set
	 */
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}

	/**
	 * @return the vehicleLength
	 */
	public Double getVehicleLength() {
		return vehicleLength;
	}

	/**
	 * @param vehicleLength the vehicleLength to set
	 */
	public void setVehicleLength(Double vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

	/**
	 * @return the entryTimeSeconds
	 */
	public Double getEntryTimeSeconds() {
		return entryTimeSeconds;
	}

	/**
	 * @param entryTimeSeconds the entryTimeSeconds to set
	 */
	public void setEntryTimeSeconds(Double entryTimeSeconds) {
		this.entryTimeSeconds = entryTimeSeconds;
	}

	/**
	 * @return the leaveTimeSeconds
	 */
	public Double getLeaveTimeSeconds() {
		return leaveTimeSeconds;
	}

	/**
	 * @param leaveTimeSeconds the leaveTimeSeconds to set
	 */
	public void setLeaveTimeSeconds(Double leaveTimeSeconds) {
		this.leaveTimeSeconds = leaveTimeSeconds;
	}

	/**
	 * @return the vehicleTypeID
	 */
	public String getVehicleTypeID() {
		return vehicleTypeID;
	}

	/**
	 * @param vehicleTypeID the vehicleTypeID to set
	 */
	public void setVehicleTypeID(String vehicleTypeID) {
		this.vehicleTypeID = vehicleTypeID;
	}
	
	

}
