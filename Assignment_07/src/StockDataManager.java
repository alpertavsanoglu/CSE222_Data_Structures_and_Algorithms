/**
 * The StockDataManager class manages a collection of stocks using an AVL tree for efficient data retrieval and updates.
 * It provides methods to add, update, remove, and search for stocks based on their symbol.
 */
public class StockDataManager {
    private AVLTree avlTree; // AVLTree instance to hold and manage Stock objects

    /**
     * Constructor for creating a new StockDataManager.
     * Initializes an AVLTree to store and manage Stock objects.
     */
    public StockDataManager() {
        avlTree = new AVLTree();
    }

    /**
     * Adds a new stock or updates an existing one in the AVL tree.
     * If the stock already exists, its details are updated. If it does not exist, a new stock is created and added.
     * 
     * @param symbol The stock symbol.
     * @param price The price of the stock.
     * @param volume The trading volume of the stock.
     * @param marketCap The market capitalization of the stock.
     */
    public void addOrUpdateStock(String symbol, double price, long volume, long marketCap) {
        Stock existingStock = avlTree.search(symbol);
        if (existingStock != null) {
            // Update existing stock details if the stock already exists
            existingStock.setPrice(price);
            existingStock.setVolume(volume);
            existingStock.setMarketCap(marketCap);
        } else {
            // Create and insert a new stock if it does not exist
            Stock newStock = new Stock(symbol, price, volume, marketCap);
            avlTree.insert(newStock);
        }
    }

    /**
     * Removes a stock from the AVL tree based on its symbol.
     * 
     * @param symbol The stock symbol to remove.
     */
    public void removeStock(String symbol) {
        avlTree.delete(symbol);
    }

    /**
     * Searches for a stock in the AVL tree based on its symbol.
     * 
     * @param symbol The symbol of the stock to find.
     * @return The Stock object if found, or null if the stock does not exist.
     */
    public Stock searchStock(String symbol) {
        return avlTree.search(symbol);
    }

    /**
     * Updates the details of an existing stock. If the stock does not exist, no action is taken.
     * 
     * @param symbol The symbol of the stock to update.
     * @param newPrice The new price of the stock.
     * @param newVolume The new trading volume of the stock.
     * @param newMarketCap The new market capitalization of the stock.
     */
    public void updateStock(String symbol, double newPrice, long newVolume, long newMarketCap) {
        Stock stock = avlTree.search(symbol);
        if (stock != null) {
            // Update stock details if the stock is found
            stock.setPrice(newPrice);
            stock.setVolume(newVolume);
            stock.setMarketCap(newMarketCap);
        }
    }

    /**
     * Main method for testing the functionality of the StockDataManager.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        StockDataManager manager = new StockDataManager();
        manager.addOrUpdateStock("AAPL", 150.0, 1000000, 2500000000L);
        manager.addOrUpdateStock("GOOGL", 2800.0, 500000, 1500000000L);
        System.out.println(manager.searchStock("AAPL")); // Expected to print details of AAPL stock
        manager.removeStock("AAPL");
        System.out.println(manager.searchStock("AAPL")); // Expected to print null
    }
}
