import java.io.File;
import java.util.Scanner;

public class TrackingSystem{
	private static final int MAX_SIZE = 100;				//max customer,operator,customer size
	private static Operator[] operator_arr = new Operator[MAX_SIZE];
	private static Customer[] customer_arr = new Customer[MAX_SIZE];
	private static Order[] order_arr = new Order[MAX_SIZE];
	private static int operator_chk = 0;					//For cheking index of operator
	private static int customer_chk = 0;					//For checking index of customer
	private static int order_chk = 0;					//For checking index of order

	public static void main(String[] args){
		readData("content.txt");
		Scanner user_scan = new Scanner(System.in);
		int ID = 0;
		int validInput = 0; // for checkinginvalid input flag 
		while(validInput == 0){
			try{
				System.out.println("Please enter your ID...");		//Ask id from user
				ID = user_scan.nextInt();
				validInput = 1;						// flag = 1 if input is valid for id
			}catch(Exception e){
				System.out.println("Invalid input. Please enter a valid ID.");		//wrong user input
			//	user_scan.next(); 					// take input from user again
				return ;
			}
		}
		int found = 0;
		for(Operator operator : operator_arr){
			if(operator != null && operator.ID == ID){			//operator id ile işleşirse
				operator.print_operator();
				operator.print_customers();
				found = 1;
				break;
			}
		}
		if(found == 0){
			for(Customer customer : customer_arr){
				if(customer != null && customer.ID == ID){		//customer id ile işleşirse
					System.out.println("*** Customer Screen ***");
					customer.print_customer();
					customer.print_orders();
					//System.out.println("----------------------------");
					found = 1;
					break;
				}
			}
		}
		if(found == 0){								//hiçbiri ile eşleşmezse
			System.out.println("No operator/customer was found with ID " + ID + "." + " Please try again.");
		}
	}
	private static void readData(String filename){					//dosyadan okumak için
		try(Scanner file_scan = new Scanner(new File(filename))){
			while(file_scan.hasNextLine()){					
				String line = file_scan.nextLine();
				if(line.isBlank()){
					continue;							// Ignore this line (empty string)
				}
                		if(line.isEmpty() || line.matches("^;*$")){
					continue;							// Ignore this line (empty string)
				}
                		if(line.isEmpty() || line.matches("^\\s*$") || line.matches("^;*$")){
					continue; 							//Ignore this line (empty string)
				}
				String[] parts = line.split(";");
				if(parts.length < 2){
					continue;							// Ignore this line (missing semicolon)
				}
				int hasEmptyPart = 0;
				for(String part : parts){
					if(part.isEmpty() || part.matches("^\\s*$")){			//Ignore this line (empty part)
						hasEmptyPart = 1;
						break;
					}
				}
				if(hasEmptyPart==1){
					continue;							// Ignore this line if any part is empty or contains only spaces
				}
				int semicolonCount = countSemicolons(line);				//for checking extra or missing semicolon
				try{
					int id;
					String recordType = parts[0];
					if("operator".equals(recordType)){				//checking for operator
							if(parts.length != 7) continue;			// check for extra or missing info
							if(semicolonCount != 6) continue;			// check for extra or missing info
							id = Integer.parseInt(parts[5]);
							if(id <= 0) continue; 				// id must be positive
							if(check_dup_id(id) == 1) continue; 		// Existing ID
							int chech_wage = Integer.parseInt(parts[6]);
							if(chech_wage <= 0) continue; 			// wage must be positive
							operator_arr[operator_chk] = new Operator(parts[1], parts[2], parts[3], parts[4], id, Integer.parseInt(parts[6]));
							operator_chk++;
					}
					else if("retail_customer".equals(recordType)){			//checking for retail_customer
							if(parts.length != 7) continue; 		// check for extra or missing info
							if(semicolonCount != 6) continue;			// check for extra or missing info
							id = Integer.parseInt(parts[5]);
							if(id <= 0) continue; 				// id must be positive
							if(check_dup_id(id) == 1) continue; 		// Existing ID
							int op_id_check = Integer.parseInt(parts[6]);
							if(id == op_id_check)continue;			// ID's cannot be same ex: operator id:500 responsble customer id:500 
							if(op_id_check <= 0) continue; 			// id must be positive
							customer_arr[customer_chk] = new RetailCustomer(parts[1], parts[2], parts[3], parts[4], id, Integer.parseInt(parts[6]));
							customer_chk++;
					}
					else if("corporate_customer".equals(recordType)){		//checking for corporate_customer
							if(parts.length != 8) continue;			// check for extra or missing info
							if(semicolonCount != 7) continue;			// check for extra or missing info
							id = Integer.parseInt(parts[5]);
							if(id <= 0) continue; 				// id must be positive
							if(check_dup_id(id) == 1) continue;		// Existing ID
							int op_id_check_cor = Integer.parseInt(parts[6]);
							if(id == op_id_check_cor)continue;		// ID's cannot be same ex: operator id:500 responsble customer id:500 
							if(op_id_check_cor <= 0) continue; 			// id must be positive
							customer_arr[customer_chk] = new CorporateCustomer(parts[1], parts[2], parts[3], parts[4], id, Integer.parseInt(parts[6]), parts[7]);
							customer_chk++;
					}
					else if("order".equals(recordType)){					//checking for corporate_customer
							if(parts.length != 6) continue; 			// check for extra or missing info
							if(semicolonCount != 5) continue;			// check for extra or missing info
							id = Integer.parseInt(parts[5]);
							if(id <= 0) continue; 					// assigned id must be positive
							int count = Integer.parseInt(parts[2]);
							if(count <= 0) continue; 				// Count must be positive
							int price_check = Integer.parseInt(parts[3]);
							if(price_check <= 0) continue; 				// price must be positive
							order_arr[order_chk] = new Order(parts[1], count, Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), id);
							order_chk++;
					}
					else{}
					
				}catch(NumberFormatException e){
					continue;				// ignore this line has invalid formatting
				}
			}
			// Read whole data and  define orders variable of a customer or  define the customers variable of an operator
			for(Operator op : operator_arr){
				if(op != null){
					op.define_customers(customer_arr);
				}
			}
			for(Customer cust : customer_arr){
				if(cust != null){
					cust.define_orders(order_arr);
				}
			}
		}catch(Exception e){
			System.out.println("An error occurred while reading the file: " + e.getMessage());
		}
	}
	private static int countSemicolons(String line){			//for checking extra or missing semicolon
		int count = 0;
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) == ';'){
				count++;
			}
		}
		return count;
	}
	private static int check_dup_id(int id){
		for(Operator op : operator_arr){
			if(op != null && op.ID == id){
				return 1; 					// Duplicate ID exist
			}
		}
		for(Customer cust : customer_arr){
			if (cust != null && cust.ID == id){
				return 1; 					// Duplicate ID exist
			}
		}
		return 0; 							// ID clear, we can use it
	}
}
