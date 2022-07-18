package model;

import java.util.ArrayList;
import java.util.List;

/**
 Represents a contact in the phonebook which has a name, phone number,
 and email associated with it.
 */

public class Contact {

    //representing mainContactList using an arraylist
    List<Contact> mainContactList = new ArrayList<Contact>();

    //TODO: NOT LETTING ME TYPE IN 10 DIGITS despite making it long
    Contact firstContact = new Contact("Divjot", 778703444,
            "virdidivjot@gmail.com");


    //TODO: dont know how enumerations work + show example in edx
    public enum TypeOfContact {
        FAMILY, FRIENDS, WORK;
    }

    //REQUIRES: Contact doesn't already exist
    //EFFECTS: creates a new contact entry
    public Contact(String name, long phoneNumber, String email) {
        addContact();
    }


    //MODIFIES: this
    //EFFECTS: adds a new contact into contactlist
    public void addContact() {
        mainContactList.add(firstContact);
    }

    //REQUIRES: Contact exists in the Phonebook
    //EFFECTS: displays the contact information searched by name
    public Contact viewContact(String name) {
        return firstContact;
    }

    //REQUIRES: contact that needs to be modified exists in the Phonebook
    //MODIFIES: this
    //EFFECTS: modifies details of the contact
    //TODO; would having separate setters for each of these fields be better?
    public void modifyContact(Contact c, String newName, long newNumber, String newEmail,
                              String newType) {
        //stub
    }

    //REQUIRES: Contact exists in the first place
    //MODIFIES: this
    //EFFECTS: deletes the contact
    public void deleteContact(String name) {
        //stub
    }

    //EFFECTS: returns name of the contact
    public String getName() {
        return null;
    }

    //REQUIRES: phoneNumber is 10 digits long, and it is not 0000000000
    //EFFECTS: returns contact's phoneNumber
    public int getPhoneNumber() {
        return 0;
    }


    //EFFECTS: returns the contact's email
    public String getEmail() {
        return null;
    }


}
