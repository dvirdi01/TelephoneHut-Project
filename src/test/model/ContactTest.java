package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 Represents the tests for the Contact class
 */
class ContactTest {

    String name;
    String phoneNumber;
    String email;

    Contact testContact;


    @BeforeEach
    public void setUp() {
        testContact = new Contact("Jaskeerat",
                "778762345", "jaskeerat@gmail.com", "FRIEND");

    }

    @Test
    public void ContactTest() {
        assertEquals("Jaskeerat", testContact.getName());
        assertEquals("778762345", testContact.getPhoneNumber());
        assertEquals("jaskeerat@gmail.com", testContact.getEmail());

    }


    @Test
    public void modifyContactTest() {
        testContact.modifyContact("Jasleen", "832172324",
                "jasleen@mail", "FAMILY");

        assertEquals("Jasleen", testContact.getName());
        assertEquals("832172324", testContact.getPhoneNumber());
        assertEquals("jasleen@mail", testContact.getEmail());
        assertEquals("FAMILY", testContact.getType());

    }



}