/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex;

import com.cortex.commands.CortexCommand;


/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public abstract class Cortex extends Thread{
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
        while(!shutdown) {
            String inputCommand = input.receiveInput();
            CortexCommand parsedCommand = parser.parseCommand(inputCommand);
            Object result = executor.execute(parsedCommand);
            output.handleOutput(result);
        }
        onCortexEnd();
    }
    
    public abstract void onCortexStart();
    public abstract void onCortexEnd();
}
