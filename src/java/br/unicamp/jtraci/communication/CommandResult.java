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

import java.io.UnsupportedEncodingException;
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

                //8 = Context Domain(4) + Variable Count(4)
                int window = this.getResult()[0] + 8;

                List<Entity> entities = new ArrayList<Entity>();

                //Finding out amount of objects.
                int headLen = 4;
                ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen));
                int countObjects = wrapped.getInt();
                window+=headLen;

                //Calculating information length.
                int infoLen = (this.getResult().length - window)/countObjects;

                //Reading and Converting objects.
                for (int i = 0; i < countObjects ; i++) {

                    byte[] rId = Arrays.copyOfRange(this.getResult(), window, window + infoLen);
                    window += infoLen;

                    try {
                        Entity entity = (Entity) entityType.newInstance();

                        String sId = new String(rId, "US-ASCII");
                        sId = sId.substring(headLen, sId.length());

                        entity.setID(sId);

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

    public Object convertToEntityAttribute(Class<?> attributeType){

        //Head(Y) + Variable(4) + Vehicle ID(X) + Return type of the variable(4).
        int window = this.getResult()[0] + 4 + getCommand().convertStringUTF8Val(getCommand().getObjectID()).size();

        //Calculating information length.
        int infoLen = this.getResult().length - window;

        //Reading attribute value.
        byte[] value = Arrays.copyOfRange(this.getResult(), window, window + infoLen);

        ByteBuffer byteBuffer = ByteBuffer.wrap(value);
        Object objectConverted = null;

        //Converting value.
        if(Double.class.isAssignableFrom(attributeType)){
            double doubleValue = byteBuffer.getDouble();
            objectConverted = doubleValue;

        }else if(Integer.class.isAssignableFrom(attributeType)){
            int intValue = byteBuffer.getInt();
            objectConverted = intValue;
        }else if(String.class.isAssignableFrom(attributeType)){
            try {
                String valString = new String(value, "US-ASCII");
                int headLen = 4;
                valString = valString.substring(headLen, valString.length());
                objectConverted = valString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return objectConverted;

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
