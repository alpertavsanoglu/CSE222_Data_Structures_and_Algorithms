import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Main class for managing the social network system via command-line interface.
 */
public class Main {
	private static SocialNetworkGraph network = new SocialNetworkGraph();
	private static Scanner scanner = new Scanner(System.in);
/**
 * Entry point of the application. Initializes demo data and starts the user interaction loop.
 *
 * @param args Command line arguments
 */
	public static void main(String[] args) {

		//initializeDemoData();							// Load initial data for demonstration purposes
		while (true) {
			displayMenu();							// Show user options
			String choice = scanner.nextLine().trim();			// Take input from user
			switch (choice) {
				case "1":
					addPerson();					// 1. Add Person
				break;
				case "2":
					removePerson();					// 2. Remove Person
				break;
				case "3":
					addFriendship();				// 3. Add Friendship
				break;
				case "4":
					removeFriendship();				// 4. Remove Friendship
				break;
				case "5":
					findShortestPath();				// 5. Find Shortest Path
				break;
				case "6":
					suggestFriends();				//6. Suggest Friends
				break;
				case "7":
					countClusters();				// 7. Count Clusters
				break;
				case "8":
					exitApplication();				//8. Exit
				break;
				case "9":
					network.listAllPersons();			//9. List All Persons
				break;
				case "10":
					network.listAllFriendships();			// 10.List All Friendships
				break;
				default:
					System.out.println("Invalid option. Please try again.");	//invalid option
			}
		}
	}
/**
 * Displays the main menu of the application.
 */
	private static void displayMenu() {
		System.out.println("\n===== Social Network Analysis Menu =====");
		System.out.println("1. Add Person");
		System.out.println("2. Remove Person");
		System.out.println("3. Add Friendship");
		System.out.println("4. Remove Friendship");
		System.out.println("5. Find Shortest Path");
		System.out.println("6. Suggest Friends");
		System.out.println("7. Count Clusters");
		System.out.println("8. Exit");
		System.out.println("9. List All Persons");
		System.out.println("10.List All Friendships");
		System.out.print("Please select an option: ");
	}
/**
 * Initializes demonstration data in the social network.
 */
	private static void initializeDemoData() {
        	LocalDateTime now = LocalDateTime.now();					// Predefined timestamps for consistency in demonstration
		// Adding some people for demonstration
		//	network.addPerson("Ali",24, Arrays.asList("cod"));
		//	network.addPerson("Alper Tavsan",24, Arrays.asList("coding", "hiking"));
		network.addPerson("John Doe", 25, Arrays.asList("reading", "hiking", "cooking"));
		network.addPerson("Jane Smith", 22, Arrays.asList("swimming", "cooking"));
		network.addPerson("Alice Johnson", 27, Arrays.asList("hiking", "painting"));
		network.addPerson("Bob Brown", 30, Arrays.asList("reading", "swimming"));
		network.addPerson("Emily Davis", 28, Arrays.asList("running", "swimming"));
		network.addPerson("Frank Wilson", 26, Arrays.asList("reading", "hiking"));
		// Add some friendships
		network.addFriendship("John Doe", now, "Jane Smith", now);
		network.addFriendship("John Doe", now, "Alice Johnson", now);
		network.addFriendship("Jane Smith", now, "Bob Brown", now);
		network.addFriendship("Emily Davis", now, "Frank Wilson", now);
		//	network.addFriendship("Alper Tavsan", now, "Bob Brown", now);
		//find shortest path
		//	network.findShortestPath("John Doe", now, "Alper Tavsan", now);
		network.findShortestPath("John Doe", now, "Bob Brown", now);
		network.countClusters();
	}
/**
 * Handles the addition of a person to the network.
 */
	private static void addPerson() {
		System.out.print("Enter name: ");
		String name = scanner.nextLine().trim();					// Take name from user
		if (name.isEmpty()) {
			System.out.println("Name cannot be empty.");				// error check
			return;
		}
		System.out.print("Enter age: ");
		int age;
		try {
			age = Integer.parseInt(scanner.nextLine());				// Take age from user
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input for age. Please enter a valid integer.");
			return;
		}
		System.out.print("Enter hobbies (comma-separated): ");
		String hobbiesInput = scanner.nextLine().trim();				// Take hobbies from user
		if (hobbiesInput.isEmpty()) {
			System.out.println("Hobbies entered empty");
		}
		List<String> hobbies = Arrays.asList(hobbiesInput.split("\\s*,\\s*"));		//comma-seperated
		network.addPerson(name, age, hobbies);
	}
/**
 * Handles the removal of a person from the network based on name and timestamp.
 */
	private static void removePerson() {
		System.out.print("Enter name: ");						// Take name from user
		String name = scanner.nextLine().trim();
		System.out.print("Enter timestamp: ");						// Take timestamp from user
		LocalDateTime timestamp = parseTimestamp();
		if (timestamp != null) {
			network.removePerson(name, timestamp);
		}
	}
/**
 * Adds a friendship between two persons identified by name and timestamp.
 */
	private static void addFriendship() {
		System.out.print("Enter first person's name: ");				// Take name from user
		String name1 = scanner.nextLine().trim();
		System.out.print("Enter first person's timestamp: ");				// Take timestamp from user
		LocalDateTime timestamp1 = parseTimestamp();
		System.out.print("Enter second person's name: ");				// Take name from user
		String name2 = scanner.nextLine().trim();
		System.out.print("Enter second person's timestamp: ");				// Take timestamp from user
		LocalDateTime timestamp2 = parseTimestamp();
		if (timestamp1 != null && timestamp2 != null) {
			network.addFriendship(name1, timestamp1, name2, timestamp2);
		}
	}
/**
 * Removes a friendship between two persons based on their names and timestamps.
 */
	private static void removeFriendship() {
		System.out.print("Enter first person's name: ");				// Take name from user
		String name1 = scanner.nextLine().trim();
		System.out.print("Enter first person's timestamp: ");				// Take timestamp from user
		LocalDateTime timestamp1 = parseTimestamp();
		System.out.print("Enter second person's name: ");				// Take name from user
		String name2 = scanner.nextLine().trim();
		System.out.print("Enter second person's timestamp: ");				// Take timestamp from user
		LocalDateTime timestamp2 = parseTimestamp();
		if (timestamp1 != null && timestamp2 != null) {
			network.removeFriendship(name1, timestamp1, name2, timestamp2);
		}
	}
/**
 * Initiates the process to find the shortest path between two persons in the network.
 */
	private static void findShortestPath() {
		System.out.print("Enter first person's name: ");				// Take name from user
		String startName = scanner.nextLine().trim();
		System.out.print("Enter first person's timestamp: ");				// Take timestamp from user
		LocalDateTime startTimestamp = parseTimestamp();
		System.out.print("Enter second person's name: ");				// Take name from user
		String endName = scanner.nextLine().trim();
		System.out.print("Enter second person's timestamp: ");				// Take timestamp from user
		LocalDateTime endTimestamp = parseTimestamp();
		if (startTimestamp != null && endTimestamp != null) {
			network.findShortestPath(startName, startTimestamp, endName, endTimestamp);
		}
	}
/**
 * Suggests potential friends for a person based on mutual friends and common hobbies.
 */
	private static void suggestFriends() {
		System.out.print("Enter person's name: ");					// Take name from user
		String name = scanner.nextLine().trim();
		System.out.print("Enter person's timestamp: ");					// Take timestamp from user
		LocalDateTime timestamp = parseTimestamp();
		if (timestamp == null) {
			System.out.println("Failed to parse timestamp. Please ensure it's in the correct format and try again.");		//error check
			return; // Early return if the timestamp is invalid
		}
		System.out.print("Enter maximum number of friends to suggest: ");		// Take number of suggest from user
		int maxSuggestions = 0;
		try {
			maxSuggestions = Integer.parseInt(scanner.nextLine().trim());
			if (maxSuggestions < 1) {
				System.out.println("Number of friends to suggest must be at least 1.");						//error check
				return; // Early return if the number of friends to suggest is less than 1
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input for the number of friends to suggest. Please enter a valid integer.");		//error check
			return; // Early return if the input is not a valid integer
		}
		network.suggestFriends(name, timestamp, maxSuggestions);
	}
/**
 * Counts the number of distinct clusters of connected individuals in the network.
 */
	private static void countClusters() {
		network.countClusters();
	}
/**
 * Exits the application, closing any open resources.
 */
	private static void exitApplication() {
		System.out.println("Exiting...");
		scanner.close();
		System.exit(0);
	}
/**
 * Parses a string input into a LocalDateTime object with error handling.
 * @return LocalDateTime object or null if the input is invalid.
 */
	private static LocalDateTime parseTimestamp() {
		String timestampInput = scanner.nextLine().trim();
		try {
			return LocalDateTime.parse(timestampInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		catch (DateTimeParseException e) {
			System.out.println("Invalid timestamp format. Please use 'yyyy-MM-dd HH:mm:ss'");
			return null;
		}
	}
}

