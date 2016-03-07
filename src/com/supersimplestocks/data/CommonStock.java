package com.supersimplestocks.data;

import com.supersimplestocks.data.validation.InputValidator;

/**
 * An implementation of common stock
 */
public class CommonStock extends Stock {

    /**
     * Constructor
     * 
     * @param symbol - the stock symbol
     * @param lastDividend - the stock last dividend
     * @param parValue - the stock par value
     */
    public CommonStock(String symbol, int lastDividend, int parValue) {
        setSymbol(symbol);
        setLastDividend(lastDividend);
        setParValue(parValue);
        setType(StockType.COMMON);
    }
    
    /**
     * Calculates the dividend yield for Common stock
     * 
     * @param price - the price at which the stock is traded
     */
    @Override
    public double getDividendYield(int price) {
        InputValidator.validatePrice(price);
        
        return (double)getLastDividend() / price;
    }
}
