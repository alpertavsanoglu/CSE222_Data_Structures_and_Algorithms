public class Operator extends Person{
	private int wage;
	private Customer[] customers;

	public Operator(String name, String surname, String address, String phone, int ID, int wage){
		super(name, surname, address, phone, ID);
		this.wage = wage;
		this.customers = new Customer[100];
	}
	public void print_operator(){						//print operator's informations
		System.out.println("*** Operator Screen ***");
		System.out.println("----------------------------");
		System.out.println("Name & Surname: " + name + " " + surname);
		System.out.println("Address: " + address);
		System.out.println("Phone: " + phone);
		System.out.println("ID: " + ID);
		System.out.println("Wage: " + wage);
		System.out.println("----------------------------");
	}
	public void define_customers(Customer[] allCustomers){				//define customers to the arrray
		int customerCount = 0;
		for(Customer cust : allCustomers){
			if(cust != null && cust.getOperator_ID() == this.ID){
				this.customers[customerCount++] = cust;
			}
		}
	}
	public void print_customers(){							//print customers informations
		int customerTemp = 0;
		for(int i = 0; i < customers.length; i++){
			if(customers[i] != null){
				customerTemp++;
				String customerType = customers[i] instanceof CorporateCustomer ? "a corporate" : "a retail";
				System.out.println("Customer #" + (i + 1) + " (" + customerType + " customer):");
				customers[i].print_customer();
				customers[i].print_orders();
				System.out.println("----------------------------");
			}
		}
		if(customerTemp == 0){								//if operator has not customer
			System.out.println("This operator doesn't have any customer.");
			System.out.println("----------------------------");
		}
	}
}
