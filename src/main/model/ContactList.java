package model;


import java.util.ArrayList;
import java.util.List;

/**
 Represents a list of all contacts in the phonebook according to their type
 */
public class ContactList {

    List<Contact> contactList;

    Contact anotherContact = new Contact("Jasleen", "3612874914",
            "jasleen@mail.com", "FRIEND");


    //Constructs a new ContactList with 0 contacts added
    public ContactList() {
        contactList  = new ArrayList<>();
    }


    //TODO: should I include a helper method or no?
//    //EFFECTS: returns true if contact is already in the contact list, false otherwise.
//    public boolean checkContactAlreadyThere(Contact c) {
//        if (contactList.contains(c)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    //MODIFIES: this
    //EFFECTS: adds a new contact into contactList if not already present and returns true.
    // else returns false and doesn't add contact to the list.
    public boolean addContact(Contact c) {
        if (!contactList.contains(c)) {
            contactList.add(c);
            return true;
        } else {
            return false;
        }
    }


    //MODIFIES: this
    //EFFECTS: deletes a contact from the contact list
    public void deleteContact(Contact c) {
        if (contactList.contains(c)) {
            contactList.remove(c);
        } else {
            // do nothing
        }
    }


    //EFFECTS: returns all contacts in the book
    public List<Contact> getAllContacts() {
        return contactList;
    }


    //NEW FUNCTION ADDED TO RETRIEVE A CONTACT BY NAME:

    //EFFECTS: returns contact if found by name, otherwise returns false;
    public Contact getContactByName(String name) {
        for (Contact c: contactList) {
            if (c.getName() == name) {
                return c;
            } else {
                return null;
            }
        }
        return null;
    }

    //EFFECTS: returns true if there is a contact with the name you are searching for,
    //false otherwise
    public boolean doesNameMatch(String name) {
        boolean x = false;
        for (Contact c: contactList) {
            if (c.getName() == name) {
                x = true;
            } else {
                //do nothing;
            }
        }
        return x;
    }
}
