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

package br.unicamp.jtraci.communication;


import br.unicamp.jtraci.entities.Entity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandResult {

    private String id;
    private Command command;
    private byte[] result;

    public Command getCommand() {
        return command;
    }

    public CommandResult(Command command, byte[] result){

        this.setId(CommandResult.class.getSimpleName() + "_" + System.currentTimeMillis());
        this.setCommand(command);
        this.setResult(result);
    }

    public List<Entity> convertToEntity(Class<?> entityType){

        try {
            if (Entity.class.isAssignableFrom(entityType)) {

                int window = this.getResult()[0] + this.getCommand().getCommandLength() + 1;

                List<Entity> entities = new ArrayList<Entity>();

                while(window < this.getResult().length) {
                    int headLen = 4;

                    ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen));
                    int infoLen = wrapped.getInt();

                    byte[] rId = Arrays.copyOfRange(this.getResult(), window + headLen, window + headLen + infoLen);

                    window += headLen+infoLen;

                    try {
                        Entity entity = (Entity) entityType.newInstance();
                        entity.setID(new String(rId, "US-ASCII"));

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


    public Object convertToEntityAttribute(){
        
    }



    public void setCommand(Command command) {
        this.command = command;
    }

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
