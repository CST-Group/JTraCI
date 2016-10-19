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
    private double speed;
    private double angle;
    private String edgeID;
    private String laneID;
    private int laneIndex;
    private String typeID;
    private String routeID;
    private int routeIndex;
    private String[] edges;
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
        setVehicleReadQuery(new ReadQuery<Vehicle>(SumoSimulation.getInstance().getConnection(), Vehicle.class));
    }


    public int getPostion(boolean getFromServer) {

        if(getFromServer){
            postion = (Integer) (getVehicleReadQuery().getPropertyValue(Constants.VAR_POSITION, getID(), Integer.class));
        }

        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public double getSpeed(boolean getFromServer) {

        if(getFromServer){
            speed = (Double)(getVehicleReadQuery().getPropertyValue(Constants.VAR_SPEED, getID(), Double.class));
        }

        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }



    public double getAngle(boolean getFromServer) {

        if(getFromServer){
            angle = (Double)(getVehicleReadQuery().getPropertyValue(Constants.VAR_ANGLE, getID(), Double.class));
        }

        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String getEdgeID() {
        return edgeID;
    }

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    public String getLaneID(boolean getFromServer) {

        if(getFromServer){
            laneID = (String)(getVehicleReadQuery().getPropertyValue(Constants.VAR_LANE_ID, getID(), String.class));
        }

        return laneID;
    }

    public void setLaneID(String laneID) {
        this.laneID = laneID;
    }

    public int getLaneIndex() {
        return laneIndex;
    }

    public void setLaneIndex(int laneIndex) {
        this.laneIndex = laneIndex;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public int getRouteIndex() {
        return routeIndex;
    }

    public void setRouteIndex(int routeIndex) {
        this.routeIndex = routeIndex;
    }

    public String[] getEdges() {
        return edges;
    }

    public void setEdges(String[] edges) {
        this.edges = edges;
    }

    public byte[] getColor() {
        return color;
    }

    public void setColor(byte[] color) {
        this.color = color;
    }

    public double getLanePostion() {
        return lanePostion;
    }

    public void setLanePostion(double lanePostion) {
        this.lanePostion = lanePostion;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getSignalState() {
        return signalState;
    }

    public void setSignalState(int signalState) {
        this.signalState = signalState;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getHc() {
        return hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public double getPmx() {
        return pmx;
    }

    public void setPmx(double pmx) {
        this.pmx = pmx;
    }

    public double getNox() {
        return nox;
    }

    public void setNox(double nox) {
        this.nox = nox;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public double getNoiseEmission() {
        return noiseEmission;
    }

    public void setNoiseEmission(double noiseEmission) {
        this.noiseEmission = noiseEmission;
    }

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public ReadQuery<Vehicle> getVehicleReadQuery() {
        return vehicleReadQuery;
    }

    public void setVehicleReadQuery(ReadQuery<Vehicle> vehicleReadQuery) {
        this.vehicleReadQuery = vehicleReadQuery;
    }
}
