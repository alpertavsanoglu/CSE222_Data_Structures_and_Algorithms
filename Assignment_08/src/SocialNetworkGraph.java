import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the social network graph containing people and their friendships.
 */
public class SocialNetworkGraph {
	private Map<String, Person> people = new HashMap<>();
	private Map<Person, List<Person>> friendships = new HashMap<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Map<Person, LocalDateTime> personTimestamps = new HashMap<>();
/**
 * Adds a new person to the social network graph.
 * @param name the name of the person
 * @param age the age of the person
 * @param hobbies a list of the person's hobbies
 */
	public void addPerson(String name, int age, List<String> hobbies) {
		LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		Person newPerson = new Person(name, age, hobbies);
		String key = name + timestamp.format(formatter);					// Create a unique key based on name and timestamp
		//System.out.println("Key generated for new person: " + key); // Debug output
		people.put(key, newPerson);
		friendships.put(newPerson, new ArrayList<>());						// Initialize an empty list of friends
		personTimestamps.put(newPerson, timestamp);						// Storing timestamp in a separate map
		System.out.println("Person added: " + newPerson.getName() + " (Timestamp: " + timestamp.format(formatter) + ")");		// Print added message
	}
/**
 * Removes a person from the social network graph based on their name and timestamp.
 * @param name the name of the person to remove
 * @param timestamp the timestamp when the person was added
 */
	public void removePerson(String name, LocalDateTime timestamp) {
		String key = name + timestamp.truncatedTo(ChronoUnit.SECONDS).format(formatter);		// Create a unique key based on name and timestamp
		Person person = people.get(key);
		if (person != null) {
			friendships.values().forEach(friendsList -> friendsList.remove(person));	// Remove the person from all friendship lists
			friendships.remove(person);							// Remove the person 
			people.remove(key);
			System.out.println("Person removed: " + name + " (Timestamp: " + timestamp.format(formatter) + ")");	// Print removed message
		}
		else {
			System.out.println("Person not found: " + name + " at " + timestamp.format(formatter));			// Print person not found message
		}
	}
/**
 * Adds a friendship between two persons identified by their names and timestamps.
 * @param name1 the name of the first person
 * @param timestamp1 the timestamp of the first person
 * @param name2 the name of the second person
 * @param timestamp2 the timestamp of the second person
 */
	public void addFriendship(String name1, LocalDateTime timestamp1, String name2, LocalDateTime timestamp2) {
		Person person1 = findPersonByApproximateTimestamp(name1, timestamp1);
		Person person2 = findPersonByApproximateTimestamp(name2, timestamp2);
		if (person1 != null && person2 != null) {								//check persons
			friendships.get(person1).add(person2);
			friendships.get(person2).add(person1);
			System.out.println("Friendship added between " + name1 + " and " + name2);			// Print Friendship added message
		}
		else {
			System.out.println("One or both persons not found for Adding Friendship.");			// Print person not found message
		}
	}
/**
 * Finds a person in the network who matches the given name and is within a small time deviation of the given timestamp.
 * @param name the name of the person
 * @param timestamp the timestamp to match, allowing slight deviations
 * @return the found Person, or null if no match is found
 */
	private Person findPersonByApproximateTimestamp(String name, LocalDateTime timestamp) {
		return people.values().stream()
		.filter(p -> p.getName().equals(name) && Math.abs(ChronoUnit.SECONDS.between(p.getTimestamp(), timestamp)) <= 5)
		.findFirst()
		.orElse(null);
	}
/**
 * Removes a friendship between two persons based on their names and timestamps.
 * @param name1 the name of the first person
 * @param timestamp1 the timestamp of the first person
 * @param name2 the name of the second person
 * @param timestamp2 the timestamp of the second person
 */
	public void removeFriendship(String name1, LocalDateTime timestamp1, String name2, LocalDateTime timestamp2) {
		String key1 = name1 + timestamp1.truncatedTo(ChronoUnit.SECONDS).format(formatter);				// Create a unique key based on name and timestamp
		String key2 = name2 + timestamp2.truncatedTo(ChronoUnit.SECONDS).format(formatter);				// Create a unique key based on name and timestamp
		Person person1 = people.get(key1);
		Person person2 = people.get(key2);
		if (person1 != null && person2 != null && friendships.get(person1).contains(person2)) {				// check key with existing user
			friendships.get(person1).remove(person2);
			friendships.get(person2).remove(person1);
			System.out.println("Friendship removed between " + name1 + " and " + name2);				// Print friendship added message
		}
		else {
			System.out.println("No existing friendship to remove between " + name1 + " and " + name2);		// Print friendship not found message
		}
	}
/**
 * Finds the shortest path between two persons using Breadth-First Search (BFS).
 * @param startName the name of the starting person
 * @param startTimestamp the timestamp of the starting person
 * @param endName the name of the ending person
 * @param endTimestamp the timestamp of the ending person
 */
	public void findShortestPath(String startName, LocalDateTime startTimestamp, String endName, LocalDateTime endTimestamp) {
		Person startPerson = findPersonByApproximateTimestamp(startName, startTimestamp);
		Person endPerson = findPersonByApproximateTimestamp(endName, endTimestamp);
		if (startPerson == null || endPerson == null) {
			System.out.println("One or both persons not found for Find Shortest Path.");				// Print person not found message
			return;
		}
		Queue<Person> queue = new LinkedList<>();
		Map<Person, Person> predecessors = new HashMap<>();
		Set<Person> visited = new HashSet<>();
		queue.add(startPerson);
		visited.add(startPerson);
		boolean found = false;
    
		while (!queue.isEmpty() && !found) {									// Find path with bfs alg.
			Person current = queue.poll();
			for (Person friend : friendships.get(current)) {
				if (!visited.contains(friend)) {
					visited.add(friend);
					predecessors.put(friend, current);
					queue.add(friend);
					if (friend.equals(endPerson)) {
						found = true;
						break;
					}
				}
			}
		}
		if (found) {
			printPath(endPerson, predecessors);								// Print path method
		}
		else {
			System.out.println("No path exists between " + startName + " and " + endName);			// Print no path exists message
		}
	}
/**
 * Prints the path from the starting person to the ending person based on the shortest path found.
 * @param endPerson the ending person of the path
 * @param predecessors a map containing each person and their predecessor on the path
 */
	private void printPath(Person endPerson, Map<Person, Person> predecessors) {
		LinkedList<Person> path = new LinkedList<>();
		for (Person at = endPerson; at != null; at = predecessors.get(at)) {
			path.addFirst(at);
		}
		System.out.println("\nShortest path: " + path.stream().map(Person::getName).collect(Collectors.joining(" -> ")));		// Print the path
	}
/**
 * Suggests friends for a person based on mutual friends and common hobbies.
 * @param name the name of the person
 * @param timestamp the timestamp when the person was added
 * @param maxSuggestions the maximum number of friend suggestions
 */
	public void suggestFriends(String name, LocalDateTime timestamp, int maxSuggestions) {
		String key = name + timestamp.truncatedTo(ChronoUnit.SECONDS).format(formatter);			// Create a unique key based on name and timestamp
		Person person = people.get(key);
		if (person == null) {
			System.out.println("Person not found.");									// Print person not found message
			return;
		}
		Map<Person, ScoreDetail> scores = new HashMap<>();
		for (Person potentialFriend : people.values()) {
			if (!potentialFriend.equals(person) && !friendships.get(person).contains(potentialFriend)) {
				ScoreDetail scoreDetail = calculateScoreDetail(person, potentialFriend);
				if (scoreDetail.score > 0) {							// Only consider potential friends with a positive score
					scores.put(potentialFriend, scoreDetail);
				}
			}
		}
		List<Map.Entry<Person, ScoreDetail>> suggestedFriends = scores.entrySet().stream()
		.sorted((e1, e2) -> Double.compare(e2.getValue().score, e1.getValue().score))
		.limit(maxSuggestions)
		.collect(Collectors.toList());
		if (suggestedFriends.isEmpty()) {
			System.out.println("No suggesting friend found.");					// No suggestion friend found so score 0
		}
		else {
			System.out.println("Suggested friends for " + person.getName() + ":");			// Print suggested friends
			suggestedFriends.forEach(entry -> {
				Person friend = entry.getKey();
				ScoreDetail detail = entry.getValue();
				System.out.printf("%s (Score: %.1f, %d mutual friends, %d common hobbies)\n",		//Print common things
				friend.getName(), detail.score, detail.mutualFriends, detail.commonHobbies);
			});
		}
	}
/**
 * Represents the detailed scoring information for suggested friends based on mutual friends and common hobbies.
 */
	private class ScoreDetail {
/**
 * Constructs a ScoreDetail object with specified score components.
 * @param score the total calculated score
 * @param mutualFriends the number of mutual friends contributing to the score
 * @param commonHobbies the number of common hobbies contributing to the score
 */
		double score;
		int mutualFriends;
		int commonHobbies;
		ScoreDetail(double score, int mutualFriends, int commonHobbies) {
			this.score = score;
			this.mutualFriends = mutualFriends;
			this.commonHobbies = commonHobbies;
		}
	}
/**
 * Calculates a score for suggesting a potential friend based on the number of mutual friends and common hobbies.
 * @param person the person for whom friends are being suggested
 * @param candidate the potential friend to score
 * @return a ScoreDetail object containing the calculated score and its components
 */
	private ScoreDetail calculateScoreDetail(Person person, Person candidate) {
		long mutualFriends = friendships.get(person).stream().filter(friend -> friendships.get(candidate).contains(friend)).count();
		long commonHobbies = person.getHobbies().stream().filter(hobby -> candidate.getHobbies().contains(hobby)).count();
		double score = mutualFriends * 1.0 + commonHobbies * 0.5;						// Adjust weights as needed
		return new ScoreDetail(score, (int) mutualFriends, (int) commonHobbies);
	}
/**
 * Counts and prints the number of distinct clusters of connected persons in the network.
 */
	public void countClusters() {
		Set<Person> visited = new HashSet<>();
		List<List<Person>> clusters = new ArrayList<>();
		for (Person person : people.values()) {
			if (!visited.contains(person)) {
				List<Person> cluster = new ArrayList<>();
				bfs(person, visited, cluster);
				clusters.add(cluster);
			}
		}
		System.out.println("\nCounting clusters in the social network...");					// Print clusters message
		System.out.println("Number of clusters found: " + clusters.size());					// Print clusters size
		int clusterNumber = 1;
		for (List<Person> cluster : clusters) {
			System.out.println("\nCluster " + clusterNumber + ":");						// Print clusters
			for (Person p : cluster) {
				System.out.println(p.getName());
			}
			clusterNumber++;										// Increase cluster size
		}
	}
/**
 * Performs a breadth-first search to find all persons connected to a starting person, marking them as visited.
 * @param start the person from whom to start the search
 * @param visited a set of already visited persons
 * @param cluster a list representing the current cluster of connected persons
 */
	private void bfs(Person start, Set<Person> visited, List<Person> cluster) {
		Queue<Person> queue = new LinkedList<>();
		queue.add(start);
		visited.add(start);
		cluster.add(start);
		while (!queue.isEmpty()) {										// bfs alg.
			Person current = queue.poll();
			for (Person neighbor : friendships.getOrDefault(current, new ArrayList<>())) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.add(neighbor);
					cluster.add(neighbor);
				}
			}
		}
	}
