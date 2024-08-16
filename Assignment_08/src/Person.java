import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents an individual in a social network, including their personal details and the time they were added.
 */
public class Person {
	private String name;
	private int age;
	private List<String> hobbies;
	private LocalDateTime timestamp;
/**
 * Constructs a new Person with the specified name, age, and hobbies.
 * The timestamp is set to the current date and time at the moment of creation.
 *
 * @param name the name of the person
 * @param age the age of the person
 * @param hobbies a list of hobbies enjoyed by the person
 */
	public Person(String name, int age, List<String> hobbies) {
		this.name = name;
		this.age = age;
		this.hobbies = new ArrayList<>(hobbies);		// Ensures that the list is mutable and owned by this Person
		this.timestamp = LocalDateTime.now();			// Automatically sets the timestamp when the person is created
	}
/**
 * Returns the name of the person.
 * @return the name of the person
 */
	public String getName() {					// Getter for name
		return name;
	}
/**
 * Sets the name of the person.
 * @param name the new name of the person
 */
	public void setName(String name) {				// Setter for name
		this.name = name;
	}
/**
 * Returns the age of the person.
 * @return the age of the person
 */
	public int getAge() {						// Getter for age
		return age;
	}
/**
 * Sets the age of the person. This can be used to update the person's age.
 * @param age the new age of the person
 */
	public void setAge(int age) {					// Setter for age
		this.age = age;
	}
/**
 * Returns a copy of the list of hobbies to ensure encapsulation.
 * @return a copy of the list of hobbies
 */
	public List<String> getHobbies() {				// Getter for hobbies
		return new ArrayList<>(hobbies);			// Returns a copy to protect internal list from external modifications
	}
/**
 * Sets the hobbies of the person. This method takes a list of hobbies and sets it to the person.
 * @param hobbies the new list of hobbies for the person
 */
	public void setHobbies(List<String> hobbies) {			// Setter for hobbies
		this.hobbies = new ArrayList<>(hobbies);
	}
/**
 * Returns the timestamp marking when the person was added to the system.
 * @return the timestamp of when the person was created
 */
	public LocalDateTime getTimestamp() {				// Getter for timestamp
		return timestamp;
	}
/**
 * Provides a string representation of the person including their name, age, and hobbies.
 * @return a string description of the person
 */
	@Override
	public String toString() {
		return name + " (Age: " + age + ", Hobbies: " + hobbies.toString()  + ")";
	}
}

