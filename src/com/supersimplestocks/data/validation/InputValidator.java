package com.supersimplestocks.data.validation;

import java.util.Date;

import com.supersimplestocks.data.Stock;
import com.supersimplestocks.data.StockType;

/**
 * Utility class with static methods for validating user input like stock symbol, price, etc
 */
public class InputValidator {
    
    /**
     * Validates the stock symbol: it must not be null or empty. A separate
     * method is written, instead of directly using validateNullEmptyString
     * where needed, in case some additional validation is needed in the
     * future.
     * 
     * @param stockSymbol - the stock symbol
     * @return - if the stock symbol is not empty or null
     */
    public static boolean validateStockSymbol(String stockSymbol) {
        return validateStringNotNullEmpty(stockSymbol);
    }
    
    /**
     * Validates the last dividend: it must not be negative. A separate
     * method is written, instead of directly using validateNegativeInt
     * where needed, in case some additional validation is needed in the
     * future.
     * 
     * @param lastDividend - the last dividend of the stock
     * @return - if the last dividend is not negative
     */
    public static boolean validateLastDividend(int lastDividend) {
        return validateIntPositiveZero(lastDividend);
    }
    
    /**
     * Validates the fixed dividend: it must not be negative. A separate
     * method is written, instead of directly using validateNegativeDouble
     * where needed, in case some additional validation is needed in the
     * future.
     * 
     * @param fixedDividend - the fixed dividend of the stock
     * @return - if the fixed dividend is not negative
     */
    public static boolean validateFixedDividend(double fixedDividend) {
        return validateDoublePositiveZero(fixedDividend);
    }
    
    /**
     * Validates the par value: it must not be negative or zero. A separate
     * method is written, instead of directly using validateNegativeZeroInt
     * where needed, in case some additional validation is needed in the
     * future.
     * 
     * @param parValue - the par value of the stock
     * @return - if the par value is positive
     */
    public static boolean validateParValue(int parValue) {
        return validateIntPositive(parValue);
    }
    
    /**
     * Validates the price: it must not be negative or zero. A separate
     * method is written, instead of directly using validateNegativeZeroInt
     * where needed, in case some additional validation is needed in the
     * future.
     * 
     * @param price - the price of the stock
     * @return - if the price is positive
     */
    public static boolean validatePrice(int price) {
        return validateIntPositive(price);
    }
    
    /**
     * Validates the stock object: it must not be null. A separate
     * method is written, instead of directly using validateNullObject
     * where needed, in case some additional validation is needed in the future.
     * 
     * @param stock - the Stock object
     * @return - if the stock object is not null
     */
    public static final boolean validateStockObject(Stock stock) {
        return validateObjectNotNull(stock);
    }
    
    /**
     * Validates the timestamp object: it must not be null. A separate
     * method is written, instead of directly using validateNullObject
     * where needed, in case some additional validation is needed in the future.
     * 
     * @param timestamp - the timestamp object
     * @return - if the timestamp is not null
     */
    public static final boolean validateTimestamp(Date timestamp) {
        return validateObjectNotNull(timestamp);
    }
    
    /**
     * Validates the stock quantity: it must not be negative or zero. A separate
     * method is written, instead of directly using validateNegativeZeroInt
     * where needed, in case some additional validation is needed in the future.
     * 
     * @param quantity - the stock quantity
     * @return - if the quantity is positive
     */
    public static final boolean validateQuantity(int quantity) {
        return validateIntPositive(quantity);
    }
    
    /**
     * Validates the stock type: it must not be null.
     * 
     * @param stockType - the stock type
     * @return - the validation result (true or false)
     */
    public static boolean validateStockType(StockType stockType) {
        return validateObjectNotNull(stockType);
    }
    
    /**
     * A generic method to validate if a string is null or empty
     * 
     * @param validatedString - the string to be validated
     * @return - if the validated string is not null or empty
     */
    public static boolean validateStringNotNullEmpty(String validatedString) {
        return validatedString != null && !validatedString.trim().isEmpty();
    }
    
    /**
     * A generic method to validate if an integer value is negative
     * 
     * @param value - the value to be validated
     * @return - if the validated value is greater or equal to zero
     */
    public static boolean validateIntPositiveZero(int value) {
        return value >= 0;
    }
    
    /**
     * A generic method to validate if an integer value is negative or zero
     * 
     * @param value - the value to be validated
     * @return - if the validated value is positive
     */
    public static boolean validateIntPositive(int value) {
        return value > 0;
    }
    
    /**
     * A generic method to validate if a double value is negative
     * 
     * @param value - the value to be validated
     * @return - if the result is greater or equal to zero
     */
    public static boolean validateDoublePositiveZero(double value) {
        return value >= 0.0;
    }

    /**
     * A generic method to validate if an object is null
     * 
     * @param object - the object to be validated
     * @return - if the validated object is different from null
     */
    public static boolean validateObjectNotNull(Object object) {
        return object != null;
    }
}
