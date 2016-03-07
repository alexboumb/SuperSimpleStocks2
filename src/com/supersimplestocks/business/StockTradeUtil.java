package com.supersimplestocks.business;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.supersimplestocks.data.Stock;
import com.supersimplestocks.data.StockData;
import com.supersimplestocks.data.StockTrade;
import com.supersimplestocks.data.resources.Messages;
import com.supersimplestocks.data.validation.InputValidator;
import com.supersimplestocks.exceptions.BusinessException;
import com.supersimplestocks.logging.Logger;

/**
 * A class that executes business operations (buy, sell) on the stock exchange
 * and retrieves information about the stocks such as dividend yield, P/E ratio,
 * Volume Weighted Stock Price for a given stock based on trades in the 5 minutes
 * and All Share Index.
 */
public class StockTradeUtil {
    
    private Map<Stock, List<StockTrade>> tradeMap = new HashMap<Stock, List<StockTrade>>();
    private Map<String, Stock> stockMap;
    
    /**
     * Main method to run the basic class functionality.
     * 
     * @param args - method arguments
     * @throws BusinessException - if there is a business problem like a bad user input
     * @throws InterruptedException - if the current thread is interrupted while waiting for notification
     */
    public static void main(String[] args) throws BusinessException, InterruptedException {
        StockTradeUtil stockTrader = new StockTradeUtil(StockData.stockMap);
        double dividendYield = stockTrader.getDividendYield(StockData.TEA, 50);
        Logger.log(Messages.MSG_STOCK_DIVIDEND_YIELD, new String[] {StockData.TEA, Double.toString(dividendYield), "50"});
        
        dividendYield = stockTrader.getDividendYield(StockData.POP, 64);
        Logger.log(Messages.MSG_STOCK_DIVIDEND_YIELD, new String[] {StockData.POP, Double.toString(dividendYield), "64"});
        
        dividendYield = stockTrader.getDividendYield(StockData.GIN, 50);
        Logger.log(Messages.MSG_STOCK_DIVIDEND_YIELD, new String[] {StockData.GIN, Double.toString(dividendYield), "50"});
        
        double peRatio = stockTrader.getPERatio(StockData.ALE, 69);
        Logger.log(Messages.MSG_PRICE_EARNINGS_RATIO, new String[] {StockData.ALE, Double.toString(peRatio), "69"});
        
        peRatio = stockTrader.getPERatio(StockData.POP, 64);
        Logger.log(Messages.MSG_PRICE_EARNINGS_RATIO, new String[] {StockData.POP, Double.toString(peRatio), "64"});
        
        stockTrader.tradeStock(StockData.TEA, 20, 60, false);
        Logger.log(Messages.MSG_SELL_STOCK, new String[] {"20", StockData.TEA, "60"});
        
        stockTrader.tradeStock(StockData.TEA, 30, 120, true);
        Logger.log(Messages.MSG_BUY_STOCK, new String[] {"30", StockData.TEA, "120"});
        
        stockTrader.tradeStock(StockData.GIN, 10, 60, true);
        Logger.log(Messages.MSG_BUY_STOCK, new String[] {"10", StockData.GIN, "60"});
        
        stockTrader.tradeStock(StockData.GIN, 20, 150, false);
        Logger.log(Messages.MSG_SELL_STOCK, new String[] {"20", StockData.GIN, "150"});
        
        double volWeighPrice = stockTrader.getVolumeWeightedStockPrice(StockData.TEA);
        Logger.log(Messages.MSG_VOL_WEIGH_PRICE, new String[] {StockData.TEA, Double.toString(volWeighPrice)});
        
        volWeighPrice = stockTrader.getVolumeWeightedStockPrice(StockData.GIN);
        Logger.log(Messages.MSG_VOL_WEIGH_PRICE, new String[] {StockData.GIN, Double.toString(volWeighPrice)});
        
        double allShareIndex = stockTrader.getGBCEAllShareIndex();
        Logger.log(Messages.MSG_ALL_SHARE_INDEX, new String[] {Double.toString(allShareIndex)});
        
        //sleep 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price is calculated
        //based on trades only in the past 5 minutes
        Logger.log(Messages.MSG_SLEEP);
        Thread.sleep(6 * 60 * 1000);
        
        stockTrader.tradeStock(StockData.TEA, 10, 80, false);
        Logger.log(Messages.MSG_SELL_STOCK, new String[] {"10", StockData.TEA, "80"});
        
        stockTrader.tradeStock(StockData.TEA, 10, 60, true);
        Logger.log(Messages.MSG_BUY_STOCK, new String[] {"10", StockData.TEA, "60"});
        
        stockTrader.tradeStock(StockData.GIN, 10, 60, true);
        Logger.log(Messages.MSG_BUY_STOCK, new String[] {"10", StockData.GIN, "60"});
        
        stockTrader.tradeStock(StockData.GIN, 20, 120, false);
        Logger.log(Messages.MSG_SELL_STOCK, new String[] {"20", StockData.GIN, "120"});
        
        volWeighPrice = stockTrader.getVolumeWeightedStockPrice(StockData.TEA);
        Logger.log(Messages.MSG_VOL_WEIGH_PRICE, new String[] {StockData.TEA, Double.toString(volWeighPrice)});
        
        volWeighPrice = stockTrader.getVolumeWeightedStockPrice(StockData.GIN);
        Logger.log(Messages.MSG_VOL_WEIGH_PRICE, new String[] {StockData.GIN, Double.toString(volWeighPrice)});
        
        allShareIndex = stockTrader.getGBCEAllShareIndex();
        Logger.log(Messages.MSG_ALL_SHARE_INDEX, new String[] {Double.toString(allShareIndex)});
    }
    
