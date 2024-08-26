package org.example;
import org.junit.*;

import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

import java.io.*;

public class AdminLoginTest {
	  private static Event event;
	    private static InputStream originalSystemIn;

	    @ClassRule
	    public static TemporaryFolder tempFolder = new TemporaryFolder();

	    @BeforeClass
	    public static void setUpClass() throws IOException {
	        // Store the original System.in
	        originalSystemIn = System.in;

	        // Create temporary files for testing
	        File adminFile = tempFolder.newFile("admin.txt");

	        // Write test data to the admin file
	        try (FileWriter writer = new FileWriter(adminFile)) {
	            writer.write("Id:1, Name:Admin1, Password:pass1\n");
	            writer.write("Id:2, Name:Admin2, Password:pass2\n");
	            writer.write("Id:3, Name:Admin3, Password:pass3\n");
	        }

	        event = new Event();
	    }

	    private void provideInput(String data) {
	        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
	        System.setIn(inputStream);
	    }

	    @Test
	    public void testAdminLoginSuccessful() {
	        provideInput("Admin1\npass1\n");
	        boolean loginStatus = event.AdminLogin();
	        assertTrue(loginStatus);
	    }

	    @Test
	    public void testAdminLoginWrongPassword() {
	        provideInput("Admin1\nwrongpass\n");
	        assertFalse(event.AdminLogin());
	    }

	    @Test
	    public void testAdminLoginNonexistentAdmin() {
	        provideInput("AdminX\npass1\n");
	        boolean loginStatus = event.AdminLogin();
	        assertFalse("This credentials expected to failed", loginStatus);
	    }

	    @Test
	    public void testAdminLoginEmptyCredentials() {
	        provideInput("\n\n");
	        assertFalse(event.AdminLogin());
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
