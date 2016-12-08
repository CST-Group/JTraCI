package br.unicamp.jtraci.entities;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;

import java.util.List;

/**
 * Created by du on 06/12/16.
 */
public class Edge extends Entity {

    private ReadQuery<Edge> edgeReadQuery;

    private double currentTravelTime;
    private double co2Emissions;
    private double coEmissions;
    private double hcEmissions;
    private double pmxEmissions;
    private double noxEmissions;
    private double fuelConsumption;
    private double noiseEmission;
    private double electricityConsumption;
    private int lastStepVehicleNumber;
    private double lastStepMeanSpeed;
    private List<String> lastStepVehicleIds;
    private double lastStepOccupancy;
    private double lastStepMeanVehicleLength;
    private double waitingTime;
    private List<String> lastStepPersonIds;

    public Edge(){
        edgeReadQuery = new ReadQuery<Edge>(SumoSimulation.getInstance().getConnection(), Edge.class);
    }

    public Edge(String id){
        setID(id);
        edgeReadQuery = new ReadQuery<Edge>(SumoSimulation.getInstance().getConnection(), Edge.class);
    }

    public double getCurrentTravelTime() {

        currentTravelTime = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_CURRENT_TRAVEL_TIME, ID, Double.class));

        return currentTravelTime;
    }

    public double getCo2Emissions() {

        co2Emissions = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_CO2_EMISSIONS, ID, Double.class));

        return co2Emissions;
    }

    public double getCoEmissions() {

        coEmissions = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_CO_EMISSIONS, ID, Double.class));

        return coEmissions;
    }

    public double getHcEmissions() {

        hcEmissions = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_HC_EMISSIONS, ID, Double.class));

        return hcEmissions;
    }

    public double getPmxEmissions() {

        pmxEmissions = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_PMX_EMISSIONS, ID, Double.class));

        return pmxEmissions;
    }

    public double getNoxEmissions() {

        noxEmissions = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_NOX_EMISSIONS, ID, Double.class));

        return noxEmissions;
    }

    public double getFuelConsumption() {

        fuelConsumption = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_FUEL_CONSUMPTION, ID, Double.class));

        return fuelConsumption;
    }

    public double getNoiseEmission() {

        noiseEmission = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_NOISE_EMISSION, ID, Double.class));

        return noiseEmission;
    }

    public double getElectricityConsumption() {

        electricityConsumption = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_ELECTRICITY_CONSUMPTION, ID, Double.class));

        return electricityConsumption;
    }

    public int getLastStepVehicleNumber() {

        lastStepVehicleNumber = (Integer)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_VEHICLE_NUMBER, ID, Integer.class));

        return lastStepVehicleNumber;
    }

    public double getLastStepMeanSpeed() {

        lastStepMeanSpeed = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_SPEED, ID, Double.class));

        return lastStepMeanSpeed;
    }

    public List<String> getLastStepVehicleIds() {

        lastStepVehicleIds = (List<String>)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_VEHICLE_IDS, ID, List.class));

        return lastStepVehicleIds;
    }

    public double getLastStepOccupancy() {

        lastStepOccupancy = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_OCCUPANCY, ID, Double.class));

        return lastStepOccupancy;
    }

    public double getLastStepMeanVehicleLength() {

        lastStepMeanVehicleLength = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_LAST_STEP_MEAN_VEHICLE_LENGTH, ID, Double.class));

        return lastStepMeanVehicleLength;
    }

    public double getWaitingTime() {

        waitingTime = (Double)(edgeReadQuery.getAttributeValue(Constants.VAR_LANE_WAITING_TIME, ID, Double.class));

        return waitingTime;
    }

    public List<String> getLastStepPersonIds() {

        lastStepPersonIds = (List<String>)(edgeReadQuery.getAttributeValue(Constants.VAR_EDGE_LAST_STEP_PERSON_IDS, ID, List.class));

        return lastStepPersonIds;
    }
}
