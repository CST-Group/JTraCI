/*******************************************************************************
 * Copyright (c) 2016  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     A. L. O. Paraense, E. Froes, R. R. Gudwin
 ******************************************************************************/

package br.unicamp.jtraci;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	public SumoProxy(){
		
		socket = new Socket();
		
	}
	
	public boolean connect(InetAddress addr, int port){
		
		boolean successfulConnection = false;
		
		try {
			
			socket.connect(new InetSocketAddress(addr, port));			
			successfulConnection =  true;
			
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			
		} catch (ConnectException ce) {
			
			successfulConnection =  false;
		} catch (IOException e) {
			
			successfulConnection =  false;
		}
		
		return successfulConnection;
		
	}
	
	
	/**
	 * TODO - This main is only for fast testing purposes. Should be deleted later.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length!=2)
		{
			System.out.println("Usage: SumoProxy <P1> <P2>");
			System.out.println("<P1> = Server IP");
			System.out.println("<P2> = Server port");
			return;
		}
		
		String ipServidor = args[0];
		int port = Integer.valueOf(args[1]);

		
		SumoProxy sumoProxy = new SumoProxy();
		
		try {
			
			sumoProxy.connect(InetAddress.getByName(ipServidor), port);
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		
		while(true){
			
			
			
		}

	}
	
	
}
