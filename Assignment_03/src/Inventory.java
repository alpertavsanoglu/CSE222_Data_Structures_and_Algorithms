import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Inventory{
	private LinkedList<ArrayList<Device>> dev_category;
/**
 * Time complexity of constructor is O(1).
 * It executes in a constant amount of time.
 * Initializes the list structure to hold the device categories.
 * Instantiating the LinkedList has a constant time complexity.
 */
public Inventory(){
			dev_category = new LinkedList<>();
}
/**
 * The Worst-Case Time complexity of add_new_device method is O(n).
 * Because add_device function's time complexity is O(n).
 * Print device info's if category valid.
 * @param category to check avaiable category.
 * @param name The name of the device.
 * @param price The price of the device.
 * @param quantity The quantity of the devices.
 */
public void add_new_device(String category, String name, double price, int quantity){
	Device new_dev = create_new_dev(category, name, price, quantity);
	if(new_dev != null){
		add_device(new_dev);
		System.out.printf("%s, Name: %s, Price: %.2f$, Quantity: %d amount added...\n", category, name, price, quantity);
	}
	else{
		System.out.println("Invalid category. Please try again.");
	}
}
/**
 * Time complexity of create_new_dev is O(1).
 * The method executes in constant time regardless of the input parameters.
 * Checking if category valid.
 * @param category to check avaiable category.
 * @param name The name of the device.
 * @param price The price of the device.
 * @param quantity The quantity of the devices.
 * @return A new instance of the specified device category or null if the category is not supported.
 */
private Device create_new_dev(String category, String name, double price, int quantity){
	switch(category){
		case "Smart Phone":
			return new SmartPhone(name, price, quantity);
		case "TV":
			return new TV(name, price, quantity);
		case "Laptop":
			return new Laptop(name, price, quantity);
		case "Headphones":
			return new Headphones(name, price, quantity);
		case "Camera":
			return new Camera(name, price, quantity);
		default:
			return null;
	}
}
/**
 * The Worst-Case Time complexity of add_device method is O(n).
 * In the worst case, it may need to iterate through the entire stream.
 * Adds a device to the inventory under its specified category.
 * If the category does not exist, a new category is created.
 * @param device to add inventory
 */
public void add_device(Device device){
		Optional<ArrayList<Device>> category_check = dev_category.stream().filter(list -> !list.isEmpty() && list.get(0).getCategory().equals(device.getCategory())).findFirst();
		if(category_check.isPresent()){
						category_check.get().add(device);
		}
		else{
			ArrayList<Device> category_create = new ArrayList<>();
			category_create.add(device);
			dev_category.add(category_create);
		}
}
/**
 * For the Worst-Case Time complexity of display_devices method is O(n*m).
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop.
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * It may need to iterate through the entire stream.
 * The nested loops give us O(nm).
 * Listing all devices in the inventory with nested loop.
 */
public void display_devices(){
	if(dev_category.isEmpty()){
				System.out.println("The inventory is currently empty.");
				return;
	}
	System.out.println("Device List:");
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			System.out.println("Category: " + device.getCategory() + ", Name: " + device.getName() + ", Price: $" + device.getPrice() + ", Quantity: " + device.getQuantity());
		}
	}
}
/**
 * The Worst-Case Time complexity of rem_exst_dev method is O(n*m).
 * Because rem_dev_fucnt function's time complexity is O(n*m).
 * Printing successfull or not successfull message to user.
 * @param name to remove.
 */
public void rem_exst_dev(String name){
	boolean flag = rem_dev_fucnt(name);
        if(flag){
		System.out.println(name + " has been removed from the inventory.");
	}
	else{
		System.out.println("Device not found. No device was removed.");
	}
}
/**
 * For the Worst-Case Time complexity of rem_dev_fucnt method is O(n*m).
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * It may need to iterate through the entire stream.
 * The nested loops give us O(n*m),and the array list removal adds another O(m).
 * However, O(nm+m) can be simplify where nm is the dominant term, we can simplify the time complexity to O(n*m).
 * Removing a device from the inventory with nested loop.
 * @param name to remove.
 * @return or false according to the operation's succes.
 */
