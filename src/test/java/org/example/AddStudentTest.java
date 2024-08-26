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
    }
    
    @Before
    public void setUp() throws IOException {
         String content = "Id:7654324, Name:Student1, Password:p7654324#\n" +
                 "Id:7654325, Name:Student2, Password:p7654325#\n" +
                 "Id:7654326, Name:Student3, Password:p7654326#\n" +
                 "Id:7654327, Name:Student4, Password:p7654327#\n" +
                 "Id:7654328, Name:Student5, Password:p7654328#\n" +
                 "Id:7654329, Name:Student6, Password:p7654329#\n" +
                 "Id:7654330, Name:Student7, Password:p7654330#\n" +
                 "Id:7654331, Name:Student8, Password:p7654331#\n" +
                 "Id:7654332, Name:Student9, Password:p7654332#";
         try (FileWriter writer = new FileWriter("student.txt")) {
             writer.write(content);
         }
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
        assertEquals(10 ,event.countStudent());
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
        provideInput("5\nCharlie Brown\npassword1\n");
        String result = event.AddStudent();
        assertTrue(result.startsWith("Last letter of the password should be #"));
    }

    @After
    public void tearDown(){
        System.setIn(originalSystemIn);
      
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        tempFolder.delete();
        String content = "Id:7654324, Name:Student1, Password:p7654324#\n" +
                "Id:7654325, Name:Student2, Password:p7654325#\n" +
                "Id:7654326, Name:Student3, Password:p7654326#\n" +
                "Id:7654327, Name:Student4, Password:p7654327#\n" +
                "Id:7654328, Name:Student5, Password:p7654328#\n" +
                "Id:7654329, Name:Student6, Password:p7654329#\n" +
                "Id:7654330, Name:Student7, Password:p7654330#\n" +
                "Id:7654331, Name:Student8, Password:p7654331#\n" +
                "Id:7654332, Name:Student9, Password:p7654332#";
        try (FileWriter writer = new FileWriter("student.txt")) {
            writer.write(content);
        }
    }
}
