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


    //REQUIRES: Contact not already present in the contact list
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

}
