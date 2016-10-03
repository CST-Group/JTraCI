package br.unicamp.jtraci.simulation;

import br.unicamp.jtraci.util.Constants;
import br.unicamp.jtraci.communication.Command;
import br.unicamp.jtraci.communication.SumoProxy;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class SumoSimulation {

    private SumoProxy connection;
    private int currentStep;
    private Process sumoProcess;

    public SumoSimulation(){
        setConnection(new SumoProxy());
    }

    public SumoSimulation(SumoProxy connection){
        this.setConnection(connection);
        this.setCurrentStep(0);
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

    public SumoProxy getConnection() {
        return connection;
    }

    public void setConnection(SumoProxy connection) {
        this.connection = connection;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public void nextStep(){
        List<Command> commands = new ArrayList<>();


        Command command0 = new Command();
        command0.setId(Constants.CMD_SIMSTEP2);
        command0.addContent(((++currentStep) * 1000));
        commands.add(command0);

        /*Command command1 = new Command();
        command1.setId(Constants.CMD_GET_VEHICLE_VARIABLE);
        command1.addContent((byte)Constants.ID_LIST);
        command1.addContent("");
        commands.add(command1);*/

        //getConnection().sendReadCommand(command);

        /*Command command2 = new Command();
        command2.setId(Constants.CMD_GET_SIM_VARIABLE); // Command
        command2.addContent((byte)Constants.VAR_TELEPORT_STARTING_VEHICLES_IDS);
        command2.addContent("");
        commands.add(command2);


        Command command3 = new Command();
        command3.setId(Constants.CMD_GET_SIM_VARIABLE);
        command3.addContent((byte)Constants.VAR_TELEPORT_ENDING_VEHICLES_IDS);
        command3.addContent("");
        commands.add(command3);*/

        getConnection().sendCommandList(commands);

    }

    public Process getSumoProcess() {
        return sumoProcess;
    }

    public void setSumoProcess(Process sumoProcess) {
        this.sumoProcess = sumoProcess;
    }
}