    /**
     * Constructor. Accepts a map that contains sample data for several stocks to be used in the stock trade.
     * 
     * @param stockMap - the stock data sample that will be used while running the application
     */
    public StockTradeUtil(Map<String, Stock> stockMap) {
        this.stockMap = stockMap;
    }
    
    /**
     * Returns the dividend yield for a stock based on a given price. It also
     * validates the input parameters; if the validation fails, a
     * {@link com.supersimplestocks.exceptions.BusinessException} is thrown.
     * 
     * @param stockSymbol - the stock symbol
     * @param price - the price at which the stock was traded
     * @return - the dividend yield of the stock
     * @throws BusinessException
     */
    public double getDividendYield(String stockSymbol, int price) throws BusinessException {
        if (!InputValidator.validateStockSymbol(stockSymbol)) {
            throw new BusinessException(Messages.ERR_STOCK_SYMBOL_NULL_EMPTY);
        }
        
        if (!InputValidator.validatePrice(price)) {
            throw new BusinessException(Messages.ERR_PRICE_NEGATIVE_ZERO);
        }
        
        Stock stock = stockMap.get(stockSymbol);
        if (stock == null) {
            throw new BusinessException(Messages.ERR_NO_STOCK_FOUND + stockSymbol);
        }
        
        return stock.getDividendYield(price);
    }
    
    /**
     * Returns the price/earnings ratio for a stock based on a given price.
     * It also validates the input parameters; if the validation fails, a
     * {@link com.supersimplestocks.exceptions.BusinessException} is thrown.
     * 
     * @param stockSymbol - the stock symbol
     * @param price - the price at which the stock was traded
     * @return - the price/earnings ratio of the stock
     * @throws BusinessException
     */
    public double getPERatio(String stockSymbol, int price) throws BusinessException {
        if (!InputValidator.validateStockSymbol(stockSymbol)) {
            throw new BusinessException(Messages.ERR_STOCK_SYMBOL_NULL_EMPTY);
        }
        
        if (!InputValidator.validatePrice(price)) {
            throw new BusinessException(Messages.ERR_PRICE_NEGATIVE_ZERO);
        }
        
        Stock stock = stockMap.get(stockSymbol);
        if (stock == null) {
            throw new BusinessException(Messages.ERR_NO_STOCK_FOUND + stockSymbol);
        }
        
        return stock.getPERatio(price);        
    }
    
