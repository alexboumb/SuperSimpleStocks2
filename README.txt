This application represents a sample stock market. This application was compiled with Java version 1.8.0_73.
The root SuperSimpleStocks folder has the following subfolders:

bin: compiled Java classes (Java version 1.8.0_73)
src: Java sources
doc: javadoc documentation
.settings: Eclipse project settings
.classpath, .project: Eclipse project files
README.txt: this file

1. Implementation

The application is separated into several modules:

1)   data: classes, containing the information about stocks and stock trade operations
2)   resources: static resources like messages to be displayed
3)   validation: classes, performing input data validation
4)   test: classes, linked to unit testing
5)   exceptions: exception classes
6)   logging: logging functionality
7)   business: classes, responsible for implementing the business logic

1.1 Data implementation

The base class of the data module is the Stock class. It is an abstract POJO class, which contains the following data:

i.   stock symbol
ii.  stock type (common or preferred)
iii. last dividend
iv.  fixed dividend (used for preferred stocks only)
v.   par value.

It also has methods for calculating the dividend yield (abstract), and the P/E ratio based on the price.
The P/E ratio is calculated as the ratio of price to dividend. Initially I got confused about what
dividend was meant: last dividend or fixed dividend. I decided that it must be the last dividend, but
then a problem arises: what to do if the last dividend is zero? I have decided to throw a business
exception which would state that it is impossible to calculate the P/E ratio if the dividend is zero.

Common and preferred stocks have different ways of calculating the dividend yield, that's why Stock has 2 child
classes: CommonStock and PreferredStock, each of which calculates the dividend yield in its own way. The data
module also contains an enum StockType for the two stock types: common and preferred. The class StockData contains
a static HashMap, containing the predefined stocks and the stock symbols as String constants. Finally, the class
StockTrade represents a single stock trade transaction, containing the following information:

i.   stock symbol
ii.  timestamp of the transaction
iii. quantity of stocks bought/sold
iv.  price at which the stock was traded
v.   the type of transaction (buy or sell)

The setter methods in StockTrade and StockData perform its own validation, in addition to the validation by the
classes in the business module (see below). The reason is that if the business module forgets to filter some
bad values, to have a "last line of defense" and not allow bad data to enter the application. In fact, for extra
security, the data is validated in 3 tiers: right, after user entry, before the business method got a chance to
execute, in the business method and in the data class. The only difference is that if there is bad data, the data
class throws "IllegalArgumentException" (unchecked), as the error is unexpected, since it should have been filtered
by the business logic. The business logic validation, on the other hand, throws a "BusinessException" (checked), since
it expects that the user might enter incorrect data; thus the user of the business logic must be aware that incorrect
data might be entered and should react to it.

1.2 Resources implementation

The resources module contains a single class, that contains the displayed messages as constants. This is done so that
the same message could be reused in multiple places. It contains error messages, displayed to the user, data prompts,
records of performed operations and the menu displayed in the console prompt.

1.3 Validation implementation

The validation module contains a class to perform validation of user input. This class validates the correctness
of data, so that the application receives only correct data. The following criteria are used for validation:

i.    stock symbol - must not be null or empty
ii.   last dividend - must not be negative (can be zero)
iii.  fixed dividend - must not be negative (can be zero)
iv.   par value - must not be negative or zero
v.    price - must not be negative or zero
vi.   stock type - must not be empty (null)
vii.  stock object - must not be null
viii. timestamp - must not be null
ix.   quantity - must not be negative or zero

Each field has its own method to perform the validation. These methods rely on generic methods for validation:
validateNullEmptyString, validateNegativeInt, validateNegativeZeroInt, validateNegativeDouble. The reason to
create validation methods that call the generic methods for each field, instead of calling the generic methods
directly where validation is needed, is to allow in the future additional validation logic to be added to the
field validation methods.

