package model;


import java.util.ArrayList;
import java.util.List;

/**
 Represents a list of all contacts in the phonebook according to their type
 */
public class ContactList {

    public static List<Contact> mainContactList = new ArrayList<>();

    Contact anotherContact = new Contact("Jasleen", "3612874914", "kajnkajkd", "FRIEND");

    //Constructs a new ContactList with 0 contacts added
    public ContactList() {

    }

    //MODIFIES: this
    //EFFECTS: adds a new contact into contactlist
    public void addContact(Contact c) {
        mainContactList.add(c);
    }


    //TODO: is it better to add addcontact, viewcontact, deletecontact to contactlist instead?
    //REQUIRES: Contact exists in the Phonebook
    //EFFECTS: displays the contact information searched by name
    public Contact viewContact(Contact c) {
        return anotherContact;
    }


    //MODIFIES: this
    //EFFECTS: deletes the contact
    public void deleteContact(Contact c) {
        mainContactList.remove(c);
    }

    //EFFECTS: displays all contacts in the book
    public List<Contact> viewAllContacts() {
        return mainContactList;
    }

}
