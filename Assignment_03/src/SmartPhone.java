public class SmartPhone implements Device{
	private final String category = "Smart Phone";
	private String name;
	private double price;
	private int quantity;
	/**
	* Time complexity of constructor is O(1)
	* It executes in a constant amount of time.
	* @param name of Smart Phone
	* @param price of Smart Phone
	* @param quantity of Smart Phone
	*/
	public SmartPhone(String name, double price, int quantity){
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	/**
	* Time complexity of getCategory method is O(1)
	* it is a direct return statement that executes in a constant amount of time.
	* @return the category
	*/
	@Override
	public String getCategory(){ return category; }
	/**
	* Time complexity of getName method is O(1)
	* it is a direct return statement that executes in a constant amount of time.
	* @return the name
	*/
	@Override
	public String getName(){ return name; }
	/**
	* Time complexity of setName method is O(1)
	* It executes in a constant amount of time.
	* @param name to the new name to be set
	*/
	@Override
	public void setName(String name){ this.name = name; }
	/**
	* Time complexity of getPrice method is O(1)
	* it is a direct return statement that executes in a constant amount of time.
	* @return the price
	*/
	@Override
	public double getPrice(){ return price; }
	/**
	* Time complexity of setPrice method is O(1)
	* It executes in a constant amount of time.
	* @param price to the new price to be set
	*/
	@Override
	public void setPrice(double price){ this.price = price; }
	/**
	* Time complexity of getQuantity method is O(1)
	* it is a direct return statement that executes in a constant amount of time.
	* @return the quantity
	*/
	@Override
	public int getQuantity(){ return quantity; }
	/**
	* Time complexity of setQuantity method is O(1)
	* It executes in a constant amount of time.
	* @param quantity to the new quantity to be set
	*/
	@Override
	public void setQuantity(int quantity){ this.quantity = quantity; }
}
