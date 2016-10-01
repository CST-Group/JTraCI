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

package br.unicamp.jtraci;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * @author andre.paraense
 *
 */
public class SumoProxy {

    private Socket socket;

    private DataInputStream dis;

    private DataOutputStream dos;

    /** The current simulation step, in ms. */
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

    public void sendCommand(Command command) {
        try {

            int totalLen = Integer.SIZE / 8;
            totalLen += command.getMessageSize();

            dos.writeInt(totalLen);

            ((OutputStream) dos).write(command.getCommand());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommandList(List<Command> commands) {

        int totalLen = Integer.SIZE / 8;

        for (int i = 0; i < commands.size(); i++)
            totalLen += commands.get(i).getMessageSize();

        try {
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


        /*try {
            int totalLen2 = dis.readInt() - Integer.SIZE/8;
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[totalLen];
        try {
            dis.readFully(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
