/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex;

import com.cortex.commands.CortexCommand;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public abstract class Cortex extends Thread {

    protected CortexInput input;
    protected CortexOutput output;
    protected CortexCommandParser parser;
    protected CortexCommandExecutor executor;

    protected boolean shutdown = false;

    public void shutdown() {
        shutdown = true;
    }

    @Override
    public void run() {
        onCortexStart();
        while (!shutdown) {
            String inputCommand = input.receiveInput();
            Object result;
            try {
                result = parser.parseCommand(inputCommand);
                while(result instanceof CortexCommand) {
                    result = executor.execute((CortexCommand)result);
                }
                output.handleOutput(result);
            } catch (Exception ex) {
                Logger.getLogger(Cortex.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        onCortexEnd();
    }

    public abstract void onCortexStart();

    public abstract void onCortexEnd();
}
