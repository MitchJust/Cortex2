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
public interface CortexCommandParser {
    public Object parseCommand(String command) throws Exception;
}
