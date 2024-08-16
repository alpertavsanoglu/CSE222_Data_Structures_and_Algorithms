public class Customer extends Person{
	protected Order[] orders;
	protected int operator_ID;

	public Customer(String name, String surname, String address, String phone, int ID, int operator_ID){
		super(name, surname, address, phone, ID);
		this.operator_ID = operator_ID;
		this.orders = new Order[100];
	}
	public int getOperator_ID(){
		return operator_ID;
	}
	public void define_orders(Order[] allOrders){						//define orders to the array
		int orderCount = 0;
		for(Order ord : allOrders) {
			if(ord != null && ord.getCustomer_ID() == this.ID) {
				this.orders[orderCount++] = ord;
			}
		}
	}
	public void print_customer(){							//print customer's informations
		System.out.println("Name & Surname: " + name + " " + surname);
		System.out.println("Address: " + address);
		System.out.println("Phone: " + phone);
		System.out.println("ID: " + ID);
		System.out.println("Operator ID: " + operator_ID);
	}
	public void print_orders(){							//print orders informations
		for(int i = 0; i < orders.length; i++){
			if(orders[i] != null){
				System.out.print("Order #" + (i + 1) + " => ");
				orders[i].print_order();
			}
		}
	}
}
