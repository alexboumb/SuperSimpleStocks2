package com.supersimplestocks.logging;

/**
 * A class that provides logging functionality to log output. It provides methods for logging
 * operation output, prompt the user for input, log errors and log messages with parameters.
 */
public class Logger {
    /**
     * Logs the message to the console.
     * 
     * @param message - the message to be logged
     */
    public static void log(String message) {
        System.out.println(message);
    }
    
    /**
     * Prompts the user for input (the only difference from the log method is that promptInput
     * does not print a new line at the end of the message.
     * 
     * @param message - the message to be logged
     */
    public static void promptInput(String message) {
        System.out.print(message);
    }
    
    /**
     * Logs to the console a preformatted message with generic parameters that are replaced by
     * user parameters. Each parameter has the name: P{N} For example:
     * 
     * Original message: The P/E ratio for stock P0 is P1 given price P2
     * 
     * Transformed message: The P/E ratio for stock POP is 12.5 given price 100
     * 
     * @param message - the message to be logged
     * @param params - an array of Strings, one String for every parameter
     */
    public static void log(String message, String[] params) {
        for (int i = 0; i < params.length; i++) {
            message = message.replace("P" + i, params[i]);
        }
        
        log (message);
    }
    
    /**
     * Log an error to the console.
     * 
     * @param error - the error message to be logged
     */
    public static void error(String error) {
        log("ERROR: " + error);
    }
    
    /** Prints a new line */
    public static final void newLine() {
        log("");
    }
}
