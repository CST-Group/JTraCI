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

    /** command: get simulation variable */
    public static final int CMD_GET_SIM_VARIABLE = 0xab;

    /** command: set vehicle variable */
    public static final int CMD_SET_VEHICLE_VARIABLE = 0xc4;

    /** ids of vehicles starting to teleport (get: simulation) */
    public static final int VAR_TELEPORT_STARTING_VEHICLES_IDS = 0x76;

    /** ids of vehicles ending to teleport (get: simulation) */
    public static final int VAR_TELEPORT_ENDING_VEHICLES_IDS = 0x78;
}