import java.util.Scanner;
import java.util.List;
/**
* Main Class ensures simple, intuitive text-based menu to navigate through the system’s functionalities like
* Adding new devices to the inventory
* Removing devices from the inventory
* Updating device details (name, price, quantity)
* Displaying a list of all devices
* Identifying the device with the minimum price
* Sorting devices based on price
* Calculating the total value of the inventory
* Restocking devices (updating quantity)
* Exporting the inventory list to a file
*/
public class Main{

public static void main(String[] args){
		Inventory inventory = new Inventory();
		Scanner scanner = new Scanner(System.in);
	while(true){
		System.out.println("\nWelcome to the Electronics Inventory Management System!\n");
		System.out.println("Please select an option:");
		System.out.println("1. Add a new device");
		System.out.println("2. Remove a device");
		System.out.println("3. Update device details");
		System.out.println("4. List all devices");
		System.out.println("5. Find the cheapest device");
		System.out.println("6. Sort devices by price");
		System.out.println("7. Calculate total inventory value");
		System.out.println("8. Restock a device");
		System.out.println("9. Export inventory report");
		System.out.println("0. Exit");
		System.out.print("Enter your choice: ");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		switch(choice){
				case 1:
					System.out.print("Enter device category: ");
					String category = scanner.nextLine();
					System.out.print("Enter device name: ");
					String name = scanner.nextLine();
					System.out.print("Enter device price: ");
					double price = scanner.nextDouble();
					System.out.print("Enter device quantity: ");
					int quantity = scanner.nextInt();
					scanner.nextLine();
					inventory.add_new_device(category, name, price, quantity);
				break;
				case 2:
					System.out.print("Enter the name of the device to remove: ");
					String dev_rem_name = scanner.nextLine();
					inventory.rem_exst_dev(dev_rem_name);
				break;
				case 3:
					System.out.print("Enter the name of the device to update: ");
					String dev_name_upt = scanner.nextLine();
					System.out.print("Enter new name (leave blank to keep current name): ");
					String newName = scanner.nextLine();
					System.out.print("Enter new price (leave blank to keep current price): ");
					String newPriceStr = scanner.nextLine();
					Double newPrice = newPriceStr.isEmpty() ? null : Double.parseDouble(newPriceStr);
					System.out.print("Enter new quantity (leave blank to keep current quantity): ");
					String newQuantityStr = scanner.nextLine();
					Integer newQuantity = newQuantityStr.isEmpty() ? null : Integer.parseInt(newQuantityStr);
					inventory.upt_exst_dev(dev_name_upt,newName, newPrice, newQuantity);
				break;
                    		case 4:
					inventory.display_devices();
				break;
				case 5:
					inventory.disp_cheapest_dev();
				break;
				case 6:
					inventory.sort_display_devices();
				break;
				case 7:
		    			inventory.print_tot_price();
				break;
				case 8:
					System.out.print("Enter the name of the device to restock: ");
					String name_for_restock = scanner.nextLine();
					System.out.print("Do you want to add or remove stock? (Add/Remove): ");
					String choice_stock = scanner.nextLine();
					boolean check_stock = "Add".equalsIgnoreCase(choice_stock);
					System.out.print("Enter the quantity to " + (check_stock ? "add: " : "remove: "));
					int take_new_stock = scanner.nextInt();
					scanner.nextLine();
					boolean stock_flag = inventory.change_stock_info(name_for_restock, take_new_stock, check_stock);
					if(stock_flag){
							System.out.println(name_for_restock + " " + (check_stock ? "restocked." : "stock reduced.") + 
							" New quantity: " + inventory.get_stock_num(name_for_restock));
					}
					else{
						System.out.println("Device not found or invalid quantity.");
					}
				break;
				case 9:
					System.out.print("Enter the filename to export the inventory report: ");
					String filename = scanner.nextLine();
					boolean flag_exp = inventory.export_rep(filename);
					if(flag_exp){
							System.out.println("Inventory report exported to " + filename);
					}
					else{
						System.out.println("Failed to export inventory report.");
					}
				break;
				case 0:
					System.out.println("Program Finished");
					scanner.close();
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
		}
	}
}
}
