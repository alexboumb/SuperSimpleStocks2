package com.supersimplestocks.business;

import java.io.Console;
import java.util.Arrays;
import java.util.Set;

import com.supersimplestocks.data.StockData;
import com.supersimplestocks.data.resources.Messages;
import com.supersimplestocks.exceptions.BusinessException;
import com.supersimplestocks.exceptions.TestFailedException;
import com.supersimplestocks.logging.Logger;
import com.supersimplestocks.test.StockTradeTest;

/**
 * A console-based interface that allows the user to execute operations on the stock market, like
 * retrieving data (dividend yield, price/earnings ratio, Volume Weighted Stock Price), to buy
 * and sell shares and to calculate the All Share Index.
 */
public class StockTradeExec {
    
    private static final String DIV_YIELD = "d";
    private static final String PE_RATIO = "p";
    private static final String BUY = "b";
    private static final String SELL = "s";
    private static final String VOL_WGH = "v";
    private static final String ALL_SHARE_INDEX = "a";
    private static final String TEST = "t";
    private static final String HELP = "h";
    private static final String QUIT = "q";
    private static final String YES = "y";
    private static final String NO = "n";
    
    private StockTradeUtil stockTradeUtil;
    
    /**
     * Main method, used to start the application.
     * 
     * @param args - method arguments
     */
    public static void main(String[] args) {
        StockTradeExec stockRunner = new StockTradeExec();
        stockRunner.run();
    }
    
    /**
     * Constructor
     */
    public StockTradeExec() {
        stockTradeUtil = new StockTradeUtil(StockData.stockMap);
    }
    
    /**
     * This method actually starts the application: it displays a menu of choices and prompts
     * the user to select a choice. After the user has made the selection, the user is prompted
     * for input arguments, depending on the operation he/she has selected. If the user enters
     * invalid arguments (like negative price for example), he/she is prompted to enter data
     * again.
     */
    public void run() {
        Logger.log(Messages.MSG_WELCOME);
        Logger.log(Messages.MENU);
        
        String selection = readSelection();
        while (!selection.equals(QUIT)) {
            try {
                switch (selection) {
                case DIV_YIELD:
                    calcDividendYield();
                    break;
                case PE_RATIO:
                    calcPERatio();
                    break;
                case BUY:
                    buy();
                    break;
                case SELL:
                    sell();
                    break;
                case VOL_WGH:
                    calcVolWeighStockPrice();
                    break;
                case ALL_SHARE_INDEX:
                    calcAllShareIndex();
                    break;
                case TEST:
                    runTests();
                    break;
                case HELP:
                    Logger.log(Messages.MENU);
                    break;
                case QUIT:
                    if (confirmQuit()) {
                        System.exit(0);
                    }
                    break;
                }
            } catch (Exception ex) {
                Logger.error(ex.getMessage());
            }
            
            Logger.newLine();
            selection = readSelection();
        }
    }
    
    /**
     * This method calculates the dividend yield for a given stock, based on the price.
     * It reads and validates the stock symbol and price before doing any calculations.
     * If the user enter wrong data, he/she is prompted to enter data again. The result
     * of the operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.getDividendYield
     * method is called with bad data (here it should not happen, as the data is validated in the
     * calcDividendYield() method)
     */
    private void calcDividendYield() throws BusinessException {
        String stockSymbol = readStockSymbol();
        int price = readPositiveInt(Messages.MSG_ENTER_PRICE, Messages.ERR_PRICE_FORMAT);
        double dividendYield = stockTradeUtil.getDividendYield(stockSymbol, price);
        
        Logger.log(Messages.MSG_STOCK_DIVIDEND_YIELD, new String[] {stockSymbol, Double.toString(dividendYield), Integer.toString(price)});
    }
    
