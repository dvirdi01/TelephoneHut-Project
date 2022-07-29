package persistence;


import model.CallingLog;
import model.Contact;
import model.ContactList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 This class represents a JsonReader that that reads workroom from JSON
 data stored in file
 *
 */
public class JsonReader {

    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

     //EFFECTS: reads contactList from file and returns it;
     //throws IOException if an error occurs reading data from file
    public ContactList readContactList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseContactList(jsonObject);
    }

    //TODO: added this for calling log
    //EFFECTS: reads calling log from file and returns it;
    //throws IOException if an error occurs reading data from file
    public CallingLog readCallingLog() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCallingLog(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {

        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }



    //-----------------------------------parsing contactlist-------------------------------------
    // EFFECTS: parses contact list from JSON object and returns it
    private ContactList parseContactList(JSONObject jsonObject) {
        ContactList contactList = new ContactList();
        addContacts(contactList, jsonObject);
        return contactList;
    }

    // MODIFIES: contactList
    // EFFECTS: parses contacts from JSON object and adds them to contact list
    private void addContacts(ContactList contactList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Contacts");
        for (Object json : jsonArray) {
            JSONObject nextContact = (JSONObject) json;
            addContact(contactList, nextContact);
        }
    }

    // MODIFIES: contactList
    // EFFECTS: parses a single contact from JSON object and adds it to contactList
    private void addContact(ContactList contactList, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String phoneNumber = jsonObject.getString("Phone Number");
        String email = jsonObject.getString("Email");
        String type = jsonObject.getString("Type");
        Contact contact = new Contact(name, phoneNumber, email, type);
        contactList.addContact(contact);
    }




    //-----------------------------------parsing calling log ------------------------------------- //TODO: get checked

    // EFFECTS: parses calling log from JSON object and returns it
    private CallingLog parseCallingLog(JSONObject jsonObject) {
        CallingLog callingLog = new CallingLog();
        addCalls(callingLog, jsonObject);
        return callingLog;
    }

    // MODIFIES: callingLog
    // EFFECTS: parses contact names from JSON object and adds them to calling logs
    private void addCalls(CallingLog callingLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Calls");
        for (Object json : jsonArray) {
            JSONObject nextContactName = (JSONObject) json;
            addCall(callingLog, nextContactName);
        }
    }

    //TODO: unsure about this one

    //MODIFIES: callingLog
    //EFFECTS: parses a single contact from JSON object and adds it to contactList
    private void addCall(CallingLog callingLog, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        String phoneNumber = jsonObject.getString("Phone Number");
        String email = jsonObject.getString("Email");
        String type = jsonObject.getString("Type");

        Contact contact = new Contact(name, phoneNumber, email, type);
        callingLog.makeCall(contact);

    }









}