The validation methods are used in three places: user input, business methods and data. The application validates
user input and prompts the user to reenter the data if the format of the data is incorrect. The business
methods also validate the input arguments, in case the application fails to validate user input. If a business
method receives incorrect input, it throws a BusinessException, which should be caught by the calling class.
Additionally, the data classes also validate the data in their setter methods as a last line of defense.

1.4 Test implementation

The test module is used to unit test the application. The package contains a test class StockTraderTest, which
includes tests for business logic and for exception processing. The business logic test tests whether the result
from operations like dividend yield, P/E ratio, Volume Weighted Stock Price and All Share Index correspond to the
expected values.

The exception processing test tests if an erroneous input, like empty stock symbol, will result
in an exception. The exception processing test tests the creation of objects, like CommonStock and PreferredStock,
as well as operations like retrieving the dividend yield, the P/E ratio, Volume Weighted Stock Price and All Share
Index. The test attempts to put a wrong value in the place of a field (like a negative price), then it checks if
an exception has been generated with the specific message.

If a test fails, the test method throws TestFailedException, which stops the test execution. The test class
StockTraderTest includes a main method to run the tests directly from the class, but the tests can also be executed
from the command prompt menu (see below).

1.5 Exceptions Implementation

The exceptions module includes 2 exceptions: BusinessException, which is thrown in case of business errors, like invalid
user data and TestFailedException, which is thrown if a unit test fails.

1.6 Logging Implementation

The logging class includes functionality for logging messages. It includes methods for logging operations, prompting for
input, error logging and logging with parameters. The latter functionality allows to log preformatted strings with generic
parameters that are replaced by user parameters. For example, the P/E ratio message string is defined in the following
format:

The P/E ratio for stock P0 is P1 given price P2

Before being displayed to the user, the parameters P0 and P1 are replaced with concrete values:

The P/E ratio for stock POP is 12.5 given price 100

Before being displayed to the user, the generic parameters

1.7 Business logic implementation

The business module contains a single class StockTradeUtil, which implements the business logic for calculating the
dividend yield, the P/E ratio, Volume Weighted Stock Price and All Share Index. It also has methods for recording
a stock trade (buy or sell); the data is kept in memory and it includes information about the stock symbol, timestamp,
quantity, price and type (buy or sell). This information is later used to calculate the Volume Weighted Stock Price
for a particular stock and the All Share Index. The StockTradeUtil includes the following public methods for performing
stock operations:

i.   getDividendYield - returns the stock dividend yield, based on the provided price
ii.  getPERatio - returns the stock price/earnings ratio, based on the provided price
iii. tradeStock - performs a trade, recording information like stock symbol, timestamp, quantity, price and information
     whether the transaction is buy or sell
iv.  getVolumeWeightedStockPrice - returns the Volume Weighted Stock Price from transactions during the last 5 minutes
v.   getGBCEAllShareIndex - returns a geometric average of all Volume Weighted Stock Prices

The application includes a console interface for running and testing. It displays the following menu to the user when started:

Welcome to the Simple Stock Market.
d - calculate dividend yield
p - calculate price/earnings ratio
b - buy stocks
s - sell stocks
v - calculate Volume Weighted Stock Price from transactions during the last 5 minutes
a - calculate the All Share Index
t - run unit tests
h - print the menu
q - quit the application

Please enter your selection:

The user is prompted to make a selection, after making the selection, the user is prompted for input parameters (see detailed
description of each operation below). Before executing each operation, the data is validated for correctness. If the provided
data is incorrect (negative or zero price for example), a BusinessException is thrown, which is expected to be caught and
processed by the calling class; the user is prompted to enter the data again. The StockTrader class includes a main method to
run the stock operations directly from the class, but the operations can also be executed from the command prompt menu (see below).

Below is a description of every operation: the parameters, valid input values and result

i. getDividendYield - returns the stock dividend yield, based on the provided price

The getDividendYield method accepts two parameters as user input: 
  -stockSymbol: String, not null, not empty, must be one of the following: TEA, POP, ALE, GIN, JOE
  -price: integer, positive

