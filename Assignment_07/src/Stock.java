/**
 * The Stock class represents a financial stock entity with properties such as symbol, price, volume, and market capitalization.
 * This class provides a simple model for storing stock data and performing operations related to stock information management.
 */
public class Stock {
    private String symbol;  // The stock's ticker symbol
    private double price;   // The current price per share of the stock
    private long volume;    // The number of shares traded
    private long marketCap; // The total market value of the company's outstanding shares

    /**
     * Constructs a new Stock object with the specified details.
     *
     * @param symbol    The stock's ticker symbol.
     * @param price     The current price per share of the stock.
     * @param volume    The number of shares traded.
     * @param marketCap The total market value of the company's outstanding shares.
     */
    public Stock(String symbol, double price, long volume, long marketCap) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    /**
     * Gets the stock symbol.
     *
     * @return The stock's ticker symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the stock symbol.
     *
     * @param symbol The stock's ticker symbol.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the price of the stock.
     *
     * @return The current price per share.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the stock.
     *
     * @param price The new price per share.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the trading volume of the stock.
     *
     * @return The number of shares traded.
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Sets the trading volume of the stock.
     *
     * @param volume The new number of shares traded.
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

    /**
     * Gets the market capitalization of the stock.
     *
     * @return The total market value of the company's outstanding shares.
     */
    public long getMarketCap() {
        return marketCap;
    }

    /**
     * Sets the market capitalization of the stock.
     *
     * @param marketCap The new market value of the company's outstanding shares.
     */
    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    /**
     * Returns a string representation of the Stock object.
     * 
     * @return A string that contains the stock's symbol, price, volume, and market capitalization.
     */
    @Override
    public String toString() {
        return "Stock [symbol=" + symbol + ", price=" + price + ", volume=" + volume + ", marketCap=" + marketCap + "]";
    }
}
