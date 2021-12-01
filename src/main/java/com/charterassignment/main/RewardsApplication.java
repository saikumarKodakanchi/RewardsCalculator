package com.charterassignment.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.charterassignment.beans.Customer;
import com.charterassignment.beans.PurchaseRecord;

/**
 * This class calculates rewards and solves below problem
 * 
 * A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. 
 * A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
 * Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total. 
 * @author saiku
 *
 */
public class RewardsApplication {

	public static void main(String[] args) {
		
		//Input dataset
		List<PurchaseRecord> records = getInputDataSet();
		
		Map<Customer, Map<String, Integer>> rewardsByCustomer = calculateRewardPoints(records);
		
		//Display reward points for each customer
		for (Entry<Customer, Map<String, Integer>> customerEntry : rewardsByCustomer.entrySet()) {
			Customer customer = customerEntry.getKey();
			Map<String, Integer> monthlyRewards = customerEntry.getValue();
			System.out.println("============================================================================================");
			System.out.println(String.format("Snapshot of reward points earned by customer %s with id %s are below",
					customer.getCustomerName(), customer.getCustomerId()));
			
			for (Entry<String, Integer> monthlyRewardEntry : monthlyRewards.entrySet()) {
				System.out.println(String.format("Reward points earned in %-14s : %d",
						monthlyRewardEntry.getKey(), monthlyRewardEntry.getValue()));
			}
		}
		System.out.println("============================================================================================");
		
	}

	//This method creates test data and returns it
	private static List<PurchaseRecord> getInputDataSet() {
		List<PurchaseRecord> records = new ArrayList<>();
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-09-10"), 65));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-09-19"), 35));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-10-05"), 120));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-10-21"), 150));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-10-15"), 25));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-25"), 75));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-26"), 125));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-09-10"), 80));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-22"), 200));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-10-23"), 200));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-29"), 25));
		records.add(new PurchaseRecord(new Customer("251", "Mary Doe"), LocalDate.parse("2021-11-10"), 145));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-09-29"), 251));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-11-29"), 135));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-10-13"), 75));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-09-30"), 245));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-10-15"), 39));
		records.add(new PurchaseRecord(new Customer("251", "Mary Doe"), LocalDate.parse("2021-09-11"), 147));
		records.add(new PurchaseRecord(new Customer("251", "Mary Doe"), LocalDate.parse("2021-10-14"), 110));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-10-05"), 125));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-10-23"), 51));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-10-11"), 49));
		records.add(new PurchaseRecord(new Customer("251", "Mary Doe"), LocalDate.parse("2021-11-15"), 50));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-09-13"), 67));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-10-10"), 85));
		records.add(new PurchaseRecord(new Customer("12", "Jane Doe"), LocalDate.parse("2021-10-03"), 140));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-25"), 105));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-10-11"), 125));
		records.add(new PurchaseRecord(new Customer("13", "David Doe"), LocalDate.parse("2021-09-01"), 135));
		records.add(new PurchaseRecord(new Customer("11", "John Doe"), LocalDate.parse("2021-11-29"), 25));
		return records;
	}
	
	/**
	 * This method calculates reward points for the provided purchase transactions and returns rewards by each customer for each month.
	 * @param records
	 * @return
	 */
	private static Map<Customer, Map<String, Integer>> calculateRewardPoints(List<PurchaseRecord> records) {
		Map<Customer, Map<String, Integer>> result = new HashMap<>();
		
		int lastCustTotalRewards = 0;
		Customer lastCustomer = null;
		
		//LinkedHashMap is used to store data in insertion order
		Map<String, Integer> custMonthlyTxMap = new LinkedHashMap<>();
		
		//Transactions are sorted based on customer id and date of transaction
		//Without this sorting, below code returns inaccurate results
		Collections.sort(records);
		for (PurchaseRecord record : records) {
			
			//This block will execute from second customer transactions, which adds last customer reward information to result and resets data variables.
			if (lastCustomer != null 
					&& !record.getCustomer().getCustomerId().equals(lastCustomer.getCustomerId())
					&& lastCustTotalRewards > 0) {
				custMonthlyTxMap.put("Total", lastCustTotalRewards);
				result.put(new Customer(lastCustomer.getCustomerId(), lastCustomer.getCustomerName()), custMonthlyTxMap);
				custMonthlyTxMap = new LinkedHashMap<>();
				lastCustTotalRewards = 0;
			}
			
			String month = record.getMonthName();
			int monthlyRewards = 0;
			
			//Gets existing reward points of the current transaction month
			if (custMonthlyTxMap.containsKey(month)) {
				monthlyRewards = custMonthlyTxMap.get(month);
			}
			
			int currentTransactionRewards = getEarnedRewardPoints(record.getPurchaseAmount());
			monthlyRewards += currentTransactionRewards;
			lastCustTotalRewards += currentTransactionRewards;
			
			custMonthlyTxMap.put(month, monthlyRewards);
			lastCustomer = record.getCustomer();
		}
		
		//Captures last customer rewards
		if (lastCustTotalRewards > 0) {
			custMonthlyTxMap.put("Total", lastCustTotalRewards);
			result.put(new Customer(lastCustomer.getCustomerId(), lastCustomer.getCustomerName()), custMonthlyTxMap);
		}
		
		return result;
	}
	
	/**
	 * This method calculates reward points based on input purchase amount and returns it
	 * Logic:
	 * A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
	 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
	 * @param amount
	 * @return
	 */
	private static int getEarnedRewardPoints(float amount) {
		int rewardPoints = 0;
		if (amount > 100) {
			rewardPoints = 50 + (((int)amount-100) * 2);
		}
		else if (amount > 50) {
			rewardPoints = (int)amount-50;
		}
		
		return rewardPoints;
	}

}
