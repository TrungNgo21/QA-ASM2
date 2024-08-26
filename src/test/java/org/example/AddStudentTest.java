package org.example;

import org.example.Event;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

public class AddStudentTest {
    private static Event event;
    private static InputStream originalSystemIn;

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUpClass(){
        originalSystemIn = System.in;
        event = new Event();
    }

    private void provideInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    public void testAddStudentSuccessful() {
        provideInput("1\nJohn Doe\npassword#\n");
        String result = event.AddStudent();
        assertEquals("Student Added Successfully", result);
        assertEquals( 9 ,event.countStudent());
    }

    @Test
    public void testAddExistingStudent() {
        provideInput("7654332\nStudent9\np7654332#");
        String result = event.AddStudent();
        assertEquals("Student Exists", result);
    }

    @Test
    public void testAddStudentInvalidPasswordLength() {
        provideInput("3\nBob Smith\ninvaldpw\n");
        String result = event.AddStudent();
        assertSame("Password length should be 9", result);
    }

    @Test
    public void testAddStudentPasswordWithoutFirstP() {
        provideInput("4\nAlice Johnson\nassword1#\n");
        String result = event.AddStudent();
        assertEquals("First letter of the Password should be p", result);
    }

    @Test
    public void testAddStudentPasswordWithoutEndHash() {
        provideInput("5\nCharlie Brown\npassword\n");
        String result = event.AddStudent();
        assertTrue(result.startsWith("Last letter of the password should be #"));
    }

    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
    }

    @AfterClass
    public static void tearDownClass() {
        tempFolder.delete();
    }
}
