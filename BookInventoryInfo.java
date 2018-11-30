package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo {
	private String bookTitle;
	private int amountInInventory;
	int price;

	//constructor
	public BookInventoryInfo(String title, int amount, int price){
		this.bookTitle=title;
		this.amountInInventory=amount;
		this.price=price;
	}
	/**
     * Retrieves the title of this book.
     * <p>
     * @return The title of this book.   
     */
	public String getBookTitle() {

		return bookTitle;
	}

	/**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     * @return amount of available books.      
     */
	public int getAmountInInventory() {
		return amountInInventory;
	}

	/**
     * Retrieves the price for  book.
     * <p>
     * @return the price of the book.
     */
	public int getPrice() {
		return price;
	}

	//made for func take in inventory
	//reduces the amount of copies of the book in the inventory
	public void reduceAmountInInventory(){  /////// can the function we write be public?
		amountInInventory--;
	}
	

	
}
