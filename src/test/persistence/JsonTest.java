package persistence;


import model.Contact;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkContact(String name, String phoneNumber,
                                String email, String type, Contact testContact) {

        assertEquals(name, testContact.getName());
        assertEquals(phoneNumber, testContact.getPhoneNumber());
        assertEquals(email, testContact.getEmail());
        assertEquals(type, testContact.getType());
    }

//    protected void checkCall(String name, String contactName {
//        assertEquals(name, testContact.getName());
//    }
}
