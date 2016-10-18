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
import br.unicamp.jtraci.entities.Entity;

import java.nio.ByteBuffer;
import java.util.*;


public class ObjectBuilder {


    public ObjectBuilder(){

    }


    public List<Entity> convertToEntity(Class<?> entityType, CommandResult commandResult){

        try {
            if (Entity.class.isAssignableFrom(entityType)) {

                int window = commandResult.getResult()[0] + commandResult.getCommand().getCommandLength() + 1;

                List<Entity> entities = new ArrayList<Entity>();

                while(window < commandResult.getResult().length) {
                    int headLen = 4;

                    ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(commandResult.getResult(), window, window + headLen)); // big-endian by default
                    int infoLen = wrapped.getInt();

                    byte[] id = Arrays.copyOfRange(commandResult.getResult(), window + headLen, window + headLen + infoLen);

                    window += headLen+infoLen;

                    try {
                        Entity entity = (Entity) entityType.newInstance();
                        entity.setID(new String(id, "US-ASCII"));

                        entities.add(entity);

                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return entities;
            }

            else
                throw new Exception("Class isn't a Entity inheritance");
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
