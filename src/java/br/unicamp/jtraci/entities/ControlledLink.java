package br.unicamp.jtraci.entities;

/**
 * Created by Du on 07/11/16.
 */
public class ControlledLink extends Entity{
    private final Lane incomingLane;
    private final Lane acrossLane;
    private final Lane outgoingLane;

    public ControlledLink(Lane incomingLane, Lane acrossLane, Lane outgoingLane) {

       this.incomingLane = incomingLane;
       this.acrossLane = acrossLane;
       this.outgoingLane = outgoingLane;
    }

    public Lane getIncomingLane() {
        return incomingLane;
    }

    public Lane getAcrossLane() {
        return acrossLane;
    }

    public Lane getOutgoingLane() {
        return outgoingLane;
    }
}
