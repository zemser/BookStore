# BookStore
In this project I have implemented a Micro-Service framework which is used to implement an online book store with a delivery option.


Details on the Micro-Service framework:

Consists of two main parts: A Message-Bus and Micro-Services. Each Micro-Service is a thread that can exchange
messages with other Micro-Services using a shared object referred to as the Message-Bus. There are two
different types of messages:
Events:

- An Event defines an action that needs to be processed, e.g., ordering a book from a store. Each
Micro-Service specializes in processing one or more types of events. Upon receiving an event,
the Message-Bus assigns it to the messages queue of an appropriate Micro-Service which
specializes in handling this type of event. It is possible that there are several Micro-Services
which specialize in the same events, (e.g., two or more Micro-Services which can handle an
event of ordering a book from a books store). In this case, the Message-Bus assigns the event
to a single Micro-Service of those which specialize in this event in a round-robin manner
(described below).
- Each event holds a result. This result should be resolved once the Micro-Service which the
event was assigned to completes processing it. For example: the event of ‘ordering a book’
from the store should return a receipt in the case that the order was successfully completed.
The result is represented in the template Future<T> object (T defines the type of the results).
- While a Micro-Service processes an event, it might create new events and send them to the
Message-Bus. The Message-Bus will then assign the new events to the queue of one of the
appropriate Micro-Services. For example: while processing an ‘ordering a book’ event, the
Micro-Service processing the event may need to check if the specific book is available in the
inventory. Therefore, it must create an event for checking the availability in the inventory. The
new event will be processed by another Micro-Service which has access to the inventory (such
as the Manager for instance). In case the Micro-Service which generated the new event is
interested in receiving the result of the new event in order to proceed, therefore it should wait
until the new event is resolved (the computation completed) and retrieve its result from the
Future object.

Broadcast: Broadcast messages represents a global announcement in the system. Each Micro-Service can
subscribe to the type of broadcast messages it is interested to receive. The Message-Bus sends the
broadcast messages that are passed to it to all the Micro-Services which are interested in receiving them
(this is in contrast to events which are sent to only one of the Micro-Services which are interested in them).

Book Store implementaion:

List of passive classes:

• BookInventoryInfo: An object which represents information about a single book in the store. It
contains the following fields:

- bookTitle: String – the title of the book.
- amountInInventory: int – the number books of bookTitle currently in the inventory.
- price: int – the price of the book.

• OrderReceipt: An object representing a receipt that should be sent to a customer after buying a
book (when the customers OrderBookEvent has been completed). The receipt should contain the
following fields:

- orderId: int – the id of the order.
- seller: string - the name of the service which handled the order.
- customer: id - the id of the customer the receipt is issued to.
- bookTitle: string – title of the book bought.
- price: int – the price the customer paid for the book.
- issuedTick: int - tick in which this receipt was issued (upon completing the corresponding
event).
- orderTick: int - tick in which the customer ordered the book.
- proccessTick: int – tick in which the selling service started processing the order.

• Inventory:

This object must be implemented as a thread safe singleton. The Inventory object holds a
collection of BookInventoryInfo: One for each book the store offers.
Only the following methods should be publicly available from the store:

- public void load (BookInventoryInfo[ ] inventory )
This method should be called in order to initialize the store inventory before starting
execution (by the BookStoreRunner class defined later). The method will add the items in
the given array to the store.

- public OrderResult take (String book)
This method will attempt to take one book from the store. It receives the title of books to
take. Its result is an enum which has the following value options:

- NOT_IN_STOCK: which indicates that there were no books of this type in stock
(the store inventory should not be changed in this case)

- SUCCESSFULLY_TAKEN: which means that the item was successfully taken
(the number books of this type should be reduced by one)

- public int checkAvailabiltyAndGetPrice(String book): this method returns the price of the
book if it is available, and -1 otherwise.

- public void printInventoryToFile(String filename): prints to a file named filename a
serialized HashMap<String,Integer> object, which is a map of all the books in the
inventory. The key is the book’s title and the value is the amount of this book in inventory.
This method is called by the BookStoreRunner class in order to generate the output
(described later).

• DeliveryVehicle: this object represents a delivery vehicle in the system. It consists of the
following field:

- license: int – the vehicle license number.
- speed: int- number of milliseconds needed for 1KM.
Contains the method deliver which gets as parameter the address of the customer and the
distance from the store and simulates delivery by calling to sleep with the required number of
milliseconds for delivery.

• MoneyRegister: 

This object holds a list of receipt issued by the store. This class should be implemented as a thread
safe singleton. Only the following methods should be publicly available from the MoneyRegister:

- public void file (OrderReceipt r): this method saves the given receipt in the
MoneyRegister.

- getTotalEarnings(): this method returns the total earnings of the store.

- getOrderReceipts(): returns all the order receipts in the system.

- printOrderReceipts(String filename): prints to a file named filename a serialized object
List<OrderReceipt> which holds all the order receipts currently in the MoneyRegister.
This method is called by the BookStoreRunner class in order to generate the output.
Contains the method chargeCreditCard which gets as parameter the amount to charge from this
customers credit card.

• ResourcesHolder:
Holds a collection of DeliveryVehicle. Only the following methods should be publicly available:

- Future< DeliveryVehicle > acquireVehicle: tries to acquire a vehicle. Returns a Future
object which will hold the acquired vehicle at some point.

- void releaseVehicle: releases a vehicle, making it available to acquire again.

• Customer:

- id: int – The id number of the customer.

- name: String – The name of the customer.

- address: String – The address of the customer.

- distance: int – the distance of the customer’s address from the store.

- Receipts: List – all the receipts issued to the customer.

- creditCard: int – The number of the credit card of the customer.


The input file for this project is a s JSON file.

Prgoram Flow: 

The BookStoreRunner class is tasked with running the simulation. When started, it should accept as
argument (command line argument) the name of the json input file to read, and the names of the four output
files- the first file is the output file for the customers HashMap, the second is for the books HashMap
object, the third is for the list of order receipts, and the fourth is for the MoneyRegister object.
The BookStoreRunner should read the input file (using Gson parsing). Next it should create the Inventory
object (adding the initial inventory to it), create all the required objects, create and initialize the MicroServices. When the number of passed ticks equal to duration, all Micro-Services should unregister
themselves and terminate gracefully. After all the Micro-Services terminate themselves, the
BookStoreRunner should generate all the output files and exi
