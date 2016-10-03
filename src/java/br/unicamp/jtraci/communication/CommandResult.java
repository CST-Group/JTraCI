package br.unicamp.jtraci.communication;

/**
 * Created by Du on 02/10/16.
 */
public class CommandResult {

    private Command command;
    private byte[] result;

    public Command getCommand() {
        return command;
    }

    public CommandResult(Command command, byte[] result){
        this.setCommand(command);
        this.setResult(result);
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
}
