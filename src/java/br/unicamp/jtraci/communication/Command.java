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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Command {

    private static final int HEADER_SIZE = Byte.SIZE / 8     // length 0
            + Integer.SIZE / 8  // integer length
            + Byte.SIZE / 8;

    private List<Byte> content;

    private int cmdID;

    private int varID;

    private String objectID;

    private int commandLength;

    private byte[] command;

    public Command() {
        content = new ArrayList<Byte>();
        this.cmdID = -1;
        this.varID = -1;
        this.objectID = "";
    }

    public Command(int cmdID) {
        content = new ArrayList<Byte>();
        this.cmdID = cmdID;
        this.varID = -1;
        this.objectID = null;
    }

    public Command(int cmdID, int varID) {
        content = new ArrayList<Byte>();
        this.cmdID = cmdID;
        this.varID = varID;
        this.objectID = "";

        this.addHeaderParameters();
    }

    public Command(int cmdID, int varID, String objectID) {
        content = new ArrayList<Byte>();
        this.cmdID = cmdID;
        this.varID = varID;
        this.objectID = objectID;

        this.addHeaderParameters();
    }

    private void addHeaderParameters() {

        if(getVarID() != -1)
        {
            List<Byte> varIDBytes =  this.convertUnsignedByteVal(getVarID());
            for(int i=0; i<varIDBytes.size(); i++)
            {
                getContent().add(varIDBytes.get(i));
            }
        }

        if(getObjectID() != null){
            List<Byte> varIDBytes =  this.convertStringUTF8Val(getObjectID());
            for(int i=0; i<varIDBytes.size(); i++)
            {
                getContent().add(varIDBytes.get(i));
            }
        }
    }


    public byte[] getCommand() {

        List<Byte> commandList = new ArrayList<Byte>();

        List<Byte> lst0 = this.convertByteVal(0);
        commandList.addAll(lst0);

        this.setCommandLength(HEADER_SIZE + getContent().size());

        List<Byte> lstHead = this.convertIntVal(getCommandLength());
        commandList.addAll(lstHead);

        List<Byte> lstCmdId = this.convertUnsignedByteVal(getCmdID());
        commandList.addAll(lstCmdId);

        byte[] message = new byte[commandList.size() + getContent().size()];

        commandList.addAll(getContent());

        for (int i = 0; i < commandList.size(); i++)
            message[i] = commandList.get(i);

        this.command = message;

        return message;
    }


    public static List<Byte> convertByteVal(int value) throws IllegalArgumentException {

        List<Byte> convertList = new ArrayList<Byte>();

        if (value >= -128 && value <= 127) {
            convertList.add(new Byte((byte) value));

            return convertList;

        } else {
            throw new IllegalArgumentException("Error writing byte: byte value may only range from -128 to 127.");
        }
    }

    public static List<Byte> convertIntVal(int value) throws IllegalArgumentException {
        List<Byte> convertList = new ArrayList<Byte>();

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream(4);
        DataOutputStream dataOut = new DataOutputStream(byteOut);

        byte bytes[] = new byte[4];

        try {
            dataOut.writeInt(value);
            dataOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = byteOut.toByteArray();

        for (int i = 0; i < bytes.length; i++)
            convertList.add(bytes[i]);

        return convertList;

    }

    public static List<Byte> convertUnsignedByteVal(int value) throws IllegalArgumentException {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException("Error writing unsigned byte: byte value may only range from 0 to 255.");

        List<Byte> convertList = new ArrayList<Byte>();

        // 0 -> 0
        // 127 -> 127
        // 128 -> -128
        // 255 -> -1

        if (value > 127) convertList.add(new Byte((byte) (value - 256)));
        else convertList.add(new Byte((byte) (value)));

        return convertList;
    }

    public List<Byte> convertStringUTF8Val(String value) throws IllegalArgumentException {
        return convertStringVal(value, "UTF-8");
    }

    private List<Byte> convertStringVal(String value, String charset) throws IllegalArgumentException {
        byte bytes[];

        try {
            bytes = value.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        List<Byte> convertList = new ArrayList<Byte>();

        List<Byte> valueLengthBytes = convertIntVal(value.length());

        convertList.addAll(valueLengthBytes);

        for (int i = 0; i < bytes.length; i++) {

            List<Byte> byteLst = convertByteVal(bytes[i]);

            for (int j = 0; j <byteLst.size(); j++) {
                convertList.add(byteLst.get(j));
            }
        }


        return convertList;
    }


    public void addContent(Object content) {

        if (content instanceof Integer) {
            getContent().addAll( this.convertIntVal((Integer) content));
        } else if (content instanceof Byte) {
            getContent().add((Byte) content);
        } else if (content instanceof String) {
            getContent().addAll(this.convertStringUTF8Val((String) content));
        }

    }


    public void addVarID(byte varID) {
        this.setVarID(varID);
        getContent().add(varID);
    }


    public List<Byte> getContent() {
        return content;
    }

    public void setContent(List<Byte> content) {
        this.content = content;
    }

    public int getCmdID() {
        return cmdID;
    }

    public void setCmdID(int cmdID) {
        this.cmdID = cmdID;
    }

    public int getMessageSize() {
        return this.HEADER_SIZE + getContent().size();
    }


    public int getVarID() {
        return varID;
    }


    public void setVarID(int varID) {
        this.varID = varID;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public int getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(int commandLength) {
        this.commandLength = commandLength;
    }
}
