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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SumoConnection {

    private Socket socket;

    private DataInputStream dis;

    private DataOutputStream dos;

    public SumoConnection() {}

    public boolean connect(InetAddress addr, int port) throws Exception {

        boolean successfulConnection = false;

        while (!successfulConnection) {
            try {

                socket = new Socket(addr, port);
                successfulConnection = true;

                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));


            } catch (ConnectException ce) {
                successfulConnection = false;
            } catch (IOException e) {
                successfulConnection = false;
            }
        }

        return successfulConnection;

    }

    public boolean isConnected() {
    	
    	boolean isConnected = false;
    	
    	if(socket != null)
    		isConnected = socket.isConnected();
    		
        return isConnected;
    }

    public CommandResult sendCommand(Command command) {

        CommandResult commandResult = null;

        try {

            int totalLen = Integer.SIZE / 8;
            totalLen += command.getMessageSize();

            dos.writeInt(totalLen);

            byte[] message = command.getCommand();
            ((OutputStream) dos).write(message);

            dos.flush();

            commandResult = readResultCommand(command);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return commandResult;
    }

    public List<CommandResult> sendCommandList(List<Command> commands) {

        int totalLen = Integer.SIZE / 8;

        try {
            for (int i = 0; i < commands.size(); i++)
                totalLen += commands.get(i).getMessageSize();

            dos.writeInt(totalLen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Command command: commands) {
            try {
                byte[] message = command.getCommand();
                ((OutputStream) dos).write(message);
                dos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return readResultCommandList(commands);

    }


    public CommandResult readResultCommand(Command command) {

        CommandResult commandResult = null;

        try {
            int totalLenResult = dis.readInt() - Integer.SIZE / 8;

            byte[] buffer = new byte[totalLenResult];
            dis.readFully(buffer);

            commandResult = new CommandResult(command, buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return commandResult;
    }


    public List<CommandResult> readResultCommandList(List<Command> executedCommands) {
        List<CommandResult> commandResults = new ArrayList<CommandResult>();

        for (int i = 0; i < executedCommands.size() ; i++) {
            commandResults.add(readResultCommand(executedCommands.get(i)));
        }

        return commandResults;
    }

	public void close() {
		
		try {
			
			socket.close();
			
			socket = null;
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}


}
