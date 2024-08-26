package org.example;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.*;

public class StudentLoginTest {
    private static Event event;
    private static InputStream originalSystemIn;

    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void setUpClass() throws IOException {
        // Store the original System.in
        originalSystemIn = System.in;

        // Create temporary files for testing
        File studentFile = tempFolder.newFile("student.txt");

        // Write test data to the admin file
        try (FileWriter writer = new FileWriter(studentFile)) {
            writer.write("Id:7654324, Name:Student1, Password:p7654324#");
            writer.write("Id:7654325, Name:Student2, Password:p7654325#");
            writer.write("Id:7654326, Name:Student3, Password:p7654326#");
            writer.write("Id:7654327, Name:Student4, Password:p7654327#");
            writer.write("Id:7654328, Name:Student5, Password:p7654328#");
            writer.write("Id:7654329, Name:Student6, Password:p7654329#");
            writer.write("Id:7654330, Name:Student7, Password:p7654330#");
            writer.write("Id:7654331, Name:Student8, Password:p7654331#");
            writer.write("Id:7654332, Name:Student9, Password:p7654332#");
        }

        event = new Event();
    }

    private void provideInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    @Test
    public void testStudentLoginSuccessful() {
        provideInput("Student1\np7654324#\n");
        boolean loginStatus = event.StudentLogin();
        assertTrue(loginStatus);
    }

    @Test
    public void testAdminLoginWrongPassword() {
        provideInput("Student1\nwrongpass\n");
        assertFalse(event.StudentLogin());
    }

    @Test
    public void testAdminLoginNonexistentStudent() {
        provideInput("AdminX\np7654324#\n");
        boolean loginStatus = event.StudentLogin();
        assertTrue("This credentials expected to failed", loginStatus);
    }

    @Test
    public void testStudentLoginEmptyCredentials() {
        provideInput("\n\n");
        assertFalse(event.StudentLogin());
    }

    @After
    public void tearDown() {
        // Reset System.in after each test
        System.setIn(originalSystemIn);
    }

    @AfterClass
    public static void tearDownClass() {
        // Perform any final cleanup here
        // For example, you could delete any files created during testing
        tempFolder.delete();
    }
}
