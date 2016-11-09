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
import br.unicamp.jtraci.util.Constants;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandResult {

    private String id;
    private Command command;
    private byte[] result;
    private int varCount = 0;
    private boolean bVarCount = false;

    private int commandID;
    private byte varID;
    private String objectID;

    private int auxWindow;

    public Command getCommand() {
        return command;
    }

    public CommandResult(Command command, byte[] result) {

        this.setId(CommandResult.class.getSimpleName() + "_" + System.currentTimeMillis());
        this.setCommand(command);
        this.setResult(result);
    }

    public List<Entity> convertToEntity(Class<?> entityType) {

        try {
            if (Entity.class.isAssignableFrom(entityType)) {

                //8 = Context Domain(4) + Object ID(4)
                int window = this.getResult()[0] + 8;
                int headLen = 4;

                List<Entity> entities = new ArrayList<Entity>();

                if (isbVarCount()) {
                    varCount = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), this.getResult()[0], this.getResult()[0] + headLen)).getInt();
                    window += headLen;
                }

                //Finding out amount of objects.
                ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), window, window + headLen));
                int countObjects = wrapped.getInt();
                window += headLen;

                //Reading and Converting objects.
                for (int i = 0; i < countObjects; i++) {

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
            } else
                throw new Exception("Class isn't a Entity inheritance");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Object convertToEntityAttribute(Class<?> attributeType) {


        //Normally head length is 7, if is different maybe happened an error.
        //Head(Y) + Variable(4) + Vehicle ID(X) + Return type of the variable(4).
        int window = this.getResult()[0] + 4 + getCommand().convertStringUTF8Val(getCommand().getObjectID()).size();
        int headLen = 4;

        if (isbVarCount()) {
            varCount = ByteBuffer.wrap(Arrays.copyOfRange(this.getResult(), this.getResult()[0], this.getResult()[0] + headLen)).getInt();
            window += headLen;
        }

        //Calculating information length.
        int infoLen = this.getResult().length - window;

        //Reading attribute value.
        byte[] value = Arrays.copyOfRange(this.getResult(), window, window + infoLen);


        Object objectConverted = null;

        //Converting value.
        if (Double.class.isAssignableFrom(attributeType)) {
            objectConverted = convertDoubleValue(value);
        } else if (Integer.class.isAssignableFrom(attributeType)) {
            int intValue = convertIntValue(value);
            objectConverted = intValue;
        } else if (String.class.isAssignableFrom(attributeType)) {
            objectConverted = convertStringValue(value);
        } else if (List.class.isAssignableFrom(attributeType)) {
            objectConverted = convertStringListValue(value, window);
        }

        return objectConverted;

    }


    public boolean verifyError(byte[] value){
        return false;
    }

    public List<Object> convertCompoundAttribute(List<Class<?>> attributeTypes) {

        if(!verifyError(getResult())) {

            int window = this.getResult()[0];
            int headLen = 4;

            int infoLen = this.getResult()[window];
            window++;

            if (infoLen == 0) {
                infoLen = ByteBuffer.wrap(Arrays.copyOfRange(getResult(), window, window + headLen)).getInt() - 6;
                window += headLen;
            } else
                infoLen = infoLen - 2;

            setCommandID(readUnsignedByte(getResult()[window]));
            window++;

            //Reading attribute value.
            byte[] value = Arrays.copyOfRange(getResult(), window, window + infoLen);
            window = 0;

            setVarID(value[window]);
            window++;

            try {
                int stringLen = command.convertStringUTF8Val(command.getObjectID()).size();
                setObjectID(new String(Arrays.copyOfRange(value, window, window + stringLen), "US-ASCII"));
                window += stringLen;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            checkType(value[window], Constants.TYPE_COMPOUND);
            window++;
            window+=headLen; // ignore data length

            checkType(value[window], Constants.TYPE_INTEGER);
            window++;

            int countObjects =  ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + infoLen)).getInt();
            window+=headLen;

            List<Object> attributeValue = convertCompoundValue(value, attributeTypes, countObjects, window);


            return attributeValue;

        }
        else
            return null;


    }

    public List<Object> convertCompoundValue(byte[] value, List<Class<?>> attributeTypes, int countObjects, int window){

        List<Object> objects = new ArrayList<Object>();
        int headLen = 4;

        for (int i = 0; i < countObjects; i++) {

            for (Class<?> type : attributeTypes) {
                if (Double.class.isAssignableFrom(type)) {
                    checkType(value[window], Constants.TYPE_DOUBLE);
                    window++;

                } else if (Integer.class.isAssignableFrom(type)) {
                    checkType(value[window], Constants.TYPE_INTEGER);
                    window++;


                } else if (String.class.isAssignableFrom(type)) {
                    checkType(value[window], Constants.TYPE_STRING);
                    window++;

                } else if (List.class.isAssignableFrom(type)) {
                    checkType(value[window], Constants.TYPE_INTEGER);
                    window++;

                    int countOfStringList = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + headLen)).getInt();
                    window+=4;

                    for (int j = 0; j < countOfStringList ; j++) {
                        checkType(value[window], Constants.TYPE_STRINGLIST);
                        window++;
                        objects.add(convertStringListValue(value, window));
                        window = auxWindow;
                    }
                }
            }
        }

        return objects;
    }


    public boolean checkType(byte type, int typeID){
        if (type != typeID)
            return false;
        else
            return true;
    }

    public int readUnsignedByte(byte value)
    {
        return (int) ((value+ 256) % 256);
    }

    public double convertDoubleValue(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(value);
        return byteBuffer.getDouble();
    }

    public int convertIntValue(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(value);
        return byteBuffer.getInt();
    }

    public String convertStringValue(byte[] value) {
        String valString = null;
        try {
            valString = new String(value, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int headLen = 4;
        valString = valString.substring(headLen, valString.length());

        return valString;
    }

    public List<String>  convertStringListValue(byte[] value, int window){
        auxWindow = 0;
        int headLen = 4;
        int countObjects = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + headLen)).getInt();
        window += headLen;

        List<String> objects = new ArrayList<String>();

        for (int i = 0; i < countObjects; i++) {
            int infoLen = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + headLen)).getInt();
            byte[] byteObject = Arrays.copyOfRange(value, window + headLen, window + headLen + infoLen);

            String valString = null;
            try {
                valString = new String(byteObject, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            objects.add(valString);
            window += headLen + infoLen;
        }

        auxWindow = window;

        return objects;
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

    public int getCommandID() {
        return commandID;
    }

    public void setCommandID(int commandID) {
        this.commandID = commandID;
    }

    public byte getVarID() {
        return varID;
    }

    public void setVarID(byte varID) {
        this.varID = varID;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

}
