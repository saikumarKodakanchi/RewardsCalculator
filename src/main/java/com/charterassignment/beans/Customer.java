package com.charterassignment.beans;

public class Customer {
	
	private String customerId;
	private String customerName;
	
	public Customer() {}
	
	public Customer(String customerId, String customerName) {
		super();
		setCustomerId(customerId);
		setCustomerName(customerName);
	}

	public String getCustomerId() {
		return customerId;
	}

	//It is very unlikely that customerId is empty for a transaction, but just to test negative cases added default value
	public void setCustomerId(String customerId) {
		if (customerId == null || customerId.equals("")) {
			customerId = "XXX";
		}
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	//It is very unlikely that customerName is empty for a transaction, but just to test negative cases added default value
	public void setCustomerName(String customerName) {
		if (customerName == null || customerName.equals("")) {
			customerName = "XXX";
		}
		this.customerName = customerName;
	}

}
