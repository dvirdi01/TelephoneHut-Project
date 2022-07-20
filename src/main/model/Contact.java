package model;

import java.util.ArrayList;
import java.util.List;

import static model.ContactList.mainContactList;

/**
 Represents a contact in the phonebook which has a name, phone number,
 and email associated with it.
 */

public class Contact {

    private String name;
    private String phoneNumber;
    private String email;
    private String type;


    Contact firstContact = new Contact("Divjot", "1234567890",
            "virdidivjot@gmail.com", "FRIEND");


    //REQUIRES: Contact doesn't already exist
    //EFFECTS: creates a new contact entry
    public Contact(String name, String phoneNumber, String email, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
        //addContact(); //TODO: should i just not call this here
    }



    //EFFECTS: adds a new contact into contactlist
//    private void addContact() {
//        // mainContactList.add(this);
//        // this
//    }


    //REQUIRES: contact that needs to be modified exists in the Phonebook
    //MODIFIES: this
    //EFFECTS: modifies details of the contact
    //TODO; would having separate setters for each of these fields be better?
    public void modifyContact(String newName, String newNumber, String newEmail,
                              String newType) {
        //stub
    }


    ////-----------SETTERS------------

    public void setName(String name) {
        ;
    }

    public void setPhoneNumber(String phoneNumber) {
        ;
    }

    public void setEmail(String email) {
        ;
    }

    public void setType(String type) {
        ;
    }


   //-----------GETTERS------------


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

    //EFFECTS: returns the contact type
    public String getType() {
        return null;
    }


}
