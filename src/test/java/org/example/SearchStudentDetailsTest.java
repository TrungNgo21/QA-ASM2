package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchStudentDetailsTest {
    private Event event;
    private List<BasicData> students;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        event = new Event();
        students = new ArrayList<>();
        students.add(new BasicData(7654324, "Student1", "p7654324#"));
        students.add(new BasicData(7654325, "Student2", "p7654325#"));
        students.add(new BasicData(7654326, "Student3", "p7654326#"));
        students.add(new BasicData(7654327, "Student4", "p7654327#"));
        students.add(new BasicData(7654328, "Student5", "p7654328#"));
        students.add(new BasicData(7654329, "Student6", "p7654329#"));
        students.add(new BasicData(7654330, "Student7", "p7654330#"));
        students.add(new BasicData(7654331, "Student8", "p7654331#"));
        students.add(new BasicData(7654332, "Student9", "p7654332#"));

        // Redirect System.out to capture print() output
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testSearchStudentDetailsExistingStudent() {
        assertTrue(event.searchStudentDetails(7654324));
        assertTrue(outContent.toString().contains("Student1"));
    }

    @Test
    public void testSearchStudentDetailsNonExistentStudent() {
        assertFalse(event.searchStudentDetails(4));
        assertEquals("", outContent.toString().trim());
    }


    @Test
    public void testSearchStudentDetailsNonIntId() {
        assertFalse(event.searchStudentDetails(Integer.parseInt("sdfsdfsdfasfasdf")));
        assertEquals("", outContent.toString().trim());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

}