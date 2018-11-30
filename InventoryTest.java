package bgu.spl.mics.application.passiveObjects;
package bgu.spl.mics.application.passiveObjects.Inventory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.awt.print.Book;

import static org.junit.Assert.*;

public class InventoryTest {
    private  Inventory i;
    @Before
    public void setUp() throws Exception {
        i.getInstance();

    }

    @After
    public void tearDown() throws Exception {
        //i=null; - not c++
    }

    @Test
    public void getInstance() {
        //final Inventory expected=getInstance();
        //final Inventory actual=getInstance()
        assertNotNull(i);
    }

    @Test
    public void load() {
        BookInventoryInfo a=new BookInventoryInfo("d",-2,3);
        BookInventoryInfo [] temp={a};
        assertTrue(a.getAmountInInventory()<0);
        assertTrue(a.getPrice()<0);
        assertTrue(a.getBookTitle().length()==0);


    }

    @Test
    public void take() {
        String bookName="";
        assertNotNull(bookName);
    }

    @Test
    public void checkAvailabiltyAndGetPrice() {
        String bookName="";
        assertNotNull(bookName);
    }

    @Test
    public void printInventoryToFile() {
    }
}