/**
 * Lists all persons currently in the social network, showing their details and timestamps.
 */
	public void listAllPersons() {
		if (people.isEmpty()) {
			System.out.println("No persons are currently in the network.");				// print no person in network
		}
		else {
			System.out.println("Listing all persons in the network:");				// print list all persons message
			for (Person person : people.values()) {
				LocalDateTime timestamp = personTimestamps.get(person); // Retrieve the timestamp from the map
				System.out.println(person + " (Timestamp: " + formatter.format(timestamp) + ")");					//Print persons
			}
		}
	}
/**
 * Lists all friendships currently in the social network.
 */
	public void listAllFriendships() {
		if (friendships.isEmpty()) {
			System.out.println("No friendships are currently in the network.");			// print no friendship in network
			return;
		}
		System.out.println("Listing all friendships in the network\n");					// print list all friendship message
		Set<String> uniqueFriendships = new HashSet<>(); // To avoid duplicate listings
		for (Map.Entry<Person, List<Person>> entry : friendships.entrySet()) {
			Person person = entry.getKey();
			for (Person friend : entry.getValue()) {
				String friendshipKey = createUniqueFriendshipKey(person.getName(), friend.getName());
				if (uniqueFriendships.add(friendshipKey)) {
					System.out.println(person.getName() + " is friends with " + friend.getName());			// print friendships
				}
			}
		}
	}
/**
 * Creates a unique key for a friendship based on the names of the two persons involved.
 * @param name1 the name of the first person in the friendship
 * @param name2 the name of the second person in the friendship
 * @return a unique string key representing the friendship
 */
	private String createUniqueFriendshipKey(String name1, String name2) {
		return name1.compareTo(name2) < 0 ? name1 + "|" + name2 : name2 + "|" + name1;
	}

}
