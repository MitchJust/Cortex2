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
public interface CortexCommandExecutor {
    public Object execute(CortexCommand command);
}
