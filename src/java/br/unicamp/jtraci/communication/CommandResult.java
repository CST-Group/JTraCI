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


import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import br.unicamp.jtraci.entities.Entity;
import br.unicamp.jtraci.util.Constants;
import br.unicamp.jtraci.util.IgnoreParameter;

public class CommandResult {

    private String id;
    private Command command;
    private byte[] result;
    private int varCount = 0;

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
                int window = 0;
                int headLen = 4;

                window = verifyError(this.getResult());

                List<Entity> entities = new ArrayList<Entity>();

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

        int window = 0;
        try {
            window = verifyError(this.getResult());
        } catch (Exception e) {
            e.printStackTrace();
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
            objectConverted = convertStringListValue(this.getResult(), window);
        } else if (Path2D.class.isAssignableFrom(attributeType)) {
            objectConverted = convertShapeValue(value);
        } else if(Point2D.class.isAssignableFrom(attributeType)){
            objectConverted = convertPoint2Dvalue(value);
        }

        return objectConverted;

    }


    public int verifyError(byte[] value) throws Exception {

        //Normally head length is 7, if is different maybe happened an error.
        //Head(Y) + Variable(4) + Vehicle ID(X) + Return type of the variable(4).
        int window = this.getResult()[0] + 4 + getCommand().convertStringUTF8Val(getCommand().getObjectID()).size();
        int headLen = 4;

        try {
            int infoTest = this.getResult()[this.getResult()[0]];

            if (infoTest == 0) {
                window += headLen;
            }
            return window;
        }
        catch (Exception ex){
            ex = new Exception(convertStringValue(this.getResult()));
            ex.printStackTrace();
            throw ex;
        }

    }

    public List<Object> convertCompoundAttribute(List<Object> attributeTypes) {


            int window = this.getResult()[0];
            int headLen = 4;

            int infoLen = this.getResult()[window];
            window++;

            if (infoLen == 0) {
                infoLen = ByteBuffer.wrap(Arrays.copyOfRange(getResult(), window, window + headLen)).getInt() - 6;
                window += headLen;
            } else
                infoLen = getResult().length - window;

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
            window += headLen; // ignore data length

            checkType(value[window], Constants.TYPE_INTEGER);
            window++;

            int countObjects = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + infoLen)).getInt();
            window += headLen;

            auxWindow = window;

            List<Object> attributeValue = convertCompoundValue(value, attributeTypes, countObjects);


            return attributeValue;




    }

    public List<Object> convertCompoundValue(byte[] value, List<Object> attributeTypes, int countObjects) {

        List<Object> objects = new ArrayList<Object>();
        int headLen = 4;

        for (int i = 0; i < countObjects; i++) {

            List<Object> attributesObject = new ArrayList<Object>();

            for (Object type : attributeTypes) {

                if (type instanceof Class<?>) {

                    if (Double.class.isAssignableFrom((Class<?>) type)) {
                        checkType(value[auxWindow], Constants.TYPE_DOUBLE);
                        auxWindow++;

                    } else if (Integer.class.isAssignableFrom((Class<?>) type)) {
                        checkType(value[auxWindow], Constants.TYPE_INTEGER);
                        auxWindow++;
                        attributesObject.add(ByteBuffer.wrap(Arrays.copyOfRange(value, auxWindow, auxWindow + headLen)).getInt());
                        auxWindow += headLen;

                    } else if (String.class.isAssignableFrom((Class<?>) type)) {
                        checkType(value[auxWindow], Constants.TYPE_STRING);
                        auxWindow++;

                        int infoLen = ByteBuffer.wrap(Arrays.copyOfRange(value, auxWindow, auxWindow + headLen)).getInt();

                        attributesObject.add(convertStringValue(ByteBuffer.wrap(Arrays.copyOfRange(value, auxWindow, auxWindow + headLen + infoLen)).array()));
                        auxWindow += headLen + infoLen;

                    } else if (List.class.isAssignableFrom((Class<?>) type)) {
                        checkType(value[auxWindow], Constants.TYPE_INTEGER);
                        auxWindow++;

                        int countOfStringList = ByteBuffer.wrap(Arrays.copyOfRange(value, auxWindow, auxWindow + headLen)).getInt();
                        auxWindow += 4;

                        for (int j = 0; j < countOfStringList; j++) {
                            checkType(value[auxWindow], Constants.TYPE_STRINGLIST);
                            auxWindow++;
                            attributesObject.add(convertStringListValue(value, auxWindow));
                        }
                    }

                } else if (type instanceof Collection) {
                    checkType(value[auxWindow], Constants.TYPE_INTEGER);
                    auxWindow++;

                    int compoundObjectCount = ByteBuffer.wrap(Arrays.copyOfRange(value, auxWindow, auxWindow + headLen)).getInt();
                    auxWindow += headLen;

                    List<Object> objectsConverted = convertCompoundValue(value, (List<Object>) type, compoundObjectCount);

                    attributesObject.add(objectsConverted);
                } else if (type instanceof IgnoreParameter) {
                    auxWindow = ((IgnoreParameter) type).sumRange(auxWindow);
                }


            }

            objects.add(attributesObject);
        }

        return objects;
    }


    public boolean checkType(byte type, int typeID) {
        if (type != typeID)
            return false;
        else
            return true;
    }

    public int readUnsignedByte(byte value) {
        return (int) ((value + 256) % 256);
    }

    public Object convertPoint2Dvalue(byte[] value) {
        int window = 0;
        int doubleHead = 8;

        double x = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + doubleHead)).getDouble();
        window+=doubleHead;

        double y = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + doubleHead)).getDouble();

        Point2D point2D = new Point2D.Double(x, y);

        return point2D;
    }

    public Object convertShapeValue(byte[] value) {
        int window = 0;
        int doubleHead = 8;

        int countObjects = value[window];
        window++;

        Path2D.Double shape = new Path2D.Double();

        for (int i = 0; i < countObjects; ++i) {

            double x = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + doubleHead)).getDouble();
            window+=doubleHead;

            double y = ByteBuffer.wrap(Arrays.copyOfRange(value, window, window + doubleHead)).getDouble();
            window+=doubleHead;

            if (i == 0) {
                shape.moveTo(x, y);
            } else {
                shape.lineTo(x, y);
            }
        }

        return shape;
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

    public List<String> convertStringListValue(byte[] value, int window) {
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
