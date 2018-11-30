package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.Message;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;

public class BookOrderEvent implements Event {

    //fields
    private String requestedBook;
    private Customer requestingCustomer;

    //constructor
    public BookOrderEvent(String book, Customer c){
        requestedBook=book;
        requestingCustomer=c;
    }
}
