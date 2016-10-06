package br.unicamp.jtraci.entities;

/**
 * Created by Du on 05/10/16.
 */
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

}
