package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.imageio.IIOException;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RemoveStudentTest {

    private Event event;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() throws IOException {
        event = new Event();
        System.setOut(new PrintStream(outContent));
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

    @Test
    public void testRemoveExistingStudent() {
        boolean removeStatus = event.removeStudent(7654324);
        int studentCount = event.countStudent();
        assertTrue(removeStatus);
        assertEquals(8, studentCount);
    }

    @Test
    public void testRemoveNonExistentStudent() {
        boolean removeStatus = event.removeStudent(4);
        int studentCount = event.countStudent();
        assertFalse(removeStatus);
        assertEquals(7, studentCount);
    }

    // Mock Student class for testing
    @Test
    public void testRewriteStudentFileCalled() {
        event.removeStudent(7654324); //valid student Id
        event.viewStudentDetails();
        String output = outContent.toString().replace("\r", "");
        String[] listOutput = output.split("\n");

        String expectedOutput  = "\nDetails of All Student:.\nID: 7654325\n" +
                "Name: Student2\n" +
                "Password: p7654325#\n" +
                "\n" +
                "ID: 7654326\n" +
                "Name: Student3\n" +
                "Password: p7654326#\n" +
                "\n" +
                "ID: 7654327\n" +
                "Name: Student4\n" +
                "Password: p7654327#\n" +
                "\n" +
                "ID: 7654328\n" +
                "Name: Student5\n" +
                "Password: p7654328#\n" +
                "\n" +
                "ID: 7654329\n" +
                "Name: Student6\n" +
                "Password: p7654329#\n" +
                "\n" +
                "ID: 7654330\n" +
                "Name: Student7\n" +
                "Password: p7654330#\n" +
                "\n" +
                "ID: 7654331\n" +
                "Name: Student8\n" +
                "Password: p7654331#\n" +
                "\n" +
                "ID: 7654332\n" +
                "Name: Student9\n" +
                "Password: p7654332#";
        String[] listExpectedOutput = expectedOutput.split("\n");

        assertArrayEquals(listExpectedOutput, listOutput);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}