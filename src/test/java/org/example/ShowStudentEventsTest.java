package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class ShowStudentEventsTest {
    private Event event;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        event = new Event();
        System.setOut(new PrintStream(outContent));
        String content = "Wild Hope: Conversations for a Planetary Commons 15 Aug 2023 - 30 Sep 2023\n" +
                "Urban Futures Symposium 21 Aug 2023 - 25 Aug 2023\n" +
                "'Basalt Study' by Christine McFetridge 22 Aug 2023 - 15 Sep 2023\n" +
                "'The Dark Botanical Garden' by Pug 22 Aug 2023 - 15 Sep 2023\n" +
                "'Off the Well-Worn Path' by Ryley Clarke 22 Aug 2023 - 15 Sep 2023\n" +
                "Future Play Lab: TRON (1982) 19 Sep 2023\n";
        try (FileWriter writer = new FileWriter("event.txt")) {
           writer.write(content);
        }
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testShowStudentEvents() {
        event.showStudentEvents();
        String output = outContent.toString().replace("\r", "");
        String[] listOutput = output.split("\n");
        String expectedOutput = "\nList of Events: \n" +
                "o Wild Hope: Conversations for a Planetary Commons 15 Aug 2023 - 30 Sep 2023\n" +
                "o Urban Futures Symposium 21 Aug 2023 - 25 Aug 2023\n" +
                "o 'Basalt Study' by Christine McFetridge 22 Aug 2023 - 15 Sep 2023\n" +
                "o 'The Dark Botanical Garden' by Pug 22 Aug 2023 - 15 Sep 2023\n" +
                "o 'Off the Well-Worn Path' by Ryley Clarke 22 Aug 2023 - 15 Sep 2023\n" +
                "o Future Play Lab: TRON (1982) 19 Sep 2023\n";
        String[] listExpectedOutput = expectedOutput.split("\n");

        assertArrayEquals(listExpectedOutput, listOutput);
    }

    @Test(expected = IOException.class)
    public void testShowStudentEventsFileNotFound() throws IOException {
        // Create an empty event file
        Path path = Paths.get("event.txt");
        Files.delete(path);
        event.showStudentEvents();
    }

}