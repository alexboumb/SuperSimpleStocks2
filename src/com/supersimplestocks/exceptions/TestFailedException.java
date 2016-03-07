package com.supersimplestocks.exceptions;

/**
 * A checked exception indicating a test has failed; when it is thrown the test process stops
 */
public class TestFailedException extends Exception {
    public static final long serialVersionUID = 1;
    
    /**
     * Constructor. It takes as an argument the message associated with the exception.
     * 
     * @param message - the message of the exception
     */
    public TestFailedException(String message) {
        super(message);
    }
}
