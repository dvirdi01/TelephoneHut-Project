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
    private int testNumOfCallsMade;
    CallingLog testCallingLog;

    @BeforeEach
    public void setUp() {
        testCallingLog = new CallingLog();
        testNumOfCallsMade = 0;
        testContact1 = new Contact("Jaskeerat", "7787623450",
                "jaskeerat@gmail.com", "FRIEND");
        testContact2 = new Contact("Divesh", "123456789",
                "divesh@gmail.com", "FRIEND");
    }

    @Test
    public void callingLogConstructorTest() {
        assertEquals(0, testCallingLog.getCallingLog().size());
        assertEquals(0, testNumOfCallsMade);
    }


    @Test
    public void makeCallTest() {

        //test empty callingLog
        assertEquals(0, testCallingLog.getCallingLog().size());
        assertEquals(0, testNumOfCallsMade);

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testNumOfCallsMade);

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testNumOfCallsMade);
    }



    @Test
    public void getLastCallMadeTest() {

        assertEquals(0, testCallingLog.getCallingLog().size());

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testNumOfCallsMade);

        assertEquals("Jaskeerat", testCallingLog.getLastCallMade());

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testNumOfCallsMade);
        assertEquals("Divesh", testCallingLog.getLastCallMade());

    }


    @Test
    public void clearCallingLogTest() {
        assertEquals(0, testCallingLog.getCallingLog().size());

        //making one call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testNumOfCallsMade);

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testNumOfCallsMade);

        testCallingLog.clearCallLog();
        assertEquals(0, testCallingLog.getCallingLog().size());

    }

    @Test
    public void getNumberOfCallsMadeTest() {
        assertEquals(0, testCallingLog.getNumberOfCallsMade());

        //make a call
        testCallingLog.makeCall(testContact1);
        assertTrue(testCallingLog.getCallingLog().contains("Jaskeerat"));
        assertEquals(1, testNumOfCallsMade);

        //making another call to a different person
        testCallingLog.makeCall(testContact2);
        assertTrue(testCallingLog.getCallingLog().contains("Divesh"));
        assertEquals(2, testNumOfCallsMade);

    }


}
