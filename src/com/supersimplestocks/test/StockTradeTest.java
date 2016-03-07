package com.supersimplestocks.test;

import com.supersimplestocks.business.StockTradeUtil;
import com.supersimplestocks.data.CommonStock;
import com.supersimplestocks.data.PreferredStock;
import com.supersimplestocks.data.StockData;
import com.supersimplestocks.data.resources.Messages;
import com.supersimplestocks.exceptions.TestFailedException;
import com.supersimplestocks.logging.Logger;

/**
 * Test class to test the stock trading functionality. It tests the business logic
 * that the dividend yield, P/E ratio, Volume Weighted Stock Price and All Share
 * Index return correct values. It also tests that any erroneous input, like an
 * empty stock symbol, or zero or negative price results in a thrown exception
 * that informs the user that there is something wrong.
 */
public class StockTradeTest {
    
    /**
     * Main method used to run the test.
     * 
     * @param args - method arguments
     * @throws TestFailedException - if one of the tests has failed
     */
    public static void main(String[] args) throws TestFailedException {
        StockTradeTest stockTest = new StockTradeTest();
        
        Logger.log(Messages.MSG_TESTS_START);
        stockTest.testBusinessLogic();
        stockTest.testExceptionProcessing();
        Logger.log(Messages.MSG_TESTS_FINISH);
    }
    
    /**
     * Tests the business logic of the application like dividend yield,
     * P/E ratio, Volume Weighted Stock Price and All Share Index return
     * correct values.
     * 
     * @throws TestFailedException - if one of the tests has failed
     */
    public void testBusinessLogic() throws TestFailedException {
        try {
            StockTradeUtil stockTrader = new StockTradeUtil(StockData.stockMap);
            assertTrue (stockTrader.getDividendYield(StockData.TEA, 50) == 0, StockData.TEA + Messages.ERR_DIVIDENT_YIELD_INCORRECT);
            assertTrue (stockTrader.getDividendYield(StockData.POP, 64) == 0.125, StockData.POP + Messages.ERR_DIVIDENT_YIELD_INCORRECT);
            assertTrue (stockTrader.getDividendYield(StockData.GIN, 50) == 0.04, StockData.GIN + Messages.ERR_DIVIDENT_YIELD_INCORRECT);
            
            assertTrue (stockTrader.getPERatio(StockData.ALE, 69) == 3, StockData.ALE + Messages.ERR_PE_RATIO_INCORRECT);
            assertTrue (stockTrader.getPERatio(StockData.POP, 64) == 8, StockData.POP + Messages.ERR_PE_RATIO_INCORRECT);
            
            stockTrader.tradeStock(StockData.TEA, 20, 60, false);
            stockTrader.tradeStock(StockData.TEA, 30, 120, true);
            
            stockTrader.tradeStock(StockData.GIN, 10, 60, true);
            stockTrader.tradeStock(StockData.GIN, 20, 150, false);
            
            assertTrue (stockTrader.getVolumeWeightedStockPrice(StockData.TEA) == 96, StockData.TEA + Messages.ERR_VOL_WEIGHT_PRICE_INCORRECT);
            assertTrue (stockTrader.getVolumeWeightedStockPrice(StockData.GIN) == 120, StockData.GIN + Messages.ERR_VOL_WEIGHT_PRICE_INCORRECT);
            
            assertTrue (stockTrader.getGBCEAllShareIndex() == Math.sqrt(11520), Messages.ERR_ALL_SHARE_INDEX_INCORRECT);
            
            //Sleep 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price is calculated
            //based on transactions only from the last 5 minutes
            Logger.log(Messages.MSG_SLEEP);
            Thread.sleep(6 * 60 * 1000);
            
            stockTrader.tradeStock(StockData.TEA, 10, 80, false);
            stockTrader.tradeStock(StockData.TEA, 10, 60, true);
            
            stockTrader.tradeStock(StockData.GIN, 10, 60, true);
            stockTrader.tradeStock(StockData.GIN, 20, 120, false);
            
            assertTrue (stockTrader.getVolumeWeightedStockPrice(StockData.TEA) == 70, StockData.TEA + Messages.ERR_VOL_WEIGHT_PRICE_INCORRECT);
            assertTrue (stockTrader.getVolumeWeightedStockPrice(StockData.GIN) == 100, StockData.GIN + Messages.ERR_VOL_WEIGHT_PRICE_INCORRECT);
            
            assertTrue (stockTrader.getGBCEAllShareIndex() == Math.sqrt(7000), Messages.ERR_ALL_SHARE_INDEX_INCORRECT);
        } catch (Exception ex) {
            throw new TestFailedException(ex.getMessage());
        }
    }
    