public boolean rem_dev_fucnt(String name){
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			if(device.getName().equals(name)){
				categoryList.remove(device);
				if(categoryList.isEmpty()){
					dev_category.remove(categoryList);
				}
				return true;
			}
		}
	}
	return false;
}
/**
 * The Worst-Case Time complexity of upt_exst_dev method is O(n*m).
 * Because upt_dev_func function's time complexity is O(n*m).
 * Printing successfull or not successfull message to user.
 * @param name to update
 * @param newName to update or pass
 * @param newPrice to update or pass
 * @param newQuantity to update or pass
 */
public void upt_exst_dev(String name,String newName, Double newPrice, Integer newQuantity){
	boolean flag = upt_dev_func(name, newName, newPrice, newQuantity);
	if(flag){
		System.out.println(name + " details updated.");
	}
	else{
		System.out.println("Device not found. No details were updated.");
	}
}
/**
 * For the Worst-Case Time complexity of upt_dev_func method is O(n*m)
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * It may need to iterate through the entire stream.
 * The nested loops give us O(n*m)
 * Updating existing device's informations.
 * @param name to update
 * @param newName to update or pass
 * @param newPrice to update or pass
 * @param newQuantity to update or pass
 * @return or false according to the operation's succes.
 */
public boolean upt_dev_func(String name, String newName, Double newPrice, Integer newQuantity){
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			if(device.getName().equals(name)){
				if(newName != null && !newName.isEmpty()){
					device.setName(newName);
				}
				if(newPrice != null){
					device.setPrice(newPrice);
				}
				if(newQuantity != null){
					device.setQuantity(newQuantity);
				}
				return true;
			}
		}
	}
	return false;
}
/**
 * The Worst-Case Time complexity of disp_cheapest_dev method is O(n*m).
 * Because chepest_dev_func function's time complexity is O(n*m).
 * Function printing cheapest device which comes from cheapest_dev_func
 */
public void disp_cheapest_dev(){
	Device cheapestDevice = chepest_dev_func();
		if(cheapestDevice != null){
			System.out.println("The cheapest device is:");
			System.out.println("Category: " + cheapestDevice.getCategory() + ", Name: " + cheapestDevice.getName() + ", Price: $" + cheapestDevice.getPrice() +
			", Quantity: " + cheapestDevice.getQuantity());
		}
		else{
			System.out.println("No devices found in the inventory.");
		}
}
/**
 * For the Worst-Case Time complexity of chepest_dev_func method is O(n*m).
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop.
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * The method checks every device to find the cheapest one.
 * The nested loops give us O(n*m).
 * @return cheapestDevice to print.
 */
public Device chepest_dev_func(){
	Device cheapestDevice = null;
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			if(cheapestDevice == null || device.getPrice() < cheapestDevice.getPrice()){
				cheapestDevice = device;
			}
		}
	}
	return cheapestDevice;
}
/**
 * For the Worst-Case Time complexity of sort_display_devices method is O(n*m).
 * In the worst case;
 * If there are n categories and there are m devices per categories so the total number of devices is nm.
 * Collections.sort() have a worst-case time complexity O(n*logn).
 * So the time complexity of the sorting operation is O(n*mlog(nm)).
 * Sorting the devices according to the price informations.
 * And print them.
 * @return temp_dev which is sorted according to the price.
 */
