package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 Represents the tests for the ContactList class
 */

public class ContactListTest {

    ContactList testContactList;
    Contact testContact1;
    Contact testContact2;


    @BeforeEach
    public void setUp() {
        testContactList = new ContactList();
        testContact1 = new Contact("Jaskeerat", "7787623450",
                "jaskeerat@gmail.com", "FRIEND");
        testContact2 = new Contact("Divesh", "123456789",
                "divesh@gmail.com", "FRIEND");
    }

    @Test
    public void ContactListConstructorTest() {
        assertEquals(0, testContactList.getAllContacts().size());
    }


    @Test
    public void addOneContactTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

    }

    @Test
    public void addTwoContactsTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact2));
        assertEquals(2, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact2));

    }

    @Test
    public void addOneContactTwiceTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        assertFalse(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

    }

    @Test
    public void addOneContactThenDeleteTest() {

        //adding 1 contact and removing it
        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        //deleting the contact
        testContactList.deleteContact(testContact1);
        assertEquals(0, testContactList.getAllContacts().size());
        assertFalse(testContactList.getAllContacts().contains(testContact1));

    }

    @Test
    public void addTwoContactsAndDeleteOneTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        assertTrue(testContactList.addContact(testContact2));
        assertEquals(2, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact2));

        testContactList.deleteContact(testContact1);
        assertEquals(1, testContactList.getAllContacts().size());
        assertFalse(testContactList.getAllContacts().contains(testContact1));
        assertTrue(testContactList.getAllContacts().contains(testContact2));
    }

    @Test
    public void addTwoContactsAndDeleteBothTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        assertTrue(testContactList.addContact(testContact2));
        assertEquals(2, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact2));

        testContactList.deleteContact(testContact1);
        assertEquals(1, testContactList.getAllContacts().size());
        assertFalse(testContactList.getAllContacts().contains(testContact1));
        assertTrue(testContactList.getAllContacts().contains(testContact2));

        testContactList.deleteContact(testContact2);
        assertEquals(0, testContactList.getAllContacts().size());
        assertFalse(testContactList.getAllContacts().contains(testContact1));
        assertFalse(testContactList.getAllContacts().contains(testContact2));

    }

    @Test
    public void deleteContactThatDoesntExistTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        testContactList.deleteContact(testContact1);
        assertEquals(0, testContactList.getAllContacts().size());

        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        testContactList.deleteContact(testContact2);
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));
    }

    @Test
    public void getContactByNameTest() {

        assertEquals(0, testContactList.getAllContacts().size());
        assertTrue(testContactList.addContact(testContact1));
        assertEquals(1, testContactList.getAllContacts().size());
        assertTrue(testContactList.getAllContacts().contains(testContact1));

        assertEquals(testContact1, testContactList.getContactByName("Jaskeerat"));
        assertEquals(null, testContactList.getContactByName("Emma"));
    }

}
