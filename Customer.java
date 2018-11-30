package bgu.spl.mics.application.passiveObjects;

import jdk.javadoc.internal.doclets.toolkit.util.Utils;
import org.graalvm.util.Pair;
import java.util.LinkedList;
import java.util.List;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer {
	//fields
	String name;
	int id;
	String address;
	int distance;
	private List<OrderReceipt> receipts;
	private List<Utils.Pair> orders;
	int availableCreditAmount;
	int creditNumber;

	public Customer(String name, int id, String address, int distance, int creditAmount, int visa, Utils.Pair[] customerOrders){
		this.name=name;
		this.id=id;
		this.address=address;
		this.distance=distance;
		this.receipts=new LinkedList<>();
		this.availableCreditAmount=creditAmount;
		this.creditNumber=visa;
		orders=sortList(customerOrders);




	}

	/**
     * Retrieves the name of the customer.
     */
	public String getName() {
		return name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		return id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		return address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		return distance;
	}

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
		return receipts;
	}
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() {
		return availableCreditAmount;
	}
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public int getCreditNumber() {
		return creditNumber;
	}

	//sorts the customer's orders list
	public List<Utils.Pair> sortList(Utils.Pair[] customerOrders){
		Utils.Pair temp;
		//sort the array of customersOrders by the value of the ticks
		for (int i = 1; i<customerOrders.length; i++) {
			for (int j = i; j > 0; j--) {
				if ((int)customerOrders[j].second<(int)customerOrders[j-1].second) {
					temp =customerOrders[j];
					customerOrders[j]=customerOrders[j-1];
					customerOrders[j-1] = temp;
				}
			}
		}
		//create a sorted list
		List<Utils.Pair> sortedList=new LinkedList<>();
		for(int i=0; i<customerOrders.length; i++)
			sortedList.add(customerOrders[i]);
		return sortedList;
	}


}
