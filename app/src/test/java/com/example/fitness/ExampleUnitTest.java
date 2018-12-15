package com.example.fitness;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void dummy1() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dummy2() {
        assertEquals(9, 2 + 7);
    }

    @Test
    public void dummy3() {
        assertEquals(4, -2 - -6);
    }

}