package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class BasicDataTest {
    private BasicData basicData;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static InputStream originalSystemIn;

    @Before
    public void setUp() {
        basicData = new BasicData(1, "John Doe", "password123");
        System.setOut(new PrintStream(outContent));
        originalSystemIn = System.in;
    }

    @Test
    public void testConstructor() {
        assertEquals(1, basicData.getID());
        assertEquals("John Doe", basicData.getName());
        assertEquals("password123", basicData.getPassword());
    }

    @Test
    public void testGetID() {
        assertEquals(1, basicData.getID());
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", basicData.getName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", basicData.getPassword());
    }

    @Test
    public void testSetID() {
        basicData.setID(2);
        assertEquals(2, basicData.getID());
    }

    @Test
    public void testSetName() {
        basicData.setName("Jane Doe");
        assertEquals("Jane Doe", basicData.getName());
    }

    @Test
    public void testSetPassword() {
        basicData.setPassword("newpassword");
        assertEquals("newpassword", basicData.getPassword());
    }

    @Test
    public void testPrint() {
        basicData.print();
        String output = outContent.toString().replace("\r", "");

        String expectedOutput = "\nID: 1\nName: John Doe\nPassword: password123\n";
        assertEquals(expectedOutput, output);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalSystemIn);
    }
}