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

package br.unicamp.jtraci.simulation;

import br.unicamp.jtraci.util.Constants;
import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.SumoConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class SumoSimulation {

    private SumoConnection connection;
    private int currentStep;
    private Process sumoProcess;
    private static SumoSimulation sumoSimulation = null;


    public static SumoSimulation getInstance(){

        if(sumoSimulation ==  null)
            sumoSimulation = new SumoSimulation();

        return sumoSimulation;
    }

    private SumoSimulation(){

        setConnection(new SumoConnection());
    }

    public void connect(InetAddress address, int port) throws Exception {

        try {
            if (getConnection() != null) {
                getConnection().connect(address, port);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }

    }


    public boolean runSumoGui(String configPath, int port){

        boolean successfulRun = false;


        String[] sSumoArgs = new String[]{
                "sumo-gui",
                "-c", configPath,
                "--remote-port", Integer.toString(port),
                "-S"
        };

        try {

            setSumoProcess(Runtime.getRuntime().exec(sSumoArgs));

            connect(InetAddress.getByName("127.0.0.1"), port);

            successfulRun = true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successfulRun;
    }

    public SumoConnection getConnection() {
        return connection;
    }

    public void setConnection(SumoConnection connection) {
        this.connection = connection;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public void nextStep(){
        List<Command> commands = new ArrayList<Command>();


        Command command0 = new Command(Constants.CMD_SIMSTEP2);
        command0.addContent(((++currentStep) * 1000));
        commands.add(command0);


        getConnection().sendCommandList(commands);

    }

    public Process getSumoProcess() {
        return sumoProcess;
    }

    public void setSumoProcess(Process sumoProcess) {
        this.sumoProcess = sumoProcess;
    }
}
