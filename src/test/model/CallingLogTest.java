package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 Represents the tests for the CallingLog class
 */
public class CallingLogTest {

    int testNumOfCallsMade;
    List<String> testCallLogDirectory = new ArrayList<>();
    CallingLog testCallingLog = new CallingLog();

    @BeforeEach
    public void setUp() {
        //TODO: WHY IS MY THING NOT INSTANTIATING?
        CallingLog testCallingLog = new CallingLog();

    }

    @Test
    public void CallingLogConstructorTest() {
        assertEquals(0, testCallLogDirectory.size());
        assertEquals(0, testNumOfCallsMade);
    }

    //MODIFIES: this
    //EFFECTS: adds Contact name in the calling log and increments the
    //number of calls made by 1
    @Test
    public void makeCallTest() {

        //test empty callLogDirectory
        assertEquals(0, testNumOfCallsMade);

        //making one call
        testCallingLog.makeCall("Jasleen");
        assertTrue(testCallLogDirectory.contains("Jasleen"));
        assertEquals(1, testNumOfCallsMade);

        //making another call to a different person
        testCallingLog.makeCall("Jaskeerat");
        assertTrue(testCallLogDirectory.contains("Jaskeerat"));
        assertEquals(2, testNumOfCallsMade);

        //making another call to the same person
        testCallingLog.makeCall("Jaskeerat");
        assertTrue(testCallLogDirectory.contains("Jaskeerat"));
        assertEquals(3, testNumOfCallsMade);
    }


    @Test
    public void getNumberOfCallsMadeTest() {
        assertEquals(0, testCallingLog.getNumberOfCallsMade());

        //make a call
        testCallingLog.makeCall("Jasleen");
        assertEquals(1, testNumOfCallsMade);

        //make another call
        testCallingLog.makeCall("Jaskeerat");
        assertEquals(2, testCallingLog.getNumberOfCallsMade());

    }


    @Test
    public void getLastCallMadeTest() {
        //assertEquals(null, testCallingLog.getLastCallMade());

        testCallingLog.makeCall("Jasleen");
        assertEquals("Jasleen", testCallingLog.getLastCallMade());

        testCallingLog.makeCall("Riya");
        assertEquals("Riya", testCallingLog.getLastCallMade());

    }

    @Test
    public void viewCallLogTest() {
        assertEquals(testCallLogDirectory, testCallingLog.viewCallLog());

    }

    @Test
    public void clearCallLogTest() {

        testCallingLog.makeCall("Jasleen");
        testCallingLog.clearCallLog();

        assertTrue(testCallLogDirectory.isEmpty());

        testCallingLog.makeCall("Jasleen");
        testCallingLog.makeCall("Riya");
        testCallingLog.clearCallLog();

        assertTrue(testCallLogDirectory.isEmpty());
    }


}
