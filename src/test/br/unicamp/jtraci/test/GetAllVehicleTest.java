package br.unicamp.jtraci.test;

import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.query.ObjectBuilder;
import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Du on 04/10/16.
 */

public class GetAllVehicleTest {

    private int port = 4011;
    private SumoSimulation sumoSimulation;

    @Before
    public void setUp(){
        sumoSimulation = SumoSimulation.getInstance();
        sumoSimulation.runSumoGui("/Users/Du/Documents/Faculdades/Unicamp/Projeto/maps/twinT/twinT.sumocfg", port);
    }

    @Test
    public void GetAllVehicles(){

        for(int i=0; i<100; i++)
            sumoSimulation.nextStep();

        ReadQuery<Vehicle> vehicleReadQuery = new ReadQuery<>(sumoSimulation.getConnection(), Vehicle.class);

        List<Vehicle> vehicles = vehicleReadQuery.getAll();


        ObjectBuilder o = new ObjectBuilder();
        o.convertToEntity(Vehicle.class);
    }
}
