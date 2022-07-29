package persistence;


import model.Contact;
import model.ContactList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 the strategy in designing tests for the JsonWriter is to
 write data to a file and then use the reader to read it back in and check that we
 read in a copy of what was written out.
 */

public class JsonWriterTest extends JsonTest{


    @Test
    void testWriterInvalidFile() {
        try {
            ContactList testContactList = new ContactList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ContactList testContactList = new ContactList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyContactList.json");
            writer.open();
            writer.writeContactList(testContactList);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyContactList.json");
            testContactList = reader.readContactList();
            assertEquals(0, testContactList.getAllContacts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ContactList testContactList = new ContactList();
            testContactList.addContact(new Contact("Jasleen", "1234567890",
                    "jasleen@gmail.com", "FRIEND"));

            testContactList.addContact(new Contact("Jaskeerat", "8978675423",
                    "Jaskeerat@hotmail.com", "FRIEND"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralContactList.json");
            writer.open();
            writer.writeContactList(testContactList);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterGeneralContactList.json");
            testContactList = reader.readContactList();

            List<Contact> contacts = testContactList.getAllContacts();
            assertEquals(2, contacts.size());

            checkContact("Jasleen", "1234567890",
                    "jasleen@gmail.com", "FRIEND", contacts.get(0));
            checkContact("Jaskeerat", "8978675423",
                    "Jaskeerat@hotmail.com", "FRIEND", contacts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}
