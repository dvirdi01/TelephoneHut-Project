package model;


import java.util.ArrayList;
import java.util.List;

/**
 Represents a list of all contacts in the phonebook according to their type
 */
public class ContactList implements List<> { //TODO: HELP

    public static List<Contact> mainContactList = new ArrayList<>(); //make it a subclass of list

    Contact anotherContact = new Contact("Jasleen", "3612874914", "kajnkajkd", "FRIEND");

    //Constructs a new ContactList with 0 contacts added
    public ContactList() {

    }

    //MODIFIES: this
    //EFFECTS: checks if the contact already exists in the contactlist or not.
    public boolean checkContactAlreadyThere(Contact c) {
        return false;
        // if contactlist.contains(c) == true,then return false and print an error message
        // if it doesnt contain; add it to contactlist. // can just execute in the add contact tho //TODO: guidance

    }

    //MODIFIES: this
    //EFFECTS: adds a new contact into contactlist
    public void addContact(Contact c) {
        mainContactList.add(c);
    }


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

    //EFFECTS: displays all contacts in the book -TODO: SHOULD THIS BE IN PHONEBOOK INSTEAD
    public List<Contact> viewAllContacts() {
        return mainContactList;
    }

}