Example output:

Please enter your selection: d
Enter stock symbol: YYY
Stock symbol not recognized. Please enter one of the following [POP, TEA, JOE, ALE, GIN]: POP
Enter price: -10
Price must be a positive integer. Please try again: 120
The dividend yield for stock POP is 0.06666666666666667 given price 120

The resulting output is the following (example): The dividend yield for stock POP is 0.08 given price 100

ii. getPERatio - returns the stock price/earnings ratio, based on the provided price

The getPERatio method accepts two parameters as user input: 
  -stockSymbol: String, not null, not empty, must be one of the following: TEA, POP, ALE, GIN, JOE
  -price: integer, positive
  
Example output:

Please enter your selection: p
Enter stock symbol: POP
Enter price: 120
The P/E ratio for stock POP is 15.0 given price 120

iii. tradeStock - performs a trade, recording information like stock symbol, timestamp, quantity, price and information
     whether the transaction is buy or sell
     
There are two varieties: buy and sell. The tradeStock method accept three parameters as user input:
  -stockSymbol: String, not null, not empty, must be one of the following: TEA, POP, ALE, GIN, JOE
  -quantity: integer, positive
  -price: integer, positive

Example output:

Please enter your selection: b
Enter stock symbol: TEA
Enter price: 120
Enter quantity: 10
Bought 10 shares of TEA at price 120

Please enter your selection: s
Enter stock symbol: POP
Enter price: 150
Enter quantity: 20
Sold 20 shares of POP at price 150
  
iv. getVolumeWeightedStockPrice - returns the Volume Weighted Stock Price from transactions during the last 5 minutes

The getVolumeWeightedStockPrice method accepts one parameter as user input:
  -stockSymbol: String, not null, not empty, must be one of the following: TEA, POP, ALE, GIN, JOE

Example output:

Please enter your selection: v
Enter stock symbol: TEA
The volume weighted stock price for TEA from transactions during the last 5 minutes is 120.0

v. getGBCEAllShareIndex - returns a geometric average of all Volume Weighted Stock Price

The getGBCEAllShareIndex method does not have any parameters.

Example output:

Please enter your selection: a
The All Share Index is 134.16407864998737

Alternatively, the user can run a predefined set of operations to check that the application functions correctly and
without errors or exceptions:

java com.supersimplestocks.business.StockTradeUtil
The dividend yield for stock TEA is 0.0 given price 50
The dividend yield for stock POP is 0.125 given price 64
The dividend yield for stock GIN is 0.04 given price 50
The P/E ratio for stock ALE is 3.0 given price 69
The P/E ratio for stock POP is 8.0 given price 64
Sold 20 shares of TEA at price 60
Bought 30 shares of TEA at price 120
Bought 10 shares of GIN at price 60
Sold 20 shares of GIN at price 150
The volume weighted stock price for TEA based on trades in the past 5 minutes is 96.0
The volume weighted stock price for GIN based on trades in the past 5 minutes is 120.0
The All Share Index is 107.33126291998991
Sleeping 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price is calculated based on trades only in the past 5 minutes
Sold 10 shares of TEA at price 80
Bought 10 shares of TEA at price 60
Bought 10 shares of GIN at price 60
Sold 20 shares of GIN at price 120
The volume weighted stock price for TEA from transactions during the last 5 minutes is 70.0
The volume weighted stock price for GIN from transactions during the last 5 minutes is 100.0
The All Share Index is 83.66600265340756

The unit tests can also be run directly, without invoking the command interface:

java com.supersimplestocks.test.StockTradeTest
Starting tests
Sleeping 6 minutes to ensure that in the subsequent transactions the Volume Weighted Stock Price is calculated based on trades only in the past 5 minutes
All tests have finished successfully

2. Documentation

Apart from the README file, the application also includes javadoc, located in the doc folder
