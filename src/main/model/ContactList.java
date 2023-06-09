package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a list of all contacts stored in the phonebook
 */

public class ContactList implements Writable {

    List<Contact> contactList;


    //EFFECTS: Constructs a new ContactList with 0 contacts added
    public ContactList() {
        contactList  = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: adds a new contact into contactList if not already present and returns true.
    //else returns false and doesn't add contact to the list.
    public boolean addContact(Contact c) {
        if (!contactList.contains(c)) {
            contactList.add(c);
            EventLog.getInstance().logEvent(new Event("Added " + c.getName() + "'s contact "
                    + "information to Contact List"));
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: if Contact is found in contact list then deletes it, otherwise does nothing
    public void deleteContact(Contact c) {
        if (contactList.contains(c)) {
            contactList.remove(c);
            EventLog.getInstance().logEvent(new Event("Deleted " + c.getName() + "'s contact "
                    + "information from Contact List"));
        }
    }




    //EFFECTS: returns all contacts in the book
    public List<Contact> getAllContacts() {
        return contactList;
    }


    //EFFECTS: If a Contact is found by its name, then returns that contact otherwise returns null
    public Contact getContactByName(String name) {
        for (Contact c: contactList) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }



    //---------------------------------PHASE-2 STUFF ---------------------------------------
    @Override

    //EFFECTS: returns Contact List as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Contact List", contactsToJson());
        return json;
    }


    //EFFECTS: returns contacts in the contact list as a JSON array
    private JSONArray contactsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Contact c : contactList) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

}