public void sort_display_devices(){
	List<Device> temp_dev = new ArrayList<>();
	for(ArrayList<Device> categoryList : dev_category){
		temp_dev.addAll(categoryList);
	}
	temp_dev.sort(Comparator.comparingDouble(Device::getPrice));
	System.out.println("Devices sorted by price:");
	for(Device device : temp_dev){
		System.out.println("Category: " + device.getCategory() + ", Name: " + device.getName() + ", Price: $" + device.getPrice() +
		", Quantity: " + device.getQuantity());
	}
}
/**
 * The Worst-Case Time complexity of print_tot_price method is O(n*m).
 * Because tot_price_func function's time complexity is O(n*m).
 * Printing total inventory value message to user.
 */
public void print_tot_price(){
	double tot_price = tot_price_func();
	System.out.printf("Total inventory value: $%.2f\n", tot_price);
}
/**
 * For the Worst-Case Time complexity of tot_price_func method is O(n*m).
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop.
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * Each device's value is calculated and summed.
 * The nested loops give us O(n*m).
 * @return tot_price to print.
 */
public double tot_price_func(){
	double tot_price = 0.0;
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			tot_price += device.getPrice() * device.getQuantity();
		}
	}
	return tot_price;
}
/**
 * For the Worst-Case Time complexity of change_stock_info method is O(n*m)
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * It may need to iterate through the entire stream.
 * The nested loops give us O(n*m)
 * Changing existing device's stock
 * @param name to update
 * @param quantity to update
 * @param check_add to check
 * @return true or false according to the operation's succes.
 */
public boolean change_stock_info(String name, int quantity, boolean check_add){
	for(ArrayList<Device> categoryList : dev_category){
		for(Device device : categoryList){
			if(device.getName().equalsIgnoreCase(name)){
				int number_of_dev = device.getQuantity();
				int new_stock = check_add ? number_of_dev + quantity : number_of_dev - quantity;
				if(new_stock < 0){
					System.out.println("Cannot remove more stock than available.");
					return false;
				}
				device.setQuantity(new_stock);
				return true;
			}
		}
	}
	return false;
}
/**
 * For the Worst-Case Time complexity of get_stock_num method is O(n*m)
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * It may need to iterate through the entire stream.
 * The nested loops give us O(n*m)
 * Take new quantity.
 * @param deviceName to check device
 * @return quantity to update
 */
public int get_stock_num(String deviceName){
    for (ArrayList<Device> categoryList : dev_category){
        for (Device device : categoryList){
            if (device.getName().equalsIgnoreCase(deviceName)){
                return device.getQuantity();
            }
        }
    }
    return -1;
}
/**
 * For the Worst-Case Time complexity of export_rep method is O(n*m).
 * In the worst case;
 * If there are n categories, the iteration happens n times for outer loop.
 * If there are m devices per categories, then for each iteration of the outer loop, the inner loop runs m times.
 * The method checks every device to print.
 * The nested loops give us O(n*m).
 * @return true or false according to the operation's succes.
 */
public boolean export_rep(String filename){
	try(BufferedWriter fp = new BufferedWriter(new FileWriter(filename))){
		fp.write("Electronics Shop Inventory Report\n");
		fp.write("Generated on: " + java.time.LocalDate.now() + "\n\n");
		fp.write(String.format("%-15s | %-30s | %-15s | %-10s\n", "Category", "Name", "Price", "Quantity"));
		fp.write("-----------------------------------------------------------------\n");
		for(ArrayList<Device> categoryList : dev_category){
			for(Device device : categoryList){
				fp.write(String.format("%-15s | %-30s | $%-14.2f | %-10d\n",
				device.getCategory(), device.getName(), device.getPrice(), device.getQuantity()));
			}
		}
		fp.write("\nSummary:\n");
		double tot_price = tot_price_func();
		int totalDevices = dev_category.stream().mapToInt(ArrayList::size).sum();
		fp.write("- Total Number of Devices: " + totalDevices + "\n");
		fp.write("- Total Inventory Value: $" + String.format("%.2f", tot_price) + "\n");
		fp.write("\nEnd of Report\n");
		return true;
		}
		catch(IOException e){
			System.err.println("Error writing to file: " + e.getMessage());
			return false;
		}
	}
}
