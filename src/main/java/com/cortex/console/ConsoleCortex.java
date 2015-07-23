/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.console;

import com.cortex.Cortex;
import com.cortex.DefaultCortexCommandExecutor;
import com.cortex.DefaultCortexCommandParser;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class ConsoleCortex extends Cortex{

    public ConsoleCortex() {
        this.input = new CortexConsoleInput();
        this.output = new CortexConsoleOutput();
        this.executor = new DefaultCortexCommandExecutor();
        this.parser = new DefaultCortexCommandParser();
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
