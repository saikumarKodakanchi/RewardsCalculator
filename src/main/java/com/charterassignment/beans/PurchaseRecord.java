package com.charterassignment.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PurchaseRecord implements Comparable<PurchaseRecord> {
	
	private LocalDate purchaseDate;
	private float purchaseAmount;
	private Customer customer;
	
	public PurchaseRecord(Customer customer, LocalDate purchaseDate, float purchaseAmount) {
		setCustomer(customer);
		setPurchaseDate(purchaseDate);
		setPurchaseAmount(purchaseAmount);
	}

 	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

 	//It is very unlikely that purchase date is empty for a transaction, for just to test negative cases added default value
	public void setPurchaseDate(LocalDate purchaseDate) {
		if (purchaseDate == null) {
			purchaseDate = LocalDate.now();
		}
		this.purchaseDate = purchaseDate;
	}

	public float getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(float purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	//It is very unlikely that customer details are empty for a transaction, for just to test negative cases added default values
	public void setCustomer(Customer customer) {
		//If customer is null, setting default value
		if (customer == null) {
			customer = new Customer("XXX", "XXX");
		}
		
		this.customer = customer;
	}

	public String getMonthName() {
		return this.getPurchaseDate().format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
	}
	
	public Integer getMonth() {
		return this.getPurchaseDate().getMonthValue();
	}

	@Override
	public int compareTo(PurchaseRecord obj) {
		int c = this.getCustomer().getCustomerId().compareTo(obj.getCustomer().getCustomerId());
		if (c == 0) {
			c = this.getPurchaseDate().compareTo(obj.getPurchaseDate());
		}
		return c;
	}
	
	
	

}
