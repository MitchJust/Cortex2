/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.console;

import com.cortex.Cortex;
import com.cortex.CortexCommandExecutor;
import com.cortex.CortexCommandParser;
import com.cortex.commands.CortexCommand;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class ConsoleCortex extends Cortex{

    public ConsoleCortex() {
        this.input = new CortexConsoleInput();
        this.output = new CortexConsoleOutput();

        this.executor = new CortexCommandExecutor() {

            @Override
            public Object execute(CortexCommand command) {
                return "pee!";
            }
        };
    }
    
    @Override
    public void onCortexStart() {
        output.handleOutput("OK READY TO GO LOL");
    }

    @Override
    public void onCortexEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        ConsoleCortex c = new ConsoleCortex();
        c.start();
    }
    
}
