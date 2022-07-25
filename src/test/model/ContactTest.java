package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 Represents the tests for the Contact class
 */

class ContactTest {

    Contact testContact;

    @BeforeEach
    public void setUp() {
        testContact = new Contact("Jaskeerat", "7787623450",
                "jaskeerat@gmail.com", "FRIEND");
    }

    @Test
    public void ContactConstructorTest() {
        assertEquals("Jaskeerat", testContact.getName());
        assertEquals("7787623450", testContact.getPhoneNumber());
        assertEquals("jaskeerat@gmail.com", testContact.getEmail());
        assertEquals("FRIEND", testContact.getType());
    }


    @Test
    public void setNameTest() {
        assertEquals("Jaskeerat", testContact.getName());
        testContact.setName("Divjot");
        assertEquals("Divjot", testContact.getName());
    }

    @Test
    public void setPhoneNumberTest() {
        assertEquals("7787623450", testContact.getPhoneNumber());
        testContact.setPhoneNumber("1234567890");
        assertEquals("1234567890", testContact.getPhoneNumber());

    }
    @Test
    public void setEmailTest() {
        assertEquals("jaskeerat@gmail.com", testContact.getEmail());
        testContact.setEmail("divjot@gmail.com");
        assertEquals("divjot@gmail.com", testContact.getEmail());

    }

    @Test
    public void setTypeTest() {
        assertEquals("FRIEND", testContact.getType());
        testContact.setType("FAMILY");
        assertEquals("FAMILY", testContact.getType());

    }

}