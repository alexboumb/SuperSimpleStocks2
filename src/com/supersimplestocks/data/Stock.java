package com.supersimplestocks.data;

import com.supersimplestocks.data.resources.Messages;
import com.supersimplestocks.data.validation.InputValidator;
import com.supersimplestocks.exceptions.BusinessException;

/**
 * Abstract class representing a stock (can be common stock or preferred stock).
 * It contains information about the stock symbol, type of stock (common or preferred),
 * last dividend, fixed dividend and par value. It also contains methods for calculating
 * the dividend yield (different for common and preferred stock types and price/earnings ratio) 
 */
public abstract class Stock {
    
    /** The stock symbol */
    private String symbol;
    
    /** The stock type (Common, Preferred) */
    private StockType type;
    
    /** Last dividend of the stock */
    private int lastDividend;
    
    /** Fixed dividend of the stock */
    private double fixedDividend;
    
    /** Par value of the stock */
    private int parValue;
    
    /**
     * Retrieves the stock symbol
     * 
     *  @return - the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Updates the stock symbol. It also validates if the stock symbol is
     * not null or empty if the new value was not already validated by the
     * business logic; if the stock symbol is null or empty,
     * an {@link java.lang.IllegalArgumentException} is thrown.
     *
     * @param symbol - the new stock symbol
     */
    public void setSymbol(String symbol) {
        if (!InputValidator.validateStockSymbol(symbol)) {
            throw new IllegalArgumentException(Messages.ERR_STOCK_SYMBOL_NULL_EMPTY);
        }
        
        this.symbol = symbol;
    }
    
    /**
     * Retrieves the stock type
     * 
     * @return - the stock type (common or preferred)
     */
    public StockType getType() {
        return type;
    }
    
    /**
     * Updates the stock type. It also validates if the stock type is not null
     * if the new value was not already validated by the business logic;
     * if the stock type is null, an {@link java.lang.IllegalArgumentException}
     * is thrown.
     * 
     * @param type - the new stock type
     */
    public void setType(StockType type) {
        if (!InputValidator.validateStockType(type)) {
            throw new IllegalArgumentException(Messages.ERR_STOCK_TYPE_NULL);
        }
        
        this.type = type;
    }
    
    /**
     * Retrieves the last stock dividend
     * 
     * @return - the last stock dividend
     */
    public int getLastDividend() {
        return lastDividend;
    }
    
    /**
     * Updates the stock fixed dividend. It also validates if the last dividend
     * is not negative if the new value was not already validated by the business
     * logic; if the last dividend is negative, an {@link java.lang.IllegalArgumentException}
     * is thrown.
     * 
     * @param lastDividend - the new last stock dividend
     */
    public void setLastDividend(int lastDividend) {
        if (!InputValidator.validateLastDividend(lastDividend)) {
            throw new IllegalArgumentException(Messages.ERR_LAST_DIVIDEND_NEGATIVE);
        }
        
        this.lastDividend = lastDividend;
    }
    
    /**
     * Retrieves the stock fixed dividend
     * 
     * @return - the stock fixed dividend
     */
    public double getFixedDividend() {
        return fixedDividend;
    }
    
    /**
     * Updates the stock fixed dividend. It also validates if the fixed dividend
     * is not negative if the new value was not already validated by the business
     * logic; if the fixed dividend is negative, an {@link java.lang.IllegalArgumentException}
     * is thrown.
     * 
     * @param fixedDividend - the new stock fixed dividend
     */
    public void setFixedDividend(double fixedDividend) {
        if (!InputValidator.validateFixedDividend(fixedDividend)) {
            throw new IllegalArgumentException(Messages.ERR_FIXED_DIVIDEND_NEGATIVE);
        }
        
        this.fixedDividend = fixedDividend;
    }
    
    
    /**
     * Retrieves the stock par value
     * 
     * @return - the stock par value
     */
    public int getParValue() {
        return parValue;
    }
    
    /**
     * Updates the stock par value. It also validates if the par value is positive
     * if the new value was not already validated by the business logic; if the
     * par value is not positive, an {@link java.lang.IllegalArgumentException}
     * is thrown.
     * 
     * @param parValue - the new stock par value
     */
    public void setParValue(int parValue) {
        if (!InputValidator.validateParValue(parValue)) {
            throw new IllegalArgumentException(Messages.ERR_PAR_VALUE_NEGATIVE_ZERO);
        }
        
        this.parValue = parValue;
    }
    
    /**
     * Returns the price/earnings ratio
     * 
     * @param price - the price at which the stock was traded
     * @return - the P/E ratio
     */
    public double getPERatio(int price) throws BusinessException {
        if (lastDividend != 0) {
            return (double)price / lastDividend;
        }
        
        throw new BusinessException(Messages.ERR_PE_DIVIDEND_ZERO);
    }
    
    /**
     * Returns the dividend yield (calculated differently for Common and Preferred stocks)
     * 
     * @param price - the price at which the stock was traded
     * @return - the dividend yield
     */
    public abstract double getDividendYield(int price);
}