    /**
     * This method calculates the price/earnings ratio for a given stock, based on the price.
     * It reads and validates the stock symbol and price before doing any calculations.
     * If the user enter wrong data, he/she is prompted to enter data again. The result
     * of the operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.getPERatio 
     * method is called with bad data (here it should not happen, as the data is validated in the
     * calcPERatio() method)
     */
    private void calcPERatio() throws BusinessException {
        String stockSymbol = readStockSymbol();
        int price = readPositiveInt(Messages.MSG_ENTER_PRICE, Messages.ERR_PRICE_FORMAT);
        double dividendYield = stockTradeUtil.getPERatio(stockSymbol, price);
        
        Logger.log(Messages.MSG_PRICE_EARNINGS_RATIO, new String[] {stockSymbol, Double.toString(dividendYield), Integer.toString(price)});
    }
    
    /**
     * This method executes a buy operation and records information such as stock symbol, quantity,
     * price, timestamp and buy/sell indicator. It reads and validates the stock symbol, price and
     * quantity before doing any operations. If the user enter wrong data, he/she is prompted to
     * enter data again. The result of the operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.tradeStock method
     * is called with bad data (here it should not happen, as the data is validated in the buy() method)
     */
    private void buy() throws BusinessException {
        String stockSymbol = readStockSymbol();
        int price = readPositiveInt(Messages.MSG_ENTER_PRICE, Messages.ERR_PRICE_FORMAT);
        int quantity = readPositiveInt(Messages.MSG_ENTER_QUANTITY, Messages.ERR_QUANTITY_FORMAT);
        
        stockTradeUtil.tradeStock(stockSymbol, quantity, price, true);
        Logger.log(Messages.MSG_BUY_STOCK, new String[] {Integer.toString(quantity), stockSymbol, Integer.toString(price)});
    }
    
    /**
     * This method executes a sell operation and records information such as stock symbol, quantity,
     * price, timestamp and buy/sell indicator. It reads and validates the stock symbol, price and
     * quantity before doing any operations. If the user enter wrong data, he/she is prompted to
     * enter data again. The result of the operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.tradeStock method
     * is called with bad data (here it should not happen, as the data is validated in the sell() method)
     */
    private void sell() throws BusinessException {
        String stockSymbol = readStockSymbol();
        int price = readPositiveInt(Messages.MSG_ENTER_PRICE, Messages.ERR_PRICE_FORMAT);
        int quantity = readPositiveInt(Messages.MSG_ENTER_QUANTITY, Messages.ERR_QUANTITY_FORMAT);
        
        stockTradeUtil.tradeStock(stockSymbol, quantity, price, false);
        Logger.log(Messages.MSG_SELL_STOCK, new String[] {Integer.toString(quantity), stockSymbol, Integer.toString(price)});
    }
    
    /**
     * This method calculates the Volume Weighted Stock Price for a given stock, based on trades in
     * the past 5 minutes. It reads and validates the stock symbol before doing any calculations.
     * If the user enter wrong data, he/she is prompted to enter data again. The result of the
     * operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.getVolumeWeightedStockPrice 
     * method is called with bad data (here it should not happen, as the data is validated in the
     * calcVolWeighStockPrice() method)
     */
    private void calcVolWeighStockPrice() throws BusinessException {
        String stockSymbol = readStockSymbol();
        double volWeighStockPrice = stockTradeUtil.getVolumeWeightedStockPrice(stockSymbol);
        
        Logger.log(Messages.MSG_VOL_WEIGH_PRICE, new String[] {stockSymbol, Double.toString(volWeighStockPrice)});
    }
    
    /**
     * This method calculates the All Share Index, using the geometric mean of the Volume Weighted Stock Price
     * for all stocks. The result of the operation is displayed on the command prompt.
     * 
     * @throws BusinessException - as an extra precaution if the StockTradeUtil.getGBCEAllShareIndex 
     * method is called with bad data (here it should not happen, as the data is validated in the
     * calcAllShareIndex() method)
     */
    private void calcAllShareIndex() throws BusinessException {
        Logger.log(Messages.MSG_ALL_SHARE_INDEX, new String[] {Double.toString(stockTradeUtil.getGBCEAllShareIndex())});
    }
    
