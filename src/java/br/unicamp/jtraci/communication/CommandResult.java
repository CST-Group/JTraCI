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


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.unicamp.jtraci.entities.Entity;

public class CommandResult {

    private String id;

    private Command command;

    private byte[] result;

    private int varCount = 0;

    private boolean bVarCount = false;

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

                //8 = Context Domain(4) + Object ID(4)
                int window = this.getResult()[0] + 8;
                int headLen = 4;

                List<Entity> entities = new ArrayList<Entity>();

                if(isbVarCount()){
                    varCount = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), this.getResult()[0], this.getResult()[0] + headLen)).getInt();
                    window+=headLen;
                }

                //Finding out amount of objects.
                ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen));
                int countObjects = wrapped.getInt();
                window+=headLen;

                //Reading and Converting objects.
                for (int i = 0; i < countObjects ; i++) {

                    //Calculating information length.
                    int infoLen = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen)).getInt();

                    byte[] rId = Arrays.copyOfRange(this.getResult(), window + headLen, window + headLen + infoLen);
                    window += headLen + infoLen;

                    try {
                        Entity entity = (Entity) entityType.newInstance();

                        String sId = new String(rId, "US-ASCII");

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

        //Normally head length is 7, if is different maybe happened a error.
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
        } else if(List.class.isAssignableFrom(attributeType)){

            int headLen = 4;
            int countObjects = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen)).getInt();
            window += headLen;

            List<Object> objects = new ArrayList<Object>();

            for (int i = 0; i < countObjects ; i++) {
                infoLen = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen)).getInt();
                byte[] byteObject = Arrays.copyOfRange(this.getResult(), window + headLen, window + headLen + infoLen);

                String valString = null;
                try {
                    valString = new String(byteObject, "US-ASCII");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                objects.add(valString);
                window += headLen + infoLen;
            }

            objectConverted = objects;
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

    public int getVarCount() {
        return varCount;
    }

    public void setVarCount(int varCount) {
        this.varCount = varCount;
    }

    public boolean isbVarCount() {
        return bVarCount;
    }

    public void setbVarCount(boolean bVarCount) {
        this.bVarCount = bVarCount;
    }
}
