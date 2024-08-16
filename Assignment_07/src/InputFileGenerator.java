import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This class is responsible for generating a text file filled with random stock commands.
 * It supports generating 'ADD', 'SEARCH', 'REMOVE', and 'UPDATE' commands for stock entries.
 * The generated file is used as input for testing the main stock processing application.
 */
public class InputFileGenerator {

    private static final String[] OPERATIONS = {"ADD", "SEARCH", "REMOVE", "UPDATE"};
    private static final int SYMBOL_LENGTH = 5;

    /**
     * The main method that executes the file generation process.
     * @param args Command line arguments are not used in this application.
     */
    public static void main(String[] args) {
        int numberOfCommands = 1000; // Total commands to generate
        String outputFile = "input.txt"; // Output file name

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Random random = new Random();
            for (int i = 0; i < numberOfCommands; i++) {
                int opIndex = random.nextInt(OPERATIONS.length);
                String operation = OPERATIONS[opIndex];
                String symbol = generateRandomSymbol(random, SYMBOL_LENGTH);
                String newSymbol = generateRandomSymbol(random, SYMBOL_LENGTH); // New symbol for UPDATE

                switch (operation) {
                    case "ADD":
                        double addPrice = 50 + (1500 - 50) * random.nextDouble(); // Random price between 50 and 1500
                        long addVolume = 100000 + random.nextInt(1000000); // Random volume between 100,000 and 1,100,000
                        long addMarketCap = 500000000 + random.nextInt(2000000000); // Random market cap between 500M and 2.5B
                        writer.write(String.format("%s %s %.2f %d %d\n", operation, symbol, addPrice, addVolume, addMarketCap));
                        break;
                    case "UPDATE":
                        double updatePrice = 50 + (1500 - 50) * random.nextDouble(); // Random price for update
                        long updateVolume = 100000 + random.nextInt(1000000); // Random volume for update
                        long updateMarketCap = 500000000 + random.nextInt(2000000000); // Random market cap for update
                        writer.write(String.format("%s %s %s %.2f %d %d\n", operation, symbol, newSymbol, updatePrice, updateVolume, updateMarketCap));
                        break;
                    case "SEARCH":
                    case "REMOVE":
                        writer.write(String.format("%s %s\n", operation, symbol));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a random stock symbol of a specified length using uppercase letters.
     * @param random The Random instance to use for generating random values.
     * @param length The length of the symbol to generate.
     * @return A string representing a random stock symbol.
     */
    private static String generateRandomSymbol(Random random, int length) {
        return random.ints('A', 'Z' + 1)
                     .limit(length)
                     .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                     .toString();
    }
}