    /**
     * Runs the unit tests. If any of the tests fails, throws a {@link com.simplestocks.exceptions.TestFailedException}
     * 
     * @throws TestFailedException - if one of the tests has failed
     */
    private void runTests() throws TestFailedException {
        StockTradeTest stockTest = new StockTradeTest();
        
        Logger.log(Messages.MSG_TESTS_START);
        stockTest.testBusinessLogic();
        stockTest.testExceptionProcessing();
        
        Logger.log(Messages.MSG_TESTS_FINISH);
    }
    
    /**
     * Reads the stock symbol from the command prompt and compares it against the set of predefined
     * stock symbols; if the stock symbol is not recognized, the user is prompted to enter it again.
     * 
     * @return - the stock symbol
     */
    private String readStockSymbol() {
        Logger.promptInput(Messages.MSG_ENTER_STOCK_SYMBOL);
        
        Console console = System.console();
        String selection = console.readLine().trim();
        Set<String> stockSet = StockData.stockMap.keySet();
        String strStocks = Arrays.toString(stockSet.toArray(new String[stockSet.size()]));
        
        while (!stockSet.contains(selection)) {
            Logger.promptInput(Messages.ERR_STOCK_NOT_RECOGNIZED + strStocks + ": ");
            selection = console.readLine().trim();
        }
        
        return selection;
    }
    
    /**
     * This method reads a positive integer from the command line. If the user enters a wrong
     * value, he/she is prompted to enter data again.
     * 
     * @param prompt - the prompt, first displayed to the user
     * @param errorPrompt - the prompt, displayed to the user, if he/she enters bad data
     * @return - the positive integer that was entered
     */
    private int readPositiveInt(String prompt, String errorPrompt) {
        Logger.promptInput(prompt);
        
        Console console = System.console();
        String selection = console.readLine().trim();
        
        while (!StockTradeUtil.checkStrPositiveInt(selection)) {
            Logger.promptInput(errorPrompt);
            selection = console.readLine().trim();
        }
        
        return Integer.parseInt(selection);
    }
    
    /**
     * This method asks the user for a confirmation to quit, expecting a reply "y" or "n".
     * If the user enters a different value, he/she is prompted to enter data again.
     * 
     * @return - true if the user has entered "y", false if the user has entered "n"
     */
    private boolean confirmQuit() {
        Logger.log(Messages.MSG_CONFIRM_QUIT);
        
        Console console = System.console();
        String selection = console.readLine().trim();
        
        while (!selection.equals(YES) && !selection.equals(NO)) {
            Logger.promptInput(Messages.MSG_ENTER_Y_N);
            selection = console.readLine().trim();
        }
        
        return selection.equals(YES);
    }
    
    /**
     * This method reads the user's menu selection. If the user input is not recognized,
     * he/she is prompted for input again.
     * 
     * @return - the user's menu choice
     */
    private String readSelection() {
        Logger.promptInput(Messages.MSG_ENTER_SELECT);
        
        Console console = System.console();
        String selection = console.readLine();
        
        while (!isValidSelection(selection)) {
            Logger.promptInput(Messages.ERR_SELECT_UNKNOWN);
            Logger.log(Messages.MENU);
            selection = console.readLine();
        }
        
        return selection;
    }
    
    /**
     * Checks if the user's menu choice is valid by comparing it to a set of predefined menu choices.
     * 
     * @param selection - the user's menu selection.
     * @return - true, if the user has made a valid selection, false otherwise
     */
    private boolean isValidSelection(String selection) {
        if (selection == null) {
            return false;
        }
        
        String strTrim = selection.trim();
        return strTrim.equals(BUY)
                || strTrim.equals(DIV_YIELD)
                || strTrim.equals(HELP)
                || strTrim.equals(PE_RATIO)
                || strTrim.equals(QUIT)
                || strTrim.equals(SELL)
                || strTrim.equals(TEST)
                || strTrim.equals(VOL_WGH)
                || strTrim.equals(ALL_SHARE_INDEX);
    }
}
