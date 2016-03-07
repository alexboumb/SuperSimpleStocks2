package com.supersimplestocks.exceptions;

/**
 * A checked exception indicating a business problem like a bad user input
 */
public class BusinessException extends Exception {
    public static final long serialVersionUID = 1;
    
    /**
     * Constructor. It takes as an argument the message associated with the exception.
     * 
     * @param message - the message of the exception
     */
    public BusinessException(String message) {
        super(message);
    }
}
