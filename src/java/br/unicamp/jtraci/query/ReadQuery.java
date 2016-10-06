package br.unicamp.jtraci.query;

import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.CommandResult;
import br.unicamp.jtraci.communication.SumoConnection;
import br.unicamp.jtraci.entities.Entity;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Du on 03/10/16.
 */
public class ReadQuery<E extends Entity> {

    private SumoConnection sumoConnection;
    private Class<E> classE;

    public ReadQuery(SumoConnection sumoConnection, Class<E> classE){
        this.setSumoConnection(sumoConnection);
        this.classE = classE;

    }

    public SumoConnection getSumoConnection() {
        return sumoConnection;
    }


    /*public E get(String varID){
        //E object = new E();

    }*/

    public List<E> getAll(){

        int commandByte = this.chooseCommand();
        int varID = Constants.ID_LIST;
        String objectID = "";

        Command command = new Command(commandByte,varID,objectID);
        CommandResult commandResult = getSumoConnection().sendCommand(command);

        return new ArrayList<E>();

    }


    public int chooseCommand(){

        int command = 0;

        if(this.classE.isAssignableFrom(Vehicle.class)){
            command = Constants.CMD_GET_VEHICLE_VARIABLE;
        }

        return command;
    }





    public void setSumoConnection(SumoConnection sumoConnection) {
        this.sumoConnection = sumoConnection;
    }
}
