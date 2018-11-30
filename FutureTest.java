package bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FutureTest {
    private  Future f;
    @Before
    public void setUp() throws Exception {
        f=new Future();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        assertNull(f.get());
    }

    @Test
    public void resolve() {
    }

    @Test
    public void isDone() {
        assertFalse(f.isDone());
        f.resolve();
    }

    @Test
    public void get1() {
    }
}