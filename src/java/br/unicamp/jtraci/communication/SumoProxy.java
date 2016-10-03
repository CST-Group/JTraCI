/*******************************************************************************
 * Copyright (c) 2016  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * A. L. O. Paraense, E. Froes, R. R. Gudwin
 ******************************************************************************/

package br.unicamp.jtraci.communication;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andre.paraense
 */
public class SumoProxy {

    private Socket socket;

    private DataInputStream dis;

    private DataOutputStream dos;

    /**
     * The current simulation step, in ms.
     */
    private int currentSimStep = 0;

    public SumoProxy() {

        socket = new Socket();

    }

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
        return socket.isConnected();
    }

    public CommandResult sendCommand(Command command) {

        int totalLenResult = 0;
        byte[] buffer = null;
        try {

            int totalLen = Integer.SIZE / 8;
            totalLen += command.getMessageSize();

            dos.writeInt(totalLen);

            byte[] message = command.getCommand();
            ((OutputStream) dos).write(message);

            dos.flush();

            totalLenResult = dis.readInt() - Integer.SIZE / 8;

            buffer = new byte[totalLenResult];
            dis.readFully(buffer);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return (new CommandResult(command, buffer));
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

        commands.forEach(c -> {
            try {
                byte[] message = c.getCommand();
                ((OutputStream) dos).write(message);
                dos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return readResultCommand(commands);

    }


    public List<CommandResult> readResultCommand(List<Command> executedCommands) {
        List<CommandResult> commandResults = new ArrayList<>();

        executedCommands.forEach(command -> {

            int totalLenResult = 0;

            try {
                totalLenResult = dis.readInt() - Integer.SIZE / 8;

                byte[] buffer = new byte[totalLenResult];
                dis.readFully(buffer);

                commandResults.add(new CommandResult(command, buffer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return commandResults;
    }


}
