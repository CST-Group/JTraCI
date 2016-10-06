package br.unicamp.jtraci.entities;

/**
 * Created by Du on 03/10/16.
 */
public abstract class Entity {

    private String ID;

    private String getID(){
        return this.ID;
    };

    public void setID(String ID) {
        this.ID = ID;
    }
}
