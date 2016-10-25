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

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

public class Vehicle extends Entity {

    private int postion; //REVIEW THE TYPE.

    /** Returns the speed of the named vehicle within the last step [m/s]; error value: -1001 */
    private double speed;

    /** Returns the angle of the named vehicle within the last step [°]; error value: -1001 */
    private double angle;

    private String edgeID;

    /** Returns the id of the lane the named vehicle was at within the last step; error value: "" */
    private String laneID;

    private int laneIndex;

    private String typeID;

    private String routeID;

    private int routeIndex;
    private List<String> edges;
    private byte[] color;

    private double lanePostion;

    private double distance;

    private int signalState; //REVIEW THIS ATTRIBUTE.

    private double co2;

    private double co;

    private double hc;

    private double pmx;

    private double nox;

    private double fuelConsumption;

    private double noiseEmission;

    private double electricityConsumption;

    private ReadQuery<Vehicle> vehicleReadQuery;


    public Vehicle(){
    	vehicleReadQuery = new ReadQuery<Vehicle>(SumoSimulation.getInstance().getConnection(), Vehicle.class);
    }


    public int getPostion() {

        postion = (Integer) (vehicleReadQuery.getAttributeValue(Constants.VAR_POSITION, getID(), Integer.class));

        return postion;
    }

    public double getSpeed() {

        speed = (Double)(vehicleReadQuery.getAttributeValue(Constants.VAR_SPEED, getID(), Double.class));

        return speed;
    }

    public double getAngle() {

        angle = (Double)(vehicleReadQuery.getAttributeValue(Constants.VAR_ANGLE, getID(), Double.class));

        return angle;
    }

    public String getLaneID() {

        laneID = (String)(vehicleReadQuery.getAttributeValue(Constants.VAR_LANE_ID, getID(), String.class));

        return laneID;
    }
}
