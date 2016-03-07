package com.supersimplestocks.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that contains sample data for several stocks to be used in the stock trade
 */
public class StockData {
    public static final Map<String, Stock> stockMap = new HashMap<String, Stock>();
    
    public static final String TEA = "TEA";
    public static final String POP = "POP";
    public static final String ALE = "ALE";
    public static final String GIN = "GIN";
    public static final String JOE = "JOE";
    public static final String NAN = "NAN";
    
    static {
        stockMap.put(TEA, new CommonStock(TEA, 0, 100));
        stockMap.put(POP, new CommonStock(POP, 8, 100));
        stockMap.put(ALE, new CommonStock(ALE, 23, 60));
        stockMap.put(GIN, new PreferredStock(GIN, 8, 0.02, 100));
        stockMap.put(JOE, new CommonStock(JOE, 13, 250));
    }
}
