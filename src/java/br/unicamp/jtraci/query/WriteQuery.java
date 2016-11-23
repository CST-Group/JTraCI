package br.unicamp.jtraci.query;

import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.CommandResult;
import br.unicamp.jtraci.communication.SumoConnection;
import br.unicamp.jtraci.entities.Entity;
import br.unicamp.jtraci.entities.Lane;
import br.unicamp.jtraci.entities.TrafficLight;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.util.Constants;

/**
 * Created by Du on 27/10/16.
 */
public class WriteQuery<E extends Entity> {

    private SumoConnection sumoConnection;

    private Class<E> classE;

    public WriteQuery(SumoConnection sumoConnection, Class<E> classE){
        this.sumoConnection = sumoConnection;
        this.classE = classE;

    }

    public synchronized void setAttributeValue(int varID, String objectID, byte typeAttribute, Object value){

        int commandByte = chooseCommand();

        Command command = new Command(commandByte, varID, objectID);
        command.addContent(typeAttribute);
        command.addContent(value);

        CommandResult commandResult = sumoConnection.sendCommand(command);


    }


    private synchronized int chooseCommand(){

        int command = 0;

        if(this.classE.isAssignableFrom(Vehicle.class)){

            command = Constants.CMD_SET_VEHICLE_VARIABLE;


        } else if(this.classE.isAssignableFrom(TrafficLight.class)){

            command = Constants.CMD_SET_TRAFFIC_LIGHT_VARIABLE;


        }  else if(this.classE.isAssignableFrom(Lane.class)){

            command = Constants.CMD_SET_LANE_VARIABLE;

        }

        return command;
    }

}