    /**
     * Tests the application error handling: whether an erroneous input results
     * in a thrown exception that informs the user that there is a problem. The
     * test also checks if a particular exception produces the necessary error
     * message.
     * 
     * @throws TestFailedException - if one of the tests has failed
     */
    public void testExceptionProcessing() throws TestFailedException {
        
        for (String stockSymbol : new String[] {null, ""}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() {
                    new CommonStock(stockSymbol, 0, 100);
                }
            }, Messages.ERR_STOCK_SYMBOL_NULL_EMPTY), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
            public void run() {
                new CommonStock(StockData.TEA, -1, 100);
            }
        }, Messages.ERR_LAST_DIVIDEND_NEGATIVE), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        
        for (int parValue : new int[] {0, -1}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() {
                    new CommonStock(StockData.TEA, 0, parValue);
                }
            }, Messages.ERR_PAR_VALUE_NEGATIVE_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
            public void run() {
                new PreferredStock(StockData.GIN, 8, -1.0, 100);
            }
        }, Messages.ERR_FIXED_DIVIDEND_NEGATIVE), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        
        StockTradeUtil stockTrader = new StockTradeUtil(StockData.stockMap);
        String[] stockSymbols = new String[] {null, "", StockData.NAN};
        String[] errorMessages = new String[] {Messages.ERR_STOCK_SYMBOL_NULL_EMPTY, Messages.ERR_STOCK_SYMBOL_NULL_EMPTY, Messages.ERR_NO_STOCK_FOUND + StockData.NAN};
        int i;
        
        for (i = 0; i < stockSymbols.length; i++) {
            String s = stockSymbols[i];
            
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.getDividendYield(s, 100);
                }
            }, errorMessages[i]), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        for (int price : new int[] {0, -1}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.getDividendYield(StockData.TEA, price);
                }
            }, Messages.ERR_PRICE_NEGATIVE_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        for (i = 0; i < stockSymbols.length; i++) {
            String s = stockSymbols[i];
            
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.getPERatio(s, 100);
                }
            }, errorMessages[i]), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        for (int price : new int[] {0, -1}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.getPERatio(StockData.TEA, price);
                }
            }, Messages.ERR_PRICE_NEGATIVE_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
            public void run() throws Exception {
                stockTrader.getPERatio(StockData.TEA, 100);
            }
        }, Messages.ERR_PE_DIVIDEND_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        
        for (i = 0; i < stockSymbols.length; i++) {
            String s = stockSymbols[i];
            
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.tradeStock(s, 20, 60, false);
                }
            }, errorMessages[i]), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        for (int quantity : new int[] {0, -1}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.tradeStock(StockData.TEA, quantity, 60, false);
                }
            }, Messages.ERR_QUANTITY_NEGATIVE_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        for (int price : new int[] {0, -1}) {
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.tradeStock(StockData.TEA, 20, price, false);
                }
            }, Messages.ERR_PRICE_NEGATIVE_ZERO), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
        
        errorMessages = new String[] {Messages.ERR_NO_STOCK_FOUND + StockData.NAN, Messages.ERR_NO_STOCK_DATA_FOUND + StockData.TEA};
        stockSymbols = new String[] {StockData.NAN, StockData.TEA};
        
        for (i = 0; i < stockSymbols.length; i++) {
            String s = stockSymbols[i];
            
            assertTrue(testExceptionCaughtWithMessage(this.new RunTestException() {
                public void run() throws Exception {
                    stockTrader.getVolumeWeightedStockPrice(s);
                }
            }, errorMessages[i]), Messages.ERR_EXCEPTION_NOT_CAUGHT);
        }
    }
    
    /** Run a code block that would throw an exception */
    private abstract class RunTestException {
        
        /** 
         * Abstract method implemented in child classes; it runs a piece
         * of code that is checked by the testExceptionCaughtWithMessage
         * method whether an exception was thrown or not.
         */
        public abstract void run() throws Exception;
    }
    
    /**
     * Checks if a particular piece of code throws an exception or not
     * 
     * @param test - a class which executes a piece of code that is checked for exception
     * @param message - the expected exception message in case the test class throws an exception
     * @return - if the exception has been caught and if it includes the specific message
     */
    private boolean testExceptionCaughtWithMessage(RunTestException test, String message) {
        boolean exceptionCaught = false;
        
        try {
            test.run();
        } catch (Exception ex) {
            exceptionCaught = ex.getMessage().equals(message);
        }
        
        return exceptionCaught;
    }
    
    /**
     * Tests whether a condition is true
     * 
     * @param condition - the condition to be tested
     * @param failMessage - the message that is generated if the condition is not satisfied
     * @throws TestFailedException - thrown if the condition is not satisfied
     */
    private void assertTrue(boolean condition, String failMessage) throws TestFailedException {
        if (!condition) {
            throw new TestFailedException(failMessage);
        }
    }
}
