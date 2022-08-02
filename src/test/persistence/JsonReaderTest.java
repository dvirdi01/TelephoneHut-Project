package persistence;

import model.CallingLog;
import model.Contact;
import model.ContactList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 Represents the tests for Json Reader class
 Source: JsonSerializationDemo Project
 */

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ContactList testContactList = reader.readContactList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyContactList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyContactList.json");
        try {
            ContactList testContactList = reader.readContactList();

            //checking that the list is empty
            assertEquals(0, testContactList.getAllContacts().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralContactList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralContactList.json");
        try {
            ContactList testContactList = reader.readContactList();

            //extract contacts list
            List<Contact> contactsList = testContactList.getAllContacts();

            //check if size of it is 2
            assertEquals(2, testContactList.getAllContacts().size());

            checkContact("Jasleen", "1234567890",
                    "jasleen@gmail.com", "FRIEND", contactsList.get(0));

            checkContact("Jaskeerat", "8978675423",
                    "Jaskeerat@hotmail.com", "FRIEND", contactsList.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    //-----------------------------For calling log-----------------------------

    @Test
    void testCallingLogReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CallingLog testCallingLog = reader.readCallingLog();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyCallingLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCallingLog.json");
        try {
            CallingLog testCallingLog = reader.readCallingLog();

            //checking that the call log list is empty
            assertEquals(0, testCallingLog.getCallingLog().size());
            //checking number of calls made is 0
            assertEquals(0, testCallingLog.getNumberOfCallsMade());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }






}
