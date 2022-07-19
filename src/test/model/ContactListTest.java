package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactListTest {

    ContactList testContactList;

    @BeforeEach
    public void setUp() {
        testContactList = new ContactList();
    }


    @Test
    public void ContactListConstructorTest() {
        assertEquals(0, testContactList.size());
    }

    @Test
    public void addContactTest() {


    }


    @Test
    public void viewContactTest() {

    }


    @Test
    public void deleteContactTest() {

    }

    @Test
    public void viewAllContactsTest() {

    }
}
