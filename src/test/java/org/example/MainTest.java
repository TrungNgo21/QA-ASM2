package org.example;

import org.junit.After;
import org.junit.AfterClass;

import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static InputStream originalSystemIn;


    String mainMenuContent;
    

    @Before
    public void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));
        originalSystemIn = System.in;
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
    public void provideInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }


    @Test
    public void testExitMainMenu() {
        provideInput("3\n\n");
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Thanks for using, Bye!"));
    }

    @Test
    public void testInvalidNonIntChoiceMainMenu(){
        provideInput("fasdfasdf\n\n");
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("*Sorry we encountered an unusual error, please try again*"));
    }

    @Test
    public void testInvalidIntChoiceMainMenu() throws InterruptedException {
        provideInput("0\n\n");
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        List<String> listOutput = List.of(output.split("\n"));
        assertTrue((listOutput).contains("Wrong choice, Choose again."));
    }

    @Test
    public void testAdminLoginViewAll(){
        String choice = "1\n1\n";
        String inputAuth = "Admin1\npass1\n";

        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()), new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("1 - View details of all Students"));
        assertTrue(output.contains("Details of All Student:."));
    }
    @Test
    public void testAdminLoginSearchDetails(){
        String choice = "1\n2\n";
        String inputAuth = "Admin1\npass1\n";
        String sID = "7654324";

        InputStream choiceInput = new ByteArrayInputStream(choice.getBytes());
        InputStream authIdInput = new SequenceInputStream(new ByteArrayInputStream(inputAuth.getBytes()),
                new ByteArrayInputStream(sID.getBytes())) ;
        InputStream combinedInput = new SequenceInputStream(choiceInput, authIdInput);
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("2 - Search details of a specific Student"));
        assertTrue(output.contains("Search result : true"));
        assertTrue(output.contains("ID: 7654324"));
    }
    @Test
    public void testAdminDeleteStudent(){
        String choice = "1\n3\n";
        String inputAuth = "Admin1\npass1\n";
        String sId = "7654324";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new SequenceInputStream(new ByteArrayInputStream(inputAuth.getBytes()), new ByteArrayInputStream(sId.getBytes())));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("3 - Delete a Student"));
        assertTrue(output.contains("Enter id of Student you want to remove: "));
        assertTrue(output.contains("Search result : true"));
    }
    @Test
    public void testAdminInsertStudent(){
        String choice = "1\n4\n";
        String inputAuth = "Admin1\npass1\n";
        String studentInfo = "2\nStudent10\npassword#";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new SequenceInputStream(new ByteArrayInputStream(inputAuth.getBytes()), new ByteArrayInputStream(studentInfo.getBytes())));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("4 - Insert a Student"));
        assertTrue(output.contains("Enter Student's ID: "));
        assertTrue(output.contains("Enter Student's Name: "));
        assertTrue(output.contains("Enter Student's Password: "));
        assertTrue(output.contains("Student Added Successfully"));
    }

    @Test
    public void testAdminCountStudent(){
        String choice = "1\n5\n";
        String inputAuth = "Admin1\npass1\n";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
               new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("5 - Counts the number of Students"));
        assertTrue(output.contains("Total Number of Students = 9"));
    }

    @Test
    public void testAdminLogout(){
        String choice = "1\n6\n";
        String inputAuth = "Admin1\npass1\n";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("6 - Logout"));
        assertTrue(output.contains("Do you want to proceed as an Admin or a Student?"));
    }

    @Test
    public void testInvalidNonIntChoiceAdminMenu(){
        String choice = "1\nasdfasdf\n";
        String inputAuth = "Admin1\npass1\n";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("*Sorry we encountered an unusual error, please try again*"));
    }


    @Test
    public void testInvalidIntChoiceAdminMenu(){
        String choice = "1\n7\n";
        String inputAuth = "Admin1\npass1\n";
        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        List<String> listOutput = List.of(output.split("\n"));
        assertTrue((listOutput).contains("Wrong functionality chosen"));
    }

    @Test
    public void testAdminLoginFail(){
        String choice = "1\n";
        String inputAuth = "Admin12\npass1\n";

        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()), new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Admin's Name:"));
        assertTrue(output.contains("Enter Admin's Password:"));
        assertTrue(output.contains("Unable to authenticate, try again"));
    }

    @Test
    public void testStudentLoginViewEvent(){
        String choice = "2\n";
        String inputAuth = "7654324\np7654324#\n";

        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()), new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Student's ID: "));
        assertTrue(output.contains("Enter Student's Password: "));
        assertTrue(output.contains("List of Events: "));
    }

    @Test
    public void testStudentLoginFail(){
        String choice = "2\n";
        String inputAuth = "7954324\np7654324#\n";

        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()), new ByteArrayInputStream(inputAuth.getBytes()));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Student's ID: "));
        assertTrue(output.contains("Enter Student's Password: "));
        assertTrue(output.contains("Unable to authenticate, Student deleted or perhaps it doesn't exist, but try again."));
    }

    @Test
    public void testStudentLogout(){
        String choice = "2\n";
        String inputAuth = "7654324\np7654324#\n";
        String logoutInput = "fasdfsd\n";

        InputStream combinedInput = new SequenceInputStream(new ByteArrayInputStream(choice.getBytes()),
                new SequenceInputStream(new ByteArrayInputStream(inputAuth.getBytes()), new ByteArrayInputStream(logoutInput.getBytes())));
        System.setIn(combinedInput);
        Main.main(new String[]{});
        String output = outContent.toString().replace("\r", "");
        assertTrue(output.contains("Enter Student's ID: "));
        assertTrue(output.contains("Enter Student's Password: "));
        assertTrue(output.contains("Do you want to proceed as an Admin or a Student? "));
    }

    @After
    public void restoreStreams(){
        System.setIn(originalSystemIn);
        System.setOut(originalOut);
    }
    
    @AfterClass
    public static void setUpClass() throws IOException {
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