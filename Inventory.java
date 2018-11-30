package bgu.spl.mics.application.passiveObjects;


import sun.jvm.hotspot.utilities.Assert;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */

public class Inventory {
	//fields
	private static Inventory instance = new Inventory();
	private LinkedList<BookInventoryInfo> bookList;
	private HashMap<String,Integer> map;

	private static class InventoryHolder {
		private static Inventory instance = new Inventory();
	}

	//constructor
	private Inventory(){

		bookList=new LinkedList<>();
		map=new HashMap<>();
	}
	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		return InventoryHolder.instance;
	}


	
	/**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (BookInventoryInfo[ ] inventory ) {
		for(int i=0; i<inventory.length; i++){
				bookList.add(inventory[i]);
		}
	}
	
	/**
     * Attempts to take one book from the store.
     * <p>
     * @param book 		Name of the book to take from the store
     * @return 	an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * 			The first should not change the state of the inventory while the 
     * 			second should reduce by one the number of books of the desired type.
     */
	public OrderResult take (String book) {
		Iterator<BookInventoryInfo> it=bookList.iterator();
		boolean notInStock=false;
		while(it.hasNext() && !notInStock){
			BookInventoryInfo tmp=it.next();
			if(tmp.getBookTitle().equals(book)) {
				if (tmp.getAmountInInventory() > 0) {  /////// if there is one book should we delete it from the list
					tmp.reduceAmountInInventory();
					return OrderResult.SUCCESSFULLY_TAKEN;
				}
				notInStock=true;
			}
		}


		return OrderResult.NOT_IN_STOCK;  /////should we send an event?
	}
	
	
	
	/**
     * Checks if a certain book is available in the inventory.
     * <p>
     * @param book 		Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
	public int checkAvailabiltyAndGetPrice(String book) {
		Iterator<BookInventoryInfo> it = bookList.iterator();
		boolean notInStock = false;
		while (it.hasNext() && !notInStock) {
			BookInventoryInfo tmp = it.next();
			if (tmp.getBookTitle().equals(book)) {
				if (tmp.getAmountInInventory() > 0)
					return tmp.getPrice();
				notInStock = true;
			}

		}
		return -1;
	}

	
	/**
     * 
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory. 
     * This method is called by the main method in order to generate the output.
     */
	public void printInventoryToFile(String filename){
		Iterator<BookInventoryInfo> it = bookList.iterator();
		while (it.hasNext()) {
			BookInventoryInfo tmp = it.next();
			map.put(tmp.getBookTitle(), tmp.getAmountInInventory());
		}
	}

	private LinkedList<BookInventoryInfo>
}

