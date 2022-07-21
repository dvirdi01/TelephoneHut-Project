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

    private Contact testContact1;
    private Contact testContact2;
    CallingLog testCallingLog;

    @BeforeEach
    public void setUp() {
        testCallingLog = new CallingLog();
        testContact1 = new Contact("Jaskeerat", "7787623450",
                "jaskeerat@gmail.com", "FRIEND");
        testContact2 = new Contact("Divesh", "123456789",
                "divesh@gmail.com", "FRIEND");
    }

    @Test
    public void callingLogConstructorTest() {
        assertEquals(0, testCallingLog.getCallingLog().size());
        assertEquals(0, testCallingLog.getNumberOfCallsMade());
    }


    @Test
    public void makeCallTest() {

        //test empty callingLog
        assertEquals(0, testCallingLog.getCallingLog().size());
        assertEquals(0, testCallingLog.getNumberOfCallsMade());

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testCallingLog.getNumberOfCallsMade());

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testCallingLog.getNumberOfCallsMade());
    }



    @Test
    public void getLastCallMadeTest() {

        assertEquals(0, testCallingLog.getCallingLog().size());

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testCallingLog.getNumberOfCallsMade());

        assertEquals("Jaskeerat", testCallingLog.getLastCallMade());

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testCallingLog.getNumberOfCallsMade());
        assertEquals("Divesh", testCallingLog.getLastCallMade());

    }


    @Test
    public void clearCallingLogTest() {
        assertEquals(0, testCallingLog.getCallingLog().size());

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testCallingLog.getNumberOfCallsMade());

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testCallingLog.getNumberOfCallsMade());

        testCallingLog.clearCallLog();
        assertEquals(0, testCallingLog.getCallingLog().size());

    }

    @Test
    public void getNumberOfCallsMadeTest() {
        assertEquals(0, testCallingLog.getNumberOfCallsMade());

        //make a call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testCallingLog.getNumberOfCallsMade());

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testCallingLog.getNumberOfCallsMade());

    }


}
