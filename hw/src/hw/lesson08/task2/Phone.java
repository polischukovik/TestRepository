package hw.lesson08.task2;

public class Phone {
	private String vendor;
	private String model;
	private int memory;
	private String madeIn;
	private String operator;
	private double account;
	public Phone(String vendor, String model, int memory, String madeIn) {
		super();
		this.vendor = vendor;
		this.model = model;
		this.memory = memory;
		this.madeIn = madeIn;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public String getVendor() {
		return vendor;
	}
	public String getModel() {
		return model;
	}
	public int getMemory() {
		return memory;
	}
	public String getMadeIn() {
		return madeIn;
	}
	
	public void print(){
		System.out.println("Phone:");
		System.out.println("  " + this.vendor);
		System.out.println("  " + this.model);
		System.out.println("  " + this.memory + "Gb");
		System.out.println("  " + this.madeIn);
		System.out.println("  " + this.operator);
		System.out.println("  " + this.account);
	}
	
	public static void main(String[] args){
		Phone phone = new Phone("Sony", "xPeria", 128, "Malaysia");
		
		phone.setOperator("Vodafone");
		phone.setAccount(10.0);
		
		phone.print();
	}
}
