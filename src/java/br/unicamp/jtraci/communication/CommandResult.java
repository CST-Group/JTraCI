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
