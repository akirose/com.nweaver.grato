package com.nweaver.grato.command;

/**
 * Command factory interface.
 * 
 * @author Min-ki,Cho
 */
public interface CommandFactory {
	
	/**
	 * Get the command instance.
	 * @param commandName The name of the command to create
	 * @return The {@link Command} matching the provided name, or
	 *    null if no such command exists.
	 */
	Command getCommand(String commandName);
}
