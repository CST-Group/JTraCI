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

package br.unicamp.jtraci.query;

import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.CommandResult;
import br.unicamp.jtraci.communication.SumoConnection;
import br.unicamp.jtraci.entities.Entity;
import br.unicamp.jtraci.entities.Vehicle;
import br.unicamp.jtraci.util.Constants;

import java.util.ArrayList;
import java.util.List;

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

    public List<E> getAll(){

        int commandByte = this.chooseCommand();
        int varID = Constants.ID_LIST;
        String objectID = "";

        Command command = new Command(commandByte, varID, objectID);
        CommandResult commandResult = getSumoConnection().sendCommand(command);

        List<E> entities = (List<E>) commandResult.convertToEntity(this.classE);

        return entities;

    }


    public Object getAttributeValue(int varID, String objectID, Class<?> typeAttribute){

        int commandByte = this.chooseCommand();

        Command command = new Command(commandByte, varID, objectID);
        CommandResult commandResult = getSumoConnection().sendCommand(command);

        Object attribute = commandResult.convertToEntityAttribute(typeAttribute);

        return attribute;
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
