package com.supersimplestocks.data;

import com.supersimplestocks.data.validation.InputValidator;

/**
 * An implementation of preferred stock
 */
public class PreferredStock extends Stock {

    /**
     * Constructor
     * 
     * @param symbol - the stock symbol
     * @param lastDividend - the stock last dividend
     * @param fixedDividend - the stock fixed dividend
     * @param parValue - the stock par value
     */
    public PreferredStock(String symbol, int lastDividend, double fixedDividend, int parValue) {
        setSymbol(symbol);
        setLastDividend(lastDividend);
        setFixedDividend(fixedDividend);
        setParValue(parValue);
        setType(StockType.PREFERRED);
    }
    
    /**
     * Calculates the dividend yield for Preferred stock.
     * 
     * @param price - the price at which the stock is traded
     */
    @Override
    public double getDividendYield(int price) {
        InputValidator.validatePrice(price);
        
        return (double)getFixedDividend() * getParValue() / price;
    }
}
