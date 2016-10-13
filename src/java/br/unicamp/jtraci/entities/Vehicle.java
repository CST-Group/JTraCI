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