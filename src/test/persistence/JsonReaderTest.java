package persistence;

import model.Contact;
import model.ContactList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;



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
            assertEquals(0, testContactList.getAllContacts().size()); //TODO?? What am I doing

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralContactList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralContactList.json");
        try {
            ContactList testContactList = reader.readContactList();
            assertEquals(0, testContactList.getAllContacts().size());

            //TODO: i AM LOST DUDE


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
