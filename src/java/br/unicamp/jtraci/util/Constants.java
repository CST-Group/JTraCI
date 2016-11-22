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

package br.unicamp.jtraci.util;

public class Constants {


    /** list of instances' ids (get: all) */
    public static final int ID_LIST = 0x00;

    /** command: simulation step */
    public static final int CMD_SIMSTEP2 = 0x02;

    /** command: stop node */
    public static final int CMD_STOP = 0x12;

    /** command: get vehicle variable */
    public static final int CMD_GET_VEHICLE_VARIABLE = 0xa4;

    public static final int CMD_GET_LANE_VARIABLE = 0xa3;

    /** command: set lane variable */
    public static final int CMD_SET_LANE_VARIABLE = 0xc3;

    /** command: get simulation variable */
    public static final int CMD_GET_SIM_VARIABLE = 0xab;

    /** command: set vehicle variable */
    public static final int CMD_SET_VEHICLE_VARIABLE = 0xc4;

    /** ids of vehicles starting to teleport (get: simulation) */
    public static final int VAR_TELEPORT_STARTING_VEHICLES_IDS = 0x76;

    /** ids of vehicles ending to teleport (get: simulation) */
    public static final int VAR_TELEPORT_ENDING_VEHICLES_IDS = 0x78;

    /** speed (get: vehicle) */
    public static final int VAR_SPEED = 0x40;

    /** position (2D) (get: vehicle, poi, set: poi) */
    public static final int VAR_POSITION = 0x42;

    /** angle (get: vehicle) */
    public static final int VAR_ANGLE = 0x43;

    /** lane id (get: vehicles) */
    public static final int VAR_LANE_ID = 0x51;

    /** edges (get: routes) */
    public static final int VAR_EDGES = 0x54;

	public static final int CMD_GET_TRAFFIC_LIGHT_VARIABLE = 0xa2;

    /** command: set traffic lights variable */
    public static final int CMD_SET_TRAFFIC_LIGHT_VARIABLE = 0xc2;

	public static final int VAR_TL_STATE = 0x20;

	public static final int VAR_TL_CURRENT_PHASE_DURATION = 0x24;

    public static final int VAR_TL_CONTROLLED_LINKS = 0x27;

    public static final int VAR_TL_COMPLETE_DEFINITION_RYG = 0x2b;

	public static final int VAR_TL_CURRENT_PHASE = 0x28;

	public static final int VAR_TL_CURRENT_PROGRAM = 0x29;

	public static final int VAR_TL_ASSUMED_TIME_OF_NEXT_SWITCH = 0x2d;

	public static final int VAR_LANE_EDGE_ID = 0x31;

	public static final int VAR_LANE_LENGTH = 0x44;

	public static final int VAR_LANE_VMAX = 0x41;

	public static final int VAR_LANE_WIDTH = 0x4d;

	public static final int VAR_LANE_LAST_STEP_VEHICLE_NUMBER = 0x10;

	public static final int VAR_LANE_LAST_STEP_MEAN_SPEED = 0x11;

	public static final int VAR_LANE_LAST_STEP_OCCUPANCY = 0x13;

	public static final int VAR_LANE_LAST_STEP_MEAN_VEHICLE_LENGTH = 0x15;

	public static final int VAR_LANE_WAITING_TIME = 0x7a;

    public static final int VAR_LANE_SHAPE = 0x4e;
	
	/** command: close sumo */
	public static final int CMD_CLOSE = 0x7F;


    // ****************************************
    // DATA TYPES
    // ****************************************
    /** Boundary Box (4 doubles) */
    public static final byte TYPE_BOUNDINGBOX = 0x05;
    /** Polygon (2*n doubles) */
    public static final byte TYPE_POLYGON = 0x06;
    /** unsigned byte */
    public static final byte TYPE_UBYTE = 0x07;
    /** signed byte */
    public static final byte TYPE_BYTE = 0x08;
    /** 32 bit signed integer */
    public static final byte TYPE_INTEGER = 0x09;
    /** float */
    public static final byte TYPE_FLOAT = 0x0A;
    /** double */
    public static final byte TYPE_DOUBLE = 0x0B;
    /** 8 bit ASCII string */
    public static final byte TYPE_STRING = 0x0C;
    /** list of traffic light phases */
    public static final byte TYPE_TLPHASELIST = 0x0D;
    /** list of strings */
    public static final byte TYPE_STRINGLIST = 0x0E;
    /** compound object */
    public static final byte TYPE_COMPOUND = 0x0F;
    /** color (four ubytes) */
    public static final byte TYPE_COLOR = 0x11;
}