    /**
     * Performs a stock trade (buy or sell). It also validates the input parameters;
     * if the validation fails, a {@link com.supersimplestocks.exceptions.BusinessException}
     * is thrown.
     * 
     * @param stockSymbol - the stock to be traded
     * @param quantity - the quantity of shares that were traded
     * @param price - the price at which the stock was traded
     * @param isBuy - whether the stock is bought or sold
     * @throws BusinessException
     */
    public void tradeStock(String stockSymbol, int quantity, int price, boolean isBuy) throws BusinessException {
        if (!InputValidator.validateStockSymbol(stockSymbol)) {
            throw new BusinessException(Messages.ERR_STOCK_SYMBOL_NULL_EMPTY);
        }
        
        if (!InputValidator.validateQuantity(quantity)) {
            throw new BusinessException(Messages.ERR_QUANTITY_NEGATIVE_ZERO);
        }
        
        if (!InputValidator.validatePrice(price)) {
            throw new BusinessException(Messages.ERR_PRICE_NEGATIVE_ZERO);
        }
        
        Stock stock = stockMap.get(stockSymbol);
        if (stock == null) {
            throw new BusinessException(Messages.ERR_NO_STOCK_FOUND + stockSymbol);
        }
        
        List<StockTrade> tradeList = tradeMap.get(stock);
        
        if (tradeList == null) {
            tradeList = new LinkedList<StockTrade>();
            tradeMap.put(stock, tradeList);
        }
        
        tradeList.add(new StockTrade(stock, new Date(), quantity, price, isBuy));
    }
    
    /**
     * Calculates the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks.
     * It also validates the input parameters; if the validation fails, a {@link com.supersimplestocks.exceptions.BusinessException}
     * is thrown.
     * 
     * @return the GBCE All Share Index
     * @throws BusinessException
     */
    public double getGBCEAllShareIndex() throws BusinessException {
        Set<Stock> stockSet = tradeMap.keySet();
        Iterator<Stock> stockIter = stockSet.iterator();
        double stockProduct = 1.0;
        int stockCount = 0;
        
        while (stockIter.hasNext()) {
            Stock stock = stockIter.next();
            
            stockProduct *= getVolumeWeightedStockPrice(stock.getSymbol());
            stockCount++;
        }
        
        if (stockCount > 0) {
            return Math.pow(stockProduct, (double)1 / stockCount);
        }
        
        return 0;
    }
    
    /**
     * Calculates the Volume Weighted Stock Price based on trades in past 5 minutes.
     * It also validates the input parameters; if the validation fails, a
     * {@link com.supersimplestocks.exceptions.BusinessException} is thrown. 
     * 
     * @param stockSymbol - the stock symbol
     * @return - the Volume Weighted Stock Price
     * @throws BusinessException
     */
    public double getVolumeWeightedStockPrice(String stockSymbol) throws BusinessException {
        if (!InputValidator.validateStockSymbol(stockSymbol)) {
            throw new BusinessException(Messages.ERR_STOCK_SYMBOL_NULL_EMPTY);
        }
        
        Stock stock = stockMap.get(stockSymbol);
        if (stock == null) {
            throw new BusinessException(Messages.ERR_NO_STOCK_FOUND + stockSymbol);
        }
        
        List<StockTrade> tradeList = tradeMap.get(stock);
        if (tradeList == null) {
            throw new BusinessException(Messages.ERR_NO_STOCK_DATA_FOUND + stockSymbol);
        }
        
        if (tradeList != null) {
            ListIterator<StockTrade> listIterator = tradeList.listIterator(tradeList.size());
            int sumPriceQuantity = 0;
            int sumQuantity = 0;
            long fiveMinsBefore = (new Date()).getTime() - 5 * 60 * 1000;
            
            while (listIterator.hasPrevious()) {
                StockTrade stockTrade = listIterator.previous();
                
                if (stockTrade.getTimestamp().getTime() >= fiveMinsBefore) {
                    sumPriceQuantity += stockTrade.getPrice() * stockTrade.getQuantity();
                    sumQuantity += stockTrade.getQuantity();
                }
            }
            
            if (sumQuantity != 0) {
                return (double) sumPriceQuantity / sumQuantity;
            }
        }
        
        return 1;
    }
    
    /**
     * Utility method to check if a user-entered value is positive integer
     * 
     * @param str - the entered value
     * @return - true if the entered value is a positive integer
     */
    public static boolean checkStrPositiveInt(String str) {
        try {
            if (Integer.parseInt(str) <= 0) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
}
