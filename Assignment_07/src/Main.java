import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class manages the main entry point of the program and handles the processing
 * of stock commands from an input file, as well as the visualization of stock operation
 * performance.
 */
public class Main {

    /**
     * The main method starts the application. It processes commands from the input file,
     * performs performance analysis, and initializes GUI for visualizing the results.
     * 
     * @param args Command line arguments where args[0] is the path to the input file.
     */
    public static void main(String[] args) {
        String inputFile = args[0]; // Input file path from command line arguments
        StockDataManager manager = new StockDataManager(); // Instance of StockDataManager
        processInputFile(inputFile, manager); // Process commands from the input file

        // Lists for storing performance data points for different operations
        List<Long> dataPointsYSearch = new ArrayList<>();
        List<Long> dataPointsYRemove = new ArrayList<>();
        List<Long> dataPointsYUpdate = new ArrayList<>();
        List<Long> dataPointsYAdd = new ArrayList<>();
        List<Integer> dataPointsX = new ArrayList<>();

        // Generate data points for performance visualization
        generateDataPoints(manager, dataPointsX, dataPointsYSearch, dataPointsYRemove, dataPointsYUpdate, dataPointsYAdd);

        // Create and display GUI frames for each operation performance visualization
        SwingUtilities.invokeLater(() -> {
            GUIVisualization frameSearch = new GUIVisualization("scatter", dataPointsX, dataPointsYSearch);
            frameSearch.setTitle("Search Time Visualization");
            frameSearch.setVisible(true);

            GUIVisualization frameRemove = new GUIVisualization("scatter", dataPointsX, dataPointsYRemove);
            frameRemove.setTitle("Remove Time Visualization");
            frameRemove.setVisible(true);

            GUIVisualization frameUpdate = new GUIVisualization("scatter", dataPointsX, dataPointsYUpdate);
            frameUpdate.setTitle("Update Time Visualization");
            frameUpdate.setVisible(true);

            GUIVisualization frameAdd = new GUIVisualization("scatter", dataPointsX, dataPointsYAdd);
            frameAdd.setTitle("Add Time Visualization");
            frameAdd.setVisible(true);
        });
    }

    /**
     * Processes the input file containing stock commands and delegates command processing
     * to the StockDataManager.
     * 
     * @param inputFile The path of the file containing stock commands.
     * @param manager The manager handling stock data operations.
     */
    private static void processInputFile(String inputFile, StockDataManager manager) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) { // Open the file for reading
            String line;
            while ((line = br.readLine()) != null) { // Read each line until the end of the file
                processCommand(line, manager); // Process each line as a command
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an I/O exception occurs
        }
    }

    /**
     * Processes a single line command from the input file and executes stock operations
     * such as add, remove, search, or update based on the command type.
     * 
     * @param line The line from the input file containing a stock command.
     * @param manager The manager that executes stock operations.
     */
    private static void processCommand(String line, StockDataManager manager) {
        String[] tokens = line.split("\\s+"); // Split the line into tokens by whitespace
        String command = tokens[0].toUpperCase(); // Standardizing command to uppercase
        switch (command) { // Execute different operations based on the command type
            case "ADD":
                // Add or update a stock entry with symbol, price, volume, and market cap
                manager.addOrUpdateStock(tokens[1], Double.parseDouble(tokens[2]), Long.parseLong(tokens[3]), Long.parseLong(tokens[4]));
                System.out.println("Stock Added: " + tokens[1]);
                break;
            case "REMOVE":
                // Remove a stock entry based on its symbol
                manager.removeStock(tokens[1]);
                System.out.println("Stock removed: " + tokens[1]);
                break;
            case "SEARCH":
                // Search for a stock by symbol and print the result
                Stock stock = manager.searchStock(tokens[1]);
                System.out.println((stock != null) ? stock : "Stock not found: " + tokens[1]);
                break;
            case "UPDATE":
                // Update an existing stock entry with new price, volume, and market cap
                manager.updateStock(tokens[1], Double.parseDouble(tokens[3]), Long.parseLong(tokens[4]), Long.parseLong(tokens[5]));
                System.out.println( "Stock Updated: " + tokens[1]);
                break;
            default:
                // Handle unrecognized commands
                System.out.println("Invalid Command");
                break;
        }
    }

    /**
     * Generates performance data points for stock operations. This method simulates
     * repeated operations to gather average times for adding, searching, removing, and updating stocks.
     * 
     * @param manager The StockDataManager managing the stock operations.
     * @param dataPointsX The x-coordinates for plotting (representing operation count).
     * @param dataPointsYSearch The y-coordinates for search time data points.
     * @param dataPointsYRemove The y-coordinates for remove time data points.
     * @param dataPointsYUpdate The y-coordinates for update time data points.
     * @param dataPointsYAdd The y-coordinates for add time data points.
     */
    private static void generateDataPoints(StockDataManager manager, List<Integer> dataPointsX, List<Long> dataPointsYSearch, List<Long> dataPointsYRemove, List<Long> dataPointsYUpdate, List<Long> dataPointsYAdd) {
        for (int i = 1; i <= 100; i++) { // Perform 100 iterations
            dataPointsX.add(i * 100); // Increase the operation count incrementally
            // Measure the time for each operation and add to respective lists
            long addTime = performPerformanceAnalysis(manager, 100, "ADD");
            long searchTime = performPerformanceAnalysis(manager, 100, "SEARCH");
            long removeTime = performPerformanceAnalysis(manager, 100, "REMOVE");
            long updateTime = performPerformanceAnalysis(manager, 100, "UPDATE");
            dataPointsYSearch.add(searchTime);
            dataPointsYRemove.add(removeTime);
            dataPointsYUpdate.add(updateTime);
            dataPointsYAdd.add(addTime);
        }
    }

    /**
     * Measures the performance of a specified stock operation by simulating it multiple times
     * and recording the average time taken.
     * 
     * @param manager The StockDataManager used to perform operations.
     * @param count The number of times to repeat the operation for averaging.
     * @param operation The type of operation to measure (ADD, SEARCH, REMOVE, UPDATE).
     * @return The average time taken for the operation in nanoseconds.
     */
    private static long performPerformanceAnalysis(StockDataManager manager, int count, String operation) {
        Random random = new Random(); // Random instance for generating random stock data
        long totalTime = 0; // Total time for all operations
        for (int i = 0; i < count; i++) { // Repeat the operation 'count' times
            // Generate random stock data
            String symbol = generateRandomSymbol(random);
            double price = 100 * random.nextDouble();
            long volume = (long) (1000000 * random.nextDouble());
            long marketCap = (long) (10000000 * random.nextDouble());

            long startTime = System.nanoTime(); // Start time measurement
            // Perform the operation based on the specified type
            switch (operation) {
                case "ADD":
                    manager.addOrUpdateStock(symbol, price, volume, marketCap);
                    break;
                case "SEARCH":
                    manager.searchStock(symbol);
                    break;
                case "REMOVE":
                    manager.removeStock(symbol);
                    break;
                case "UPDATE":
                    manager.updateStock(symbol, price, volume, marketCap); // Assume updates are always successful
                    break;
            }
            totalTime += System.nanoTime() - startTime; // Calculate the total elapsed time
        }
        return totalTime / count; // Return the average time
    }

    /**
     * Generates a random stock symbol consisting of 6 uppercase letters.
     * 
     * @param random The Random instance used to generate random numbers.
     * @return A random stock symbol.
     */
    private static String generateRandomSymbol(Random random) {
        return random.ints('A', 'Z' + 1).limit(6)
                     .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                     .toString();
    }
}

