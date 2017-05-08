/*******************************************************************************
 * Copyright (c) 2016  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * A. L. O. Paraense, E. M. Froes, R. R. Gudwin
 ******************************************************************************/

package br.unicamp.jtraci.entities;

import java.awt.geom.Point2D;
import java.util.List;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

public class Vehicle extends Entity {
	
	/** Returns a list of ids of all vehicles currently running within the scenario (the given vehicle ID is ignored)  */
	private List<String> allVehicles;

    /** Returns the speed of the named vehicle within the last step [m/s]; error value: -1001 */
    private double speed;

    /** Returns the angle of the named vehicle within the last step [°]; error value: -1001 */
    private double angle;

    /** Returns the id of the lane the named vehicle was at within the last step; error value: "" */
    private String laneID;

    /** Returns the ids of the edges the vehicle's route is made of */
    private List<String> edges;
    
    /** Returns the position(two doubles) of the named vehicle (center of the front bumper) within the 
     * last step [m,m]; error value: [-2^30, -2^30]. */
    private Point2D position;

    private ReadQuery<Vehicle> vehicleReadQuery;

    public Vehicle(){
    	vehicleReadQuery = new ReadQuery<Vehicle>(SumoSimulation.getInstance().getConnection(), Vehicle.class);
    }

    public double getSpeed() {

        speed = (Double)(vehicleReadQuery.getAttributeValue(Constants.VAR_SPEED, ID, Double.class));

        return speed;
    }

    public double getAngle() {

        angle = (Double)(vehicleReadQuery.getAttributeValue(Constants.VAR_ANGLE, ID, Double.class));

        return angle;
    }

    public String getLaneID() {

        laneID = (String)(vehicleReadQuery.getAttributeValue(Constants.VAR_LANE_ID, ID, String.class));

        return laneID;
    }


    public List<String> getEdges() {

        edges = (List<String>)(vehicleReadQuery.getAttributeValue(Constants.VAR_EDGES, ID, List.class));

        return edges;
    }


	/**
	 * @return the position
	 */
	public Point2D getPosition() {

        position = (Point2D)vehicleReadQuery.getAttributeValue(Constants.VAR_POSITION, ID, Point2D.class);
		
		return position;
	}

	/**
	 * @return the allVehicles
	 */
	public List<String> getAllVehicles() {
		
		allVehicles = (List<String>)(vehicleReadQuery.getAttributeValue(Constants.ID_LIST, ID, List.class));
		
		return allVehicles;
	}
	
	
        
}
