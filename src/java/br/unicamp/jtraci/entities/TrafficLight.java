/**
 *
 */
package br.unicamp.jtraci.entities;

import br.unicamp.jtraci.query.ReadQuery;
import br.unicamp.jtraci.query.WriteQuery;
import br.unicamp.jtraci.simulation.SumoSimulation;
import br.unicamp.jtraci.util.Constants;
import br.unicamp.jtraci.util.IgnoreParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author andre
 */
public class TrafficLight extends Entity {

    private ReadQuery<TrafficLight> trafficLightReadQuery;

    private WriteQuery<TrafficLight> trafficLightWriteQuery;

    /** red light -> 'r' */
    /** red light w/o deceleration -> 'R' */
    /** yellow light -> 'y' */
    /** yellow light w/out deceleration -> 'Y' */
    /** green light -> 'g' */
    /** green light w/out deceleration -> 'G' */
    /** light off -> 'O' */
    /**
     * Returns the named tl's state as a tuple of light definitions from rRgGyYoO, for red, green, yellow, off, where lower case letters mean that the stream has to decelerate
     */
    private String state;

    /**
     * Returns the default duration of the currently active phase [s]; note that this is not the remaining duration, but the complete; use "assumed time till next switch (0x2d)" for obtaining the remaining duration
     */
    private Integer currentPhaseDuration;

    /**
     * Returns the index of the current phase in the current program
     */
    private Integer currentPhase;

    /**
     * Returns the id of the current program
     */
    private String currentProgram;

    /**
     * Returns the assumed time (in milliseconds) at which the tls changes the phase. Please note that the time to switch is not relative to current simulation step (the result returned by the query will be absolute time, counting from simulation start); to obtain relative time, one needs to subtract current simulation time from the result returned by this query. Please also note that the time may vary in the case of actuated/adaptive traffic lights
     */
    private Integer assumedTimeOfNextSwitch;

    /**
     * Returns the complete traffic light program. Structure: Length (integer) -	Number of logics (type + integer ) - 	logic 1 ... 	logic n . For each Logic, structure is: SubID (type + string ) -  	Type (type + integer ) -  	SubParameter (type + compound) -  	Current phase index (type + integer ) -  	Number of phases (type + integer ) - 	Phase 1 	... 	Phase n . For each Phase, structure is: Duration (type + integer ) -  	Duration 1 (type + integer ) -  	Duration 2 (type + integer )  -  	Phase definition (type + stringList )
     */
    private List<Logic> completeDefinition;

    public TrafficLight() {

        trafficLightReadQuery = new ReadQuery<TrafficLight>(SumoSimulation.getInstance().getConnection(), TrafficLight.class);
        trafficLightWriteQuery = new WriteQuery<TrafficLight>(SumoSimulation.getInstance().getConnection(), TrafficLight.class);

    }

    public String getState() {

        state = (String) (trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_STATE, ID, String.class));

        return state;

    }

    public void setState(String state) {

        trafficLightWriteQuery.setAttributeValue(Constants.VAR_TL_STATE, ID, Constants.TYPE_STRING, state);

    }

    public Integer getCurrentPhaseDuration() {

        currentPhaseDuration = (Integer) (trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE_DURATION, ID, Integer.class));

        return currentPhaseDuration;

    }

    public Integer getCurrentPhase() {

        currentPhase = (Integer) (trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PHASE, ID, Integer.class));

        return currentPhase;

    }

    public String getCurrentProgram() {

        currentProgram = (String) (trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_CURRENT_PROGRAM, ID, String.class));

        return currentProgram;

    }

    public Integer getAssumedTimeOfNextSwitch() {

        assumedTimeOfNextSwitch = (Integer) (trafficLightReadQuery.getAttributeValue(Constants.VAR_TL_ASSUMED_TIME_OF_NEXT_SWITCH, ID, Integer.class));

        return assumedTimeOfNextSwitch;

    }

    /**
     * @return the completeDefinition
     */
    public List<Logic> getCompleteDefinition() {


        List<Object> attributeTypes = new ArrayList<Object>();
        attributeTypes.add(String.class);
        attributeTypes.add(new IgnoreParameter(10));
        attributeTypes.add(Integer.class);


        List<Object> internalAttributeTypes = new ArrayList<Object>();
        internalAttributeTypes.add(Integer.class);
        internalAttributeTypes.add(Integer.class);
        internalAttributeTypes.add(Integer.class);
        internalAttributeTypes.add(String.class);
        attributeTypes.add(internalAttributeTypes);

        List<Object> compoundObjects = trafficLightReadQuery.getCompoundAttributeValue(Constants.VAR_TL_COMPLETE_DEFINITION_RYG, ID, attributeTypes);

        List<Logic> logics = new ArrayList<Logic>();

        for (Object object : compoundObjects) {

            Logic logic = new Logic();

            for (Object attribute : (List<Object>) object) {
                if (attribute instanceof Collection) {

                    List<Object> phases = ((List<Object>) attribute);

                    Phase phase = new Phase((Integer) ((List<Object>)phases.get(0)).get(0),
                            (Integer) ((List<Object>)phases.get(0)).get(1),
                            (Integer) ((List<Object>)phases.get(0)).get(2),
                            (String) ((List<Object>)phases.get(0)).get(3));
                    logic.getPhases().add(phase);

                } else if (attribute instanceof String) {
                    logic.setSubID((String) attribute);
                } else if (attribute instanceof Integer) {
                    logic.setCurrentPhase((Integer) attribute);
                }
            }

            logics.add(logic);
        }

        completeDefinition = logics;

        return completeDefinition;
    }


    public List<ControlledLink> getControlledLinks() {

        List<Object> attributeTypes = new ArrayList<Object>();
        attributeTypes.add(List.class);

        List<Object> compoundObjects = trafficLightReadQuery.getCompoundAttributeValue(Constants.VAR_TL_CONTROLLED_LINKS, ID, attributeTypes);
        List<ControlledLink> controlledLinks = new ArrayList<ControlledLink>();

        for (Object item : compoundObjects) {
            controlledLinks.add(new ControlledLink(new Lane(((List<String>) item).get(0)),
                    new Lane(((List<String>) item).get(2)),
                    new Lane(((List<String>) item).get(1))));
        }

        return controlledLinks;
    }


}
