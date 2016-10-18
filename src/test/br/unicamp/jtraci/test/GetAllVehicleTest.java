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

package br.unicamp.jtraci.test;

import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.query.ObjectBuilder;
import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetAllVehicleTest {

    private int port = 4011;
    private SumoSimulation sumoSimulation;

    private String pathMap = "/home/efroes/Items/MAPS/twinT/twinT.sumocfg";

    @Before
    public void setUp(){
        sumoSimulation = SumoSimulation.getInstance();
        sumoSimulation.runSumoGui(pathMap, port);
    }

    @Test
    public void GetAllVehicles(){

        for(int i=0; i<10; i++)
            sumoSimulation.nextStep();

        ReadQuery<Vehicle> vehicleReadQuery = new ReadQuery<Vehicle>(sumoSimulation.getConnection(), Vehicle.class);

        List<Vehicle> vehicles = vehicleReadQuery.getAll();

        vehicles.get(0).getSpeed();


    }
}
