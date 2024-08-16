public class Order{
	private String product_name;
	private int count;
	private int total_price;
	private int status; 	// 0: Initialized, 1: Processing, 2: Completed, 3: Cancelled
	private int customer_ID;

	public Order(String product_name, int count, int total_price, int status, int customer_ID){
		this.product_name = product_name;
		this.count = count;
		this.total_price = total_price;
		this.status = status;
		this.customer_ID = customer_ID;
	}
	public int getCustomer_ID(){
		return customer_ID;
	}
	public void print_order(){					//print order's information
		String statusString = getStatusString();
		System.out.println("Product name: " + product_name + " - Count: " + count + " - Total price: " + total_price + " - Status: " + statusString);
	}
	private String getStatusString(){				// order's status int to string
		switch (status) {
			case 0: return "Initialized.";
			case 1: return "Processing.";
			case 2: return "Completed.";
			case 3: return "Cancelled.";
			default: return "Unknown.";
		}
	}
}
