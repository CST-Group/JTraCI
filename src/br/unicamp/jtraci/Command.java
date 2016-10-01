package br.unicamp.jtraci;

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

    private int id;

    private byte varID;

    public Command() {
        content = new ArrayList<Byte>();
    }


    public byte[] getCommand() {

        List<Byte> commandList = new ArrayList<>();

        List<Byte> lst0 = this.convertByteVal(0);
        lst0.forEach(a -> commandList.add(a));

        List<Byte> lstHead  = this.convertIntVal(HEADER_SIZE + getContent().size());
        lstHead.forEach(b -> commandList.add(b));

        List<Byte> lstId = this.convertUnsignedByteVal(getId());
        lstId.forEach(c -> commandList.add(c));

        byte[] message = new byte[commandList.size() + getContent().size()];

        getContent().forEach(i -> commandList.add(i));

        for(int i=0; i<commandList.size(); i++)
            message[i] = commandList.get(i);

        return message;
    }




    public static List<Byte> convertByteVal(int value) throws IllegalArgumentException {

        List<Byte> convertList = new ArrayList<Byte>();

        if(value >= -128 && value <= 127) {
            convertList.add(new Byte((byte)value));

            return convertList;

        } else {
            throw new IllegalArgumentException("Error writing byte: byte value may only range from -128 to 127.");
        }
    }

    public static List<Byte> convertIntVal(int value) throws IllegalArgumentException
    {
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

        for(int i=0; i<bytes.length; i++)
            convertList.add(bytes[i]);

        return convertList;

    }

    public static List<Byte> convertUnsignedByteVal(int value) throws IllegalArgumentException
    {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException("Error writing unsigned byte: byte value may only range from 0 to 255.");

        List<Byte> convertList = new ArrayList<Byte>();

        // 0 -> 0
        // 127 -> 127
        // 128 -> -128
        // 255 -> -1

        if (value > 127) convertList.add(new Byte( (byte)(value-256) ));
        else convertList.add(new Byte( (byte)(value) ));

        return convertList;
    }

    /**
     * Write a string to the list by encoding the characters in UTF-8
     * @param value the String to be written
     */
    public List<Byte> convertStringUTF8Val(String value) throws IllegalArgumentException
    {
        return convertStringVal(value,"UTF-8");
    }

    private List<Byte> convertStringVal(String value, String charset) throws IllegalArgumentException
    {
        byte bytes[];

        try {
            bytes = value.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        List<Byte> convertList = new ArrayList<Byte>();

        convertIntVal(value.length()).forEach(c -> convertList.add(c));

        for (int i=0; i<bytes.length; i++)
            convertByteVal(bytes[i]).forEach(c -> convertList.add(c));

        return convertList;
    }


    public void addContent(Object content) {

        if(content instanceof Integer){
            this.convertIntVal((Integer) content).forEach(c -> getContent().add(c));
        }
        else if(content instanceof Byte) {
            getContent().add((Byte) content);
        } else if(content instanceof String){
            this.convertStringUTF8Val((String) content).forEach(c -> getContent().add(c));
        }

    }


    public void addVarID(byte varID){
        this.varID = varID;
        getContent().add(varID);
    }


    public List<Byte> getContent() {
        return content;
    }

    public void setContent(List<Byte> content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageSize() {
        return this.HEADER_SIZE + getContent().size();
    }


    public int getVarID() {
        return varID;
    }


}
