package persistence;

import model.CallingLog;
import model.Contact;
import model.ContactList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 This class represents a JsonWriter that that writes JSON representation of phonebook to file
 */

public class JsonWriter {

    private static final int TAB = 4; //purpose?
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer that writes to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer and throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Contact to file
    public void writeContact(Contact c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ContactList to file
    public void writeContactList(ContactList contactList) {
        JSONObject json = contactList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CallingLog to file
    public void writeCallingLog(CallingLog callingLog) {
        JSONObject json = callingLog.toJson();
        saveToFile(json.toString(TAB));
    }



    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
