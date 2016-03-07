package com.supersimplestocks.data.resources;

/**
 * A resource class that contains the messages displayed to the user
 */
public class Messages {
    
    public static final String ERR_PE_DIVIDEND_ZERO = "Impossible to calculate P/E ratio: dividend is zero";
    
    public static final String ERR_STOCK_NULL = "Stock object cannot be null";
    
    public static final String ERR_STOCK_SYMBOL_NULL_EMPTY = "Stock symbol cannot be null or empty";
    
    public static final String ERR_LAST_DIVIDEND_NEGATIVE = "Last dividend cannot be negative";
    
    public static final String ERR_FIXED_DIVIDEND_NEGATIVE = "Fixed dividend cannot be negative";
    
    public static final String ERR_PAR_VALUE_NEGATIVE_ZERO = "Par value cannot be negative or zero";
    
    public static final String ERR_QUANTITY_NEGATIVE_ZERO = "Quantity cannot be negative or zero";
    
    public static final String ERR_PRICE_NEGATIVE_ZERO = "Price cannot be negative or zero";
    
    public static final String ERR_STOCK_TYPE_NULL = "Stock type cannot be null; it must be either Commmon or Preferred";
    
    public static final String ERR_TIMESTAMP_NULL = "Timestamp cannot be null";
    
    public static final String ERR_NO_STOCK_FOUND = "No stock found: ";
    
    public static final String ERR_NO_STOCK_DATA_FOUND = "No data found for stock ";
    
    public static final String ERR_DIVIDENT_YIELD_INCORRECT = " dividend yield incorrect";
    
    public static final String ERR_PE_RATIO_INCORRECT = " P/E ratio incorrect";
    
    public static final String ERR_VOL_WEIGHT_PRICE_INCORRECT = " Volume Weighted Stock Price incorrect";
    
    public static final String ERR_ALL_SHARE_INDEX_INCORRECT = " All Share Index incorrect";
    
    public static final String ERR_EXCEPTION_NOT_CAUGHT = "Exception not caught";
    
    public static final String MSG_WELCOME = "Welcome to the Simple Stock Market. ";
    
    public static final String MSG_ENTER_SELECT = "Please enter your selection: ";
    
    public static final String ERR_SELECT_UNKNOWN = "Selection unknown. Enter one of the following: ";
    
    public static final String MSG_CONFIRM_QUIT = "Are you sure you want to quit (y/n)?";
    
    public static final String MSG_ENTER_Y_N = "Selection not recongnized. Please enter y or n: ";
    
    public static final String MENU =
            "d - calculate dividend yield\n"
          + "p - calculate price/earnings ratio\n"
          + "b - buy stocks\n"
          + "s - sell stocks\n"
          + "v - calculate Volume Weighted Stock Price from transactions during the last 5 minutes\n"
          + "a - calculate the All Share Index\n"
          + "t - run unit tests\n"
          + "h - print the menu\n"
          + "q - quit the application\n";
    
    public static final String MSG_ENTER_STOCK_SYMBOL = "Enter stock symbol: ";
    
    public static final String ERR_STOCK_NOT_RECOGNIZED = "Stock symbol not recognized. Please enter one of the following ";
    
    public static final String MSG_ENTER_PRICE = "Enter price: ";
    
    public static final String ERR_PRICE_FORMAT = "Price must be a positive integer. Please try again: ";
    
    public static final String MSG_STOCK_DIVIDEND_YIELD = "The dividend yield for stock P0 is P1 given price P2";
    
    public static final String MSG_PRICE_EARNINGS_RATIO = "The P/E ratio for stock P0 is P1 given price P2";
    
    public static final String MSG_ENTER_QUANTITY = "Enter quantity: ";
    
    public static final String ERR_QUANTITY_FORMAT = "Quantity must be a positive integer. Please try again: ";
    
    public static final String MSG_BUY_STOCK = "Bought P0 shares of P1 at price P2";
    
    public static final String MSG_SELL_STOCK = "Sold P0 shares of P1 at price P2";
    
    public static final String MSG_VOL_WEIGH_PRICE = "The volume weighted stock price for P0 from transactions during the last 5 minutes is P1";
    
    public static final String MSG_ALL_SHARE_INDEX = "The All Share Index is P0";
    
    public static final String MSG_TESTS_START = "Starting tests";
    
    public static final String MSG_TESTS_FINISH = "All tests have finished successfully";
    
    //Sleep 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price is calculated
    //based on trades in the past 5 minutes
    public static final String MSG_SLEEP = "Sleeping 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price"
            + "is calculated based on trades only in the past 5 minutes";
}
