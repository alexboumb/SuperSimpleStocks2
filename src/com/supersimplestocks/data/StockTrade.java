package com.supersimplestocks.data;

import java.util.Date;

import com.supersimplestocks.data.resources.Messages;
import com.supersimplestocks.data.validation.InputValidator;

/**
 * This class represents a stock trade (buy or sell). It contains information about
 * the transaction such as stock symbol, timestamp of the transaction, quantity of
 * shares bough/sold and the price at which the shares were traded.
 */
public class StockTrade {
    
    /**
     * Constructor. It validates the input parameters in case they were not already validated
     * by the business logic. If the validation fails, an {@link java.lang.IllegalArgumentException}
     * is thrown.
     * 
     * @param stock - the Stock object
     * @param timestamp - a timestamp of the time the trade was made
     * @param quantity - the quantity of shares bought/sold
     * @param price - the price at which the shares were traded
     * @param isBuy - whether the shares were bought or sold
     */
    public StockTrade(Stock stock, Date timestamp, int quantity, int price, boolean isBuy) {
        if (!InputValidator.validateStockObject(stock)) {
            throw new IllegalArgumentException(Messages.ERR_STOCK_NULL);
        }
        
        if (!InputValidator.validateTimestamp(timestamp)) {
            throw new IllegalArgumentException(Messages.ERR_TIMESTAMP_NULL);
        }
        
        if (!InputValidator.validateQuantity(quantity)) {
            throw new IllegalArgumentException(Messages.ERR_QUANTITY_NEGATIVE_ZERO);
        }
        
        if (!InputValidator.validatePrice(price)) {
            throw new IllegalArgumentException(Messages.ERR_PRICE_NEGATIVE_ZERO);
        }
        
        this.stock = stock;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.price = price;
        this.isBuy = isBuy;
        
        
    }
    
    /** The stock symbol that is traded */
    private Stock stock;
    
    /** The timestamp of the trade (Date instead of Timestamp is used to record trades over midnight */
    private Date timestamp;
    
    /** The quantity of the stock bought or sold */
    private int quantity;
    
    /** The price at which the stock was traded */
    private int price;
    
    /** The type of transaction (buy or sell) */
    private boolean isBuy;
    
    /**
     * Retrieves the transaction type (buy or sell)
     * 
     * @return - the type of transaction (buy or sell)
     */
    public boolean isBuy() {
        return isBuy;
    }
    
    /**
     * Retrieves the stock symbol
     * 
     * @return - the stock symbol
     */
    public Stock getStock() {
        return stock;
    }
    
    /**
     * Retrieves the timestamp of the trade
     * 
     * @return - the timestamp of the stock transaction
     */
    public Date getTimestamp() {
        return timestamp;
    }
    
    /**
     * Retrieves the number of stocks traded
     * 
     * @return - the number of stocks traded
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Retrieves the price at which the stock was traded
     * 
     * @return - the stock price
     */
    public int getPrice() {
        return price;
    }
}
